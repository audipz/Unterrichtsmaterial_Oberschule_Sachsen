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
public class AdminRoutes {

    @Bean
    RouterFunction<ServerResponse> adminAccountRoutes(AdminAccountHandler handler) {
        return route()
                .path("/api/v1/schulen/{schoolSlug}/admin/accounts", builder -> builder
                        .POST("/students", accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON)), handler::createStudent)
                        .POST("/teachers", accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON)), handler::createTeacher)
                        .POST("/teachers/{teacherId}/school-membership", handler::addExistingTeacher))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> adminClassRoutes(AdminClassHandler handler) {
        return route()
                .path("/api/v1/schulen/{schoolSlug}/admin/classes", builder -> builder
                        .POST("", accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON)), handler::createClass)
                        .POST("/{classId}/students/{studentAccountId}", handler::addStudent)
                        .DELETE("/{classId}/students/{studentAccountId}", handler::removeStudent)
                        .POST("/{sourceClassId}/students/{studentAccountId}/move/{targetClassId}", handler::moveStudent)
                        .POST("/{classId}/teachers/{teacherAccountId}", handler::addTeacher)
                        .DELETE("/{classId}/teachers/{teacherAccountId}", handler::removeTeacher)
                        .DELETE("/{classId}", handler::deleteClass)
                        .POST("/{classId}/reactivate", handler::reactivateClass))
                .build();
    }
}
