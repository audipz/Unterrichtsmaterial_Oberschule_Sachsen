# Domänenmodell

## Überblick

Das Domänenmodell trennt Organisation, Identität, Lernmaterial und individuelle Schülerarbeit.

```text
School
├── User
│   └── UserRole
├── SchoolClass
│   └── SchoolClassMembership
├── Course
│   ├── CourseTeacher
│   └── CourseStudent
└── Lernbereich
    ├── MaterialRelease
    ├── WorkbookAssignment
    └── AssessmentAssignment
```

Individuelle Schülerdaten hängen an Zuweisungen und Versuchen, nicht direkt an den Markdown-Quelldateien.

## School

Zentrale Mandanteneinheit.

Wichtige Felder:

```text
id
name
short_name
status
created_at
updated_at
deleted_at
deleted_by
```

## User

Ein Benutzer gehört mindestens einer Schule an. Für die erste Ausbaustufe wird eine primäre Schulzugehörigkeit vorgesehen.

Wichtige Felder:

```text
id
school_id
username
password_hash
first_name
last_name
status
must_change_password
last_login_at
created_at
updated_at
deleted_at
deleted_by
```

Eindeutigkeit:

```text
UNIQUE(school_id, username)
```

## UserRole

Mehrfachrollen sind ausdrücklich erlaubt.

```text
user_id
school_id
role
```

Mögliche Rollen:

```text
STUDENT
TEACHER
SCHOOL_ADMIN
SYSTEM_ADMIN
```

## SchoolClass

Organisatorische Klasse, zum Beispiel `7a`.

```text
id
school_id
name
grade_level
school_year
status
created_at
updated_at
deleted_at
deleted_by
```

## SchoolClassMembership

Die Klassenzugehörigkeit wird historisiert und nicht direkt als `class_id` am Schüler gespeichert.

```text
id
school_class_id
student_id
valid_from
valid_until
status
created_at
updated_at
deleted_at
deleted_by
```

Dadurch kann ein Schüler die Klasse wechseln, ohne dass frühere Zuordnungen überschrieben werden.

## Course

Ein konkreter Unterrichtskurs, zum Beispiel `Informatik 7a – 2026/27`.

```text
id
school_id
name
subject
school_year
status
created_at
updated_at
deleted_at
deleted_by
```

## CourseTeacher

```text
course_id
teacher_id
role
```

Damit können mehrere Lehrer einen Kurs betreuen.

## CourseStudent

```text
course_id
student_id
status
joined_at
left_at
```

Ein Kurs ist damit von der organisatorischen Klasse getrennt.

## Material

Logische Einheit eines Lernmaterials.

```text
id
kind
stable_key
title
grade_level
subject
```

`kind` kann sein:

```text
REFERENCE
WORKBOOK
EXERCISE_SET
ASSESSMENT
```

`stable_key` bleibt über Materialversionen hinweg stabil, zum Beispiel:

```text
informatik-k7-binaersystem
```

## MaterialRelease

Konkrete veröffentlichte Fassung eines Materials.

```text
id
material_id
version
source_commit
published_at
status
content_manifest
```

Dadurch kann eine Schülerzuweisung auf einer bekannten Materialfassung bleiben, auch wenn das Repository später weiterentwickelt wird.

## LearningUnit

Fachlicher Themenbereich, zum Beispiel `Binärsystem`.

```text
id
stable_key
title
grade_level
sort_order
parent_id
```

Eine LearningUnit kann Nachschlagewerk, Arbeitsheft, Übungen und Lernkontrollen bündeln.

## Exercise

Einzelne Aufgabe.

```text
id
stable_key
learning_unit_id
type
title
prompt
max_points
difficulty
auto_gradable
metadata
```

Mögliche Typen:

```text
SHORT_TEXT
LONG_TEXT
NUMBER
SINGLE_CHOICE
MULTIPLE_CHOICE
GAP_TEXT
MATCHING
ORDERING
TABLE
CODE
FILE_UPLOAD
DRAWING
```

