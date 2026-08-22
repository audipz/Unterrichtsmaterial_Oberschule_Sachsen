package de.schule.informatik.lernplattform.domain.user;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Account {

    private final UUID id;
    private final AccountType type;
    private String displayName;
    private String displayNameNormalized;
    private String teacherEmail;
    private String teacherEmailNormalized;
    private AccountStatus status;
    private Instant pendingDeletionAt;

    public Account(UUID id,
                   AccountType type,
                   String displayName,
                   String displayNameNormalized,
                   String teacherEmail,
                   String teacherEmailNormalized,
                   AccountStatus status,
                   Instant pendingDeletionAt) {
        this.id = Objects.requireNonNull(id);
        this.type = Objects.requireNonNull(type);
        this.displayName = requireText(displayName, "displayName");
        this.displayNameNormalized = requireText(displayNameNormalized, "displayNameNormalized");
        this.status = Objects.requireNonNull(status);
        this.pendingDeletionAt = pendingDeletionAt;

        if (type == AccountType.TEACHER) {
            this.teacherEmail = requireText(teacherEmail, "teacherEmail");
            this.teacherEmailNormalized = requireText(teacherEmailNormalized, "teacherEmailNormalized");
        } else if (teacherEmail != null || teacherEmailNormalized != null) {
            throw new IllegalArgumentException("only teacher accounts may contain a teacher email");
        }
    }

    public UUID id() { return id; }
    public AccountType type() { return type; }
    public String displayName() { return displayName; }
    public String displayNameNormalized() { return displayNameNormalized; }
    public String teacherEmail() { return teacherEmail; }
    public String teacherEmailNormalized() { return teacherEmailNormalized; }
    public AccountStatus status() { return status; }
    public Instant pendingDeletionAt() { return pendingDeletionAt; }

    public void rename(String displayName, String normalized) {
        this.displayName = requireText(displayName, "displayName");
        this.displayNameNormalized = requireText(normalized, "displayNameNormalized");
    }

    public void scheduleDeletion(Instant purgeAt) {
        this.status = AccountStatus.PENDING_DELETION;
        this.pendingDeletionAt = Objects.requireNonNull(purgeAt);
    }

    public void cancelPendingDeletion() {
        this.status = AccountStatus.ACTIVE;
        this.pendingDeletionAt = null;
    }

    public void softDelete() {
        this.status = AccountStatus.SOFT_DELETED;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}
