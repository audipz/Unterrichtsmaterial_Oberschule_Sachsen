# PostgreSQL-Schema und Flyway-Struktur

## Ziel

Das relationale Modell trennt globale Identität, Schulzugehörigkeit, schulbezogene Rechte, Klassenverantwortung und Lernstände.

Grundsätze:

- UUIDs als technische Primärschlüssel,
- `school_slug` als stabiler eindeutiger technischer Schulschlüssel für URLs,
- Accounts sind nicht direkt an eine Schule gebunden,
- Schülerlogin ist schulbezogen,
- Lehrerlogin ist global über E-Mail,
- Rechte gelten innerhalb einer SchoolMembership,
- Soft Delete und historisierte Memberships,
- konkrete Schülerantworten werden von Lehreransichten getrennt,
- Lernstände werden über `learning_period` historisiert,
- `timestamptz` für Zeitstempel,
- Testcontainers mit echtem PostgreSQL.

## school

```sql
CREATE TABLE school (
    id uuid PRIMARY KEY,
    name varchar(200) NOT NULL,
    school_slug varchar(120) NOT NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid NULL,
    updated_at timestamptz NOT NULL DEFAULT now(),
    updated_by uuid NULL,
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_school_status CHECK (status IN ('ACTIVE','DISABLED','SOFT_DELETED'))
);

CREATE UNIQUE INDEX uq_school_slug_active
    ON school (lower(school_slug))
    WHERE deleted_at IS NULL;
```

## account

Globale Identität eines Schülers oder Lehrers.

```sql
CREATE TABLE account (
    id uuid PRIMARY KEY,
    account_type varchar(20) NOT NULL,
    display_name varchar(120) NOT NULL,
    display_name_normalized varchar(120) NOT NULL,
    status varchar(32) NOT NULL,
    pending_deletion_at timestamptz NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz NULL,
    deleted_by uuid NULL,
    CONSTRAINT ck_account_type CHECK (account_type IN ('STUDENT','TEACHER')),
    CONSTRAINT ck_account_status CHECK (
        status IN ('ACTIVE','DISABLED','PENDING_DELETION','SOFT_DELETED')
    )
);
```

`display_name` ist nicht global eindeutig.

## teacher_identity

```sql
CREATE TABLE teacher_identity (
    account_id uuid PRIMARY KEY REFERENCES account(id),
    email varchar(320) NOT NULL,
    email_normalized varchar(320) NOT NULL,
    email_verified_at timestamptz NULL
);

CREATE UNIQUE INDEX uq_teacher_email
    ON teacher_identity (email_normalized);
```

Nur Accounts mit `account_type = 'TEACHER'` dürfen hier verwendet werden. Diese fachliche Typprüfung wird zusätzlich serverseitig abgesichert.

## school_membership

```sql
CREATE TABLE school_membership (
    id uuid PRIMARY KEY,
    account_id uuid NOT NULL REFERENCES account(id),
    school_id uuid NOT NULL REFERENCES school(id),
    status varchar(32) NOT NULL,
    valid_from date NOT NULL,
    valid_until date NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid NULL REFERENCES account(id),
    ended_at timestamptz NULL,
    ended_by uuid NULL REFERENCES account(id),
    deleted_at timestamptz NULL,
    deleted_by uuid NULL REFERENCES account(id),
    CONSTRAINT ck_school_membership_status CHECK (
        status IN ('ACTIVE','ENDED','SOFT_DELETED')
    ),
    CONSTRAINT ck_school_membership_dates CHECK (
        valid_until IS NULL OR valid_until >= valid_from
    )
);

CREATE UNIQUE INDEX uq_school_membership_active
    ON school_membership (account_id, school_id)
    WHERE status = 'ACTIVE' AND deleted_at IS NULL;
```

Lehrer dürfen mehrere aktive Memberships zu unterschiedlichen Schulen besitzen.

## student_school_login

Logininformationen eines Schülers gehören zur Schulmitgliedschaft.

```sql
CREATE TABLE student_school_login (
    school_membership_id uuid PRIMARY KEY REFERENCES school_membership(id),
    username varchar(120) NOT NULL,
    username_normalized varchar(120) NOT NULL,
    password_hash varchar(500) NOT NULL,
    must_change_password boolean NOT NULL DEFAULT true,
    last_login_at timestamptz NULL
);
```

Da `school_id` in `school_membership` liegt, wird die schulweite Eindeutigkeit über einen denormalisierten/technisch abgesicherten Lookup oder einen transaktionalen Service gewährleistet. Empfohlen ist ein zusätzlicher Login-Lookup:

```sql
CREATE TABLE student_login_lookup (
    school_id uuid NOT NULL REFERENCES school(id),
    username_normalized varchar(120) NOT NULL,
    school_membership_id uuid NOT NULL UNIQUE REFERENCES school_membership(id),
    PRIMARY KEY (school_id, username_normalized)
);
```

