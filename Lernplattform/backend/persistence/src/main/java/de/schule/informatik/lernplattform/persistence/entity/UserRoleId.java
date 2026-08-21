package de.schule.informatik.lernplattform.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserRoleId implements Serializable {

    private UUID userId;
    private UUID schoolId;
    private String role;

    public UserRoleId() {
    }

    public UserRoleId(UUID userId, UUID schoolId, String role) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId that)) return false;
        return Objects.equals(userId, that.userId)
                && Objects.equals(schoolId, that.schoolId)
                && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, schoolId, role);
    }
}
