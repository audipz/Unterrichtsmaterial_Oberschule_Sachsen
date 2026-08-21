package de.schule.informatik.lernplattform.domain.user;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class User {

    private final UUID id;
    private final UUID schoolId;
    private final String username;
    private String displayName;
    private String displayNameNormalized;
    private UserStatus status;
    private final Set<UserRole> roles;

    public User(UUID id,
                UUID schoolId,
                String username,
                String displayName,
                String displayNameNormalized,
                UserStatus status,
                Set<UserRole> roles) {
        this.id = Objects.requireNonNull(id);
        this.schoolId = Objects.requireNonNull(schoolId);
        this.username = requireText(username, "username");
        this.displayName = requireText(displayName, "displayName");
        this.displayNameNormalized = requireText(displayNameNormalized, "displayNameNormalized");
        this.status = Objects.requireNonNull(status);
        this.roles = Set.copyOf(roles);
    }

    public UUID id() { return id; }
    public UUID schoolId() { return schoolId; }
    public String username() { return username; }
    public String displayName() { return displayName; }
    public String displayNameNormalized() { return displayNameNormalized; }
    public UserStatus status() { return status; }
    public Set<UserRole> roles() { return roles; }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public void rename(String displayName, String normalized) {
        this.displayName = requireText(displayName, "displayName");
        this.displayNameNormalized = requireText(normalized, "displayNameNormalized");
    }

    public void disable() {
        this.status = UserStatus.DISABLED;
    }

    public void softDelete() {
        this.status = UserStatus.SOFT_DELETED;
    }

    public void reactivate() {
        this.status = UserStatus.ACTIVE;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}
