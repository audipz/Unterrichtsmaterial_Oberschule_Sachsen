# REST-API – erster Entwurf

## Grundsätze

- JSON über HTTPS
- OAuth 2 / OpenID Connect
- Autorisierung serverseitig mit Spring Security
- Mandant `School` wird aus der authentifizierten Identität abgeleitet
- keine frei manipulierbare `schoolId` als Vertrauensanker
- UUIDs als externe IDs
- konsistente Fehlerobjekte
- Schreiboperationen möglichst idempotent oder mit klarer Konfliktbehandlung

Basis:

```text
/api/v1
```

## Authentifizierter Benutzer

```text
GET /api/v1/me
```

## Schulen – SYSTEM_ADMIN

```text
GET    /api/v1/admin/schools
POST   /api/v1/admin/schools
GET    /api/v1/admin/schools/{schoolId}
PATCH  /api/v1/admin/schools/{schoolId}
DELETE /api/v1/admin/schools/{schoolId}
```

Das normale Löschen ist Soft Delete.

## Benutzerverwaltung – SCHOOL_ADMIN

```text
GET   /api/v1/admin/users
POST  /api/v1/admin/users/students
POST  /api/v1/admin/users/teachers
GET   /api/v1/admin/users/{userId}
PATCH /api/v1/admin/users/{userId}
```

Rollenverwaltung:

```text
PUT    /api/v1/admin/users/{userId}/roles/{role}
DELETE /api/v1/admin/users/{userId}/roles/{role}
```

`SYSTEM_ADMIN` kann nicht durch einen Schul-Admin vergeben werden.

## Schüler verlässt Schule / Reaktivierung

```text
POST /api/v1/admin/students/{studentId}/leave-school
POST /api/v1/admin/students/{studentId}/restore
```

`leave-school` beendet aktive Mitgliedschaften, sperrt den Login und startet den Soft-Delete-Lebenszyklus.

## Papierkorb

```text
GET /api/v1/admin/trash
GET /api/v1/admin/trash?type=STUDENT
GET /api/v1/admin/trash?type=CLASS
```

Antwort enthält mindestens:

```text
id
type
displayName
deletedAt
deletedBy
purgeAt
restorable
conflict
```

## Klassen

```text
GET    /api/v1/classes
POST   /api/v1/classes
GET    /api/v1/classes/{classId}
PATCH  /api/v1/classes/{classId}
DELETE /api/v1/classes/{classId}
POST   /api/v1/classes/{classId}/restore
```

Mitgliedschaften:

```text
GET    /api/v1/classes/{classId}/students
POST   /api/v1/classes/{classId}/students/{studentId}
DELETE /api/v1/classes/{classId}/students/{studentId}
```

Klassenwechsel:

```text
POST /api/v1/students/{studentId}/move-class
```

## Kurse

```text
GET    /api/v1/courses
POST   /api/v1/courses
GET    /api/v1/courses/{courseId}
PATCH  /api/v1/courses/{courseId}
POST   /api/v1/courses/{courseId}/archive
```

Lehrer und Schüler:

```text
POST   /api/v1/courses/{courseId}/teachers/{teacherId}
DELETE /api/v1/courses/{courseId}/teachers/{teacherId}
POST   /api/v1/courses/{courseId}/students/{studentId}
DELETE /api/v1/courses/{courseId}/students/{studentId}
```

## Materialien

```text
GET /api/v1/materials
GET /api/v1/materials/{materialId}
GET /api/v1/materials/{materialId}/releases
GET /api/v1/material-releases/{releaseId}
```

Filter:

```text
GET /api/v1/materials?kind=REFERENCE
GET /api/v1/materials?kind=WORKBOOK
GET /api/v1/materials?kind=EXERCISE_SET
```

## Lernbereiche

```text
GET /api/v1/learning-units
GET /api/v1/learning-units/{unitId}
```

Beispiel:

```json
{
  "id": "...",
  "title": "Binärsystem",
  "referenceMaterial": {},
  "workbookMaterial": {},
  "exerciseSet": {}
}
```

## Arbeitsheft zuweisen – Lehrer

```text
POST /api/v1/courses/{courseId}/workbook-assignments
GET  /api/v1/courses/{courseId}/workbook-assignments
```

## Arbeitsheft – Schüler

```text
GET /api/v1/my/workbooks
GET /api/v1/my/workbooks/{workbookId}
PUT /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}
```

Autosave verwendet optimistische Versionierung. Eine veraltete `clientRevision` führt zu `409 Conflict`, statt neuere Daten still zu überschreiben.

## Antwortrevisionen

```text
GET  /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/revisions
POST /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/restore/{revisionId}
```

## Übungen zuweisen – Lehrer

```text
POST /api/v1/courses/{courseId}/exercise-assignments
GET  /api/v1/courses/{courseId}/exercise-assignments
```

Übungen haben keinen Prüfungsmodus, kein Zeitlimit und keine benotete Abgabe.

## Übungen – Schüler

```text
GET /api/v1/my/exercises
GET /api/v1/my/exercises/{assignmentId}
PUT /api/v1/my/exercises/{assignmentId}/answers/{exerciseId}
POST /api/v1/my/exercises/{assignmentId}/answers/{exerciseId}/check
```

`check` liefert ausschließlich eine Lernrückmeldung für dafür geeignete Aufgaben. Es erzeugt keine Note oder formale Bewertung.

Mögliche Antwort:

```json
{
  "correct": false,
  "feedback": "Prüfe noch einmal den Stellenwert der zweiten Ziffer.",
  "reference": {
    "learningUnit": "k7-binaersystem",
    "anchor": "binaerzahlen-in-dezimalzahlen-umwandeln"
  }
}
```

## Lernfortschritt

Schüler:

```text
GET /api/v1/my/progress
GET /api/v1/my/progress/{learningUnitId}
```

Lehrer:

```text
GET /api/v1/courses/{courseId}/progress
GET /api/v1/courses/{courseId}/students/{studentId}/progress
GET /api/v1/courses/{courseId}/students/{studentId}/workbooks
```

Fortschritt beschreibt Bearbeitung und Lernaktivität, nicht eine Zeugnis- oder Prüfungsleistung.

## Feedback

```text
POST   /api/v1/teacher-feedback
PATCH  /api/v1/teacher-feedback/{feedbackId}
DELETE /api/v1/teacher-feedback/{feedbackId}
```

Feedback ist getrennt von der Schülerantwort.

## Dateien

```text
POST   /api/v1/files
GET    /api/v1/files/{fileId}
DELETE /api/v1/files/{fileId}
```

Upload- und Download-Berechtigungen werden anhand von Schule, Besitzer und fachlichem Bezug geprüft.

## Fehlerformat

Ein konsistentes Fehlerformat wird vorgesehen, beispielsweise orientiert an Problem Details.

## Nicht Bestandteil der API

Ausdrücklich nicht vorgesehen sind Endpunkte für:

- Lernkontrollen,
- Prüfungsversuche,
- Zeitlimits für Prüfungen,
- Punkte/Noten,
- benotete Abgaben,
- Prüfungsaufsicht.

## Noch offene API-Entscheidungen

- genauer OAuth/OIDC-Endpunktaufbau,
- Cursor- oder Seitenpagination,
- Suche und Filterkonventionen,
- Batch-Import von Schülern,
- Batch-Zuweisung kompletter Klassen zu Kursen,
- Offline-Synchronisation,
- Streaming/Server-Sent Events nur falls später fachlich nötig.
