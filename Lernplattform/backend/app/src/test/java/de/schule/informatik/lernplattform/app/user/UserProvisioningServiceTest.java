package de.schule.informatik.lernplattform.app.user;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.user.CreateUserCommand;
import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import de.schule.informatik.lernplattform.domain.user.UserAccountPort;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class UserProvisioningServiceTest {

    @Test
    void createsUserWithHashedPasswordRolesAndGeneratedDisplayName() {
        UUID schoolId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();
        UUID createdBy = UUID.randomUUID();
        var captured = new AtomicReference<CapturedUser>();

        UserAccountPort accountPort = new UserAccountPort() {
            @Override
            public boolean usernameExists(UUID school, String username) {
                return false;
            }

            @Override
            public void createUser(UUID userId,
                                   UUID school,
                                   String username,
                                   String displayName,
                                   String displayNameNormalized,
                                   String passwordHash,
                                   Set<UserRole> roles,
                                   boolean mustChangePassword,
                                   UUID actor) {
                captured.set(new CapturedUser(
                        userId, school, username, displayName, displayNameNormalized,
                        passwordHash, roles, mustChangePassword, actor
                ));
            }
        };

        PasswordHashPort passwordHashPort = raw -> "HASH:" + raw;
        DisplayNameConflictPort conflictPort = (userId, normalized, classes) -> false;

        var service = new UserProvisioningService(accountPort, passwordHashPort, conflictPort);

        var result = service.create(new CreateUserCommand(
                schoolId,
                "s7a-0184",
                "Start!123",
                Set.of(UserRole.STUDENT),
                Set.of(classId),
                createdBy
        ));

        CapturedUser user = captured.get();
        assertThat(user).isNotNull();
        assertThat(user.schoolId()).isEqualTo(schoolId);
        assertThat(user.username()).isEqualTo("s7a-0184");
        assertThat(user.displayName()).isNotBlank();
        assertThat(user.displayNameNormalized()).isEqualTo(user.displayName().toLowerCase());
        assertThat(user.passwordHash()).isEqualTo("HASH:Start!123");
        assertThat(user.roles()).containsExactly(UserRole.STUDENT);
        assertThat(user.mustChangePassword()).isTrue();
        assertThat(user.createdBy()).isEqualTo(createdBy);
        assertThat(result.displayName()).isEqualTo(user.displayName());
    }

    private record CapturedUser(
            UUID userId,
            UUID schoolId,
            String username,
            String displayName,
            String displayNameNormalized,
            String passwordHash,
            Set<UserRole> roles,
            boolean mustChangePassword,
            UUID createdBy
    ) {
    }
}
