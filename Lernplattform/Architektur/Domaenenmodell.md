# Domänenmodell

## Leitidee

Identität, Schulzugehörigkeit, Rechte, Klassenverantwortung und Lernhistorie werden getrennt modelliert.

```text
Account
├── StudentIdentity oder TeacherIdentity
├── SchoolMembership(s)
│   └── SchoolRole(s)
├── ClassMembership(s) bei Schülern
└── ClassTeacherAssignment(s) bei Lehrern
```

## Account

Globale technische Identität.

```text
id
account_type              STUDENT | TEACHER
display_name
display_name_normalized
status                    ACTIVE | DISABLED | PENDING_DELETION | SOFT_DELETED
pending_deletion_at
created_at
updated_at
deleted_at
deleted_by
```

Der Account ist nicht fest an eine Schule gebunden.

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

`school_slug` ist systemweit eindeutig und wird im URL-Pfad verwendet.

## SchoolMembership

Historisierte Zugehörigkeit eines Accounts zu einer Schule.

```text
id
account_id
school_id
status                    ACTIVE | ENDED | SOFT_DELETED
valid_from
valid_until
created_at
created_by
ended_at
ended_by
```

Lehrer können mehrere aktive Schulmitgliedschaften besitzen. Schüler können bei einem Schulwechsel kontrolliert von einer Schule in eine andere übertragen werden.

## StudentSchoolLogin

Schulbezogener Login eines Schülers.

```text
school_membership_id
username
password_hash
must_change_password
last_login_at
```

Der Benutzername ist nur innerhalb der Schule eindeutig.

## TeacherIdentity

Globale Lehreridentität.

```text
account_id
email
email_normalized
email_verified_at
```

Die E-Mail-Adresse ist systemweit eindeutig und wird für Anmeldung und Recovery verwendet. Der Fantasiename bleibt der sichtbare Name.

## SchoolRole

Schulbezogene Rechte.

```text
school_membership_id
role
created_at
created_by
```

Für die erste Version:

```text
SCHOOL_ADMIN
```

`TEACHER` ist kein Recht, sondern der Anwendertyp des Accounts. Weitere schulbezogene Rechte können später ergänzt werden.

`SYSTEM_ADMIN` wird separat als systemweite Berechtigung modelliert.

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

Eine aktive Klasse muss jederzeit mindestens einen aktiven zugewiesenen Lehrer besitzen.

## StudentClassMembership

Historisierte Klassenzugehörigkeit.

```text
id
school_class_id
student_school_membership_id
valid_from
valid_until
status
created_at
created_by
updated_at
updated_by
```

Klassenwechsel beenden die alte Membership und legen eine neue an. Account und Lernhistorie bleiben bestehen.

## ClassTeacherAssignment

Mehrere Lehrer einer Klasse sind gleichberechtigt zuständig.

```text
id
school_class_id
teacher_school_membership_id
status
assigned_at
assigned_by
ended_at
ended_by
```

Diese Zuordnung ist keine exklusive Bearbeitungsberechtigung. Jeder Lehrer einer Schule darf grundsätzlich alle Klassen dieser Schule fachlich bearbeiten. Die Zuordnung definiert Verantwortung, Benachrichtigung und besondere Aktionen wie das Neustarten eines Lernzyklus.

## Lernperiode

Lernstände werden zeitlich bzw. organisatorisch in Lernperioden getrennt.

```text
LearningPeriod
--------------
id
student_account_id
school_membership_id
grade_level
school_year
status                    ACTIVE | ARCHIVED
started_at
archived_at
created_by
```

Bei Sitzenbleiben oder bewusstem Neustart einer Klassenstufe wird der bisherige Stand nicht gelöscht. Die aktive Lernperiode wird archiviert und eine neue erzeugt.

Nur ein aktuell der Klasse zugewiesener Lehrer darf diesen Vorgang auslösen.

## LearningPeriodResetAudit

```text
id
student_account_id
old_learning_period_id
new_learning_period_id
school_class_id
reset_by_teacher_account_id
reset_at
reason
```

## Material

```text
id
kind                      REFERENCE | WORKBOOK | EXERCISE_SET
stable_key
title
grade_level
subject
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

## StudentWorkbook

Individuelle Arbeitsheftinstanz.

```text
id
material_release_id
student_account_id
learning_period_id
origin                    SELF_STARTED | TEACHER_ASSIGNED
status
started_at
last_activity_at
completed_at
```

## Answer

Konkrete Antworten bleiben inhaltlich privat.

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

Lehrer und Schuladmins erhalten keinen generellen Zugriff auf `answer_data`.

## AnswerRevision

```text
id
answer_id
revision_no
answer_data
created_at
```

## StudentExerciseProgress

```text
id
student_account_id
learning_period_id
material_release_id
exercise_id
status
attempt_count
last_answer_data
last_feedback
last_activity_at
```

Die Inhalte `last_answer_data` und `last_feedback` sind für den Schüler bestimmt und werden nicht als Lehreransicht verwendet.

## LearningProgress

Für Lehrer wird ein separater, datensparsamer Fortschrittszustand bereitgestellt.

```text
id
student_account_id
learning_period_id
learning_unit_id
status                    NOT_STARTED | IN_PROGRESS | COMPLETE
completed_items
total_items
last_activity_at
updated_at
```

Lehrer sehen nur diesen Bearbeitungs-/Vollständigkeitsstatus, nicht die Schülerantworten.

## TeacherNotification

Relevante Änderungen durch einen nicht zugewiesenen Lehrer können die zugewiesenen Lehrer informieren.

```text
id
school_class_id
recipient_teacher_account_id
actor_teacher_account_id
type
reference_id
created_at
read_at
```

## Account-Lebenszyklus

Wenn die letzte aktive Schulmitgliedschaft endet:

```text
Account → PENDING_DELETION
pending_deletion_at = now
```

Nach drei Monaten ohne neue aktive Schulmitgliedschaft wird der Account kontrolliert endgültig bereinigt.

Ein regelmäßiger Hintergrundjob prüft zusätzlich auf Accounts ohne aktive SchoolMembership, damit keine vergessenen aktiven Accounts bestehen bleiben.

## Lehrer aus einer Schule entfernen

Es wird nur die betreffende `SchoolMembership` beendet. Vorher muss geprüft werden:

- keine aktive Klasse verliert ihren letzten zugewiesenen Lehrer,
- mindestens ein anderer `SCHOOL_ADMIN` bleibt, falls der Lehrer Schuladmin ist.

Hat der Lehrer danach noch andere aktive Schulmitgliedschaften, bleibt sein globaler Account aktiv.

## Schulwechsel eines Schülers

Der Schulwechsel ist ein atomarer Transfer:

```text
neue SchoolMembership
→ neuer schulbezogener Login
→ neue Klassenzuordnung
→ alte SchoolMembership beenden
```

Persönliche Lernhistorie bleibt am globalen Account. Schulinterne Informationen der alten Schule werden nicht automatisch für die neue Schule sichtbar.

## Datenschutzregel für Lehreransicht

Lehrer dürfen sehen:

```text
Lernbereich
Bearbeitungsstatus
Vollständigkeit
Anzahl bearbeitet / gesamt
letzte Aktivität
```

Lehrer dürfen standardmäßig nicht sehen:

```text
Freitextantworten
Codeeingaben
Zeichnungen
konkrete Auswahlantworten
sonstige Antwortinhalte
```

Damit bleibt die Plattform eine Selbstlernplattform und keine Inhaltsüberwachung der Schülerarbeit.
