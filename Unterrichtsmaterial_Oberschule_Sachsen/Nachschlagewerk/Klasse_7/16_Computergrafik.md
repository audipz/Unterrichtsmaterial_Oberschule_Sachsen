# 16 Computergrafik

## Vom digitalen Bild zur Computergrafik

In **Kapitel 5** wurde erklärt, wie digitale Bilder grundsätzlich gespeichert werden: als Raster aus Pixeln oder als mathematisch beschriebene Vektorgrafik.

Dieses Kapitel betrachtet nun stärker das **Erstellen und Bearbeiten von Computergrafiken**. Dabei geht es darum, wie grafische Elemente aufgebaut, ausgewählt, verändert, angeordnet und für einen bestimmten Zweck ausgegeben werden.

Computergrafiken begegnen uns beispielsweise als:

- Fotos und Bildbearbeitungen,
- Logos,
- Icons und Piktogramme,
- Diagramme,
- Karten,
- technische Zeichnungen,
- Benutzeroberflächen,
- Illustrationen,
- Grafiken auf Webseiten.

## Rastergrafik und Vektorgrafik

Die beiden wichtigsten Grundformen sind **Rastergrafiken** und **Vektorgrafiken**.

![Vergleich von Rastergrafik und Vektorgrafik](grafiken/raster_vektor_vergleich.svg)

### Rastergrafik

Eine Rastergrafik besteht aus einzelnen **Pixeln**. Für jeden Bildpunkt wird eine Farbinformation gespeichert.

Rastergrafiken eignen sich besonders für Bilder mit sehr vielen feinen Farb- und Helligkeitsunterschieden, beispielsweise Fotos.

Typische Formate sind:

- JPEG,
- PNG,
- WebP.

### Vektorgrafik

Eine Vektorgrafik beschreibt grafische Objekte durch mathematische Angaben.

Ein Kreis kann beispielsweise durch folgende Eigenschaften beschrieben werden:

```text
Mittelpunkt
Radius
Füllfarbe
Konturfarbe
Konturstärke
```

Ein Programm berechnet daraus die sichtbare Darstellung.

Vektorgrafiken eignen sich besonders für:

- Logos,
- Symbole,
- Pläne,
- Diagramme,
- technische Zeichnungen,
- einfache Illustrationen.

Ein verbreitetes Vektorformat ist **SVG**.

## Warum sieht eine Rastergrafik beim Vergrößern anders aus?

Eine Rastergrafik besitzt eine bestimmte Anzahl vorhandener Pixel. Wird ein kleiner Ausschnitt stark vergrößert, werden diese Pixel sichtbar beziehungsweise müssen durch das Programm vergrößert oder Zwischenwerte berechnet werden.

Eine Vektorgrafik speichert dagegen die Beschreibung der Formen. Beim Vergrößern können Linien und Kurven für die neue Größe erneut berechnet werden.

> **Merke:** Rastergrafiken speichern Bildpunkte. Vektorgrafiken speichern die Beschreibung grafischer Formen.

## Vergleich

| Eigenschaft | Rastergrafik | Vektorgrafik |
|---|---|---|
| Grundaufbau | Pixel | geometrisch beschriebene Objekte |
| besonders geeignet | Fotos, gemalte Bilder | Logos, Icons, Diagramme, Pläne |
| starke Vergrößerung | Pixel beziehungsweise Unschärfe können sichtbar werden | Formen bleiben grundsätzlich sauber berechenbar |
| Bearbeitung | einzelne Pixel/Bildbereiche | einzelne Objekte und Pfade |
| typische Formate | JPEG, PNG, WebP | SVG |

## Grafische Objekte

In einem Vektorgrafikprogramm arbeitet man häufig mit einzelnen **Objekten**.

Beispiele:

- Rechteck,
- Ellipse,
- Linie,
- Stern,
- Text,
- freier Pfad.

Jedes Objekt kann eigene Eigenschaften besitzen:

```text
Position
Breite
Höhe
Füllfarbe
Kontur
Drehung
Transparenz
```

Das passt zur Modellidee aus **Kapitel 11**: Ein konkretes grafisches Objekt besitzt Attribute mit bestimmten Werten.

## Füllung und Kontur

Bei vielen Vektorobjekten unterscheidet man zwischen **Füllung** und **Kontur**.

Die **Füllung** ist der innere Bereich einer Form.

Die **Kontur** ist die Linie um die Form beziehungsweise entlang eines Pfades.

Bei der Kontur können beispielsweise eingestellt werden:

