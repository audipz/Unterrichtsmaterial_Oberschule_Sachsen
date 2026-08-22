package de.schule.informatik.lernplattform.app;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminMembershipRolePort;
import de.schule.informatik.lernplattform.domain.user.SchoolAdminMembershipRoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SchoolAdminRoleConfiguration {

    @Bean
    SchoolAdminMembershipRoleService schoolAdminMembershipRoleService(SchoolAdminMembershipRolePort port,
                                                                      SchoolAuthorizationPort authorizationPort) {
        return new SchoolAdminMembershipRoleService(port, authorizationPort);
    }
}
