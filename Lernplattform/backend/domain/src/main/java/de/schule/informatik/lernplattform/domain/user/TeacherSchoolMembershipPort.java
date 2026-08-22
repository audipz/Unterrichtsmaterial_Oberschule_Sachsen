package de.schule.informatik.lernplattform.domain.user;

import java.util.Set;
import java.util.UUID;

public interface TeacherSchoolMembershipPort {

    record TeacherMembershipContext(UUID membershipId,
                                    UUID teacherId,
                                    UUID schoolId,
                                    boolean active,
                                    boolean schoolAdmin) {}

    TeacherMembershipContext requireTeacherMembership(UUID teacherId, UUID schoolId);

    Set<UUID> classesWhereTeacherIsSoleAssignedTeacher(UUID membershipId);

    long countOtherActiveSchoolAdmins(UUID schoolId, UUID excludedMembershipId);

    void endTeacherMembership(UUID membershipId, UUID actorId);

    long countActiveSchoolMemberships(UUID teacherId);

    void markAccountPendingDeletion(UUID teacherId, UUID actorId);
}
