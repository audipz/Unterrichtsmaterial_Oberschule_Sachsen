package de.schule.informatik.lernplattform.app.security;

import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentSessionService {
    private static final Duration SESSION_LIFETIME = Duration.ofHours(8);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JdbcTemplate jdbc;
    private final PasswordHashPort passwordHashPort;

    public StudentSessionService(JdbcTemplate jdbc, PasswordHashPort passwordHashPort) {
        this.jdbc = jdbc;
        this.passwordHashPort = passwordHashPort;
    }

    @Transactional
    public LoginResult login(String schoolSlug, String username, String password) {
        if (schoolSlug == null || schoolSlug.isBlank() || username == null || username.isBlank()) {
            throw new SecurityException("Ungültige Anmeldedaten.");
        }
        String normalized = username.trim().toLowerCase(Locale.ROOT);
        List<LoginRow> rows = jdbc.query("""
                select a.id as account_id, sm.id as membership_id, l.password_hash, l.must_change_password
                from student_school_login l
                join school_membership sm on sm.id = l.school_membership_id and sm.school_id = l.school_id
                join school s on s.id = sm.school_id
                join account a on a.id = sm.account_id
                where lower(s.slug) = lower(?) and l.username_normalized = ?
                  and s.status = 'ACTIVE' and s.deleted_at is null
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                """, (rs, rowNum) -> new LoginRow(
                rs.getObject("account_id", UUID.class),
                rs.getObject("membership_id", UUID.class),
                rs.getString("password_hash"),
                rs.getBoolean("must_change_password")), schoolSlug, normalized);
        if (rows.size() != 1 || !passwordHashPort.matches(password, rows.getFirst().passwordHash())) {
            throw new SecurityException("Ungültige Anmeldedaten.");
        }
        var row = rows.getFirst();
        String rawToken = randomToken();
        Instant now = Instant.now();
        Instant expiresAt = now.plus(SESSION_LIFETIME);
        jdbc.update("delete from student_session where expires_at <= ?", now);
        jdbc.update("""
                insert into student_session (id, account_id, school_membership_id, token_hash, created_at, expires_at, last_seen_at)
                values (?, ?, ?, ?, ?, ?, ?)
                """, UUID.randomUUID(), row.accountId(), row.membershipId(), sha256(rawToken), now, expiresAt, now);
        return new LoginResult(rawToken, expiresAt, row.mustChangePassword());
    }

    @Transactional
    public Optional<SessionIdentity> authenticate(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) return Optional.empty();
        Instant now = Instant.now();
        List<SessionIdentity> rows = jdbc.query("""
                select ss.id, ss.account_id, ss.school_membership_id, l.must_change_password
                from student_session ss
                join school_membership sm on sm.id = ss.school_membership_id
                join account a on a.id = ss.account_id
                join student_school_login l on l.school_membership_id = sm.id
                where ss.token_hash = ? and ss.expires_at > ?
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                """, (rs, rowNum) -> new SessionIdentity(
                rs.getObject("id", UUID.class), rs.getObject("account_id", UUID.class),
                rs.getObject("school_membership_id", UUID.class), rs.getBoolean("must_change_password")),
                sha256(rawToken), now);
        if (rows.size() != 1) return Optional.empty();
        var identity = rows.getFirst();
        jdbc.update("update student_session set last_seen_at = ? where id = ?", now, identity.sessionId());
        return Optional.of(identity);
    }

    @Transactional
    public void changePassword(UUID accountId, String currentPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 12) throw new IllegalArgumentException("Passwort zu kurz.");
        var rows = jdbc.query("""
                select l.school_membership_id, l.password_hash
                from student_school_login l
                join school_membership sm on sm.id = l.school_membership_id
                where sm.account_id = ? and sm.status = 'ACTIVE' and sm.deleted_at is null
                """, (rs, rowNum) -> new PasswordRow(rs.getObject(1, UUID.class), rs.getString(2)), accountId);
        if (rows.size() != 1 || !passwordHashPort.matches(currentPassword, rows.getFirst().passwordHash())) {
            throw new SecurityException("Das aktuelle Passwort ist falsch.");
        }
        jdbc.update("update student_school_login set password_hash = ?, must_change_password = false, updated_at = now() where school_membership_id = ?",
                passwordHashPort.hash(newPassword), rows.getFirst().membershipId());
        jdbc.update("delete from student_session where account_id = ?", accountId);
    }

    @Transactional
    public void logout(String rawToken) {
        if (rawToken != null && !rawToken.isBlank()) jdbc.update("delete from student_session where token_hash = ?", sha256(rawToken));
    }

    private static String randomToken() { byte[] b = new byte[32]; RANDOM.nextBytes(b); return Base64.getUrlEncoder().withoutPadding().encodeToString(b); }
    private static String sha256(String value) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
        catch (Exception e) { throw new IllegalStateException(e); }
    }

    private record LoginRow(UUID accountId, UUID membershipId, String passwordHash, boolean mustChangePassword) {}
    private record PasswordRow(UUID membershipId, String passwordHash) {}
    public record LoginResult(String token, Instant expiresAt, boolean mustChangePassword) {}
    public record SessionIdentity(UUID sessionId, UUID accountId, UUID membershipId, boolean mustChangePassword) {}
}
