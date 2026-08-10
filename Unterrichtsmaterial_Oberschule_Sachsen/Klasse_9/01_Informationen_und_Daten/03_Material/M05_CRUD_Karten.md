# M05 – CRUD-Karten

Schneidet die Karten aus und ordnet sie den vier CRUD-Operationen zu.

---

## Karte A

```sql
INSERT INTO Schueler (SchuelerID, Name)
VALUES (1001, 'Mia');
```

---

## Karte B

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1001;
```

---

## Karte C

```sql
UPDATE Schueler
SET Name = 'Mia Müller'
WHERE SchuelerID = 1001;
```

---

## Karte D

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1001;
```

---

## Zuordnung

| CRUD | SQL |
|---|---|
| Create | |
| Read | |
| Update | |
| Delete | |

## Wichtig

`CREATE TABLE` gehört nicht zum CRUD-Create. CRUD-Create meint das Anlegen eines **Datensatzes**.