- Farbe,
- Stärke,
- Linienart,
- Enden und Ecken.

Ein Objekt kann auch nur eine Füllung, nur eine Kontur oder beides besitzen.

## Auswählen und verändern

Bevor ein grafisches Objekt verändert wird, muss es normalerweise ausgewählt werden.

Typische Veränderungen sind:

- verschieben,
- vergrößern,
- verkleinern,
- drehen,
- spiegeln,
- Farbe ändern,
- Kontur verändern.

Diese Änderungen werden häufig als **Transformationen** bezeichnet.

### Proportional skalieren

Wird ein Objekt in Breite und Höhe im gleichen Verhältnis verändert, bleibt seine Form erhalten.

Wird dagegen nur die Breite verändert, kann beispielsweise ein Kreis zur Ellipse oder ein Bild sichtbar verzerrt werden.

## Reihenfolge von Objekten

Vektorobjekte können sich überdecken. Deshalb spielt ihre Reihenfolge eine Rolle.

Ein Programm bietet dafür häufig Befehle wie:

```text
nach vorn
nach hinten
in den Vordergrund
in den Hintergrund
```

Liegt ein blaues Rechteck vor einem roten Kreis, kann es einen Teil des Kreises verdecken. Wird die Reihenfolge geändert, sieht das Ergebnis anders aus, obwohl Position und Größe beider Objekte gleich bleiben.

## Ebenen

Viele Grafikprogramme verwenden **Ebenen**. Man kann sie sich zunächst wie transparente Folien vorstellen, die übereinanderliegen.

Beispiel:

```text
Ebene 3: Beschriftung
Ebene 2: Symbole
Ebene 1: Hintergrund
```

Ebenen helfen dabei, komplexe Grafiken zu ordnen. Sie können je nach Programm beispielsweise:

- ein- und ausgeblendet,
- gesperrt,
- umbenannt,
- in ihrer Reihenfolge verändert werden.

Ebenen sind besonders nützlich, wenn eine Grafik aus vielen Elementen besteht.

## Gruppieren

Mehrere Objekte können häufig zu einer **Gruppe** zusammengefasst werden.

Beispiel: Ein selbst gezeichnetes Verkehrsschild besteht aus Kreis, Symbol und Text. Werden diese Elemente gruppiert, kann das gesamte Schild gemeinsam verschoben oder skaliert werden.

```text
mehrere Einzelobjekte → Gruppe → gemeinsam bearbeiten
```

Die einzelnen Bestandteile können je nach Programm später wieder getrennt werden.

## Ausrichten und Verteilen

Bei sauber gestalteten Grafiken sollen Objekte häufig exakt zueinander stehen.

Dafür besitzen Grafikprogramme Funktionen zum **Ausrichten**, beispielsweise:

- linksbündig,
- rechtsbündig,
- horizontal zentriert,
- vertikal zentriert.

Beim **Verteilen** werden Abstände zwischen mehreren Objekten gleichmäßig angeordnet.

Das ist genauer als das Verschieben „nach Augenmaß“.

## Hilfslinien und Raster

**Hilfslinien** und ein eingeblendetes **Raster** können beim genauen Positionieren helfen.

Manche Programme lassen Objekte an solchen Linien „einrasten“. Dies wird häufig **Snapping** beziehungsweise Einrasten genannt.

Damit lassen sich beispielsweise Kanten exakt aufeinander ausrichten.

## Pfade

Eine wichtige Grundidee von Vektorgrafiken ist der **Pfad**.

Ein Pfad besteht aus Punkten und den Verbindungen zwischen ihnen. Diese Punkte werden häufig **Knoten** oder **Ankerpunkte** genannt.

Ein Pfad kann:

- offen sein, beispielsweise eine Linie,
- geschlossen sein, beispielsweise die Umrandung einer Fläche.

Bei einem geschlossenen Pfad kann der eingeschlossene Bereich gefüllt werden.

## Knoten bearbeiten

Bei der Knotenbearbeitung wird nicht das gesamte Objekt auf einmal verändert. Stattdessen können einzelne Punkte eines Pfades verschoben oder angepasst werden.

So lässt sich beispielsweise die Form einer Kurve gezielt verändern.

Vereinfacht:

```text
Objekt auswählen → Pfad/Knoten bearbeiten → einzelne Formpunkte verändern
```

## Bézierkurven

Vektorgrafikprogramme verwenden häufig **Bézierkurven**. Sie ermöglichen glatte Kurven, deren Verlauf durch Punkte und zusätzliche Richtungsinformationen gesteuert wird.

