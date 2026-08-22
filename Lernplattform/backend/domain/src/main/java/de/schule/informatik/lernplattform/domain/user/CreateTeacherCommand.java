package de.schule.informatik.lernplattform.domain.user;

import java.util.UUID;

public record CreateTeacherCommand(
        String email,
        UUID schoolId,
        UUID createdBy
) {}
