# Lernfluss und Lernkontrollen

## Ziel

Die Plattform soll nicht nur Arbeitsblätter digital anzeigen. Sie soll einen nachvollziehbaren Lernprozess unterstützen, bei dem Nachschlagewerk, Arbeitsheft, Übung und Lernkontrolle zusammengehören.

Ein Lernbereich kann deshalb aus mehreren aufeinander bezogenen Bestandteilen bestehen:

```text
Lernbereich
├── Nachschlagewerk
├── Arbeitsheft
├── Übungen
└── Lernkontrolle
```

## Beispiel Klasse 7: Binärsystem

```text
Binärsystem
├── Nachschlagewerk
│   ├── Stellenwerte
│   ├── Binär → Dezimal
│   ├── Dezimal → Binär
│   └── Binäraddition
│
├── Arbeitsheft
│   ├── Stellenwerte anwenden
│   ├── Umrechnungen
│   └── Binäraddition
│
├── Übungen
│   ├── freie Wiederholung
│   └── unmittelbare Rückmeldung
│
└── Lernkontrolle
    ├── Stellenwerte
    ├── Umrechnung
    └── Addition
```

## Lernbereich

Ein Lernbereich ist die fachliche Klammer um zusammengehörige Inhalte.

Mögliche Eigenschaften:

- stabile ID,
- Titel,
- Beschreibung,
- Klassenstufe,
- Reihenfolge,
- Lernziele beziehungsweise Kompetenzen,
- Verweise auf Materialien.

Ein Lernbereich muss nicht exakt einem einzelnen Markdown-Kapitel entsprechen. Er kann mehrere Kapitel oder Teilabschnitte verbinden.

## Bearbeitungszustand

Für einen Schüler kann ein Lernbereich beispielsweise folgende Zustände besitzen:

```text
NOT_STARTED
IN_PROGRESS
COMPLETED
```

Diese Zustände beziehen sich auf die Bearbeitung und sind nicht automatisch eine Leistungsbewertung.

Ein Bereich kann als `COMPLETED` gelten, wenn alle als Pflichtaufgaben gekennzeichneten Arbeitsheftaufgaben bearbeitet wurden.

Bei Freitext kann zunächst das Vorhandensein einer Antwort als „bearbeitet“ gelten, ohne dass das System behauptet, die Antwort sei fachlich richtig.

## Nachschlagewerk

Das Nachschlagewerk ist jederzeit lesbar, sofern der Lehrer den Bereich freigegeben hat.

Es ist keine Voraussetzung, dass der Schüler jede Seite „abschließt“. Die Plattform darf aber speichern, welche Bereiche zuletzt geöffnet wurden, um das Weiterarbeiten zu erleichtern.

## Arbeitsheft

Das Arbeitsheft ist der persönliche Bearbeitungsbereich des Schülers.

Für jede Aufgabe werden gespeichert:

- aktuelle Antwort,
- Bearbeitungsstatus,
- letzte Änderung,
- Revision,
- gegebenenfalls Lehrerfeedback.

Die Plattform speichert automatisch.

Ein Schüler kann die Bearbeitung auf einem Gerät beginnen und später nach erneuter Anmeldung auf einem anderen Gerät fortsetzen.

## Übungen

Übungen dienen der Selbstkontrolle und dürfen weniger streng sein als Lernkontrollen.

Mögliche Eigenschaften:

- beliebig viele Versuche,
- sofortige Rückmeldung,
- Hinweise,
- Lösung nach einem oder mehreren Fehlversuchen,
- Verweis auf den passenden Abschnitt des Nachschlagewerks.

Ein Übungsversuch zählt nicht als offizielle Lernkontrolle.

## Lernkontrolle

Eine Lernkontrolle ist eine bewusst gestartete Überprüfung eines Lernbereichs.

Sie besitzt eine eigene Zuweisung durch den Lehrer.

Mögliche Einstellungen:

```text
available_from
available_until
max_attempts
time_limit_minutes
show_score_immediately
show_solutions_after
require_workbook_completion
```

## Freigabe

Eine Lernkontrolle kann auf verschiedene Weise freigegeben werden.

### Direkte Freigabe

Der Lehrer gibt sie unabhängig vom Arbeitsheft frei.

### Freigabe nach Arbeitsheft

Optional kann gelten:

```text
require_workbook_completion = true
```

Dann darf ein Schüler die Lernkontrolle erst beginnen, wenn die Pflichtaufgaben des zugehörigen Arbeitsheftbereichs bearbeitet sind.

Das ist eine organisatorische Regel und keine Aussage darüber, dass alle Arbeitsheftantworten fachlich richtig sind.

## Start einer Lernkontrolle

Beim Start wird ein `AssessmentAttempt` erzeugt.

Gespeichert werden mindestens:

- Schüler,
- Lernkontrollzuweisung,
- verwendete Material-/Aufgabenversion,
- Startzeit,
- Status,
- gegebenenfalls Endzeit,
- ausgewählte Aufgaben bei Aufgabenpool-Nutzung.

Nach dem Start darf sich die zugrunde liegende Aufgabenfassung für diesen Versuch nicht mehr verändern.

## Status eines Versuchs

Vorgesehen sind beispielsweise:

```text
STARTED
SUBMITTED
AUTO_GRADED
REVIEW_REQUIRED
GRADED
EXPIRED
```

`EXPIRED` kann verwendet werden, wenn ein definiertes Zeitlimit abläuft.

## Zeitlimit

Bei einer Lernkontrolle mit Zeitlimit wird die Endzeit **serverseitig** aus dem Startzeitpunkt berechnet.

Das Frontend zeigt einen Countdown, ist aber nicht die maßgebliche Zeitquelle.

