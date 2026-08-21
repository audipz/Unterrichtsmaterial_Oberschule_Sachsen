# Material- und Aufgabenformat

## Ziel

Die bestehenden Unterrichtsmaterialien sollen weiterhin als gut lesbares Markdown gepflegt werden können. Gleichzeitig benötigt die Lernplattform eine eindeutige maschinenlesbare Struktur für interaktive Aufgaben und Lernbereiche.

Das Format muss zwei Anforderungen gleichzeitig erfüllen:

1. Menschen sollen die Quelldateien im Repository weiterhin gut lesen und bearbeiten können.
2. Ein Importer muss Aufgaben, IDs, Typen und Metadaten zuverlässig erkennen können.

## Grundsatz: stabile IDs

Jedes interaktive Element erhält eine **dauerhaft stabile ID**.

Beispiel:

```text
k7-binaer-umrechnung-01
```

Die ID darf nach Veröffentlichung nicht nur deshalb geändert werden, weil eine Überschrift, Reihenfolge oder Formulierung angepasst wird. Schülerantworten und Lernstände beziehen sich auf diese ID.

Empfohlenes Schema:

```text
k<klasse>-<thema>-<kurzname>-<nummer>
```

## Materialtypen

Die Plattform unterscheidet:

- `REFERENCE` – Nachschlagewerk
- `WORKBOOK` – Arbeitsheft
- `EXERCISE_SET` – interaktive Übungssammlung

**Ein Materialtyp für Lernkontrollen, Prüfungen oder Benotung ist nicht vorgesehen.**

## Strukturierte Markdown-Blöcke

Für interaktive Aufgaben werden Pandoc-kompatible fenced divs verwendet.

```markdown
::: exercise
id: k7-binaer-dezimal-01
type: short-text

Wandle `101101₂` in eine Dezimalzahl um.
:::
```

Pflichtfelder:

```text
id
type
```

Optionale Metadaten:

```text
title
competencies
difficulty
required
feedback-mode
```

## Aufgabentypen Version 1

### short-text

Kurze Texteingabe oder eindeutiger Wert.

Antwortformat:

```json
{ "text": "13" }
```

### long-text

Mehrzeilige freie Antwort.

### single-choice

Genau eine Antwort ist auswählbar.

### multiple-choice

Mehrere Antworten können ausgewählt werden.

### number

Numerische Antwort mit optionaler Einheit beziehungsweise Toleranz.

### table

Strukturierte Tabellenantwort.

### code

Mehrzeiliger Quelltext beziehungsweise Pseudocode. In Version 1 wird Code als Text gespeichert; automatische Programmausführung ist ein separates späteres Feature.

### file-upload

Datei als Arbeitsergebnis. Dateigröße, MIME-Type und erlaubte Endungen werden serverseitig geprüft.

## Spätere Aufgabentypen

Mögliche Erweiterungen:

- Zuordnung/Matching,
- Reihenfolge,
- Lückentext,
- grafische Eingabe,
- eingebettete Programmierumgebung,
- interaktive Netzwerk- oder Datenbankaufgaben.

## Lösungen und Lernfeedback

Für **Übungen** dürfen serverseitig hinterlegte erwartete Antworten oder Lösungsinformationen existieren, damit unmittelbares Lernfeedback möglich ist. Diese Informationen dürfen nicht vorab in der Schülerrepräsentation ausgeliefert werden.

Beispiel:

```markdown
::: solution
for: k7-binaer-dezimal-01

`13`
:::
```

Dieser Block dient ausschließlich der Selbstkontrolle beziehungsweise der Erzeugung von Lernfeedback. Er ist **keine Prüfungs- oder Benotungslösung**.

Bei offenen Arbeitsheftaufgaben ist eine Musterlösung nicht zwingend erforderlich.

## Feedback-Modi

Eine Übung kann beispielsweise definieren:

```text
feedback-mode: immediate
```

oder

```text
feedback-mode: hint-first
```

Denkbare Lernreaktionen:

- richtig/falsch bei eindeutig prüfbaren Aufgaben,
- ein fachlicher Hinweis,
- ein weiterer Versuch,
- Verweis auf einen Abschnitt des Nachschlagewerks,
- optional später die Lösung anzeigen.

Es entstehen daraus keine Punkte oder Noten.

## Lernbereiche

Ein Lernbereich fasst zusammengehörige Inhalte zusammen.

```markdown
::: learning-unit
id: k7-binaersystem
title: Binärsystem
order: 3

... Inhalt und Aufgaben ...

:::
```

Ein Lernbereich kann verknüpfen:

- Nachschlagewerk-Kapitel,
- Arbeitsheftabschnitt,
- Übungssammlung.

## Kompetenzen / Lernziele

Für die erste Version genügt ein stabiler Schlüssel, beispielsweise:

```text
BIN_STELLENWERT
BIN_TO_DEC
DEC_TO_BIN
BIN_ADD
```

Eine Aufgabe kann einem oder mehreren Lernzielen zugeordnet werden. Das ermöglicht Rückverweise und eine fachlich strukturierte Fortschrittsanzeige.

## Schwierigkeit

Für den Anfang:

```text
BASIC
STANDARD
ADVANCED
```

Die Schwierigkeit dient zur Auswahl passender Übungen und nicht zur Benotung.

## Pflichtaufgaben

`required: true` bedeutet, dass eine Aufgabe für die Bearbeitungsvollständigkeit eines Arbeitsheftabschnitts berücksichtigt wird.

Bei Freitext kann bereits eine vorhandene Antwort als „bearbeitet“ zählen. Das System behauptet dadurch nicht, die Antwort sei fachlich richtig.

## Materialversion

Beim Import wird eine veröffentlichte, unveränderliche Materialfassung erzeugt. Eine Schülerzuweisung verweist auf diese Fassung.

Änderungen im Repository erzeugen eine neue Fassung, überschreiben aber nicht automatisch laufende Schülerinstanzen.

## Änderungen an Aufgaben

Unkritische Änderungen können bei stabiler ID erfolgen, beispielsweise Rechtschreibung oder klarere Formulierung.

Wenn sich die eigentliche fachliche Aufgabe wesentlich ändert, erhält sie eine neue ID. Dadurch bleiben vorhandene Schülerantworten eindeutig interpretierbar.

## Validierung beim Import

Der Import muss mindestens prüfen:

- IDs sind eindeutig,
- jede interaktive Aufgabe besitzt `id` und `type`,
- der Aufgabentyp ist bekannt,
- referenzierte Lernziele existieren,
- Lösungs-/Feedbackdaten verweisen auf eine existierende Aufgabe,
- Auswahlaufgaben besitzen Antwortoptionen,
- stabile IDs enthalten keine Leerzeichen.

Fehler verhindern die Veröffentlichung der Materialfassung.

## Darstellung im klassischen Build

Interaktive Aufgaben müssen auch in PDF/DOCX/HTML sinnvoll lesbar bleiben. Technische Metadaten wie `id`, `type` oder `competencies` werden in der Schülerausgabe nicht angezeigt.

Damit bleibt eine Quelle für beide Nutzungsarten möglich:

```text
Markdown
 ├── klassischer Build → PDF/DOCX/HTML
 └── Lernplattform-Import → interaktives Lernen
```
