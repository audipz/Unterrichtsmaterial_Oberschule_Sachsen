package de.schule.informatik.lernplattform.domain.user;

import java.time.LocalDate;
import java.util.UUID;

public interface UserLifecyclePort {

    record UserContext(UUID userId, UUID schoolId, UserStatus status, java.util.Set<UserRole> roles) {}

    UserContext requireUser(UUID userId);

    void leaveSchool(UUID schoolId, UUID studentId, LocalDate effectiveDate, UUID actorId);

    void reactivateUser(UUID schoolId, UUID userId, UUID actorId);
}
