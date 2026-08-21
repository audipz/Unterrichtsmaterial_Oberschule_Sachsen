package de.schule.informatik.lernplattform.domain.user;

import java.util.Set;
import java.util.UUID;

public interface UserAccountPort {

    boolean usernameExists(UUID schoolId, String username);

    void createUser(UUID userId,
                    UUID schoolId,
                    String username,
                    String displayName,
                    String displayNameNormalized,
                    String passwordHash,
                    Set<UserRole> roles,
                    boolean mustChangePassword,
                    UUID createdBy);
}
