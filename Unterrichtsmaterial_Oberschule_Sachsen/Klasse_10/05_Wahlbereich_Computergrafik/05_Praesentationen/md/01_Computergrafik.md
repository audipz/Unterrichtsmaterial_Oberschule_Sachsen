# Präsentation – Computergrafik

## Leitfrage

> **Wie entsteht aus Daten ein Bild – und warum unterscheiden sich Qualität, Speicherbedarf und Rechenaufwand?**

---

## Folie 1 – Computergrafik ist überall

Computergrafiken begegnen uns in:
- Fotos und Webseiten
- Spielen
- Filmen und Animationen
- Karten und Navigation
- technischen Zeichnungen
- Benutzeroberflächen
- Augmented und Virtual Reality
- KI-generierten Bildern

### LEHRERHINWEIS

**Computergrafik:** Erzeugung, Verarbeitung und Darstellung visueller Inhalte mit Computersystemen. Dazu gehören sowohl zweidimensionale als auch dreidimensionale Grafiken.

---

## Folie 2 – Zwei grundlegende Darstellungsprinzipien

### Rastergrafik
Ein Bild besteht aus vielen einzelnen Bildpunkten – den Pixeln.

### Vektorgrafik
Ein Bild wird durch mathematisch beschriebene Formen wie Linien, Kurven und Flächen dargestellt.

> **Beide Verfahren haben unterschiedliche Stärken.**

### LEHRERHINWEIS

**Rastergrafik:** Bilddarstellung als regelmäßiges Raster einzelner Pixel.

**Vektorgrafik:** Darstellung durch geometrische Objekte und mathematische Beschreibungen. Beim Anzeigen oder Drucken muss auch eine Vektorgrafik letztlich für das konkrete Ausgabegerät rasterisiert werden.

---

## Folie 3 – Wann Raster, wann Vektor?

**Rastergrafik eignet sich besonders für:**
- Fotos
- komplexe Farbverläufe
- pixelgenaue Bildbearbeitung

**Vektorgrafik eignet sich besonders für:**
- Logos
- Symbole
- Diagramme
- technische Zeichnungen

### LEHRERHINWEIS

Vektorgrafiken lassen sich ohne die für Rasterbilder typische Pixelbildung vergrößern, weil die Formen für die neue Ausgabegröße neu berechnet werden. Das bedeutet nicht, dass Vektorgrafik grundsätzlich „besser“ ist.

---

## Folie 4 – Dateiformat und Darstellungsprinzip sind nicht dasselbe

Typische Rasterformate:
- JPEG
- PNG
- WebP

Typisches Vektorformat:
- Scalable Vector Graphics (SVG)

Eine Datei kann zusätzlich Informationen wie Metadaten, Transparenz oder Farbprofile enthalten.

### LEHRERHINWEIS

**Dateiformat:** Festgelegte Struktur, in der Daten gespeichert werden.

**Scalable Vector Graphics (SVG):** XML-basiertes Vektorgrafikformat, das insbesondere im Web verbreitet ist.

JPEG und PNG sind Rasterformate. Die Wahl des Formats bestimmt nicht allein die sichtbare Qualität; Inhalt, Kompression und Einstellungen spielen ebenfalls eine Rolle.

---

## Folie 5 – Was ist ein Pixel?

**Pixel:** Kleinste adressierbare Bildeinheit einer Rastergrafik oder digitalen Anzeige.

Ein Pixel besitzt beispielsweise Informationen über:
- Farbe
- Helligkeit
- ggf. Transparenz

Viele Pixel zusammen ergeben das Bild.

### LEHRERHINWEIS

**Pixel** ist eine Kurzform von „Picture Element“. Ein Bildpixel in einer Datei und ein physisches Displayelement sind konzeptionell zu unterscheiden, auch wenn im Unterricht häufig vereinfachend von Bildpunkten gesprochen wird.

---

## Folie 6 – Was bedeutet Auflösung?

Beispiel:

**1920 × 1080 Pixel**

Das bedeutet:
- 1920 Pixel in der Breite
- 1080 Pixel in der Höhe
- insgesamt 2.073.600 Pixel

