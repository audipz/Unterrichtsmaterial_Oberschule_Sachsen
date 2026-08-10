# SELECT – Daten lesen

Mit `SELECT` werden Daten abgefragt.

```sql
SELECT Name
FROM Schueler;
```

Mehrere Spalten:

```sql
SELECT SchuelerID, Name
FROM Schueler;
```

Alle Spalten:

```sql
SELECT *
FROM Schueler;
```

## Empfehlung

Für produktive Abfragen ist es häufig besser, benötigte Spalten ausdrücklich anzugeben.

## CRUD

`SELECT` entspricht dem **Read** in CRUD.
