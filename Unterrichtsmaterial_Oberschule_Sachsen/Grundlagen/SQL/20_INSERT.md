# INSERT – Daten anlegen

Mit `INSERT` werden neue Datensätze gespeichert.

```sql
INSERT INTO Klasse (KlasseID, Bezeichnung)
VALUES (1, '9a');
```

```sql
INSERT INTO Schueler (SchuelerID, Name, KlasseID)
VALUES (1001, 'Mia', 1);
```

## Mehrere Datensätze

```sql
INSERT INTO Schueler (SchuelerID, Name, KlasseID)
VALUES
    (1002, 'Tim', 1),
    (1003, 'Lea', 1);
```

## CRUD

`INSERT` entspricht dem **Create** in CRUD.
