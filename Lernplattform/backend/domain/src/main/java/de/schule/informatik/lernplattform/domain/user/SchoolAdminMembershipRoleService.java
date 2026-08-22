package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;

import java.util.UUID;

public final class SchoolAdminMembershipRoleService {

    private final SchoolAdminMembershipRolePort port;
    private final SchoolAuthorizationPort authorizationPort;

    public SchoolAdminMembershipRoleService(SchoolAdminMembershipRolePort port,
                                            SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.authorizationPort = authorizationPort;
    }

    public void grant(UUID schoolId, UUID teacherAccountId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var membership = port.requireTeacherMembership(teacherAccountId, schoolId);
        requireActiveTeacher(membership);
        if (membership.schoolAdmin()) {
            return;
        }
        port.grantSchoolAdmin(membership.membershipId(), actorId);
    }

    public void revoke(UUID schoolId, UUID teacherAccountId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var membership = port.requireTeacherMembership(teacherAccountId, schoolId);
        requireActiveTeacher(membership);
        if (!membership.schoolAdmin()) {
            return;
        }
        if (port.countActiveSchoolAdmins(schoolId) <= 1) {
            throw new IllegalStateException("Eine Schule muss mindestens einen aktiven Schuladmin besitzen.");
        }
        port.revokeSchoolAdmin(membership.membershipId(), actorId);
    }

    private static void requireActiveTeacher(SchoolAdminMembershipRolePort.TeacherMembership membership) {
        if (membership.accountType() != AccountType.TEACHER) {
            throw new IllegalArgumentException("account is not a teacher");
        }
        if (!membership.active()) {
            throw new IllegalArgumentException("school membership is not active");
        }
    }
}
