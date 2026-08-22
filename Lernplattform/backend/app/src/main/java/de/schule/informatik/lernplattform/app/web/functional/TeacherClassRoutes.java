package de.schule.informatik.lernplattform.app.web.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class TeacherClassRoutes {

    @Bean
    RouterFunction<ServerResponse> teacherClassRoutes(TeacherClassHandler handler) {
        return route()
                .GET("/api/v1/schools/{school}/classes", handler::listClasses)
                .GET("/api/v1/schools/{school}/classes/{classId}/students", handler::listStudents)
                .build();
    }
}
