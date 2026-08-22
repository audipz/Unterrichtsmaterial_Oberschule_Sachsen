package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.app.security.SystemAdminSessionFilter;
import de.schule.informatik.lernplattform.app.security.SystemAdminSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

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

    public ServerResponse csrf(ServerRequest request) {
        CsrfToken token = (CsrfToken) request.servletRequest().getAttribute(CsrfToken.class.getName());
        return ServerResponse.ok().body(Map.of("token", token.getToken()));
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
