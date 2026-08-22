# Identität, Schulen, Lehrer und Klassenverantwortung

## Grundentscheidung

Technische Identität, Schulzugehörigkeit, Berechtigungen und Klassenverantwortung sind getrennte Konzepte.

```text
Account
├── type = STUDENT | TEACHER
├── Fantasiename
└── Status

Account
└── SchoolMembership
    ├── Schule
    ├── schulbezogene Login-/Organisationsdaten
    └── SchoolRole(s)

SchoolClass
└── ClassTeacherAssignment(s)
```

Diese Trennung erlaubt spätere zusätzliche Rechte, ohne Anwendertypen, Mandantenzugehörigkeit und Berechtigungen miteinander zu vermischen.

## Anwendertypen

`STUDENT` und `TEACHER` sind **Anwendertypen**, keine administrativen Rechte.

Ein Account besitzt genau einen Anwendertyp:

```text
STUDENT
TEACHER
```

Ein Benutzer wird nicht gleichzeitig als Schüler und Lehrer modelliert.

## Schule als eindeutiger Mandant

Jede Schule besitzt:

```text
id
name
school_slug
status
```

`school_slug` ist systemweit eindeutig und stabil:

```text
UNIQUE(lower(school_slug))
```

Beispiel:

```text
name        = Oberschule Musterstadt
school_slug = oberschule-musterstadt
```

Der Anzeigename darf sich ändern. Der technische Slug bleibt nach Möglichkeit stabil.

## Schule im Pfad

Schulbezogene Bereiche verwenden den Schul-Slug im Pfad:

```text
/schule/oberschule-musterstadt/
/schule/oberschule-musterstadt/klassen
/schule/oberschule-musterstadt/materialien
/schule/oberschule-musterstadt/admin
```

API:

```text
/api/v1/schulen/{schoolSlug}/...
```

Der Slug dient der Zuordnung und Navigation, ist aber **kein Vertrauensanker**. Das Backend löst ihn zu einer internen `school_id` auf und prüft anschließend immer die Berechtigung der authentifizierten Identität für genau diese Schule.

## Globaler Account

Ein Account ist nicht fest an eine einzelne Schule gebunden.

```text
Account
-------
id
account_type
display_name
display_name_normalized
status
pending_deletion_at
created_at
updated_at
```

Der Fantasiename ist der sichtbare Name in der Lernplattform. Klarnamen werden nicht benötigt.

Wenn ein Account keine aktive Schulzuordnung mehr besitzt, wird er automatisch in einen Löschvormerkungsstatus versetzt. Wird innerhalb der Aufbewahrungsfrist erneut eine Schulzuordnung hergestellt, kann diese Vormerkung aufgehoben werden.

## Schulzugehörigkeit

Die Zugehörigkeit eines Accounts zu einer Schule wird historisiert:

```text
SchoolMembership
----------------
id
account_id
school_id
status
valid_from
valid_until
created_at
created_by
ended_at
ended_by
```

Ein Account kann über seine Lebenszeit mehreren Schulen zugeordnet sein. Für Lehrer können mehrere Schulzuordnungen gleichzeitig aktiv sein. Ein Schulwechsel eines Schülers wird als kontrollierter Transfer ausgeführt, damit während des Transfers kein herrenloser aktiver Account entsteht.

## Schülerlogin

Schüler benötigen keine E-Mail-Adresse.

Der Loginname gehört zur Schulzuordnung und ist nur innerhalb einer Schule eindeutig:

```text
StudentSchoolLogin
------------------
school_membership_id
username
password_hash / Auth-Referenz
must_change_password
```

Eindeutigkeit:

```text
UNIQUE(school_id, lower(username))
```

Beim Schulwechsel kann derselbe Benutzername weiterverwendet werden, wenn er an der Zielschule frei ist. Andernfalls erhält die neue Schulzuordnung einen anderen Login-Namen. Die globale Account-ID und persönliche Lernhistorie bleiben erhalten.

Schülerlogin:

```text
/schule/{schoolSlug}/login

Benutzername
Passwort
```

## Lehrerlogin

Lehrer besitzen eine globale Login-Identität über eine verifizierte E-Mail-Adresse:

