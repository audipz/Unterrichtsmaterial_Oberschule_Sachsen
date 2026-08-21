package de.schule.informatik.lernplattform.persistence.repository;

import de.schule.informatik.lernplattform.persistence.entity.SchoolClassMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SchoolClassMembershipRepository extends JpaRepository<SchoolClassMembershipEntity, UUID> {

    List<SchoolClassMembershipEntity> findByStudentIdAndStatusAndDeletedAtIsNull(UUID studentId, String status);
}
