# UPDATE – Daten ändern

```sql
UPDATE Schueler
SET Name = 'Mia Müller'
WHERE SchuelerID = 1001;
```

## Gefahr ohne WHERE

```sql
UPDATE Schueler
SET KlasseID = 2;
```

ändert **alle** Datensätze.

## CRUD

`UPDATE` entspricht dem **Update** in CRUD.