```text
TeacherIdentity
---------------
account_id
email
email_normalized
email_verified_at
```

Eindeutigkeit:

```text
UNIQUE(email_normalized)
```

Die E-Mail-Adresse wird nicht gegenüber Schülern angezeigt. In der Plattform bleibt der Fantasiename sichtbar.

Die E-Mail-Adresse ermöglicht insbesondere Account-Recovery und Passwort-Reset.

## Passwortlose Anmeldung für Lehrer

Die Architektur soll passwortlose Anmeldung unterstützen. Bevorzugtes Ziel sind **Passkeys/WebAuthn**.

Mögliche Entwicklung:

```text
Phase 1: E-Mail + Passwort + Passwort-Reset
Phase 2: optional Passkey registrieren
Phase 3: Passkey bevorzugt, Passwort als Recovery/Fallback
```

E-Mail-Magic-Links können später geprüft werden, sind aber nicht das bevorzugte Primärverfahren.

Für Schüler bleibt zunächst `schoolSlug + username + password` vorgesehen, da bewusst keine E-Mail-Adresse benötigt wird.

## Schulbezogene Rechte

Rechte gelten immer in einem Schulkontext.

Beispiel:

```text
Lehreraccount
├── Schule A
│   └── SCHOOL_ADMIN
└── Schule B
    └── keine Adminrechte
```

`SCHOOL_ADMIN` ist ein **schulbezogenes Recht** und kein Anwendertyp.

Mögliche spätere Rechte werden genauso kontextbezogen ergänzt, zum Beispiel:

```text
SCHOOL_ADMIN
MATERIAL_ADMIN
USER_ADMIN
```

Für die erste Version genügt `SCHOOL_ADMIN` als zusätzliches Schulrecht.

`SYSTEM_ADMIN` ist systemweit und wird getrennt von Schulrollen modelliert.

## Rechte eines Lehrers innerhalb einer Schule

Jeder aktive Lehrer mit aktiver Schulzuordnung darf grundsätzlich alle Klassen dieser Schule fachlich bearbeiten.

Dazu gehören insbesondere:

- Lernmaterialien zuordnen,
- Bearbeitungsfortschritt sehen,
- Klassen fachlich verwalten,
- Unterrichtsmaterial für Schüler strukturieren.

Ein Lehrer sieht bei Schülern **keine Inhalte der Antworten**. Sichtbar sind nur Lern-/Bearbeitungsmetadaten, beispielsweise:

```text
NOT_STARTED
IN_PROGRESS
COMPLETE
```

Optional zusätzlich:

```text
8 von 10 Aufgaben bearbeitet
letzte Bearbeitung: Datum/Zeit
```

Nicht sichtbar sind Freitexte, Zeichnungen, Codeeingaben oder sonstige Schülerantworten.

## Zuständige Lehrer einer Klasse

Jede aktive Klasse besitzt mindestens einen explizit zugewiesenen Lehrer. Mehrere zugewiesene Lehrer sind **gleichberechtigt**.

```text
ClassTeacherAssignment
----------------------
class_id
teacher_school_membership_id
status
assigned_at
assigned_by
```

Es gibt zunächst keine Hierarchie wie Haupt-/Nebenlehrer.

Fachregel:

> Eine aktive Klasse darf zu keinem Zeitpunkt ohne mindestens einen aktiven, dieser Schule zugeordneten Lehrer existieren.

Eine Lehrerzuordnung ist primär Verantwortlichkeit und Benachrichtigung, kein exklusives Bearbeitungsrecht.

## Lernstand einer Klassenstufe neu beginnen

Wenn ein Schüler eine Klassenstufe wiederholt oder aus pädagogisch-organisatorischen Gründen neu beginnen soll, darf **nur ein aktuell dieser Klasse zugewiesener Lehrer** den Lernstand für die betreffende Klassenstufe neu beginnen.

`SCHOOL_ADMIN` allein verleiht dieses Recht nicht.

Es wird nichts physisch gelöscht. Stattdessen wird ein neuer Lernzyklus beziehungsweise eine neue Lernperiode erzeugt:

```text
LearningPeriod 2026/27 – Klasse 7 – ARCHIVED
LearningPeriod 2027/28 – Klasse 7 – ACTIVE
```

