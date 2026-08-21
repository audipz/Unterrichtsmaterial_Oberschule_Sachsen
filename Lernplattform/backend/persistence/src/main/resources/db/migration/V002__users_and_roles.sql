CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    school_id UUID NOT NULL REFERENCES school(id),
    username VARCHAR(120) NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    display_name_normalized VARCHAR(120) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    must_change_password BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,
    CONSTRAINT ck_app_user_status CHECK (status IN ('ACTIVE', 'SOFT_DELETED')),
    CONSTRAINT ck_app_user_username_not_blank CHECK (btrim(username) <> ''),
    CONSTRAINT ck_app_user_display_name_not_blank CHECK (btrim(display_name) <> '')
);

CREATE UNIQUE INDEX uq_app_user_school_username_active
    ON app_user (school_id, lower(username))
    WHERE deleted_at IS NULL;

CREATE INDEX ix_app_user_school ON app_user (school_id);
CREATE INDEX ix_app_user_display_name_normalized ON app_user (display_name_normalized)
    WHERE deleted_at IS NULL;
CREATE INDEX ix_app_user_deleted_at ON app_user (deleted_at)
    WHERE deleted_at IS NOT NULL;

CREATE TABLE user_role (
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    school_id UUID NOT NULL REFERENCES school(id),
    role VARCHAR(40) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    PRIMARY KEY (user_id, school_id, role),
    CONSTRAINT ck_user_role CHECK (role IN ('STUDENT', 'TEACHER', 'SCHOOL_ADMIN', 'SYSTEM_ADMIN'))
);

CREATE INDEX ix_user_role_school_role ON user_role (school_id, role);
