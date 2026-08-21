package de.schule.informatik.lernplattform.persistence.repository;

import de.schule.informatik.lernplattform.persistence.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {
}
