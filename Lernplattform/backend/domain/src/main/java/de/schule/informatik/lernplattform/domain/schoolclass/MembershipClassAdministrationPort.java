package de.schule.informatik.lernplattform.domain.schoolclass;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface MembershipClassAdministrationPort {

    record MembershipContext(UUID membershipId,
                             UUID accountId,
                             UUID schoolId,
                             String accountType,
                             String displayNameNormalized) {}

    MembershipContext requireActiveMembership(UUID schoolId, UUID accountId, String accountType);

    UUID createClassWithTeachers(UUID schoolId,
                                 String name,
                                 int gradeLevel,
                                 String schoolYear,
                                 Set<UUID> teacherMembershipIds,
                                 UUID actorId);

    void addStudent(UUID schoolId, UUID classId, UUID studentMembershipId, LocalDate validFrom, UUID actorId);

    void removeStudent(UUID schoolId, UUID classId, UUID studentMembershipId, LocalDate effectiveDate, UUID actorId);

    void moveStudent(UUID schoolId,
                     UUID studentMembershipId,
                     UUID sourceClassId,
                     UUID targetClassId,
                     LocalDate effectiveDate,
                     UUID actorId);

    void addTeacher(UUID schoolId, UUID classId, UUID teacherMembershipId, UUID actorId);

    void removeTeacher(UUID schoolId, UUID classId, UUID teacherMembershipId, UUID actorId);

    int activeTeacherCount(UUID schoolId, UUID classId);

    boolean isTeacherAssigned(UUID schoolId, UUID classId, UUID teacherMembershipId);

    void softDeleteClass(UUID schoolId, UUID classId, UUID actorId);

    void reactivateClass(UUID schoolId, UUID classId, UUID actorId);

    Set<UUID> activeClassIdsForAccount(UUID accountId);

    void lockClasses(Set<UUID> classIds);
}
