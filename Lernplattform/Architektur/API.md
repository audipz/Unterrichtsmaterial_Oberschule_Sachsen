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

liefert unter anderem:

```json
{
  "id": "...",
  "schoolId": "...",
  "username": "max.mustermann",
  "roles": ["STUDENT"]
}
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
GET  /api/v1/admin/users
POST /api/v1/admin/users/students
POST /api/v1/admin/users/teachers
GET  /api/v1/admin/users/{userId}
PATCH /api/v1/admin/users/{userId}
```

Rollenverwaltung:

```text
PUT    /api/v1/admin/users/{userId}/roles/{role}
DELETE /api/v1/admin/users/{userId}/roles/{role}
```

Nur zulässige Rollen der eigenen Schule dürfen geändert werden. `SYSTEM_ADMIN` ist hiervon ausgenommen und wird nicht durch Schul-Admins vergeben.

## Schüler löschen und reaktivieren

```text
DELETE /api/v1/admin/students/{studentId}
POST   /api/v1/admin/students/{studentId}/restore
```

Optional fachlich deutlicher:

```text
POST /api/v1/admin/students/{studentId}/leave-school
```

Diese Aktion beendet aktive Mitgliedschaften und startet den Soft-Delete-Lebenszyklus.

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

Lesen und fachliche Zuordnung können Lehrer erhalten, kritisches Löschen/Reaktivieren nur Schul-Admins.

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

Das Entfernen aus der Klasse löscht nicht den Benutzer.

Klassenwechsel kann als atomarer fachlicher Endpoint angeboten werden:

```text
POST /api/v1/students/{studentId}/move-class
```

Request:

```json
{
  "fromClassId": "...",
  "toClassId": "...",
  "effectiveDate": "2026-08-21"
}
```

## Kurse

```text
GET    /api/v1/courses
POST   /api/v1/courses
GET    /api/v1/courses/{courseId}
PATCH  /api/v1/courses/{courseId}
POST   /api/v1/courses/{courseId}/archive
```

Lehrerzuordnung:

```text
POST   /api/v1/courses/{courseId}/teachers/{teacherId}
DELETE /api/v1/courses/{courseId}/teachers/{teacherId}
```

Schülerzuordnung:

```text
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

Materialien können nach Typ gefiltert werden:

```text
GET /api/v1/materials?kind=REFERENCE
GET /api/v1/materials?kind=WORKBOOK
GET /api/v1/materials?kind=ASSESSMENT
```

## Lernbereiche

```text
GET /api/v1/learning-units
GET /api/v1/learning-units/{unitId}
```

Eine Lernbereichsantwort kann die zusammengehörigen Ressourcen verknüpfen:

```json
{
  "id": "...",
  "title": "Binärsystem",
  "referenceMaterial": {...},
  "workbookMaterial": {...},
  "exerciseSet": {...},
  "assessment": {...}
}
```

## Arbeitsheft zuweisen – Lehrer

```text
POST /api/v1/courses/{courseId}/workbook-assignments
GET  /api/v1/courses/{courseId}/workbook-assignments
```

Beispielrequest:

```json
{
  "materialReleaseId": "...",
  "availableFrom": "2026-09-01T06:00:00Z",
  "dueAt": null
}
```

## Arbeitsheft – Schüler

```text
GET /api/v1/my/workbooks
GET /api/v1/my/workbooks/{workbookId}
```

Antwort speichern:

```text
PUT /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}
```

Beispiel:

```json
{
  "answerData": {
    "text": "Daten erhalten durch ihren Kontext eine Bedeutung."
  },
  "clientRevision": 7
}
```

Der Server liefert eine neue Versionsnummer zurück. Dadurch können gleichzeitige beziehungsweise veraltete Änderungen erkannt werden.

## Autosave und Konflikte

Für Antworten wird optimistische Versionierung vorgesehen.

Beispielantwort:

```json
{
  "answerId": "...",
  "revision": 8,
  "savedAt": "2026-08-21T21:10:13Z"
}
```

Sendet ein Client eine veraltete Revision, kann der Server `409 Conflict` liefern, statt stillschweigend neuere Daten zu überschreiben.

## Antwortrevisionen

```text
GET  /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/revisions
POST /api/v1/my/workbooks/{workbookId}/answers/{exerciseId}/restore/{revisionId}
```

Eine spätere Version kann Restore möglicherweise auf Lehrer/Admin begrenzen; das fachliche Verhalten wird noch festgelegt.

## Lehreransicht auf Arbeitsstände

```text
GET /api/v1/courses/{courseId}/progress
GET /api/v1/courses/{courseId}/students/{studentId}/workbooks
GET /api/v1/courses/{courseId}/students/{studentId}/workbooks/{workbookId}
```

## Feedback

```text
POST  /api/v1/teacher-feedback
PATCH /api/v1/teacher-feedback/{feedbackId}
DELETE /api/v1/teacher-feedback/{feedbackId}
```

Feedback ist getrennt von der Schülerantwort.

## Lernkontrollen – Lehrer

```text
POST /api/v1/courses/{courseId}/assessment-assignments
GET  /api/v1/courses/{courseId}/assessment-assignments
```

Konfiguration beispielsweise:

```json
{
  "assessmentId": "...",
  "availableFrom": "2026-10-12T06:00:00Z",
  "availableUntil": "2026-10-19T16:00:00Z",
  "attemptLimit": 1,
  "timeLimitSeconds": 1200,
  "showResult": "AFTER_TEACHER_RELEASE",
  "showSolutions": "AFTER_TEACHER_RELEASE"
}
```

## Lernkontrollen – Schüler

```text
GET  /api/v1/my/assessments
POST /api/v1/my/assessments/{assignmentId}/attempts
GET  /api/v1/my/assessment-attempts/{attemptId}
PUT  /api/v1/my/assessment-attempts/{attemptId}/answers/{exerciseId}
POST /api/v1/my/assessment-attempts/{attemptId}/submit
```

`submit` muss serverseitig prüfen:

- Versuch gehört zum angemeldeten Schüler,
- Versuch ist noch offen,
- Freigabezeitraum ist gültig,
- Zeitlimit ist nicht abgelaufen,
- Versuchslimit wurde eingehalten.

## Dateien

```text
POST   /api/v1/files
GET    /api/v1/files/{fileId}
DELETE /api/v1/files/{fileId}
```

Upload-Berechtigung und Download-Berechtigung werden anhand von Schule, Besitzer und fachlichem Bezug geprüft.

## Fehlerformat

Ein konsistentes Fehlerformat wird vorgesehen, beispielsweise orientiert an Problem Details:

```json
{
  "type": "https://.../problems/username-conflict",
  "title": "Benutzername bereits vergeben",
  "status": 409,
  "detail": "Der Benutzername ist innerhalb der Schule bereits vergeben.",
  "instance": "/api/v1/admin/users/students"
}
```

## Noch offene API-Entscheidungen

- genauer OAuth/OIDC-Endpunktaufbau,
- Cursor- oder Seitenpagination,
- Suche und Filterkonventionen,
- Batch-Import von Schülern,
- Batch-Zuweisung kompletter Klassen zu Kursen,
- Offline-Synchronisation,
- Streaming/Server-Sent Events nur falls später fachlich nötig.
