# Identität, Mandanten und Lehrermodell

## Grundentscheidung

Die Identität eines Benutzers und seine Zugehörigkeit zu einer Schule werden getrennt modelliert.

Das ist insbesondere für Lehrer erforderlich, weil ein Lehrer mehreren Schulen gleichzeitig zugeordnet sein kann und seine Rechte je Schule unterschiedlich sein können.

```text
Identity
   │
   ├── StudentAccount ── genau eine aktive Schule
   │
   └── TeacherAccount ── eine oder mehrere Schulen
                              │
                              ├── SchoolMembership A
                              │      └── Rollen für Schule A
                              └── SchoolMembership B
                                     └── Rollen für Schule B
```

## Schule als eindeutiger Mandant

Jede Schule besitzt neben ihrem Anzeigenamen einen systemweit eindeutigen, stabilen technischen Slug.

Beispiel:

```text
name        = Oberschule Musterstadt
school_slug = oberschule-musterstadt
```

Der Slug wird für URLs verwendet und ist eindeutig:

```text
UNIQUE(school_slug)
```

Der Anzeigename darf geändert werden, ohne dass sich bestehende URLs ändern müssen.

## Schule im URL-Pfad

Schulbezogene Bereiche tragen den Slug sichtbar im Pfad.

Beispiele:

```text
/schule/oberschule-musterstadt/
/schule/oberschule-musterstadt/klassen
/schule/oberschule-musterstadt/klassen/7a
/schule/oberschule-musterstadt/materialien
/schule/oberschule-musterstadt/admin/benutzer
```

Die API folgt demselben Grundsatz:

```text
/api/v1/schulen/{schoolSlug}/...
```

Der `schoolSlug` ist **kein Vertrauensanker**. Das Backend löst ihn zu einer `school_id` auf und prüft anschließend immer, ob die authentifizierte Identität für diese Schule berechtigt ist.

Ein Benutzer darf durch Manipulation des URL-Pfads niemals Zugriff auf eine andere Schule erhalten.

## Schüleridentität

Ein Schüler benötigt weiterhin keinen Klarnamen und keine E-Mail-Adresse.

Vorgesehen sind:

```text
id
username
password_hash / Auth-Credentials
display_name
status
```

Die aktive Schulzugehörigkeit wird getrennt geführt.

Für den Login eines Schülers wird die Schule durch den Schulpfad beziehungsweise `schoolSlug` bestimmt:

```text
/schule/oberschule-musterstadt/login
```

Dort reichen beispielsweise:

```text
Benutzername
Passwort
```

Der Benutzername muss nur innerhalb der jeweiligen Schule eindeutig sein.

## Lehreridentität

Ein Lehrer ist eine schulübergreifende Identität.

Für Lehrer wird eine E-Mail-Adresse als Login-Identität vorgesehen:

```text
id
email
email_normalized
email_verified_at
display_name
status
```

Die E-Mail-Adresse ist systemweit eindeutig:

```text
UNIQUE(email_normalized)
```

Klarnamen bleiben auch für Lehrer optional und werden nicht benötigt. Der in der Lernplattform sichtbare Name bleibt ein Fantasiename beziehungsweise Anzeigename.

Die E-Mail-Adresse wird insbesondere benötigt für:

- Anmeldung,
- E-Mail-Verifikation,
- Passwort-Reset beziehungsweise Account-Recovery,
- Sicherheitsbenachrichtigungen.

Die E-Mail-Adresse wird nicht als sichtbarer Lehrername für Schüler verwendet.

## Lehrer an mehreren Schulen

Ein Lehrer kann mehreren Schulen zugeordnet werden.

Dafür wird eine eigene Entität vorgesehen:

```text
TeacherSchoolMembership
-----------------------
id
teacher_id
school_id
status
joined_at
left_at
created_by
```

Eine Lehreridentität wird daher nicht mehr direkt über `user.school_id` an genau eine Schule gebunden.

## Schulbezogene Rollen

Rollen eines Lehrers gelten immer innerhalb einer konkreten Schulzuordnung.

