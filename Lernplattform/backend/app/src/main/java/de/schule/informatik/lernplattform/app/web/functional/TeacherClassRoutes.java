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
public class TeacherClassRoutes {

    @Bean
    RouterFunction<ServerResponse> teacherClassRoutes(TeacherClassHandler handler,
                                                       TeacherStudentHandler studentHandler) {
        var json = accept(MediaType.APPLICATION_JSON).and(contentType(MediaType.APPLICATION_JSON));
        return route()
                .GET("/api/v1/schools/{school}/classes", handler::listClasses)
                .GET("/api/v1/schools/{school}/students", studentHandler::listStudents)
                .GET("/api/v1/schools/{school}/classes/{classId}/students", handler::listStudents)
                .GET("/api/v1/schools/{school}/classes/{classId}/teachers", handler::listTeachers)
                .GET("/api/v1/schools/{school}/teachers", handler::listAvailableTeachers)
                .POST("/api/v1/schools/{school}/classes/{classId}/teachers", json, handler::addTeacher)
                .DELETE("/api/v1/schools/{school}/classes/{classId}/teachers/{membershipId}", handler::removeTeacher)
                .build();
    }
}
