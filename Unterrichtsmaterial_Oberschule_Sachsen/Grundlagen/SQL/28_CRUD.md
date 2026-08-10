# CRUD

CRUD fasst vier grundlegende Datenoperationen zusammen.

| CRUD | SQL |
|---|---|
| Create | INSERT |
| Read | SELECT |
| Update | UPDATE |
| Delete | DELETE |

## Beispiel

```sql
INSERT INTO Schueler (SchuelerID, Name)
VALUES (1, 'Mia');

SELECT *
FROM Schueler
WHERE SchuelerID = 1;

UPDATE Schueler
SET Name = 'Mia Müller'
WHERE SchuelerID = 1;

DELETE FROM Schueler
WHERE SchuelerID = 1;
```

> `CREATE TABLE` gehört trotz des Wortes CREATE nicht zum CRUD-Create. CRUD-Create meint das Anlegen eines Datensatzes.
