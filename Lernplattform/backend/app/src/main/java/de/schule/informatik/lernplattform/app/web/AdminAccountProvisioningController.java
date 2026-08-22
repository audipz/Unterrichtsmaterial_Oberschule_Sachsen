package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.app.user.AccountProvisioningService;
import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import de.schule.informatik.lernplattform.domain.user.CreateStudentCommand;
import de.schule.informatik.lernplattform.domain.user.CreateTeacherCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schulen/{schoolSlug}/admin/accounts")
public class AdminAccountProvisioningController {

    private final AccountProvisioningService service;
    private final SchoolLookupPort schoolLookupPort;
    private final CurrentActor currentActor;

    public AdminAccountProvisioningController(AccountProvisioningService service,
                                              SchoolLookupPort schoolLookupPort,
                                              CurrentActor currentActor) {
        this.service = service;
        this.schoolLookupPort = schoolLookupPort;
        this.currentActor = currentActor;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createStudent(@PathVariable String schoolSlug,
                                         @Valid @RequestBody CreateStudentRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        UUID accountId = service.createStudent(new CreateStudentCommand(
                schoolId,
                request.username(),
                request.initialPassword(),
                request.visibleClassIds() == null ? Set.of() : request.visibleClassIds(),
                currentActor.id()
        ));
        return new AccountResponse(accountId);
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createTeacher(@PathVariable String schoolSlug,
                                         @Valid @RequestBody CreateTeacherRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        UUID accountId = service.createTeacher(new CreateTeacherCommand(
                request.email(),
                schoolId,
                currentActor.id()
        ));
        return new AccountResponse(accountId);
    }

    @PostMapping("/teachers/{teacherId}/school-membership")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addExistingTeacher(@PathVariable String schoolSlug,
                                   @PathVariable UUID teacherId) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.addExistingTeacherToSchool(schoolId, teacherId, currentActor.id());
    }

    public record CreateStudentRequest(
            @NotBlank String username,
            @NotBlank String initialPassword,
            Set<UUID> visibleClassIds
    ) {}

    public record CreateTeacherRequest(
            @NotBlank @Email String email
    ) {}

    public record AccountResponse(@NotNull UUID accountId) {}
}
