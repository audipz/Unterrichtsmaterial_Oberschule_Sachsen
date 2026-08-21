package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/schools/{schoolId}/teachers/{teacherId}/roles/school-admin")
public class AdminRoleController {

    private final SchoolAdminRoleService service;
    private final CurrentActor currentActor;

    public AdminRoleController(SchoolAdminRoleService service, CurrentActor currentActor) {
        this.service = service;
        this.currentActor = currentActor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void grant(@PathVariable UUID schoolId,
                      @PathVariable UUID teacherId) {
        service.grant(schoolId, teacherId, currentActor.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(@PathVariable UUID schoolId,
                       @PathVariable UUID teacherId) {
        service.revoke(schoolId, teacherId, currentActor.id());
    }
}
