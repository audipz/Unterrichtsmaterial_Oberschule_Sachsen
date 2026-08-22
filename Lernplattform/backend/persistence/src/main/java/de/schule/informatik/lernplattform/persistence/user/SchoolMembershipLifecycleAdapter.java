package de.schule.informatik.lernplattform.persistence.user;

import de.schule.informatik.lernplattform.domain.user.AccountType;
import de.schule.informatik.lernplattform.domain.user.SchoolMembershipLifecyclePort;
import de.schule.informatik.lernplattform.domain.user.TeacherSchoolMembershipPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class SchoolMembershipLifecycleAdapter implements SchoolMembershipLifecyclePort, TeacherSchoolMembershipPort {

    private final JdbcTemplate jdbc;

    public SchoolMembershipLifecycleAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public MembershipContext requireMembership(UUID accountId, UUID schoolId) {
        return jdbc.query("""
                select sm.id, sm.account_id, sm.school_id, a.account_type, sm.status
                  from school_membership sm
                  join account a on a.id = sm.account_id
                 where sm.account_id = ? and sm.school_id = ?
                 order by sm.created_at desc
                 limit 1
                """, rs -> {
            if (!rs.next()) throw new IllegalArgumentException("school membership not found");
            return new MembershipContext(
                    rs.getObject("id", UUID.class),
                    rs.getObject("account_id", UUID.class),
                    rs.getObject("school_id", UUID.class),
                    AccountType.valueOf(rs.getString("account_type")),
                    "ACTIVE".equals(rs.getString("status"))
            );
        }, accountId, schoolId);
    }

    @Override
    public long countActiveSchoolMemberships(UUID accountId) {
        Long count = jdbc.queryForObject("""
                select count(*) from school_membership
                 where account_id = ? and status = 'ACTIVE' and deleted_at is null
                """, Long.class, accountId);
        return count == null ? 0 : count;
    }

    @Override
    public void endMembership(UUID membershipId, LocalDate effectiveDate, UUID actorId) {
        jdbc.update("""
                update school_membership
                   set status = 'ENDED', left_at = ?, updated_at = now(), updated_by = ?
                 where id = ? and status = 'ACTIVE'
                """, effectiveDate, actorId, membershipId);
    }

    @Override
    public void reactivateMembership(UUID membershipId, UUID actorId) {
        jdbc.update("""
                update school_membership
                   set status = 'ACTIVE', left_at = null, deleted_at = null, deleted_by = null,
                       updated_at = now(), updated_by = ?
                 where id = ?
                """, actorId, membershipId);
    }

    @Override
    public void markAccountPendingDeletion(UUID accountId, UUID actorId) {
        jdbc.update("""
                update account
                   set status = 'PENDING_DELETION', pending_deletion_at = now(),
                       updated_at = now(), updated_by = ?
                 where id = ? and status <> 'SOFT_DELETED'
                """, actorId, accountId);
    }

    @Override
    public void reactivateAccount(UUID accountId, UUID actorId) {
        jdbc.update("""
                update account
                   set status = 'ACTIVE', pending_deletion_at = null, deleted_at = null, deleted_by = null,
                       updated_at = now(), updated_by = ?
                 where id = ?
                """, actorId, accountId);
    }

    @Override
    public TeacherMembershipContext requireTeacherMembership(UUID teacherId, UUID schoolId) {
        return jdbc.query("""
                select sm.id, sm.account_id, sm.school_id, sm.status,
                       exists(select 1 from school_role r
                               where r.school_membership_id = sm.id and r.role = 'SCHOOL_ADMIN') as school_admin
                  from school_membership sm
                  join account a on a.id = sm.account_id and a.account_type = 'TEACHER'
                 where sm.account_id = ? and sm.school_id = ?
                 order by sm.created_at desc
                 limit 1
                """, rs -> {
            if (!rs.next()) throw new IllegalArgumentException("teacher school membership not found");
            return new TeacherMembershipContext(
                    rs.getObject("id", UUID.class),
                    rs.getObject("account_id", UUID.class),
                    rs.getObject("school_id", UUID.class),
                    "ACTIVE".equals(rs.getString("status")),
                    rs.getBoolean("school_admin")
            );
        }, teacherId, schoolId);
    }

    @Override
    public Set<UUID> classesWhereTeacherIsSoleAssignedTeacher(UUID membershipId) {
        return new HashSet<>(jdbc.query("""
                select ct.school_class_id
                  from class_teacher ct
                  join school_class sc on sc.id = ct.school_class_id
                 where ct.teacher_school_membership_id = ?
                   and ct.deleted_at is null
                   and sc.status = 'ACTIVE'
                   and sc.deleted_at is null
                   and not exists (
                       select 1 from class_teacher other
                        where other.school_class_id = ct.school_class_id
                          and other.teacher_school_membership_id <> ct.teacher_school_membership_id
                          and other.deleted_at is null
                   )
                """, (rs, rowNum) -> rs.getObject(1, UUID.class), membershipId));
    }

    @Override
    public long countOtherActiveSchoolAdmins(UUID schoolId, UUID excludedMembershipId) {
        Long count = jdbc.queryForObject("""
                select count(*)
                  from school_membership sm
                  join school_role r on r.school_membership_id = sm.id
                  join account a on a.id = sm.account_id
                 where sm.school_id = ? and sm.id <> ?
                   and sm.status = 'ACTIVE' and sm.deleted_at is null
                   and a.status = 'ACTIVE' and a.deleted_at is null
                   and a.account_type = 'TEACHER'
                   and r.role = 'SCHOOL_ADMIN'
                """, Long.class, schoolId, excludedMembershipId);
        return count == null ? 0 : count;
    }

    @Override
    public void endTeacherMembership(UUID membershipId, UUID actorId) {
        jdbc.update("""
                update school_membership
                   set status = 'ENDED', left_at = current_date, updated_at = now(), updated_by = ?
                 where id = ? and status = 'ACTIVE'
                """, actorId, membershipId);
    }
}
