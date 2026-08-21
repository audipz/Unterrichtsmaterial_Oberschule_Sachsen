# Sicherheit und Datenschutz

## Ziel

Die Lernplattform verarbeitet Benutzerkonten, Lernstände, Antworten und gegebenenfalls Dateien. Deshalb gelten Datenschutz und technische Sicherheit als Bestandteil der Architektur und nicht als nachträgliche Ergänzung.

## Datenminimierung

Die Plattform speichert nur Daten, die für Betrieb und Lernfunktion notwendig sind.

Für Benutzer werden insbesondere keine Klarnamen verlangt. Ein Konto besteht im Kern aus:

```text
Schule
Benutzername
Passwort-Hash
Fantasiename / Anzeigename
Rollen
organisatorische Zuordnungen
```

Der Fantasiename wird initial automatisch erzeugt und kann später geändert werden. Reale Namen und private Kontaktdaten sind nicht erforderlich.

## Authentifizierung

Die Anmeldung erfolgt fachlich über:

```text
Schule
Benutzername
Passwort
```

Technisch wird OAuth 2 / OpenID Connect verwendet. Für Browseranwendungen wird Authorization Code mit PKCE vorgesehen.

Der Benutzername muss nur innerhalb einer Schule eindeutig sein.

## Passwörter

Passwörter werden niemals verschlüsselt oder im Klartext gespeichert.

Gespeichert wird ausschließlich ein geeigneter Passwort-Hash. Vorgesehen ist Argon2id beziehungsweise eine gleichwertige, zeitgemäße Passwort-Hashing-Funktion.

Ein initiales Startpasswort kann beim Anlegen eines Kontos erzeugt werden. Nach der ersten Anmeldung kann beziehungsweise soll ein Passwortwechsel erzwungen werden.

## Transportverschlüsselung

Alle relevanten Verbindungen werden über TLS geschützt:

```text
Browser ── HTTPS/TLS ──> Frontend/API
Backend ── TLS ──> PostgreSQL
Backend ── TLS ──> Object Storage / Backup Storage
```

Unsichere HTTP-Verbindungen werden im produktiven Betrieb nicht verwendet.

## Verschlüsselung ruhender Daten

Der Storage von PostgreSQL soll verschlüsselt betrieben werden. Gleiches gilt für persistenten Datei- beziehungsweise Object-Storage.

Die konkrete Umsetzung hängt vom verwendeten K3s-Storage ab, beispielsweise Longhorn, Ceph, verschlüsselten lokalen Volumes oder verschlüsseltem NAS-Storage.

## Feldverschlüsselung

Anwendungsseitige Feldverschlüsselung ist optional und wird gezielt eingesetzt, wenn ein Feld besonderen Schutz benötigt.

Eine pauschale Verschlüsselung aller Datenbankfelder ist nicht vorgesehen, da sie Suche, Indizierung und fachliche Abfragen unnötig erschweren kann.

Schlüssel für Feldverschlüsselung dürfen nicht zusammen mit den verschlüsselten Daten in PostgreSQL gespeichert werden.

## Secret Management

Secrets werden nicht in Git und nicht im Klartext in Helm-`values.yaml` abgelegt.

Beispiele:

- Datenbankkennwort,
- OAuth/OIDC-Schlüssel,
- Backup-Zugangsdaten,
- Storage-Zugangsdaten,
- optionale Verschlüsselungsschlüssel.

Helm referenziert bestehende Kubernetes-Secrets beziehungsweise einen später gewählten Secret-Management-Mechanismus.

## Mandantentrennung

`School` ist die zentrale Mandantengrenze.

Jeder Zugriff auf schulbezogene Daten wird serverseitig autorisiert. Die Schule eines Requests wird nicht aus einer frei manipulierbaren `schoolId` des Frontends als Vertrauensanker übernommen.

Die Schulzugehörigkeit stammt aus der validierten Identität und den serverseitigen Zuordnungen.

Beispiel:

Ein `SCHOOL_ADMIN` darf einen Schüler nur verwalten, wenn beide derselben Schule zugeordnet sind.

## Rollen und Berechtigungen

Rollen:

```text
STUDENT
TEACHER
SCHOOL_ADMIN
SYSTEM_ADMIN
```

Autorisierung erfolgt im Backend mit Spring Security. Das Ausblenden eines Buttons im Angular-Frontend ist keine Sicherheitsmaßnahme.

## Fantasienamen

Fantasienamen werden in der Lernoberfläche anstelle von Klarnamen verwendet.

Sie müssen innerhalb des jeweils sichtbaren Klassenkontexts eindeutig sein:

- Schüler: eindeutig innerhalb der eigenen aktiven Klasse,
- Lehrer: eindeutig in allen Klassen, denen sie aktiv zugewiesen sind.

Die Prüfung erfolgt serverseitig und case-insensitive auf einer normalisierten Form.

## Soft Delete und endgültige Löschung

Gelöschte Benutzer und Klassen werden zunächst soft-gelöscht.

Für Benutzer bedeutet das insbesondere:

- Login sofort gesperrt,
- Objekt aus normalen Listen entfernt,
- Daten während der Wiederherstellungsfrist erhalten.

Ein `SCHOOL_ADMIN` kann berechtigte Objekte innerhalb der Frist reaktivieren.

Nach drei Kalendermonaten erfolgt der kontrollierte automatische Purge.

## Audit

Kritische administrative Aktionen werden nachvollziehbar gespeichert, beispielsweise:

- Benutzer angelegt,
- Rolle geändert,
- Konto soft-gelöscht,
- Konto reaktiviert,
- Klasse gelöscht oder reaktiviert.

Audit-Einträge referenzieren technische Benutzer-IDs und benötigen keine Klarnamen.

Nicht jede Lernaktion eines Schülers wird als umfassendes Audit protokolliert.

## Dateien

Dateiuploads werden serverseitig geprüft:

- maximale Größe,
- erlaubter Inhaltstyp,
- erlaubte Dateiendung soweit sinnvoll,
- fachlicher Besitzer und Kontext,
- Zugriffsberechtigung beim Download.

Dateinamen des Benutzers werden nicht ungeprüft als Storage-Pfade verwendet.

## Backups

Backups enthalten potenziell dieselben schützenswerten Daten wie das Produktivsystem. Deshalb müssen auch Backups verschlüsselt übertragen und verschlüsselt gespeichert werden.

Backup-Schlüssel und Backup-Zugangsdaten werden getrennt vom eigentlichen Backup aufbewahrt.

Details stehen in `Backup_und_Wiederherstellung.md`.

## Logging

Logs sollen keine Passwörter, Access Tokens oder vollständigen vertraulichen Request-Inhalte enthalten.

Personenbezogene beziehungsweise pseudonyme Daten werden nur protokolliert, wenn dies für Betrieb und Fehleranalyse notwendig ist.

## Sicherheitsgrundsatz

```text
Daten minimieren
      ↓
Zugriffe serverseitig autorisieren
      ↓
Transport verschlüsseln
      ↓
Storage und Backups verschlüsseln
      ↓
Secrets getrennt verwalten
      ↓
Löschung kontrolliert durchführen
```
