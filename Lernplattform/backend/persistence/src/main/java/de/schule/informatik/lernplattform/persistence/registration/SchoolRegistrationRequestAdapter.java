package de.schule.informatik.lernplattform.persistence.registration;

import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequest;
import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequestPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class SchoolRegistrationRequestAdapter implements SchoolRegistrationRequestPort {

    private static final Duration VERIFICATION_WINDOW = Duration.ofHours(24);

    private final JdbcTemplate jdbc;

    public SchoolRegistrationRequestAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(SchoolRegistrationRequest request) {
        Instant deadline = request.submittedAt().plus(VERIFICATION_WINDOW);
        jdbc.update("""
                insert into school_registration_request (
                    id, school_name, school_type, federal_state, city,
                    contact_email, contact_email_normalized, school_website,
                    requested_slug, status, submission_nonce, created_ip_hash,
                    user_agent_hash, submitted_at, verification_expires_at, delete_after
                ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'EMAIL_VERIFICATION_PENDING', ?, ?, ?, ?, ?, ?)
                """,
                request.id(),
                request.schoolName(),
                request.schoolType(),
                request.federalState(),
                request.city(),
                request.contactEmail(),
                request.contactEmailNormalized(),
                request.schoolWebsite(),
                request.requestedSlug(),
                request.submissionNonce(),
                request.createdIpHash(),
                request.userAgentHash(),
                request.submittedAt(),
                deadline,
                deadline);
    }
}
