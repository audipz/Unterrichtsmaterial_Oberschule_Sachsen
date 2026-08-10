# Lösung – CRUD Daten ändern und löschen

## Aufgabe 1

```sql
UPDATE Schueler
SET Name = 'Lea Schneider'
WHERE SchuelerID = 1003;
```

## Aufgabe 2

```sql
UPDATE Schueler
SET KlasseID = 2
WHERE SchuelerID = 1001;
```

## Aufgabe 3

Kontrolle:

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1002;
```

Löschen:

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1002;
```
