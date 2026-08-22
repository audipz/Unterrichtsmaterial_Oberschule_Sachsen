package de.schule.informatik.lernplattform.app;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.displayname.DisplayNameConflictPort;
import de.schule.informatik.lernplattform.domain.schoolclass.MembershipClassAdministrationPort;
import de.schule.informatik.lernplattform.domain.schoolclass.MembershipClassAdministrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MembershipClassServiceConfiguration {

    @Bean
    MembershipClassAdministrationService membershipClassAdministrationService(
            MembershipClassAdministrationPort port,
            DisplayNameConflictPort displayNameConflictPort,
            SchoolAuthorizationPort authorizationPort) {
        return new MembershipClassAdministrationService(port, displayNameConflictPort, authorizationPort);
    }
}
