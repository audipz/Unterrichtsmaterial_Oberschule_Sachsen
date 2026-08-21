package de.schule.informatik.lernplattform.domain.auth;

import java.util.UUID;

public interface SchoolAuthorizationPort {

    void requireSchoolAdmin(UUID actorId, UUID schoolId);

    void requireSystemAdmin(UUID actorId);
}
