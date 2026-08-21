package de.schule.informatik.lernplattform.domain.displayname;

import de.schule.informatik.lernplattform.domain.user.User;

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

    public void changeDisplayName(User user,
                                  String requestedDisplayName,
                                  Set<UUID> visibleClassIds) {
        String normalized = normalizer.normalize(requestedDisplayName);

        if (conflictPort.conflictsInClasses(user.id(), normalized, visibleClassIds)) {
            throw new DisplayNameConflictException(requestedDisplayName);
        }

        user.rename(requestedDisplayName.trim(), normalized);
    }
}
