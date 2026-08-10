# SQLite CLI

Die SQLite-Kommandozeile wird mit `sqlite3` gestartet.

## Neue Datenbank öffnen oder anlegen

```bash
sqlite3 schulverwaltung.db
```

Existiert die Datei noch nicht, wird sie beim ersten Speichern angelegt.

## Hilfe

```text
.help
```

## Tabellen anzeigen

```text
.tables
```

## Schema anzeigen

```text
.schema
```

## Ausgabe übersichtlicher darstellen

```text
.headers on
.mode column
```

## SQLite verlassen

```text
.quit
```

## Merke

Befehle, die mit einem Punkt beginnen, sind Befehle der SQLite-CLI und **kein SQL**.

Beispiel:

```text
.tables
```

ist SQLite-CLI.

```sql
SELECT * FROM Schueler;
```

ist SQL.