Für Klasse 7 reicht die Grundidee:

- Knoten legen wichtige Punkte fest,
- Griffe beziehungsweise Kontrollrichtungen beeinflussen den Kurvenverlauf,
- wenige gut gesetzte Punkte können eine glatte Form beschreiben.

Bézierkurven begegnen unter anderem bei Logos, Schriftkonturen und Illustrationen.

## Text in Grafiken

Text kann in einer Grafik als eigenes Objekt vorkommen. Solange er als Text erhalten bleibt, kann er normalerweise weiterhin bearbeitet werden.

Bei der Weitergabe einer Grafik kann jedoch eine verwendete Schriftart auf einem anderen Rechner fehlen.

Manche Programme können Text deshalb in **Pfade** umwandeln. Dann bleibt die sichtbare Form der Buchstaben erhalten, der Text lässt sich aber nicht mehr wie normaler Text bearbeiten.

> **Merke:** Text in Pfade umzuwandeln kann die Darstellung sichern, nimmt aber die normale Textbearbeitbarkeit.

## Rasterbilder in Vektorgrafiken

Eine SVG-Datei kann neben Vektorobjekten je nach Aufbau auch ein eingebettetes oder verknüpftes Rasterbild enthalten.

Dadurch wird ein Foto jedoch nicht automatisch zur Vektorgrafik. Es bleibt ein Rasterbild innerhalb eines Vektordokuments.

## Vektorisieren

Beim **Vektorisieren** versucht ein Programm, Formen und Kanten eines Rasterbildes als Vektorpfade nachzubilden.

Das kann beispielsweise bei einem einfachen einfarbigen Logo gut funktionieren. Bei einem komplexen Foto entstehen dagegen sehr viele Formen oder ein ungeeignetes Ergebnis.

```text
Rasterbild → Analyse → angenäherte Vektorpfade
```

Vektorisieren bedeutet also nicht, dass die ursprünglichen Bildinformationen perfekt in „unendlich genaue“ Formen verwandelt werden.

## Rastern

Der umgekehrte Vorgang heißt **Rastern** beziehungsweise Rasterisieren. Dabei wird eine Vektorgrafik für eine bestimmte Pixelgröße in ein Rasterbild umgerechnet.

Beispiel:

```text
SVG-Logo → Export als 800 × 800 Pixel PNG
```

Die ursprüngliche SVG kann weiterhin beliebig neu berechnet werden. Die exportierte PNG-Datei besitzt dagegen die gewählte Pixelauflösung.

## Zuschneiden und Maskieren

Beim **Zuschneiden** wird nur ein gewünschter Bildbereich beibehalten beziehungsweise ausgegeben.

In Vektorgrafiken können außerdem **Masken** oder **Clipping-Pfade** verwendet werden. Dabei bestimmt eine Form, welcher Teil eines anderen Objekts sichtbar ist.

Das Originalobjekt kann dabei je nach Verfahren erhalten bleiben und nur teilweise sichtbar gemacht werden.

## Transparenz

Grafische Objekte können vollständig oder teilweise transparent sein.

Bei teilweiser Transparenz scheint der Hintergrund durch das Objekt hindurch.

PNG und SVG können Transparenz speichern. Das klassische JPEG-Format besitzt dagegen keinen normalen Alpha-Kanal für transparente Bildbereiche.

## Farben

Für Bildschirme ist das **RGB-Farbmodell** besonders wichtig. Beim Druck begegnet häufig **CMYK**.

### RGB

```text
Rot + Grün + Blau
```

RGB beschreibt die Mischung von Licht und wird deshalb als additives Farbmodell bezeichnet.

### CMYK

```text
Cyan + Magenta + Yellow + Key/Black
```

CMYK wird insbesondere im Druck verwendet. Druckfarbe und leuchtende Bildschirmpixel funktionieren physikalisch unterschiedlich. Deshalb kann dieselbe Grafik auf einem Bildschirm und auf Papier unterschiedlich wirken.

Für Klasse 7 ist vor allem wichtig: **Der Verwendungszweck beeinflusst die Wahl des Farbmodells und des Ausgabeformats.**

## Exportieren und Speichern unterscheiden

Beim Arbeiten mit Grafiken sollte zwischen einer **bearbeitbaren Arbeitsdatei** und einer **Ausgabedatei** unterschieden werden.

Beispiel:

```text
Arbeitsdatei: SVG mit getrennten Objekten
Ausgabe:      PNG für eine bestimmte Webseite
```

