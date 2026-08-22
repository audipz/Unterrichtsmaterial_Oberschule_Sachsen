# REST-API – erster Entwurf

> **Architekturhinweis:** Das Identitätsmodell wurde weiterentwickelt. Lehrer sind globale Identitäten mit E-Mail und können mehreren Schulen zugeordnet sein. Schulbezogene Rollen gelten nur innerhalb einer `TeacherSchoolMembership`. Details stehen in `Identitaet_Mandanten_und_Lehrer.md`.

## Grundsätze

- JSON über HTTPS
- OAuth 2 / OpenID Connect
- Autorisierung serverseitig mit Spring Security
- jede Schule besitzt einen systemweit eindeutigen, stabilen `schoolSlug`
- schulbezogene Endpunkte tragen den `schoolSlug` sichtbar im Pfad
- der `schoolSlug` ist kein Vertrauensanker; das Backend löst ihn zu `school_id` auf und prüft die Mitgliedschaft des angemeldeten Benutzers
- Lehrerrollen wie `SCHOOL_ADMIN` gelten nur für die konkrete Schule
- keine frei manipulierbare `schoolId` als Vertrauensanker
- UUIDs als interne/externe Objekt-IDs, Slugs für lesbare Schulpfade
- konsistente Fehlerobjekte
- Schreiboperationen möglichst idempotent oder mit klarer Konfliktbehandlung

Basis für schulbezogene API-Endpunkte:

```text
/api/v1/schulen/{schoolSlug}
```

Systemweite Endpunkte bleiben außerhalb dieses Pfads:

```text
/api/v1/system/...
```

## Authentifizierter Benutzer

```text
GET /api/v1/me
```

Die Antwort kann bei Lehrern mehrere Schulzuordnungen enthalten:

```json
{
  "userType": "TEACHER",
  "email": "teacher@example.org",
  "displayName": "CodeOtter42",
  "schools": [
    {
      "slug": "oberschule-musterstadt",
      "roles": ["TEACHER", "SCHOOL_ADMIN"]
    },
    {
      "slug": "oberschule-nord",
      "roles": ["TEACHER"]
    }
  ]
}
```

## Schulen – SYSTEM_ADMIN

```text
GET    /api/v1/system/schulen
POST   /api/v1/system/schulen
GET    /api/v1/system/schulen/{schoolSlug}
PATCH  /api/v1/system/schulen/{schoolSlug}
DELETE /api/v1/system/schulen/{schoolSlug}
```

Beim Anlegen einer Schule wird ein eindeutiger `schoolSlug` vergeben.

## Lehrer – schulbezogene Mitgliedschaft

Ein Lehrer ist eine globale Identität mit systemweit eindeutiger E-Mail-Adresse. Die Zugehörigkeit zu einer Schule wird getrennt verwaltet.

```text
GET    /api/v1/schulen/{schoolSlug}/lehrer
POST   /api/v1/schulen/{schoolSlug}/lehrer
DELETE /api/v1/schulen/{schoolSlug}/lehrer/{teacherId}
```

`POST` kann einen bereits existierenden Lehrer anhand seiner verifizierten E-Mail einer weiteren Schule zuordnen. Es wird kein zweiter Lehreraccount angelegt.

Die Entfernung beendet nur die Schulzuordnung. Der globale Lehreraccount bleibt bestehen, solange mindestens eine andere aktive Schulzuordnung vorhanden ist.

Vor `DELETE` wird geprüft:

- Lehrer ist nicht alleiniger zuständiger Lehrer einer aktiven Klasse,
- jede betroffene Klasse besitzt danach weiterhin mindestens einen zuständigen Lehrer,
- falls der Lehrer `SCHOOL_ADMIN` ist, bleibt mindestens ein anderer aktiver Schuladmin.

## Schulbezogene Rollen eines Lehrers

```text
PUT    /api/v1/schulen/{schoolSlug}/lehrer/{teacherId}/rollen/SCHOOL_ADMIN
DELETE /api/v1/schulen/{schoolSlug}/lehrer/{teacherId}/rollen/SCHOOL_ADMIN
```

Eine Rolle an einer Schule verändert niemals die Rechte desselben Lehrers an einer anderen Schule.

