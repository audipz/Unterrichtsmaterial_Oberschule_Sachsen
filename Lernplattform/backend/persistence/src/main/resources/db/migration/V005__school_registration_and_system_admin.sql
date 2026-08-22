ALTER TABLE account DROP CONSTRAINT ck_account_type;
ALTER TABLE account ADD CONSTRAINT ck_account_type
    CHECK (account_type IN ('STUDENT', 'TEACHER', 'SYSTEM'));

ALTER TABLE account DROP CONSTRAINT ck_account_teacher_email;
ALTER TABLE account ADD CONSTRAINT ck_account_teacher_email CHECK (
    (account_type = 'TEACHER' AND teacher_email IS NOT NULL AND teacher_email_normalized IS NOT NULL)
    OR
    (account_type IN ('STUDENT', 'SYSTEM') AND teacher_email IS NULL AND teacher_email_normalized IS NULL)
);

CREATE TABLE system_account_login (
    account_id UUID PRIMARY KEY REFERENCES account(id) ON DELETE CASCADE,
    username VARCHAR(120) NOT NULL,
    username_normalized VARCHAR(120) NOT NULL,
    password_hash VARCHAR(500) NOT NULL,
    must_change_password BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_system_account_login_username_not_blank CHECK (btrim(username) <> '')
);

CREATE UNIQUE INDEX uq_system_account_username
    ON system_account_login (username_normalized);

CREATE TABLE school_registration_request (
    id UUID PRIMARY KEY,
    school_name VARCHAR(200) NOT NULL,
    school_type VARCHAR(60) NOT NULL,
    federal_state VARCHAR(60) NOT NULL,
    city VARCHAR(160) NOT NULL,
    contact_email VARCHAR(320) NOT NULL,
    contact_email_normalized VARCHAR(320) NOT NULL,
    school_website VARCHAR(500),
    requested_slug VARCHAR(120),
    status VARCHAR(40) NOT NULL DEFAULT 'EMAIL_VERIFICATION_PENDING',
    verification_token_hash VARCHAR(128),
    verification_expires_at TIMESTAMPTZ,
    email_verified_at TIMESTAMPTZ,
    submitted_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    reviewed_at TIMESTAMPTZ,
    reviewed_by UUID REFERENCES account(id),
    rejection_reason VARCHAR(1000),
    approved_school_id UUID REFERENCES school(id),
    created_ip_hash VARCHAR(128),
    user_agent_hash VARCHAR(128),
    submission_nonce VARCHAR(120) NOT NULL,
    CONSTRAINT ck_school_registration_status CHECK (status IN (
        'EMAIL_VERIFICATION_PENDING',
        'PENDING_REVIEW',
        'APPROVED',
        'REJECTED',
        'EXPIRED'
    )),
    CONSTRAINT ck_school_registration_name_not_blank CHECK (btrim(school_name) <> ''),
    CONSTRAINT ck_school_registration_city_not_blank CHECK (btrim(city) <> ''),
    CONSTRAINT ck_school_registration_contact_email_not_blank CHECK (btrim(contact_email) <> ''),
    CONSTRAINT ck_school_registration_nonce_not_blank CHECK (btrim(submission_nonce) <> '')
);

CREATE UNIQUE INDEX uq_school_registration_nonce
    ON school_registration_request (submission_nonce);

CREATE INDEX ix_school_registration_status
    ON school_registration_request (status, submitted_at);

CREATE INDEX ix_school_registration_contact_email
    ON school_registration_request (contact_email_normalized, submitted_at DESC);
