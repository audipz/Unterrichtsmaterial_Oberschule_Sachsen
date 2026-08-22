package de.schule.informatik.lernplattform.app.web.functional;

import de.schule.informatik.lernplattform.app.security.CurrentActor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class MeHandler {

    private final JdbcTemplate jdbc;
    private final CurrentActor currentActor;

    public MeHandler(JdbcTemplate jdbc, CurrentActor currentActor) {
        this.jdbc = jdbc;
        this.currentActor = currentActor;
    }

    public ServerResponse me(ServerRequest request) {
        UUID accountId = currentActor.id();
        Account account = loadAccount(accountId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (account.type().equals("SYSTEM")) {
            return ServerResponse.ok().body(systemContext(account, authentication));
        }

        List<SchoolContext> schools = loadSchools(accountId);
        SchoolContext selected = selectSchool(request.param("school").orElse(null), schools);
        return ServerResponse.ok().body(schoolBoundContext(account, schools, selected));
    }

    private Map<String, Object> systemContext(Account account, Authentication authentication) {
        boolean passwordChangeRequired = hasAuthority(authentication, "SYSTEM_ADMIN_PASSWORD_CHANGE_REQUIRED");
        Map<String, Object> response = base(account);
        response.put("context", null);
        response.put("availableContexts", List.of());

        if (passwordChangeRequired) {
            response.put("capabilities", List.of("CHANGE_OWN_PASSWORD"));
            response.put("navigation", List.of(Map.of(
                    "id", "password",
                    "label", "Passwort ändern",
                    "route", "/system-admin")));
        } else {
            response.put("capabilities", List.of("SCHOOL_REGISTRATION_REVIEW", "SCHOOL_MANAGEMENT"));
            response.put("navigation", List.of(Map.of(
                    "id", "registrations",
                    "label", "Schulregistrierungen",
                    "route", "/system-admin")));
        }
        return response;
    }

    private Map<String, Object> schoolBoundContext(Account account, List<SchoolContext> schools, SchoolContext selected) {
        Map<String, Object> response = base(account);
        response.put("availableContexts", schools.stream().map(this::contextMap).toList());
        response.put("context", selected == null ? null : contextMap(selected));

        if (selected == null) {
            response.put("capabilities", List.of());
            response.put("navigation", List.of());
            return response;
        }

        if (account.type().equals("TEACHER")) {
            boolean schoolAdmin = hasSchoolRole(account.id(), selected.schoolId(), "SCHOOL_ADMIN");
            List<String> capabilities = new ArrayList<>(List.of(
                    "CLASS_READ",
                    "CLASS_EDIT",
                    "STUDENT_READ",
                    "STUDENT_MOVE",
                    "PROGRESS_VIEW"));
            if (schoolAdmin) {
                capabilities.add("SCHOOL_ADMINISTRATION");
            }
            response.put("capabilities", capabilities);
            response.put("navigation", teacherNavigation(selected, schoolAdmin));
        } else {
            response.put("capabilities", List.of("LEARNING_CONTENT_READ", "OWN_PROGRESS_READ"));
            response.put("navigation", List.of(
                    nav("learning", "Lernen", "/" + selected.slug() + "/lernen"),
                    nav("progress", "Mein Lernstand", "/" + selected.slug() + "/lernstand")));
        }
        return response;
    }

    private List<Map<String, String>> teacherNavigation(SchoolContext school, boolean schoolAdmin) {
        List<Map<String, String>> navigation = new ArrayList<>();
        navigation.add(nav("classes", "Klassen", "/" + school.slug() + "/klassen"));
        navigation.add(nav("students", "Schüler", "/" + school.slug() + "/schueler"));
        navigation.add(nav("progress", "Lernstände", "/" + school.slug() + "/lernstaende"));
        if (schoolAdmin) {
            navigation.add(nav("school-admin", "Schulverwaltung", "/" + school.slug() + "/verwaltung"));
        }
        return navigation;
    }

    private Account loadAccount(UUID accountId) {
        return jdbc.queryForObject("""
                select id, account_type, display_name
                from account
                where id = ? and status = 'ACTIVE' and deleted_at is null
                """, (rs, rowNum) -> new Account(
                rs.getObject("id", UUID.class),
                rs.getString("account_type"),
                rs.getString("display_name")), accountId);
    }

    private List<SchoolContext> loadSchools(UUID accountId) {
        return jdbc.query("""
                select s.id, s.slug, s.name
                from school_membership sm
                join school s on s.id = sm.school_id
                where sm.account_id = ?
                  and sm.status = 'ACTIVE'
                  and sm.deleted_at is null
                  and s.status = 'ACTIVE'
                order by lower(s.name), s.id
                """, (rs, rowNum) -> new SchoolContext(
                rs.getObject("id", UUID.class),
                rs.getString("slug"),
                rs.getString("name")), accountId);
    }

    private boolean hasSchoolRole(UUID accountId, UUID schoolId, String role) {
        Boolean exists = jdbc.queryForObject("""
                select exists(
                    select 1
                    from school_membership sm
                    join school_role sr on sr.school_membership_id = sm.id
                    where sm.account_id = ? and sm.school_id = ?
                      and sm.status = 'ACTIVE' and sm.deleted_at is null
                      and sr.role = ?
                )
                """, Boolean.class, accountId, schoolId, role);
        return Boolean.TRUE.equals(exists);
    }

    private static SchoolContext selectSchool(String requestedSlug, List<SchoolContext> schools) {
        if (schools.isEmpty()) return null;
        if (requestedSlug == null || requestedSlug.isBlank()) {
            return schools.size() == 1 ? schools.getFirst() : null;
        }
        return schools.stream()
                .filter(school -> school.slug().equalsIgnoreCase(requestedSlug))
                .findFirst()
                .orElse(null);
    }

    private Map<String, Object> base(Account account) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("account", Map.of(
                "id", account.id().toString(),
                "type", account.type(),
                "displayName", account.displayName()));
        return response;
    }

    private Map<String, String> contextMap(SchoolContext school) {
        return Map.of(
                "schoolId", school.schoolId().toString(),
                "schoolSlug", school.slug(),
                "schoolName", school.name());
    }

    private static Map<String, String> nav(String id, String label, String route) {
        return Map.of("id", id, "label", label, "route", route);
    }

    private static boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

    private record Account(UUID id, String type, String displayName) {}
    private record SchoolContext(UUID schoolId, String slug, String name) {}
}
