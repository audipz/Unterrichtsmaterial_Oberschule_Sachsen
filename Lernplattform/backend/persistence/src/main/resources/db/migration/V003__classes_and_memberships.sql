CREATE TABLE school_class (
    id UUID PRIMARY KEY,
    school_id UUID NOT NULL REFERENCES school(id),
    name VARCHAR(80) NOT NULL,
    grade_level SMALLINT NOT NULL,
    school_year VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES app_user(id),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID REFERENCES app_user(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES app_user(id),
    CONSTRAINT ck_school_class_grade_level CHECK (grade_level BETWEEN 1 AND 13),
    CONSTRAINT ck_school_class_status CHECK (status IN ('ACTIVE', 'SOFT_DELETED'))
);

CREATE UNIQUE INDEX uq_school_class_name_year_active
    ON school_class (school_id, lower(name), school_year)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_school_class_school ON school_class (school_id);
CREATE INDEX ix_school_class_deleted_at ON school_class (deleted_at)
    WHERE deleted_at IS NOT NULL;

CREATE TABLE school_class_membership (
    id UUID PRIMARY KEY,
    school_class_id UUID NOT NULL REFERENCES school_class(id),
    student_id UUID NOT NULL REFERENCES app_user(id),
    valid_from DATE NOT NULL,
    valid_until DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES app_user(id),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID REFERENCES app_user(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES app_user(id),
    CONSTRAINT ck_school_class_membership_dates CHECK (valid_until IS NULL OR valid_until >= valid_from),
    CONSTRAINT ck_school_class_membership_status CHECK (status IN ('ACTIVE', 'ENDED', 'SOFT_DELETED'))
);

CREATE UNIQUE INDEX uq_school_class_membership_active
    ON school_class_membership (school_class_id, student_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;

CREATE INDEX ix_school_class_membership_student
    ON school_class_membership (student_id)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_school_class_membership_class
    ON school_class_membership (school_class_id)
    WHERE deleted_at IS NULL;

CREATE TABLE class_teacher (
    school_class_id UUID NOT NULL REFERENCES school_class(id),
    teacher_id UUID NOT NULL REFERENCES app_user(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES app_user(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES app_user(id),
    PRIMARY KEY (school_class_id, teacher_id)
);

CREATE INDEX ix_class_teacher_teacher
    ON class_teacher (teacher_id)
    WHERE deleted_at IS NULL;