Damit ist `(school_id, username)` hart eindeutig und der Login effizient.

## school_role

Schulbezogene Berechtigungen.

```sql
CREATE TABLE school_role (
    school_membership_id uuid NOT NULL REFERENCES school_membership(id),
    role varchar(40) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid NULL REFERENCES account(id),
    PRIMARY KEY (school_membership_id, role),
    CONSTRAINT ck_school_role CHECK (role IN ('SCHOOL_ADMIN'))
);
```

`TEACHER` und `STUDENT` sind Anwendertypen und stehen nicht in dieser Tabelle.

Systemweite Rechte werden separat modelliert:

```sql
CREATE TABLE system_role (
    account_id uuid NOT NULL REFERENCES account(id),
    role varchar(40) NOT NULL,
    PRIMARY KEY (account_id, role),
    CONSTRAINT ck_system_role CHECK (role IN ('SYSTEM_ADMIN'))
);
```

## school_class

```sql
CREATE TABLE school_class (
    id uuid PRIMARY KEY,
    school_id uuid NOT NULL REFERENCES school(id),
    name varchar(80) NOT NULL,
    grade_level smallint NOT NULL,
    school_year varchar(20) NOT NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid NULL REFERENCES account(id),
    updated_at timestamptz NOT NULL DEFAULT now(),
    updated_by uuid NULL REFERENCES account(id),
    deleted_at timestamptz NULL,
    deleted_by uuid NULL REFERENCES account(id),
    CONSTRAINT ck_school_class_status CHECK (
        status IN ('ACTIVE','ARCHIVED','SOFT_DELETED')
    ),
    CONSTRAINT ck_school_class_grade CHECK (grade_level BETWEEN 1 AND 13)
);

CREATE UNIQUE INDEX uq_school_class_active
    ON school_class (school_id, lower(name), school_year)
    WHERE deleted_at IS NULL;
```

## student_class_membership

```sql
CREATE TABLE student_class_membership (
    id uuid PRIMARY KEY,
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    student_school_membership_id uuid NOT NULL REFERENCES school_membership(id),
    valid_from date NOT NULL,
    valid_until date NULL,
    status varchar(32) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid NULL REFERENCES account(id),
    updated_at timestamptz NOT NULL DEFAULT now(),
    updated_by uuid NULL REFERENCES account(id),
    CONSTRAINT ck_student_class_membership_status CHECK (
        status IN ('ACTIVE','ENDED','SOFT_DELETED')
    ),
    CONSTRAINT ck_student_class_membership_dates CHECK (
        valid_until IS NULL OR valid_until >= valid_from
    )
);

CREATE UNIQUE INDEX uq_student_class_membership_active
    ON student_class_membership (school_class_id, student_school_membership_id)
    WHERE status = 'ACTIVE';
```

Beim Klassenwechsel wird die alte Membership beendet und eine neue erzeugt.

## class_teacher_assignment

Alle zugewiesenen Lehrer sind gleichberechtigt.

```sql
CREATE TABLE class_teacher_assignment (
    id uuid PRIMARY KEY,
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    teacher_school_membership_id uuid NOT NULL REFERENCES school_membership(id),
    status varchar(32) NOT NULL,
    assigned_at timestamptz NOT NULL DEFAULT now(),
    assigned_by uuid NULL REFERENCES account(id),
    ended_at timestamptz NULL,
    ended_by uuid NULL REFERENCES account(id),
    CONSTRAINT ck_class_teacher_status CHECK (
        status IN ('ACTIVE','ENDED')
    )
);

CREATE UNIQUE INDEX uq_class_teacher_active
    ON class_teacher_assignment (school_class_id, teacher_school_membership_id)
    WHERE status = 'ACTIVE';
```

Harte Fachregel:

> Eine aktive Klasse muss jederzeit mindestens einen aktiven `class_teacher_assignment` besitzen.

Diese Regel wird transaktional im Domain-Service abgesichert. Beim Entfernen eines Lehrers oder Beenden seiner SchoolMembership werden alle betroffenen Klassen gesperrt und geprüft.

## Fantasienamen-Eindeutigkeit

Der Fantasiename muss für alle in einer Klasse sichtbaren aktiven Benutzer eindeutig sein.

Sichtbar sind:

- aktive Schüler der Klasse,
- aktive zugewiesene Lehrer,
- bei Bedarf weitere Lehrer nur als Bearbeiter, nicht dauerhaft als Klassenmitglied.

Die Prüfung erfolgt case-insensitive über `display_name_normalized`. Vor Änderungen werden die betroffenen Klassenzeilen mit `FOR UPDATE` gesperrt.

