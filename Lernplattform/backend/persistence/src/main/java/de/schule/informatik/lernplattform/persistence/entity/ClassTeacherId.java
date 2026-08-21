package de.schule.informatik.lernplattform.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ClassTeacherId implements Serializable {

    private UUID schoolClassId;
    private UUID teacherId;

    public ClassTeacherId() {
    }

    public ClassTeacherId(UUID schoolClassId, UUID teacherId) {
        this.schoolClassId = schoolClassId;
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTeacherId that)) return false;
        return Objects.equals(schoolClassId, that.schoolClassId)
                && Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolClassId, teacherId);
    }
}
