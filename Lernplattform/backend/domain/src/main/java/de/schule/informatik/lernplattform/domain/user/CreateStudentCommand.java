package de.schule.informatik.lernplattform.domain.user;

import java.util.Set;
import java.util.UUID;

public record CreateStudentCommand(
        UUID schoolId,
        String username,
        String initialPassword,
        Set<UUID> visibleClassIds,
        UUID createdBy
) {}
