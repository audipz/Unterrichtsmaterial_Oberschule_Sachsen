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
                  FROM account a
                  JOIN school_membership sm
                    ON sm.account_id = a.id
                   AND sm.school_id = :schoolId
                   AND sm.status = 'ACTIVE'
                   AND sm.deleted_at IS NULL
                  JOIN school_role sr
                    ON sr.school_membership_id = sm.id
                   AND sr.role = 'SCHOOL_ADMIN'
                 WHERE a.id = :actorId
                   AND a.status = 'ACTIVE'
                   AND a.deleted_at IS NULL
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
                  FROM account a
                  JOIN system_role sr
                    ON sr.account_id = a.id
                   AND sr.role = 'SYSTEM_ADMIN'
                 WHERE a.id = :actorId
                   AND a.status = 'ACTIVE'
                   AND a.deleted_at IS NULL
                """, new MapSqlParameterSource("actorId", actorId), Integer.class);
        if (count == null || count != 1) {
            throw new SecurityException("SYSTEM_ADMIN role is required");
        }
    }
}
