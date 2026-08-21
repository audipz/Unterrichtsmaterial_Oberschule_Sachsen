package de.schule.informatik.lernplattform.domain.user;

import java.util.Set;
import java.util.UUID;

public interface SchoolAdminRolePort {

    record TargetUser(UUID userId, UUID schoolId, UserStatus status, Set<UserRole> roles) {}

    TargetUser requireUser(UUID userId);

    long countActiveSchoolAdmins(UUID schoolId);

    void grantSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId);

    void revokeSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId);
}
