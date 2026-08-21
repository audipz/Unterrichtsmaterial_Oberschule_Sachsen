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

## Materialkatalog für Schüler

Der Materialkatalog enthält alle für den angemeldeten Schüler grundsätzlich zugänglichen Lernmaterialien. Eine Lehrerzuweisung ist dafür nicht erforderlich.

```text
GET /api/v1/my/catalog
GET /api/v1/my/catalog?gradeLevel=7
GET /api/v1/my/catalog?kind=WORKBOOK
GET /api/v1/my/catalog?learningUnit=k7-binaersystem
```

Ein Katalogeintrag kann beispielsweise enthalten:

```json
{
  "materialReleaseId": "...",
  "kind": "WORKBOOK",
  "title": "Binärsystem – Arbeitsblatt",
  "gradeLevel": 7,
  "learningUnitId": "k7-binaersystem",
  "started": false,
  "assignedByTeacher": false
}
```

Die serverseitige Sichtbarkeitslogik entscheidet, welche Materialien für Klassenstufe beziehungsweise Schule angeboten werden.

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

## Arbeitsmaterial selbstständig starten – Schüler

Ein Schüler kann ein sichtbares Arbeitsheft oder Arbeitsblatt direkt aus dem Materialkatalog starten.

```text
POST /api/v1/my/workbooks
```

Beispielrequest:

```json
{
  "materialReleaseId": "..."
}
```

Der Endpoint ist idempotent im fachlichen Sinn: Existiert für denselben Schüler und dieselbe Materialfassung bereits eine aktive persönliche Instanz, wird keine zweite unbeabsichtigte Kopie angelegt.

Mögliche Antwort:

```json
{
  "workbookId": "...",
  "origin": "SELF_STARTED",
  "status": "IN_PROGRESS"
}
```

## Arbeitsheft zuweisen – Lehrer

Eine Lehrerzuweisung ist weiterhin möglich, beispielsweise damit Material in einem Kurs hervorgehoben wird.

```text
POST /api/v1/courses/{courseId}/workbook-assignments
GET  /api/v1/courses/{courseId}/workbook-assignments
```

Die Zuweisung ist keine technische Voraussetzung für die Bearbeitung eines grundsätzlich sichtbaren Materials.

Wenn ein Schüler dasselbe Material zuvor bereits selbst gestartet hat, soll die bestehende persönliche Instanz nach Möglichkeit weiterverwendet und mit der Zuweisung verknüpft werden, statt die bisherigen Antworten zu duplizieren.

## Arbeitsheft – Schüler

```text
GET /api/v1/my/workbooks
GET /api/v1/my/workbooks/{workbookId}
PUT /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}
```

`GET /api/v1/my/workbooks` enthält sowohl selbst gestartete als auch zugewiesene Arbeitsmaterialien.

Autosave verwendet optimistische Versionierung. Eine veraltete `clientRevision` führt zu `409 Conflict`, statt neuere Daten still zu überschreiben.

## Antwortrevisionen

```text
GET  /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/revisions
POST /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/restore/{revisionId}
```

## Übungen selbstständig starten – Schüler

Sichtbare Übungssammlungen können ohne Lehrerzuweisung gestartet werden:

```text
POST /api/v1/my/exercises
```

Beispielrequest:

```json
{
  "materialReleaseId": "..."
}
```

Mögliche Antwort:

```json
{
  "exerciseSessionId": "...",
  "origin": "SELF_STARTED"
}
```

Die persönliche Übungsinstanz speichert den Lernstand. Ein Schüler kann sie verlassen und später fortsetzen.

## Übungen zuweisen – Lehrer

```text
POST /api/v1/courses/{courseId}/exercise-assignments
GET  /api/v1/courses/{courseId}/exercise-assignments
```

Lehrerzuweisungen können Materialien hervorheben oder in den Unterricht einordnen. Übungen haben keinen Prüfungsmodus, kein Zeitlimit und keine benotete Abgabe.

## Übungen – Schüler

```text
GET /api/v1/my/exercises
GET /api/v1/my/exercises/{exerciseSessionId}
PUT /api/v1/my/exercises/{exerciseSessionId}/answers/{exerciseId}
POST /api/v1/my/exercises/{exerciseSessionId}/answers/{exerciseId}/check
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

## Meine Materialien

Für die Startseite beziehungsweise das Schülerdashboard kann ein zusammengefasster Endpoint angeboten werden:

```text
GET /api/v1/my/learning-materials
```

Er liefert beispielsweise:

- selbst gestartete Arbeitsblätter,
- selbst gestartete Übungen,
- durch Lehrer zugewiesene Materialien,
- zuletzt bearbeitete Materialien,
- Bearbeitungsstand,
- Herkunft `SELF_STARTED` oder `TEACHER_ASSIGNED`.

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