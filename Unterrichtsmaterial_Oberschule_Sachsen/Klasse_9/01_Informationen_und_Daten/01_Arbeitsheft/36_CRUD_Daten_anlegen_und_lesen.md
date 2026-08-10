# CRUD – Daten anlegen und lesen

CRUD beschreibt vier grundlegende Operationen mit Datensätzen:

| CRUD | SQL |
|---|---|
| Create | `INSERT` |
| Read | `SELECT` |
| Update | `UPDATE` |
| Delete | `DELETE` |

In diesem Kapitel beginnen wir mit **Create** und **Read**.

---

## Create – INSERT

```sql
INSERT INTO Klasse (KlasseID, Bezeichnung)
VALUES (1, '9a');
```

Mehrere Datensätze:

```sql
INSERT INTO Schueler
    (SchuelerID, Schuelernummer, Name, KlasseID)
VALUES
    (1001, 'S-1001', 'Mia', 1),
    (1002, 'S-1002', 'Tim', 1);
```

---

## Read – SELECT

Alle Datensätze:

```sql
SELECT *
FROM Schueler;
```

Nur bestimmte Spalten:

```sql
SELECT Schuelernummer, Name
FROM Schueler;
```

Gezielt filtern:

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1001;
```

---

## Aufgabe 1

Füge die Klasse `9b` mit der ID `2` ein.

```sql
____________________________________________________
____________________________________________________
```

---

## Aufgabe 2

Füge `Lea` mit

```text
SchuelerID: 1003
Schuelernummer: S-1003
KlasseID: 2
```

ein.

```sql
____________________________________________________
____________________________________________________
____________________________________________________
```

---

## Aufgabe 3

Lies nur Name und Schülernummer aller Schülerinnen und Schüler aus.

```sql
____________________________________________________
____________________________________________________
```

---

## Aufgabe 4

Lies nur den Datensatz mit `SchuelerID = 1003`.

```sql
____________________________________________________
____________________________________________________
____________________________________________________
```

---

## Typischer Fehler

`CREATE TABLE` ist **nicht** das Create aus CRUD.

CRUD-Create bedeutet:

> einen neuen **Datensatz** anlegen.

Dafür wird `INSERT` verwendet.
