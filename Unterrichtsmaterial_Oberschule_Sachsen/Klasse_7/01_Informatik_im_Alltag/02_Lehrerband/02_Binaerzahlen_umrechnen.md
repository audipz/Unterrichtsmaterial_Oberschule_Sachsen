# Lehrerband – 02 Wie schreibt ein Computer Zahlen?

## Einordnung

Die vorherige Stunde hat geklärt, dass Computer zwei Zustände unterscheiden und diese als `0` und `1` darstellen können. In dieser Stunde wird daraus erstmals ein vollständiges Zahlensystem.

Die Schülerinnen und Schüler sollen zwei Umrechnungsverfahren kennenlernen:

1. Zerlegung in Zweierpotenzen,
2. wiederholte Division durch 2.

Beide Verfahren sind prüfungsrelevant. Die Lernenden dürfen später das für sie verständlichere Verfahren bevorzugen, sollten aber beide erklären können.

## Zeit

45 Minuten

## Lernziele

Die Schülerinnen und Schüler können

- die Stellenwerte des Binärsystems als Zweierpotenzen erklären,
- Dezimalzahlen mit Zweierpotenzen in Binärzahlen umwandeln,
- Dezimalzahlen durch wiederholte Division durch 2 umwandeln,
- Binärzahlen wieder in Dezimalzahlen überführen,
- beide Verfahren vergleichen.

## Benötigtes Material

- Arbeitsheft Kapitel 02
- Präsentation Kapitel 02
- Materialblatt `M02_Binaerzahlen_Uebung.md`
- optional Karten mit `1, 2, 4, 8, 16, 32, 64`

## Rückblick – ca. 4 Minuten

Fragen:

> Was ist ein Bit?

Erwartung: Eine Stelle mit zwei möglichen Zuständen, meist `0` und `1`.

> Warum reichen 0 und 1 grundsätzlich aus, um Informationen darzustellen?

Erwartung: Mehrere Bits können kombiniert und nach festen Regeln gedeutet werden.

## Einstieg – ca. 4 Minuten

An der Tafel steht:

```text
13
```

Daneben:

```text
1101
```

Impuls:

> Könnten beide Schreibweisen dieselbe Zahl meinen?

Nicht sofort auflösen. Vermutungen sammeln.

## Erarbeitung I – Zweierpotenzen – ca. 10 Minuten

Die Stellenwerttabelle gemeinsam entwickeln:

```text
... 32 16 8 4 2 1
```

Wichtig ist zunächst das Muster der Verdopplung. Erst danach die Potenzschreibweise ergänzen:

```text
2⁵ 2⁴ 2³ 2² 2¹ 2⁰
```

Beispiel 13 gemeinsam zerlegen:

```text
13 = 8 + 4 + 1
```

Daraus:

```text
8 4 2 1
1 1 0 1
```

### Typische Fehlvorstellung

Manche Lernende setzen `2⁰ = 0`.

Unbedingt sichern:

```text
2⁰ = 1
```

## Erarbeitung II – Division durch 2 – ca. 9 Minuten

Am selben Beispiel `13` das zweite Verfahren zeigen.

Die Reste deutlich in einer eigenen Spalte notieren.

Kernpunkt:

> Die Reste werden von unten nach oben gelesen.

Ein häufiger Fehler ist das Lesen in Rechenrichtung. Deshalb unmittelbar eine Kontrollfrage stellen:

> Wie können wir prüfen, ob `1101₂` wirklich 13 ergibt?

Rückrechnung über die Stellenwerte.

## Übungsphase – ca. 12 Minuten

Arbeitsheft Aufgaben 1–3 oder Materialblatt.

Empfehlung:

- schwächere Lernende zunächst Zahlen bis 15,
- mittleres Niveau bis 31,
- schnelle Lernende bis 127.

Die Lehrkraft fordert bei mindestens einer Zahl ausdrücklich beide Verfahren.

## Sicherung – ca. 4 Minuten

Gemeinsam festhalten:

```text
Dezimal → Binär
1. Zweierpotenzen ODER
2. Division durch 2

Binär → Dezimal
Stellenwerte mit 1 addieren
```

## Ausblick – ca. 2 Minuten

> Wir können jetzt Zahlen nur mit 0 und 1 darstellen. Aber auf eurem Smartphone stehen keine Binärzahlen, sondern Texte. Wie wird aus einer Zahl ein Buchstabe?

Damit wird die nächste Stunde zur Zeichencodierung vorbereitet.

## Differenzierung

### Unterstützung

- vorbereitete Stellenwertkarten verwenden,
- Potenzschreibweise zunächst weglassen und mit `1, 2, 4, 8, ...` arbeiten,
- Umrechnung in Partnerarbeit mit gegenseitiger Kontrolle.

### Erweiterung

Fragen:

- Welche größte Zahl kann mit 4 Bits dargestellt werden?
- Welche mit 8 Bits?
- Erkennst du eine Regel?

Erwartung:

```text
4 Bit → 15
8 Bit → 255
```

Die allgemeine Formel `2^n - 1` kann als Erweiterung genannt werden, muss in Klasse 7 aber nicht formalisiert werden.

## Typische Fehler

- Stellenwerte werden von links mit `1,2,4,...` begonnen statt von rechts.
- `2⁰` wird falsch berechnet.
- Reste der Divisionsmethode werden von oben nach unten gelesen.
- Führende Nullen werden als wertverändernd interpretiert.

## Wenn heute weniger Zeit ist

Die Divisionsmethode vollständig erklären und nur eine gemeinsame Übung rechnen. Aufgabe 3 und der Methodenvergleich können als Einstieg in der Folgestunde nachgeholt werden.

## Prüfungsrelevanz

Ja. Erwartet werden:

- Stellenwerte/Zweierpotenzen,
- Dezimal → Binär,
- Binär → Dezimal,
- mindestens ein nachvollziehbarer Rechenweg.