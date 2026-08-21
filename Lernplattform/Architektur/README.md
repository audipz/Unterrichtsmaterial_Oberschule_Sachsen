# Architektur

## Zielarchitektur

Die Lernplattform wird als Webanwendung für Schüler, Lehrer, Schul-Administratoren und System-Administratoren entwickelt.

```text
Browser
  │
  ▼
Angular-Anwendung
  │ HTTPS / REST
  ▼
Spring-Boot-Backend
  │
  ├── Authentifizierung und Autorisierung
  ├── Schulen und Benutzer
  ├── Klassen und Kurse
  ├── Materialien
  ├── Arbeitshefte und Antworten
  ├── Übungen und Lernfortschritt
  ├── Feedback
  └── Dateien
  │
  ▼
PostgreSQL
```

Die Anwendung soll auf Kubernetes/K3s betrieben werden.

**Nicht Bestandteil der Zielarchitektur sind Lernkontrollen, Prüfungsmodi, Benotung oder formale Leistungsbewertung.**

## Frontend

Vorgesehen ist Angular in der jeweils aktuellen stabilen Version.

Angular **Signals** bilden den zentralen UI-Zustand ab. Asynchrone APIs dürfen intern weiterhin RxJS verwenden; für den Komponenten- und Anwendungszustand werden die Ergebnisse in Signals überführt.

Beispiele für Signal-Zustände:

- aktueller Benutzer,
- aktueller Kurs,
- geöffnetes Kapitel,
- Antworten des Schülers,
- Speicherstatus,
- Übungsstatus,
- Lernfortschritt.

## Backend

Das Backend wird mit Java und Spring Boot umgesetzt.

Zu Beginn wird ein modularer Monolith bevorzugt. Dadurch bleiben Deployment, Transaktionen und Entwicklung überschaubar. Eine spätere Trennung einzelner Module bleibt möglich.

Vorgesehene Module:

```text
auth
schools
users
classes
courses
materials
workbooks
exercises
progress
feedback
files
administration
```

## Authentifizierung

Die Anmeldung soll für Benutzer einfach bleiben:

```text
Schule
Benutzername
Passwort
```

Technisch wird OAuth 2 / OpenID Connect verwendet. Für browserbasierte Anmeldung ist Authorization Code mit PKCE vorgesehen; ein veralteter Password Grant wird nicht verwendet.

Die Schule ist Bestandteil der Identität. Ein Benutzername muss deshalb nur innerhalb seiner Schule eindeutig sein.

Nach der Authentifizierung muss die Schulzugehörigkeit aus der vertrauenswürdigen serverseitigen Identität beziehungsweise aus validierten Claims stammen. Das Frontend darf nicht durch frei gesetzte `schoolId`-Parameter auf Daten einer anderen Schule zugreifen können.

## Rollen

Vorgesehene Rollen:

- `STUDENT`
- `TEACHER`
- `SCHOOL_ADMIN`
- `SYSTEM_ADMIN`

Ein Benutzer kann mehrere Rollen besitzen. Ein Lehrer kann beispielsweise gleichzeitig `TEACHER` und `SCHOOL_ADMIN` sein.

### STUDENT

- eigene Materialien lesen,
- eigene Arbeitshefte bearbeiten,
- eigene Antworten speichern,
- freigegebene Übungen bearbeiten,
- Hinweise und Lernrückmeldungen nutzen,
- eigenen Fortschritt sehen.

### TEACHER

- Klassen und Kurse fachlich betreuen,
- Schüler Klassen/Kursen zuordnen,
- Lernmaterialien zuweisen,
- Arbeitsstände und Lernfortschritt der betreuten Schüler sehen,
- Feedback geben.

### SCHOOL_ADMIN

Zusätzlich zu Lehrerfunktionen insbesondere:

