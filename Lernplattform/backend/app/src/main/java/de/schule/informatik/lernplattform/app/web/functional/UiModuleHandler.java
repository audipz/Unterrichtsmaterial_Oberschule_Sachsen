package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UiModuleHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;
    private final Path artifactRoot;

    public UiModuleHandler(JdbcTemplate jdbc,
                           CurrentActor currentActor,
                           @Value("${lernplattform.ui-modules.artifact-root:/opt/lernplattform/ui-artifacts}") String artifactRoot) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
        this.artifactRoot = Path.of(artifactRoot).toAbsolutePath().normalize();
    }

    public ServerResponse resolve(ServerRequest request) {
        AuthorizedModule authorized = authorize(request);
        if (authorized == null) return ServerResponse.notFound().build();

        Module module = authorized.module();
        String schoolSlug = authorized.schoolSlug();
        String artifactUrl = "/api/v1/ui-modules/" + module.id() + "/artifact";
        if (schoolSlug != null) artifactUrl += "?school=" + schoolSlug;

        Map<String, String> response = new LinkedHashMap<>();
        response.put("moduleId", module.id().toString());
        response.put("version", module.version());
        response.put("artifactUrl", artifactUrl);
        response.put("integrity", module.integrity() == null ? "" : module.integrity());
        return ServerResponse.ok().body(response);
    }

    public ServerResponse artifact(ServerRequest request) {
        AuthorizedModule authorized = authorize(request);
        if (authorized == null) return ServerResponse.notFound().build();

        Path file = resolveArtifactPath(authorized.module().artifactPath());
        FileSystemResource resource = new FileSystemResource(file);
        if (!resource.exists() || !resource.isReadable()) {
            return ServerResponse.notFound().build();
        }
        return ServerResponse.ok()
                .contentType(MediaType.valueOf("text/javascript"))
                .header("Cache-Control", "private, max-age=300")
                .body(resource);
    }

    private AuthorizedModule authorize(ServerRequest request) {
        UUID moduleId;
        try {
            moduleId = UUID.fromString(request.pathVariable("moduleId"));
        } catch (IllegalArgumentException ex) {
            return null;
        }

        Module module = loadModule(moduleId);
        if (module == null) return null;

        UUID accountId = currentActor.id();
        String accountType = loadAccountType(accountId);
        if (!module.accountType().equals(accountType)) return null;

        if (accountType.equals("SYSTEM")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return hasAuthority(authentication, "SYSTEM_ADMIN")
                    ? new AuthorizedModule(module, null)
                    : null;
        }

        String schoolSlug = request.param("school").orElse(null);
        if (schoolSlug == null || schoolSlug.isBlank() || !hasActiveSchoolMembership(accountId, schoolSlug)) {
            return null;
        }
        if (module.requiredSchoolRole() != null
                && !hasSchoolRole(accountId, schoolSlug, module.requiredSchoolRole())) {
            return null;
        }
        return new AuthorizedModule(module, schoolSlug);
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
                rs.getString("version")), moduleId).stream().findFirst().orElse(null);
    }

    private Path resolveArtifactPath(String storedPath) {
        String relative = storedPath.startsWith("/ui-artifacts/")
                ? storedPath.substring("/ui-artifacts/".length())
                : storedPath;
        Path resolved = artifactRoot.resolve(relative).normalize();
        if (!resolved.startsWith(artifactRoot)) {
            throw new IllegalStateException("UI module artifact path escapes configured root");
        }
        return resolved;
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
                    where sm.account_id = ? and lower(s.slug) = lower(?)
                      and sm.status = 'ACTIVE' and sm.deleted_at is null and s.status = 'ACTIVE'
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
                    where sm.account_id = ? and lower(s.slug) = lower(?)
                      and sm.status = 'ACTIVE' and sm.deleted_at is null
                      and s.status = 'ACTIVE' and sr.role = ?
                )
                """, Boolean.class, accountId, schoolSlug, role);
        return Boolean.TRUE.equals(exists);
    }

    private static boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

    private record Module(UUID id, String artifactPath, String integrity,
                          String accountType, String requiredSchoolRole, String version) {}
    private record AuthorizedModule(Module module, String schoolSlug) {}
}
