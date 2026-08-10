# M14 – SQL-Referenzkarte

## Tabelle

```sql
CREATE TABLE Tabelle (
    ID INTEGER PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);
```

## Create

```sql
INSERT INTO Tabelle (ID, Name)
VALUES (1, 'Beispiel');
```

## Read

```sql
SELECT *
FROM Tabelle
WHERE ID = 1;
```

## Update

```sql
UPDATE Tabelle
SET Name = 'Neu'
WHERE ID = 1;
```

## Delete

```sql
DELETE FROM Tabelle
WHERE ID = 1;
```

## Sortieren

```sql
ORDER BY Name ASC
```

## Eindeutigkeit

```sql
UNIQUE
```

## Beziehung

```sql
FOREIGN KEY (...)
REFERENCES ...
```

Ausführliche Erklärung:

```text
Grundlagen/SQL/
```