> **Die Pixelanzahl allein sagt noch nicht, wie groß ein Bildschirm oder Ausdruck ist.**

### LEHRERHINWEIS

**Bildauflösung:** Im schulischen Kontext kann damit die Anzahl der Pixel in Breite und Höhe gemeint sein. Der Begriff wird in unterschiedlichen Zusammenhängen auch anders verwendet. Deshalb möglichst konkret „Pixelmaße“ oder „Pixelanzahl“ nennen.

---

## Folie 7 – HD, Full HD, QHD, UHD und 8K

Typische Bezeichnungen:

| Bezeichnung | typische Pixelmaße | Pixel gesamt |
|---|---:|---:|
| HD | 1280 × 720 | ca. 0,9 Mio. |
| Full HD (FHD) | 1920 × 1080 | ca. 2,1 Mio. |
| Quad HD (QHD) | 2560 × 1440 | ca. 3,7 Mio. |
| Ultra HD (UHD / häufig „4K“) | 3840 × 2160 | ca. 8,3 Mio. |
| 8K UHD | 7680 × 4320 | ca. 33,2 Mio. |

### LEHRERHINWEIS

Die Bezeichnungen werden im Markt nicht immer völlig einheitlich verwendet. Im Consumer- und TV-Bereich bezeichnet „4K“ häufig Ultra HD mit 3840 × 2160 Pixeln. Im Kino-/Produktionsbereich existiert beispielsweise DCI 4K mit 4096 × 2160 Pixeln.

---

## Folie 8 – Doppelte Breite bedeutet nicht doppelte Pixelzahl

Full HD:
1920 × 1080 ≈ 2,1 Millionen Pixel

Ultra HD:
3840 × 2160 ≈ 8,3 Millionen Pixel

Breite × 2 und Höhe × 2

→ **Pixelzahl × 4**

### LEHRERHINWEIS

Die Folie eignet sich für eine kurze Kopfrechen- bzw. Schätzaufgabe. Eine höhere Pixelzahl erhöht potenziell Detaildarstellung, Datenmenge und Rechenaufwand, aber nicht automatisch die wahrgenommene Bildqualität im gleichen Verhältnis.

---

## Folie 9 – Seitenverhältnis

**Seitenverhältnis:** Verhältnis von Breite zu Höhe.

Beispiele:
- 16:9 – verbreitet bei Monitoren und Video
- 4:3 – ältere Bildschirm- und Bildformate
- 1:1 – quadratisch
- 9:16 – Hochformat, häufig bei Smartphones

> **Gleiche Pixelzahl bedeutet nicht automatisch gleiche Form.**

### LEHRERHINWEIS

Das Seitenverhältnis ist dimensionslos. 1920 × 1080 und 3840 × 2160 besitzen beide das Verhältnis 16:9, obwohl die Pixelanzahl stark unterschiedlich ist.

---

## Folie 10 – Mehr Pixel – sieht man den Unterschied immer?

Nicht unbedingt.

Die Wahrnehmung hängt unter anderem ab von:
- Größe der Darstellung
- Betrachtungsabstand
- Pixeldichte
- Sehvermögen
- Bildinhalt und Ausgangsmaterial

> **Ab einem bestimmten Punkt kann zusätzliche Auflösung unter den konkreten Bedingungen kaum noch wahrnehmbar sein.**

### LEHRERHINWEIS

Das menschliche Auge besitzt keine feste „Pixelgrenze“. Als grobe Orientierung wird für normales Sehvermögen häufig eine Winkelauflösung in der Größenordnung einer Bogenminute genannt. Die tatsächliche Wahrnehmung ist individuell und hängt von Kontrast und weiteren Bedingungen ab.

---

## Folie 11 – Pixeldichte: PPI

**Pixels per Inch (PPI):** Anzahl der Pixel pro Zoll einer dargestellten oder ausgegebenen Fläche.

Gleiche Pixelzahl auf kleiner Fläche:
→ höhere Pixeldichte

Gleiche Pixelzahl auf großer Fläche:
→ niedrigere Pixeldichte

### LEHRERHINWEIS

