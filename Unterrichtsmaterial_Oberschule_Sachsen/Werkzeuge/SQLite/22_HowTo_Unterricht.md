# HowTo – SQLite im Unterricht

## Neue Übungsdatenbank

```bash
sqlite3 schule.db
```

## Fremdschlüssel aktivieren

```sql
PRAGMA foreign_keys = ON;
```

## Schema ausführen

```text
.read Beispiele/schulverwaltung_sqlite.sql
```

## Tabellen prüfen

```text
.tables
```

## Daten anzeigen

```sql
SELECT *
FROM Schueler;
```

## CRUD üben

```sql
INSERT ...
SELECT ...
UPDATE ...
DELETE ...
```

## Beenden

```text
.quit
```

## Empfohlener Lernweg

1. Tabelle ansehen.
2. SELECT ausführen.
3. INSERT ausführen.
4. UPDATE ausführen.
5. DELETE ausführen.
6. neue Tabelle anlegen.
7. Fremdschlüssel ergänzen.
8. Join ausführen.
