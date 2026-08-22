package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;

import java.util.Set;
import java.util.UUID;

public final class TeacherSchoolMembershipService {

    private final TeacherSchoolMembershipPort port;
    private final SchoolAuthorizationPort authorizationPort;

    public TeacherSchoolMembershipService(TeacherSchoolMembershipPort port,
                                          SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.authorizationPort = authorizationPort;
    }

    public void removeTeacherFromSchool(UUID schoolId, UUID teacherId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var membership = port.requireTeacherMembership(teacherId, schoolId);
        if (!membership.active()) {
            throw new IllegalArgumentException("teacher school membership is not active");
        }

        Set<UUID> orphanedClasses = port.classesWhereTeacherIsSoleAssignedTeacher(membership.membershipId());
        if (!orphanedClasses.isEmpty()) {
            throw new IllegalStateException("Lehrer kann nicht entfernt werden; mindestens eine Klasse hätte danach keinen zugewiesenen Lehrer: " + orphanedClasses);
        }

        if (membership.schoolAdmin() && port.countOtherActiveSchoolAdmins(schoolId, membership.membershipId()) == 0) {
            throw new IllegalStateException("Der letzte aktive Schuladmin kann nicht aus der Schule entfernt werden.");
        }

        port.endTeacherMembership(membership.membershipId(), actorId);
        if (port.countActiveSchoolMemberships(teacherId) == 0) {
            port.markAccountPendingDeletion(teacherId, actorId);
        }
    }
}
