package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.AccountType;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminMembershipRolePort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SchoolAdminMembershipRoleAdapter implements SchoolAdminMembershipRolePort {

    private final NamedParameterJdbcTemplate jdbc;

    public SchoolAdminMembershipRoleAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public TeacherMembership requireTeacherMembership(UUID teacherAccountId, UUID schoolId) {
        String sql = """
                SELECT sm.id AS membership_id,
                       sm.account_id,
                       sm.school_id,
                       a.account_type,
                       (sm.status = 'ACTIVE' AND sm.deleted_at IS NULL AND a.status = 'ACTIVE' AND a.deleted_at IS NULL) AS active,
                       EXISTS (
                           SELECT 1
                             FROM school_role sr
                            WHERE sr.school_membership_id = sm.id
                              AND sr.role = 'SCHOOL_ADMIN'
                       ) AS school_admin
                  FROM school_membership sm
                  JOIN account a ON a.id = sm.account_id
                 WHERE sm.account_id = :accountId
                   AND sm.school_id = :schoolId
                   AND sm.deleted_at IS NULL
                 ORDER BY CASE WHEN sm.status = 'ACTIVE' THEN 0 ELSE 1 END, sm.joined_at DESC
                 LIMIT 1
                """;
        var result = jdbc.query(sql, new MapSqlParameterSource()
                .addValue("accountId", teacherAccountId)
                .addValue("schoolId", schoolId), (rs, rowNum) -> new TeacherMembership(
                rs.getObject("membership_id", UUID.class),
                rs.getObject("account_id", UUID.class),
                rs.getObject("school_id", UUID.class),
                AccountType.valueOf(rs.getString("account_type")),
                rs.getBoolean("active"),
                rs.getBoolean("school_admin")));
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Keine Schulzuordnung für diesen Lehrer gefunden.");
        }
        return result.getFirst();
    }

    @Override
    public long countActiveSchoolAdmins(UUID schoolId) {
        Long count = jdbc.queryForObject("""
                SELECT count(DISTINCT sm.account_id)
                  FROM school_membership sm
                  JOIN account a ON a.id = sm.account_id
                  JOIN school_role sr ON sr.school_membership_id = sm.id
                 WHERE sm.school_id = :schoolId
                   AND sm.status = 'ACTIVE'
                   AND sm.deleted_at IS NULL
                   AND a.account_type = 'TEACHER'
                   AND a.status = 'ACTIVE'
                   AND a.deleted_at IS NULL
                   AND sr.role = 'SCHOOL_ADMIN'
                """, new MapSqlParameterSource("schoolId", schoolId), Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public void grantSchoolAdmin(UUID membershipId, UUID actorId) {
        jdbc.update("""
                INSERT INTO school_role (school_membership_id, role, created_by)
                VALUES (:membershipId, 'SCHOOL_ADMIN', :actorId)
                ON CONFLICT (school_membership_id, role) DO NOTHING
                """, new MapSqlParameterSource()
                .addValue("membershipId", membershipId)
                .addValue("actorId", actorId));
    }

    @Override
    public void revokeSchoolAdmin(UUID membershipId, UUID actorId) {
        jdbc.update("""
                DELETE FROM school_role
                 WHERE school_membership_id = :membershipId
                   AND role = 'SCHOOL_ADMIN'
                """, new MapSqlParameterSource("membershipId", membershipId));
    }
}
