package de.schule.informatik.lernplattform.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SystemAdminSessionFilter extends OncePerRequestFilter {

    public static final String COOKIE_NAME = "LP_SYSTEM_ADMIN";

    private final SystemAdminSessionService sessions;

    public SystemAdminSessionFilter(SystemAdminSessionService sessions) {
        this.sessions = sessions;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = cookie(request, COOKIE_NAME);
            sessions.authenticate(token).ifPresent(identity -> {
                String authority = identity.mustChangePassword()
                        ? "SYSTEM_ADMIN_PASSWORD_CHANGE_REQUIRED"
                        : "SYSTEM_ADMIN";
                var authentication = UsernamePasswordAuthenticationToken.authenticated(
                        identity.accountId(), null, List.of(new SimpleGrantedAuthority(authority)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }
        filterChain.doFilter(request, response);
    }

    public static String cookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
