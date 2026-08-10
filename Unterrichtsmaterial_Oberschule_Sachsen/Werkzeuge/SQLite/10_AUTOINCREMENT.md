# AUTOINCREMENT

SQLite unterstützt:

```sql
INTEGER PRIMARY KEY AUTOINCREMENT
```

Beispiel:

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT NOT NULL
);
```

## Unterschied

`INTEGER PRIMARY KEY` kann bereits automatisch neue IDs erzeugen.

`AUTOINCREMENT` verändert die Regel, nach der neue IDs vergeben werden, und verhindert insbesondere die Wiederverwendung bestimmter bereits vergebener Werte.

## Konsequenz

`AUTOINCREMENT` verursacht zusätzlichen Verwaltungsaufwand.

## Empfehlung

> `AUTOINCREMENT` nur verwenden, wenn die strengere ID-Vergabe tatsächlich benötigt wird.