## Schülerverwaltung – SCHOOL_ADMIN

```text
GET   /api/v1/schulen/{schoolSlug}/schueler
POST  /api/v1/schulen/{schoolSlug}/schueler
GET   /api/v1/schulen/{schoolSlug}/schueler/{studentId}
PATCH /api/v1/schulen/{schoolSlug}/schueler/{studentId}
```

Schüler besitzen weiterhin keine verpflichtende E-Mail-Adresse. Ihr Login erfolgt im Kontext der Schule über Benutzername und Passwort.

## Schüler verlässt Schule / Reaktivierung

```text
POST /api/v1/schulen/{schoolSlug}/schueler/{studentId}/schule-verlassen
POST /api/v1/schulen/{schoolSlug}/schueler/{studentId}/reaktivieren
```

## Papierkorb

```text
GET /api/v1/schulen/{schoolSlug}/admin/papierkorb
GET /api/v1/schulen/{schoolSlug}/admin/papierkorb?type=STUDENT
GET /api/v1/schulen/{schoolSlug}/admin/papierkorb?type=CLASS
```

## Klassen

Alle aktiven Lehrer einer Schule dürfen Klassen fachlich bearbeiten. Organisatorische Änderungen wie Anlegen, Löschen oder Änderung von Mitgliedschaften bleiben Schuladmins vorbehalten.

```text
GET    /api/v1/schulen/{schoolSlug}/klassen
POST   /api/v1/schulen/{schoolSlug}/klassen
GET    /api/v1/schulen/{schoolSlug}/klassen/{classId}
PATCH  /api/v1/schulen/{schoolSlug}/klassen/{classId}
DELETE /api/v1/schulen/{schoolSlug}/klassen/{classId}
POST   /api/v1/schulen/{schoolSlug}/klassen/{classId}/reaktivieren
```

### Schülerzuordnung

```text
GET    /api/v1/schulen/{schoolSlug}/klassen/{classId}/schueler
POST   /api/v1/schulen/{schoolSlug}/klassen/{classId}/schueler/{studentId}
DELETE /api/v1/schulen/{schoolSlug}/klassen/{classId}/schueler/{studentId}
POST   /api/v1/schulen/{schoolSlug}/schueler/{studentId}/klasse-wechseln
```

### Zuständige Lehrer

Jede aktive Klasse muss mindestens einen aktiven zuständigen Lehrer besitzen.

```text
GET    /api/v1/schulen/{schoolSlug}/klassen/{classId}/lehrer
POST   /api/v1/schulen/{schoolSlug}/klassen/{classId}/lehrer/{teacherId}
PATCH  /api/v1/schulen/{schoolSlug}/klassen/{classId}/lehrer/{teacherId}
DELETE /api/v1/schulen/{schoolSlug}/klassen/{classId}/lehrer/{teacherId}
```

Beispiel für die Rolle innerhalb der Klasse:

```json
{
  "responsibility": "RESPONSIBLE"
}
```

Das Entfernen des letzten `RESPONSIBLE`-Lehrers wird mit `409 Conflict` abgelehnt.

## Benachrichtigungen an zuständige Lehrer

Bearbeitet ein anderer Lehrer eine Klasse, kann daraus eine interne Benachrichtigung für die zuständigen Lehrer entstehen.

```text
GET  /api/v1/me/benachrichtigungen
POST /api/v1/me/benachrichtigungen/{notificationId}/gelesen
```

Nicht jede Aktion muss eine E-Mail auslösen; E-Mail-Benachrichtigungen werden separat konfigurierbar gehalten.

## Login-Pfade

### Schüler

```text
/schule/{schoolSlug}/login
```

Anmeldung zunächst mit:

```text
Benutzername
Passwort
```

### Lehrer

Lehrer melden sich mit ihrer globalen E-Mail-Identität beim Identity Provider an. Nach dem Login wählen sie bei mehreren Schulzuordnungen den Schulkontext oder gelangen direkt über den Schulpfad in die entsprechende Schule.

Passwort-Reset erfolgt über die verifizierte E-Mail-Adresse.

Passkeys/WebAuthn werden als bevorzugte passwortlose Erweiterung vorgesehen.

