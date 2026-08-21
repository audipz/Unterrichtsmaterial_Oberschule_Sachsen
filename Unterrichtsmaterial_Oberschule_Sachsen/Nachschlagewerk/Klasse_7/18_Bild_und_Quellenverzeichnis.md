# 18 Bild- und Quellenverzeichnis

## Umgang mit Quellen

Dieses Nachschlagewerk wird aus eigenen Unterrichtsmaterialien sowie fachlich geprüften Grundlagen aufgebaut. Für fremde Texte, Daten, Bilder oder Grafiken werden Quellen und Nutzungsrechte nachvollziehbar dokumentiert.

Quellenangaben erfüllen mehrere Aufgaben:

- Herkunft einer Information nachvollziehbar machen,
- Urheber nennen,
- Nutzungsrechte dokumentieren,
- eine spätere fachliche Überprüfung ermöglichen.

> **Grundsatz:** Keine fremde Abbildung ohne geklärte Herkunft und Nutzungsrecht.

## Eigene Grafiken

Die folgenden Erklärgrafiken wurden speziell für dieses Nachschlagewerk als SVG erstellt und liegen im Unterordner `grafiken/`:

| Datei | Darstellung |
|---|---|
| `einfaches_informatiksystem.svg` | vereinfachtes Modell mit Eingabe, Verarbeitung, Ausgabe und Speicherung |
| `binaer_stellenwert.svg` | Binär-Stellenwerttafel am Beispiel `00101101₂ = 45₁₀` |
| `zeichen_code_bits.svg` | Weg vom Zeichen über den Codewert zur Bitfolge |
| `pixel_aufloesung.svg` | Vergleich einer gröberen und feineren Pixelauflösung auf gleicher Fläche |
| `audio_digitalisierung.svg` | schematische Abtastung eines analogen Audiosignals |
| `evas_prinzip.svg` | Eingabe, Verarbeitung, Ausgabe und Speicherung |
| `verzeichnisbaum.svg` | Datei- und Ordnerstruktur als farbiger Verzeichnisbaum |
| `objekt_klasse_attribute_methoden.svg` | Zusammenhang von Klasse, Objekt, Attributen und Methoden |
| `raster_vektor_vergleich.svg` | anschaulicher Vergleich von Raster- und Vektorgrafik |

Alle genannten Grafiken sind **eigene Darstellungen dieses Repositories**. Für sie ist keine externe Bildquelle erforderlich.

Die SVG-Dateien werden bevorzugt, weil sich didaktische Formen, Pfeile und Beschriftungen damit sauber skalieren lassen und die Grafiken in verschiedenen Ausgabeformaten möglichst stabil bleiben.

## Externe Abbildungen

Externe Bilder sollen nur verwendet werden, wenn sie gegenüber einer eigenen didaktischen Grafik einen deutlichen Mehrwert besitzen und Herkunft sowie Nutzungsrecht eindeutig nachvollziehbar sind.

Bevorzugt werden:

- gemeinfreie beziehungsweise Public-Domain-Inhalte,
- CC0,
- CC BY,
- CC BY-SA,
- andere ausdrücklich geeignete freie Lizenzen.

Für jede externe Abbildung sollen mindestens folgende Angaben dokumentiert werden:

```text
Titel / Beschreibung
Urheber
Quelle / Originalseite
Lizenz
Abrufdatum
```

Falls erforderlich kommen weitere Angaben hinzu, beispielsweise Änderungen an der Originaldatei oder die genaue Lizenzversion.

Die direkte Bilddatei und die Webseite, auf der Lizenz und Urheber dokumentiert sind, müssen voneinander unterschieden werden.

## Warum die Originalquelle wichtig ist

Eine Bilddatei kann auf vielen Webseiten erneut veröffentlicht worden sein. Eine beliebige Fundstelle ist deshalb nicht automatisch die beste Quelle für Urheber- und Lizenzangaben.

Wenn möglich, sollte die ursprüngliche beziehungsweise maßgebliche Veröffentlichungsseite verwendet werden, auf der Herkunft und Lizenz nachvollziehbar dokumentiert sind.

