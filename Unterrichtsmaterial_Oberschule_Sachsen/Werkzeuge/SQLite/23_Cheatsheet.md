# SQLite Cheatsheet

## Start

```bash
sqlite3 datenbank.db
```

## Hilfe

```text
.help
```

## Tabellen

```text
.tables
```

## Schema

```text
.schema
```

## SQL-Datei

```text
.read datei.sql
```

## Format

```text
.headers on
.mode column
```

## Fremdschlüssel

```sql
PRAGMA foreign_keys = ON;
```

## Backup

```text
.backup sicherung.db
```

## Import CSV

```text
.mode csv
.import daten.csv Tabelle
```

## Beenden

```text
.quit
```
