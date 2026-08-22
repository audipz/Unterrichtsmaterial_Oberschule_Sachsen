package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.app.security.SystemAdminSessionFilter;
import de.schule.informatik.lernplattform.app.security.SystemAdminSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;

@Component
public class SystemAdminAuthHandler {

    private final SystemAdminSessionService sessions;
    private final CurrentActor currentActor;
    private final boolean secureCookie;

    public SystemAdminAuthHandler(SystemAdminSessionService sessions,
                                  CurrentActor currentActor,
                                  @Value("${lernplattform.security.system-admin.secure-cookie:true}") boolean secureCookie) {
        this.sessions = sessions;
        this.currentActor = currentActor;
        this.secureCookie = secureCookie;
    }

    public ServerResponse login(ServerRequest request) throws Exception {
        LoginRequest body = request.body(LoginRequest.class);
        var result = sessions.login(body.username(), body.password());
        ResponseCookie cookie = ResponseCookie.from(SystemAdminSessionFilter.COOKIE_NAME, result.token())
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Strict")
                .path("/api/v1/system-admin")
                .maxAge(sessions.sessionLifetime())
                .build();
        return ServerResponse.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "mustChangePassword", result.mustChangePassword(),
                        "expiresAt", result.expiresAt().toString()));
    }

    public ServerResponse me(ServerRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean passwordChangeRequired = hasAuthority(authentication, "SYSTEM_ADMIN_PASSWORD_CHANGE_REQUIRED");

        if (passwordChangeRequired) {
            return ServerResponse.ok().body(Map.of(
                    "accountId", currentActor.id().toString(),
                    "accountType", "SYSTEM",
                    "capabilities", List.of("CHANGE_OWN_PASSWORD"),
                    "navigation", List.of(Map.of(
                            "id", "password",
                            "label", "Passwort ändern",
                            "route", "/system-admin/passwort"))));
        }

        return ServerResponse.ok().body(Map.of(
                "accountId", currentActor.id().toString(),
                "accountType", "SYSTEM",
                "capabilities", List.of(
                        "SCHOOL_REGISTRATION_REVIEW",
                        "SCHOOL_MANAGEMENT"),
                "navigation", List.of(
                        Map.of(
                                "id", "registrations",
                                "label", "Schulregistrierungen",
                                "route", "/system-admin/schulregistrierungen"),
                        Map.of(
                                "id", "schools",
                                "label", "Schulen",
                                "route", "/system-admin/schulen"))));
    }

    public ServerResponse csrf(ServerRequest request) {
        CsrfToken token = (CsrfToken) request.servletRequest().getAttribute(CsrfToken.class.getName());
        return ServerResponse.ok().body(Map.of(
                "token", token.getToken(),
                "headerName", token.getHeaderName()));
    }

    public ServerResponse changePassword(ServerRequest request) throws Exception {
        PasswordChangeRequest body = request.body(PasswordChangeRequest.class);
        sessions.changePassword(currentActor.id(), body.currentPassword(), body.newPassword());
        return clearCookie(ServerResponse.noContent()).build();
    }

    public ServerResponse logout(ServerRequest request) {
        sessions.logout(SystemAdminSessionFilter.cookie(request.servletRequest(), SystemAdminSessionFilter.COOKIE_NAME));
        return clearCookie(ServerResponse.noContent()).build();
    }

    private static boolean hasAuthority(Authentication authentication, String authority) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

    private ServerResponse.BodyBuilder clearCookie(ServerResponse.BodyBuilder builder) {
        ResponseCookie cookie = ResponseCookie.from(SystemAdminSessionFilter.COOKIE_NAME, "")
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Strict")
                .path("/api/v1/system-admin")
                .maxAge(0)
                .build();
        return builder.header(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public record LoginRequest(String username, String password) {}
    public record PasswordChangeRequest(String currentPassword, String newPassword) {}
}
