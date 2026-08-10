# Indizes in SQLite

Index anlegen:

```sql
CREATE INDEX idx_schueler_name
ON Schueler(Name);
```

Indizes anzeigen:

```sql
SELECT name
FROM sqlite_master
WHERE type = 'index';
```

Index löschen:

```sql
DROP INDEX idx_schueler_name;
```

## Abfrageplan

SQLite kann den geplanten Zugriff anzeigen:

```sql
EXPLAIN QUERY PLAN
SELECT *
FROM Schueler
WHERE Name = 'Mia';
```

Das hilft zu erkennen, ob ein Index verwendet wird.
