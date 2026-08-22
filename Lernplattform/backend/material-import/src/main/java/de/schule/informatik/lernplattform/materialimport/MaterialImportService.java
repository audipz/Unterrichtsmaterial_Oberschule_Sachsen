package de.schule.informatik.lernplattform.materialimport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class MaterialImportService {
    private final JdbcTemplate jdbc;
    private final YAMLMapper yaml = new YAMLMapper();

    public MaterialImportService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public ImportResult importAndPublish(Path root, Path manifestFile) throws IOException {
        Path normalizedRoot = root.toAbsolutePath().normalize();
        Path normalizedManifest = manifestFile.toAbsolutePath().normalize();
        if (!normalizedManifest.startsWith(normalizedRoot)) throw new IllegalArgumentException("Manifest liegt außerhalb des Importverzeichnisses");

        ContentManifest manifest = yaml.readValue(Files.readString(normalizedManifest, StandardCharsets.UTF_8), ContentManifest.class);
        validateManifest(manifest);

        UUID releaseId = UUID.randomUUID();
        jdbc.update("insert into content_release (id, release_key, status) values (?, ?, 'DRAFT')", releaseId, manifest.releaseKey());

        UUID manifestId = UUID.randomUUID();
        jdbc.update("""
                insert into content_manifest (
                    id, manifest_key, content_release_id, language_tag, fallback_language_tag,
                    federal_state_key, school_type_key, grade_level, subject_key, title
                ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, manifestId, manifest.manifestKey(), releaseId,
                value(manifest.language(), "de-DE"), emptyToNull(manifest.fallbackLanguage()),
                key(manifest.federalState()), key(manifest.schoolType()), manifest.grade(),
                key(value(manifest.subject(), "INFORMATIK")), manifest.title().trim());

        int position = 0;
        for (ContentManifest.Entry entry : manifest.entries()) {
            Path sourcePath = normalizedRoot.resolve(entry.path()).normalize();
            if (!sourcePath.startsWith(normalizedRoot)) throw new IllegalArgumentException("Pfad verlässt Importverzeichnis: " + entry.path());
            MarkdownSource source = MarkdownSource.parse(Files.readString(sourcePath, StandardCharsets.UTF_8));

            UUID itemId = findOrCreateItem(entry, source);
            String language = value(source.metadata().get("language"), value(manifest.language(), "de-DE"));
            String title = value(source.metadata().get("title"), firstHeading(source.body(), entry.key()));
            jdbc.update("""
                    insert into content_variant (
                        id, content_item_id, content_release_id, language_tag, title,
                        body_markdown, content_hash, metadata
                    ) values (?, ?, ?, ?, ?, ?, ?, '{}'::jsonb)
                    """, UUID.randomUUID(), itemId, releaseId, language, title, source.body(), sha256(source.body()));

            upsertTarget(itemId, manifest, source.metadata());
            UUID parentId = entry.parentKey() == null || entry.parentKey().isBlank() ? null : findItemId(entry.parentKey());
            jdbc.update("""
                    insert into content_manifest_entry (
                        manifest_id, content_item_id, position, parent_content_item_id, required
                    ) values (?, ?, ?, ?, ?)
                    """, manifestId, itemId, position++, parentId, entry.required() == null || entry.required());
        }

        jdbc.update("update content_release set status = 'RETIRED' where status = 'PUBLISHED' and release_key <> ?", manifest.releaseKey());
        jdbc.update("update content_release set status = 'PUBLISHED', published_at = ? where id = ?", OffsetDateTime.now(), releaseId);
        return new ImportResult(releaseId, manifestId, manifest.entries().size());
    }

    private UUID findOrCreateItem(ContentManifest.Entry entry, MarkdownSource source) {
        var ids = jdbc.query("select id from content_item where content_key = ?", (rs, n) -> rs.getObject(1, UUID.class), entry.key());
        if (!ids.isEmpty()) return ids.getFirst();
        UUID id = UUID.randomUUID();
        jdbc.update("insert into content_item (id, content_key, content_type, subject_key) values (?, ?, ?, ?)",
                id, entry.key(), key(value(entry.type(), "TOPIC")), key(value(source.metadata().get("subject"), "INFORMATIK")));
        return id;
    }

    private void upsertTarget(UUID itemId, ContentManifest manifest, Map<String, String> metadata) {
        String federalState = key(value(metadata.get("federalState"), value(manifest.federalState(), "ALL")));
        String schoolType = key(value(metadata.get("schoolType"), value(manifest.schoolType(), "ALL")));
        Integer grade = metadata.containsKey("grade") ? Integer.valueOf(metadata.get("grade")) : manifest.grade();
        String subject = key(value(metadata.get("subject"), value(manifest.subject(), "INFORMATIK")));
        jdbc.update("""
                insert into content_target (id, content_item_id, federal_state_key, school_type_key, grade_level, subject_key)
                values (?, ?, ?, ?, ?, ?)
                on conflict (content_item_id, federal_state_key, school_type_key, grade_level, subject_key) do nothing
                """, UUID.randomUUID(), itemId, federalState, schoolType, grade, subject);
    }

    private UUID findItemId(String key) {
        UUID id = jdbc.queryForObject("select id from content_item where content_key = ?", UUID.class, key);
        if (id == null) throw new IllegalArgumentException("Unbekanntes parentKey: " + key);
        return id;
    }

    private static void validateManifest(ContentManifest manifest) {
        if (manifest == null || blank(manifest.releaseKey()) || blank(manifest.manifestKey()) || blank(manifest.title())) {
            throw new IllegalArgumentException("releaseKey, manifestKey und title sind Pflichtfelder");
        }
        if (manifest.entries() == null || manifest.entries().isEmpty()) throw new IllegalArgumentException("Manifest enthält keine Einträge");
        for (var entry : manifest.entries()) {
            if (blank(entry.key()) || blank(entry.path())) throw new IllegalArgumentException("Jeder Manifest-Eintrag braucht key und path");
        }
    }

    private static String firstHeading(String body, String fallback) {
        for (String line : body.split("\\R")) if (line.startsWith("# ")) return line.substring(2).trim();
        return fallback;
    }
    private static String sha256(String value) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
        catch (Exception e) { throw new IllegalStateException(e); }
    }
    private static String key(String value) { return value(value, "ALL").trim().toUpperCase(Locale.ROOT); }
    private static String value(String value, String fallback) { return value == null || value.isBlank() ? fallback : value.trim(); }
    private static String emptyToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private static boolean blank(String value) { return value == null || value.isBlank(); }

    public record ImportResult(UUID releaseId, UUID manifestId, int importedItems) {}
}
