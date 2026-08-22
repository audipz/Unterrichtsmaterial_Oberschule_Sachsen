package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class StudentLearningHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public StudentLearningHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    public ServerResponse learning(ServerRequest request) {
        StudentContext context = loadContext(currentActor.id(), request.pathVariable("school"));
        if (context == null) return ServerResponse.notFound().build();

        String language = request.param("language").filter(v -> !v.isBlank()).orElse("de-DE");
        Manifest manifest = selectManifest(context, language);
        if (manifest == null && !"de-DE".equalsIgnoreCase(language)) {
            manifest = selectManifest(context, "de-DE");
        }
        if (manifest == null) {
            return ServerResponse.ok().body(Map.of(
                    "context", contextMap(context),
                    "language", language,
                    "manifest", Map.of(),
                    "items", List.of()));
        }

        List<Map<String, Object>> items = jdbc.query("""
                select ci.id, ci.content_key, ci.content_type, ci.subject_key,
                       coalesce(cv_requested.title, cv_fallback.title) as title,
                       coalesce(cv_requested.body_markdown, cv_fallback.body_markdown) as body_markdown,
                       coalesce(cv_requested.language_tag, cv_fallback.language_tag) as language_tag,
                       cme.position, cme.required, cme.parent_content_item_id
                from content_manifest_entry cme
                join content_item ci on ci.id = cme.content_item_id
                left join content_variant cv_requested
                  on cv_requested.content_item_id = ci.id
                 and cv_requested.content_release_id = ?
                 and lower(cv_requested.language_tag) = lower(?)
                left join content_variant cv_fallback
                  on cv_fallback.content_item_id = ci.id
                 and cv_fallback.content_release_id = ?
                 and lower(cv_fallback.language_tag) = lower(?)
                where cme.manifest_id = ?
                  and coalesce(cv_requested.id, cv_fallback.id) is not null
                  and exists (
                    select 1 from content_target ct
                    where ct.content_item_id = ci.id
                      and ct.subject_key = 'INFORMATIK'
                      and ct.federal_state_key in ('ALL', ?)
                      and ct.school_type_key in ('ALL', ?)
                      and (ct.grade_level is null or ct.grade_level = ?)
                  )
                order by cme.position
                """, (rs, rowNum) -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", rs.getObject("id", UUID.class).toString());
                    item.put("key", rs.getString("content_key"));
                    item.put("type", rs.getString("content_type"));
                    item.put("subject", rs.getString("subject_key"));
                    item.put("title", rs.getString("title"));
                    item.put("bodyMarkdown", rs.getString("body_markdown"));
                    item.put("language", rs.getString("language_tag"));
                    item.put("position", rs.getInt("position"));
                    item.put("required", rs.getBoolean("required"));
                    UUID parent = rs.getObject("parent_content_item_id", UUID.class);
                    item.put("parentId", parent == null ? null : parent.toString());
                    return item;
                }, manifest.releaseId(), language, manifest.releaseId(), manifest.fallbackLanguage(), manifest.id(),
                context.federalState(), context.schoolType(), context.gradeLevel());

        return ServerResponse.ok().body(Map.of(
                "context", contextMap(context),
                "language", language,
                "manifest", Map.of(
                        "id", manifest.id().toString(),
                        "title", manifest.title(),
                        "language", manifest.language(),
                        "fallbackLanguage", manifest.fallbackLanguage()),
                "items", items));
    }

    private StudentContext loadContext(UUID accountId, String schoolSlug) {
        return jdbc.query("""
                select s.id as school_id, s.slug, s.name, s.school_type, s.federal_state,
                       c.grade_level
                from account a
                join school_membership sm on sm.account_id = a.id
                join school s on s.id = sm.school_id
                left join school_class_membership scm
                  on scm.student_school_membership_id = sm.id
                 and scm.status = 'ACTIVE' and scm.deleted_at is null
                left join school_class c
                  on c.id = scm.school_class_id and c.status = 'ACTIVE' and c.deleted_at is null
                where a.id = ? and a.account_type = 'STUDENT'
                  and a.status = 'ACTIVE' and a.deleted_at is null
                  and lower(s.slug) = lower(?) and s.status = 'ACTIVE' and s.deleted_at is null
                  and sm.status = 'ACTIVE' and sm.deleted_at is null
                """, (rs, rowNum) -> new StudentContext(
                        rs.getObject("school_id", UUID.class),
                        rs.getString("slug"), rs.getString("name"),
                        rs.getString("school_type"), rs.getString("federal_state"),
                        rs.getObject("grade_level") == null ? null : rs.getInt("grade_level")),
                accountId, schoolSlug).stream().findFirst().orElse(null);
    }

    private Manifest selectManifest(StudentContext c, String language) {
        Integer grade = c.gradeLevel();
        return jdbc.query("""
                select cm.id, cm.content_release_id, cm.title, cm.language_tag,
                       coalesce(cm.fallback_language_tag, 'de-DE') as fallback_language_tag
                from content_manifest cm
                join content_release cr on cr.id = cm.content_release_id
                where cr.status = 'PUBLISHED'
                  and cm.subject_key = 'INFORMATIK'
                  and lower(cm.language_tag) = lower(?)
                  and cm.federal_state_key in ('ALL', ?)
                  and cm.school_type_key in ('ALL', ?)
                  and (cm.grade_level is null or cm.grade_level = ?)
                order by
                  case when cm.federal_state_key = ? then 1 else 0 end desc,
                  case when cm.school_type_key = ? then 1 else 0 end desc,
                  case when cm.grade_level = ? then 1 else 0 end desc,
                  cr.published_at desc nulls last,
                  cm.created_at desc
                limit 1
                """, (rs, rowNum) -> new Manifest(
                        rs.getObject("id", UUID.class),
                        rs.getObject("content_release_id", UUID.class),
                        rs.getString("title"), rs.getString("language_tag"),
                        rs.getString("fallback_language_tag")),
                language, c.federalState(), c.schoolType(), grade,
                c.federalState(), c.schoolType(), grade).stream().findFirst().orElse(null);
    }

    private static Map<String, Object> contextMap(StudentContext c) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("schoolId", c.schoolId().toString());
        result.put("schoolSlug", c.slug());
        result.put("schoolName", c.name());
        result.put("schoolType", c.schoolType());
        result.put("federalState", c.federalState());
        result.put("gradeLevel", c.gradeLevel());
        return result;
    }

    private record StudentContext(UUID schoolId, String slug, String name, String schoolType, String federalState, Integer gradeLevel) {}
    private record Manifest(UUID id, UUID releaseId, String title, String language, String fallbackLanguage) {}
}
