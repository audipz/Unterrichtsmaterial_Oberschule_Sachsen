package de.schule.informatik.lernplattform.domain.displayname;

import java.security.SecureRandom;
import java.util.List;

public final class DisplayNameGenerator {

    private static final List<String> PREFIXES = List.of(
            "Pixel", "Code", "Daten", "Logik", "Byte", "Netz", "Algo", "Bit"
    );

    private static final List<String> SUFFIXES = List.of(
            "Fuchs", "Otter", "Falke", "Panda", "Luchs", "Dachs", "Rabe", "Igel"
    );

    private final SecureRandom random;

    public DisplayNameGenerator() {
        this(new SecureRandom());
    }

    DisplayNameGenerator(SecureRandom random) {
        this.random = random;
    }

    public String generate() {
        String prefix = PREFIXES.get(random.nextInt(PREFIXES.size()));
        String suffix = SUFFIXES.get(random.nextInt(SUFFIXES.size()));
        int number = 10 + random.nextInt(90);
        return prefix + suffix + number;
    }
}
