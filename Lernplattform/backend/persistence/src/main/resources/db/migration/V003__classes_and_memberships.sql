CREATE TABLE school_class (
    id UUID PRIMARY KEY,
    school_id UUID NOT NULL REFERENCES school(id),
    name VARCHAR(80) NOT NULL,
    grade_level SMALLINT NOT NULL,
    school_year VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID REFERENCES account(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES account(id),
    CONSTRAINT ck_school_class_grade_level CHECK (grade_level BETWEEN 1 AND 13),
    CONSTRAINT ck_school_class_status CHECK (status IN ('ACTIVE', 'ARCHIVED', 'SOFT_DELETED')),
    CONSTRAINT uq_school_class_id_school UNIQUE (id, school_id)
);

CREATE UNIQUE INDEX uq_school_class_name_year_active
    ON school_class (school_id, lower(name), school_year)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_school_class_school ON school_class (school_id);
CREATE INDEX ix_school_class_deleted_at ON school_class (deleted_at)
    WHERE deleted_at IS NOT NULL;

CREATE TABLE school_class_membership (
    id UUID PRIMARY KEY,
    school_class_id UUID NOT NULL,
    school_id UUID NOT NULL,
    student_school_membership_id UUID NOT NULL,
    valid_from DATE NOT NULL,
    valid_until DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by UUID REFERENCES account(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES account(id),
    CONSTRAINT fk_class_membership_class_school
        FOREIGN KEY (school_class_id, school_id)
        REFERENCES school_class(id, school_id),
    CONSTRAINT fk_class_membership_student_school
        FOREIGN KEY (student_school_membership_id, school_id)
        REFERENCES school_membership(id, school_id),
    CONSTRAINT ck_school_class_membership_dates CHECK (valid_until IS NULL OR valid_until >= valid_from),
    CONSTRAINT ck_school_class_membership_status CHECK (status IN ('ACTIVE', 'ENDED', 'SOFT_DELETED'))
);

CREATE UNIQUE INDEX uq_school_class_membership_active
    ON school_class_membership (school_class_id, student_school_membership_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;

CREATE INDEX ix_school_class_membership_student
    ON school_class_membership (student_school_membership_id)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_school_class_membership_class
    ON school_class_membership (school_class_id)
    WHERE deleted_at IS NULL;

CREATE TABLE class_teacher (
    school_class_id UUID NOT NULL,
    school_id UUID NOT NULL,
    teacher_school_membership_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by UUID REFERENCES account(id),
    deleted_at TIMESTAMPTZ,
    deleted_by UUID REFERENCES account(id),
    PRIMARY KEY (school_class_id, teacher_school_membership_id),
    CONSTRAINT fk_class_teacher_class_school
        FOREIGN KEY (school_class_id, school_id)
        REFERENCES school_class(id, school_id),
    CONSTRAINT fk_class_teacher_membership_school
        FOREIGN KEY (teacher_school_membership_id, school_id)
        REFERENCES school_membership(id, school_id)
);

CREATE INDEX ix_class_teacher_teacher
    ON class_teacher (teacher_school_membership_id)
    WHERE deleted_at IS NULL;

-- Fachregel (im transaktionalen Domain-Service zu erzwingen):
-- Eine ACTIVE-Klasse muss jederzeit mindestens einen aktiven class_teacher besitzen.
-- Alle class_teacher einer Klasse sind gleichberechtigt verantwortlich.
