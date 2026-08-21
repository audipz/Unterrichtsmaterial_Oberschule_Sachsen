package de.schule.informatik.lernplattform.persistence.displayname;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public class DisplayNameConflictAdapter implements DisplayNameConflictPort {

    private static final String SQL = """
        SELECT EXISTS (
            SELECT 1
            FROM app_user u
            WHERE u.deleted_at IS NULL
              AND u.id <> :userId
              AND u.display_name_normalized = :displayName
              AND (
                    EXISTS (
                        SELECT 1
                        FROM school_class_membership scm
                        WHERE scm.student_id = u.id
                          AND scm.school_class_id IN (:classIds)
                          AND scm.status = 'ACTIVE'
                          AND scm.deleted_at IS NULL
                    )
                    OR EXISTS (
                        SELECT 1
                        FROM class_teacher ct
                        WHERE ct.teacher_id = u.id
                          AND ct.school_class_id IN (:classIds)
                          AND ct.deleted_at IS NULL
                    )
              )
        )
        """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DisplayNameConflictAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean conflictsInClasses(UUID userId, String normalizedDisplayName, Set<UUID> classIds) {
        if (classIds == null || classIds.isEmpty()) {
            return false;
        }

        var parameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("displayName", normalizedDisplayName)
                .addValue("classIds", classIds);

        Boolean result = jdbcTemplate.queryForObject(SQL, parameters, Boolean.class);
        return Boolean.TRUE.equals(result);
    }
}
