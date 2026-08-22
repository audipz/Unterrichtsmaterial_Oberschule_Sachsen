package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.UUID;

@Component
public class UiModuleHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public UiModuleHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    public ServerResponse resolve(ServerRequest request) {
        UUID moduleId = UUID.fromString(request.pathVariable("moduleId"));
        UUID accountId = currentActor.id();
        Module module = loadModule(moduleId);
        String accountType = loadAccountType(accountId);

        if (!module.accountType().equals(accountType)) {
            return ServerResponse.notFound().build();
        }

        if (accountType.equals("SYSTEM")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!hasAuthority(authentication, "SYSTEM_ADMIN")) {
                return ServerResponse.notFound().build();
            }
        } else {
            String schoolSlug = request.param("school").orElse(null);
            if (schoolSlug == null || schoolSlug.isBlank() || !hasActiveSchoolMembership(accountId, schoolSlug)) {
                return ServerResponse.notFound().build();
            }
            if (module.requiredSchoolRole() != null
                    && !hasSchoolRole(accountId, schoolSlug, module.requiredSchoolRole())) {
                return ServerResponse.notFound().build();
            }
        }

        return ServerResponse.ok().body(Map.of(
                "moduleId", module.id().toString(),
                "version", module.version(),
                "artifactUrl", "/api/v1/ui-modules/" + module.id() + "/artifact",
                "integrity", module.integrity() == null ? "" : module.integrity()));
    }

    private Module loadModule(UUID moduleId) {
        return jdbc.query("""
                select id, artifact_path, integrity_sha384, account_type, required_school_role, version
                from ui_module
                where id = ? and status = 'ACTIVE'
                """, (rs, rowNum) -> new Module(
                rs.getObject("id", UUID.class),
                rs.getString("artifact_path"),
                rs.getString("integrity_sha384"),
                rs.getString("account_type"),
                rs.getString("required_school_role"),
                rs.getString("version")), moduleId).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown module"));
    }

    private String loadAccountType(UUID accountId) {
        return jdbc.queryForObject("""
                select account_type from account
                where id = ? and status = 'ACTIVE' and deleted_at is null
                """, String.class, accountId);
    }

    private boolean hasActiveSchoolMembership(UUID accountId, String schoolSlug) {
        Boolean exists = jdbc.queryForObject("""
                select exists(
                    select 1
                    from school_membership sm
                    join school s on s.id = sm.school_id
                    where sm.account_id = ?
                      and lower(s.slug) = lower(?)
                      and sm.status = 'ACTIVE' and sm.deleted_at is null
                      and s.status = 'ACTIVE'
                )
                """, Boolean.class, accountId, schoolSlug);
        return Boolean.TRUE.equals(exists);
    }

    private boolean hasSchoolRole(UUID accountId, String schoolSlug, String role) {
        Boolean exists = jdbc.queryForObject("""
                select exists(
                    select 1
                    from school_membership sm
                    join school s on s.id = sm.school_id
                    join school_role sr on sr.school_membership_id = sm.id
                    where sm.account_id = ?
                      and lower(s.slug) = lower(?)
                      and sm.status = 'ACTIVE' and sm.deleted_at is null
                      and s.status = 'ACTIVE'
                      and sr.role = ?
                )
                """, Boolean.class, accountId, schoolSlug, role);
        return Boolean.TRUE.equals(exists);
    }

    private static boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

    private record Module(
            UUID id,
            String artifactPath,
            String integrity,
            String accountType,
            String requiredSchoolRole,
            String version) {}
}
