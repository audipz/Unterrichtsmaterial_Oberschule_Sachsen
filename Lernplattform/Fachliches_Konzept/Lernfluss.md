# Lernfluss

## Ziel

Die Plattform unterstützt einen kontinuierlichen Lernprozess. Sie ist kein Prüfungs- oder Benotungssystem.

Ein zentrales Prinzip ist das **selbstständige Lernen**: Schüler sollen Arbeitsblätter, Arbeitsheftabschnitte und Übungen auch dann bearbeiten können, wenn keine Lehrkraft sie vorher einzeln zugewiesen oder freigeschaltet hat.

Ein Lernbereich kann aus drei aufeinander bezogenen Teilen bestehen:

```text
Lernbereich
├── Nachschlagewerk
├── Arbeitsheft / Arbeitsblätter
└── Übungen
```

## Zwei Nutzungswege

Die Plattform unterstützt zwei gleichwertige Wege.

### Selbstständiger Lernweg

Ein Schüler kann aus den für seine Klassenstufe beziehungsweise Schule verfügbaren Materialien selbst auswählen:

```text
Material öffnen
    ↓
Arbeitsblatt oder Übung starten
    ↓
Bearbeiten
    ↓
Autosave
    ↓
später fortsetzen
```

Dafür ist **keine Lehrerzuweisung erforderlich**.

### Begleiteter Lernweg

Eine Lehrkraft kann zusätzlich Materialien für einen Kurs hervorheben oder zuweisen. Dadurch erscheinen sie beispielsweise in einem Bereich „Für meinen Kurs“.

Die Zuweisung erleichtert Organisation und Unterrichtsbegleitung, ist aber **keine technische Voraussetzung zum Bearbeiten** eines grundsätzlich verfügbaren Materials.

> **Grundsatz:** Lehrerzuweisungen ergänzen den Selbstlernmodus, sie ersetzen ihn nicht.

## Materialkatalog

Schüler erhalten einen für sie zugänglichen Materialkatalog.

Mögliche Struktur:

```text
Klasse 7
├── Informatik und Computer
│   ├── Nachschlagewerk
│   ├── Arbeitsblatt
│   └── Übungen
├── Binärsystem
│   ├── Nachschlagewerk
│   ├── Arbeitsblatt
│   └── Übungen
└── ...
```

Ein Schüler kann aus diesem Katalog selbst ein Arbeitsblatt oder eine Übung starten.

Die Plattform legt beim ersten Bearbeiten automatisch eine persönliche Instanz beziehungsweise einen persönlichen Arbeitsstand an.

## Beispiel Klasse 7: Binärsystem

```text
Binärsystem
├── Nachschlagewerk
│   ├── Stellenwerte
│   ├── Binär → Dezimal
│   ├── Dezimal → Binär
│   └── Binäraddition
│
├── Arbeitsheft / Arbeitsblätter
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

Für einen Schüler kann ein Lernbereich beziehungsweise ein selbst gestartetes Material folgende Bearbeitungszustände besitzen:

```text
NOT_STARTED
IN_PROGRESS
COMPLETED
```

Diese Zustände sind keine Leistungsbewertung.

Ein Arbeitsheftbereich kann als `COMPLETED` gelten, wenn alle als Pflichtaufgaben markierten Aufgaben bearbeitet wurden.

Bei Freitext bedeutet „bearbeitet“ nicht automatisch „fachlich richtig“.

## Nachschlagewerk

Das Nachschlagewerk ist der jederzeit verfügbare Wissensbereich.

Die Plattform kann speichern:

- zuletzt geöffneten Abschnitt,
- Lesezeichen,
- persönliche Markierungen als spätere Erweiterung.

Ein Schüler muss ein Nachschlagekapitel nicht formal „abschließen“.

## Arbeitsheft und Arbeitsblätter

Arbeitsheftabschnitte und einzelne Arbeitsblätter sind persönliche Bearbeitungsbereiche.

Ein Schüler kann sie selbstständig starten. Beim ersten Öffnen beziehungsweise ersten Bearbeiten wird automatisch ein eigener Arbeitsstand angelegt.

Für jede Aufgabe werden gespeichert:

- aktuelle Antwort,
- Bearbeitungsstatus,
- letzte Änderung,
- Revisionsnummer,
- gegebenenfalls Lehrerfeedback.

Die Plattform speichert automatisch. Ein Schüler kann auf einem Gerät beginnen und nach erneuter Anmeldung auf einem anderen Gerät weiterarbeiten.

### Meine Materialien

Selbst gestartete und von Lehrern zugewiesene Materialien erscheinen gemeinsam in einem persönlichen Bereich, beispielsweise:

```text
Meine Materialien
├── Binärsystem – Arbeitsblatt        in Bearbeitung
├── Dateien und Ordner – Übungen      begonnen
└── EVA(S) – Arbeitsheft              abgeschlossen
```

Dabei kann sichtbar gekennzeichnet werden, ob ein Material selbst gestartet oder von einer Lehrkraft empfohlen beziehungsweise zugewiesen wurde.

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

Sie müssen grundsätzlich **selbstständig startbar** sein, sofern sie für den Schüler im Materialkatalog sichtbar sind.

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
Arbeitsblatt: 8 von 10 Aufgaben bearbeitet
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

Arbeitsblätter und Übungen kennen ihren Lernbereich und gegebenenfalls ein Lernziel.

Dadurch können direkte Verweise angeboten werden:

```text
Noch unsicher bei Binäraddition?
→ Nachschlagewerk: „Binärzahlen addieren“
```

Nach dem Nachlesen kehrt der Schüler zur vorherigen Aufgabe zurück.

## Lehrerfeedback

Eine Lehrkraft kann ausgewählten Antworten eine Rückmeldung hinzufügen, wenn das Material zu einem von ihr betreuten Kurs beziehungsweise Schüler gehört und die entsprechenden Rechte vorliegen.

Beispiele:

- Verständnisfrage beantworten,
- auf einen Denkfehler hinweisen,
- auf einen Nachschlageabschnitt verweisen,
- eine gute Erklärung bestätigen.

Feedback und Schülerantwort bleiben getrennte Datensätze.

## Lehrerübersicht

Eine Lehreransicht kann beispielsweise zeigen:

| Schüler | Arbeitsmaterial | Übungen | Letzte Aktivität |
|---|---:|---|---|
| Anna | 100 % | Binäraddition geübt | heute |
| Ben | 80 % | Umrechnung begonnen | gestern |
| Clara | 100 % | alle Bereiche bearbeitet | heute |

Dabei muss unterscheidbar bleiben, was von der Lehrkraft zugewiesen und was vom Schüler selbstständig begonnen wurde.

Die Übersicht dient der Unterrichtsbegleitung und nicht der automatischen Benotung.

## Kein Prüfungsmodus

Die Lernplattform besitzt ausdrücklich keinen Prüfungsmodus. Nicht vorgesehen sind insbesondere:

- benotete Lernkontrollen,
- begrenzte Prüfungsversuche,
- Prüfungs-Countdowns,
- automatische Noten,
- Prüfungssperren,
- Anti-Cheating-Mechanismen.

Der Fokus bleibt auf **selbstständigem Lernen**, dauerhaft gespeicherten Arbeitsständen und gezielter Unterstützung.