Beispiel:

```text
Lehrer codeotter@example.org

Schule A
  TEACHER
  SCHOOL_ADMIN

Schule B
  TEACHER
```

`SCHOOL_ADMIN` an Schule A verleiht keinerlei Adminrechte an Schule B.

Geeignete Modellierung:

```text
TeacherSchoolRole
-----------------
teacher_school_membership_id
role
```

Schulrollen:

```text
TEACHER
SCHOOL_ADMIN
```

`SYSTEM_ADMIN` ist dagegen eine systemweite Rolle und gehört nicht zu einer Schulmitgliedschaft.

## Rechte eines Lehrers innerhalb einer Schule

Jeder aktive Lehrer einer Schule darf fachlich alle Klassen dieser Schule bearbeiten.

Dazu gehören beispielsweise:

- Arbeitsmaterial ansehen,
- Lernstände im zulässigen Umfang ansehen,
- Feedback geben,
- Material zuweisen,
- Klassen fachlich bearbeiten.

Organisatorische Verwaltungsfunktionen bleiben `SCHOOL_ADMIN` vorbehalten, zum Beispiel:

- Lehrer einer Schule hinzufügen oder entfernen,
- Schülerkonten verwalten,
- Klassen organisatorisch anlegen/löschen,
- Schuladminrolle vergeben oder entziehen.

## Zuständige Lehrer einer Klasse

Obwohl alle Lehrer einer Schule fachlich alle Klassen bearbeiten dürfen, besitzt jede Klasse mindestens einen ausdrücklich zuständigen Lehrer.

Dafür wird eine Zuordnung vorgesehen:

```text
ClassTeacherAssignment
----------------------
class_id
teacher_school_membership_id
responsibility
status
```

`responsibility` kann zunächst sein:

```text
RESPONSIBLE
ADDITIONAL
```

Fachregel:

> Eine aktive Klasse darf niemals ohne mindestens einen aktiven `RESPONSIBLE`-Lehrer existieren.

Die Datenbank allein kann diese Regel nur begrenzt absichern; Änderungen an Lehrer-/Klassenzuordnungen laufen deshalb über einen transaktionalen Domain-Service.

## Benachrichtigung des zuständigen Lehrers

Bearbeitet ein anderer Lehrer eine Klasse, soll mindestens der zuständige Lehrer darüber informiert werden, sofern der Bearbeiter nicht selbst zu den zuständigen Lehrern gehört.

Beispiele für relevante Aktionen:

- Lehrerfeedback zu einem Schüler,
- Änderung einer Materialzuweisung,
- organisatorisch relevante Änderung an Lernmaterial einer Klasse.

Dafür ist ein fachliches Ereignismodell sinnvoll:

```text
ClassActivity
    ↓
ResponsibleTeacherNotification
```

Die erste Umsetzung kann als interne Benachrichtigung in der Plattform erfolgen. E-Mail-Benachrichtigungen sollten separat konfigurierbar sein, damit nicht jede kleine Änderung E-Mail-Verkehr erzeugt.

## Lehrer aus einer Schule entfernen

Ein Lehrer wird nicht unmittelbar als globale Identität gelöscht.

Stattdessen wird seine `TeacherSchoolMembership` beendet beziehungsweise soft-gelöscht.

Vorher müssen folgende Bedingungen erfüllt sein:

1. Der Lehrer ist für keine Klasse mehr der einzige zuständige Lehrer.
2. Jede betroffene aktive Klasse besitzt nach der Entfernung weiterhin mindestens einen aktiven zuständigen Lehrer.
3. Falls der Lehrer `SCHOOL_ADMIN` ist, bleibt mindestens ein anderer aktiver `SCHOOL_ADMIN` der Schule erhalten.

Sind diese Bedingungen nicht erfüllt, wird die Entfernung abgelehnt und die betroffenen Klassen werden angezeigt.

## Globale Löschung eines Lehrers

