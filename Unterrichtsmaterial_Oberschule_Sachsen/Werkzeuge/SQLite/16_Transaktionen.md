# Transaktionen in SQLite

SQLite unterstützt Transaktionen.

```sql
BEGIN;

UPDATE Konto
SET Guthaben = Guthaben - 10
WHERE KontoID = 1;

UPDATE Konto
SET Guthaben = Guthaben + 10
WHERE KontoID = 2;

COMMIT;
```

Bei einem Fehler:

```sql
ROLLBACK;
```

## Bedeutung

Mehrere Änderungen können damit als gemeinsame Einheit behandelt werden.

Transaktionen gehören fachlich zu den SQL-/Datenbankgrundlagen, werden hier aber zusätzlich praktisch für SQLite gezeigt.
