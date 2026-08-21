package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;

import java.util.UUID;

public final class SchoolAdminRoleService {

    private final SchoolAdminRolePort port;
    private final SchoolAuthorizationPort authorizationPort;

    public SchoolAdminRoleService(SchoolAdminRolePort port,
                                  SchoolAuthorizationPort authorizationPort) {
        this.port = port;
        this.authorizationPort = authorizationPort;
    }

    public void grant(UUID schoolId, UUID teacherId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var target = requireActiveTeacherOfSchool(schoolId, teacherId);
        if (target.roles().contains(UserRole.SCHOOL_ADMIN)) {
            return;
        }
        port.grantSchoolAdmin(schoolId, teacherId, actorId);
    }

    public void revoke(UUID schoolId, UUID teacherId, UUID actorId) {
        authorizationPort.requireSchoolAdmin(actorId, schoolId);
        var target = requireActiveTeacherOfSchool(schoolId, teacherId);
        if (!target.roles().contains(UserRole.SCHOOL_ADMIN)) {
            return;
        }
        if (port.countActiveSchoolAdmins(schoolId) <= 1) {
            throw new IllegalStateException("Der letzte aktive Schuladministrator kann nicht entfernt werden.");
        }
        port.revokeSchoolAdmin(schoolId, teacherId, actorId);
    }

    private SchoolAdminRolePort.TargetUser requireActiveTeacherOfSchool(UUID schoolId, UUID teacherId) {
        var target = port.requireUser(teacherId);
        if (!schoolId.equals(target.schoolId())) {
            throw new IllegalArgumentException("Benutzer gehört zu einer anderen Schule.");
        }
        if (target.status() != UserStatus.ACTIVE) {
            throw new IllegalArgumentException("Benutzer ist nicht aktiv.");
        }
        if (!target.roles().contains(UserRole.TEACHER)) {
            throw new IllegalArgumentException("Nur Lehrern kann die Schuladmin-Rolle zugewiesen werden.");
        }
        return target;
    }
}
