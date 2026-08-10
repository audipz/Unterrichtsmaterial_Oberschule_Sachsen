# 04 – Wie speichert ein Computer Bilder?

## Rückblick

In der letzten Stunde habt ihr herausgefunden:

- Zeichen können durch Zahlen dargestellt werden.
- Ein Zeichensatz legt fest, welche Zahl zu welchem Zeichen gehört.
- Computer speichern Texte als Bitfolgen.

Heute beantworten wir die nächste Frage:

> **Wie kann ein Computer ein Bild speichern, obwohl er nur Zahlen und Bits kennt?**

---

# Ein Bild aus kleinen Punkten

Vergrößert man ein digitales Foto stark genug, erkennt man kleine quadratische Bildpunkte.

Diese Bildpunkte heißen **Pixel**.

> **Pixel** ist die Kurzform von *Picture Element* – also Bildelement.

Ein digitales Rasterbild besteht aus vielen Pixeln, die in Zeilen und Spalten angeordnet sind.

## Beispiel

Ein sehr kleines Schwarz-Weiß-Bild könnte aus einem Raster mit 5 × 5 Pixeln bestehen:

```text
0 0 1 0 0
0 1 1 1 0
1 1 1 1 1
0 1 1 1 0
0 0 1 0 0
```

Wir vereinbaren:

```text
0 = weiß
1 = schwarz
```

Damit kann bereits ein kleines Bild als Folge von Nullen und Einsen beschrieben werden.

---

## Aufgabe 1 – Pixelbild lesen

Zeichne ein 5 × 5-Raster auf kariertes Papier und färbe alle Felder schwarz, an denen im folgenden Raster eine `1` steht:

```text
1 0 0 0 1
0 1 0 1 0
0 0 1 0 0
0 1 0 1 0
1 0 0 0 1
```

Was erkennst du?

____________________________________________________

---

# Auflösung

Die **Auflösung** beschreibt, aus wie vielen Pixeln ein Bild besteht.

Beispiel:

```text
1920 × 1080 Pixel
```

Das bedeutet:

- 1920 Pixel in der Breite,
- 1080 Pixel in der Höhe.

Die Gesamtzahl der Pixel erhält man durch Multiplikation:

```text
1920 · 1080 = 2 073 600 Pixel
```

Das sind ungefähr 2,1 Millionen Pixel.

## Aufgabe 2 – Auflösung berechnen

Berechne die Anzahl der Pixel:

1. `800 × 600`
2. `1280 × 720`
3. `1920 × 1080`

---

# Farbe braucht mehr Information

Bei einem Schwarz-Weiß-Bild reicht eine einfache Unterscheidung:

```text
0 = weiß
1 = schwarz
```

Bei Farbbildern muss für jeden Pixel zusätzlich gespeichert werden, **welche Farbe** er besitzt.

Eine häufige Darstellung verwendet drei Farbanteile:

- Rot
- Grün
- Blau

Dafür wird die Abkürzung **RGB** verwendet.

---

# Das RGB-Modell

Eine Farbe wird durch drei Zahlen beschrieben:

```text
(Rot, Grün, Blau)
```

Typischerweise kann jeder Farbanteil Werte von `0` bis `255` annehmen.

Beispiele:

| Farbe | Rot | Grün | Blau |
|---|---:|---:|---:|
| Schwarz | 0 | 0 | 0 |
| Weiß | 255 | 255 | 255 |
| Rot | 255 | 0 | 0 |
| Grün | 0 | 255 | 0 |
| Blau | 0 | 0 | 255 |
| Gelb | 255 | 255 | 0 |

> **Merke:** Bei RGB entsteht eine Farbe durch das Mischen von rotem, grünem und blauem Licht.

---

## Aufgabe 3 – Farben untersuchen

Welche Farbe erwartest du ungefähr bei folgenden RGB-Werten?

1. `(255, 0, 0)`
2. `(0, 0, 255)`
3. `(255, 255, 255)`
4. `(0, 0, 0)`
5. `(255, 255, 0)`

---

# Warum gerade 0 bis 255?

Ein Farbkanal wird häufig mit **8 Bit** gespeichert.

Mit 8 Bit sind `2⁸ = 256` verschiedene Werte möglich.

Da beim Zählen mit `0` begonnen wird, reichen die Werte von:

```text
0 bis 255
```

Für Rot, Grün und Blau werden also jeweils 8 Bit benötigt:

```text
8 Bit + 8 Bit + 8 Bit = 24 Bit pro Pixel
```

Ein solches Bild besitzt eine **Farbtiefe von 24 Bit**.

---

# Wie viele Farben sind möglich?

Für jeden der drei Farbkanäle stehen 256 Werte zur Verfügung.

