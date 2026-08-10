# Lösung – SQL Tabellen anlegen

## Aufgabe 2

Eine mögliche Lösung:

```sql
CREATE TABLE Fach (
    FachID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(100) NOT NULL UNIQUE
);
```

Andere geeignete Datentypen sind abhängig vom verwendeten Datenbanksystem möglich.
