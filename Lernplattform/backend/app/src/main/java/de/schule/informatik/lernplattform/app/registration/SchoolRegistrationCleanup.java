package de.schule.informatik.lernplattform.app.registration;

import de.schule.informatik.lernplattform.app.scheduling.KubernetesLeaseLeaderElection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SchoolRegistrationCleanup {

    private final JdbcTemplate jdbc;
    private final KubernetesLeaseLeaderElection leaderElection;

    public SchoolRegistrationCleanup(JdbcTemplate jdbc,
                                     KubernetesLeaseLeaderElection leaderElection) {
        this.jdbc = jdbc;
        this.leaderElection = leaderElection;
    }

    @Scheduled(fixedDelayString = "${lernplattform.registration.cleanup-delay:PT1H}")
    @Transactional
    public void deleteExpiredUnverifiedRequests() {
        if (!leaderElection.isLeader()) {
            return;
        }

        jdbc.update("""
                delete from school_registration_request
                where status = 'EMAIL_VERIFICATION_PENDING'
                  and delete_after <= now()
                """);
    }
}
