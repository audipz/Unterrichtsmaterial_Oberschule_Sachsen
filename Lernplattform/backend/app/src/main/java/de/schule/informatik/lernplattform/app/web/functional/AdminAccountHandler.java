package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.app.user.AccountProvisioningService;
import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import de.schule.informatik.lernplattform.domain.user.CreateStudentCommand;
import de.schule.informatik.lernplattform.domain.user.CreateTeacherCommand;
import de.schule.informatik.lernplattform.domain.user.TeacherSchoolMembershipService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Set;
import java.util.UUID;

@Component
public final class AdminAccountHandler {

    private final AccountProvisioningService service;
    private final TeacherSchoolMembershipService teacherMembershipService;
    private final SchoolLookupPort schoolLookupPort;
    private final CurrentActor currentActor;
    private final Validator validator;

    public AdminAccountHandler(AccountProvisioningService service,
                               TeacherSchoolMembershipService teacherMembershipService,
                               SchoolLookupPort schoolLookupPort,
                               CurrentActor currentActor,
                               Validator validator) {
        this.service = service;
        this.teacherMembershipService = teacherMembershipService;
        this.schoolLookupPort = schoolLookupPort;
        this.currentActor = currentActor;
        this.validator = validator;
    }

    public ServerResponse createStudent(ServerRequest request) throws Exception {
        String schoolSlug = request.pathVariable("schoolSlug");
        var body = validated(request.body(CreateStudentRequest.class));
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        UUID accountId = service.createStudent(new CreateStudentCommand(
                schoolId,
                body.username(),
                body.initialPassword(),
                body.visibleClassIds() == null ? Set.of() : body.visibleClassIds(),
                currentActor.id()));

        return ServerResponse.status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AccountResponse(accountId));
    }

    public ServerResponse createTeacher(ServerRequest request) throws Exception {
        String schoolSlug = request.pathVariable("schoolSlug");
        var body = validated(request.body(CreateTeacherRequest.class));
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        UUID accountId = service.createTeacher(new CreateTeacherCommand(
                body.email(),
                schoolId,
                currentActor.id()));

        return ServerResponse.status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AccountResponse(accountId));
    }

    public ServerResponse addExistingTeacher(ServerRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(request.pathVariable("schoolSlug"));
        UUID teacherId = UUID.fromString(request.pathVariable("teacherId"));
        service.addExistingTeacherToSchool(schoolId, teacherId, currentActor.id());
        return ServerResponse.noContent().build();
    }

    public ServerResponse removeTeacherFromSchool(ServerRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(request.pathVariable("schoolSlug"));
        UUID teacherId = UUID.fromString(request.pathVariable("teacherId"));
        teacherMembershipService.removeTeacherFromSchool(schoolId, teacherId, currentActor.id());
        return ServerResponse.noContent().build();
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

    public record CreateStudentRequest(
            @NotBlank String username,
            @NotBlank String initialPassword,
            Set<UUID> visibleClassIds
    ) {}

    public record CreateTeacherRequest(@NotBlank @Email String email) {}

    public record AccountResponse(UUID accountId) {}
}
