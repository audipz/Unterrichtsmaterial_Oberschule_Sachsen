package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.SchoolAdminRolePort;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import de.schule.informatik.lernplattform.domain.user.UserStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SchoolAdminRoleAdapter implements SchoolAdminRolePort {

    private final NamedParameterJdbcTemplate jdbc;

    public SchoolAdminRoleAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public TargetUser requireUser(UUID userId) {
        String sql = """
                SELECT u.id, u.school_id, u.status,
                       COALESCE(string_agg(ur.role, ','), '') AS roles
                  FROM app_user u
                  LEFT JOIN user_role ur
                    ON ur.user_id = u.id
                   AND ur.school_id = u.school_id
                 WHERE u.id = :userId
                 GROUP BY u.id, u.school_id, u.status
                """;

        return jdbc.queryForObject(sql, new MapSqlParameterSource("userId", userId), (rs, rowNum) -> {
            String rolesValue = rs.getString("roles");
            Set<UserRole> roles = rolesValue == null || rolesValue.isBlank()
                    ? Set.of()
                    : Arrays.stream(rolesValue.split(","))
                        .map(UserRole::valueOf)
                        .collect(Collectors.toUnmodifiableSet());
            return new TargetUser(
                    rs.getObject("id", UUID.class),
                    rs.getObject("school_id", UUID.class),
                    UserStatus.valueOf(rs.getString("status")),
                    roles);
        });
    }

    @Override
    public long countActiveSchoolAdmins(UUID schoolId) {
        Long count = jdbc.queryForObject("""
                SELECT count(DISTINCT u.id)
                  FROM app_user u
                  JOIN user_role ur
                    ON ur.user_id = u.id
                   AND ur.school_id = u.school_id
                 WHERE u.school_id = :schoolId
                   AND u.status = 'ACTIVE'
                   AND u.deleted_at IS NULL
                   AND ur.role = 'SCHOOL_ADMIN'
                """, new MapSqlParameterSource("schoolId", schoolId), Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public void grantSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId) {
        jdbc.update("""
                INSERT INTO user_role (user_id, school_id, role, created_by)
                VALUES (:teacherId, :schoolId, 'SCHOOL_ADMIN', :actorId)
                ON CONFLICT (user_id, school_id, role) DO NOTHING
                """, new MapSqlParameterSource()
                .addValue("teacherId", teacherId)
                .addValue("schoolId", schoolId)
                .addValue("actorId", actorId));
    }

    @Override
    public void revokeSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId) {
        int deleted = jdbc.update("""
                DELETE FROM user_role
                 WHERE user_id = :teacherId
                   AND school_id = :schoolId
                   AND role = 'SCHOOL_ADMIN'
                """, new MapSqlParameterSource()
                .addValue("teacherId", teacherId)
                .addValue("schoolId", schoolId));
        if (deleted != 1) {
            throw new IllegalArgumentException("Schuladmin-Rolle nicht vorhanden.");
        }
    }
}
