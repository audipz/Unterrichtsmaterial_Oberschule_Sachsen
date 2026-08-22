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
public class PublicRoutes {

    @Bean
    RouterFunction<ServerResponse> publicRegistrationRoutes(PublicRegistrationHandler handler) {
        var json = accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON));
        return route()
                .POST("/api/v1/public/school-registrations", json, handler::submitSchoolRegistration)
                .POST("/api/v1/public/school-registrations/verify", json, handler::verifyEmail)
                .build();
    }
}
