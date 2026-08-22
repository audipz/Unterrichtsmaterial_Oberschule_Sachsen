package de.schule.informatik.lernplattform.domain.user;

import java.time.LocalDate;
import java.util.UUID;

public interface SchoolMembershipLifecyclePort {

    record MembershipContext(UUID membershipId,
                             UUID accountId,
                             UUID schoolId,
                             AccountType accountType,
                             boolean active) {}

    MembershipContext requireMembership(UUID accountId, UUID schoolId);

    long countActiveSchoolMemberships(UUID accountId);

    void endMembership(UUID membershipId, LocalDate effectiveDate, UUID actorId);

    void reactivateMembership(UUID membershipId, UUID actorId);

    void markAccountPendingDeletion(UUID accountId, UUID actorId);

    void reactivateAccount(UUID accountId, UUID actorId);
}
