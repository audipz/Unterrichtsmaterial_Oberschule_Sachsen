package de.schule.informatik.lernplattform.app.security;

import de.schule.informatik.lernplattform.domain.user.PasswordHashPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "lernplattform.bootstrap.system-admin.enabled", havingValue = "true")
public class SystemAdminBootstrap implements ApplicationRunner {

    private static final long BOOTSTRAP_LOCK_ID = 7349021001L;

    private final JdbcTemplate jdbc;
    private final PasswordHashPort passwordHashPort;
    private final String username;
    private final String password;

    public SystemAdminBootstrap(JdbcTemplate jdbc,
                                PasswordHashPort passwordHashPort,
                                @Value("${lernplattform.bootstrap.system-admin.username}") String username,
                                @Value("${lernplattform.bootstrap.system-admin.password}") String password) {
        this.jdbc = jdbc;
        this.passwordHashPort = passwordHashPort;
        this.username = username;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        jdbc.execute("select pg_advisory_xact_lock(" + BOOTSTRAP_LOCK_ID + ")");

        Integer existingAdmins = jdbc.queryForObject(
                "select count(*) from system_role where role = 'SYSTEM_ADMIN'",
                Integer.class);
        if (existingAdmins != null && existingAdmins > 0) {
            return;
        }

        if (username == null || username.isBlank()) {
            throw new IllegalStateException("SYSTEM_ADMIN_USERNAME must not be blank when bootstrap is enabled");
        }
        if (password == null || password.length() < 16) {
            throw new IllegalStateException("SYSTEM_ADMIN_PASSWORD must contain at least 16 characters");
        }

        UUID accountId = UUID.randomUUID();
        String normalizedUsername = username.trim().toLowerCase(Locale.ROOT);
        String passwordHash = passwordHashPort.hash(password);

        jdbc.update("""
                insert into account (
                    id, account_type, display_name, display_name_normalized, status
                ) values (?, 'SYSTEM', ?, ?, 'ACTIVE')
                """, accountId, username.trim(), normalizedUsername);

        jdbc.update("""
                insert into system_account_login (
                    account_id, username, username_normalized, password_hash, must_change_password
                ) values (?, ?, ?, ?, true)
                """, accountId, username.trim(), normalizedUsername, passwordHash);

        jdbc.update("""
                insert into system_role (account_id, role)
                values (?, 'SYSTEM_ADMIN')
                """, accountId);
    }
}
