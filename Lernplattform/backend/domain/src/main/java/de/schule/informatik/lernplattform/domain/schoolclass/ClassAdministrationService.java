package de.schule.informatik.lernplattform.domain.schoolclass;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictException;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ClassAdministrationService {

    private final ClassAdministrationPort port;
    private final DisplayNameConflictPort displayNameConflictPort;

    public ClassAdministrationService(ClassAdministrationPort port,
                                      DisplayNameConflictPort displayNameConflictPort) {
        this.port = port;
        this.displayNameConflictPort = displayNameConflictPort;
    }

    public UUID createClass(UUID schoolId, String name, int gradeLevel, String schoolYear, UUID actorId) {
        if (gradeLevel < 1 || gradeLevel > 13) {
            throw new IllegalArgumentException("gradeLevel must be between 1 and 13");
        }
        if (name == null || name.isBlank() || schoolYear == null || schoolYear.isBlank()) {
            throw new IllegalArgumentException("name and schoolYear must not be blank");
        }
        return port.createClass(schoolId, name.trim(), gradeLevel, schoolYear.trim(), actorId);
    }

    public void addStudent(UUID schoolId, UUID classId, UUID studentId, LocalDate validFrom, UUID actorId) {
        var user = port.requireActiveUser(studentId);
        requireSameSchool(schoolId, user.schoolId());
        requireRole(user.roles(), "STUDENT");
        ensureDisplayNameAvailable(user, classId, Set.of());
        port.addStudent(schoolId, classId, studentId, validFrom, actorId);
    }

    public void removeStudent(UUID schoolId, UUID classId, UUID studentId, LocalDate effectiveDate, UUID actorId) {
        var user = port.requireActiveUser(studentId);
        requireSameSchool(schoolId, user.schoolId());
        requireRole(user.roles(), "STUDENT");
        port.removeStudent(schoolId, classId, studentId,
                effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
    }

    public void moveStudent(UUID schoolId,
                            UUID studentId,
                            UUID sourceClassId,
                            UUID targetClassId,
                            LocalDate effectiveDate,
                            UUID actorId) {
        if (sourceClassId.equals(targetClassId)) {
            throw new IllegalArgumentException("sourceClassId and targetClassId must differ");
        }
        var user = port.requireActiveUser(studentId);
        requireSameSchool(schoolId, user.schoolId());
        requireRole(user.roles(), "STUDENT");

        Set<UUID> excludedClasses = Set.of(sourceClassId);
        ensureDisplayNameAvailable(user, targetClassId, excludedClasses);
        port.moveStudent(schoolId, studentId, sourceClassId, targetClassId,
                effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
    }

    public void addTeacher(UUID schoolId, UUID classId, UUID teacherId, UUID actorId) {
        var user = port.requireActiveUser(teacherId);
        requireSameSchool(schoolId, user.schoolId());
        requireRole(user.roles(), "TEACHER");
        ensureDisplayNameAvailable(user, classId, Set.of());
        port.addTeacher(schoolId, classId, teacherId, actorId);
    }

    public void removeTeacher(UUID schoolId, UUID classId, UUID teacherId, UUID actorId) {
        var user = port.requireActiveUser(teacherId);
        requireSameSchool(schoolId, user.schoolId());
        requireRole(user.roles(), "TEACHER");
        port.removeTeacher(schoolId, classId, teacherId, actorId);
    }

    public void softDeleteClass(UUID schoolId, UUID classId, UUID actorId) {
        port.softDeleteClass(schoolId, classId, actorId);
    }

    public void reactivateClass(UUID schoolId, UUID classId, UUID actorId) {
        port.reactivateClass(schoolId, classId, actorId);
    }

    private void ensureDisplayNameAvailable(ClassAdministrationPort.UserContext user,
                                            UUID targetClassId,
                                            Set<UUID> excludedClasses) {
        Set<UUID> affectedClasses = new HashSet<>(port.activeClassIdsForUser(user.userId()));
        affectedClasses.removeAll(excludedClasses);
        affectedClasses.add(targetClassId);
        port.lockClasses(affectedClasses);
        if (displayNameConflictPort.conflictsInClasses(user.userId(), user.displayNameNormalized(), affectedClasses)) {
            throw new DisplayNameConflictException("displayName conflicts in one of the assigned classes");
        }
    }

    private static void requireSameSchool(UUID expected, UUID actual) {
        if (!expected.equals(actual)) {
            throw new IllegalArgumentException("user belongs to another school");
        }
    }

    private static void requireRole(Set<String> roles, String requiredRole) {
        if (!roles.contains(requiredRole)) {
            throw new IllegalArgumentException("user does not have role " + requiredRole);
        }
    }
}
