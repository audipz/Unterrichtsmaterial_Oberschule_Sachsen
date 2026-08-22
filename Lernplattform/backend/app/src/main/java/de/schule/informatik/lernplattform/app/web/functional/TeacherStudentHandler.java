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
public class TeacherStudentHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public TeacherStudentHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    public ServerResponse listStudents(ServerRequest request) {
        UUID accountId = currentActor.id();
        UUID schoolId = requireTeacherSchool(accountId, request.pathVariable("school"));
        if (schoolId == null) return ServerResponse.notFound().build();

        List<Map<String, Object>> students = jdbc.query("""
                select a.id, a.display_name,
                       c.id as class_id, c.name as class_name, c.grade_level,
                       ssl.username, ssl.must_change_password
                from school_membership sm
                join account a on a.id = sm.account_id
                left join school_class_membership scm
                  on scm.student_school_membership_id = sm.id
                 and scm.status = 'ACTIVE' and scm.deleted_at is null
                left join school_class c
                  on c.id = scm.school_class_id and c.status = 'ACTIVE' and c.deleted_at is null
                left join student_school_login ssl
                  on ssl.school_membership_id = sm.id and ssl.school_id = sm.school_id
                where sm.school_id = ?
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                  and a.account_type = 'STUDENT' and a.status = 'ACTIVE' and a.deleted_at is null
                order by lower(a.display_name), a.id
                """, (rs, rowNum) -> {
                    var result = new java.util.LinkedHashMap<String, Object>();
                    result.put("id", rs.getObject("id", UUID.class).toString());
                    result.put("displayName", rs.getString("display_name"));
                    UUID classId = rs.getObject("class_id", UUID.class);
                    result.put("classId", classId == null ? null : classId.toString());
                    result.put("className", rs.getString("class_name"));
                    Object grade = rs.getObject("grade_level");
                    result.put("gradeLevel", grade == null ? null : ((Number) grade).intValue());
                    String username = rs.getString("username");
                    result.put("hasLogin", username != null);
                    result.put("username", username);
                    Object mustChange = rs.getObject("must_change_password");
                    result.put("mustChangePassword", mustChange == null ? null : rs.getBoolean("must_change_password"));
                    return result;
                }, schoolId);
        return ServerResponse.ok().body(students);
    }

    private UUID requireTeacherSchool(UUID accountId, String schoolSlug) {
        return jdbc.query("""
                select s.id
                from account a
                join school_membership sm on sm.account_id = a.id
                join school s on s.id = sm.school_id
                where a.id = ? and a.account_type = 'TEACHER'
                  and a.status = 'ACTIVE' and a.deleted_at is null
                  and lower(s.slug) = lower(?) and s.status = 'ACTIVE'
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                """, (rs, rowNum) -> rs.getObject("id", UUID.class), accountId, schoolSlug)
                .stream().findFirst().orElse(null);
    }
}
