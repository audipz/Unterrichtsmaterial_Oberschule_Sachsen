package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class StudentMoveHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public StudentMoveHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    @Transactional
    public ServerResponse moveClass(ServerRequest request) throws Exception {
        UUID actorId = currentActor.id();
        SchoolAccess access = requireTeacherSchoolAccess(actorId, request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();

        UUID studentId = parseUuid(request.pathVariable("studentId"));
        if (studentId == null) return ServerResponse.notFound().build();
        MoveClassRequest body = request.body(MoveClassRequest.class);
        UUID targetClassId = parseUuid(body.targetClassId());
        if (targetClassId == null) return ServerResponse.badRequest().body(Map.of("code", "INVALID_TARGET_CLASS"));

        StudentMembership student = lockStudentMembership(studentId, access.schoolId());
        if (student == null) return ServerResponse.notFound().build();
        if (!activeClassExists(targetClassId, access.schoolId())) return ServerResponse.notFound().build();

        LocalDate effectiveDate = body.effectiveDate() == null ? LocalDate.now() : body.effectiveDate();
        jdbc.update("""
                update school_class_membership
                set status = 'ENDED', valid_until = ?, updated_at = now(), updated_by = ?
                where student_school_membership_id = ?
                  and school_id = ?
                  and status = 'ACTIVE' and deleted_at is null
                """, effectiveDate.minusDays(1), actorId, student.membershipId(), access.schoolId());

        jdbc.update("""
                insert into school_class_membership (
                    id, school_class_id, school_id, student_school_membership_id,
                    valid_from, status, created_by, updated_by
                ) values (?, ?, ?, ?, ?, 'ACTIVE', ?, ?)
                """, UUID.randomUUID(), targetClassId, access.schoolId(), student.membershipId(), effectiveDate, actorId, actorId);

        return ServerResponse.ok().body(Map.of(
                "studentId", studentId.toString(),
                "schoolId", access.schoolId().toString(),
                "classId", targetClassId.toString(),
                "effectiveDate", effectiveDate.toString()));
    }

    @Transactional
    public ServerResponse moveSchool(ServerRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!hasAuthority(authentication, "SYSTEM_ADMIN")) {
            return ServerResponse.status(HttpStatus.NOT_FOUND).build();
        }

        UUID actorId = currentActor.id();
        UUID studentId = parseUuid(request.pathVariable("studentId"));
        if (studentId == null) return ServerResponse.notFound().build();
        MoveSchoolRequest body = request.body(MoveSchoolRequest.class);

        School targetSchool = findSchool(body.targetSchoolSlug());
        UUID targetClassId = parseUuid(body.targetClassId());
        if (targetSchool == null || targetClassId == null || !activeClassExists(targetClassId, targetSchool.id())) {
            return ServerResponse.notFound().build();
        }

        Account account = lockStudentAccount(studentId);
        if (account == null) return ServerResponse.notFound().build();
        LocalDate effectiveDate = body.effectiveDate() == null ? LocalDate.now() : body.effectiveDate();
        OffsetDateTime effectiveAt = effectiveDate.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());

        jdbc.update("""
                update school_class_membership scm
                set status = 'ENDED', valid_until = ?, updated_at = now(), updated_by = ?
                where scm.student_school_membership_id in (
                    select sm.id from school_membership sm
                    where sm.account_id = ? and sm.status = 'ACTIVE' and sm.deleted_at is null
                ) and scm.status = 'ACTIVE' and scm.deleted_at is null
                """, effectiveDate.minusDays(1), actorId, studentId);

        jdbc.update("""
                update school_membership
                set status = 'ENDED', left_at = ?, updated_at = now(), updated_by = ?
                where account_id = ? and status = 'ACTIVE' and deleted_at is null
                """, effectiveAt, actorId, studentId);

        UUID membershipId = UUID.randomUUID();
        jdbc.update("""
                insert into school_membership (
                    id, account_id, school_id, status, joined_at, created_by, updated_by
                ) values (?, ?, ?, 'ACTIVE', ?, ?, ?)
                """, membershipId, studentId, targetSchool.id(), effectiveAt, actorId, actorId);

        jdbc.update("""
                insert into school_class_membership (
                    id, school_class_id, school_id, student_school_membership_id,
                    valid_from, status, created_by, updated_by
                ) values (?, ?, ?, ?, ?, 'ACTIVE', ?, ?)
                """, UUID.randomUUID(), targetClassId, targetSchool.id(), membershipId, effectiveDate, actorId, actorId);

        return ServerResponse.ok().body(Map.of(
                "studentId", studentId.toString(),
                "schoolId", targetSchool.id().toString(),
                "schoolSlug", targetSchool.slug(),
                "classId", targetClassId.toString(),
                "effectiveDate", effectiveDate.toString(),
                "loginRequired", true));
    }

    private SchoolAccess requireTeacherSchoolAccess(UUID accountId, String schoolSlug) {
        return jdbc.query("""
                select s.id as school_id, sm.id as membership_id
                from account a
                join school_membership sm on sm.account_id = a.id
                join school s on s.id = sm.school_id
                where a.id = ? and a.account_type = 'TEACHER'
                  and a.status = 'ACTIVE' and a.deleted_at is null
                  and lower(s.slug) = lower(?) and s.status = 'ACTIVE'
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                """, (rs, rowNum) -> new SchoolAccess(
                rs.getObject("school_id", UUID.class), rs.getObject("membership_id", UUID.class)), accountId, schoolSlug)
                .stream().findFirst().orElse(null);
    }

    private StudentMembership lockStudentMembership(UUID studentId, UUID schoolId) {
        return jdbc.query("""
                select sm.id
                from school_membership sm
                join account a on a.id = sm.account_id
                where sm.account_id = ? and sm.school_id = ?
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                for update
                """, (rs, rowNum) -> new StudentMembership(rs.getObject("id", UUID.class)), studentId, schoolId)
                .stream().findFirst().orElse(null);
    }

    private Account lockStudentAccount(UUID studentId) {
        return jdbc.query("""
                select id from account
                where id = ? and account_type = 'STUDENT' and status = 'ACTIVE' and deleted_at is null
                for update
                """, (rs, rowNum) -> new Account(rs.getObject("id", UUID.class)), studentId)
                .stream().findFirst().orElse(null);
    }

    private boolean activeClassExists(UUID classId, UUID schoolId) {
        Boolean exists = jdbc.queryForObject("""
                select exists(select 1 from school_class
                              where id = ? and school_id = ?
                                and status = 'ACTIVE' and deleted_at is null)
                """, Boolean.class, classId, schoolId);
        return Boolean.TRUE.equals(exists);
    }

    private School findSchool(String slug) {
        if (slug == null || slug.isBlank()) return null;
        return jdbc.query("""
                select id, slug from school
                where lower(slug) = lower(?) and status = 'ACTIVE' and deleted_at is null
                """, (rs, rowNum) -> new School(rs.getObject("id", UUID.class), rs.getString("slug")), slug)
                .stream().findFirst().orElse(null);
    }

    private static UUID parseUuid(String value) {
        try { return UUID.fromString(value); } catch (RuntimeException ex) { return null; }
    }

    private static boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(authority::equals);
    }

    public record MoveClassRequest(String targetClassId, LocalDate effectiveDate) {}
    public record MoveSchoolRequest(String targetSchoolSlug, String targetClassId, LocalDate effectiveDate) {}
    private record SchoolAccess(UUID schoolId, UUID membershipId) {}
    private record StudentMembership(UUID membershipId) {}
    private record Account(UUID id) {}
    private record School(UUID id, String slug) {}
}
