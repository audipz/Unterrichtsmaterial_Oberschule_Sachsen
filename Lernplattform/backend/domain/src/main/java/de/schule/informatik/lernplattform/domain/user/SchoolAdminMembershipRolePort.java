package de.schule.informatik.lernplattform.domain.user;

import java.util.UUID;

public interface SchoolAdminMembershipRolePort {

    record TeacherMembership(UUID membershipId,
                             UUID accountId,
                             UUID schoolId,
                             AccountType accountType,
                             boolean active,
                             boolean schoolAdmin) {}

    TeacherMembership requireTeacherMembership(UUID teacherAccountId, UUID schoolId);

    long countActiveSchoolAdmins(UUID schoolId);

    void grantSchoolAdmin(UUID membershipId, UUID actorId);

    void revokeSchoolAdmin(UUID membershipId, UUID actorId);
}
