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
└── LearningUnit
    ├── MaterialRelease
    ├── WorkbookAssignment
    └── ExerciseAssignment
```

Individuelle Lerndaten hängen an Zuweisungen und persönlichen Arbeitsständen, nicht direkt an den Markdown-Quelldateien.

## Datenschutzgrundsatz für Benutzerkonten

Für **keinen Benutzer** sollen echte Vor- und Nachnamen erforderlich sein. Das gilt für Schüler, Lehrer und Schul-Administratoren gleichermaßen.

Ein Benutzerkonto benötigt technisch nur Schule, Benutzername, Passwort beziehungsweise Passwort-Hash, Rollen und organisatorische Zuordnungen sowie einen Fantasienamen als Anzeigenamen.

> **Grundsatz:** So wenig personenbezogene Daten wie möglich speichern. Für die Nutzung der Lernplattform sind Klarnamen nicht erforderlich.

## Fantasiename / Anzeigename

Beim initialen Anlegen eines Schüler- oder Lehrerkontos wird automatisch ein Fantasiename erzeugt und gespeichert. Der Fantasiename ist damit von Beginn an vorhanden und wird in der Lernoberfläche anstelle eines Klarnamens verwendet.

Beispiele:

```text
PixelFuchs
CodeOtter
DatenFalke
LogikPanda
```

Der Benutzer darf seinen Fantasienamen später beliebig ändern, sofern die Eindeutigkeitsregeln eingehalten werden.

### Eindeutigkeit für Schüler

Der Fantasiename eines Schülers muss innerhalb jeder aktuell zugeordneten Klasse eindeutig sein. Ein Schüler, der Mitglied der Klasse `7a` ist, darf also keinen Fantasienamen wählen, den bereits ein anderer aktiver Benutzer in `7a` verwendet.

Die Eindeutigkeit wird case-insensitive und auf einer normalisierten Form geprüft. `PixelFuchs`, `pixelfuchs` und `PIXELFUCHS` gelten damit als derselbe Name.

### Eindeutigkeit für Lehrer

Der Fantasiename eines Lehrers muss in allen Klassen eindeutig sein, denen der Lehrer aktuell zugewiesen ist. Dadurch können Schüler innerhalb ihrer Klasse Lehrer eindeutig erkennen.

Ist ein Lehrer beispielsweise den Klassen `7a`, `7b` und `8a` zugeordnet, darf sein Fantasiename in keiner dieser Klassen bereits von einem dort sichtbaren aktiven Benutzer verwendet werden.

### Konflikt beim Klassenwechsel oder bei neuer Zuweisung

Ein Klassenwechsel oder die Zuweisung eines Lehrers zu einer weiteren Klasse kann einen bereits bestehenden Namenskonflikt erzeugen. Die Operation darf dann nicht stillschweigend durchgeführt werden. Vor Abschluss der Zuordnung muss ein konfliktfreier Fantasiename gewählt beziehungsweise automatisch vorgeschlagen werden.

Die Eindeutigkeit ist eine fachliche Regel über aktive Memberships und wird deshalb serverseitig in einem Domain-Service geprüft. Ein einfacher globaler `UNIQUE(display_name)`-Constraint wäre zu streng und ein `UNIQUE(class_id, display_name)` am Benutzer wäre wegen Mehrfachzuordnungen nicht ausreichend.

## School

Zentrale Mandanteneinheit.

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

Gemeinsame technische Benutzeridentität.

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

`display_name` enthält den aktuellen Fantasienamen. `display_name_normalized` dient der robusten Eindeutigkeitsprüfung.

Für Benutzer werden keine Pflichtfelder `first_name` oder `last_name` vorgesehen.

Login-Eindeutigkeit:

```text
UNIQUE(school_id, username)
```

Der Benutzername ist ein technischer Loginname und muss nicht dem echten Namen entsprechen.

## UserRole

Mehrfachrollen sind erlaubt.

```text
user_id
school_id
role
```

Rollen:

```text
STUDENT
TEACHER
SCHOOL_ADMIN
SYSTEM_ADMIN
```

Ein Lehrer kann gleichzeitig `TEACHER` und `SCHOOL_ADMIN` sein.

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

Historisierte Klassenzugehörigkeit.

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

## Course

Konkreter Unterrichtskurs, zum Beispiel `Informatik 7a – 2026/27`.

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

## CourseStudent

```text
course_id
student_id
status
joined_at
left_at
```

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

Eine laufende Schülerzuweisung bleibt dadurch auf einer bekannten Materialfassung, auch wenn das Repository weiterentwickelt wird.

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

Eine LearningUnit kann Nachschlagewerk, Arbeitsheft und Übungen bündeln.

## Exercise

Einzelne Lern- oder Arbeitsaufgabe.

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

`self_checkable` bedeutet ausschließlich, dass eine Übung eine unmittelbare Lernrückmeldung geben kann. Es handelt sich nicht um Benotung.

## WorkbookAssignment

Zuweisung eines Arbeitshefts beziehungsweise Lernbereichs an einen Kurs oder Schüler.

```text
id
course_id
material_release_id
available_from
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
revision
created_at
updated_at
```

`answer_data` kann als `jsonb` gespeichert werden, damit unterschiedliche Aufgabentypen unterstützt werden können.

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

## ExerciseAssignment

Optionale Zuweisung eines Übungssets an einen Kurs oder Schüler.

```text
id
course_id
material_release_id
available_from
status
created_by
```

## StudentExerciseProgress

Persönlicher Lernstand innerhalb einer Übung.

```text
id
assignment_id
student_id
exercise_id
status
attempt_count
last_answer_data
last_feedback
last_activity_at
```

Übungsversuche dienen ausschließlich dem Lernen. Sie bilden keine Prüfungsversuche oder Noten ab.

## LearningProgress

Zusammengefasster Bearbeitungs- und Lernfortschritt pro Schüler und Lernbereich.

```text
id
student_id
learning_unit_id
workbook_status
exercise_status
last_activity_at
```

Fortschritt ist eine Lernhilfe und keine automatische Leistungsbewertung.

## TeacherFeedback

Feedback wird getrennt von der Schülerantwort gespeichert.

```text
id
teacher_id
target_type
target_id
text
created_at
updated_at
```

In der Oberfläche wird der Fantasiename der Lehrkraft angezeigt.

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

Audit-Felder referenzieren technische Benutzer-IDs. Auch dafür sind keine Klarnamen erforderlich.

## Soft-Delete-Regeln

### Benutzer

- Login sofort sperren,
- normales Listing ausschließen,
- Lernstände für drei Monate erhalten,
- Reaktivierung durch `SCHOOL_ADMIN` ermöglichen.

### Klasse

- Klasse aus normalen Ansichten entfernen,
- Mitgliedschaften erhalten,
- Schülerkonten nicht löschen,
- Reaktivierung inklusive noch vorhandener Memberships ermöglichen.

### Schüler verlässt Schule

- aktive Klassenmitgliedschaften beenden,
- aktive Kursmitgliedschaften beenden,
- Schülerkonto soft-löschen,
- Login sperren,
- Purge-Frist starten.

## Purge

Der Purge arbeitet regelmäßig und idempotent.

```text
deleted_at + 3 Kalendermonate <= now
```

Vor dem physischen Löschen wird geprüft, welche abhängigen personenbezogenen Daten physisch gelöscht oder entkoppelt werden müssen.