Ein Zoll entspricht 2,54 cm. PPI verbindet Pixelanzahl mit physischer Größe. Bei Displays wird häufig die Pixeldichte angegeben. Hohe PPI-Werte können dazu führen, dass einzelne Pixel bei normalem Betrachtungsabstand kaum unterscheidbar sind.

---

## Folie 12 – Bildschirm und Ausdruck sind nicht dasselbe

Ein Bild besitzt beispielsweise **3000 × 2000 Pixel**.

Bei 300 PPI:
→ ungefähr 25,4 × 16,9 cm

Bei 150 PPI:
→ ungefähr 50,8 × 33,9 cm

> **Dasselbe Bild kann klein gedruckt scharf und groß gedruckt sichtbar gröber wirken.**

### LEHRERHINWEIS

Die Druckgröße ergibt sich aus Pixelanzahl und gewünschter Pixeldichte. 3000 Pixel / 300 Pixel pro Zoll = 10 Zoll = 25,4 cm.

300 PPI ist ein verbreiteter Orientierungswert für hochwertige Druckausgabe, aber keine universelle Mindestanforderung. Betrachtungsabstand und Druckverfahren beeinflussen die sinnvolle Auflösung.

---

## Folie 13 – PPI und DPI sind nicht dasselbe

**Pixels per Inch (PPI):** Bildpixel pro Zoll bei einer bestimmten Ausgabegröße.

**Dots per Inch (DPI):** Druckpunkte, die ein Ausgabegerät pro Zoll erzeugen kann.

> **Ein Bildpixel und ein Druckpunkt sind nicht dasselbe.**

### LEHRERHINWEIS

**Dots per Inch (DPI):** Technische Punktauflösung eines Drucksystems. Ein Drucker kann mehrere Druckpunkte verwenden, um Farbe bzw. Tonwert eines einzelnen Bildpixels wiederzugeben.

Im Alltag werden PPI und DPI häufig vermischt. Im Unterricht sollte die Unterscheidung fachlich sauber erklärt werden.

---

## Folie 14 – Farbe braucht Daten

Ein Pixel kann unterschiedliche Farbinformationen speichern.

**Farbtiefe:** Anzahl der Bits, die zur Beschreibung der Farbe eines Pixels bzw. Farbkanals verwendet werden.

Beispiel:
24 Bit pro Pixel
→ 2²⁴ mögliche Bitkombinationen
→ etwa 16,7 Millionen mögliche Farbwerte

### LEHRERHINWEIS

Bei klassischem 24-Bit-RGB werden häufig 8 Bit pro Farbkanal verwendet: Rot, Grün und Blau. 256 × 256 × 256 = 16.777.216 mögliche Kombinationen.

Farbtiefe und tatsächlich wahrnehmbare bzw. darstellbare Farben sind nicht identisch; Geräte, Farbprofile und Inhalte beeinflussen die Ausgabe.

---

## Folie 15 – RGB: Licht wird addiert

**Red Green Blue (RGB)** ist ein additives Farbmodell.

Rot + Grün + Blau mit hoher Intensität
→ näherungsweise Weiß

Keine Lichtanteile
→ Schwarz

Typischer Einsatz:
- Displays
- digitale Bilder
- Kameraverarbeitung

### LEHRERHINWEIS

**Additives Farbmodell:** Farben entstehen durch Überlagerung von Lichtanteilen. RGB ist für selbstleuchtende bzw. lichtbasierte Ausgabesysteme geeignet.

Die Darstellung ist ein Modell; reale Displays unterscheiden sich in Farbraum, Helligkeit und technischer Umsetzung.

---

## Folie 16 – CMYK: Farbe beim Druck

**Cyan Magenta Yellow Key/Black (CMYK)** ist ein typisches Farbmodell für den Vierfarbdruck.

- Cyan
- Magenta
- Yellow / Gelb
- Key / Schwarz

Druckfarben beeinflussen das reflektierte Licht.

### LEHRERHINWEIS

**Subtraktives Farbmodell:** Farbmittel absorbieren Teile des einfallenden Lichts. CMYK ist ein praxisnahes Modell für viele Druckverfahren.

Schwarz wird zusätzlich verwendet, weil die Mischung idealisierter Grundfarben in der Praxis nicht einfach ein perfektes tiefes Schwarz liefert und Schwarz für Text, Kontrast und wirtschaftlichen Druck wichtig ist.

