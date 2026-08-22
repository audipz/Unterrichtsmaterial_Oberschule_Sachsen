CREATE TABLE account (
    id UUID PRIMARY KEY,
    account_type VARCHAR(30) NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    display_name_normalized VARCHAR(120) NOT NULL,
    teacher_email VARCHAR(320),
    teacher_email_normalized VARCHAR(320),
    email_verified_at TIMESTAMPTZ,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    pending_deletion_at TIMESTAMPTZ,
    last_login_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,
    CONSTRAINT ck_account_type CHECK (account_type IN ('STUDENT', 'TEACHER')),
    CONSTRAINT ck_account_status CHECK (status IN ('ACTIVE', 'DISABLED', 'PENDING_DELETION', 'SOFT_DELETED')),
    CONSTRAINT ck_account_display_name_not_blank CHECK (btrim(display_name) <> ''),
    CONSTRAINT ck_account_teacher_email CHECK (
        (account_type = 'TEACHER' AND teacher_email IS NOT NULL AND teacher_email_normalized IS NOT NULL)
        OR
        (account_type = 'STUDENT' AND teacher_email IS NULL AND teacher_email_normalized IS NULL)
    )
);

CREATE UNIQUE INDEX uq_account_teacher_email
    ON account (teacher_email_normalized)
    WHERE account_type = 'TEACHER' AND deleted_at IS NULL;

CREATE INDEX ix_account_display_name_normalized
    ON account (display_name_normalized)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_account_pending_deletion
    ON account (pending_deletion_at)
    WHERE status = 'PENDING_DELETION' AND deleted_at IS NULL;

CREATE TABLE school_membership (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES account(id),
    school_id UUID NOT NULL REFERENCES school(id),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    joined_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    left_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID REFERENCES account(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES account(id),
    CONSTRAINT ck_school_membership_status CHECK (status IN ('ACTIVE', 'ENDED', 'SOFT_DELETED')),
    CONSTRAINT ck_school_membership_dates CHECK (left_at IS NULL OR left_at >= joined_at)
);

CREATE UNIQUE INDEX uq_school_membership_active
    ON school_membership (account_id, school_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;

CREATE INDEX ix_school_membership_school
    ON school_membership (school_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;

CREATE TABLE student_school_login (
    school_membership_id UUID PRIMARY KEY REFERENCES school_membership(id) ON DELETE CASCADE,
    username VARCHAR(120) NOT NULL,
    username_normalized VARCHAR(120) NOT NULL,
    password_hash VARCHAR(500) NOT NULL,
    must_change_password BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_student_school_login_username_not_blank CHECK (btrim(username) <> '')
);

CREATE UNIQUE INDEX uq_student_school_username
    ON student_school_login (username_normalized);

-- Die globale Eindeutigkeit des obigen Index wird in V003 durch einen schulbezogenen
-- Eindeutigkeitsindex ersetzt, sobald school_id über die Membership sicher eingebunden ist.

CREATE TABLE school_role (
    school_membership_id UUID NOT NULL REFERENCES school_membership(id) ON DELETE CASCADE,
    role VARCHAR(40) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    PRIMARY KEY (school_membership_id, role),
    CONSTRAINT ck_school_role CHECK (role IN ('SCHOOL_ADMIN'))
);

CREATE TABLE system_role (
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    role VARCHAR(40) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    PRIMARY KEY (account_id, role),
    CONSTRAINT ck_system_role CHECK (role IN ('SYSTEM_ADMIN'))
);
