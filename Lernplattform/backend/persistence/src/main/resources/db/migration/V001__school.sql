CREATE TABLE school (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    short_name VARCHAR(80) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,
    CONSTRAINT ck_school_status CHECK (status IN ('ACTIVE', 'SOFT_DELETED'))
);

CREATE UNIQUE INDEX uq_school_short_name_active
    ON school (lower(short_name))
    WHERE deleted_at IS NULL;

CREATE INDEX ix_school_deleted_at ON school (deleted_at)
    WHERE deleted_at IS NOT NULL;
