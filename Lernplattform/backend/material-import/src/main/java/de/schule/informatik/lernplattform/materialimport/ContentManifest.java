package de.schule.informatik.lernplattform.materialimport;

import java.util.List;

public record ContentManifest(
        String releaseKey,
        String manifestKey,
        String title,
        String language,
        String fallbackLanguage,
        String federalState,
        String schoolType,
        Integer grade,
        String subject,
        List<Entry> entries) {

    public record Entry(
            String key,
            String path,
            String type,
            String parentKey,
            Boolean required) {}
}