---

## Folie 17 – Warum sieht ein Ausdruck manchmal anders aus?

Mögliche Ursachen:
- Display erzeugt Licht, Papier reflektiert Licht
- RGB- und CMYK-Farbräume unterscheiden sich
- Displayhelligkeit
- Papier und Druckverfahren
- Farbprofile und Einstellungen

> **Nicht jede Bildschirmfarbe lässt sich identisch drucken.**

### LEHRERHINWEIS

**Farbraum:** Beschreibt einen bestimmten Bereich darstellbarer Farben und deren Zuordnung. Professionelles Farbmanagement ist deutlich komplexer; für Klasse 10 genügt die Erkenntnis, dass Geräte und Verfahren unterschiedliche Farbbereiche darstellen können.

Bezug zur Abschlusszeitung aus Bereich 02 herstellen.

---

## Folie 18 – Wie groß ist ein unkomprimiertes Bild?

Vereinfachte Rechnung:

**Breite × Höhe × Bit pro Pixel**

Beispiel:
640 × 480 × 24 Bit
= 7.372.800 Bit
= 921.600 Byte
≈ 0,88 MiB

### LEHRERHINWEIS

8 Bit = 1 Byte. Für die Umrechnung in Mebibyte (MiB) wird durch 1024² geteilt. Dezimale Megabyte (MB) verwenden dagegen 1000² Byte.

Die Rechnung beschreibt eine vereinfachte unkomprimierte reine Pixeldatenmenge. Dateiköpfe, Metadaten, Ausrichtung und weitere Daten sind nicht berücksichtigt.

---

## Folie 19 – Full HD unkomprimiert

1920 × 1080 × 24 Bit

= 49.766.400 Bit
= 6.220.800 Byte
≈ 5,93 MiB pro Bild

Vergleich zu 640 × 480 bei gleicher Farbtiefe:
→ **6,75-mal so viele Pixel und ungefähr 6,75-mal so viele reine Pixeldaten.**

### LEHRERHINWEIS

Diese Rechnung eignet sich, um den direkten Zusammenhang zwischen Pixelzahl und unkomprimierter Datenmenge zu zeigen. Eine tatsächliche JPEG-, PNG- oder Videodatei kann aufgrund von Kompression völlig andere Größen besitzen.

---

## Folie 20 – Warum komprimieren wir Bilder?

Ohne Kompression würden Bilder und Videos sehr große Datenmengen erzeugen.

Kompression kann:
- Speicherplatz reduzieren
- Übertragungszeit reduzieren
- Streaming ermöglichen

Zwei Grundideen:
- verlustfrei
- verlustbehaftet

### LEHRERHINWEIS

**Kompression:** Verfahren zur Verringerung der für Daten benötigten Speichermenge bzw. Darstellungslänge.

**Verlustfreie Kompression:** Originaldaten können vollständig rekonstruiert werden.

**Verlustbehaftete Kompression:** Informationen werden gezielt entfernt bzw. angenähert; das Original kann nicht bitgenau wiederhergestellt werden.

---

## Folie 21 – PNG oder JPEG?

**Portable Network Graphics (PNG):**
- verlustfreie Kompression
- unterstützt Transparenz
- häufig gut für Screenshots, Grafiken und scharfe Kanten

**JPEG:**
- typischerweise verlustbehaftete Bildkompression
- häufig sehr effizient für Fotografien

> **Das passende Format hängt vom Inhalt und Verwendungszweck ab.**

### LEHRERHINWEIS

PNG ist nicht grundsätzlich „qualitativ besser“ und JPEG nicht grundsätzlich „schlecht“. Bei Fotos kann PNG unnötig große Dateien erzeugen. Bei wiederholter verlustbehafteter JPEG-Kompression können sichtbare Artefakte zunehmen.

JPEG bezeichnet eine Familie von Standards; im Schulkontext genügt das verbreitete klassische Bildformat.

---

## Folie 22 – Transparenz und Alpha-Kanal

Neben Rot, Grün und Blau kann ein Bild zusätzliche Transparenzinformation besitzen.

