# SQL-Dateien ausführen

SQL-Befehle können in Dateien gespeichert werden.

Beispiel:

```text
schema.sql
```

## CLI

```bash
sqlite3 schule.db < schema.sql
```

Oder innerhalb der SQLite-CLI:

```text
.read schema.sql
```

## Vorteil

Damit können Datenbanken reproduzierbar aufgebaut werden.

Ein SQL-Skript kann enthalten:

- `CREATE TABLE`
- `CREATE INDEX`
- `INSERT`
- Beispieldaten

## Empfehlung

Datenbankschema und Beispieldaten nach Möglichkeit als SQL-Dateien versionieren.
