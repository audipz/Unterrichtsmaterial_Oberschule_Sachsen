package de.schule.informatik.lernplattform.persistence.schoolclass;

import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class ClassAdministrationAdapter implements ClassAdministrationPort {

    private final NamedParameterJdbcTemplate jdbc;

    public ClassAdministrationAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public UserContext requireActiveUser(UUID userId) {
        String sql = """
                SELECT u.id, u.school_id, u.display_name_normalized,
                       COALESCE(string_agg(ur.role, ','), '') roles
                FROM app_user u
                LEFT JOIN user_role ur ON ur.user_id = u.id AND ur.school_id = u.school_id
                WHERE u.id = :userId AND u.deleted_at IS NULL AND u.status = 'ACTIVE'
                GROUP BY u.id, u.school_id, u.display_name_normalized
                """;
        return jdbc.queryForObject(sql, new MapSqlParameterSource("userId", userId), (rs, rowNum) -> {
            Set<String> roles = new HashSet<>();
            String value = rs.getString("roles");
            if (value != null && !value.isBlank()) roles.addAll(java.util.List.of(value.split(",")));
            return new UserContext(
                    rs.getObject("id", UUID.class),
                    rs.getObject("school_id", UUID.class),
                    rs.getString("display_name_normalized"),
                    Set.copyOf(roles));
        });
    }

    @Override
    public UUID createClass(UUID schoolId, String name, int gradeLevel, String schoolYear, UUID actorId) {
        UUID id = UUID.randomUUID();
        jdbc.update("""
                INSERT INTO school_class
                    (id, school_id, name, grade_level, school_year, status, created_by, updated_by)
                VALUES
                    (:id, :schoolId, :name, :gradeLevel, :schoolYear, 'ACTIVE', :actorId, :actorId)
                """, new MapSqlParameterSource()
                .addValue("id", id).addValue("schoolId", schoolId).addValue("name", name)
                .addValue("gradeLevel", gradeLevel).addValue("schoolYear", schoolYear).addValue("actorId", actorId));
        return id;
    }

    @Override
    public void addStudent(UUID schoolId, UUID classId, UUID studentId, LocalDate validFrom, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        jdbc.update("""
                INSERT INTO school_class_membership
                    (id, school_class_id, student_id, valid_from, status, created_by, updated_by)
                VALUES (:id, :classId, :studentId, :validFrom, 'ACTIVE', :actorId, :actorId)
                """, new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID()).addValue("classId", classId).addValue("studentId", studentId)
                .addValue("validFrom", validFrom == null ? LocalDate.now() : validFrom).addValue("actorId", actorId));
    }

    @Override
    public void moveStudent(UUID schoolId,
                            UUID studentId,
                            UUID sourceClassId,
                            UUID targetClassId,
                            LocalDate effectiveDate,
                            UUID actorId) {
        requireActiveClassOfSchool(schoolId, sourceClassId);
        requireActiveClassOfSchool(schoolId, targetClassId);

        var params = new MapSqlParameterSource()
                .addValue("studentId", studentId)
                .addValue("sourceClassId", sourceClassId)
                .addValue("targetClassId", targetClassId)
                .addValue("effectiveDate", effectiveDate)
                .addValue("actorId", actorId)
                .addValue("newMembershipId", UUID.randomUUID());

        int ended = jdbc.update("""
                UPDATE school_class_membership
                   SET status = 'ENDED',
                       valid_until = :effectiveDate,
                       updated_at = now(),
                       updated_by = :actorId
                 WHERE school_class_id = :sourceClassId
                   AND student_id = :studentId
                   AND status = 'ACTIVE'
                   AND deleted_at IS NULL
                """, params);
        if (ended != 1) {
            throw new IllegalArgumentException("active source class membership not found");
        }

        jdbc.update("""
                INSERT INTO school_class_membership
                    (id, school_class_id, student_id, valid_from, status, created_by, updated_by)
                VALUES
                    (:newMembershipId, :targetClassId, :studentId, :effectiveDate, 'ACTIVE', :actorId, :actorId)
                """, params);
    }

    @Override
    public void addTeacher(UUID schoolId, UUID classId, UUID teacherId, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        jdbc.update("""
                INSERT INTO class_teacher (school_class_id, teacher_id, created_by)
                VALUES (:classId, :teacherId, :actorId)
                """, new MapSqlParameterSource()
                .addValue("classId", classId).addValue("teacherId", teacherId).addValue("actorId", actorId));
    }

    @Override
    public Set<UUID> activeClassIdsForUser(UUID userId) {
        String sql = """
                SELECT school_class_id FROM school_class_membership
                 WHERE student_id = :userId AND status = 'ACTIVE' AND deleted_at IS NULL
                UNION
                SELECT school_class_id FROM class_teacher
                 WHERE teacher_id = :userId AND deleted_at IS NULL
                """;
        return new HashSet<>(jdbc.query(sql, new MapSqlParameterSource("userId", userId),
                (rs, rowNum) -> rs.getObject(1, UUID.class)));
    }

    @Override
    public void lockClasses(Set<UUID> classIds) {
        if (classIds == null || classIds.isEmpty()) return;
        jdbc.query("SELECT id FROM school_class WHERE id IN (:ids) ORDER BY id FOR UPDATE",
                new MapSqlParameterSource("ids", classIds), rs -> null);
    }

    private void requireActiveClassOfSchool(UUID schoolId, UUID classId) {
        Integer count = jdbc.queryForObject("""
                SELECT count(*) FROM school_class
                WHERE id = :classId AND school_id = :schoolId AND deleted_at IS NULL AND status = 'ACTIVE'
                """, new MapSqlParameterSource().addValue("classId", classId).addValue("schoolId", schoolId), Integer.class);
        if (count == null || count == 0) throw new IllegalArgumentException("class not found in school");
    }
}
