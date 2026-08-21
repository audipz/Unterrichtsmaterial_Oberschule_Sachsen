package de.schule.informatik.lernplattform.domain.displayname;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DisplayNameNormalizerTest {

    private final DisplayNameNormalizer normalizer = new DisplayNameNormalizer();

    @Test
    void normalizesCaseAndWhitespace() {
        assertEquals("pixelfuchs", normalizer.normalize("  PixelFuchs  "));
    }

    @Test
    void rejectsBlankNames() {
        assertThrows(IllegalArgumentException.class, () -> normalizer.normalize("   "));
    }
}
