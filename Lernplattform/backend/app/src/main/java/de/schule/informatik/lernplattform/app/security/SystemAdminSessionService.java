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
public class SystemAdminSessionService {

    private static final Duration SESSION_LIFETIME = Duration.ofMinutes(30);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JdbcTemplate jdbc;
    private final PasswordHashPort passwordHashPort;

    public SystemAdminSessionService(JdbcTemplate jdbc, PasswordHashPort passwordHashPort) {
        this.jdbc = jdbc;
        this.passwordHashPort = passwordHashPort;
    }

    @Transactional
    public LoginResult login(String username, String password) {
        String normalized = requireUsername(username).toLowerCase(Locale.ROOT);
        List<LoginRow> rows = jdbc.query("""
                select a.id, l.password_hash, l.must_change_password
                from system_account_login l
                join account a on a.id = l.account_id
                join system_role r on r.account_id = a.id and r.role = 'SYSTEM_ADMIN'
                where l.username_normalized = ?
                  and a.account_type = 'SYSTEM'
                  and a.status = 'ACTIVE'
                  and a.deleted_at is null
                """, (rs, rowNum) -> new LoginRow(
                rs.getObject("id", UUID.class),
                rs.getString("password_hash"),
                rs.getBoolean("must_change_password")), normalized);

        if (rows.size() != 1 || !passwordHashPort.matches(password, rows.getFirst().passwordHash())) {
            throw new SecurityException("Ungültige Anmeldedaten.");
        }

        LoginRow row = rows.getFirst();
        String rawToken = randomToken();
        Instant now = Instant.now();
        Instant expiresAt = now.plus(SESSION_LIFETIME);
        jdbc.update("delete from system_admin_session where expires_at <= ?", now);
        jdbc.update("""
                insert into system_admin_session (id, account_id, token_hash, created_at, expires_at, last_seen_at)
                values (?, ?, ?, ?, ?, ?)
                """, UUID.randomUUID(), row.accountId(), sha256(rawToken), now, expiresAt, now);
        return new LoginResult(rawToken, expiresAt, row.mustChangePassword());
    }

    @Transactional
    public Optional<SessionIdentity> authenticate(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) return Optional.empty();
        Instant now = Instant.now();
        List<SessionIdentity> rows = jdbc.query("""
                select s.id, s.account_id, l.must_change_password
                from system_admin_session s
                join account a on a.id = s.account_id
                join system_account_login l on l.account_id = a.id
                join system_role r on r.account_id = a.id and r.role = 'SYSTEM_ADMIN'
                where s.token_hash = ? and s.expires_at > ?
                  and a.account_type = 'SYSTEM' and a.status = 'ACTIVE' and a.deleted_at is null
                """, (rs, rowNum) -> new SessionIdentity(
                rs.getObject("id", UUID.class),
                rs.getObject("account_id", UUID.class),
                rs.getBoolean("must_change_password")), sha256(rawToken), now);
        if (rows.size() != 1) return Optional.empty();
        SessionIdentity identity = rows.getFirst();
        jdbc.update("update system_admin_session set last_seen_at = ? where id = ?", now, identity.sessionId());
        return Optional.of(identity);
    }

    @Transactional
    public void changePassword(UUID accountId, String currentPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 16) {
            throw new IllegalArgumentException("Das neue Passwort muss mindestens 16 Zeichen enthalten.");
        }
        String hash = jdbc.queryForObject(
                "select password_hash from system_account_login where account_id = ?",
                String.class, accountId);
        if (!passwordHashPort.matches(currentPassword, hash)) {
            throw new SecurityException("Das aktuelle Passwort ist falsch.");
        }
        jdbc.update("""
                update system_account_login
                set password_hash = ?, must_change_password = false, updated_at = now()
                where account_id = ?
                """, passwordHashPort.hash(newPassword), accountId);
        jdbc.update("delete from system_admin_session where account_id = ?", accountId);
    }

    @Transactional
    public void logout(String rawToken) {
        if (rawToken != null && !rawToken.isBlank()) {
            jdbc.update("delete from system_admin_session where token_hash = ?", sha256(rawToken));
        }
    }

    public Duration sessionLifetime() {
        return SESSION_LIFETIME;
    }

    private static String requireUsername(String username) {
        if (username == null || username.isBlank()) throw new SecurityException("Ungültige Anmeldedaten.");
        return username.trim();
    }

    private static String randomToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    private record LoginRow(UUID accountId, String passwordHash, boolean mustChangePassword) {}
    public record LoginResult(String token, Instant expiresAt, boolean mustChangePassword) {}
    public record SessionIdentity(UUID sessionId, UUID accountId, boolean mustChangePassword) {}
}
