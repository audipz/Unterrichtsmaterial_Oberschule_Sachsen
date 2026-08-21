package de.schule.informatik.lernplattform.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "class_teacher")
@IdClass(ClassTeacherId.class)
public class ClassTeacherEntity {

    @Id
    @Column(name = "school_class_id", nullable = false)
    private UUID schoolClassId;

    @Id
    @Column(name = "teacher_id", nullable = false)
    private UUID teacherId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    protected ClassTeacherEntity() {
    }

    public UUID getSchoolClassId() { return schoolClassId; }
    public UUID getTeacherId() { return teacherId; }
    public Instant getDeletedAt() { return deletedAt; }
}
