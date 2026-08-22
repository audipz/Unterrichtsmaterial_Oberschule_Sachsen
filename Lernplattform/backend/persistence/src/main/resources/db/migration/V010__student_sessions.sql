CREATE TABLE student_session (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    school_membership_id UUID NOT NULL REFERENCES school_membership(id) ON DELETE CASCADE,
    token_hash VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at TIMESTAMPTZ NOT NULL,
    last_seen_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_student_session_expiry CHECK (expires_at > created_at)
);

CREATE UNIQUE INDEX uq_student_session_token_hash ON student_session (token_hash);
CREATE INDEX ix_student_session_expiry ON student_session (expires_at);
CREATE INDEX ix_student_session_membership ON student_session (school_membership_id);
