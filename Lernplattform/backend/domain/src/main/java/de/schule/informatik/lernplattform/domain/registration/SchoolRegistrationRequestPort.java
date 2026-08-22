package de.schule.informatik.lernplattform.domain.registration;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolRegistrationRequestPort {
    void save(SchoolRegistrationRequest request);
    void delete(UUID requestId);
    boolean verifyEmail(String tokenHash, Instant now);
    List<PendingSchoolRegistration> findPendingReview();
    Optional<PendingSchoolRegistration> findPendingReview(UUID requestId);
    void markApproved(UUID requestId, UUID schoolId, UUID reviewedBy, Instant reviewedAt);
    void markRejected(UUID requestId, String reason, UUID reviewedBy, Instant reviewedAt);

    record PendingSchoolRegistration(
            UUID id,
            String schoolName,
            String schoolType,
            String federalState,
            String city,
            String contactEmail,
            String schoolWebsite,
            Instant submittedAt,
            Instant emailVerifiedAt) {
    }
}
