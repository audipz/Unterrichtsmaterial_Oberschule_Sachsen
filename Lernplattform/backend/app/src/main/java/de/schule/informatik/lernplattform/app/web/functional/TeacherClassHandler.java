package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class TeacherClassHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public TeacherClassHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    public ServerResponse listClasses(ServerRequest request) {
        UUID accountId = currentActor.id();
        SchoolAccess access = requireTeacherSchoolAccess(accountId, request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();

        List<Map<String, Object>> classes = jdbc.query("""
                select c.id, c.name, c.grade_level, c.school_year,
                       exists(
                           select 1 from class_teacher ct
                           where ct.school_class_id = c.id
                             and ct.teacher_school_membership_id = ?
                             and ct.deleted_at is null
                       ) as responsible,
                       (select count(*) from school_class_membership scm
                        where scm.school_class_id = c.id
                          and scm.status = 'ACTIVE' and scm.deleted_at is null) as student_count,
                       (select count(*) from class_teacher ct2
                        where ct2.school_class_id = c.id and ct2.deleted_at is null) as teacher_count
                from school_class c
                where c.school_id = ? and c.status = 'ACTIVE' and c.deleted_at is null
                order by c.grade_level, lower(c.name), c.id
                """, (rs, rowNum) -> Map.<String, Object>of(
                        "id", rs.getObject("id", UUID.class).toString(),
                        "name", rs.getString("name"),
                        "gradeLevel", rs.getInt("grade_level"),
                        "schoolYear", rs.getString("school_year"),
                        "responsible", rs.getBoolean("responsible"),
                        "studentCount", rs.getInt("student_count"),
                        "teacherCount", rs.getInt("teacher_count")),
                access.membershipId(), access.schoolId());
        return ServerResponse.ok().body(classes);
    }

    public ServerResponse listStudents(ServerRequest request) {
        UUID accountId = currentActor.id();
        SchoolAccess access = requireTeacherSchoolAccess(accountId, request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();

        UUID classId = parseUuid(request.pathVariable("classId"));
        if (classId == null || !classExists(classId, access.schoolId())) return ServerResponse.notFound().build();

        List<Map<String, Object>> students = jdbc.query("""
                select a.id, a.display_name, scm.valid_from
                from school_class_membership scm
                join school_membership sm on sm.id = scm.student_school_membership_id
                join account a on a.id = sm.account_id
                where scm.school_class_id = ? and scm.school_id = ?
                  and scm.status = 'ACTIVE' and scm.deleted_at is null
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                order by lower(a.display_name), a.id
                """, (rs, rowNum) -> Map.<String, Object>of(
                        "id", rs.getObject("id", UUID.class).toString(),
                        "displayName", rs.getString("display_name"),
                        "validFrom", rs.getDate("valid_from").toLocalDate().toString()),
                classId, access.schoolId());
        return ServerResponse.ok().body(students);
    }

    public ServerResponse listTeachers(ServerRequest request) {
        SchoolAccess access = requireTeacherSchoolAccess(currentActor.id(), request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();
        UUID classId = parseUuid(request.pathVariable("classId"));
        if (classId == null || !classExists(classId, access.schoolId())) return ServerResponse.notFound().build();

        List<Map<String, Object>> teachers = jdbc.query("""
                select a.id, a.display_name, sm.id as membership_id
                from class_teacher ct
                join school_membership sm on sm.id = ct.teacher_school_membership_id
                join account a on a.id = sm.account_id
                where ct.school_class_id = ? and ct.school_id = ? and ct.deleted_at is null
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'TEACHER' and a.status = 'ACTIVE' and a.deleted_at is null
                order by lower(a.display_name), a.id
                """, (rs, rowNum) -> Map.<String, Object>of(
                        "accountId", rs.getObject("id", UUID.class).toString(),
                        "membershipId", rs.getObject("membership_id", UUID.class).toString(),
                        "displayName", rs.getString("display_name")),
                classId, access.schoolId());
        return ServerResponse.ok().body(teachers);
    }

    public ServerResponse listAvailableTeachers(ServerRequest request) {
        SchoolAccess access = requireTeacherSchoolAccess(currentActor.id(), request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();
        return ServerResponse.ok().body(jdbc.query("""
                select a.id, a.display_name, sm.id as membership_id
                from school_membership sm
                join account a on a.id = sm.account_id
                where sm.school_id = ? and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'TEACHER' and a.status = 'ACTIVE' and a.deleted_at is null
                order by lower(a.display_name), a.id
                """, (rs, rowNum) -> Map.<String, Object>of(
                        "accountId", rs.getObject("id", UUID.class).toString(),
                        "membershipId", rs.getObject("membership_id", UUID.class).toString(),
                        "displayName", rs.getString("display_name")), access.schoolId()));
    }

    @Transactional
    public ServerResponse addTeacher(ServerRequest request) throws Exception {
        SchoolAccess access = requireTeacherSchoolAccess(currentActor.id(), request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();
        UUID classId = parseUuid(request.pathVariable("classId"));
        if (classId == null || !lockActiveClass(classId, access.schoolId())) return ServerResponse.notFound().build();

        TeacherAssignment body = request.body(TeacherAssignment.class);
        UUID membershipId = parseUuid(body.membershipId());
        if (membershipId == null || !isActiveTeacherMembership(membershipId, access.schoolId())) {
            return ServerResponse.badRequest().body(Map.of("error", "INVALID_TEACHER"));
        }

        jdbc.update("""
                insert into class_teacher (school_class_id, school_id, teacher_school_membership_id, created_by)
                values (?, ?, ?, ?)
                on conflict (school_class_id, teacher_school_membership_id)
                do update set deleted_at = null, deleted_by = null
                """, classId, access.schoolId(), membershipId, currentActor.id());
        return ServerResponse.noContent().build();
    }

    @Transactional
    public ServerResponse removeTeacher(ServerRequest request) {
        SchoolAccess access = requireTeacherSchoolAccess(currentActor.id(), request.pathVariable("school"));
        if (access == null) return ServerResponse.notFound().build();
        UUID classId = parseUuid(request.pathVariable("classId"));
        UUID membershipId = parseUuid(request.pathVariable("membershipId"));
        if (classId == null || membershipId == null || !lockActiveClass(classId, access.schoolId())) {
            return ServerResponse.notFound().build();
        }

        Integer count = jdbc.queryForObject("""
                select count(*) from class_teacher
                where school_class_id = ? and school_id = ? and deleted_at is null
                """, Integer.class, classId, access.schoolId());
        if (count == null || count <= 1) {
            return ServerResponse.status(409).body(Map.of("error", "LAST_CLASS_TEACHER"));
        }

        int updated = jdbc.update("""
                update class_teacher
                set deleted_at = now(), deleted_by = ?
                where school_class_id = ? and school_id = ?
                  and teacher_school_membership_id = ? and deleted_at is null
                """, currentActor.id(), classId, access.schoolId(), membershipId);
        return updated == 0 ? ServerResponse.notFound().build() : ServerResponse.noContent().build();
    }

    private boolean classExists(UUID classId, UUID schoolId) {
        Boolean exists = jdbc.queryForObject("""
                select exists(select 1 from school_class
                              where id = ? and school_id = ?
                                and status = 'ACTIVE' and deleted_at is null)
                """, Boolean.class, classId, schoolId);
        return Boolean.TRUE.equals(exists);
    }

    private boolean lockActiveClass(UUID classId, UUID schoolId) {
        return !jdbc.query("""
                select id from school_class
                where id = ? and school_id = ? and status = 'ACTIVE' and deleted_at is null
                for update
                """, (rs, rowNum) -> rs.getObject("id", UUID.class), classId, schoolId).isEmpty();
    }

    private boolean isActiveTeacherMembership(UUID membershipId, UUID schoolId) {
        Boolean exists = jdbc.queryForObject("""
                select exists(
                    select 1 from school_membership sm
                    join account a on a.id = sm.account_id
                    where sm.id = ? and sm.school_id = ?
                      and sm.status = 'ACTIVE' and sm.deleted_at is null
                      and a.account_type = 'TEACHER' and a.status = 'ACTIVE' and a.deleted_at is null)
                """, Boolean.class, membershipId, schoolId);
        return Boolean.TRUE.equals(exists);
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
                        rs.getObject("school_id", UUID.class),
                        rs.getObject("membership_id", UUID.class)), accountId, schoolSlug)
                .stream().findFirst().orElse(null);
    }

    private static UUID parseUuid(String value) {
        try { return UUID.fromString(value); } catch (RuntimeException ex) { return null; }
    }

    public record TeacherAssignment(String membershipId) {}
    private record SchoolAccess(UUID schoolId, UUID membershipId) {}
}
