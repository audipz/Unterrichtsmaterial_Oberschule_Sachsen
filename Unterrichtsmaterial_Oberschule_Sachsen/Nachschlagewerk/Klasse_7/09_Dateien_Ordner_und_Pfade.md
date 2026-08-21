# 9 Dateien, Ordner und Pfade

## Dateien speichern Daten

Eine **Datei** ist eine zusammengehörige Menge gespeicherter Daten. Sie kann beispielsweise Text, ein Bild, Musik, ein Video oder Programmdaten enthalten.

Dateien besitzen einen Namen. Häufig endet der Name mit einer **Dateiendung** wie `.txt`, `.pdf`, `.png` oder `.mp3`. Die Endung gibt einen Hinweis auf das verwendete Dateiformat.

> **Wichtig:** Eine Dateiendung umzubenennen wandelt den Inhalt nicht automatisch in ein anderes Dateiformat um.

## Ordner schaffen Struktur

**Ordner** beziehungsweise **Verzeichnisse** helfen, Dateien und weitere Ordner zu strukturieren. Dadurch entsteht eine Baumstruktur.

![Beispiel eines Verzeichnisbaums mit Ordnern und Dateien](grafiken/verzeichnisbaum.svg)

```text
Schule/
├── Informatik/
│   ├── Bilder/
│   │   └── eva.png
│   └── Notizen.txt
└── Deutsch/
    └── Aufsatz.odt
```

## Pfade

Ein **Pfad** beschreibt den Weg zu einer Datei oder einem Verzeichnis.

Ein absoluter Pfad beginnt an einem festgelegten Ausgangspunkt des Dateisystems. Ein relativer Pfad beschreibt den Weg ausgehend vom aktuellen Verzeichnis.

Beispiel für einen relativen Pfad:

```text
Bilder/eva.png
```

Welches Trennzeichen verwendet wird, hängt vom Betriebssystem und vom jeweiligen Kontext ab. Unter Linux und macOS ist `/` üblich; Windows verwendet in vielen Darstellungen `\`.

## Sinnvolle Dateinamen

Gute Dateinamen helfen beim Wiederfinden. Sie sollten den Inhalt verständlich beschreiben und möglichst nach einem einheitlichen Schema aufgebaut sein.

Beispiel:

```text
2026-09-03_Referat_Binaersystem.odt
```

Ungünstig sind Namen wie `neu2_final_finalwirklich.docx`, weil ihre Bedeutung später kaum noch nachvollziehbar ist.

## Kopieren, Verschieben und Löschen

Beim **Kopieren** entsteht eine zusätzliche Datei. Beim **Verschieben** ändert sich ihr Speicherort. Beim Löschen wird eine Datei je nach System zunächst in einen Papierkorb verschoben oder unmittelbar entfernt.

Vor wichtigen Änderungen sind Sicherungskopien sinnvoll.

## Dateiformat und Programm

Ein Dateiformat beschreibt, wie Daten innerhalb einer Datei organisiert sind. Programme müssen das Format verstehen, um die Datei korrekt zu öffnen oder zu bearbeiten. Manche Formate sind offen dokumentiert, andere hängen stärker von bestimmten Programmen ab.

> **Merke:** Eine gute Ordnerstruktur und verständliche Dateinamen sparen Zeit und verhindern Verwechslungen.

## Begriffe zum Nachschlagen

**Datei:** zusammengehörige gespeicherte Datenmenge.

**Dateiendung:** Teil des Dateinamens, der häufig auf das Format hinweist.

**Dateiformat:** festgelegte Struktur zur Speicherung bestimmter Daten.

**Ordner/Verzeichnis:** Struktur zum Gruppieren von Dateien und weiteren Verzeichnissen.

**Pfad:** Beschreibung des Speicherorts einer Datei oder eines Verzeichnisses.

→ Siehe auch **Kapitel 8: Speicher und Datenmengen** und **Kapitel 13: Werkzeuge**.
