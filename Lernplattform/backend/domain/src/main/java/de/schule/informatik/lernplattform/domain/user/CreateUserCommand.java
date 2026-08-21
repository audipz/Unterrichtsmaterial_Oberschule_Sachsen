package de.schule.informatik.lernplattform.domain.user;

import java.util.Set;
import java.util.UUID;

public record CreateUserCommand(
        UUID schoolId,
        String username,
        String initialPassword,
        Set<UserRole> roles,
        Set<UUID> visibleClassIds,
        UUID createdBy
) {
}
