package de.schule.informatik.lernplattform.domain.school;

import java.util.UUID;

public interface SchoolLookupPort {
    UUID requireActiveSchoolId(String schoolSlug);
}
