package de.schule.informatik.lernplattform.persistence.school;

import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SchoolLookupAdapter implements SchoolLookupPort {

    private final JdbcTemplate jdbcTemplate;

    public SchoolLookupAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID requireActiveSchoolId(String schoolSlug) {
        var ids = jdbcTemplate.query(
                "select id from school where lower(slug) = lower(?) and status = 'ACTIVE' and deleted_at is null",
                (rs, rowNum) -> rs.getObject("id", UUID.class),
                schoolSlug
        );
        if (ids.size() != 1) {
            throw new IllegalArgumentException("Schule wurde nicht gefunden oder ist nicht aktiv.");
        }
        return ids.getFirst();
    }
}
