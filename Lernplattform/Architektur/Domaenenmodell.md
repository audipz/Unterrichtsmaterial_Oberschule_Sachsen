# Domänenmodell

> **Aktueller Stand:** Lehreridentität und Schulzugehörigkeit sind getrennt. Ein Lehrer kann mehreren Schulen angehören; schulbezogene Rollen gelten ausschließlich innerhalb der jeweiligen Schule. Details siehe `Identitaet_Mandanten_und_Lehrer.md`.

## Überblick

```text
School
├── schoolSlug
├── SchoolClass
│   ├── StudentClassMembership
│   └── ClassTeacherAssignment
└── TeacherSchoolMembership
    └── TeacherSchoolRole

Identity
├── StudentAccount
│   └── genau eine aktive School-Zuordnung
└── TeacherAccount
    └── eine oder mehrere TeacherSchoolMemberships
```

Lernmaterial und individuelle Lernstände bleiben davon getrennt.

## Datenschutzgrundsatz

Klarnamen sind weder für Schüler noch Lehrer erforderlich.

Alle Benutzer erhalten einen Fantasienamen/Anzeigenamen. Bei Lehrern ist zusätzlich eine E-Mail-Adresse erforderlich, weil sie als globale Login-Identität und für Account-Recovery dient. Die E-Mail wird Schülern nicht als sichtbarer Name angezeigt.

## School

```text
id
name
school_slug
status
created_at
updated_at
deleted_at
deleted_by
```

`school_slug` ist systemweit eindeutig und stabil:

```text
UNIQUE(school_slug)
```

Beispiel:

```text
name        = Oberschule Musterstadt
school_slug = oberschule-musterstadt
```

## StudentAccount

Schüler bleiben schulbezogene Identitäten ohne verpflichtende E-Mail-Adresse.

```text
id
school_id
username
display_name
display_name_normalized
password_hash
status
must_change_password
last_login_at
created_at
updated_at
deleted_at
deleted_by
```

Login-Eindeutigkeit:

```text
UNIQUE(school_id, username)
```

Login erfolgt im Kontext des Schulpfads über:

```text
schoolSlug + username + password
```

## TeacherAccount

Ein Lehrer ist eine schulübergreifende Identität.

```text
id
email
email_normalized
email_verified_at
display_name
display_name_normalized
status
last_login_at
created_at
updated_at
deleted_at
deleted_by
```

E-Mail-Eindeutigkeit:

```text
UNIQUE(email_normalized)
```

Ein Lehreraccount besitzt **keine direkte `school_id`**.

## TeacherSchoolMembership

Ordnet einen Lehrer einer Schule zu.

```text
id
teacher_id
school_id
status
joined_at
left_at
created_at
created_by
updated_at
updated_by
deleted_at
deleted_by
```

Aktive Doppelzuordnungen desselben Lehrers zur selben Schule sind nicht erlaubt.

## TeacherSchoolRole

Schulbezogene Rollen eines Lehrers:

```text
teacher_school_membership_id
role
```

Rollen:

```text
TEACHER
SCHOOL_ADMIN
```

`SCHOOL_ADMIN` gilt immer nur für die Schule dieser Membership.

`SYSTEM_ADMIN` wird als systemweite Rolle getrennt modelliert.

## Fantasiename / Anzeigename

Beim initialen Anlegen wird ein Fantasiename erzeugt. Er kann später geändert werden.

### Schüler

Der Fantasiename muss innerhalb der aktuell zugeordneten Klasse eindeutig sein.

### Lehrer

Der Fantasiename muss in allen Klassen eindeutig sein, in denen der Lehrer innerhalb der jeweiligen Schule sichtbar beziehungsweise zugeordnet ist.

Bei einem Lehrer mit mehreren Schulen wird die Eindeutigkeit je Schul-/Klassenkontext geprüft, nicht global über alle Schulen.

## SchoolClass

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

Eine aktive Klasse muss jederzeit mindestens einen aktiven zuständigen Lehrer besitzen.

## StudentClassMembership

Historisierte Klassenzugehörigkeit eines Schülers:

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

