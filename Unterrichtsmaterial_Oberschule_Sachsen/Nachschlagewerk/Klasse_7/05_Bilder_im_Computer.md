# 5 Bilder im Computer

## Bilder aus Pixeln

Ein digitales Rasterbild besteht aus vielen kleinen Bildpunkten, den **Pixeln**. Jeder Pixel besitzt einen Farbwert. Betrachtet man ein Bild aus normaler Entfernung, nimmt das menschliche Auge die einzelnen Pixel meist nicht getrennt wahr. Aus vielen Bildpunkten entsteht der Eindruck eines zusammenhängenden Bildes.

## Auflösung

Die **Auflösung** beschreibt bei Rasterbildern häufig die Anzahl der Pixel in Breite und Höhe, beispielsweise `1920 × 1080` Pixel.

Bekannte Bezeichnungen sind unter anderem:

| Bezeichnung | typische Auflösung |
|---|---:|
| HD | 1280 × 720 |
| Full HD | 1920 × 1080 |
| UHD / 4K UHD | 3840 × 2160 |

Solche Bezeichnungen allein sagen jedoch nicht, wie groß ein Bildschirm ist oder wie scharf ein Ausdruck wirkt. Entscheidend ist auch, auf welcher Fläche die Pixel verteilt werden.

## Pixeldichte

Die **Pixeldichte** beschreibt, wie viele Pixel auf einer bestimmten Strecke liegen. Bei Bildschirmen wird häufig **ppi** (pixels per inch) verwendet. Je mehr Pixel auf derselben Fläche liegen, desto schwerer sind einzelne Pixel zu erkennen.

Beim Drucken wird häufig **dpi** (dots per inch) verwendet. Pixel eines Bildes und Druckpunkte eines Druckers sind nicht genau dasselbe, auch wenn die Begriffe im Alltag manchmal vermischt werden.

## Farben

Farben werden durch Zahlenwerte codiert. Bei Bildschirmen ist das **RGB-Modell** verbreitet. Dabei wird eine Farbe aus Anteilen von Rot, Grün und Blau zusammengesetzt.

```text
R = Rot
G = Grün
B = Blau
```

Je mehr unterschiedliche Werte pro Farbkanal gespeichert werden können, desto mehr Farbabstufungen lassen sich darstellen.

## Dateigröße und Kompression

Ein Bild mit vielen Pixeln und hoher Farbtiefe kann viel Speicher benötigen. Bildformate verwenden deshalb häufig **Kompression**.

Bei **verlustfreier Kompression** können die ursprünglichen Bilddaten vollständig wiederhergestellt werden. Bei **verlustbehafteter Kompression** werden Daten weggelassen, um die Datei stärker zu verkleinern. Zu starke Kompression kann sichtbare Bildfehler erzeugen.

## Raster- und Vektorgrafik

Rastergrafiken speichern Pixel. **Vektorgrafiken** beschreiben dagegen Formen mathematisch, beispielsweise Linien, Kreise und Flächen. Sie lassen sich deshalb ohne typische Pixelbildung stark vergrößern.

| Rastergrafik | Vektorgrafik |
|---|---|
| besteht aus Pixeln | besteht aus beschriebenen Formen |
| gut für Fotos | gut für Logos, Symbole, Diagramme |
| beim starken Vergrößern werden Pixel sichtbar | lässt sich meist verlustfrei skalieren |
| Beispiele: JPEG, PNG | Beispiel: SVG |

> **Merke:** Eine höhere Pixelzahl kann mehr Details ermöglichen, benötigt aber nicht automatisch in jeder Situation eine höhere sichtbare Qualität.

## Begriffe zum Nachschlagen

**Auflösung:** Anzahl der Bildpunkte in Breite und Höhe beziehungsweise Detailgrad einer Darstellung.

**Kompression:** Verfahren zur Verringerung der benötigten Datenmenge.

**Pixel:** einzelner Bildpunkt eines Rasterbildes.

**Rastergrafik:** Bild, das aus Pixeln aufgebaut ist.

**RGB:** Farbmodell aus Rot, Grün und Blau.

**Vektorgrafik:** Grafik, deren Elemente durch mathematisch beschriebene Formen dargestellt werden.

→ Siehe auch **Kapitel 8: Speicher und Datenmengen** und das Kapitel **Computergrafik**.