Der Vorgang wird protokolliert:

```text
student_id
grade_level
old_learning_period_id
new_learning_period_id
reset_by_teacher_id
reset_at
reason optional
```

## Benachrichtigung zuständiger Lehrer

Bearbeitet ein Lehrer eine Klasse, der dieser Klasse nicht zugewiesen ist, werden die zugewiesenen Lehrer über relevante Änderungen informiert.

Benachrichtigungswürdige Aktionen werden fachlich begrenzt, damit keine unnötige Ereignisflut entsteht.

Beispiele:

- Änderung einer Materialzuweisung,
- Änderung einer Klassenstruktur,
- pädagogisch relevante organisatorische Änderung.

Die erste Umsetzung kann als interne Benachrichtigung erfolgen. E-Mail-Benachrichtigungen bleiben separat konfigurierbar.

## Lehrer aus einer Schule entfernen

Ein Lehreraccount wird beim Entfernen aus einer Schule nicht gelöscht. Stattdessen wird nur die betreffende `SchoolMembership` beendet.

Vorher gelten harte Regeln:

1. Jede aktive Klasse muss nach der Entfernung weiterhin mindestens einen zugewiesenen Lehrer besitzen.
2. Ist der Lehrer der einzige zugewiesene Lehrer einer Klasse, muss zuerst ein weiterer Lehrer zugeordnet werden.
3. Besitzt der Lehrer `SCHOOL_ADMIN`, muss mindestens ein anderer aktiver Schuladmin verbleiben.

Erst wenn keine aktive Schulzuordnung mehr vorhanden ist, wird der globale Account zur Löschung vorgemerkt.

## Verwaiste Accounts verhindern

Das Vergessen von Accounts wird technisch verhindert.

```text
letzte aktive SchoolMembership endet
        ↓
Account.status = PENDING_DELETION
pending_deletion_at = now
        ↓
3 Monate ohne neue aktive Membership
        ↓
kontrollierter Purge
```

Ein regelmäßiger Hintergrundjob prüft zusätzlich Accounts ohne aktive Schulzuordnung und korrigiert inkonsistente Zustände automatisch in Richtung `PENDING_DELETION`.

Das gilt für Schüler und Lehrer gleichermaßen.

## Klassenwechsel

Ein Klassenwechsel innerhalb derselben Schule verändert nur die historisierte Klassenmitgliedschaft:

```text
alte ClassMembership → ENDED
neue ClassMembership → ACTIVE
```

Account und Lernhistorie bleiben erhalten.

## Schulwechsel eines Schülers

Ein Schulwechsel ist ein atomarer fachlicher Transfer:

```text
1. Zielschule akzeptiert Transfer
2. neue SchoolMembership anlegen
3. neuen schulbezogenen Login festlegen
4. Zielklasse zuordnen
5. alte SchoolMembership beenden
```

Persönliche Lernhistorie bleibt am globalen Schüleraccount. Schulinterne Daten der bisherigen Schule werden nicht automatisch für die neue Schule sichtbar.

## Datenschutzgrenze

Daten werden in drei Bereiche getrennt:

```text
Accountbezogen
- Fantasiename
- technische Identität
- persönliche Lernhistorie

Schulbezogen
- Schulmitgliedschaft
- Klasse
- organisatorische Zuweisungen
- schulinterne Auditdaten

Inhaltlich privat
- konkrete Schülerantworten
- Freitexte
- Zeichnungen
- Codeeingaben
```

Lehrer und Schuladmins erhalten keinen generellen Zugriff auf den inhaltlich privaten Bereich. Sie sehen nur Bearbeitungs- und Vollständigkeitsstatus.

## OIDC / Identity Provider

Die Plattform bleibt OAuth2/OIDC-basiert. Ein dedizierter Identity Provider kann Passwort-Reset, E-Mail-Verifikation, Passkeys und Token-Lebenszyklen übernehmen.

Die fachlichen Schulmitgliedschaften und schulbezogenen Rechte bleiben trotzdem in der Lernplattform maßgeblich. Ein Token darf niemals ein globales `SCHOOL_ADMIN`-Recht für alle Schulen implizieren.
