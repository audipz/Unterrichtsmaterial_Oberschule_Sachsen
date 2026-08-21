package de.schule.informatik.lernplattform.app.user;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameGenerator;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameNormalizer;
import de.schule.informatik.lernplattform.domain.user.CreateUserCommand;
import de.schule.informatik.lernplattform.domain.user.CreateUserResult;
import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import de.schule.informatik.lernplattform.domain.user.UserAccountPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class UserProvisioningService {

    private static final int MAX_DISPLAY_NAME_ATTEMPTS = 100;

    private final UserAccountPort userAccountPort;
    private final PasswordHashPort passwordHashPort;
    private final DisplayNameConflictPort displayNameConflictPort;
    private final DisplayNameGenerator displayNameGenerator = new DisplayNameGenerator();
    private final DisplayNameNormalizer displayNameNormalizer = new DisplayNameNormalizer();

    public UserProvisioningService(UserAccountPort userAccountPort,
                                   PasswordHashPort passwordHashPort,
                                   DisplayNameConflictPort displayNameConflictPort) {
        this.userAccountPort = userAccountPort;
        this.passwordHashPort = passwordHashPort;
        this.displayNameConflictPort = displayNameConflictPort;
    }

    @Transactional
    public CreateUserResult create(CreateUserCommand command) {
        validate(command);

        String username = command.username().trim();
        if (userAccountPort.usernameExists(command.schoolId(), username)) {
            throw new IllegalArgumentException("Der Benutzername ist in dieser Schule bereits vergeben.");
        }

        UUID userId = UUID.randomUUID();
        String displayName = generateAvailableDisplayName(userId, command.visibleClassIds());
        String normalizedDisplayName = displayNameNormalizer.normalize(displayName);
        String passwordHash = passwordHashPort.hash(command.initialPassword());

        userAccountPort.createUser(
                userId,
                command.schoolId(),
                username,
                displayName,
                normalizedDisplayName,
                passwordHash,
                Set.copyOf(command.roles()),
                true,
                command.createdBy()
        );

        return new CreateUserResult(userId, username, displayName);
    }

    private String generateAvailableDisplayName(UUID userId, Set<UUID> classIds) {
        Set<UUID> visibleClasses = classIds == null ? Set.of() : Set.copyOf(classIds);

        for (int attempt = 0; attempt < MAX_DISPLAY_NAME_ATTEMPTS; attempt++) {
            String candidate = displayNameGenerator.generate();
            String normalized = displayNameNormalizer.normalize(candidate);
            if (!displayNameConflictPort.conflictsInClasses(userId, normalized, visibleClasses)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Es konnte kein eindeutiger Fantasiename erzeugt werden.");
    }

    private static void validate(CreateUserCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Benutzeranlage darf nicht leer sein.");
        }
        if (command.schoolId() == null) {
            throw new IllegalArgumentException("Schule ist erforderlich.");
        }
        if (command.username() == null || command.username().isBlank()) {
            throw new IllegalArgumentException("Benutzername ist erforderlich.");
        }
        if (command.initialPassword() == null || command.initialPassword().isBlank()) {
            throw new IllegalArgumentException("Startpasswort ist erforderlich.");
        }
        if (command.roles() == null || command.roles().isEmpty()) {
            throw new IllegalArgumentException("Mindestens eine Rolle ist erforderlich.");
        }
    }
}
