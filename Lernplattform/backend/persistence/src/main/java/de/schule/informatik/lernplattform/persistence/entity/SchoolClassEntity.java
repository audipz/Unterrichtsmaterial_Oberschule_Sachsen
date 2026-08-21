package de.schule.informatik.lernplattform.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "school_class")
public class SchoolClassEntity {

    @Id
    private UUID id;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "grade_level", nullable = false)
    private short gradeLevel;

    @Column(name = "school_year", nullable = false, length = 20)
    private String schoolYear;

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

    protected SchoolClassEntity() {
    }

    public UUID getId() { return id; }
    public UUID getSchoolId() { return schoolId; }
    public String getName() { return name; }
    public short getGradeLevel() { return gradeLevel; }
    public String getSchoolYear() { return schoolYear; }
    public String getStatus() { return status; }
}
