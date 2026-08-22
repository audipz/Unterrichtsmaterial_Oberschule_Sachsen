package de.schule.informatik.lernplattform.app.web.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class SystemAdminRoutes {

    @Bean
    RouterFunction<ServerResponse> systemAdminAuthRoutes(SystemAdminAuthHandler handler) {
        var json = accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON));
        return route()
                .POST("/api/v1/system-admin/auth/login", json, handler::login)
                .GET("/api/v1/system-admin/me", handler::me)
                .GET("/api/v1/system-admin/auth/csrf", handler::csrf)
                .POST("/api/v1/system-admin/auth/change-password", json, handler::changePassword)
                .POST("/api/v1/system-admin/auth/logout", handler::logout)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> systemAdminRegistrationRoutes(SystemAdminRegistrationHandler handler) {
        var json = accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON));
        return route()
                .GET("/api/v1/system-admin/school-registrations", handler::listPending)
                .POST("/api/v1/system-admin/school-registrations/{requestId}/approve", handler::approve)
                .POST("/api/v1/system-admin/school-registrations/{requestId}/reject", json, handler::reject)
                .build();
    }
}
