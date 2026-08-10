# UNIQUE Constraint

`UNIQUE` stellt sicher, dass ein Wert oder eine Kombination von Werten nicht doppelt vorkommt.

```sql
CREATE TABLE Benutzer (
    BenutzerID INTEGER PRIMARY KEY,
    Benutzername VARCHAR(100) UNIQUE,
    Email VARCHAR(200) UNIQUE
);
```

## PRIMARY KEY und UNIQUE

| PRIMARY KEY | UNIQUE |
|---|---|
| identifiziert Datensatz | erzwingt Eindeutigkeit |
| genau einer pro Tabelle | mehrere möglich |
| kann zusammengesetzt sein | kann ebenfalls zusammengesetzt sein |

## Zusammengesetztes UNIQUE

```sql
UNIQUE (Vorname, Nachname, Geburtsdatum)
```

## NULL

Das Verhalten von `NULL` in `UNIQUE`-Constraints kann sich zwischen Datenbanksystemen unterscheiden.