Damit kann ein Schüler das Zeitlimit nicht einfach durch Manipulation der lokalen Uhr umgehen.

Verbindungsabbrüche müssen fachlich gesondert berücksichtigt werden. Antworten sollen während des Versuchs regelmäßig gespeichert werden.

## Autosave bei Lernkontrollen

Auch während einer Lernkontrolle werden Antworten automatisch gespeichert.

Das bedeutet nicht, dass jede Speicherung eine Abgabe ist.

```text
Antwort eingeben
    ↓
Autosave
    ↓
weiter bearbeiten
    ↓
Lernkontrolle abgeben
```

Erst die explizite Abgabe beziehungsweise das serverseitige Ende des Versuchs setzt den Versuch auf `SUBMITTED`.

## Bewertung

### Automatisch auswertbare Aufgaben

Beispiele:

- Single Choice,
- Multiple Choice,
- Zahlenantwort mit definierter Regel,
- einfache eindeutige Kurzantworten.

### Lehrerbewertung erforderlich

Beispiele:

- freie Erklärung,
- längerer Programmcode,
- Datei-/Grafikabgabe,
- komplexe Tabellenantwort.

Eine Lernkontrolle kann beide Arten enthalten.

## Punkte und Ergebnis

Ein Versuch speichert die erreichten Punkte pro Aufgabe sowie die Gesamtpunktzahl.

Die Plattform soll zwischen folgenden Informationen unterscheiden:

- automatisch berechnete Punkte,
- durch Lehrer korrigierte Punkte,
- Lehrerfeedback,
- Endergebnis.

Eine automatische Bewertung darf durch eine berechtigte Lehrkraft korrigiert werden, falls dies fachlich notwendig ist.

## Sichtbarkeit der Ergebnisse

Der Lehrer legt fest, wann Schüler Informationen sehen dürfen.

Mögliche Varianten:

- Punktzahl sofort nach Abgabe,
- Punktzahl erst nach Lehrerfreigabe,
- richtige Lösungen erst nach Ablauf des Freigabezeitraums,
- Lehrerfeedback erst nach Abschluss der Bewertung.

Die API muss sicherstellen, dass noch nicht freigegebene Lösungen nicht an das Frontend übertragen werden.

## Aufgabenpool

Später kann eine Lernkontrolle aus einem Aufgabenpool zusammengestellt werden.

Beispiel:

```text
Binärsystem – 20 Punkte

2 Aufgaben Stellenwerte
2 Aufgaben Binär → Dezimal
2 Aufgaben Dezimal → Binär
2 Aufgaben Binäraddition
```

Zusätzlich können Schwierigkeitsverteilungen definiert werden.

Beim Start eines Versuchs wird die konkrete Auswahl gespeichert. Dadurch bleibt der Versuch auch später nachvollziehbar.

## Vergleichbarkeit

Wenn unterschiedliche Aufgabenvarianten verwendet werden, müssen sie hinsichtlich Lernziel, Schwierigkeit und erreichbarer Punkte ausreichend vergleichbar sein.

Ein zufälliger Aufgabenpool darf nicht dazu führen, dass ein Schüler zufällig eine wesentlich schwierigere Lernkontrolle erhält.

## Wiederholungsversuche

Bei mehreren erlaubten Versuchen muss festgelegt werden, welches Ergebnis zählt.

Mögliche Strategien:

```text
LAST_ATTEMPT
BEST_ATTEMPT
FIRST_ATTEMPT
```

Für die erste Version sollte der Lehrer diese Strategie bei der Zuweisung festlegen können.

## Lehrerübersicht

Für einen Lernbereich kann eine Lehreransicht beispielsweise zeigen:

| Schüler | Arbeitsheft | Lernkontrolle | Ergebnis | Status |
|---|---|---|---:|---|
| Anna | vollständig | abgegeben | 17/20 | bewertet |
| Ben | 80 % | nicht gestartet | – | offen |
| Clara | vollständig | abgegeben | – | Lehrerbewertung nötig |

Bei Freitext- oder Dateiaufgaben kann die Lehrkraft direkt zur betreffenden Antwort springen.

## Fachlicher Fortschritt

Neben der reinen Bearbeitung kann aus Lernkontrollen später ein fachlicher Fortschritt abgeleitet werden.

Beispiel:

```text
Stellenwerte       sicher
Binär → Dezimal    sicher
Dezimal → Binär    teilweise sicher
Binäraddition      noch unsicher
```

Dabei muss klar bleiben, auf welcher Grundlage eine solche Aussage entsteht. Ein einzelner Fehler darf nicht automatisch zu einer dauerhaften Einstufung führen.

## Rückverweise zum Nachschlagewerk

Übungen und Lernkontrollen kennen die zugehörigen Lernziele und Lernbereiche. Dadurch kann nach einer Übung beispielsweise angeboten werden:

```text
Noch unsicher bei Binäraddition?
→ Abschnitt „Binärzahlen addieren“ öffnen
```

Während einer echten Lernkontrolle kann dieser Zugriff je nach gewünschtem Prüfungsmodus eingeschränkt werden. Diese Entscheidung liegt beim Lehrer beziehungsweise bei der Konfiguration der Lernkontrolle.

## Kein Zwang zur vollständigen Automatisierung

Die Plattform soll Lehrkräfte unterstützen, nicht fachliche Entscheidungen vollständig ersetzen.

Deshalb gilt:

- automatische Auswertung nur für geeignete Aufgaben,
- Lehrer darf Punkte und Feedback überprüfen,
- Lernfortschritt bleibt nachvollziehbar,
- keine automatische pädagogische Entscheidung allein aus einem einzelnen Messwert.
