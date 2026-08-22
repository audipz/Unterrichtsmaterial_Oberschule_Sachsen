package de.schule.informatik.lernplattform.app.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameGenerator;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameNormalizer;
import de.schule.informatik.lernplattform.domain.user.AccountProvisioningPort;
import de.schule.informatik.lernplattform.domain.user.CreateStudentCommand;
import de.schule.informatik.lernplattform.domain.user.CreateTeacherCommand;
import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class AccountProvisioningService {

    private static final int MAX_DISPLAY_NAME_ATTEMPTS = 100;

    private final AccountProvisioningPort provisioningPort;
    private final PasswordHashPort passwordHashPort;
    private final DisplayNameConflictPort displayNameConflictPort;
    private final SchoolAuthorizationPort authorizationPort;
    private final DisplayNameGenerator displayNameGenerator = new DisplayNameGenerator();
    private final DisplayNameNormalizer displayNameNormalizer = new DisplayNameNormalizer();

    public AccountProvisioningService(AccountProvisioningPort provisioningPort,
                                      PasswordHashPort passwordHashPort,
                                      DisplayNameConflictPort displayNameConflictPort,
                                      SchoolAuthorizationPort authorizationPort) {
        this.provisioningPort = provisioningPort;
        this.passwordHashPort = passwordHashPort;
        this.displayNameConflictPort = displayNameConflictPort;
        this.authorizationPort = authorizationPort;
    }

    @Transactional
    public UUID createStudent(CreateStudentCommand command) {
        authorizationPort.requireSchoolAdmin(command.createdBy(), command.schoolId());
        String username = requireText(command.username(), "username");
        String normalizedUsername = username.toLowerCase(Locale.ROOT);
        if (provisioningPort.studentUsernameExists(command.schoolId(), normalizedUsername)) {
            throw new IllegalArgumentException("Der Benutzername ist in dieser Schule bereits vergeben.");
        }
        String password = requireText(command.initialPassword(), "initialPassword");
        Set<UUID> classIds = command.visibleClassIds() == null ? Set.of() : Set.copyOf(command.visibleClassIds());
        String displayName = generateAvailableDisplayName(UUID.randomUUID(), classIds);
        return provisioningPort.createStudent(
                command.schoolId(), username, normalizedUsername, displayName,
                displayNameNormalizer.normalize(displayName), passwordHashPort.hash(password), command.createdBy());
    }

    @Transactional
    public UUID createTeacher(CreateTeacherCommand command) {
        authorizationPort.requireSchoolAdmin(command.createdBy(), command.schoolId());
        String email = requireText(command.email(), "email");
        String normalizedEmail = email.toLowerCase(Locale.ROOT);
        if (provisioningPort.teacherEmailExists(normalizedEmail)) {
            throw new IllegalArgumentException("Für diese E-Mail existiert bereits ein Lehreraccount; dieser muss der Schule zugeordnet werden.");
        }
        String displayName = generateAvailableDisplayName(UUID.randomUUID(), Set.of());
        UUID teacherId = provisioningPort.createTeacher(
                email, normalizedEmail, displayName, displayNameNormalizer.normalize(displayName), command.createdBy());
        provisioningPort.addTeacherToSchool(teacherId, command.schoolId(), command.createdBy());
        return teacherId;
    }

    @Transactional
    public void addExistingTeacherToSchool(UUID schoolId, UUID teacherId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        provisioningPort.addTeacherToSchool(teacherId, schoolId, actorId);
    }

    private String generateAvailableDisplayName(UUID accountId, Set<UUID> classIds) {
        for (int attempt = 0; attempt < MAX_DISPLAY_NAME_ATTEMPTS; attempt++) {
            String candidate = displayNameGenerator.generate();
            if (!displayNameConflictPort.conflictsInClasses(
                    accountId, displayNameNormalizer.normalize(candidate), classIds)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Es konnte kein eindeutiger Fantasiename erzeugt werden.");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " ist erforderlich.");
        return value.trim();
    }
}