## learning_period

```sql
CREATE TABLE learning_period (
    id uuid PRIMARY KEY,
    student_account_id uuid NOT NULL REFERENCES account(id),
    school_membership_id uuid NOT NULL REFERENCES school_membership(id),
    grade_level smallint NOT NULL,
    school_year varchar(20) NOT NULL,
    status varchar(32) NOT NULL,
    started_at timestamptz NOT NULL DEFAULT now(),
    archived_at timestamptz NULL,
    created_by uuid NULL REFERENCES account(id),
    CONSTRAINT ck_learning_period_status CHECK (
        status IN ('ACTIVE','ARCHIVED')
    ),
    CONSTRAINT ck_learning_period_grade CHECK (grade_level BETWEEN 1 AND 13)
);

CREATE UNIQUE INDEX uq_learning_period_active_grade
    ON learning_period (student_account_id, grade_level)
    WHERE status = 'ACTIVE';
```

Ein Reset einer Klassenstufe archiviert die aktive Lernperiode und legt eine neue an. Er löscht keine Antworten.

## learning_period_reset_audit

```sql
CREATE TABLE learning_period_reset_audit (
    id uuid PRIMARY KEY,
    student_account_id uuid NOT NULL REFERENCES account(id),
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    old_learning_period_id uuid NOT NULL REFERENCES learning_period(id),
    new_learning_period_id uuid NOT NULL REFERENCES learning_period(id),
    reset_by_teacher_account_id uuid NOT NULL REFERENCES account(id),
    reset_at timestamptz NOT NULL DEFAULT now(),
    reason varchar(500) NULL
);
```

Nur ein Lehrer mit aktiver `class_teacher_assignment` für die aktuelle Klasse darf diesen Vorgang auslösen.

## Materialien

```sql
CREATE TABLE material (
    id uuid PRIMARY KEY,
    kind varchar(32) NOT NULL,
    stable_key varchar(200) NOT NULL UNIQUE,
    title varchar(300) NOT NULL,
    grade_level smallint NULL,
    subject varchar(120) NOT NULL,
    CONSTRAINT ck_material_kind CHECK (
        kind IN ('REFERENCE','WORKBOOK','EXERCISE_SET')
    )
);

CREATE TABLE material_release (
    id uuid PRIMARY KEY,
    material_id uuid NOT NULL REFERENCES material(id),
    version varchar(80) NOT NULL,
    source_commit varchar(80) NOT NULL,
    published_at timestamptz NOT NULL,
    status varchar(32) NOT NULL,
    content_manifest jsonb NOT NULL,
    UNIQUE (material_id, version)
);
```

## learning_unit und exercise

```sql
CREATE TABLE learning_unit (
    id uuid PRIMARY KEY,
    stable_key varchar(200) NOT NULL UNIQUE,
    title varchar(300) NOT NULL,
    grade_level smallint NULL,
    sort_order integer NOT NULL,
    parent_id uuid NULL REFERENCES learning_unit(id)
);

CREATE TABLE exercise (
    id uuid PRIMARY KEY,
    stable_key varchar(220) NOT NULL UNIQUE,
    learning_unit_id uuid NOT NULL REFERENCES learning_unit(id),
    type varchar(40) NOT NULL,
    title varchar(300) NULL,
    prompt text NOT NULL,
    difficulty varchar(32) NULL,
    self_checkable boolean NOT NULL DEFAULT false,
    metadata jsonb NOT NULL DEFAULT '{}'::jsonb
);
```

## student_workbook

```sql
CREATE TABLE student_workbook (
    id uuid PRIMARY KEY,
    material_release_id uuid NOT NULL REFERENCES material_release(id),
    student_account_id uuid NOT NULL REFERENCES account(id),
    learning_period_id uuid NOT NULL REFERENCES learning_period(id),
    origin varchar(32) NOT NULL,
    status varchar(32) NOT NULL,
    started_at timestamptz NOT NULL,
    last_activity_at timestamptz NOT NULL,
    completed_at timestamptz NULL,
    CONSTRAINT ck_student_workbook_origin CHECK (
        origin IN ('SELF_STARTED','TEACHER_ASSIGNED')
    ),
    CONSTRAINT ck_student_workbook_status CHECK (
        status IN ('IN_PROGRESS','COMPLETED','ARCHIVED')
    )
);
```

## answer und answer_revision

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
    UNIQUE (student_workbook_id, exercise_id)
);