**Alpha-Kanal:** zusätzlicher Kanal, der die Deckkraft bzw. Transparenz beschreibt.

Beispiel:
Ein Logo ohne sichtbaren rechteckigen Hintergrund.

### LEHRERHINWEIS

Nicht jedes Bildformat unterstützt einen Alpha-Kanal. PNG kann Transparenz speichern; klassisches JPEG besitzt keinen Alpha-Kanal.

Bei 32-Bit-RGBA werden häufig 8 Bit für Rot, Grün, Blau und Alpha verwendet.

---

## Folie 23 – Ebenen bei der Bildbearbeitung

Grafikprogramme können Inhalte auf getrennten Ebenen verwalten.

Beispiel:
- Hintergrund
- Foto
- Text
- Logo
- Korrekturebene

Vorteil:
Elemente können getrennt bearbeitet werden.

### LEHRERHINWEIS

**Ebene / Layer:** Logische Schicht innerhalb eines Grafikdokuments. Ebenen erleichtern nicht-destruktives bzw. getrenntes Bearbeiten. Ein exportiertes JPEG enthält typischerweise keine editierbare Ebenenstruktur mehr.

---

## Folie 24 – Von 2D zu 3D

Eine 3D-Szene kann vereinfacht bestehen aus:
- 3D-Modellen
- Materialien und Texturen
- Lichtquellen
- Kamera

Das Computersystem berechnet daraus ein zweidimensionales Bild für den Bildschirm.

### LEHRERHINWEIS

**3D-Modell:** Mathematische Beschreibung eines dreidimensionalen Objekts.

**Textur:** Bild- oder Dateninformation, die auf eine Oberfläche angewendet werden kann.

**Material:** Beschreibung, wie eine Oberfläche auf Licht reagiert bzw. dargestellt werden soll.

---

## Folie 25 – Polygone bilden Oberflächen

Viele 3D-Modelle bestehen aus einem Netz kleiner Flächen.

**Polygon:** Vieleck aus Punkten und Kanten.

In Echtzeitgrafik werden besonders häufig Dreiecke verwendet.

Mehr Geometriedetails können:
- Formen genauer darstellen
- aber auch mehr Verarbeitung erfordern.

### LEHRERHINWEIS

**Mesh:** Netz aus Punkten, Kanten und Flächen, das die Geometrie eines 3D-Modells beschreibt.

Dreiecke sind für Computergrafik besonders praktisch, weil drei nicht auf einer Geraden liegende Punkte eindeutig eine Ebene definieren.

---

## Folie 26 – Was bedeutet Rendering?

**Rendering:** Berechnung eines Bildes aus einer digitalen Szene bzw. Beschreibung.

Vereinfacht:

3D-Modelle + Materialien + Licht + Kamera
→ Berechnung
→ Pixelbild

Rendering kann in Echtzeit oder über längere Zeit erfolgen.

### LEHRERHINWEIS

Bei Echtzeitgrafik muss die Berechnung schnell genug für fortlaufende Bildausgabe erfolgen. Filmproduktion kann dagegen für einzelne Bilder erheblich mehr Rechenzeit verwenden.

Nicht alle Computergrafik wird aus 3D-Szenen gerendert; der Begriff wird auch breiter verwendet.

---

## Folie 27 – CPU und GPU teilen sich Arbeit

**Central Processing Unit (CPU):** universelle zentrale Recheneinheit für viele unterschiedliche Aufgaben.

**Graphics Processing Unit (GPU):** stark parallel arbeitende Recheneinheit, besonders geeignet für viele Grafik- und Datenoperationen.

> **Ein Spiel oder Grafikprogramm benötigt das Zusammenspiel mehrerer Komponenten.**

### LEHRERHINWEIS

Die Gegenüberstellung ist vereinfacht. Moderne CPUs besitzen mehrere Kerne und GPUs werden längst nicht nur für Grafik verwendet. GPUs eignen sich besonders für viele ähnlich strukturierte parallele Berechnungen.

---

## Folie 28 – RAM und VRAM

**Random Access Memory (RAM):** Arbeitsspeicher des Computersystems.