Also gibt es:

```text
256 · 256 · 256 = 16 777 216
```

mögliche Farbkombinationen.

Das sind mehr als 16 Millionen Farben.

---

## Aufgabe 4 – Bit und Farbe

1. Wie viele Werte sind mit 1 Bit möglich?
2. Wie viele Werte sind mit 2 Bit möglich?
3. Wie viele Werte sind mit 8 Bit möglich?
4. Wie viele Bit benötigt ein RGB-Pixel mit 8 Bit pro Farbkanal?

---

# Bildgröße und Speicherbedarf

Je mehr Pixel ein Bild besitzt und je mehr Bit pro Pixel gespeichert werden, desto mehr Speicher wird benötigt.

Vereinfacht gilt für ein unkomprimiertes Bild:

```text
Speicherbedarf = Anzahl der Pixel · Bit pro Pixel
```

## Beispiel

Ein Bild besitzt:

```text
100 × 100 Pixel
```

und verwendet 24 Bit pro Pixel.

Dann gilt:

```text
100 · 100 = 10 000 Pixel
10 000 · 24 Bit = 240 000 Bit
```

Da 8 Bit einem Byte entsprechen:

```text
240 000 : 8 = 30 000 Byte
```

Das sind ungefähr 30 kB.

---

## Aufgabe 5 – Speicherbedarf

Ein kleines Bild hat `200 × 100 Pixel` und eine Farbtiefe von 24 Bit.

1. Wie viele Pixel besitzt das Bild?
2. Wie viele Bit werden unkomprimiert benötigt?
3. Wie viele Byte sind das?

---

# Warum sind Bilddateien oft kleiner?

Die gerade berechnete Dateigröße gilt nur für eine vereinfachte **unkomprimierte** Speicherung.

Bildformate können Daten anders speichern und häufig komprimieren.

Beispiele:

- PNG
- JPEG
- GIF

Bei der Kompression wird versucht, weniger Speicher zu benötigen.

Dabei gibt es zwei grundlegende Möglichkeiten:

- **verlustfrei:** Das Original kann vollständig wiederhergestellt werden.
- **verlustbehaftet:** Einige Informationen werden weggelassen, um stärker zu verkleinern.

Für diese Unterrichtsstunde genügt die Grundidee. Die genauen Kompressionsverfahren sind nicht prüfungsrelevant.

---

# Rastergrafik oder Vektorgrafik?

Fotos werden meist als **Rastergrafiken** gespeichert. Sie bestehen aus Pixeln.

Logos und einfache Zeichnungen können dagegen als **Vektorgrafiken** gespeichert werden. Dabei werden nicht alle einzelnen Pixel gespeichert, sondern Formen und Linien beschrieben.

Beispiel:

```text
Kreis mit Mittelpunkt (100,100) und Radius 50
```

Vektorgrafiken können deshalb häufig stark vergrößert werden, ohne sichtbar zu verpixeln.

> **Merke:** Rastergrafiken speichern Pixel. Vektorgrafiken speichern geometrische Beschreibungen.

---

## Aufgabe 6 – Was passt besser?

Entscheide, ob eher Rastergrafik oder Vektorgrafik geeignet ist:

1. Urlaubsfoto
2. Schullogo
3. Screenshot
4. einfache geometrische Zeichnung
5. Klassenfoto

Begründe bei zwei Beispielen deine Entscheidung.

---

# Transfer – Ein Pixel ist Information

Bei Texten brauchten wir eine Vereinbarung, welche Zahl zu welchem Zeichen gehört.

Bei Bildern brauchen wir ebenfalls Vereinbarungen:

- Wo befindet sich der Pixel?
- Welche Farbe besitzt er?
- Wie viele Bit werden verwendet?

Damit zeigt sich erneut:

> **Für einen Computer sind auch Bilder letztlich Zahlen und Bitfolgen.**

---

# Das Wichtigste

- Rasterbilder bestehen aus Pixeln.
- Die Auflösung beschreibt die Anzahl der Pixel in Breite und Höhe.
- Beim RGB-Modell wird eine Farbe aus Rot, Grün und Blau zusammengesetzt.
- Häufig werden 8 Bit je Farbkanal und damit 24 Bit pro Pixel verwendet.
- Mehr Pixel und größere Farbtiefe bedeuten grundsätzlich mehr Speicherbedarf.
- Bilddateien können komprimiert werden.
- Rastergrafiken speichern Pixel, Vektorgrafiken geometrische Beschreibungen.

## Ausblick

> **Texte und Bilder können als Zahlen gespeichert werden. Aber Musik besteht aus Schallwellen – wie bekommt ein Computer einen Ton in Nullen und Einsen?**