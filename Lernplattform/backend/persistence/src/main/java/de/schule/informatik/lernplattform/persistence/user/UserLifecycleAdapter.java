package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.UserLifecyclePort;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import de.schule.informatik.lernplattform.domain.user.UserStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserLifecycleAdapter implements UserLifecyclePort {

    private final NamedParameterJdbcTemplate jdbc;

    public UserLifecycleAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public UserContext requireUser(UUID userId) {
        return jdbc.queryForObject("""
                SELECT u.id, u.school_id, u.status,
                       COALESCE(string_agg(ur.role, ','), '') AS roles
                  FROM app_user u
                  LEFT JOIN user_role ur ON ur.user_id = u.id AND ur.school_id = u.school_id
                 WHERE u.id = :userId
                 GROUP BY u.id, u.school_id, u.status
                """, new MapSqlParameterSource("userId", userId), (rs, rowNum) -> {
            String rolesText = rs.getString("roles");
            Set<UserRole> roles = rolesText == null || rolesText.isBlank()
                    ? Set.of()
                    : Arrays.stream(rolesText.split(","))
                    .map(UserRole::valueOf)
                    .collect(Collectors.toUnmodifiableSet());
            return new UserContext(
                    rs.getObject("id", UUID.class),
                    rs.getObject("school_id", UUID.class),
                    UserStatus.valueOf(rs.getString("status")),
                    roles);
        });
    }

    @Override
    public void leaveSchool(UUID schoolId, UUID studentId, LocalDate effectiveDate, UUID actorId) {
        var params = new MapSqlParameterSource()
                .addValue("schoolId", schoolId)
                .addValue("studentId", studentId)
                .addValue("effectiveDate", effectiveDate)
                .addValue("actorId", actorId);

        Instant deletedAt = jdbc.queryForObject("""
                UPDATE app_user
                   SET status = 'SOFT_DELETED', deleted_at = now(), deleted_by = :actorId,
                       updated_at = now(), updated_by = :actorId
                 WHERE id = :studentId AND school_id = :schoolId
                   AND status = 'ACTIVE' AND deleted_at IS NULL
                RETURNING deleted_at
                """, params, (rs, rowNum) -> rs.getTimestamp(1).toInstant());

        if (deletedAt == null) throw new IllegalArgumentException("active student not found in school");
        params.addValue("deletedAt", deletedAt);

        jdbc.update("""
                UPDATE school_class_membership scm
                   SET status = 'SOFT_DELETED', deleted_at = :deletedAt, deleted_by = :actorId,
                       updated_at = now(), updated_by = :actorId
                  FROM school_class sc
                 WHERE scm.school_class_id = sc.id
                   AND sc.school_id = :schoolId
                   AND scm.student_id = :studentId
                   AND scm.status = 'ACTIVE'
                   AND scm.deleted_at IS NULL
                """, params);
    }

    @Override
    public void reactivateUser(UUID schoolId, UUID userId, UUID actorId) {
        var params = new MapSqlParameterSource()
                .addValue("schoolId", schoolId)
                .addValue("userId", userId)
                .addValue("actorId", actorId);

        Instant deletionMarker = jdbc.queryForObject("""
                SELECT deleted_at FROM app_user
                 WHERE id = :userId AND school_id = :schoolId
                   AND status = 'SOFT_DELETED' AND deleted_at IS NOT NULL
                 FOR UPDATE
                """, params, (rs, rowNum) -> rs.getTimestamp(1).toInstant());
        if (deletionMarker == null) throw new IllegalArgumentException("soft-deleted user not found in school");
        params.addValue("deletionMarker", deletionMarker);

        jdbc.update("""
                UPDATE app_user
                   SET status = 'ACTIVE', deleted_at = NULL, deleted_by = NULL,
                       updated_at = now(), updated_by = :actorId
                 WHERE id = :userId AND school_id = :schoolId
                """, params);

        jdbc.update("""
                UPDATE school_class_membership scm
                   SET status = 'ACTIVE', deleted_at = NULL, deleted_by = NULL,
                       updated_at = now(), updated_by = :actorId
                  FROM school_class sc
                 WHERE scm.school_class_id = sc.id
                   AND sc.school_id = :schoolId
                   AND scm.student_id = :userId
                   AND scm.status = 'SOFT_DELETED'
                   AND scm.deleted_at = :deletionMarker
                   AND sc.status = 'ACTIVE' AND sc.deleted_at IS NULL
                """, params);
    }
}
