package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;

import java.time.LocalDate;
import java.util.UUID;

public final class SchoolMembershipLifecycleService {

    private final SchoolMembershipLifecyclePort port;
    private final SchoolAuthorizationPort authorizationPort;

    public SchoolMembershipLifecycleService(SchoolMembershipLifecyclePort port,
                                            SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.authorizationPort = authorizationPort;
    }

    public void removeStudentFromSchool(UUID schoolId, UUID studentId, LocalDate effectiveDate, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var membership = port.requireMembership(studentId, schoolId);
        if (membership.accountType() != AccountType.STUDENT) {
            throw new IllegalArgumentException("account is not a student");
        }
        if (!membership.active()) {
            throw new IllegalArgumentException("school membership is not active");
        }
        port.endMembership(membership.membershipId(), effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
        if (port.countActiveSchoolMemberships(studentId) == 0) {
            port.markAccountPendingDeletion(studentId, actorId);
        }
    }

    public void restoreStudentToSchool(UUID schoolId, UUID studentId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var membership = port.requireMembership(studentId, schoolId);
        if (membership.accountType() != AccountType.STUDENT) {
            throw new IllegalArgumentException("account is not a student");
        }
        if (membership.active()) {
            return;
        }
        port.reactivateAccount(studentId, actorId);
        port.reactivateMembership(membership.membershipId(), actorId);
    }
}