Klassenwechsel erzeugen Historie und verändern nicht das Schülerkonto oder seine Lernstände.

## ClassTeacherAssignment

Zuordnung von Lehrern zu einer Klasse:

```text
id
school_class_id
teacher_school_membership_id
responsibility
status
created_at
created_by
updated_at
updated_by
deleted_at
deleted_by
```

`responsibility`:

```text
RESPONSIBLE
ADDITIONAL
```

Fachregel:

> Jede aktive Klasse besitzt mindestens einen aktiven `RESPONSIBLE`-Lehrer.

Alle aktiven Lehrer einer Schule dürfen fachlich alle Klassen der Schule bearbeiten. Die explizite Klassen-Zuordnung dient deshalb primär der Zuständigkeit und Benachrichtigung, nicht als allgemeine Zugriffssperre.

## Lehrer aus Schule entfernen

Eine Entfernung beendet die `TeacherSchoolMembership`, nicht sofort den globalen Lehreraccount.

Sie ist nur zulässig, wenn:

- der Lehrer in keiner Klasse der einzige zuständige Lehrer ist,
- nach Entfernung jede aktive Klasse weiterhin mindestens einen zuständigen Lehrer besitzt,
- bei `SCHOOL_ADMIN` mindestens ein anderer aktiver Schuladmin verbleibt.

Ein globaler Lehreraccount kann erst soft-gelöscht werden, wenn keine aktive Schulmitgliedschaft mehr existiert.

## Benachrichtigung bei Klassenbearbeitung

Bearbeitet ein Lehrer eine Klasse, für die er nicht selbst zuständig ist, wird ein fachliches Ereignis erzeugt. Mindestens die zuständigen Lehrer der Klasse können daraus eine interne Benachrichtigung erhalten.

```text
ClassActivity
  → ResponsibleTeacherNotification
```

## Material

```text
id
kind
stable_key
title
grade_level
subject
```

`kind`:

```text
REFERENCE
WORKBOOK
EXERCISE_SET
```

## MaterialRelease

```text
id
material_id
version
source_commit
published_at
status
content_manifest
```

## LearningUnit

```text
id
stable_key
title
grade_level
sort_order
parent_id
```

## Exercise

```text
id
stable_key
learning_unit_id
type
title
prompt
difficulty
self_checkable
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

## StudentWorkbook

```text
id
student_id
material_release_id
origin
status
started_at
last_activity_at
completed_at
```

## Answer

```text
id
student_workbook_id
exercise_id
answer_data
status
revision
created_at
updated_at
```

## AnswerRevision

```text
id
answer_id
revision_no
answer_data
created_at
created_by
```

## StudentExerciseProgress

```text
id
student_id
exercise_id
status
attempt_count
last_answer_data
last_feedback
last_activity_at
```

## LearningProgress

```text
id
student_id
learning_unit_id
workbook_status
exercise_status
last_activity_at
```

Fortschritt dient ausschließlich dem Lernen und ist keine formale Leistungsbewertung.

## TeacherFeedback

```text
id
teacher_id
school_id
target_type
target_id
text
created_at
updated_at
```

`school_id` dokumentiert den Schulkontext, in dem der Lehrer das Feedback erstellt hat.

## Attachment

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

## Soft Delete

### Schüler

- Login sperren,
- aktive Klassenmitgliedschaften beenden,
- Lernstände für Wiederherstellungsfrist erhalten,
- nach drei Monaten kontrollierter Purge.

### Lehrer-Schulzuordnung

- Membership beenden beziehungsweise soft-löschen,
- globalen Lehreraccount nicht löschen, solange andere aktive Schulen bestehen.

### Lehreraccount

- erst soft-löschbar, wenn keine aktive `TeacherSchoolMembership` mehr existiert.

### Klasse

- nur löschbar, wenn fachliche Regeln eingehalten werden,
- Schülerkonten bleiben erhalten.

## Purge

```text
deleted_at + 3 Kalendermonate <= now
```

Vor physischem Löschen werden Abhängigkeiten und noch bestehende Schulzuordnungen geprüft.
