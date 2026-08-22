package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.registration.SystemAdminRegistrationService;
import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.UUID;

@Component
public class SystemAdminRegistrationHandler {

    private final SystemAdminRegistrationService service;
    private final CurrentActor currentActor;

    public SystemAdminRegistrationHandler(SystemAdminRegistrationService service, CurrentActor currentActor) {
        this.service = service;
        this.currentActor = currentActor;
    }

    public ServerResponse listPending(ServerRequest request) {
        return ServerResponse.ok().body(service.pending(currentActor.id()));
    }

    public ServerResponse approve(ServerRequest request) {
        UUID requestId = UUID.fromString(request.pathVariable("requestId"));
        UUID schoolId = service.approve(requestId, currentActor.id());
        return ServerResponse.status(HttpStatus.CREATED).body(Map.of("schoolId", schoolId.toString(), "status", "APPROVED"));
    }

    public ServerResponse reject(ServerRequest request) throws Exception {
        UUID requestId = UUID.fromString(request.pathVariable("requestId"));
        RejectPayload payload = request.body(RejectPayload.class);
        service.reject(requestId, payload.reason(), currentActor.id());
        return ServerResponse.noContent().build();
    }

    public record RejectPayload(String reason) {
    }
}
