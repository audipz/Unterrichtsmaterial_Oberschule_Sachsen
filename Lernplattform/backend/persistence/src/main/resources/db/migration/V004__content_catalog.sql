CREATE TABLE content_release (
    id UUID PRIMARY KEY,
    release_key VARCHAR(120) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    published_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_content_release_key UNIQUE (release_key),
    CONSTRAINT ck_content_release_status CHECK (status IN ('DRAFT', 'PUBLISHED', 'RETIRED'))
);

CREATE TABLE content_item (
    id UUID PRIMARY KEY,
    content_key VARCHAR(180) NOT NULL,
    content_type VARCHAR(40) NOT NULL,
    subject_key VARCHAR(80) NOT NULL DEFAULT 'INFORMATIK',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_content_item_key UNIQUE (content_key),
    CONSTRAINT ck_content_item_type CHECK (content_type IN ('TOPIC', 'SECTION', 'EXERCISE', 'WORKSHEET', 'REFERENCE'))
);

CREATE TABLE content_variant (
    id UUID PRIMARY KEY,
    content_item_id UUID NOT NULL REFERENCES content_item(id),
    content_release_id UUID NOT NULL REFERENCES content_release(id),
    language_tag VARCHAR(35) NOT NULL,
    title VARCHAR(300) NOT NULL,
    body_markdown TEXT NOT NULL,
    content_hash VARCHAR(64) NOT NULL,
    metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_content_variant UNIQUE (content_item_id, content_release_id, language_tag),
    CONSTRAINT ck_content_variant_language CHECK (btrim(language_tag) <> ''),
    CONSTRAINT ck_content_variant_title CHECK (btrim(title) <> ''),
    CONSTRAINT ck_content_variant_hash CHECK (content_hash ~ '^[0-9a-fA-F]{64}$')
);

CREATE INDEX ix_content_variant_item_language
    ON content_variant (content_item_id, language_tag);

CREATE TABLE content_solution (
    content_variant_id UUID PRIMARY KEY REFERENCES content_variant(id) ON DELETE CASCADE,
    solution_data JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE content_target (
    id UUID PRIMARY KEY,
    content_item_id UUID NOT NULL REFERENCES content_item(id) ON DELETE CASCADE,
    federal_state_key VARCHAR(80) NOT NULL DEFAULT 'ALL',
    school_type_key VARCHAR(80) NOT NULL DEFAULT 'ALL',
    grade_level SMALLINT,
    subject_key VARCHAR(80) NOT NULL DEFAULT 'INFORMATIK',
    CONSTRAINT ck_content_target_grade CHECK (grade_level IS NULL OR grade_level BETWEEN 1 AND 13),
    CONSTRAINT uq_content_target UNIQUE (content_item_id, federal_state_key, school_type_key, grade_level, subject_key)
);

CREATE INDEX ix_content_target_selection
    ON content_target (federal_state_key, school_type_key, grade_level, subject_key);

CREATE TABLE content_manifest (
    id UUID PRIMARY KEY,
    manifest_key VARCHAR(180) NOT NULL,
    content_release_id UUID NOT NULL REFERENCES content_release(id),
    language_tag VARCHAR(35) NOT NULL,
    fallback_language_tag VARCHAR(35),
    federal_state_key VARCHAR(80) NOT NULL DEFAULT 'ALL',
    school_type_key VARCHAR(80) NOT NULL DEFAULT 'ALL',
    grade_level SMALLINT,
    subject_key VARCHAR(80) NOT NULL DEFAULT 'INFORMATIK',
    title VARCHAR(300) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_content_manifest UNIQUE (content_release_id, manifest_key),
    CONSTRAINT ck_content_manifest_grade CHECK (grade_level IS NULL OR grade_level BETWEEN 1 AND 13)
);

CREATE TABLE content_manifest_entry (
    manifest_id UUID NOT NULL REFERENCES content_manifest(id) ON DELETE CASCADE,
    content_item_id UUID NOT NULL REFERENCES content_item(id),
    position INTEGER NOT NULL,
    parent_content_item_id UUID REFERENCES content_item(id),
    required BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (manifest_id, content_item_id),
    CONSTRAINT uq_content_manifest_position UNIQUE (manifest_id, position),
    CONSTRAINT ck_content_manifest_position CHECK (position >= 0)
);

COMMENT ON TABLE content_variant IS 'Published runtime content. Repository paths, branch names and commit metadata are deliberately not part of the learner-facing content model.';
COMMENT ON TABLE content_solution IS 'Internal solution/evaluation data. Must never be exposed by learner content endpoints.';
COMMENT ON TABLE content_manifest IS 'Published composition of reusable content items for a target context such as state, school type, grade and language.';
