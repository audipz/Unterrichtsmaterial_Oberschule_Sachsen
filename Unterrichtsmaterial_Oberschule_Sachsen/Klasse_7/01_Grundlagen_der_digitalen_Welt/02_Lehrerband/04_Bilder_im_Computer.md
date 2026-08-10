# Lehrerband – 04 Bilder im Computer

## Einordnung

Die Schülerinnen und Schüler kennen bereits Binärzahlen sowie die Darstellung von Texten über Zeichencodes. Nun übertragen sie die Grundidee „Information wird als Zahl dargestellt“ auf digitale Bilder.

## Leitfrage

> **Wie speichert ein Computer Bilder, obwohl er nur Zahlen und Bits kennt?**

## Kompetenzen

Die Schülerinnen und Schüler können

- Pixel als elementare Bildpunkte erklären,
- die Auflösung eines Rasterbilds bestimmen,
- das RGB-Modell grundlegend beschreiben,
- den Zusammenhang zwischen Farbtiefe und Speicherbedarf erklären,
- einfachen Speicherbedarf berechnen,
- Raster- und Vektorgrafiken unterscheiden.

## Prüfungsrelevant

- Pixel
- Auflösung
- RGB
- 8 Bit = 256 Werte
- 24 Bit pro RGB-Pixel bei 8 Bit je Farbkanal
- einfacher Speicherbedarf
- Unterschied Raster-/Vektorgrafik

Nicht vertiefend prüfen:

- konkrete JPEG-/PNG-Kompressionsverfahren
- Farbmanagement
- Alpha-Kanal

## Material

- Arbeitsheft Kapitel 04
- Präsentation Kapitel 04
- Material M04
- kariertes Papier oder Pixelraster
- optional Bildschirm mit stark vergrößertem Bild

## Unterrichtsverlauf – 45 Minuten

### Rückblick – 3 Minuten

Fragen:

- Wie werden Buchstaben im Computer gespeichert?
- Warum ist eine Vereinbarung wie ASCII oder Unicode nötig?

Überleitung:

> Wenn Buchstaben als Zahlen gespeichert werden können, gilt das vielleicht auch für Bilder.

### Einstieg – 5 Minuten

Ein stark vergrößertes Bild zeigen oder ein grobes Pixelbild an die Tafel zeichnen.

Impuls:

> Was passiert, wenn wir ein digitales Bild immer weiter vergrößern?

Erwartung: einzelne Kästchen/Punkte werden sichtbar.

Begriff **Pixel** einführen.

### Erarbeitung 1 – 10 Minuten

Schwarz-Weiß-Pixelraster aus dem Arbeitsheft bearbeiten.

Ziel: Die Lernenden erkennen unmittelbar, dass ein sehr einfaches Bild bereits mit `0` und `1` beschrieben werden kann.

Anschließend Auflösung erklären:

```text
Breite × Höhe
```

### Erarbeitung 2 – 12 Minuten

RGB-Modell.

Tafelbild:

```text
Rot   Grün   Blau
 8     8      8 Bit
          ↓
        24 Bit
```

Wichtiger Rückgriff auf Binärzahlen:

```text
2⁸ = 256
```

Damit wird erklärt, warum Werte von 0 bis 255 üblich sind.

### Erarbeitung 3 – 8 Minuten

Einfachen Speicherbedarf gemeinsam berechnen.

Beispiel bewusst klein wählen:

```text
100 × 100 Pixel
24 Bit je Pixel
```

Danach Aufgabe 5 selbstständig oder paarweise.

### Sicherung – 5 Minuten

Gemeinsam festhalten:

> Ein Rasterbild besteht aus Pixeln. Für jeden Pixel werden Informationen über seine Farbe gespeichert.

Kurze Gegenüberstellung Raster/Vektor.

### Ausblick – 2 Minuten

> Ein Bild können wir in Zahlen zerlegen. Aber Schall verändert sich ständig. Wie kann daraus eine digitale Datei werden?

## Typische Fehlvorstellungen

### „Ein Pixel ist immer ein Bit.“

Nur bei sehr einfachen Schwarz-Weiß-Darstellungen kann ein Bit pro Pixel genügen. Ein Farbpixel benötigt wesentlich mehr Information.

### „1920 × 1080 bedeutet 1920 + 1080 Pixel.“

Die Gesamtzahl ergibt sich durch Multiplikation.

### „RGB 255 bedeutet 255 Bit.“

Der Wert 255 ist der größte Wert, der sich mit 8 Bit ohne Vorzeichen darstellen lässt.

### „JPEG-Bilder brauchen immer genau Breite × Höhe × 24 Bit.“

Diese Rechnung beschreibt einen vereinfachten unkomprimierten Speicherbedarf. Dateiformate können komprimieren.

### „Vektorgrafiken haben keine Pixel.“

Bei der Darstellung auf einem Bildschirm werden sie natürlich auf Pixel gerendert. Die Datei beschreibt jedoch primär geometrische Formen statt einer festen Pixelmatrix.

## Differenzierung

### Unterstützung

- Zweierpotenzen-Tabelle bereitstellen.
- Speicherrechnung Schritt für Schritt gliedern: Pixel → Bit → Byte.
- RGB zunächst nur mit Grundfarben behandeln.

### Erweiterung

Schnelle Schülerinnen und Schüler können untersuchen:

- Warum ergeben 32 Bit pro Pixel häufig Sinn?
- Welche Rolle könnte Transparenz spielen?
- Warum eignet sich SVG gut für Logos?

Diese Erweiterungen sind nicht verpflichtend.

## Tafelbild

```text
DIGITALES BILD

Rasterbild
   ↓
Pixel
   ↓
Position + Farbe

RGB
Rot:  0 ... 255
Grün: 0 ... 255
Blau: 0 ... 255

8 + 8 + 8 = 24 Bit je Pixel

Speicher ≈ Pixelanzahl × Bit je Pixel
```

## Wenn die Zeit knapp wird

- Raster-/Vektorgrafik nur kurz mündlich gegenüberstellen.
- Aufgabe 6 als Hausaufgabe oder Einstieg der nächsten Stunde nutzen.
- Kompression nur als Hinweis erwähnen.

Nicht streichen sollte man den Zusammenhang `2⁸ = 256`, da er die vorherige Binärstunde sinnvoll mit dem RGB-Modell verbindet.