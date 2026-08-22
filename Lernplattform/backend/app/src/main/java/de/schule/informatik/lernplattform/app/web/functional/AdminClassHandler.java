package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import de.schule.informatik.lernplattform.domain.schoolclass.MembershipClassAdministrationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Component
public final class AdminClassHandler {

    private final MembershipClassAdministrationService service;
    private final SchoolLookupPort schoolLookupPort;
    private final CurrentActor currentActor;
    private final Validator validator;

    public AdminClassHandler(MembershipClassAdministrationService service,
                             SchoolLookupPort schoolLookupPort,
                             CurrentActor currentActor,
                             Validator validator) {
        this.service = service;
        this.schoolLookupPort = schoolLookupPort;
        this.currentActor = currentActor;
        this.validator = validator;
    }

    public ServerResponse createClass(ServerRequest request) throws Exception {
        UUID schoolId = schoolId(request);
        var body = validated(request.body(CreateClassRequest.class));
        UUID classId = service.createClass(
                schoolId,
                body.name(),
                body.gradeLevel(),
                body.schoolYear(),
                body.initialTeacherAccountIds(),
                currentActor.id());
        return ServerResponse.status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateClassResponse(classId));
    }

    public ServerResponse addStudent(ServerRequest request) {
        service.addStudent(
                schoolId(request),
                uuid(request, "classId"),
                uuid(request, "studentAccountId"),
                dateParam(request, "validFrom"),
                currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse removeStudent(ServerRequest request) {
        service.removeStudent(
                schoolId(request),
                uuid(request, "classId"),
                uuid(request, "studentAccountId"),
                dateParam(request, "effectiveDate"),
                currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse moveStudent(ServerRequest request) {
        service.moveStudent(
                schoolId(request),
                uuid(request, "studentAccountId"),
                uuid(request, "sourceClassId"),
                uuid(request, "targetClassId"),
                dateParam(request, "effectiveDate"),
                currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse addTeacher(ServerRequest request) {
        service.addTeacher(
                schoolId(request),
                uuid(request, "classId"),
                uuid(request, "teacherAccountId"),
                currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse removeTeacher(ServerRequest request) {
        service.removeTeacher(
                schoolId(request),
                uuid(request, "classId"),
                uuid(request, "teacherAccountId"),
                currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse deleteClass(ServerRequest request) {
        service.softDeleteClass(schoolId(request), uuid(request, "classId"), currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse reactivateClass(ServerRequest request) {
        service.reactivateClass(schoolId(request), uuid(request, "classId"), currentActor.id());
        return ServerResponse.noContent().build();
    }

    private UUID schoolId(ServerRequest request) {
        return schoolLookupPort.requireActiveSchoolId(request.pathVariable("schoolSlug"));
    }

    private static UUID uuid(ServerRequest request, String name) {
        return UUID.fromString(request.pathVariable(name));
    }

    private static LocalDate dateParam(ServerRequest request, String name) {
        return request.param(name).filter(v -> !v.isBlank()).map(LocalDate::parse).orElse(null);
    }

    private <T> T validated(T value) {
        Set<ConstraintViolation<T>> violations = validator.validate(value);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .sorted()
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("validation failed");
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public record CreateClassRequest(
            @NotBlank String name,
            @Min(1) @Max(13) int gradeLevel,
            @NotBlank String schoolYear,
            @NotEmpty Set<UUID> initialTeacherAccountIds
    ) {}

    public record CreateClassResponse(UUID classId) {}
}
