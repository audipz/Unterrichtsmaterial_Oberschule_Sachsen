package de.schule.informatik.lernplattform.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "school_class_membership")
public class SchoolClassMembershipEntity {

    @Id
    private UUID id;

    @Column(name = "school_class_id", nullable = false)
    private UUID schoolClassId;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    protected SchoolClassMembershipEntity() {
    }

    public UUID getId() { return id; }
    public UUID getSchoolClassId() { return schoolClassId; }
    public UUID getStudentId() { return studentId; }
    public String getStatus() { return status; }
}
