package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class StudentCredentialHandler {

    private static final Pattern USERNAME = Pattern.compile("[A-Za-z0-9._-]{3,40}");
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;
    private final PasswordHashPort passwordHashPort;

    public StudentCredentialHandler(JdbcTemplate jdbc,
                                    CurrentActor currentActor,
                                    PasswordHashPort passwordHashPort) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
        this.passwordHashPort = passwordHashPort;
    }

    @Transactional
    public ServerResponse create(ServerRequest request) throws Exception {
        UUID actorId = currentActor.id();
        SchoolAccess school = requireTeacherSchool(actorId, request.pathVariable("school"));
        if (school == null) return ServerResponse.notFound().build();

        UUID studentId = parseUuid(request.pathVariable("studentId"));
        if (studentId == null) return ServerResponse.notFound().build();
        StudentMembership student = activeStudentMembership(studentId, school.schoolId());
        if (student == null) return ServerResponse.notFound().build();

        CreateCredentialRequest body = request.body(CreateCredentialRequest.class);
        String username = normalizeInputUsername(body.username());
        if (username == null) {
            return ServerResponse.badRequest().body(Map.of("code", "INVALID_USERNAME"));
        }

        Integer existing = jdbc.queryForObject(
                "select count(*) from student_school_login where school_membership_id = ?",
                Integer.class, student.membershipId());
        if (existing != null && existing > 0) {
            return ServerResponse.status(409).body(Map.of("code", "LOGIN_ALREADY_EXISTS"));
        }

        String temporaryPassword = temporaryPassword();
        try {
            jdbc.update("""
                    insert into student_school_login (
                        school_membership_id, school_id, username, username_normalized,
                        password_hash, must_change_password
                    ) values (?, ?, ?, ?, ?, true)
                    """, student.membershipId(), school.schoolId(), username,
                    username.toLowerCase(Locale.ROOT), passwordHashPort.hash(temporaryPassword));
        } catch (DataIntegrityViolationException ex) {
            return ServerResponse.status(409).body(Map.of("code", "USERNAME_ALREADY_EXISTS"));
        }

        return ServerResponse.ok().body(Map.of(
                "studentId", studentId.toString(),
                "username", username,
                "temporaryPassword", temporaryPassword,
                "mustChangePassword", true));
    }

    @Transactional
    public ServerResponse resetPassword(ServerRequest request) {
        UUID actorId = currentActor.id();
        SchoolAccess school = requireTeacherSchool(actorId, request.pathVariable("school"));
        if (school == null) return ServerResponse.notFound().build();

        UUID studentId = parseUuid(request.pathVariable("studentId"));
        if (studentId == null) return ServerResponse.notFound().build();
        StudentMembership student = activeStudentMembership(studentId, school.schoolId());
        if (student == null) return ServerResponse.notFound().build();

        String temporaryPassword = temporaryPassword();
        int changed = jdbc.update("""
                update student_school_login
                set password_hash = ?, must_change_password = true, updated_at = now()
                where school_membership_id = ? and school_id = ?
                """, passwordHashPort.hash(temporaryPassword), student.membershipId(), school.schoolId());
        if (changed == 0) {
            return ServerResponse.status(409).body(Map.of("code", "LOGIN_NOT_CONFIGURED"));
        }

        String username = jdbc.queryForObject(
                "select username from student_school_login where school_membership_id = ?",
                String.class, student.membershipId());
        return ServerResponse.ok().body(Map.of(
                "studentId", studentId.toString(),
                "username", username,
                "temporaryPassword", temporaryPassword,
                "mustChangePassword", true));
    }

    private SchoolAccess requireTeacherSchool(UUID accountId, String schoolSlug) {
        return jdbc.query("""
                select s.id as school_id
                from account a
                join school_membership sm on sm.account_id = a.id
                join school s on s.id = sm.school_id
                where a.id = ? and a.account_type = 'TEACHER'
                  and a.status = 'ACTIVE' and a.deleted_at is null
                  and lower(s.slug) = lower(?) and s.status = 'ACTIVE'
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                """, (rs, rowNum) -> new SchoolAccess(rs.getObject("school_id", UUID.class)), accountId, schoolSlug)
                .stream().findFirst().orElse(null);
    }

    private StudentMembership activeStudentMembership(UUID studentId, UUID schoolId) {
        return jdbc.query("""
                select sm.id
                from school_membership sm
                join account a on a.id = sm.account_id
                where sm.account_id = ? and sm.school_id = ?
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                """, (rs, rowNum) -> new StudentMembership(rs.getObject("id", UUID.class)), studentId, schoolId)
                .stream().findFirst().orElse(null);
    }

    private static String normalizeInputUsername(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return USERNAME.matcher(trimmed).matches() ? trimmed : null;
    }

    private static String temporaryPassword() {
        byte[] bytes = new byte[18];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static UUID parseUuid(String value) {
        try { return UUID.fromString(value); } catch (RuntimeException ex) { return null; }
    }

    public record CreateCredentialRequest(String username) {}
    private record SchoolAccess(UUID schoolId) {}
    private record StudentMembership(UUID membershipId) {}
}
