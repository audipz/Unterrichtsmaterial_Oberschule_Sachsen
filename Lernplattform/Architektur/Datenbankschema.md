# PostgreSQL-Schema und Flyway-Struktur

## Ziel

Dieses Dokument konkretisiert das relationale Datenmodell für PostgreSQL. Es dient als Grundlage für Flyway-Migrationen, Spring Data/JPA und Integrationstests.

Grundsätze:

- UUIDs als technische Primärschlüssel,
- Schule als Mandant,
- Soft Delete mit `deleted_at` und `deleted_by`,
- fachliche Statuswerte als klar definierte Enums beziehungsweise `varchar` mit Check-Constraints,
- JSONB nur dort, wo strukturvariable Nutzdaten sinnvoll sind,
- harte Fremdschlüssel für organisatorische Beziehungen,
- keine Klarnamenpflicht,
- Fantasienamen werden serverseitig erzeugt und auf Klassenebene eindeutig gehalten,
- alle Zeitstempel als `timestamptz`.

## Erweiterungen

Für die erste Version wird mindestens benötigt:

```sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;
```

`pgcrypto` kann für UUID-Erzeugung beziehungsweise kryptografische Hilfsfunktionen genutzt werden. Anwendungsschlüssel und Passwort-Hashes werden trotzdem in der Anwendung erzeugt beziehungsweise verwaltet.

## Tabelle `school`

```sql
CREATE TABLE school (
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL,
    short_name varchar(80) NOT NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    updated_at timestamptz NOT NULL,
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_school_status CHECK (status IN ('ACTIVE', 'DISABLED', 'SOFT_DELETED'))
);
```

Aktive Kurznamen sollen innerhalb der Plattform eindeutig sein:

```sql
CREATE UNIQUE INDEX uq_school_short_name_active
    ON school (lower(short_name))
    WHERE deleted_at IS NULL;
```

## Tabelle `app_user`

`user` wird vermieden, weil der Begriff in SQL-Kontexten leicht missverständlich ist.

```sql
CREATE TABLE app_user (
    id uuid PRIMARY KEY,
    school_id uuid NOT NULL REFERENCES school(id),
    username varchar(120) NOT NULL,
    display_name varchar(120) NOT NULL,
    display_name_normalized varchar(120) NOT NULL,
    password_hash varchar(500) NOT NULL,
    status varchar(32) NOT NULL,
    must_change_password boolean NOT NULL DEFAULT true,
    last_login_at timestamptz NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    updated_at timestamptz NOT NULL,
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_app_user_status CHECK (status IN ('ACTIVE', 'DISABLED', 'SOFT_DELETED'))
);
```

Loginname ist innerhalb einer Schule eindeutig:

```sql
CREATE UNIQUE INDEX uq_app_user_school_username_active
    ON app_user (school_id, lower(username))
    WHERE deleted_at IS NULL;
```

`display_name` ist absichtlich **nicht global eindeutig**.

## Tabelle `user_role`

```sql
CREATE TABLE user_role (
    user_id uuid NOT NULL REFERENCES app_user(id),
    school_id uuid NOT NULL REFERENCES school(id),
    role varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    PRIMARY KEY (user_id, school_id, role),
    CONSTRAINT ck_user_role_role CHECK (role IN ('STUDENT', 'TEACHER', 'SCHOOL_ADMIN', 'SYSTEM_ADMIN'))
);
```

Für `SYSTEM_ADMIN` kann `school_id` in einer späteren Migration gegebenenfalls in eine getrennte systemweite Rollenzuordnung ausgelagert werden. Für die erste Version bleibt das Modell bewusst einfach.

## Tabelle `school_class`

```sql
CREATE TABLE school_class (
    id uuid PRIMARY KEY,
    school_id uuid NOT NULL REFERENCES school(id),
    name varchar(80) NOT NULL,
    grade_level smallint NULL,
    school_year varchar(20) NOT NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    updated_at timestamptz NOT NULL,
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_school_class_status CHECK (status IN ('ACTIVE', 'ARCHIVED', 'SOFT_DELETED'))
);
```

