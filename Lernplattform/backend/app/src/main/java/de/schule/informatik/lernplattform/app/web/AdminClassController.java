package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/schools/{schoolId}/classes")
public class AdminClassController {
    private final ClassAdministrationService service;
    private final CurrentActor currentActor;

    public AdminClassController(ClassAdministrationService service, CurrentActor currentActor) {
        this.service = service;
        this.currentActor = currentActor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateClassResponse create(@PathVariable UUID schoolId, @Valid @RequestBody CreateClassRequest request) {
        return new CreateClassResponse(service.createClass(schoolId, request.name(), request.gradeLevel(), request.schoolYear(), currentActor.id()));
    }

    @PostMapping("/{classId}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(@PathVariable UUID schoolId, @PathVariable UUID classId, @PathVariable UUID studentId,
                           @RequestParam(required = false) LocalDate validFrom) {
        service.addStudent(schoolId, classId, studentId, validFrom, currentActor.id());
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStudent(@PathVariable UUID schoolId, @PathVariable UUID classId, @PathVariable UUID studentId,
                              @RequestParam(required = false) LocalDate effectiveDate) {
        service.removeStudent(schoolId, classId, studentId, effectiveDate, currentActor.id());
    }

    @PostMapping("/{sourceClassId}/students/{studentId}/move/{targetClassId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void moveStudent(@PathVariable UUID schoolId, @PathVariable UUID sourceClassId,
                            @PathVariable UUID targetClassId, @PathVariable UUID studentId,
                            @RequestParam(required = false) LocalDate effectiveDate) {
        service.moveStudent(schoolId, studentId, sourceClassId, targetClassId, effectiveDate, currentActor.id());
    }

    @PostMapping("/{classId}/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTeacher(@PathVariable UUID schoolId, @PathVariable UUID classId, @PathVariable UUID teacherId) {
        service.addTeacher(schoolId, classId, teacherId, currentActor.id());
    }

    @DeleteMapping("/{classId}/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTeacher(@PathVariable UUID schoolId, @PathVariable UUID classId, @PathVariable UUID teacherId) {
        service.removeTeacher(schoolId, classId, teacherId, currentActor.id());
    }

    @DeleteMapping("/{classId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable UUID schoolId, @PathVariable UUID classId) {
        service.softDeleteClass(schoolId, classId, currentActor.id());
    }

    @PostMapping("/{classId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateClass(@PathVariable UUID schoolId, @PathVariable UUID classId) {
        service.reactivateClass(schoolId, classId, currentActor.id());
    }

    public record CreateClassRequest(@NotBlank String name, @Min(1) @Max(13) int gradeLevel, @NotBlank String schoolYear) {}
    public record CreateClassResponse(@NotNull UUID classId) {}
}
