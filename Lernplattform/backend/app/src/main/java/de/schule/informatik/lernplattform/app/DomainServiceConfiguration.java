package de.schule.informatik.lernplattform.app;

import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationPort;
import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationService;
import de.schule.informatik.lernplattform.domain.user.UserLifecyclePort;
import de.schule.informatik.lernplattform.domain.user.UserLifecycleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfiguration {

    @Bean
    ClassAdministrationService classAdministrationService(ClassAdministrationPort classAdministrationPort,
                                                          DisplayNameConflictPort displayNameConflictPort) {
        return new ClassAdministrationService(classAdministrationPort, displayNameConflictPort);
    }

    @Bean
    UserLifecycleService userLifecycleService(UserLifecyclePort userLifecyclePort) {
        return new UserLifecycleService(userLifecyclePort);
    }
}
