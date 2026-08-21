package de.schule.informatik.lernplattform.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRoleEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Id
    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Id
    @Column(nullable = false, length = 40)
    private String role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    protected UserRoleEntity() {
    }

    public UUID getUserId() { return userId; }
    public UUID getSchoolId() { return schoolId; }
    public String getRole() { return role; }
}