## Materialien

```text
GET /api/v1/schulen/{schoolSlug}/materials
GET /api/v1/schulen/{schoolSlug}/materials/{materialId}
GET /api/v1/schulen/{schoolSlug}/materials/{materialId}/releases
GET /api/v1/schulen/{schoolSlug}/material-releases/{releaseId}
```

## Materialkatalog für Schüler

```text
GET /api/v1/schulen/{schoolSlug}/my/catalog
GET /api/v1/schulen/{schoolSlug}/my/catalog?gradeLevel=7
GET /api/v1/schulen/{schoolSlug}/my/catalog?kind=WORKBOOK
```

## Lernbereiche

```text
GET /api/v1/schulen/{schoolSlug}/learning-units
GET /api/v1/schulen/{schoolSlug}/learning-units/{unitId}
```

## Arbeitsmaterial selbstständig starten – Schüler

```text
POST /api/v1/schulen/{schoolSlug}/my/workbooks
GET  /api/v1/schulen/{schoolSlug}/my/workbooks
GET  /api/v1/schulen/{schoolSlug}/my/workbooks/{workbookId}
PUT  /api/v1/schulen/{schoolSlug}/my/workbooks/{workbookId}/answers/{exerciseId}
```

Autosave verwendet optimistische Versionierung. Eine veraltete `clientRevision` führt zu `409 Conflict`.

## Übungen selbstständig starten – Schüler

```text
POST /api/v1/schulen/{schoolSlug}/my/exercises
GET  /api/v1/schulen/{schoolSlug}/my/exercises
GET  /api/v1/schulen/{schoolSlug}/my/exercises/{exerciseSessionId}
PUT  /api/v1/schulen/{schoolSlug}/my/exercises/{exerciseSessionId}/answers/{exerciseId}
POST /api/v1/schulen/{schoolSlug}/my/exercises/{exerciseSessionId}/answers/{exerciseId}/check
```

## Lehrerzugriff auf Lernfortschritt

Jeder aktive Lehrer einer Schule darf die Klassen der Schule fachlich bearbeiten. Die endgültige Sichtbarkeit einzelner Schülerdaten wird serverseitig über Schule und fachlichen Kontext geprüft.

```text
GET /api/v1/schulen/{schoolSlug}/klassen/{classId}/progress
GET /api/v1/schulen/{schoolSlug}/klassen/{classId}/schueler/{studentId}/progress
```

## Feedback

```text
POST   /api/v1/schulen/{schoolSlug}/teacher-feedback
PATCH  /api/v1/schulen/{schoolSlug}/teacher-feedback/{feedbackId}
DELETE /api/v1/schulen/{schoolSlug}/teacher-feedback/{feedbackId}
```

Wenn der bearbeitende Lehrer nicht selbst zuständiger Lehrer der Klasse ist, wird ein fachliches Ereignis für die Benachrichtigung der zuständigen Lehrer erzeugt.

## Dateien

```text
POST   /api/v1/schulen/{schoolSlug}/files
GET    /api/v1/schulen/{schoolSlug}/files/{fileId}
DELETE /api/v1/schulen/{schoolSlug}/files/{fileId}
```

## Fehlerformat

Ein konsistentes Fehlerformat wird vorgesehen, orientiert an Problem Details.

Wichtige Konflikte:

```text
403 school-membership-required
403 school-admin-required
409 last-responsible-teacher
409 last-school-admin
409 display-name-conflict
409 teacher-still-responsible-for-classes
```

## Nicht Bestandteil der API

Ausdrücklich nicht vorgesehen sind Endpunkte für:

- Lernkontrollen,
- Prüfungsversuche,
- Zeitlimits für Prüfungen,
- Punkte/Noten,
- benotete Abgaben,
- Prüfungsaufsicht.

## Noch offene API-Entscheidungen

- konkreter Identity Provider und dessen Login-/Recovery-Flows,
- Passkey-Einführung für Lehrer und später optional Schüler,
- Cursor- oder Seitenpagination,
- Batch-Import von Schülern,
- Offline-Synchronisation,
- Granularität und Kanal der Lehrerbenachrichtigungen.
