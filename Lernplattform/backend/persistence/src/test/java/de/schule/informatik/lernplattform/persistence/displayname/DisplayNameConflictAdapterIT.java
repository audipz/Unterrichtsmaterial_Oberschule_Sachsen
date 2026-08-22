package de.schule.informatik.lernplattform.persistence.displayname;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DisplayNameConflictAdapterIT {

    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:17-alpine");

    private static JdbcTemplate jdbc;
    private static DisplayNameConflictAdapter adapter;

    @BeforeAll
    static void startPostgres() {
        POSTGRES.start();

        var dataSource = new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword());

        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load()
                .migrate();

        jdbc = new JdbcTemplate(dataSource);
        adapter = new DisplayNameConflictAdapter(new NamedParameterJdbcTemplate(dataSource));
    }

    @AfterAll
    static void stopPostgres() {
        POSTGRES.stop();
    }

    @BeforeEach
    void clearData() {
        jdbc.execute("DELETE FROM class_teacher");
        jdbc.execute("DELETE FROM school_class_membership");
        jdbc.execute("DELETE FROM student_school_login");
        jdbc.execute("DELETE FROM school_role");
        jdbc.execute("DELETE FROM system_role");
        jdbc.execute("DELETE FROM school_class");
        jdbc.execute("DELETE FROM school_membership");
        jdbc.execute("DELETE FROM account");
        jdbc.execute("DELETE FROM school");
    }

    @Test
    void detectsActiveStudentWithSameDisplayNameInClass() {
        Fixture f = fixture();
        AccountMembership student = insertAccountWithMembership(f.schoolId(), "STUDENT", "PixelFuchs", "pixelfuchs", false);
        insertStudentClassMembership(f, student.membershipId(), "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isTrue();
    }

    @Test
    void ignoresEndedStudentMembership() {
        Fixture f = fixture();
        AccountMembership student = insertAccountWithMembership(f.schoolId(), "STUDENT", "PixelFuchs", "pixelfuchs", false);
        insertStudentClassMembership(f, student.membershipId(), "ENDED");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    @Test
    void detectsActiveTeacherWithSameDisplayNameInClass() {
        Fixture f = fixture();
        AccountMembership teacher = insertAccountWithMembership(f.schoolId(), "TEACHER", "CodeOtter", "codeotter", false);
        jdbc.update("""
                INSERT INTO class_teacher (school_class_id, school_id, teacher_school_membership_id)
                VALUES (?, ?, ?)
                """, f.classId(), f.schoolId(), teacher.membershipId());

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "codeotter", Set.of(f.classId()));

        assertThat(conflict).isTrue();
    }

    @Test
    void ignoresSoftDeletedAccounts() {
        Fixture f = fixture();
        AccountMembership student = insertAccountWithMembership(f.schoolId(), "STUDENT", "PixelFuchs", "pixelfuchs", true);
        insertStudentClassMembership(f, student.membershipId(), "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    @Test
    void ignoresCurrentAccountWhenRenamingWithoutChangingName() {
        Fixture f = fixture();
        AccountMembership student = insertAccountWithMembership(f.schoolId(), "STUDENT", "PixelFuchs", "pixelfuchs", false);
        insertStudentClassMembership(f, student.membershipId(), "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                student.accountId(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    private Fixture fixture() {
        UUID schoolId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();

        jdbc.update("INSERT INTO school (id, slug, name) VALUES (?, ?, ?)",
                schoolId, "test-" + schoolId.toString().substring(0, 8), "Testschule");
        jdbc.update("""
                INSERT INTO school_class (id, school_id, name, grade_level, school_year)
                VALUES (?, ?, ?, ?, ?)
                """, classId, schoolId, "7a", 7, "2026/27");

        return new Fixture(schoolId, classId);
    }

    private AccountMembership insertAccountWithMembership(UUID schoolId,
                                                          String accountType,
                                                          String displayName,
                                                          String normalizedDisplayName,
                                                          boolean softDeleted) {
        UUID accountId = UUID.randomUUID();
        UUID membershipId = UUID.randomUUID();
        String teacherEmail = "TEACHER".equals(accountType)
                ? "teacher-" + accountId + "@example.invalid"
                : null;

        jdbc.update("""
                INSERT INTO account (
                    id, account_type, display_name, display_name_normalized,
                    teacher_email, teacher_email_normalized, status, deleted_at
                ) VALUES (?, ?, ?, ?, ?, ?, ?, CASE WHEN ? THEN now() ELSE NULL END)
                """,
                accountId,
                accountType,
                displayName,
                normalizedDisplayName,
                teacherEmail,
                teacherEmail,
                softDeleted ? "SOFT_DELETED" : "ACTIVE",
                softDeleted);

        jdbc.update("""
                INSERT INTO school_membership (id, account_id, school_id, status)
                VALUES (?, ?, ?, 'ACTIVE')
                """, membershipId, accountId, schoolId);

        return new AccountMembership(accountId, membershipId);
    }

    private void insertStudentClassMembership(Fixture fixture, UUID studentMembershipId, String status) {
        LocalDate today = LocalDate.now();
        LocalDate validUntil = "ENDED".equals(status) ? today : null;

        jdbc.update("""
                INSERT INTO school_class_membership (
                    id, school_class_id, school_id, student_school_membership_id,
                    valid_from, valid_until, status
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                UUID.randomUUID(), fixture.classId(), fixture.schoolId(), studentMembershipId,
                today.minusDays(1), validUntil, status);
    }

    private record Fixture(UUID schoolId, UUID classId) {}
    private record AccountMembership(UUID accountId, UUID membershipId) {}
}
