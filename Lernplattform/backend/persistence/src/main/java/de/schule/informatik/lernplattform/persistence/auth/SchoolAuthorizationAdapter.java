package de.schule.informatik.lernplattform.persistence.auth;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SchoolAuthorizationAdapter implements SchoolAuthorizationPort {

    private final NamedParameterJdbcTemplate jdbc;

    public SchoolAuthorizationAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void requireSchoolAdmin(UUID actorId, UUID schoolId) {
        Integer count = jdbc.queryForObject("""
                SELECT count(*)
                  FROM app_user u
                  JOIN user_role ur
                    ON ur.user_id = u.id
                   AND ur.school_id = u.school_id
                 WHERE u.id = :actorId
                   AND u.school_id = :schoolId
                   AND u.status = 'ACTIVE'
                   AND u.deleted_at IS NULL
                   AND ur.role = 'SCHOOL_ADMIN'
                """, new MapSqlParameterSource()
                .addValue("actorId", actorId)
                .addValue("schoolId", schoolId), Integer.class);
        if (count == null || count != 1) {
            throw new SecurityException("SCHOOL_ADMIN role for this school is required");
        }
    }

    @Override
    public void requireSystemAdmin(UUID actorId) {
        Integer count = jdbc.queryForObject("""
                SELECT count(*)
                  FROM app_user u
                  JOIN user_role ur ON ur.user_id = u.id
                 WHERE u.id = :actorId
                   AND u.status = 'ACTIVE'
                   AND u.deleted_at IS NULL
                   AND ur.role = 'SYSTEM_ADMIN'
                """, new MapSqlParameterSource("actorId", actorId), Integer.class);
        if (count == null || count == 0) {
            throw new SecurityException("SYSTEM_ADMIN role is required");
        }
    }
}
