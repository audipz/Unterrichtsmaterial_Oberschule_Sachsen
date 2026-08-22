package de.schule.informatik.lernplattform.domain.schoolclass;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictException;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class MembershipClassAdministrationService {

    private final MembershipClassAdministrationPort port;
    private final DisplayNameConflictPort displayNameConflictPort;
    private final SchoolAuthorizationPort authorizationPort;

    public MembershipClassAdministrationService(MembershipClassAdministrationPort port,
                                                DisplayNameConflictPort displayNameConflictPort,
                                                SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.displayNameConflictPort = displayNameConflictPort;
        this.authorizationPort = authorizationPort;
    }

    public UUID createClass(UUID schoolId,
                            String name,
                            int gradeLevel,
                            String schoolYear,
                            Set<UUID> initialTeacherAccountIds,
                            UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        if (gradeLevel < 1 || gradeLevel > 13) throw new IllegalArgumentException("gradeLevel must be between 1 and 13");
        if (name == null || name.isBlank() || schoolYear == null || schoolYear.isBlank()) {
            throw new IllegalArgumentException("name and schoolYear must not be blank");
        }
        if (initialTeacherAccountIds == null || initialTeacherAccountIds.isEmpty()) {
            throw new IllegalArgumentException("an active class needs at least one assigned teacher");
        }

        Set<UUID> memberships = new HashSet<>();
        for (UUID accountId : initialTeacherAccountIds) {
            memberships.add(port.requireActiveMembership(schoolId, accountId, "TEACHER").membershipId());
        }
        return port.createClassWithTeachers(schoolId, name.trim(), gradeLevel, schoolYear.trim(), memberships, actorId);
    }

    public void addStudent(UUID schoolId, UUID classId, UUID studentAccountId, LocalDate validFrom, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var student = port.requireActiveMembership(schoolId, studentAccountId, "STUDENT");
        ensureDisplayNameAvailable(student, classId, Set.of());
        port.addStudent(schoolId, classId, student.membershipId(), validFrom == null ? LocalDate.now() : validFrom, actorId);
    }

    public void removeStudent(UUID schoolId, UUID classId, UUID studentAccountId, LocalDate effectiveDate, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var student = port.requireActiveMembership(schoolId, studentAccountId, "STUDENT");
        port.removeStudent(schoolId, classId, student.membershipId(), effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
    }

    public void moveStudent(UUID schoolId,
                            UUID studentAccountId,
                            UUID sourceClassId,
                            UUID targetClassId,
                            LocalDate effectiveDate,
                            UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        if (sourceClassId.equals(targetClassId)) throw new IllegalArgumentException("source and target class must differ");
        var student = port.requireActiveMembership(schoolId, studentAccountId, "STUDENT");
        ensureDisplayNameAvailable(student, targetClassId, Set.of(sourceClassId));
        port.moveStudent(schoolId, student.membershipId(), sourceClassId, targetClassId,
                effectiveDate == null ? LocalDate.now() : effectiveDate, actorId);
    }

    public void addTeacher(UUID schoolId, UUID classId, UUID teacherAccountId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var teacher = port.requireActiveMembership(schoolId, teacherAccountId, "TEACHER");
        ensureDisplayNameAvailable(teacher, classId, Set.of());
        port.addTeacher(schoolId, classId, teacher.membershipId(), actorId);
    }

    public void removeTeacher(UUID schoolId, UUID classId, UUID teacherAccountId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var teacher = port.requireActiveMembership(schoolId, teacherAccountId, "TEACHER");
        port.lockClasses(Set.of(classId));
        if (!port.isTeacherAssigned(schoolId, classId, teacher.membershipId())) {
            throw new IllegalArgumentException("teacher is not assigned to class");
        }
        if (port.activeTeacherCount(schoolId, classId) <= 1) {
            throw new IllegalStateException("an active class must keep at least one assigned teacher");
        }
        port.removeTeacher(schoolId, classId, teacher.membershipId(), actorId);
    }

    public void softDeleteClass(UUID schoolId, UUID classId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        port.softDeleteClass(schoolId, classId, actorId);
    }

    public void reactivateClass(UUID schoolId, UUID classId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        if (port.activeTeacherCount(schoolId, classId) < 1) {
            throw new IllegalStateException("a reactivated class needs at least one assigned teacher");
        }
        port.reactivateClass(schoolId, classId, actorId);
    }

    private void ensureDisplayNameAvailable(MembershipClassAdministrationPort.MembershipContext membership,
                                            UUID targetClassId,
                                            Set<UUID> excludedClasses) {
        Set<UUID> affectedClasses = new HashSet<>(port.activeClassIdsForAccount(membership.accountId()));
        affectedClasses.removeAll(excludedClasses);
        affectedClasses.add(targetClassId);
        port.lockClasses(affectedClasses);
        if (displayNameConflictPort.conflictsInClasses(
                membership.accountId(), membership.displayNameNormalized(), affectedClasses)) {
            throw new DisplayNameConflictException("displayName conflicts in one of the assigned classes");
        }
    }
}
