# QS – fehlende H1 in Klasse 9 ergänzen

Das Skript ändert ausschließlich Markdown-Dateien unter `Klasse_9/`, die aktuell keine H1-Überschrift besitzen.

Es fügt am Dateianfang eine aus dem Dateinamen bzw. Ordnernamen abgeleitete H1 ein und verändert den übrigen Inhalt nicht.

## Dry-Run

```bash
./Build/fix_klasse9_h1.sh --dry-run
```

## Ausführen

```bash
./Build/fix_klasse9_h1.sh
```

## Danach prüfen

```bash
./Build/validate.sh --strict
git diff --stat
git diff
```

Das Skript ist als einmaliges QS-Werkzeug gedacht und muss nicht eingecheckt werden.
