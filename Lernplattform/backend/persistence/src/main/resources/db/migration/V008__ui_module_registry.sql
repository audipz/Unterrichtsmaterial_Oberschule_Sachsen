CREATE TABLE ui_module (
    id UUID PRIMARY KEY,
    internal_key VARCHAR(120) NOT NULL,
    artifact_path VARCHAR(500) NOT NULL,
    integrity_sha384 VARCHAR(160),
    account_type VARCHAR(30) NOT NULL,
    required_school_role VARCHAR(40),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    version VARCHAR(60) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_ui_module_internal_key UNIQUE (internal_key),
    CONSTRAINT ck_ui_module_account_type CHECK (account_type IN ('SYSTEM', 'TEACHER', 'STUDENT')),
    CONSTRAINT ck_ui_module_status CHECK (status IN ('ACTIVE', 'DISABLED')),
    CONSTRAINT ck_ui_module_path_not_blank CHECK (btrim(artifact_path) <> ''),
    CONSTRAINT ck_ui_module_version_not_blank CHECK (btrim(version) <> '')
);

-- Opaque IDs are deliberately unrelated to feature names. The internal keys never leave the backend.
INSERT INTO ui_module (id, internal_key, artifact_path, account_type, required_school_role, version) VALUES
('9d736c88-48eb-4af4-80ef-84adffb7283f', 'system.registration-review', '/ui-artifacts/9d736c88-48eb-4af4-80ef-84adffb7283f/1/index.js', 'SYSTEM', NULL, '1'),
('61868074-759c-48f2-a32b-c281558376ca', 'teacher.classes', '/ui-artifacts/61868074-759c-48f2-a32b-c281558376ca/1/index.js', 'TEACHER', NULL, '1'),
('754a1826-5e1c-4fc7-8ea4-8eb059832cd6', 'teacher.students', '/ui-artifacts/754a1826-5e1c-4fc7-8ea4-8eb059832cd6/1/index.js', 'TEACHER', NULL, '1'),
('d6dedfef-5cc7-4be0-9ab1-d8d40ce1950d', 'teacher.progress', '/ui-artifacts/d6dedfef-5cc7-4be0-9ab1-d8d40ce1950d/1/index.js', 'TEACHER', NULL, '1'),
('1e904e72-38bd-450b-ae7f-cdc7fa4d7eef', 'school.administration', '/ui-artifacts/1e904e72-38bd-450b-ae7f-cdc7fa4d7eef/1/index.js', 'TEACHER', 'SCHOOL_ADMIN', '1'),
('3f9f936f-b2d2-4e04-99be-b4ebeb3a4f42', 'student.learning', '/ui-artifacts/3f9f936f-b2d2-4e04-99be-b4ebeb3a4f42/1/index.js', 'STUDENT', NULL, '1'),
('91c27363-e37c-40cf-b17f-b2fd9c196584', 'student.progress', '/ui-artifacts/91c27363-e37c-40cf-b17f-b2fd9c196584/1/index.js', 'STUDENT', NULL, '1');

CREATE INDEX ix_ui_module_active_account_type
    ON ui_module (account_type)
    WHERE status = 'ACTIVE';
