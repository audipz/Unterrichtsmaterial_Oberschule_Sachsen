package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import de.schule.informatik.lernplattform.app.security.StudentSessionFilter;
import de.schule.informatik.lernplattform.app.security.StudentSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Duration;
import java.util.Map;

@Component
public class StudentAuthHandler {
    private final StudentSessionService sessions;
    private final CurrentActor currentActor;

    public StudentAuthHandler(StudentSessionService sessions, CurrentActor currentActor) {
        this.sessions = sessions;
        this.currentActor = currentActor;
    }

    public ServerResponse login(ServerRequest request) throws Exception {
        LoginRequest body = request.body(LoginRequest.class);
        var result = sessions.login(request.pathVariable("school"), body.username(), body.password());
        HttpServletResponse response = request.servletRequest().getAttribute("jakarta.servlet.response") instanceof HttpServletResponse r ? r : null;
        if (response == null) throw new IllegalStateException("Servlet response unavailable");
        response.addHeader("Set-Cookie", cookie(result.token(), Duration.between(java.time.Instant.now(), result.expiresAt()).toSeconds()));
        return ServerResponse.ok().body(Map.of("mustChangePassword", result.mustChangePassword(), "expiresAt", result.expiresAt().toString()));
    }

    public ServerResponse changePassword(ServerRequest request) throws Exception {
        PasswordRequest body = request.body(PasswordRequest.class);
        sessions.changePassword(currentActor.id(), body.currentPassword(), body.newPassword());
        return ServerResponse.noContent().build();
    }

    public ServerResponse logout(ServerRequest request) {
        HttpServletRequest servlet = request.servletRequest();
        sessions.logout(StudentSessionFilterCookie.cookie(servlet));
        return ServerResponse.noContent().header("Set-Cookie", expiredCookie()).build();
    }

    private static String cookie(String token, long maxAge) {
        return StudentSessionFilter.COOKIE_NAME + "=" + token + "; Path=/; Max-Age=" + Math.max(0, maxAge) + "; HttpOnly; Secure; SameSite=Strict";
    }
    private static String expiredCookie() {
        return StudentSessionFilter.COOKIE_NAME + "=; Path=/; Max-Age=0; HttpOnly; Secure; SameSite=Strict";
    }

    public record LoginRequest(String username, String password) {}
    public record PasswordRequest(String currentPassword, String newPassword) {}

    private static final class StudentSessionFilterCookie {
        static String cookie(HttpServletRequest request) {
            return de.schule.informatik.lernplattform.app.security.SystemAdminSessionFilter.cookie(request, StudentSessionFilter.COOKIE_NAME);
        }
    }
}