```sql
CREATE UNIQUE INDEX uq_school_class_active
    ON school_class (school_id, lower(name), school_year)
    WHERE deleted_at IS NULL;
```

## Tabelle `school_class_membership`

```sql
CREATE TABLE school_class_membership (
    id uuid PRIMARY KEY,
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    student_id uuid NOT NULL REFERENCES app_user(id),
    valid_from date NOT NULL,
    valid_until date NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    updated_at timestamptz NOT NULL,
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_class_membership_status CHECK (status IN ('ACTIVE', 'ENDED', 'SOFT_DELETED')),
    CONSTRAINT ck_class_membership_dates CHECK (valid_until IS NULL OR valid_until >= valid_from)
);
```

Ein Schüler darf nicht mehrfach gleichzeitig aktiv in derselben Klasse eingetragen sein:

```sql
CREATE UNIQUE INDEX uq_class_membership_active
    ON school_class_membership (school_class_id, student_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;
```

## Lehrerzuordnung zu Klassen

Für die Eindeutigkeit der Fantasienamen von Lehrern auf Klassenebene ist eine direkte Lehrer-Klassen-Zuordnung sinnvoll.

```sql
CREATE TABLE school_class_teacher (
    id uuid PRIMARY KEY,
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    teacher_id uuid NOT NULL REFERENCES app_user(id),
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_school_class_teacher_status CHECK (status IN ('ACTIVE', 'ENDED', 'SOFT_DELETED'))
);
```

```sql
CREATE UNIQUE INDEX uq_school_class_teacher_active
    ON school_class_teacher (school_class_id, teacher_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;
```

## Eindeutigkeit von Fantasienamen

Die Regel lautet:

- Schüler: Fantasiename innerhalb jeder aktiven Klasse eindeutig,
- Lehrer: Fantasiename innerhalb jeder aktiv zugeordneten Klasse eindeutig,
- Groß-/Kleinschreibung und triviale Schreibvarianten werden über `display_name_normalized` abgefangen.

Weil Benutzer mehreren Klassen zugeordnet sein können und Lehrer und Schüler in derselben Klasse sichtbar sind, wird diese Regel nicht nur mit einem einfachen Unique-Index auf `app_user` gelöst.

Die Prüfung erfolgt in einem transaktionalen Domain-Service vor:

- Änderung des Fantasienamens,
- Aufnahme eines Schülers in eine Klasse,
- Klassenwechsel,
- Zuweisung eines Lehrers zu einer Klasse,
- Reaktivierung von Benutzer oder Klasse.

Zur Vermeidung von Race Conditions wird zusätzlich ein Sperrkonzept benötigt. Vorgesehen ist, die betroffene `school_class`-Zeile während der Prüfung mit `SELECT ... FOR UPDATE` zu sperren. Danach wird gegen alle aktiven Schüler- und Lehrerzuordnungen dieser Klasse geprüft.

Pseudocode:

```text
lock class
collect active users visible in class
compare display_name_normalized
reject conflict with 409
write change
commit
```

Damit bleibt die Regel auch bei parallelen Requests zuverlässig.

## Tabelle `course`

```sql
CREATE TABLE course (
    id uuid PRIMARY KEY,
    school_id uuid NOT NULL REFERENCES school(id),
    name varchar(160) NOT NULL,
    subject varchar(120) NOT NULL,
    school_year varchar(20) NOT NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL,
    updated_at timestamptz NOT NULL,
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_course_status CHECK (status IN ('ACTIVE', 'ARCHIVED', 'SOFT_DELETED'))
);
```

## Kurszuordnungen

```sql
CREATE TABLE course_teacher (
    course_id uuid NOT NULL REFERENCES course(id),
    teacher_id uuid NOT NULL REFERENCES app_user(id),
    role varchar(40) NOT NULL DEFAULT 'TEACHER',
    created_at timestamptz NOT NULL,
    PRIMARY KEY (course_id, teacher_id)
);

CREATE TABLE course_student (
    course_id uuid NOT NULL REFERENCES course(id),
    student_id uuid NOT NULL REFERENCES app_user(id),
    status varchar(32) NOT NULL,
    joined_at timestamptz NOT NULL,
    left_at timestamptz NULL,
    PRIMARY KEY (course_id, student_id),
    CONSTRAINT ck_course_student_status CHECK (status IN ('ACTIVE', 'LEFT'))
);
```

