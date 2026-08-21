package de.schule.informatik.lernplattform.domain.displayname;

import java.util.Set;
import java.util.UUID;

public interface DisplayNameConflictPort {

    boolean conflictsInClasses(UUID userId,
                               String normalizedDisplayName,
                               Set<UUID> classIds);
}
