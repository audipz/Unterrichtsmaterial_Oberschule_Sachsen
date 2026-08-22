package de.schule.informatik.lernplattform.app.registration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SchoolRegistrationCleanup {

    private final JdbcTemplate jdbc;

    public SchoolRegistrationCleanup(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Scheduled(fixedDelayString = "${lernplattform.registration.cleanup-delay:PT15M}")
    @Transactional
    public void deleteExpiredUnverifiedRequests() {
        jdbc.update("""
                delete from school_registration_request
                where status = 'EMAIL_VERIFICATION_PENDING'
                  and delete_after <= now()
                """);
    }
}
