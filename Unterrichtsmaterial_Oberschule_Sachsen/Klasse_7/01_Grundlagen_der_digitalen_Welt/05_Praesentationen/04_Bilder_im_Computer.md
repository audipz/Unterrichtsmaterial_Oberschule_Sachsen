---
title: "Wie speichert ein Computer Bilder?"
lang: de-DE
---

# Wie speichert ein Computer Bilder?

## Rückblick

> Wie hat ein Computer in der letzten Stunde Buchstaben gespeichert?

---

# Leitfrage

> **Wie kann ein Computer ein Bild speichern, wenn er nur Zahlen und Bits kennt?**

---

# Ganz nah heranzoomen

Was passiert, wenn wir ein digitales Bild immer weiter vergrößern?

**Pixel werden sichtbar.**

---

# Pixel

Ein Rasterbild besteht aus vielen kleinen Bildpunkten.

> **Pixel = Picture Element**

---

# Ein Bild aus 0 und 1

```text
0 0 1 0 0
0 1 1 1 0
1 1 1 1 1
0 1 1 1 0
0 0 1 0 0
```

Vereinbarung:

```text
0 = weiß
1 = schwarz
```

> Erkennt ihr das Bild?

---

# Auflösung

```text
1920 × 1080 Pixel
```

Frage:

> Wie viele Pixel sind das insgesamt?

---

# Mehr als Schwarz und Weiß

Ein Farbpixel braucht zusätzliche Informationen.

**Welche Farbe soll dargestellt werden?**

---

# RGB

```text
R = Rot
G = Grün
B = Blau
```

Beispiele:

```text
(255, 0, 0)     → Rot
(0, 255, 0)     → Grün
(0, 0, 255)     → Blau
(255, 255, 255) → Weiß
(0, 0, 0)       → Schwarz
```

---

# Warum 255?

Aus der Binärstunde:

```text
2⁸ = 256
```

Mit 8 Bit sind 256 Werte möglich:

```text
0 ... 255
```

---

# Ein RGB-Pixel

```text
Rot   Grün   Blau
8 Bit 8 Bit  8 Bit
  \     |     /
      24 Bit
```

---

# Mehr Pixel = mehr Information

Ein unkomprimiertes Bild:

```text
Pixelanzahl × Bit je Pixel
```

Beispiel:

```text
100 × 100 × 24 Bit
```

---

# Warum ist ein JPEG oft kleiner?

Dateiformate können Bilddaten **komprimieren**.

- verlustfrei
- verlustbehaftet

Die genaue Technik kommt heute nicht dran.

---

# Raster oder Vektor?

**Rastergrafik**

- besteht aus Pixeln
- gut für Fotos

**Vektorgrafik**

- beschreibt Formen und Linien
- gut für Logos und Zeichnungen

---

# Sicherung

Ein digitales Rasterbild benötigt mindestens Informationen über:

1. die Anordnung der Pixel,
2. die Farbe der Pixel,
3. die verwendete Farbdarstellung.

---

# Ausblick

> **Ein Bild können wir in Zahlen zerlegen. Aber Musik besteht aus Schallwellen. Wie bekommt ein Computer einen Ton in Nullen und Einsen?**