# Material- und Aufgabenformat

## Ziel

Die bestehenden Unterrichtsmaterialien sollen weiterhin als gut lesbares Markdown gepflegt werden können. Gleichzeitig benötigt die Lernplattform eine eindeutige maschinenlesbare Struktur für interaktive Aufgaben, Lernbereiche und Lernkontrollen.

Das Format muss deshalb zwei Anforderungen gleichzeitig erfüllen:

1. Menschen sollen die Quelldateien im Repository weiterhin gut lesen und bearbeiten können.
2. Ein Importer muss Aufgaben, IDs, Typen und Metadaten zuverlässig erkennen können.

## Grundsatz: stabile IDs

Jedes interaktive Element erhält eine **dauerhaft stabile ID**.

Beispiel:

```text
k7-binaer-umrechnung-01
```

Die ID darf nach Veröffentlichung nicht nur deshalb geändert werden, weil eine Überschrift, Reihenfolge oder Formulierung angepasst wird.

Die Schülerantworten beziehen sich auf diese ID. Eine Änderung der ID würde aus Sicht der Plattform eine neue Aufgabe erzeugen.

Empfohlenes Schema:

```text
k<klasse>-<thema>-<kurzname>-<nummer>
```

Beispiele:

```text
k7-binaer-dezimal-01
k7-binaer-addition-02
k8-algorithmen-auswahl-01
k9-datenbanken-schluessel-03
```

Die ID ist ein technischer Schlüssel und keine sichtbare Aufgabennummer.

## Materialtypen

Die Plattform unterscheidet mindestens:

- `REFERENCE` – Nachschlagewerk
- `WORKBOOK` – Arbeitsheft
- `EXERCISE_SET` – Übungssammlung
- `ASSESSMENT` – Lernkontrolle

Eine Markdown-Datei kann weiterhin normalen erklärenden Inhalt enthalten. Interaktive Blöcke werden zusätzlich ausgezeichnet.

## Vorgeschlagene Markdown-Syntax

Für strukturierte Blöcke werden Pandoc-kompatible fenced divs verwendet.

Beispiel:

```markdown
::: exercise
id: k7-binaer-dezimal-01
type: short-text
points: 2

Wandle `101101₂` in eine Dezimalzahl um.
:::
```

Der Inhalt zwischen Kopf und Ende bleibt normales Markdown.

Der Importer liest die Metadaten im Kopf des Blocks und den Markdown-Inhalt als Aufgabenstellung.

## Warum fenced divs?

- weiterhin lesbares Markdown,
- gut in Git-Diffs erkennbar,
- keine separate zweite Aufgabenquelle notwendig,
- kompatibel mit dem vorhandenen Pandoc-orientierten Build,
- später durch einen eigenen Filter auch für PDF/DOCX sinnvoll darstellbar.

## Gemeinsame Eigenschaften einer Aufgabe

Pflichtfelder:

```text
id
type
```

Empfohlene optionale Felder:

```text
title
points
competencies
difficulty
required
```

Beispiel:

```markdown
::: exercise
id: k7-binaer-addition-01
type: short-text
title: Binärzahlen addieren
points: 3
difficulty: BASIC
competencies: [BIN_ADD]
required: true

Addiere `0101₂ + 0011₂`.
:::
```

## Aufgabentypen Version 1

Für die erste Version sollten nur Typen aufgenommen werden, die wir sauber darstellen, speichern und gegebenenfalls auswerten können.

### short-text

Kurze Texteingabe oder eindeutiger Wert.

```markdown
::: exercise
id: k7-binaer-dezimal-01
type: short-text

Wandle `1101₂` in eine Dezimalzahl um.
:::
```

Antwortformat:

```json
{
  "text": "13"
}
```

### long-text

Mehrzeilige freie Antwort.

```markdown
::: exercise
id: k7-info-kontext-01
type: long-text

Erkläre mit einem eigenen Beispiel, warum dieselben Daten je nach Kontext unterschiedliche Informationen liefern können.
:::
```

Antwortformat:

```json
{
  "text": "..."
}
```

### single-choice

Genau eine Antwort ist auswählbar.

```markdown
::: exercise
id: k7-binaer-choice-01
type: single-choice

Welche Dezimalzahl entspricht `1010₂`?

- [ ] 8
- [x] 10
- [ ] 12
- [ ] 14
:::
```

In Schülerausgaben darf die Markierung der richtigen Lösung nicht sichtbar sein. Beim Import wird die Lösung getrennt in den nicht für Schüler freigegebenen Lösungsteil übernommen.

### multiple-choice

Mehrere Antworten können richtig sein.

```markdown
::: exercise
id: k7-dateien-formate-01
type: multiple-choice

Welche Dateiformate sind typische Rastergrafikformate?

- [x] PNG
- [x] JPEG
- [ ] SVG
- [ ] TXT
:::
```

### number

Numerische Antwort mit optionaler Toleranz.

```markdown
::: exercise
id: k7-speicher-bit-byte-01
type: number
unit: Byte

Wie viele Byte sind 8000 Bit?
:::
```

### table

Strukturierte Tabellenantwort.

```markdown
::: exercise
id: k7-evas-tabelle-01
type: table
columns: [Schritt, Beispiel]
rows: 4

Ordne für eine Smartphone-Kamera Eingabe, Verarbeitung, Ausgabe und Speicherung zu.
:::
```

### code

Mehrzeiliger Quelltext beziehungsweise Pseudocode.

```markdown
::: exercise
id: k8-karol-schleife-01
type: code
language: karol

Schreibe eine Wiederholung, mit der Karol fünf Schritte vorwärts geht.
:::
```

Die erste Version speichert Code als Text. Automatische Programmausführung ist ein separates späteres Feature.

### file-upload

