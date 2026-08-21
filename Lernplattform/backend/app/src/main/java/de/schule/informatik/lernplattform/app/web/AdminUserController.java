package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.app.user.UserProvisioningService;
import de.schule.informatik.lernplattform.domain.user.CreateUserCommand;
import de.schule.informatik.lernplattform.domain.user.UserLifecycleService;
import de.schule.informatik.lernplattform.domain.user.UserRole;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/admin/schools/{schoolId}/users")
public class AdminUserController {

    private final UserProvisioningService provisioningService;
    private final UserLifecycleService lifecycleService;

    public AdminUserController(UserProvisioningService provisioningService,
                               UserLifecycleService lifecycleService) {
        this.provisioningService = provisioningService;
        this.lifecycleService = lifecycleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse create(@PathVariable UUID schoolId,
                                     @RequestParam UUID actorId,
                                     @Valid @RequestBody CreateUserRequest request) {
        var result = provisioningService.create(new CreateUserCommand(
                schoolId,
                request.username(),
                request.initialPassword(),
                request.roles(),
                request.visibleClassIds() == null ? Set.of() : request.visibleClassIds(),
                actorId
        ));
        return new CreateUserResponse(result.userId(), result.username(), result.displayName());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void studentLeavesSchool(@PathVariable UUID schoolId,
                                    @PathVariable UUID userId,
                                    @RequestParam UUID actorId,
                                    @RequestParam(required = false) LocalDate effectiveDate) {
        lifecycleService.studentLeavesSchool(schoolId, userId, effectiveDate, actorId);
    }

    @PostMapping("/{userId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivate(@PathVariable UUID schoolId,
                           @PathVariable UUID userId,
                           @RequestParam UUID actorId) {
        lifecycleService.reactivate(schoolId, userId, actorId);
    }

    public record CreateUserRequest(
            @NotBlank String username,
            @NotBlank String initialPassword,
            @NotEmpty Set<UserRole> roles,
            Set<UUID> visibleClassIds
    ) {}

    public record CreateUserResponse(
            @NotNull UUID userId,
            @NotBlank String username,
            @NotBlank String displayName
    ) {}
}
