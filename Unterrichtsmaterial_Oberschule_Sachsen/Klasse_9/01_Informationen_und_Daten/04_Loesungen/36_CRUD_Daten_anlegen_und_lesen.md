# Lösung – CRUD Daten anlegen und lesen

## Aufgabe 1

```sql
INSERT INTO Klasse (KlasseID, Bezeichnung)
VALUES (2, '9b');
```

## Aufgabe 2

```sql
INSERT INTO Schueler
    (SchuelerID, Schuelernummer, Name, KlasseID)
VALUES
    (1003, 'S-1003', 'Lea', 2);
```

## Aufgabe 3

```sql
SELECT Schuelernummer, Name
FROM Schueler;
```

## Aufgabe 4

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1003;
```
