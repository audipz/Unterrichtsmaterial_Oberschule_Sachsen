package de.schule.informatik.lernplattform.domain.displayname;

import de.schule.informatik.lernplattform.domain.user.Account;
import de.schule.informatik.lernplattform.domain.user.AccountStatus;
import de.schule.informatik.lernplattform.domain.user.AccountType;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DisplayNameServiceTest {

    @Test
    void changesNameWhenNoConflictExists() {
        Account account = studentAccount("PixelFuchs");
        DisplayNameConflictPort port = (accountId, name, classes) -> false;
        DisplayNameService service = new DisplayNameService(new DisplayNameNormalizer(), port);

        service.changeDisplayName(account, "CodeOtter", Set.of(UUID.randomUUID()));

        assertEquals("CodeOtter", account.displayName());
        assertEquals("codeotter", account.displayNameNormalized());
    }

    @Test
    void rejectsNameWhenConflictExists() {
        Account account = studentAccount("PixelFuchs");
        DisplayNameConflictPort port = (accountId, name, classes) -> true;
        DisplayNameService service = new DisplayNameService(new DisplayNameNormalizer(), port);

        assertThrows(DisplayNameConflictException.class,
                () -> service.changeDisplayName(account, "CodeOtter", Set.of(UUID.randomUUID())));
    }

    private static Account studentAccount(String displayName) {
        return new Account(
                UUID.randomUUID(),
                AccountType.STUDENT,
                displayName,
                displayName.toLowerCase(),
                null,
                null,
                AccountStatus.ACTIVE,
                null
        );
    }
}
