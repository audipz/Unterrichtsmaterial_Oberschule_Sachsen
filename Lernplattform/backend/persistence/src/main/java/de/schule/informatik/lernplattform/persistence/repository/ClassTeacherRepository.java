package de.schule.informatik.lernplattform.persistence.repository;

import de.schule.informatik.lernplattform.persistence.entity.ClassTeacherEntity;
import de.schule.informatik.lernplattform.persistence.entity.ClassTeacherId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, ClassTeacherId> {

    List<ClassTeacherEntity> findByTeacherIdAndDeletedAtIsNull(UUID teacherId);
}
