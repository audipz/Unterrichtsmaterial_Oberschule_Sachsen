package de.schule.informatik.lernplattform.domain.displayname;

public final class DisplayNameConflictException extends RuntimeException {

    public DisplayNameConflictException(String displayName) {
        super("Display name is already in use in at least one assigned class: " + displayName);
    }
}