Ein Lehreraccount kann erst gelöscht beziehungsweise soft-gelöscht werden, wenn keine aktive Schulzuordnung mehr existiert.

```text
TeacherAccount
   ├── Schule A beendet
   ├── Schule B beendet
   └── keine aktive Membership
             ↓
        Account löschbar
```

Vor einer endgültigen Löschung gelten weiterhin Soft-Delete- und Aufbewahrungsregeln.

## Passwort-Reset für Lehrer

Da Lehrer eine verifizierte E-Mail-Adresse besitzen, kann ein sicherer Reset-Prozess angeboten werden.

Grundregeln:

- niemals bestehende Passwörter per E-Mail versenden,
- zeitlich begrenztes, einmal verwendbares Reset-Token,
- Token nur gehasht beziehungsweise anderweitig sicher gespeichert,
- nach erfolgreichem Reset Token sofort ungültig,
- Rate Limiting,
- Antwort bei unbekannter E-Mail darf keine Kontenexistenz verraten.

## Passwortlose Anmeldung

Eine Anmeldung ohne Passwort ist möglich und sollte als Erweiterung vorgesehen werden.

### Passkeys / WebAuthn

Passkeys sind für Lehrer besonders geeignet:

- kein wiederverwendbares Passwort,
- phishing-resistenter als klassische Passwörter,
- Anmeldung über Geräte-PIN, Biometrie oder Hardware-Key,
- technisch auf WebAuthn/FIDO2 basierend.

Empfohlenes Zielbild für Lehrer:

```text
E-Mail + Passwort
      oder
Passkey
```

Nach erfolgreicher Erstanmeldung kann ein Lehrer einen oder mehrere Passkeys registrieren. Mittelfristig kann der Passkey zum bevorzugten Login werden; Passwort beziehungsweise Recovery bleibt als Rückfallweg verfügbar.

### E-Mail-Magic-Link

Für Lehrer wäre auch ein zeitlich begrenzter Login-Link per E-Mail möglich. Das ist benutzerfreundlich, macht die Sicherheit aber stark vom E-Mail-Konto abhängig und ist weniger phishing-resistent als ein Passkey.

Daher wird ein Magic-Link nicht als bevorzugtes Primärverfahren vorgesehen, kann aber später als Recovery-/Login-Option geprüft werden.

### Schüler

Da Schüler bewusst keine E-Mail-Adresse benötigen, ist ein E-Mail-Magic-Link für Schüler nicht geeignet.

Passkeys wären grundsätzlich auch für Schüler möglich. Für gemeinsam genutzte Schulgeräte und bei Geräteverlust muss aber vorher ein praktikabler Recovery-Prozess definiert sein. Deshalb bleibt für Schüler zunächst `schoolSlug + username + password` der robuste Standard; Passkeys können später optional hinzukommen.

## OIDC / Identity Provider

Die Anwendung bleibt OAuth2/OIDC-basiert. Die Authentifizierung sollte möglichst von einem dedizierten Identity Provider übernommen werden; das Spring-Boot-Backend bleibt Resource Server und fachliche Autorisierungsinstanz.

Ein Identity Provider kann dabei Passwörter, Passwort-Reset, E-Mail-Verifikation, Passkeys und Token-Lebenszyklen zentral verwalten.

Die fachlichen Schulmitgliedschaften und schulbezogenen Rollen bleiben trotzdem in der Lernplattform maßgeblich. Token-Claims dürfen nicht dazu führen, dass eine Rolle automatisch für alle Schulen gilt.

## Sicherheitsnotiz zu WebAuthn

Bei direkter WebAuthn-Implementierung in Spring Security muss die jeweils eingesetzte Version vor Produktivbetrieb auf aktuelle Sicherheitsfixes geprüft werden. Im August 2026 wurde beispielsweise eine Schwachstelle für bestimmte Spring-Security-WebAuthn-Konfigurationen mit serialisierten Sessions veröffentlicht. Eine Authentifizierungskomponente wird deshalb nicht auf eine ungeprüfte Bibliotheksversion festgeschrieben.
