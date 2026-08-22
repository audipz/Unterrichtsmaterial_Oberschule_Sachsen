package de.schule.informatik.lernplattform.domain.user;

import java.util.UUID;

public interface AccountProvisioningPort {

    boolean studentUsernameExists(UUID schoolId, String normalizedUsername);

    boolean teacherEmailExists(String normalizedEmail);

    UUID createStudent(UUID schoolId,
                       String username,
                       String normalizedUsername,
                       String displayName,
                       String normalizedDisplayName,
                       String passwordHash,
                       UUID actorId);

    UUID createTeacher(String email,
                       String normalizedEmail,
                       String displayName,
                       String normalizedDisplayName,
                       UUID actorId);

    void addTeacherToSchool(UUID teacherId, UUID schoolId, UUID actorId);
}
