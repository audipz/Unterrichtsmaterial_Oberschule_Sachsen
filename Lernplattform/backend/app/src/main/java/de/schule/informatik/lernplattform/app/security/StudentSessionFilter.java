package de.schule.informatik.lernplattform.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class StudentSessionFilter extends OncePerRequestFilter {
    public static final String COOKIE_NAME = "LP_STUDENT";
    private final StudentSessionService sessions;

    public StudentSessionFilter(StudentSessionService sessions) { this.sessions = sessions; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = SystemAdminSessionFilter.cookie(request, COOKIE_NAME);
            sessions.authenticate(token).ifPresent(identity -> {
                String authority = identity.mustChangePassword() ? "STUDENT_PASSWORD_CHANGE_REQUIRED" : "STUDENT";
                SecurityContextHolder.getContext().setAuthentication(
                        UsernamePasswordAuthenticationToken.authenticated(identity.accountId(), null,
                                List.of(new SimpleGrantedAuthority(authority))));
            });
        }
        filterChain.doFilter(request, response);
    }
}
