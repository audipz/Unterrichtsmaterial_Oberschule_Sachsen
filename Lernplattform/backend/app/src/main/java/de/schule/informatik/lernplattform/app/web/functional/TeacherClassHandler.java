package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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

        UUID classId;
        try {
            classId = UUID.fromString(request.pathVariable("classId"));
        } catch (IllegalArgumentException ex) {
            return ServerResponse.notFound().build();
        }

        Boolean classExists = jdbc.queryForObject("""
                select exists(select 1 from school_class
                              where id = ? and school_id = ?
                                and status = 'ACTIVE' and deleted_at is null)
                """, Boolean.class, classId, access.schoolId());
        if (!Boolean.TRUE.equals(classExists)) return ServerResponse.notFound().build();

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

    private record SchoolAccess(UUID schoolId, UUID membershipId) {}
}
