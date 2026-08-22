package de.schule.informatik.lernplattform.app.registration;

import de.schule.informatik.lernplattform.domain.registration.SchoolRegistrationRequestPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class SystemAdminRegistrationService {

    private final JdbcTemplate jdbc;
    private final SchoolRegistrationRequestPort registrations;

    public SystemAdminRegistrationService(JdbcTemplate jdbc, SchoolRegistrationRequestPort registrations) {
        this.jdbc = jdbc;
        this.registrations = registrations;
    }

    public List<SchoolRegistrationRequestPort.PendingSchoolRegistration> pending(UUID actorId) {
        requireSystemAdmin(actorId);
        return registrations.findPendingReview();
    }

    @Transactional
    public UUID approve(UUID requestId, UUID actorId) {
        requireSystemAdmin(actorId);
        var request = registrations.findPendingReview(requestId)
                .orElseThrow(() -> new IllegalArgumentException("registration request not pending review"));

        UUID schoolId = UUID.randomUUID();
        String slug = uniqueSlug(request.schoolName());
        jdbc.update("""
                insert into school (id, slug, name, school_type, federal_state, status, created_by)
                values (?, ?, ?, ?, ?, 'ACTIVE', ?)
                """, schoolId, slug, request.schoolName(), request.schoolType(), request.federalState(), actorId);

        UUID teacherId = findTeacherByEmail(request.contactEmail());
        if (teacherId == null) {
            teacherId = UUID.randomUUID();
            String displayName = "Lehrkraft-" + teacherId.toString().substring(0, 8);
            jdbc.update("""
                    insert into account (
                        id, account_type, display_name, display_name_normalized,
                        teacher_email, teacher_email_normalized, email_verified_at, status, created_by
                    ) values (?, 'TEACHER', ?, ?, ?, ?, ?, 'ACTIVE', ?)
                    """, teacherId, displayName, displayName.toLowerCase(Locale.ROOT),
                    request.contactEmail(), request.contactEmail().toLowerCase(Locale.ROOT), Instant.now(), actorId);
        }

        UUID membershipId = UUID.randomUUID();
        jdbc.update("""
                insert into school_membership (id, account_id, school_id, status, created_by)
                values (?, ?, ?, 'ACTIVE', ?)
                """, membershipId, teacherId, schoolId, actorId);
        jdbc.update("""
                insert into school_role (school_membership_id, role, created_by)
                values (?, 'SCHOOL_ADMIN', ?)
                """, membershipId, actorId);

        registrations.markApproved(requestId, schoolId, actorId, Instant.now());
        return schoolId;
    }

    @Transactional
    public void reject(UUID requestId, String reason, UUID actorId) {
        requireSystemAdmin(actorId);
        String cleanReason = reason == null ? "" : reason.trim();
        if (cleanReason.isBlank() || cleanReason.length() > 1000) {
            throw new IllegalArgumentException("rejection reason must contain 1..1000 characters");
        }
        registrations.markRejected(requestId, cleanReason, actorId, Instant.now());
    }

    private void requireSystemAdmin(UUID actorId) {
        Integer count = jdbc.queryForObject("""
                select count(*) from system_role sr
                join account a on a.id = sr.account_id
                where sr.account_id = ? and sr.role = 'SYSTEM_ADMIN'
                  and a.account_type = 'SYSTEM' and a.status = 'ACTIVE' and a.deleted_at is null
                """, Integer.class, actorId);
        if (count == null || count != 1) {
            throw new SecurityException("SYSTEM_ADMIN required");
        }
    }

    private UUID findTeacherByEmail(String email) {
        List<UUID> ids = jdbc.query("""
                select id from account
                where account_type = 'TEACHER' and teacher_email_normalized = ? and deleted_at is null
                """, (rs, rowNum) -> rs.getObject(1, UUID.class), email.toLowerCase(Locale.ROOT));
        return ids.stream().findFirst().orElse(null);
    }

    private String uniqueSlug(String name) {
        String base = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        if (base.isBlank()) base = "schule";
        if (base.length() > 100) base = base.substring(0, 100).replaceAll("-$", "");
        String candidate = base;
        int suffix = 2;
        while (Boolean.TRUE.equals(jdbc.queryForObject(
                "select exists(select 1 from school where lower(slug) = lower(?))", Boolean.class, candidate))) {
            candidate = base + "-" + suffix++;
        }
        return candidate;
    }
}
