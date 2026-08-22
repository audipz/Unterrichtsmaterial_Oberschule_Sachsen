package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.AccountProvisioningPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AccountProvisioningAdapter implements AccountProvisioningPort {

    private final JdbcTemplate jdbcTemplate;

    public AccountProvisioningAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean studentUsernameExists(UUID schoolId, String normalizedUsername) {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from student_school_login where school_id = ? and username_normalized = ? and deleted_at is null",
                Integer.class, schoolId, normalizedUsername);
        return count != null && count > 0;
    }

    @Override
    public boolean teacherEmailExists(String normalizedEmail) {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from account where account_type = 'TEACHER' and email_normalized = ? and deleted_at is null",
                Integer.class, normalizedEmail);
        return count != null && count > 0;
    }

    @Override
    public UUID createStudent(UUID schoolId,
                              String username,
                              String normalizedUsername,
                              String displayName,
                              String normalizedDisplayName,
                              String passwordHash,
                              UUID actorId) {
        UUID accountId = UUID.randomUUID();
        UUID membershipId = UUID.randomUUID();
        UUID loginId = UUID.randomUUID();

        jdbcTemplate.update("""
                insert into account (id, account_type, display_name, display_name_normalized, status, created_by, updated_by)
                values (?, 'STUDENT', ?, ?, 'ACTIVE', ?, ?)
                """, accountId, displayName, normalizedDisplayName, actorId, actorId);

        jdbcTemplate.update("""
                insert into school_membership (id, account_id, school_id, status, joined_at, created_by, updated_by)
                values (?, ?, ?, 'ACTIVE', now(), ?, ?)
                """, membershipId, accountId, schoolId, actorId, actorId);

        jdbcTemplate.update("""
                insert into student_school_login
                    (id, membership_id, school_id, username, username_normalized, password_hash, must_change_password, created_by, updated_by)
                values (?, ?, ?, ?, ?, ?, true, ?, ?)
                """, loginId, membershipId, schoolId, username, normalizedUsername, passwordHash, actorId, actorId);

        return accountId;
    }

    @Override
    public UUID createTeacher(String email,
                              String normalizedEmail,
                              String displayName,
                              String normalizedDisplayName,
                              UUID actorId) {
        UUID accountId = UUID.randomUUID();
        jdbcTemplate.update("""
                insert into account
                    (id, account_type, email, email_normalized, display_name, display_name_normalized, status, created_by, updated_by)
                values (?, 'TEACHER', ?, ?, ?, ?, 'ACTIVE', ?, ?)
                """, accountId, email, normalizedEmail, displayName, normalizedDisplayName, actorId, actorId);
        return accountId;
    }

    @Override
    public void addTeacherToSchool(UUID teacherId, UUID schoolId, UUID actorId) {
        Integer count = jdbcTemplate.queryForObject("""
                select count(*) from school_membership
                 where account_id = ? and school_id = ? and status = 'ACTIVE' and deleted_at is null
                """, Integer.class, teacherId, schoolId);
        if (count != null && count > 0) {
            return;
        }

        jdbcTemplate.update("""
                insert into school_membership (id, account_id, school_id, status, joined_at, created_by, updated_by)
                values (?, ?, ?, 'ACTIVE', now(), ?, ?)
                """, UUID.randomUUID(), teacherId, schoolId, actorId, actorId);
    }
}
