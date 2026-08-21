package de.schule.informatik.lernplattform.domain.displayname;

import de.schule.informatik.lernplattform.domain.user.User;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import de.schule.informatik.lernplattform.domain.user.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DisplayNameServiceTest {

    @Test
    void changesNameWhenNoConflictExists() {
        User user = user("PixelFuchs");
        DisplayNameConflictPort port = (userId, name, classes) -> false;
        DisplayNameService service = new DisplayNameService(new DisplayNameNormalizer(), port);

        service.changeDisplayName(user, "CodeOtter", Set.of(UUID.randomUUID()));

        assertEquals("CodeOtter", user.displayName());
        assertEquals("codeotter", user.displayNameNormalized());
    }

    @Test
    void rejectsNameWhenConflictExists() {
        User user = user("PixelFuchs");
        DisplayNameConflictPort port = (userId, name, classes) -> true;
        DisplayNameService service = new DisplayNameService(new DisplayNameNormalizer(), port);

        assertThrows(DisplayNameConflictException.class,
                () -> service.changeDisplayName(user, "CodeOtter", Set.of(UUID.randomUUID())));
    }

    private static User user(String displayName) {
        return new User(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "u-001",
                displayName,
                displayName.toLowerCase(),
                UserStatus.ACTIVE,
                Set.of(UserRole.STUDENT)
        );
    }
}
