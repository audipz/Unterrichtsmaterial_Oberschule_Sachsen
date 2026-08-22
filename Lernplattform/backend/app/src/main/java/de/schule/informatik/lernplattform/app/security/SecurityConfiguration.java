package de.schule.informatik.lernplattform.app.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfiguration {

    private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "TRACE", "OPTIONS");

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            SystemAdminSessionFilter systemAdminSessionFilter,
                                            StudentSessionFilter studentSessionFilter) throws Exception {
        CookieCsrfTokenRepository csrfRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfRepository.setCookiePath("/");
        RequestMatcher cookieSessionCsrf = SecurityConfiguration::requiresCookieSessionCsrf;

        return http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfRepository)
                        .requireCsrfProtectionMatcher(cookieSessionCsrf))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/health/**", "/actuator/info").permitAll()
                        .requestMatchers(
                                "/api/v1/public/school-registrations",
                                "/api/v1/public/school-registrations/verify",
                                "/api/v1/system-admin/auth/login",
                                "/api/v1/schools/*/student-auth/login").permitAll()
                        .requestMatchers(
                                "/api/v1/system-admin/me",
                                "/api/v1/system-admin/auth/csrf",
                                "/api/v1/system-admin/auth/change-password",
                                "/api/v1/system-admin/auth/logout")
                            .hasAnyAuthority("SYSTEM_ADMIN", "SYSTEM_ADMIN_PASSWORD_CHANGE_REQUIRED")
                        .requestMatchers("/api/v1/student-auth/csrf", "/api/v1/student-auth/change-password", "/api/v1/student-auth/logout")
                            .hasAnyAuthority("STUDENT", "STUDENT_PASSWORD_CHANGE_REQUIRED")
                        .requestMatchers("/api/v1/system-admin/**").hasAuthority("SYSTEM_ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())
                .addFilterBefore(systemAdminSessionFilter, BearerTokenAuthenticationFilter.class)
                .addFilterBefore(studentSessionFilter, BearerTokenAuthenticationFilter.class)
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()))
                .build();
    }

    private static boolean requiresCookieSessionCsrf(HttpServletRequest request) {
        if (SAFE_METHODS.contains(request.getMethod())) return false;
        String path = request.getRequestURI();
        if (path.equals("/api/v1/system-admin/auth/login") || path.matches("/api/v1/schools/[^/]+/student-auth/login")) return false;
        return path.startsWith("/api/v1/system-admin/") || path.startsWith("/api/v1/student-auth/");
    }
}
