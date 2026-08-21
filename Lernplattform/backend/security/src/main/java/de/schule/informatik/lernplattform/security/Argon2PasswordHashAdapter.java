package de.schule.informatik.lernplattform.security;

import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordHashAdapter implements PasswordHashPort {

    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Override
    public String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Passwort darf nicht leer sein.");
        }
        return encoder.encode(rawPassword);
    }
}
