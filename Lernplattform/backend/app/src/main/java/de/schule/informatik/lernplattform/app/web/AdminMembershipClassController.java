package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.school.SchoolLookupPort;
import de.schule.informatik.lernplattform.domain.schoolclass.MembershipClassAdministrationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schulen/{schoolSlug}/admin/classes")
public class AdminMembershipClassController {

    private final MembershipClassAdministrationService service;
    private final SchoolLookupPort schoolLookupPort;
    private final CurrentActor currentActor;

    public AdminMembershipClassController(MembershipClassAdministrationService service,
                                          SchoolLookupPort schoolLookupPort,
                                          CurrentActor currentActor) {
        this.service = service;
        this.schoolLookupPort = schoolLookupPort;
        this.currentActor = currentActor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateClassResponse create(@PathVariable String schoolSlug,
                                      @Valid @RequestBody CreateClassRequest request) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        UUID classId = service.createClass(
                schoolId,
                request.name(),
                request.gradeLevel(),
                request.schoolYear(),
                request.initialTeacherAccountIds(),
                currentActor.id());
        return new CreateClassResponse(classId);
    }

    @PostMapping("/{classId}/students/{studentAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(@PathVariable String schoolSlug,
                           @PathVariable UUID classId,
                           @PathVariable UUID studentAccountId,
                           @RequestParam(required = false) LocalDate validFrom) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.addStudent(schoolId, classId, studentAccountId, validFrom, currentActor.id());
    }

    @DeleteMapping("/{classId}/students/{studentAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStudent(@PathVariable String schoolSlug,
                              @PathVariable UUID classId,
                              @PathVariable UUID studentAccountId,
                              @RequestParam(required = false) LocalDate effectiveDate) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.removeStudent(schoolId, classId, studentAccountId, effectiveDate, currentActor.id());
    }

    @PostMapping("/{sourceClassId}/students/{studentAccountId}/move/{targetClassId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void moveStudent(@PathVariable String schoolSlug,
                            @PathVariable UUID sourceClassId,
                            @PathVariable UUID targetClassId,
                            @PathVariable UUID studentAccountId,
                            @RequestParam(required = false) LocalDate effectiveDate) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.moveStudent(schoolId, studentAccountId, sourceClassId, targetClassId, effectiveDate, currentActor.id());
    }

    @PostMapping("/{classId}/teachers/{teacherAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTeacher(@PathVariable String schoolSlug,
                           @PathVariable UUID classId,
                           @PathVariable UUID teacherAccountId) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.addTeacher(schoolId, classId, teacherAccountId, currentActor.id());
    }

    @DeleteMapping("/{classId}/teachers/{teacherAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTeacher(@PathVariable String schoolSlug,
                              @PathVariable UUID classId,
                              @PathVariable UUID teacherAccountId) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.removeTeacher(schoolId, classId, teacherAccountId, currentActor.id());
    }

    @DeleteMapping("/{classId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable String schoolSlug,
                            @PathVariable UUID classId) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.softDeleteClass(schoolId, classId, currentActor.id());
    }

    @PostMapping("/{classId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateClass(@PathVariable String schoolSlug,
                                @PathVariable UUID classId) {
        UUID schoolId = schoolLookupPort.requireActiveSchoolId(schoolSlug);
        service.reactivateClass(schoolId, classId, currentActor.id());
    }

    public record CreateClassRequest(
            @NotBlank String name,
            @Min(1) @Max(13) int gradeLevel,
            @NotBlank String schoolYear,
            @NotEmpty Set<UUID> initialTeacherAccountIds
    ) {}

    public record CreateClassResponse(@NotNull UUID classId) {}
}
