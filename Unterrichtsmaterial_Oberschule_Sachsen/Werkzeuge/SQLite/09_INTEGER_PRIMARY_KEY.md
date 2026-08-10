# INTEGER PRIMARY KEY

`INTEGER PRIMARY KEY` besitzt in SQLite eine besondere Bedeutung.

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Name TEXT NOT NULL
);
```

Die Spalte wird dabei mit der internen `rowid` verbunden.

## Automatische ID

Wird keine ID angegeben, kann SQLite eine passende Ganzzahl erzeugen.

```sql
INSERT INTO Schueler (Name)
VALUES ('Mia');
```

## Empfehlung

Für einfache lokale Datenbanken ist

```sql
INTEGER PRIMARY KEY
```

meist ausreichend.

`AUTOINCREMENT` ist nicht automatisch notwendig.
