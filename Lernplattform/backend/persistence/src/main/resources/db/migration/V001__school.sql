CREATE TABLE school (
    id UUID PRIMARY KEY,
    slug VARCHAR(120) NOT NULL,
    name VARCHAR(200) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,
    CONSTRAINT ck_school_status CHECK (status IN ('ACTIVE', 'DISABLED', 'SOFT_DELETED')),
    CONSTRAINT ck_school_slug_not_blank CHECK (btrim(slug) <> ''),
    CONSTRAINT ck_school_name_not_blank CHECK (btrim(name) <> '')
);

CREATE UNIQUE INDEX uq_school_slug
    ON school (lower(slug));

CREATE INDEX ix_school_deleted_at ON school (deleted_at)
    WHERE deleted_at IS NOT NULL;
