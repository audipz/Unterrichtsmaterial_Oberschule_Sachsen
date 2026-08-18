# Lösungen – Computergrafik und Datenmengen

## Berechnung
**640 × 480:** 307 200 Pixel. Bei 24 Bit = 3 Byte pro Pixel ergibt das 921 600 Byte ≈ 0,88 MiB.

**1920 × 1080:** 2 073 600 Pixel. Bei 24 Bit = 3 Byte pro Pixel ergibt das 6 220 800 Byte ≈ 5,93 MiB.

**Faktor:** 2 073 600 ÷ 307 200 = 6,75. Bei gleicher Farbtiefe steigt auch die unkomprimierte Rohdatenmenge um Faktor 6,75.

## Bildrate und Engpässe
Bei Echtzeitgrafik müssen Bilder wiederholt berechnet und Daten rechtzeitig bereitgestellt werden. Engpässe können daher auch bei starker GPU durch langsamen Massenspeicher, zu geringe Speicherbandbreite, fehlenden Grafikspeicher oder andere Systemgrenzen entstehen.

## Streaming
Stärkere Kompression bzw. geringere Datenrate kann Detailverlust und Artefakte verursachen. Die sichtbare Qualität hängt deshalb nicht allein von der Displayauflösung ab.
