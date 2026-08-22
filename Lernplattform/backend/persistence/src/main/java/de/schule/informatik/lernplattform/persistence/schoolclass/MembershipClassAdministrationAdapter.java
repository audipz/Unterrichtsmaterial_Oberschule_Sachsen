package de.schule.informatik.lernplattform.persistence.schoolclass;

import de.schule.informatik.lernplattform.domain.schoolclass.MembershipClassAdministrationPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class MembershipClassAdministrationAdapter implements MembershipClassAdministrationPort {

    private final NamedParameterJdbcTemplate jdbc;

    public MembershipClassAdministrationAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public MembershipContext requireActiveMembership(UUID schoolId, UUID accountId, String accountType) {
        String sql = """
                SELECT sm.id AS membership_id, sm.account_id, sm.school_id,
                       a.account_type, a.display_name_normalized
                  FROM school_membership sm
                  JOIN account a ON a.id = sm.account_id
                 WHERE sm.school_id = :schoolId
                   AND sm.account_id = :accountId
                   AND sm.status = 'ACTIVE'
                   AND sm.left_at IS NULL
                   AND a.status = 'ACTIVE'
                   AND a.deleted_at IS NULL
                   AND a.account_type = :accountType
                """;
        return jdbc.queryForObject(sql,
                new MapSqlParameterSource()
                        .addValue("schoolId", schoolId)
                        .addValue("accountId", accountId)
                        .addValue("accountType", accountType),
                (rs, rowNum) -> new MembershipContext(
                        rs.getObject("membership_id", UUID.class),
                        rs.getObject("account_id", UUID.class),
                        rs.getObject("school_id", UUID.class),
                        rs.getString("account_type"),
                        rs.getString("display_name_normalized")));
    }

    @Override
    public UUID createClassWithTeachers(UUID schoolId,
                                        String name,
                                        int gradeLevel,
                                        String schoolYear,
                                        Set<UUID> teacherMembershipIds,
                                        UUID actorId) {
        UUID classId = UUID.randomUUID();
        jdbc.update("""
                INSERT INTO school_class
                    (id, school_id, name, grade_level, school_year, status, created_by, updated_by)
                VALUES
                    (:id, :schoolId, :name, :gradeLevel, :schoolYear, 'ACTIVE', :actorId, :actorId)
                """, new MapSqlParameterSource()
                .addValue("id", classId)
                .addValue("schoolId", schoolId)
                .addValue("name", name)
                .addValue("gradeLevel", gradeLevel)
                .addValue("schoolYear", schoolYear)
                .addValue("actorId", actorId));

        for (UUID membershipId : teacherMembershipIds) {
            jdbc.update("""
                    INSERT INTO class_teacher
                        (school_class_id, school_id, teacher_school_membership_id, created_by)
                    VALUES
                        (:classId, :schoolId, :membershipId, :actorId)
                    """, new MapSqlParameterSource()
                    .addValue("classId", classId)
                    .addValue("schoolId", schoolId)
                    .addValue("membershipId", membershipId)
                    .addValue("actorId", actorId));
        }
        return classId;
    }

    @Override
    public void addStudent(UUID schoolId, UUID classId, UUID studentMembershipId, LocalDate validFrom, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        jdbc.update("""
                INSERT INTO school_class_membership
                    (id, school_class_id, school_id, student_school_membership_id,
                     valid_from, status, created_by, updated_by)
                VALUES
                    (:id, :classId, :schoolId, :membershipId,
                     :validFrom, 'ACTIVE', :actorId, :actorId)
                """, new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID())
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("membershipId", studentMembershipId)
                .addValue("validFrom", validFrom)
                .addValue("actorId", actorId));
    }

    @Override
    public void removeStudent(UUID schoolId, UUID classId, UUID studentMembershipId, LocalDate effectiveDate, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        int updated = jdbc.update("""
                UPDATE school_class_membership
                   SET status = 'ENDED', valid_until = :effectiveDate,
                       updated_at = now(), updated_by = :actorId
                 WHERE school_class_id = :classId
                   AND school_id = :schoolId
                   AND student_school_membership_id = :membershipId
                   AND status = 'ACTIVE'
                   AND deleted_at IS NULL
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("membershipId", studentMembershipId)
                .addValue("effectiveDate", effectiveDate)
                .addValue("actorId", actorId));
        if (updated != 1) throw new IllegalArgumentException("active class membership not found");
    }

    @Override
    public void moveStudent(UUID schoolId,
                            UUID studentMembershipId,
                            UUID sourceClassId,
                            UUID targetClassId,
                            LocalDate effectiveDate,
                            UUID actorId) {
        requireActiveClassOfSchool(schoolId, sourceClassId);
        requireActiveClassOfSchool(schoolId, targetClassId);
        var params = new MapSqlParameterSource()
                .addValue("schoolId", schoolId)
                .addValue("membershipId", studentMembershipId)
                .addValue("sourceClassId", sourceClassId)
                .addValue("targetClassId", targetClassId)
                .addValue("effectiveDate", effectiveDate)
                .addValue("actorId", actorId)
                .addValue("newId", UUID.randomUUID());

        int ended = jdbc.update("""
                UPDATE school_class_membership
                   SET status = 'ENDED', valid_until = :effectiveDate,
                       updated_at = now(), updated_by = :actorId
                 WHERE school_class_id = :sourceClassId
                   AND school_id = :schoolId
                   AND student_school_membership_id = :membershipId
                   AND status = 'ACTIVE'
                   AND deleted_at IS NULL
                """, params);
        if (ended != 1) throw new IllegalArgumentException("active source class membership not found");

        jdbc.update("""
                INSERT INTO school_class_membership
                    (id, school_class_id, school_id, student_school_membership_id,
                     valid_from, status, created_by, updated_by)
                VALUES
                    (:newId, :targetClassId, :schoolId, :membershipId,
                     :effectiveDate, 'ACTIVE', :actorId, :actorId)
                """, params);
    }

    @Override
    public void addTeacher(UUID schoolId, UUID classId, UUID teacherMembershipId, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        jdbc.update("""
                INSERT INTO class_teacher
                    (school_class_id, school_id, teacher_school_membership_id, created_by)
                VALUES (:classId, :schoolId, :membershipId, :actorId)
                ON CONFLICT (school_class_id, teacher_school_membership_id)
                DO UPDATE SET deleted_at = NULL, deleted_by = NULL, created_by = EXCLUDED.created_by
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("membershipId", teacherMembershipId)
                .addValue("actorId", actorId));
    }

    @Override
    public void removeTeacher(UUID schoolId, UUID classId, UUID teacherMembershipId, UUID actorId) {
        int updated = jdbc.update("""
                UPDATE class_teacher
                   SET deleted_at = now(), deleted_by = :actorId
                 WHERE school_class_id = :classId
                   AND school_id = :schoolId
                   AND teacher_school_membership_id = :membershipId
                   AND deleted_at IS NULL
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("membershipId", teacherMembershipId)
                .addValue("actorId", actorId));
        if (updated != 1) throw new IllegalArgumentException("active teacher assignment not found");
    }

    @Override
    public int activeTeacherCount(UUID schoolId, UUID classId) {
        Integer count = jdbc.queryForObject("""
                SELECT count(*)
                  FROM class_teacher ct
                  JOIN school_membership sm ON sm.id = ct.teacher_school_membership_id
                 WHERE ct.school_class_id = :classId
                   AND ct.school_id = :schoolId
                   AND ct.deleted_at IS NULL
                   AND sm.status = 'ACTIVE'
                   AND sm.left_at IS NULL
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public boolean isTeacherAssigned(UUID schoolId, UUID classId, UUID teacherMembershipId) {
        Integer count = jdbc.queryForObject("""
                SELECT count(*) FROM class_teacher
                 WHERE school_class_id = :classId
                   AND school_id = :schoolId
                   AND teacher_school_membership_id = :membershipId
                   AND deleted_at IS NULL
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("membershipId", teacherMembershipId), Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void softDeleteClass(UUID schoolId, UUID classId, UUID actorId) {
        requireActiveClassOfSchool(schoolId, classId);
        jdbc.update("""
                UPDATE school_class
                   SET status = 'SOFT_DELETED', deleted_at = now(), deleted_by = :actorId,
                       updated_at = now(), updated_by = :actorId
                 WHERE id = :classId AND school_id = :schoolId
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("actorId", actorId));
    }

    @Override
    public void reactivateClass(UUID schoolId, UUID classId, UUID actorId) {
        int updated = jdbc.update("""
                UPDATE school_class
                   SET status = 'ACTIVE', deleted_at = NULL, deleted_by = NULL,
                       updated_at = now(), updated_by = :actorId
                 WHERE id = :classId
                   AND school_id = :schoolId
                   AND status = 'SOFT_DELETED'
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId)
                .addValue("actorId", actorId));
        if (updated != 1) throw new IllegalArgumentException("soft-deleted class not found in school");
    }

    @Override
    public Set<UUID> activeClassIdsForAccount(UUID accountId) {
        String sql = """
                SELECT scm.school_class_id
                  FROM school_class_membership scm
                  JOIN school_membership sm ON sm.id = scm.student_school_membership_id
                  JOIN school_class sc ON sc.id = scm.school_class_id
                 WHERE sm.account_id = :accountId
                   AND scm.status = 'ACTIVE' AND scm.deleted_at IS NULL
                   AND sc.status = 'ACTIVE' AND sc.deleted_at IS NULL
                UNION
                SELECT ct.school_class_id
                  FROM class_teacher ct
                  JOIN school_membership sm ON sm.id = ct.teacher_school_membership_id
                  JOIN school_class sc ON sc.id = ct.school_class_id
                 WHERE sm.account_id = :accountId
                   AND ct.deleted_at IS NULL
                   AND sc.status = 'ACTIVE' AND sc.deleted_at IS NULL
                """;
        return new HashSet<>(jdbc.query(sql, new MapSqlParameterSource("accountId", accountId),
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
                 WHERE id = :classId
                   AND school_id = :schoolId
                   AND status = 'ACTIVE'
                   AND deleted_at IS NULL
                """, new MapSqlParameterSource()
                .addValue("classId", classId)
                .addValue("schoolId", schoolId), Integer.class);
        if (count == null || count == 0) throw new IllegalArgumentException("class not found in school");
    }
}
