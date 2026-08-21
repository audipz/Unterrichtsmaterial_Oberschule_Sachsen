package de.schule.informatik.lernplattform.domain.user;

import java.util.UUID;

public record CreateUserResult(UUID userId, String username, String displayName) {
}