CREATE TABLE answer_revision (
    id uuid PRIMARY KEY,
    answer_id uuid NOT NULL REFERENCES answer(id),
    revision_no bigint NOT NULL,
    answer_data jsonb NOT NULL,
    created_at timestamptz NOT NULL,
    UNIQUE (answer_id, revision_no)
);
```

`answer_data` wird nicht über Lehrer-/Admin-Progress-Endpunkte ausgeliefert.

## learning_progress

Datensparsame Lehreransicht.

```sql
CREATE TABLE learning_progress (
    id uuid PRIMARY KEY,
    student_account_id uuid NOT NULL REFERENCES account(id),
    learning_period_id uuid NOT NULL REFERENCES learning_period(id),
    learning_unit_id uuid NOT NULL REFERENCES learning_unit(id),
    status varchar(32) NOT NULL,
    completed_items integer NOT NULL DEFAULT 0,
    total_items integer NOT NULL DEFAULT 0,
    last_activity_at timestamptz NULL,
    updated_at timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT ck_learning_progress_status CHECK (
        status IN ('NOT_STARTED','IN_PROGRESS','COMPLETE')
    ),
    UNIQUE (student_account_id, learning_period_id, learning_unit_id)
);
```

Lehrer sehen ausschließlich diesen aggregierten Bearbeitungszustand, nicht `answer.answer_data`.

## teacher_notification

```sql
CREATE TABLE teacher_notification (
    id uuid PRIMARY KEY,
    school_class_id uuid NOT NULL REFERENCES school_class(id),
    recipient_teacher_account_id uuid NOT NULL REFERENCES account(id),
    actor_teacher_account_id uuid NOT NULL REFERENCES account(id),
    type varchar(80) NOT NULL,
    reference_id uuid NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    read_at timestamptz NULL
);
```

## Account-Löschvormerkung

Wenn die letzte aktive `school_membership` endet, wird transaktional gesetzt:

```text
account.status = PENDING_DELETION
account.pending_deletion_at = now()
```

Ein regelmäßiger Job prüft zusätzlich:

```sql
SELECT a.id
FROM account a
WHERE a.status IN ('ACTIVE','PENDING_DELETION')
  AND NOT EXISTS (
      SELECT 1
      FROM school_membership sm
      WHERE sm.account_id = a.id
        AND sm.status = 'ACTIVE'
        AND sm.deleted_at IS NULL
  );
```

Nach drei Kalendermonaten ohne aktive SchoolMembership erfolgt der kontrollierte Purge.

## Entfernen eines Lehrers aus einer Schule

Vor Beenden einer Lehrer-SchoolMembership werden in einer Transaktion geprüft:

1. alle aktiven Klassen, denen diese Membership zugeordnet ist,
2. jede Klasse besitzt danach mindestens einen anderen aktiven Lehrer,
3. falls `SCHOOL_ADMIN`: mindestens ein anderer aktiver Schuladmin bleibt.

Erst danach wird die SchoolMembership beendet.

## Klassenwechsel

```text
lock source class + target class
validate same school
validate Fantasiename in target class
end old StudentClassMembership
insert new StudentClassMembership
commit
```

## Schulwechsel

```text
lock account
create target SchoolMembership
reserve target student login
assign target class
end source SchoolMembership
preserve global account + learning history
commit
```

Der Vorgang muss atomar sein.

## Flyway-Neuordnung

Da sich das Kernmodell grundlegend geändert hat und das Projekt noch nicht produktiv ist, werden die bisherigen frühen Migrationen vor dem ersten stabilen Release **bereinigt statt mit einer langen Korrekturkette konserviert**.

Zielreihenfolge:

```text
V001__school.sql
V002__account_and_identity.sql
V003__school_memberships_and_roles.sql
V004__classes_and_teacher_assignments.sql
V005__learning_periods.sql
V006__materials.sql
V007__workbooks_and_answers.sql
V008__learning_progress.sql
V009__notifications.sql
V010__indexes.sql
```

Sobald ein produktiver Datenbestand existiert, werden bestehende Migrationen nicht mehr verändert; ab diesem Zeitpunkt gelten ausschließlich additive/transformierende neue Flyway-Migrationen.

## Tests

Integrationstests verwenden echtes PostgreSQL über Testcontainers. Besonders zu testen sind:

- eindeutiger `school_slug`,
- globale eindeutige Lehrer-E-Mail,
- schulbezogene Eindeutigkeit des Schüler-Benutzernamens,
- mehrere aktive Schulen pro Lehrer,
- schulbezogene Adminrechte,
- Klasse darf letzten Lehrer nicht verlieren,
- Lehrer darf SchoolMembership nicht verlieren, wenn dadurch eine Klasse ohne Lehrer entsteht,
- letzter Schuladmin darf nicht entfernt werden,
- Klassenwechsel mit Fantasienamen-Konflikt,
- Schulwechsel mit Username-Konflikt,
- automatische `PENDING_DELETION`-Vormerkung,
- Reset einer Lernperiode nur durch zugewiesenen Lehrer,
- Lehrer-Progress-Abfragen liefern keine Antwortinhalte.
