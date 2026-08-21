package de.schule.informatik.lernplattform.domain.user;

public interface PasswordHashPort {

    String hash(String rawPassword);
}
