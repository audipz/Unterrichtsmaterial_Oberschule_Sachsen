# SQL Cheatsheet

## Tabelle

```sql
CREATE TABLE Tabelle (
    ID INTEGER PRIMARY KEY
);
```

## Einfügen

```sql
INSERT INTO Tabelle (ID)
VALUES (1);
```

## Lesen

```sql
SELECT *
FROM Tabelle;
```

## Filtern

```sql
SELECT *
FROM Tabelle
WHERE ID = 1;
```

## Ändern

```sql
UPDATE Tabelle
SET ...
WHERE ...;
```

## Löschen

```sql
DELETE FROM Tabelle
WHERE ...;
```

## Sortieren

```sql
ORDER BY Spalte ASC;
```

## Gruppieren

```sql
GROUP BY Spalte;
```

## Join

```sql
SELECT ...
FROM A
JOIN B ON A.ID = B.A_ID;
```

## Constraints

```sql
PRIMARY KEY
FOREIGN KEY
UNIQUE
NOT NULL
CHECK
DEFAULT
```
