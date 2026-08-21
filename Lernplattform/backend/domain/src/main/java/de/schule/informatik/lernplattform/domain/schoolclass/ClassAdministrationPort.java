package de.schule.informatik.lernplattform.domain.schoolclass;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ClassAdministrationPort {

    record UserContext(UUID userId, UUID schoolId, String displayNameNormalized, Set<String> roles) {}

    UserContext requireActiveUser(UUID userId);

    UUID createClass(UUID schoolId, String name, int gradeLevel, String schoolYear, UUID actorId);

    void addStudent(UUID schoolId, UUID classId, UUID studentId, LocalDate validFrom, UUID actorId);

    void addTeacher(UUID schoolId, UUID classId, UUID teacherId, UUID actorId);

    Set<UUID> activeClassIdsForUser(UUID userId);

    void lockClasses(Set<UUID> classIds);
}
