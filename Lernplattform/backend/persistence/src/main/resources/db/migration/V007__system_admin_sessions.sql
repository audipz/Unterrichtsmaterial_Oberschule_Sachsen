CREATE TABLE system_admin_session (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    token_hash VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at TIMESTAMPTZ NOT NULL,
    last_seen_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_system_admin_session_expiry CHECK (expires_at > created_at)
);

CREATE UNIQUE INDEX uq_system_admin_session_token_hash
    ON system_admin_session (token_hash);

CREATE INDEX ix_system_admin_session_expiry
    ON system_admin_session (expires_at);