## WorkbookAssignment

Zuweisung eines Arbeitshefts beziehungsweise Lernbereichs an einen Kurs oder Schüler.

```text
id
course_id
material_release_id
available_from
due_at
status
created_by
```

## StudentWorkbook

Individuelle Instanz eines Schülers.

```text
id
assignment_id
student_id
status
started_at
last_activity_at
completed_at
```

## Answer

Aktuelle Antwort eines Schülers auf eine Aufgabe.

```text
id
student_workbook_id
exercise_id
answer_data
status
created_at
updated_at
```

`answer_data` kann als `jsonb` gespeichert werden, damit unterschiedliche Aufgabentypen ohne eine Vielzahl leerer Spalten unterstützt werden können.

## AnswerRevision

Historie wichtiger Antwortstände.

```text
id
answer_id
revision_no
answer_data
created_at
created_by
```

Die aktuelle Antwort bleibt in `Answer`, historische Stände in `AnswerRevision`.

## Assessment

Definition einer Lernkontrolle.

```text
id
material_release_id
learning_unit_id
title
attempt_limit
time_limit_seconds
result_visibility
solution_visibility
```

## AssessmentAssignment

Freigabe einer Lernkontrolle für einen Kurs oder einzelne Schüler.

```text
id
assessment_id
course_id
available_from
available_until
attempt_limit_override
time_limit_override
created_by
```

## AssessmentAttempt

Ein konkreter Versuch eines Schülers.

```text
id
assignment_id
student_id
attempt_no
status
started_at
submitted_at
expires_at
score
max_score
```

## AssessmentAnswer

```text
id
attempt_id
exercise_id
answer_data
auto_score
teacher_score
feedback
```

Automatische und manuelle Bewertung bleiben getrennt.

## TeacherFeedback

Feedback kann unabhängig von der eigentlichen Schülerantwort gespeichert werden.

```text
id
teacher_id
target_type
target_id
text
created_at
updated_at
```

## Attachment

Dateiuploads werden über Metadaten in PostgreSQL verwaltet.

```text
id
school_id
owner_user_id
purpose
storage_key
original_filename
content_type
size_bytes
sha256
created_at
deleted_at
```

Die Binärdaten liegen außerhalb der relationalen Tabellen in einem geeigneten Storage.

## Audit-Felder

Für administrative Entitäten werden mindestens vorgesehen:

```text
created_at
created_by
updated_at
updated_by
deleted_at
deleted_by
```

Nicht jede fachliche Schüleraktion benötigt ein vollständiges Administrations-Audit. Kritische Verwaltungsaktionen dagegen schon.

## Soft-Delete-Regeln

### Benutzer

Soft Delete bedeutet:

- Login sofort sperren,
- normales Listing ausschließen,
- Lernstände für drei Monate erhalten,
- Reaktivierung durch `SCHOOL_ADMIN` ermöglichen.

### Klasse

Soft Delete einer Klasse:

- Klasse aus normalen Ansichten entfernen,
- Mitgliedschaften erhalten,
- Schülerkonten nicht löschen,
- Reaktivierung inklusive noch vorhandener Memberships ermöglichen.

### Schüler verlässt Schule

Dies ist ein eigener fachlicher Vorgang:

- aktive Klassenmitgliedschaften beenden,
- aktive Kursmitgliedschaften beenden,
- Schülerkonto soft-löschen,
- Login sperren,
- Purge-Frist starten.

## Purge

Der Purge arbeitet regelmäßig und idempotent.

Grundbedingung:

```text
deleted_at + 3 Kalendermonate <= now
```

Vor dem physischen Löschen wird geprüft, welche abhängigen Daten physisch gelöscht, anonymisiert oder aus fachlichen Gründen bereits zuvor entkoppelt werden müssen.
