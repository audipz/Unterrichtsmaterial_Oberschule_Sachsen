package de.schule.informatik.lernplattform.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentActor {

    public UUID id() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Nicht authentifiziert.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID id) {
            return id;
        }
        if (principal instanceof Jwt jwt) {
            try {
                return UUID.fromString(jwt.getSubject());
            } catch (RuntimeException ex) {
                throw new SecurityException("Ungültige Benutzer-ID im Authentifizierungstoken.");
            }
        }
        throw new SecurityException("Ungültige Authentifizierungsidentität.");
    }
}
