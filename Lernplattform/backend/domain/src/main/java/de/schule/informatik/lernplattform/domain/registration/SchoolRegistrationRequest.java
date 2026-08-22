package de.schule.informatik.lernplattform.domain.registration;

import java.time.Instant;
import java.util.UUID;

public record SchoolRegistrationRequest(
        UUID id,
        String schoolName,
        String schoolType,
        String federalState,
        String city,
        String contactEmail,
        String contactEmailNormalized,
        String schoolWebsite,
        String requestedSlug,
        String submissionNonce,
        String verificationTokenHash,
        Instant verificationExpiresAt,
        String createdIpHash,
        String userAgentHash,
        Instant submittedAt) {
}