- Schülerkonten anlegen und verwalten,
- Lehrer der Schule verwalten,
- geeigneten Lehrern die Schul-Adminrolle geben beziehungsweise entziehen,
- Klassen verwalten,
- Konten und Klassen soft-löschen,
- soft-gelöschte Konten und Klassen innerhalb der Frist reaktivieren.

### SYSTEM_ADMIN

- Schulen anlegen und verwalten,
- initiale Schul-Administratoren einrichten,
- systemweite Administration durchführen.

## Mandantentrennung

`School` ist der zentrale Mandant.

Jede fachliche Operation mit Schuldaten muss serverseitig sicherstellen, dass der angemeldete Benutzer für die betreffende Schule berechtigt ist.

Eine reine Prüfung der Rolle `SCHOOL_ADMIN` reicht beispielsweise nicht. Zusätzlich muss geprüft werden, dass Admin und Zielobjekt zur selben Schule gehören.

## Soft Delete

Organisatorische Daten werden im normalen Betrieb nicht sofort physisch gelöscht.

Typische Felder:

```text
status
deleted_at
deleted_by
```

Nach dem Soft Delete:

- verschwindet das Objekt aus normalen Ansichten,
- ein gelöschtes Benutzerkonto kann sich nicht mehr anmelden,
- abhängige Daten bleiben während der Wiederherstellungsfrist erhalten,
- ein `SCHOOL_ADMIN` kann berechtigte Objekte reaktivieren.

## Purge nach drei Monaten

Soft-gelöschte Daten werden nach **drei Kalendermonaten** automatisch endgültig bereinigt.

Ein idempotenter Hintergrundprozess sucht regelmäßig nach Datensätzen mit:

```text
deleted_at + 3 Monate <= aktueller Zeitpunkt
```

Der Purge muss fachliche Abhängigkeiten kontrolliert behandeln. Blindes Datenbank-Cascade-Löschen wird für komplexe Schüler- und Lerndaten vermieden.

## Klassen und Schüler unterscheiden

Das Löschen einer Klasse löscht nicht automatisch ihre Schülerkonten.

Ein Schüler kann:

- aus einer Klasse entfernt werden,
- in eine andere Klasse wechseln,
- im nächsten Schuljahr einer neuen Klasse zugeordnet werden,
- die Schule verlassen.

Nur der Vorgang **Schüler verlässt die Schule** startet den Soft-Delete-Lebenszyklus des Schülerkontos.

Für Klassenzugehörigkeiten wird eine eigene Membership-Struktur vorgesehen, statt lediglich eine `class_id` am Benutzer zu speichern.

## Persistenz

PostgreSQL ist die zentrale Datenbank für operative Daten, beispielsweise:

- Schulen,
- Benutzer und Rollen,
- Klassen und Kurse,
- Mitgliedschaften,
- Materialzuweisungen,
- Arbeitsstände,
- Antworten und Revisionen,
- Übungsstände,
- Lernfortschritt,
- Feedback.

Dateiuploads werden getrennt von großen Binärdaten in relationalen Tabellen behandelt; Metadaten und Berechtigungen bleiben in PostgreSQL.

## Schülerantworten und Autosave

Während der Bearbeitung liegt der aktuelle Zustand im Angular-Frontend in Signals. Änderungen werden automatisch mit kurzer Verzögerung serverseitig gespeichert.

Die Oberfläche zeigt den Speicherzustand sichtbar an, beispielsweise:

```text
Speichert …
Gespeichert 10:42
Speichern fehlgeschlagen
```

Für wichtige Antworten ist ein Versionsmodell vorgesehen, damit versehentlich überschriebene Inhalte wiederhergestellt werden können.

## Materialquelle und Schülerdaten

Fachliche Quellen und individuelle Schülerdaten werden strikt getrennt:

```text
Git / Markdown / SVG
        │
        ▼
veröffentlichtes Material
        │
        ▼
individuelle Schülerinstanz
        │
        ▼
Antworten / Fortschritt / Revisionen in PostgreSQL
```

Eine Änderung am Markdown darf vorhandene Schülerantworten nicht überschreiben.
