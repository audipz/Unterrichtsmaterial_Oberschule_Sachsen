package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.registration.SchoolRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

@Component
public class PublicRegistrationHandler {

    private final SchoolRegistrationService service;

    public PublicRegistrationHandler(SchoolRegistrationService service) {
        this.service = service;
    }

    public ServerResponse submitSchoolRegistration(ServerRequest request) throws Exception {
        RegistrationPayload payload = request.body(RegistrationPayload.class);
        var id = service.submit(new SchoolRegistrationService.Command(
                payload.schoolName(),
                payload.schoolType(),
                payload.federalState(),
                payload.city(),
                payload.contactEmail(),
                payload.schoolWebsite(),
                payload.website(),
                request.headers().firstHeader("User-Agent")));

        return ServerResponse.status(HttpStatus.ACCEPTED)
                .body(Map.of("requestId", id.toString(), "status", "EMAIL_VERIFICATION_PENDING"));
    }

    public record RegistrationPayload(
            String schoolName,
            String schoolType,
            String federalState,
            String city,
            String contactEmail,
            String schoolWebsite,
            String website) {
    }
}