**Video Random Access Memory (VRAM):** Grafikspeicher, der einer GPU für Grafikdaten und Berechnungen zur Verfügung steht.

Dort können beispielsweise liegen:
- Texturen
- Geometriedaten
- Bildpuffer
- Zwischenergebnisse

### LEHRERHINWEIS

Bei separaten Grafikkarten existiert häufig eigener VRAM. In integrierten Systemen können CPU und GPU gemeinsamen Arbeitsspeicher verwenden. Deshalb ist „VRAM ist immer ein eigener Speicherchip“ keine allgemeingültige Aussage.

---

## Folie 29 – Bildrate und Frame

**Frame:** einzelnes berechnetes bzw. dargestelltes Bild einer Bildfolge.

**Frames per Second (FPS):** Anzahl der Bilder pro Sekunde.

Beispiele:
- 30 FPS
- 60 FPS
- 120 FPS

Bei 60 FPS stehen im Mittel nur etwa **16,7 Millisekunden pro Bild** zur Verfügung.

### LEHRERHINWEIS

1000 ms / 60 ≈ 16,7 ms. Für eine stabile Echtzeitdarstellung müssen alle notwendigen Verarbeitungsschritte rechtzeitig abgeschlossen werden.

Hohe FPS allein garantieren kein gutes Nutzungserlebnis; Gleichmäßigkeit der Framezeiten und Eingabelatenz spielen ebenfalls eine Rolle.

---

## Folie 30 – Warum kann ein Spiel ruckeln?

Mögliche Ursachen:
- GPU benötigt zu lange für die Grafikberechnung
- CPU benötigt zu lange für Spiellogik oder Vorbereitung
- Daten müssen nachgeladen werden
- Speicher ist knapp
- Hintergrundprozesse benötigen Ressourcen
- Netzwerkprobleme bei Online-Inhalten

> **Eine starke GPU allein garantiert keine flüssige Darstellung.**

### LEHRERHINWEIS

**Bottleneck / Flaschenhals:** Der Teil eines Systems, der die Gesamtleistung in einer konkreten Situation begrenzt. Der Flaschenhals kann sich je nach Anwendung und Einstellung verändern.

---

## Folie 31 – Bildrate und Latenz sind verschieden

**Bildrate:** Wie viele Bilder werden pro Sekunde erzeugt bzw. angezeigt?

**Latenz:** Wie viel Zeit vergeht zwischen einem Ereignis und der wahrnehmbaren Reaktion?

Beispiel:
Tastendruck → Verarbeitung → neues Bild → Anzeige

> **Hohe Bildrate kann Latenz reduzieren, ist aber nicht dasselbe wie geringe Latenz.**

### LEHRERHINWEIS

**Latenz:** Zeitliche Verzögerung zwischen Ursache und Wirkung. Bei interaktiver Grafik können Eingabegerät, CPU, GPU, Bildpuffer, Display und bei Netzwerkdiensten zusätzlich das Netzwerk zur Gesamtlatenz beitragen.

---

## Folie 32 – Grafik-Streaming / Cloud-Gaming

Vereinfacht:

Spiel wird auf einem Server berechnet
↓
Bild wird als Video codiert
↓
Netzwerkübertragung
↓
Endgerät decodiert das Video
↓
Bild wird angezeigt

Eingaben müssen zurück zum Server übertragen werden.

### LEHRERHINWEIS

**Cloud-Gaming:** Das Spiel läuft wesentlich auf entfernten Serversystemen; das Endgerät empfängt typischerweise einen Videostream und sendet Nutzereingaben zurück.

**Codierung/Encoding:** Umwandlung des berechneten Bildstroms in ein für Speicherung oder Übertragung geeignetes komprimiertes Format.

**Decodierung/Decoding:** Rückumwandlung für die Darstellung.

---

## Folie 33 – Bandbreite und Latenz beim Streaming

**Bandbreite / verfügbare Datenrate:** Wie viele Daten können in einer bestimmten Zeit übertragen werden?

**Latenz:** Wie lange benötigt eine Information für den Weg und die Verarbeitung?

Zu wenig Datenrate:
→ stärkere Kompression, Qualitätsverlust oder Unterbrechung