Wird nur die PNG-Datei aufgehoben, sind die ursprünglichen Vektorobjekte dort nicht mehr getrennt bearbeitbar.

> **Merke:** Die bearbeitbare Quelldatei sollte aufbewahrt werden. Exportierte Dateien sind häufig für einen bestimmten Verwendungszweck optimiert.

## Das richtige Format wählen

| Aufgabe | häufig geeignet | Warum? |
|---|---|---|
| Foto | JPEG/WebP | gute Kompression für fotografische Inhalte |
| Screenshot mit Schrift | PNG | klare Kanten, verlustfreie Speicherung |
| Logo | SVG | skalierbare Formen |
| Icon | SVG | in verschiedenen Größen nutzbar |
| Grafik mit Transparenz | PNG oder SVG | Transparenz möglich |
| Grafik für Weiterbearbeitung | ursprüngliches Projekt-/Vektorformat | Objekte bleiben bearbeitbar |

Die konkrete Wahl hängt immer vom Inhalt, Programm und Verwendungszweck ab.

## Gestaltung: Technik allein reicht nicht

Eine technisch korrekte Grafik ist nicht automatisch verständlich.

Bei einer Informationsgrafik sind beispielsweise wichtig:

- klare Aussage,
- gut lesbare Schrift,
- ausreichender Kontrast,
- sinnvolle Anordnung,
- nicht zu viele unterschiedliche Farben,
- einheitliche Formen und Abstände,
- verständliche Beschriftungen.

Farben sollten möglichst nicht die einzige Möglichkeit sein, wichtige Unterschiede zu erkennen. Symbole, Muster oder Beschriftungen können zusätzliche Hinweise geben.

## Ein konkretes Beispiel: ein einfaches Logo

Ein Logo soll aus einem blauen Kreis, einem weißen Buchstaben `i` und einem kurzen Schriftzug bestehen.

Als Vektorgrafik könnten dafür drei Objekte verwendet werden:

```text
Objekt 1: Kreis
- Füllung: blau
- Kontur: keine

Objekt 2: Text "i"
- Füllung: weiß

Objekt 3: Schriftzug
- Füllung: dunkel
```

Die Objekte können:

- ausgerichtet,
- gruppiert,
- gemeinsam skaliert,
- als SVG gespeichert,
- für andere Zwecke als PNG exportiert werden.

Dieses Beispiel zeigt den Vorteil objektorientierten Arbeitens in einer Vektorgrafik: Die Bestandteile bleiben getrennt bearbeitbar.

## Begriffe zum Nachschlagen

**Ausrichten:** mehrere Objekte exakt an gemeinsamen Kanten oder Mittelpunkten anordnen.

**Bézierkurve:** mathematisch beschriebene Kurve, deren Verlauf durch Punkte und Kontrollinformationen beeinflusst wird.

**Ebene:** getrennt organisierbare Schicht innerhalb einer Grafik.

**Füllung:** innerer sichtbarer Bereich einer geschlossenen Form.

**Gruppe:** Zusammenfassung mehrerer Objekte zur gemeinsamen Bearbeitung.

**Knoten/Ankerpunkt:** Punkt, der den Verlauf eines Vektorpfades mitbestimmt.

**Kontur:** Linie entlang der Begrenzung einer Form beziehungsweise eines Pfades.

**Pfad:** aus Punkten und Linien beziehungsweise Kurven aufgebautes Vektorobjekt.

**Rastergrafik:** aus einzelnen Pixeln aufgebautes Bild.

**Rastern/Rasterisieren:** Umwandlung einer Vektordarstellung in ein Pixelraster mit festgelegter Auflösung.

**Skalieren:** Größe eines Objekts oder einer Grafik verändern.

**SVG:** Scalable Vector Graphics; verbreitetes Vektorgrafikformat.

**Transformation:** geometrische Veränderung wie Verschieben, Skalieren, Drehen oder Spiegeln.

**Transparenz:** vollständige oder teilweise Durchsichtigkeit eines Bildbereiches oder Objekts.

**Vektorgrafik:** aus mathematisch beschriebenen Formen und Pfaden aufgebaute Grafik.

**Vektorisieren:** Nachbilden eines Rasterbildes durch Vektorformen beziehungsweise Pfade.

→ Siehe auch **Kapitel 5: Bilder im Computer**, **Kapitel 10: Darstellung von Informationen** und **Kapitel 11: Objekte, Attribute, Klassen und Methoden**.