package de.schule.informatik.lernplattform.domain.displayname;

import java.text.Normalizer;
import java.util.Locale;

public final class DisplayNameNormalizer {

    public String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("displayName must not be blank");
        }

        return Normalizer.normalize(value.trim(), Normalizer.Form.NFKC)
                .toLowerCase(Locale.ROOT);
    }
}
