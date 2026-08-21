package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;

import java.time.LocalDate;
import java.util.UUID;

public final class UserLifecycleService {

    private final UserLifecyclePort port;
    private final SchoolAuthorizationPort authorizationPort;

    public UserLifecycleService(UserLifecyclePort port,
                                SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.authorizationPort = authorizationPort;
    }

    public void studentLeavesSchool(UUID schoolId, UUID studentId, LocalDate effectiveDate, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var user = port.requireUser(studentId);
        if (!schoolId.equals(user.schoolId())) throw new IllegalArgumentException("user belongs to another school");
        if (!user.roles().contains(UserRole.STUDENT)) throw new IllegalArgumentException("user is not a student");
        if (user.status() != UserStatus.ACTIVE) throw new IllegalArgumentException("student is not active");
        port.leaveSchool(schoolId, studentId, effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
    }

    public void reactivate(UUID schoolId, UUID userId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var user = port.requireUser(userId);
        if (!schoolId.equals(user.schoolId())) throw new IllegalArgumentException("user belongs to another school");
        if (user.status() != UserStatus.SOFT_DELETED) throw new IllegalArgumentException("user is not soft-deleted");
        port.reactivateUser(schoolId, userId, actorId);
    }
}
