# Datenbank anlegen

## CLI

```bash
sqlite3 schule.db
```

Danach kann direkt SQL eingegeben werden.

```sql
CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung TEXT NOT NULL UNIQUE
);
```

## Speichern

SQLite schreibt Änderungen grundsätzlich in die Datenbankdatei.

Die Datei kann danach mit anderen SQLite-Werkzeugen geöffnet werden.

## Dateiendungen

Übliche Endungen sind:

```text
.db
.sqlite
.sqlite3
```

Die Endung ändert nicht das Datenbankformat.
