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
        jdbc.execute("DELETE FROM user_role");
        jdbc.execute("DELETE FROM school_class");
        jdbc.execute("DELETE FROM app_user");
        jdbc.execute("DELETE FROM school");
    }

    @Test
    void detectsActiveStudentWithSameDisplayNameInClass() {
        Fixture f = fixture();
        UUID existingStudent = insertUser(f.schoolId(), "student-1", "PixelFuchs", "pixelfuchs", false);
        insertStudentMembership(f.classId(), existingStudent, "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isTrue();
    }

    @Test
    void ignoresEndedStudentMembership() {
        Fixture f = fixture();
        UUID existingStudent = insertUser(f.schoolId(), "student-1", "PixelFuchs", "pixelfuchs", false);
        insertStudentMembership(f.classId(), existingStudent, "ENDED");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    @Test
    void detectsActiveTeacherWithSameDisplayNameInClass() {
        Fixture f = fixture();
        UUID teacher = insertUser(f.schoolId(), "teacher-1", "CodeOtter", "codeotter", false);
        jdbc.update("INSERT INTO class_teacher (school_class_id, teacher_id) VALUES (?, ?)",
                f.classId(), teacher);

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "codeotter", Set.of(f.classId()));

        assertThat(conflict).isTrue();
    }

    @Test
    void ignoresSoftDeletedUsers() {
        Fixture f = fixture();
        UUID existingStudent = insertUser(f.schoolId(), "student-1", "PixelFuchs", "pixelfuchs", true);
        insertStudentMembership(f.classId(), existingStudent, "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                UUID.randomUUID(), "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    @Test
    void ignoresCurrentUserWhenRenamingWithoutChangingName() {
        Fixture f = fixture();
        UUID currentUser = insertUser(f.schoolId(), "student-1", "PixelFuchs", "pixelfuchs", false);
        insertStudentMembership(f.classId(), currentUser, "ACTIVE");

        boolean conflict = adapter.conflictsInClasses(
                currentUser, "pixelfuchs", Set.of(f.classId()));

        assertThat(conflict).isFalse();
    }

    private Fixture fixture() {
        UUID schoolId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();

        jdbc.update("INSERT INTO school (id, name, short_name) VALUES (?, ?, ?)",
                schoolId, "Testschule", "TS-" + schoolId.toString().substring(0, 8));
        jdbc.update("""
                INSERT INTO school_class (id, school_id, name, grade_level, school_year)
                VALUES (?, ?, ?, ?, ?)
                """,
                classId, schoolId, "7a", 7, "2026/27");

        return new Fixture(schoolId, classId);
    }

    private UUID insertUser(UUID schoolId,
                            String username,
                            String displayName,
                            String normalizedDisplayName,
                            boolean softDeleted) {
        UUID userId = UUID.randomUUID();

        jdbc.update("""
                INSERT INTO app_user (
                    id, school_id, username, display_name, display_name_normalized,
                    password_hash, status, deleted_at
                ) VALUES (?, ?, ?, ?, ?, ?, ?, CASE WHEN ? THEN now() ELSE NULL END)
                """,
                userId,
                schoolId,
                username,
                displayName,
                normalizedDisplayName,
                "$argon2id$test",
                softDeleted ? "SOFT_DELETED" : "ACTIVE",
                softDeleted);

        return userId;
    }

    private void insertStudentMembership(UUID classId, UUID studentId, String status) {
        LocalDate today = LocalDate.now();
        LocalDate validUntil = "ENDED".equals(status) ? today : null;

        jdbc.update("""
                INSERT INTO school_class_membership (
                    id, school_class_id, student_id, valid_from, valid_until, status
                ) VALUES (?, ?, ?, ?, ?, ?)
                """,
                UUID.randomUUID(), classId, studentId, today.minusDays(1), validUntil, status);
    }

    private record Fixture(UUID schoolId, UUID classId) {
    }
}
