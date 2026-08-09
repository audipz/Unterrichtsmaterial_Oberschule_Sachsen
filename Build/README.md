# Build-System

Die Markdown-Dateien im Ordner

`Unterrichtsmaterial_Oberschule_Sachsen/`

sind die maßgebliche Quelle.

Die Build-Skripte erzeugen daraus Dokumente im Ordner `Ausgabe/`.

## Voraussetzungen

- Pandoc
- PowerShell 7+ für `build.ps1` oder Bash für `build.sh`
- Optional eine LaTeX-Engine für PDF-Ausgaben

## Schnellstart

### Windows / PowerShell

```powershell
.\Build\build.ps1
```

Nur Klasse 9:

```powershell
.\Build\build.ps1 -Bereich Klasse_9
```

Nur ein Kapitel:

```powershell
.\Build\build.ps1 -Bereich "Klasse_9/02_Komplexaufgabe_zur_Algorithmierung"
```

### Linux / macOS

```bash
./Build/build.sh
```

Nur Klasse 9:

```bash
./Build/build.sh Klasse_9
```

## Ausgabe

Die Struktur unter `Ausgabe/` spiegelt die Quellenstruktur.

Beispiel:

```text
Ausgabe/
└── Klasse_9/
    └── 02_Komplexaufgabe_zur_Algorithmierung/
        ├── 01_Arbeitsheft.docx
        ├── 01_Arbeitsheft.html
        ├── 02_Lehrerband.docx
        └── 02_Lehrerband.html
```

## Build-Prinzip

Ein Ordner wird als Dokument gebaut, wenn er Markdown-Dateien enthält.

Die Dateien werden alphabetisch sortiert zusammengeführt.

Dadurch steuert die numerische Dateibenennung die Reihenfolge.

## PDF

PDF wird nur gebaut, wenn eine unterstützte LaTeX-Engine vorhanden ist.

Der Build schlägt nicht fehl, wenn keine PDF-Engine installiert ist.

## GitHub Actions

Bei jedem Push auf `main` wird der Build automatisch ausgeführt.

Die erzeugten Dateien werden nach `Ausgabe/` geschrieben und automatisch zurück in `main` committed.

Der automatische Commit enthält `[skip ci]`, damit keine Endlosschleife entsteht.