Hohe Latenz:
→ Eingaben fühlen sich verzögert an

### LEHRERHINWEIS

Bandbreite und Latenz sind unterschiedliche Eigenschaften einer Verbindung. Eine Verbindung kann hohe Datenrate besitzen und trotzdem eine für interaktive Anwendungen störende Latenz aufweisen.

---

## Folie 34 – KI kann Bilder erzeugen und verändern

Künstliche Intelligenz (KI) kann beispielsweise:
- aus Textbeschreibungen Bilder erzeugen
- Bildbereiche ergänzen oder verändern
- Auflösung bzw. Details rekonstruieren oder ergänzen
- Objekte entfernen oder ersetzen
- Stilmerkmale verändern

### LEHRERHINWEIS

**Text-to-Image:** Generatives Verfahren, bei dem ein Modell aus einer Texteingabe ein Bild erzeugt.

**Generative Bildbearbeitung:** Ein Modell erzeugt neue Bildinhalte passend zu Eingabe und vorhandenem Kontext.

Bei KI-Upscaling können Details geschätzt bzw. neu erzeugt werden. Ein vergrößertes Bild enthält daher nicht automatisch authentische zusätzliche Originaldetails.

---

## Folie 35 – KI-Bild und 3D-Rendering sind nicht dasselbe

Klassische 3D-Grafik:
- explizite Szene
- Modelle
- Materialien
- Licht
- Kamera
- Rendering

Generative KI:
- erzeugt Bilddaten anhand gelernter statistischer Zusammenhänge und Eingaben

> **Beide erzeugen Computergrafik, aber auf grundsätzlich unterschiedlichen Wegen.**

### LEHRERHINWEIS

Die Gegenüberstellung ist bewusst vereinfacht. Moderne Produktionsprozesse können klassische Renderingverfahren und KI kombinieren.

Wichtig ist die Unterscheidung zwischen einer explizit modellierten 3D-Szene und einem generativ erzeugten 2D-Bild.

---

## Folie 36 – Kann ich einem Bild noch trauen?

Digitale Bilder können:
- klassisch bearbeitet
- zusammengesetzt
- aus dem Kontext gerissen
- vollständig KI-generiert
- teilweise generativ verändert werden.

Prüffragen:
- Woher stammt das Bild?
- Gibt es eine verlässliche Quelle?
- Passt der Kontext?
- Gibt es weitere unabhängige Belege?

### LEHRERHINWEIS

Nicht vermitteln, dass KI-generierte Bilder grundsätzlich durch Anschauen sicher erkannt werden können. Sichtbare Fehler können Hinweise sein, sind aber kein zuverlässiger allgemeiner Nachweis.

Urheberrecht, Persönlichkeitsrechte und Kennzeichnung wurden bereits in anderen Bereichen behandelt und können hier kurz wieder aufgegriffen werden.

---

## Folie 37 – Was bestimmt die Grafikqualität?

Nicht nur die Auflösung.

Zusammen wirken beispielsweise:
- Ausgangsmaterial
- Pixelzahl und Pixeldichte
- Farbdarstellung
- Kompression
- Display oder Druckverfahren
- Rechenleistung
- Bildrate
- Renderingverfahren
- Betrachtungsabstand

> **Grafikqualität ist das Ergebnis eines Gesamtsystems.**

### LEHRERHINWEIS

Die Folie dient der Sicherung: Schüler sollen einfache Aussagen wie „mehr Pixel = immer besser“ oder „stärkere GPU = immer flüssiger“ kritisch einordnen können.

---

## Folie 38 – Abschluss: Von Daten zum Bild

Raster oder Vektor
→ Auflösung und Farbe
→ Speicherung und Kompression
→ Verarbeitung bzw. Rendering
→ Ausgabe auf Display oder Papier
→ menschliche Wahrnehmung

> **Ein digitales Bild ist nicht nur das, was wir sehen – dahinter stehen Daten, Algorithmen, Hardware und ein Ausgabesystem.**

### LEHRERHINWEIS

Als Abschluss können die Schüler ein selbst gewähltes Bild, einen Screenshot, ein Spiel oder einen Ausdruck anhand der Begriffe aus dem Wahlbereich analysieren.