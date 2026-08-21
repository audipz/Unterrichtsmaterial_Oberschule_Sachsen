package de.schule.informatik.lernplattform.app.web;

import de.schule.informatik.lernplattform.domain.user.SchoolAdminRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/schools/{schoolId}/teachers/{teacherId}/roles/school-admin")
public class AdminRoleController {

    private final SchoolAdminRoleService service;

    public AdminRoleController(SchoolAdminRoleService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void grant(@PathVariable UUID schoolId,
                      @PathVariable UUID teacherId,
                      @RequestParam UUID actorId) {
        service.grant(schoolId, teacherId, actorId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(@PathVariable UUID schoolId,
                       @PathVariable UUID teacherId,
                       @RequestParam UUID actorId) {
        service.revoke(schoolId, teacherId, actorId);
    }
}