Datei als Ergebnis einer Aufgabe.

```markdown
::: exercise
id: k7-grafik-logo-01
type: file-upload
accept: [.svg, .png]

Erstelle die geforderte Grafik und lade dein Ergebnis hoch.
:::
```

Dateigröße, MIME-Type und erlaubte Endungen werden serverseitig geprüft.

## Spätere Aufgabentypen

Mögliche spätere Erweiterungen:

- Zuordnung/Matching,
- Reihenfolge,
- Lückentext,
- grafische Eingabe,
- eingebettete Programmierumgebung,
- interaktive Netzwerk- oder Datenbankaufgaben.

Sie werden erst aufgenommen, wenn Datenmodell, UI und Bewertung eindeutig definiert sind.

## Lösungen getrennt behandeln

Lösungen dürfen nicht Bestandteil der für Schüler ausgelieferten Aufgabenrepräsentation sein.

Im Repository kann für lehrerbezogene Materialien ein strukturierter Lösungsblock verwendet werden:

```markdown
::: solution
for: k7-binaer-dezimal-01

`13`
:::
```

Der Importer ordnet diesen Block der Aufgabe zu. Die Schüler-API liefert ihn nicht aus.

Damit bleibt auch bei automatischer Bewertung die Lösung serverseitig geschützt.

## Lernbereiche

Aufgaben werden Lernbereichen zugeordnet.

Beispiel:

```markdown
::: learning-unit
id: k7-binaersystem
title: Binärsystem
order: 3

... Inhalt und Aufgaben ...

:::
```

Ein Lernbereich kann Verknüpfungen auf folgende Materialien besitzen:

- Nachschlagewerk-Kapitel,
- Arbeitsheftabschnitt,
- Übungssammlung,
- Lernkontrolle.

## Kompetenzen / Lernziele

Für die erste Version genügt ein einfacher stabiler Schlüssel.

Beispiel:

```text
BIN_STELLENWERT
BIN_TO_DEC
DEC_TO_BIN
BIN_ADD
```

Eine Aufgabe kann mehrere Lernziele prüfen:

```text
competencies: [BIN_STELLENWERT, BIN_TO_DEC]
```

Die Bezeichnungen und Beschreibungen der Kompetenzen werden zentral gepflegt und relational importiert.

## Schwierigkeit

Für den Anfang reichen drei Stufen:

```text
BASIC
STANDARD
ADVANCED
```

Diese Stufen dienen nicht als Bewertung des Schülers, sondern als Metadaten für Übungen und Aufgabenpools.

## Punkte

`points` beschreibt die maximal erreichbaren Punkte einer Aufgabe.

Bei Arbeitsheftaufgaben kann `points` fehlen. Bei Lernkontrollaufgaben soll die Punktzahl angegeben sein.

## Pflichtaufgaben

`required: true` bedeutet, dass eine Aufgabe für die fachliche Vollständigkeit eines Arbeitsheftabschnitts berücksichtigt wird.

Eine Aufgabe kann trotzdem ohne automatische Bewertung abgeschlossen werden. Beispielsweise kann bei Freitext bereits das Vorhandensein einer Antwort für den Bearbeitungsfortschritt zählen.

## Materialversion

Beim Import wird aus einer veröffentlichten Materialfassung eine unveränderliche `MaterialVersion` erzeugt.

Eine Schülerzuweisung verweist immer auf diese Version.

Änderungen im Repository erzeugen bei erneuter Veröffentlichung eine neue Version, überschreiben aber nicht automatisch laufende Schülerinstanzen.

## Änderungen an Aufgaben

### Unkritische Änderungen

Bei gleicher stabiler ID können beispielsweise korrigiert werden:

- Rechtschreibung,
- klarere Formulierung,
- zusätzliche Erklärung,
- Layout.

Trotzdem entsteht bei einer neuen Veröffentlichung eine neue Materialversion.

### Fachlich wesentliche Änderungen

Wenn sich die eigentliche Aufgabe oder erwartete Antwort so stark ändert, dass bestehende Schülerantworten nicht mehr vergleichbar sind, erhält die Aufgabe eine **neue ID**.

Beispiel:

```text
k7-binaer-addition-01
```

bleibt für dieselbe fachliche Aufgabe bestehen.

Wird daraus eine völlig andere Aufgabe, wird beispielsweise

```text
k7-binaer-addition-02
```

angelegt.

## Validierung beim Import

Der Import muss mindestens prüfen:

- IDs sind innerhalb des Materialbestands eindeutig,
- jede interaktive Aufgabe besitzt `id` und `type`,
- Aufgabentyp ist bekannt,
- referenzierte Kompetenzen existieren,
- Lösung verweist auf eine existierende Aufgabe,
- Lernkontrollaufgaben besitzen eine gültige Punktzahl,
- bei Auswahlaufgaben sind Antwortoptionen vorhanden,
- stabile IDs enthalten keine Leerzeichen oder wechselnden Laufnummern aus sichtbaren Überschriften.

Fehler führen dazu, dass die Materialversion nicht veröffentlicht wird.

## Darstellung im klassischen Build

Interaktive Aufgaben müssen auch in PDF/DOCX/HTML sinnvoll lesbar bleiben.

Beispielsweise wird aus:

```markdown
::: exercise
...
Frage ...
:::
```

im klassischen Arbeitsheft eine normale Aufgabe mit ausreichend Platz für handschriftliche beziehungsweise dokumentbasierte Bearbeitung.

Technische Metadaten wie `id`, `type` oder `competencies` werden in der Schülerausgabe nicht angezeigt.

Damit bleibt eine Quelle für beide Nutzungsarten möglich:

```text
Markdown
 ├── klassischer Build → PDF/DOCX/HTML
 └── Lernplattform-Import → interaktive Aufgabe
```
