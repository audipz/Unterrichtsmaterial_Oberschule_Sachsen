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
public class StudentMoveRoutes {

    @Bean
    RouterFunction<ServerResponse> studentMoveRoutes(StudentMoveHandler handler) {
        var json = accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON));
        return route()
                .POST("/api/v1/schools/{school}/students/{studentId}/move-class", json, handler::moveClass)
                .POST("/api/v1/system-admin/students/{studentId}/move-school", json, handler::moveSchool)
                .build();
    }
}
