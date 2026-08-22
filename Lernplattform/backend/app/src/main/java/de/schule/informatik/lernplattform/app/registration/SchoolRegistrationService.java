package de.schule.informatik.lernplattform.app.registration;

import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequest;
import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequestPort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Locale;
import java.util.UUID;

@Service
public class SchoolRegistrationService {

    private final SchoolRegistrationRequestPort port;

    public SchoolRegistrationService(SchoolRegistrationRequestPort port) {
        this.port = port;
    }

    public UUID submit(Command command) {
        requireBlank(command.website(), "automation trap");

        String schoolName = requireText(command.schoolName(), 200, "schoolName");
        String schoolType = requireText(command.schoolType(), 60, "schoolType").toUpperCase(Locale.ROOT);
        String federalState = requireText(command.federalState(), 60, "federalState").toUpperCase(Locale.ROOT);
        String city = requireText(command.city(), 160, "city");
        String contactEmail = requireText(command.contactEmail(), 320, "contactEmail").toLowerCase(Locale.ROOT);
        String schoolWebsite = optionalText(command.schoolWebsite(), 500, "schoolWebsite");

        if (!contactEmail.contains("@") || contactEmail.startsWith("@") || contactEmail.endsWith("@")) {
            throw new IllegalArgumentException("contactEmail is invalid");
        }

        UUID id = UUID.randomUUID();
        String nonce = UUID.randomUUID().toString();
        SchoolRegistrationRequest request = new SchoolRegistrationRequest(
                id,
                schoolName,
                schoolType,
                federalState,
                city,
                contactEmail,
                contactEmail,
                schoolWebsite,
                null,
                nonce,
                null,
                sha256(command.userAgent()),
                Instant.now());
        port.save(request);
        return id;
    }

    private static String requireText(String value, int maxLength, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new IllegalArgumentException(field + " is too long");
        }
        return trimmed;
    }

    private static String optionalText(String value, int maxLength, String field) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return requireText(value, maxLength, field);
    }

    private static void requireBlank(String value, String field) {
        if (value != null && !value.isBlank()) {
            throw new IllegalArgumentException(field + " rejected");
        }
    }

    private static String sha256(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    public record Command(
            String schoolName,
            String schoolType,
            String federalState,
            String city,
            String contactEmail,
            String schoolWebsite,
            String website,
            String userAgent) {
    }
}
