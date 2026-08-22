package de.schule.informatik.lernplattform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LernplattformApplication {

    public static void main(String[] args) {
        boolean migrationOnly = Boolean.parseBoolean(
                System.getenv().getOrDefault("MIGRATION_ONLY", "false"));

        SpringApplication application = new SpringApplication(LernplattformApplication.class);
        if (migrationOnly) {
            application.setWebApplicationType(WebApplicationType.NONE);
        }

        var context = application.run(args);
        if (migrationOnly) {
            context.close();
        }
    }
}
