package de.schule.informatik.lernplattform.persistence.repository;

import de.schule.informatik.lernplattform.persistence.entity.UserRoleEntity;
import de.schule.informatik.lernplattform.persistence.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    List<UserRoleEntity> findByUserId(UUID userId);
}
