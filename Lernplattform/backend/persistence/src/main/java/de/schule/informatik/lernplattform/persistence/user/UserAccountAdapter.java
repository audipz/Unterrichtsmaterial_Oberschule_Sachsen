package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.UserAccountPort;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public class UserAccountAdapter implements UserAccountPort {

    private static final String USERNAME_EXISTS_SQL = """
        SELECT EXISTS (
            SELECT 1
            FROM app_user
            WHERE school_id = :schoolId
              AND lower(username) = lower(:username)
              AND deleted_at IS NULL
        )
        """;

    private static final String INSERT_USER_SQL = """
        INSERT INTO app_user (
            id, school_id, username, display_name, display_name_normalized,
            password_hash, status, must_change_password,
            created_by, updated_by
        ) VALUES (
            :id, :schoolId, :username, :displayName, :displayNameNormalized,
            :passwordHash, 'ACTIVE', :mustChangePassword,
            :createdBy, :createdBy
        )
        """;

    private static final String INSERT_ROLE_SQL = """
        INSERT INTO user_role (user_id, school_id, role, created_by)
        VALUES (:userId, :schoolId, :role, :createdBy)
        """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserAccountAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean usernameExists(UUID schoolId, String username) {
        var params = new MapSqlParameterSource()
                .addValue("schoolId", schoolId)
                .addValue("username", username);
        Boolean result = jdbcTemplate.queryForObject(USERNAME_EXISTS_SQL, params, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void createUser(UUID userId,
                           UUID schoolId,
                           String username,
                           String displayName,
                           String displayNameNormalized,
                           String passwordHash,
                           Set<UserRole> roles,
                           boolean mustChangePassword,
                           UUID createdBy) {
        var userParams = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("schoolId", schoolId)
                .addValue("username", username)
                .addValue("displayName", displayName)
                .addValue("displayNameNormalized", displayNameNormalized)
                .addValue("passwordHash", passwordHash)
                .addValue("mustChangePassword", mustChangePassword)
                .addValue("createdBy", createdBy);

        jdbcTemplate.update(INSERT_USER_SQL, userParams);

        for (UserRole role : roles) {
            var roleParams = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("schoolId", schoolId)
                    .addValue("role", role.name())
                    .addValue("createdBy", createdBy);
            jdbcTemplate.update(INSERT_ROLE_SQL, roleParams);
        }
    }
}
