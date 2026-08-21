package de.schule.informatik.lernplattform.app;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationPort;
import de.schule.informatik.lernplattform.domain.schoolclass.ClassAdministrationService;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminRolePort;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminRoleService;
import de.schule.informatik.lernplattform.domain.user.UserLifecyclePort;
import de.schule.informatik.lernplattform.domain.user.UserLifecycleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfiguration {

    @Bean
    ClassAdministrationService classAdministrationService(ClassAdministrationPort classAdministrationPort,
                                                          DisplayNameConflictPort displayNameConflictPort,
                                                          SchoolAuthorizationPort authorizationPort) {
        return new ClassAdministrationService(classAdministrationPort, displayNameConflictPort, authorizationPort);
    }

    @Bean
    UserLifecycleService userLifecycleService(UserLifecyclePort userLifecyclePort,
                                              SchoolAuthorizationPort authorizationPort) {
        return new UserLifecycleService(userLifecyclePort, authorizationPort);
    }

    @Bean
    SchoolAdminRoleService schoolAdminRoleService(SchoolAdminRolePort schoolAdminRolePort,
                                                  SchoolAuthorizationPort authorizationPort) {
        return new SchoolAdminRoleService(schoolAdminRolePort, authorizationPort);
    }
}