## Materialien

```sql
CREATE TABLE material (
    id uuid PRIMARY KEY,
    kind varchar(32) NOT NULL,
    stable_key varchar(200) NOT NULL,
    title varchar(300) NOT NULL,
    grade_level smallint NULL,
    subject varchar(120) NOT NULL,
    CONSTRAINT ck_material_kind CHECK (kind IN ('REFERENCE', 'WORKBOOK', 'EXERCISE_SET')),
    CONSTRAINT uq_material_stable_key UNIQUE (stable_key)
);

CREATE TABLE material_release (
    id uuid PRIMARY KEY,
    material_id uuid NOT NULL REFERENCES material(id),
    version varchar(80) NOT NULL,
    source_commit varchar(80) NOT NULL,
    published_at timestamptz NOT NULL,
    status varchar(32) NOT NULL,
    content_manifest jsonb NOT NULL,
    CONSTRAINT ck_material_release_status CHECK (status IN ('DRAFT', 'PUBLISHED', 'RETIRED')),
    CONSTRAINT uq_material_release_version UNIQUE (material_id, version)
);
```

## Lernbereiche

```sql
CREATE TABLE learning_unit (
    id uuid PRIMARY KEY,
    stable_key varchar(200) NOT NULL UNIQUE,
    title varchar(300) NOT NULL,
    grade_level smallint NULL,
    sort_order integer NOT NULL,
    parent_id uuid NULL REFERENCES learning_unit(id)
);
```

## Aufgaben

```sql
CREATE TABLE exercise (
    id uuid PRIMARY KEY,
    stable_key varchar(220) NOT NULL UNIQUE,
    learning_unit_id uuid NOT NULL REFERENCES learning_unit(id),
    type varchar(40) NOT NULL,
    title varchar(300) NULL,
    prompt text NOT NULL,
    difficulty varchar(32) NULL,
    self_checkable boolean NOT NULL DEFAULT false,
    metadata jsonb NOT NULL DEFAULT '{}'::jsonb,
    CONSTRAINT ck_exercise_type CHECK (type IN (
        'SHORT_TEXT', 'LONG_TEXT', 'NUMBER', 'SINGLE_CHOICE', 'MULTIPLE_CHOICE',
        'GAP_TEXT', 'MATCHING', 'ORDERING', 'TABLE', 'CODE', 'FILE_UPLOAD', 'DRAWING'
    )),
    CONSTRAINT ck_exercise_difficulty CHECK (difficulty IS NULL OR difficulty IN ('BASIC', 'STANDARD', 'ADVANCED'))
);
```

## Arbeitsheftzuweisungen

```sql
CREATE TABLE workbook_assignment (
    id uuid PRIMARY KEY,
    course_id uuid NULL REFERENCES course(id),
    material_release_id uuid NOT NULL REFERENCES material_release(id),
    available_from timestamptz NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NOT NULL REFERENCES app_user(id),
    CONSTRAINT ck_workbook_assignment_status CHECK (status IN ('ACTIVE', 'ARCHIVED'))
);
```

Für selbst gestartete Materialien kann `course_id` `NULL` sein beziehungsweise eine persönliche Instanz ohne vorherige Lehrerzuweisung entstehen.

## Persönliches Arbeitsheft

```sql
CREATE TABLE student_workbook (
    id uuid PRIMARY KEY,
    assignment_id uuid NULL REFERENCES workbook_assignment(id),
    material_release_id uuid NOT NULL REFERENCES material_release(id),
    student_id uuid NOT NULL REFERENCES app_user(id),
    status varchar(32) NOT NULL,
    started_at timestamptz NOT NULL,
    last_activity_at timestamptz NOT NULL,
    completed_at timestamptz NULL,
    CONSTRAINT ck_student_workbook_status CHECK (status IN ('IN_PROGRESS', 'COMPLETED', 'ARCHIVED'))
);
```

