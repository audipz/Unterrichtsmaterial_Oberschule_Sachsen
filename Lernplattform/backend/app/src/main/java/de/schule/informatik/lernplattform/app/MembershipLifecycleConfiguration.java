package de.schule.informatik.lernplattform.app;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import de.schule.informatik.lernplattform.domain.user.SchoolMembershipLifecyclePort;
import de.schule.informatik.lernplattform.domain.user.SchoolMembershipLifecycleService;
import de.schule.informatik.lernplattform.domain.user.TeacherSchoolMembershipPort;
import de.schule.informatik.lernplattform.domain.user.TeacherSchoolMembershipService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MembershipLifecycleConfiguration {

    @Bean
    SchoolMembershipLifecycleService schoolMembershipLifecycleService(SchoolMembershipLifecyclePort lifecyclePort,
                                                                      SchoolAuthorizationPort authorizationPort) {
        return new SchoolMembershipLifecycleService(lifecyclePort, authorizationPort);
    }

    @Bean
    TeacherSchoolMembershipService teacherSchoolMembershipService(TeacherSchoolMembershipPort membershipPort,
                                                                  SchoolAuthorizationPort authorizationPort) {
        return new TeacherSchoolMembershipService(membershipPort, authorizationPort);
    }
}
