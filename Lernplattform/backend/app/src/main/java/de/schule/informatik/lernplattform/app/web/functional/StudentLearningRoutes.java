package de.schule.informatik.lernplattform.app.web.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class StudentLearningRoutes {

    @Bean
    RouterFunction<ServerResponse> studentLearningRoutes(StudentLearningHandler handler) {
        return route()
                .GET("/api/v1/schools/{school}/learning", handler::learning)
                .build();
    }
}
