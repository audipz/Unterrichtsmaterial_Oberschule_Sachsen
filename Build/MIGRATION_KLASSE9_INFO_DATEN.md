# Strukturmigration Klasse 9 – Informationen und Daten

Der Lernbereich `01_Informationen_und_Daten` verwendet im aktuellen Repository noch die ältere Werkteilnummerierung.

## Zielstruktur

```text
01_Arbeitsheft
02_Lehrerband
03_Material
04_Loesungen
05_Praesentationen
06_Dateien
07_Quellen
08_Bilder
09_Lernkontrollen
```

## Dry-Run

```bash
./Build/migrate_klasse9_info_daten.sh --dry-run
```

## Ausführen

```bash
./Build/migrate_klasse9_info_daten.sh
```

Danach:

```bash
git status
git add -A
git commit -m "refactor(klasse9): Werkteilstruktur Informationen und Daten vereinheitlicht"
git push origin main
```
