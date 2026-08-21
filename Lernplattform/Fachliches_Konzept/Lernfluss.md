# Lernfluss

## Ziel

Die Plattform unterstützt einen kontinuierlichen Lernprozess. Sie ist kein Prüfungs- oder Benotungssystem.

Ein Lernbereich kann aus drei aufeinander bezogenen Teilen bestehen:

```text
Lernbereich
├── Nachschlagewerk
├── Arbeitsheft
└── Übungen
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
└── Übungen
    ├── freie Wiederholung
    ├── Hinweise
    ├── unmittelbare Rückmeldung
    └── Rückverweise zum Nachschlagewerk
```

## Lernbereich

Ein Lernbereich ist die fachliche Klammer um zusammengehörige Inhalte.

Mögliche Eigenschaften:

- stabile ID,
- Titel,
- Beschreibung,
- Klassenstufe,
- Reihenfolge,
- Lernziele,
- Verweise auf Materialien.

## Bearbeitungszustand

Für einen Schüler kann ein Lernbereich beispielsweise folgende Bearbeitungszustände besitzen:

```text
NOT_STARTED
IN_PROGRESS
COMPLETED
```

Diese Zustände sind keine Leistungsbewertung.

Ein Bereich kann als `COMPLETED` gelten, wenn alle als Pflichtaufgaben markierten Arbeitsheftaufgaben bearbeitet wurden.

Bei Freitext bedeutet „bearbeitet“ nicht automatisch „fachlich richtig“.

## Nachschlagewerk

Das Nachschlagewerk ist der jederzeit verfügbare Wissensbereich.

Die Plattform kann speichern:

- zuletzt geöffneten Abschnitt,
- Lesezeichen,
- persönliche Markierungen als spätere Erweiterung.

Ein Schüler muss ein Nachschlagekapitel nicht formal „abschließen“.

## Arbeitsheft

Das Arbeitsheft ist der persönliche Bearbeitungsbereich.

Für jede Aufgabe werden gespeichert:

- aktuelle Antwort,
- Bearbeitungsstatus,
- letzte Änderung,
- Revisionsnummer,
- gegebenenfalls Lehrerfeedback.

Die Plattform speichert automatisch. Ein Schüler kann auf einem Gerät beginnen und nach erneuter Anmeldung auf einem anderen Gerät weiterarbeiten.

## Autosave

Der sichtbare Zustand wird sofort im Angular-Signal aktualisiert. Die Serverübertragung erfolgt mit kurzer Verzögerung.

Die Oberfläche zeigt beispielsweise:

```text
Speichert …
Gespeichert 14:32
Speichern fehlgeschlagen
```

Eine veraltete Revision darf keine neuere Serverversion still überschreiben.

## Übungen

Übungen dienen der Selbstkontrolle und dem Vertiefen.

Mögliche Eigenschaften:

- beliebig viele Versuche,
- sofortige Rückmeldung bei eindeutig prüfbaren Aufgaben,
- fachliche Hinweise,
- Lösung nach mehreren Versuchen,
- Verweis auf einen passenden Nachschlagewerk-Abschnitt,
- unterschiedliche Schwierigkeitsstufen.

## Fachliche Rückmeldung

Bei einer eindeutig prüfbaren Aufgabe kann die Plattform beispielsweise antworten:

```text
Noch nicht richtig.
Achte auf den Stellenwert 4.
→ „Binärzahl in Dezimalzahl umwandeln“ nachlesen
```

Bei offenen Aufgaben sollte das System nicht versuchen, ohne belastbare Grundlage eine fachliche Bewertung vorzutäuschen. Dort kann Lehrerfeedback sinnvoller sein.

## Wiederholen

Eine Übung darf beliebig oft wiederholt werden. Frühere Fehlversuche sollen nicht als negative Bewertung behandelt werden.

Fehler sind Teil des Lernprozesses.

Optional kann gespeichert werden:

- Anzahl der Versuche,
- letzter Bearbeitungszeitpunkt,
- zuletzt verwendeter Hinweis,
- ob eine Aufgabe zuletzt selbstständig gelöst wurde.

Diese Daten dienen ausschließlich zur Lernunterstützung.

## Lernfortschritt

Die Plattform unterscheidet mindestens:

### Bearbeitungsfortschritt

Beispielsweise:

```text
Arbeitsheft: 8 von 10 Pflichtaufgaben bearbeitet
```

### Übungsfortschritt

Beispielsweise:

```text
Stellenwerte       geübt
Binär → Dezimal    geübt
Dezimal → Binär    noch offen
Binäraddition      begonnen
```

Eine spätere fachliche Einschätzung muss nachvollziehbar bleiben und darf nicht automatisch mit einer Note gleichgesetzt werden.

## Rückverweise

Arbeitsheft und Übungen kennen ihren Lernbereich und gegebenenfalls ein Lernziel.

Dadurch können direkte Verweise angeboten werden:

```text
Noch unsicher bei Binäraddition?
→ Nachschlagewerk: „Binärzahlen addieren“
```

Nach dem Nachlesen kehrt der Schüler zur vorherigen Aufgabe zurück.

## Lehrerfeedback

Eine Lehrkraft kann ausgewählten Antworten eine Rückmeldung hinzufügen.

Beispiele:

- Verständnisfrage beantworten,
- auf einen Denkfehler hinweisen,
- auf einen Nachschlageabschnitt verweisen,
- eine gute Erklärung bestätigen.

Feedback und Schülerantwort bleiben getrennte Datensätze.

## Lehrerübersicht

Eine Lehreransicht kann beispielsweise zeigen:

| Schüler | Arbeitsheft | Übungen | Letzte Aktivität |
|---|---:|---|---|
| Anna | 100 % | Binäraddition geübt | heute |
| Ben | 80 % | Umrechnung begonnen | gestern |
| Clara | 100 % | alle Bereiche bearbeitet | heute |

Die Übersicht dient der Unterrichtsbegleitung und nicht der automatischen Benotung.

## Kein Prüfungsmodus

Die Lernplattform besitzt ausdrücklich keinen Prüfungsmodus. Nicht vorgesehen sind insbesondere:

- benotete Lernkontrollen,
- begrenzte Prüfungsversuche,
- Prüfungs-Countdowns,
- automatische Noten,
- Prüfungssperren,
- Anti-Cheating-Mechanismen.

Der Fokus bleibt auf selbstständigem Lernen, dauerhaft gespeicherten Arbeitsständen und gezielter Unterstützung.
