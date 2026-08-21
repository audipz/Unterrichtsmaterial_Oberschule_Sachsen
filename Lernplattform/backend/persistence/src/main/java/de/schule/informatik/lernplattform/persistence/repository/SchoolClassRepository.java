package de.schule.informatik.lernplattform.persistence.repository;

import de.schule.informatik.lernplattform.persistence.entity.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, UUID> {

    List<SchoolClassEntity> findBySchoolIdAndDeletedAtIsNull(UUID schoolId);
}
