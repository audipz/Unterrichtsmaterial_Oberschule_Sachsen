package de.schule.informatik.lernplattform.materialimport;

import java.util.LinkedHashMap;
import java.util.Map;

public record MarkdownSource(Map<String, String> metadata, String body) {

    public static MarkdownSource parse(String raw) {
        if (raw == null) return new MarkdownSource(Map.of(), "");
        String normalized = raw.replace("\r\n", "\n");
        if (!normalized.startsWith("---\n")) {
            return new MarkdownSource(Map.of(), normalized);
        }
        int end = normalized.indexOf("\n---\n", 4);
        if (end < 0) throw new IllegalArgumentException("Ungeschlossenes Markdown-Frontmatter");
        Map<String, String> metadata = new LinkedHashMap<>();
        for (String line : normalized.substring(4, end).split("\n")) {
            if (line.isBlank() || line.stripLeading().startsWith("#")) continue;
            int colon = line.indexOf(':');
            if (colon <= 0) throw new IllegalArgumentException("Ungültiges Frontmatter: " + line);
            String key = line.substring(0, colon).trim();
            String value = line.substring(colon + 1).trim();
            if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
                value = value.substring(1, value.length() - 1);
            }
            metadata.put(key, value);
        }
        return new MarkdownSource(Map.copyOf(metadata), normalized.substring(end + 5));
    }
}