Ein Schüler soll pro Materialversion nicht unbeabsichtigt mehrere aktive Instanzen erhalten:

```sql
CREATE UNIQUE INDEX uq_student_workbook_release_active
    ON student_workbook (student_id, material_release_id)
    WHERE status IN ('IN_PROGRESS', 'COMPLETED');
```

Dadurch kann eine spätere Lehrerzuweisung eine bereits selbst gestartete Instanz weiterverwenden.

## Antworten und Revisionen

```sql
CREATE TABLE answer (
    id uuid PRIMARY KEY,
    student_workbook_id uuid NOT NULL REFERENCES student_workbook(id),
    exercise_id uuid NOT NULL REFERENCES exercise(id),
    answer_data jsonb NOT NULL,
    status varchar(32) NOT NULL,
    revision bigint NOT NULL DEFAULT 1,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT ck_answer_status CHECK (status IN ('EMPTY', 'IN_PROGRESS', 'ANSWERED')),
    CONSTRAINT uq_answer_workbook_exercise UNIQUE (student_workbook_id, exercise_id)
);

CREATE TABLE answer_revision (
    id uuid PRIMARY KEY,
    answer_id uuid NOT NULL REFERENCES answer(id),
    revision_no bigint NOT NULL,
    answer_data jsonb NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NOT NULL REFERENCES app_user(id),
    CONSTRAINT uq_answer_revision UNIQUE (answer_id, revision_no)
);
```

Optimistische Versionierung erfolgt über `answer.revision`.

## Übungsfortschritt

```sql
CREATE TABLE exercise_assignment (
    id uuid PRIMARY KEY,
    course_id uuid NULL REFERENCES course(id),
    material_release_id uuid NOT NULL REFERENCES material_release(id),
    available_from timestamptz NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL,
    created_by uuid NULL REFERENCES app_user(id),
    CONSTRAINT ck_exercise_assignment_status CHECK (status IN ('ACTIVE', 'ARCHIVED'))
);

CREATE TABLE student_exercise_progress (
    id uuid PRIMARY KEY,
    assignment_id uuid NULL REFERENCES exercise_assignment(id),
    material_release_id uuid NOT NULL REFERENCES material_release(id),
    student_id uuid NOT NULL REFERENCES app_user(id),
    exercise_id uuid NOT NULL REFERENCES exercise(id),
    status varchar(32) NOT NULL,
    attempt_count integer NOT NULL DEFAULT 0,
    last_answer_data jsonb NULL,
    last_feedback jsonb NULL,
    last_activity_at timestamptz NOT NULL,
    CONSTRAINT ck_student_exercise_progress_status CHECK (status IN ('NOT_STARTED', 'IN_PROGRESS', 'PRACTICED')),
    CONSTRAINT uq_student_exercise_progress UNIQUE (student_id, material_release_id, exercise_id)
);
```

## Lernfortschritt

```sql
CREATE TABLE learning_progress (
    id uuid PRIMARY KEY,
    student_id uuid NOT NULL REFERENCES app_user(id),
    learning_unit_id uuid NOT NULL REFERENCES learning_unit(id),
    workbook_status varchar(32) NOT NULL,
    exercise_status varchar(32) NOT NULL,
    last_activity_at timestamptz NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT uq_learning_progress UNIQUE (student_id, learning_unit_id)
);
```

`learning_progress` ist ein abgeleiteter Komfortzustand. Die fachlich maßgeblichen Rohdaten bleiben Antworten und Übungsfortschritte.

## Lehrerfeedback

```sql
CREATE TABLE teacher_feedback (
    id uuid PRIMARY KEY,
    teacher_id uuid NOT NULL REFERENCES app_user(id),
    target_type varchar(40) NOT NULL,
    target_id uuid NOT NULL,
    text text NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_teacher_feedback_target_type CHECK (target_type IN ('ANSWER', 'WORKBOOK'))
);
```

