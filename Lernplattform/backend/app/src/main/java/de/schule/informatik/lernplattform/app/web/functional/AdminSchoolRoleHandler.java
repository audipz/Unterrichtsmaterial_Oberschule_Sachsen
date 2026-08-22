package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminMembershipRoleService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.UUID;

@Component
public class AdminSchoolRoleHandler {

    private final SchoolAdminMembershipRoleService service;
    private final SchoolLookupPort schoolLookupPort;
    private final CurrentActor currentActor;

    public AdminSchoolRoleHandler(SchoolAdminMembershipRoleService service,
                                  SchoolLookupPort schoolLookupPort,
                                  CurrentActor currentActor) {
        this.service = service;
        this.schoolLookupPort = schoolLookupPort;
        this.currentActor = currentActor;
    }

    public ServerResponse grantSchoolAdmin(ServerRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(request.pathVariable("schoolSlug"));
        UUID teacherId = UUID.fromString(request.pathVariable("teacherId"));
        service.grant(schoolId, teacherId, currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse revokeSchoolAdmin(ServerRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(request.pathVariable("schoolSlug"));
        UUID teacherId = UUID.fromString(request.pathVariable("teacherId"));
        service.revoke(schoolId, teacherId, currentActor.id());
        return ServerResponse.noContent().build();
    }
}
