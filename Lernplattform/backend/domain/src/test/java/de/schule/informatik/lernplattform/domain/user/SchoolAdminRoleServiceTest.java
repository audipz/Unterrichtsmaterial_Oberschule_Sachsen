package de.schule.informatik.lernplattform.domain.user;

import de.schule.informatik.lernplattform.domain.auth.SchoolAuthorizationPort;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SchoolAdminRoleServiceTest {

    @Test
    void grantsRoleOnlyToActiveTeacherOfSameSchool() {
        UUID schoolId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        UUID actorId = UUID.randomUUID();
        AtomicBoolean granted = new AtomicBoolean();

        var port = new StubPort(new SchoolAdminRolePort.TargetUser(
                teacherId, schoolId, UserStatus.ACTIVE, Set.of(UserRole.TEACHER)), 2, granted);
        var service = new SchoolAdminRoleService(port, allowingAdmin(actorId, schoolId));

        service.grant(schoolId, teacherId, actorId);

        assertThat(granted).isTrue();
    }

    @Test
    void refusesToRemoveLastActiveSchoolAdmin() {
        UUID schoolId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        UUID actorId = UUID.randomUUID();
        AtomicBoolean changed = new AtomicBoolean();

        var port = new StubPort(new SchoolAdminRolePort.TargetUser(
                teacherId, schoolId, UserStatus.ACTIVE,
                Set.of(UserRole.TEACHER, UserRole.SCHOOL_ADMIN)), 1, changed);
        var service = new SchoolAdminRoleService(port, allowingAdmin(actorId, schoolId));

        assertThatThrownBy(() -> service.revoke(schoolId, teacherId, actorId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("letzte aktive Schuladministrator");
        assertThat(changed).isFalse();
    }

    private static SchoolAuthorizationPort allowingAdmin(UUID actorId, UUID schoolId) {
        return new SchoolAuthorizationPort() {
            @Override
            public void requireSchoolAdmin(UUID actor, UUID school) {
                if (!actorId.equals(actor) || !schoolId.equals(school)) {
                    throw new SecurityException("not allowed");
                }
            }

            @Override
            public void requireSystemAdmin(UUID actorId) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static final class StubPort implements SchoolAdminRolePort {
        private final TargetUser user;
        private final long adminCount;
        private final AtomicBoolean changed;

        private StubPort(TargetUser user, long adminCount, AtomicBoolean changed) {
            this.user = user;
            this.adminCount = adminCount;
            this.changed = changed;
        }

        @Override public TargetUser requireUser(UUID userId) { return user; }
        @Override public long countActiveSchoolAdmins(UUID schoolId) { return adminCount; }
        @Override public void grantSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId) { changed.set(true); }
        @Override public void revokeSchoolAdmin(UUID schoolId, UUID teacherId, UUID actorId) { changed.set(true); }
    }
}