## Eigene Screenshots

Screenshots einer Softwareoberfläche sind nicht automatisch mit vollständig selbst erstellten SVG-Erklärgrafiken gleichzusetzen. Darin können beispielsweise geschützte Benutzeroberflächen, Logos, Inhalte oder personenbezogene Daten sichtbar sein.

Vor der Verwendung eines Screenshots sollte deshalb geprüft werden:

- Ist der Screenshot für die Erklärung wirklich notwendig?
- Sind persönliche oder vertrauliche Daten sichtbar?
- Welche Nutzungsbedingungen beziehungsweise Rechte sind zu beachten?
- Muss die verwendete Software beziehungsweise Quelle genannt werden?

## Historische Abbildungen

Historische Fotografien können einen Mehrwert bieten, wenn ein konkretes Gerät oder eine Entwicklung gezeigt werden soll. Auch bei alten Bildern ist jedoch nicht automatisch davon auszugehen, dass sie frei verwendet werden dürfen.

Für Kapitel 1 kann beispielsweise eine frei nutzbare historische Abbildung eines frühen Computers beziehungsweise einer geeigneten Rekonstruktion verwendet werden. Eine solche Datei soll erst eingebunden werden, nachdem Originalquelle, Urheberstatus und Lizenz geprüft und hier dokumentiert wurden.

## Quellen für fachliche Informationen

Nicht jede allgemein erklärte Grundlage benötigt eine lange Einzelnachweisliste. Bei konkreten Zahlen, Standards, historischen Angaben, Definitionen oder zeitabhängigen technischen Informationen sollte jedoch eine geeignete fachliche Quelle nachvollziehbar sein.

Geeignet können je nach Thema beispielsweise sein:

- offizielle Standardisierungsorganisationen,
- Herstellerdokumentationen für konkrete technische Eigenschaften,
- Behörden und öffentliche Einrichtungen,
- Hochschulen und wissenschaftliche Einrichtungen,
- anerkannte Fachliteratur.

Eine Suchmaschine ist dabei ein Werkzeug zum Auffinden von Quellen, aber normalerweise **nicht selbst die fachliche Quelle**.

## Onlinequellen

Bei Onlinequellen können sich Inhalte ändern. Deshalb ist ein **Abrufdatum** sinnvoll.

Eine Quellenangabe sollte möglichst auf die konkrete verwendete Seite verweisen und nicht nur auf die Startseite einer großen Website.

Beispielstruktur:

```text
Organisation/Autor: Titel der Seite.
Originaladresse: ...
Abrufdatum: TT.MM.JJJJ
```

## Build-Prinzip für externe Abbildungen

Externe Abbildungen sollen beim Build nicht unkontrolliert von beliebigen Internetadressen eingebunden werden. Vorgesehen ist ein kontrollierter Download beziehungsweise lokaler Bestand mit nachvollziehbarer Herkunft.

Dadurch sollen:

- PDF, DOCX und HTML reproduzierbar bleiben,
- Ausfälle externer Webseiten den Build nicht unnötig zerstören,
- verwendete Bildstände nachvollziehbar bleiben,
- Lizenzinformationen zusammen mit der Grafik dokumentiert werden können.

## Aktueller Stand Klasse 7

Die derzeit im eigentlichen Nachschlagewerk verwendeten didaktischen Erklärgrafiken sind repositoryeigene SVG-Grafiken. Externe historische oder fotografische Abbildungen werden erst dann in diese Liste aufgenommen, wenn sie tatsächlich eingebunden und ihre Rechteangaben vollständig dokumentiert wurden.

Damit unterscheidet das Verzeichnis bewusst zwischen:

```text
bereits verwendeten Quellen/Grafiken
und
nur geplanten möglichen Ergänzungen
```

> **Merke:** Eine Quellenangabe soll nicht nur zeigen, wo etwas gefunden wurde, sondern nachvollziehbar machen, woher es stammt und unter welchen Bedingungen es verwendet werden darf.