Die referenzielle Integrität von `target_id` wird im Domain-Service geprüft, weil polymorphe Fremdschlüssel relational nicht direkt abbildbar sind. Falls sich nur wenige Zieltypen bewähren, kann später auf getrennte Tabellen umgestellt werden.

## Dateianhänge

```sql
CREATE TABLE attachment (
    id uuid PRIMARY KEY,
    school_id uuid NOT NULL REFERENCES school(id),
    owner_user_id uuid NOT NULL REFERENCES app_user(id),
    purpose varchar(60) NOT NULL,
    storage_key varchar(500) NOT NULL UNIQUE,
    original_filename varchar(500) NOT NULL,
    content_type varchar(200) NOT NULL,
    size_bytes bigint NOT NULL,
    sha256 varchar(64) NOT NULL,
    created_at timestamptz NOT NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_attachment_size CHECK (size_bytes >= 0)
);
```

## Wichtige Indizes

Neben Unique-Indizes werden mindestens vorgesehen:

```sql
CREATE INDEX ix_app_user_school_status
    ON app_user (school_id, status)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_class_membership_student
    ON school_class_membership (student_id, status)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_class_teacher_teacher
    ON school_class_teacher (teacher_id, status)
    WHERE deleted_at IS NULL;

CREATE INDEX ix_course_student_student
    ON course_student (student_id, status);

CREATE INDEX ix_student_workbook_student
    ON student_workbook (student_id, last_activity_at DESC);

CREATE INDEX ix_answer_workbook
    ON answer (student_workbook_id);

CREATE INDEX ix_exercise_progress_student
    ON student_exercise_progress (student_id, last_activity_at DESC);
```

JSONB-GIN-Indizes werden erst eingeführt, wenn konkrete Abfragen dies erfordern. Sie werden nicht vorsorglich auf jedes JSONB-Feld gelegt.

## Soft Delete und Purge

Soft Delete verändert Status und setzt:

```text
deleted_at
deleted_by
```

Normale Repository-Abfragen müssen `deleted_at IS NULL` berücksichtigen.

Der automatische Purge sucht Datensätze mit:

```text
deleted_at + INTERVAL '3 months' <= now()
```

Die Löschung erfolgt fachlich geordnet. Für Benutzer beispielsweise:

1. noch aktive Mitgliedschaften sicher beenden,
2. Attachments nach Storage-Regel entfernen,
3. persönliche Lernstände löschen beziehungsweise erforderliche Entkopplung durchführen,
4. Rollenzuordnungen entfernen,
5. Benutzer physisch löschen.

Die konkrete Reihenfolge wird in Integrationstests abgesichert.

## Flyway-Struktur

Vorgesehen ist:

```text
backend/persistence/src/main/resources/db/migration/
├── V001__extensions.sql
├── V002__schools_and_users.sql
├── V003__roles.sql
├── V004__classes.sql
├── V005__class_memberships_and_teachers.sql
├── V006__courses.sql
├── V007__materials.sql
├── V008__learning_units_and_exercises.sql
├── V009__workbooks_and_answers.sql
├── V010__exercise_progress.sql
├── V011__teacher_feedback.sql
├── V012__attachments.sql
└── V013__indexes.sql
```

Neue produktive Migrationen werden niemals nachträglich inhaltlich verändert. Änderungen erfolgen ausschließlich durch neue Migrationen.

## Tests

Für das Datenbankschema werden mindestens benötigt:

- Testcontainers mit echter PostgreSQL-Version,
- Flyway-Migration von leerer Datenbank,
- Constraint-Tests,
- Mandantentrennungs-Tests auf Repository-/Service-Ebene,
- Fantasienamen-Konflikttests inklusive paralleler Requests,
- Soft-Delete-/Restore-Tests,
- Purge-Integrationstests,
- Optimistic-Locking-Tests für Antworten.

H2 wird nicht als Ersatz für PostgreSQL in Integrationstests verwendet, weil PostgreSQL-spezifische Eigenschaften wie Partial Indexes, JSONB und Sperrverhalten relevant sind.
