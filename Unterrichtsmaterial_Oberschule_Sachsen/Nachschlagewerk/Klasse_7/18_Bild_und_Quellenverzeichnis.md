# 18 Bild- und Quellenverzeichnis

## Umgang mit Quellen

Dieses Nachschlagewerk wird aus eigenen Unterrichtsmaterialien sowie fachlich geprüften Grundlagen aufgebaut. Für fremde Texte, Daten, Bilder oder Grafiken werden Quellen und Nutzungsrechte nachvollziehbar dokumentiert.

## Eigene Grafiken

Die folgenden Erklärgrafiken wurden speziell für dieses Nachschlagewerk als SVG erstellt und liegen im Unterordner `grafiken/`:

| Datei | Darstellung |
|---|---|
| `binaer_stellenwert.svg` | Binär-Stellenwerttafel am Beispiel `00101101₂ = 45₁₀` |
| `zeichen_code_bits.svg` | Weg vom Zeichen über den Codewert zur Bitfolge |
| `pixel_aufloesung.svg` | Vergleich unterschiedlicher Pixelauflösungen auf gleicher Fläche |
| `audio_digitalisierung.svg` | schematische Abtastung eines analogen Audiosignals |
| `evas_prinzip.svg` | Eingabe, Verarbeitung, Ausgabe und Speicherung |
| `verzeichnisbaum.svg` | Datei- und Ordnerstruktur als Baum |
| `objekt_klasse_attribute_methoden.svg` | Zusammenhang von Klasse, Objekt, Attributen und Methoden |

Alle genannten Grafiken sind **eigene Darstellungen dieses Repositories**. Für sie ist keine externe Bildquelle erforderlich.

Weitere eigene Grafiken können ergänzt werden, wenn sie gegenüber Text und Tabellen einen erkennbaren didaktischen Mehrwert bieten.

## Externe Abbildungen

Externe Bilder werden nur verwendet, wenn Herkunft und Nutzungsrecht eindeutig nachvollziehbar sind. Bevorzugt werden gemeinfreie Inhalte sowie geeignete freie Lizenzen wie CC0, CC BY oder CC BY-SA.

Für jede externe Abbildung sollen mindestens folgende Angaben hinterlegt werden:

```text
Titel / Beschreibung
Urheber
Quelle / Originalseite
Lizenz
Abrufdatum
```

Die direkte Bilddatei und die Seite, auf der Lizenz und Urheber dokumentiert sind, müssen voneinander unterschieden werden.

## Geplante historische Abbildung

Für Kapitel 1 ist eine frei nutzbare historische Abbildung eines frühen Computers, beispielsweise der Z3 beziehungsweise einer Rekonstruktion, vorgesehen. Die konkrete Datei wird erst eingebunden, nachdem Originalquelle, Urheberstatus und Lizenz geprüft wurden.

## Build-Prinzip

Externe Abbildungen sollen beim Build nicht unkontrolliert von beliebigen Internetadressen eingebunden werden. Vorgesehen ist ein kontrollierter Download mit lokalem Cache. Dadurch bleiben PDF und DOCX reproduzierbar und die Lizenzinformationen können zusammen mit der Grafik verarbeitet werden.

> **Grundsatz:** Keine Abbildung ohne geklärte Herkunft und Nutzungsrecht.
