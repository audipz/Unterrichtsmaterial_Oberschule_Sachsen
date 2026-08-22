package de.schule.informatik.lernplattform.persistence.registration;

import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequest;
import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequestPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SchoolRegistrationRequestAdapter implements SchoolRegistrationRequestPort {

    private final JdbcTemplate jdbc;

    public SchoolRegistrationRequestAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(SchoolRegistrationRequest request) {
        jdbc.update("""
                insert into school_registration_request (
                    id, school_name, school_type, federal_state, city,
                    contact_email, contact_email_normalized, school_website,
                    requested_slug, status, submission_nonce, verification_token_hash,
                    verification_expires_at, delete_after, created_ip_hash,
                    user_agent_hash, submitted_at
                ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'EMAIL_VERIFICATION_PENDING', ?, ?, ?, ?, ?, ?, ?)
                """,
                request.id(), request.schoolName(), request.schoolType(), request.federalState(), request.city(),
                request.contactEmail(), request.contactEmailNormalized(), request.schoolWebsite(), request.requestedSlug(),
                request.submissionNonce(), request.verificationTokenHash(), request.verificationExpiresAt(),
                request.verificationExpiresAt(), request.createdIpHash(), request.userAgentHash(), request.submittedAt());
    }

    @Override
    public void delete(UUID requestId) {
        jdbc.update("delete from school_registration_request where id = ?", requestId);
    }

    @Override
    public boolean verifyEmail(String tokenHash, Instant now) {
        return jdbc.update("""
                update school_registration_request
                set status = 'PENDING_REVIEW', email_verified_at = ?, verification_token_hash = null,
                    verification_expires_at = null, delete_after = null
                where status = 'EMAIL_VERIFICATION_PENDING'
                  and verification_token_hash = ?
                  and verification_expires_at > ?
                """, now, tokenHash, now) == 1;
    }

    @Override
    public List<PendingSchoolRegistration> findPendingReview() {
        return jdbc.query("""
                select id, school_name, school_type, federal_state, city, contact_email,
                       school_website, submitted_at, email_verified_at
                from school_registration_request
                where status = 'PENDING_REVIEW'
                order by email_verified_at asc
                """, (rs, rowNum) -> mapPending(rs));
    }

    @Override
    public Optional<PendingSchoolRegistration> findPendingReview(UUID requestId) {
        List<PendingSchoolRegistration> rows = jdbc.query("""
                select id, school_name, school_type, federal_state, city, contact_email,
                       school_website, submitted_at, email_verified_at
                from school_registration_request
                where id = ? and status = 'PENDING_REVIEW'
                """, (rs, rowNum) -> mapPending(rs), requestId);
        return rows.stream().findFirst();
    }

    @Override
    public void markApproved(UUID requestId, UUID schoolId, UUID reviewedBy, Instant reviewedAt) {
        if (jdbc.update("""
                update school_registration_request
                set status = 'APPROVED', approved_school_id = ?, reviewed_by = ?, reviewed_at = ?
                where id = ? and status = 'PENDING_REVIEW'
                """, schoolId, reviewedBy, reviewedAt, requestId) != 1) {
            throw new IllegalStateException("registration request is no longer pending review");
        }
    }

    @Override
    public void markRejected(UUID requestId, String reason, UUID reviewedBy, Instant reviewedAt) {
        if (jdbc.update("""
                update school_registration_request
                set status = 'REJECTED', rejection_reason = ?, reviewed_by = ?, reviewed_at = ?
                where id = ? and status = 'PENDING_REVIEW'
                """, reason, reviewedBy, reviewedAt, requestId) != 1) {
            throw new IllegalStateException("registration request is no longer pending review");
        }
    }

    private static PendingSchoolRegistration mapPending(ResultSet rs) throws SQLException {
        return new PendingSchoolRegistration(
                rs.getObject("id", UUID.class),
                rs.getString("school_name"), rs.getString("school_type"), rs.getString("federal_state"),
                rs.getString("city"), rs.getString("contact_email"), rs.getString("school_website"),
                rs.getTimestamp("submitted_at").toInstant(), rs.getTimestamp("email_verified_at").toInstant());
    }
}
