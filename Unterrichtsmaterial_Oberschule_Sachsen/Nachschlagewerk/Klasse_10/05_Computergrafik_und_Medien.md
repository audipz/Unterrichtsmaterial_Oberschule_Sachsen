# 5 Computergrafik und Medien

## Von einfachen Bildern zu digitalen Medienwelten

Computergrafik umfasst die digitale Darstellung von Bildern, Animationen, 3D-Szenen und Videos. In Klasse 7 wurden Pixel, Auflösung und Farbtiefe eingeführt. In Klasse 10 wird besonders wichtig, wie Grafikqualität, Datenmenge, Rechenleistung, Kompression und Übertragung zusammenhängen.

Digitale Medien wirken selbstverständlich, benötigen aber viele informatische Entscheidungen: Wie fein ist die Auflösung? Wie viele Farben werden gespeichert? Wie oft wird ein Bild pro Sekunde aktualisiert? Wie stark wird komprimiert?

## Auflösung, Farbtiefe und Rohdatenmenge

Die **Auflösung** beschreibt, aus wie vielen Bildpunkten ein Bild besteht. Die **Farbtiefe** beschreibt, wie viele Bits pro Pixel zur Farbdarstellung verwendet werden.

Beispiel für ein unkomprimiertes Bild:

```text
Breite × Höhe × Farbtiefe
1920 × 1080 × 24 Bit
= 49 766 400 Bit
≈ 6,2 MB
```

Das ist nur ein einzelnes Bild. Bei Videos entstehen viel größere Rohdatenmengen.

| Medium | Einfluss auf Datenmenge |
|---|---|
| Foto | Auflösung, Farbtiefe, Kompression |
| Video | Auflösung, Farbtiefe, Bilder pro Sekunde, Ton |
| 3D-Szene | Modelle, Texturen, Licht, Animation |
| Stream | zusätzlich Bandbreite und Anpassung |

## Bildrate und Bewegung

Die **Bildrate** gibt an, wie viele Einzelbilder pro Sekunde angezeigt werden. Sie wird oft in fps angegeben. Je höher die Bildrate, desto flüssiger wirkt Bewegung, aber desto mehr Daten müssen verarbeitet oder erzeugt werden.

| Bildrate | Wirkung |
|---:|---|
| 12 fps | ruckelig, eher Animationseindruck |
| 24 fps | typisch filmische Bewegung |
| 30 fps | häufig für Video |
| 60 fps | sehr flüssig, mehr Daten und Rechenaufwand |

Bewegung in digitalen Medien ist also eine schnelle Folge von Einzelbildern. Das Grundprinzip ist einfach, die technische Umsetzung kann sehr anspruchsvoll sein.

## Rechenleistung und Speicher

Grafische Anwendungen benötigen Rechenleistung, Arbeitsspeicher und Speicherplatz. Besonders aufwendig sind hohe Auflösungen, Echtzeitdarstellung, 3D-Modelle, Schatten, Spiegelungen, Animationen und Videobearbeitung.

Eine Grafikkarte oder Grafikeinheit kann viele ähnliche Berechnungen parallel ausführen. Das ist hilfreich, weil für sehr viele Pixel ähnliche Arbeitsschritte nötig sind.

> **Merke:** Gute Grafikqualität entsteht nicht nur durch schöne Gestaltung, sondern auch durch viele Daten und viele Berechnungen.

## Kompression

**Kompression** verringert die Datenmenge. Man unterscheidet verlustfreie und verlustbehaftete Kompression.

| Art | Bedeutung | Beispiel |
|---|---|---|
| verlustfrei | Original kann wiederhergestellt werden | einfache Grafiken, Text, Archiv |
| verlustbehaftet | unwichtige Details werden entfernt | Foto, Video, Streaming |

Bei Fotos oder Videos kann verlustbehaftete Kompression sinnvoll sein, weil Menschen nicht jede Veränderung gleich stark wahrnehmen. Bei Text oder Programmen wäre Datenverlust dagegen problematisch.

## Streaming

Beim **Streaming** werden Medien abgespielt, während Daten nachgeladen werden. Dabei müssen Datenrate, Netzwerkqualität und Geräteleistung zusammenpassen. Moderne Dienste passen Qualität häufig automatisch an.

Wenn die Verbindung schwächer wird, kann ein Stream zum Beispiel:

- niedrigere Auflösung verwenden,
- stärker komprimieren,
- Puffer aufbauen,
- kurz unterbrechen.

Streaming zeigt den Zusammenhang zwischen Informatik und Alltag besonders deutlich: Sichtbare Qualität hängt von unsichtbaren Daten- und Netzwerkprozessen ab.

## Manipulation und Authentizität

Digitale Bilder und Videos lassen sich leicht verändern. Das kann kreativ und nützlich sein, zum Beispiel bei Bildbearbeitung, Filmproduktion oder Barrierefreiheit. Es kann aber auch täuschen.

Wichtige Fragen zur Einschätzung:

| Frage | Bedeutung |
|---|---|
| Woher stammt das Medium? | Quelle prüfen |
| Gibt es Metadaten? | Hinweise auf Gerät, Zeit, Bearbeitung |
| Passt der Kontext? | Ort, Datum, Ereignis |
| Gibt es andere Quellen? | Vergleich hilft |
| Wirkt etwas unplausibel? | Schatten, Perspektive, Ränder, Hände |

> **Merke:** Digitale Medien sind Daten. Daten können kopiert, komprimiert, verändert und neu zusammengesetzt werden.

## Begriffe zum Nachschlagen

**Auflösung:** Anzahl der Bildpunkte in Breite und Höhe.

**Farbtiefe:** Anzahl der Bits zur Darstellung eines Pixels.

**Bildrate:** Anzahl der Einzelbilder pro Sekunde.

**Rohdatenmenge:** Datenmenge ohne Kompression.

**Kompression:** Verringerung der Datenmenge.

**Streaming:** Wiedergabe während der fortlaufenden Übertragung.

**Authentizität:** Echtheit beziehungsweise Vertrauenswürdigkeit eines Mediums.

→ Vorwissen: Klasse 7, **Bilder im Computer**; Klasse 9, **Computergrafik, Manipulation und Authentizität**.
