package de.schule.informatik.lernplattform.domain.displayname;

import de.schule.informatik.lernplattform.domain.user.Account;

import java.util.Set;
import java.util.UUID;

public final class DisplayNameService {

    private final DisplayNameNormalizer normalizer;
    private final DisplayNameConflictPort conflictPort;

    public DisplayNameService(DisplayNameNormalizer normalizer,
                              DisplayNameConflictPort conflictPort) {
        this.normalizer = normalizer;
        this.conflictPort = conflictPort;
    }

    public void changeDisplayName(Account account,
                                  String requestedDisplayName,
                                  Set<UUID> visibleClassIds) {
        String normalized = normalizer.normalize(requestedDisplayName);

        if (conflictPort.conflictsInClasses(account.id(), normalized, visibleClassIds)) {
            throw new DisplayNameConflictException(requestedDisplayName);
        }

        account.rename(requestedDisplayName.trim(), normalized);
    }
}
