# Datentypen und Type Affinity

SQLite behandelt Datentypen flexibler als viele andere relationale Datenbanksysteme.

SQLite verwendet sogenannte **Type Affinities**.

Wichtige Gruppen sind:

- INTEGER
- REAL
- TEXT
- BLOB
- NUMERIC

## Beispiel

```sql
CREATE TABLE Messwert (
    MesswertID INTEGER PRIMARY KEY,
    Wert REAL,
    Einheit TEXT
);
```

## Wichtig

SQLite erzwingt Datentypen in klassischen Tabellen weniger streng als viele andere Datenbanksysteme.

Für Unterricht und Datenqualität gilt trotzdem:

> Spalten sollten fachlich passende Datentypen erhalten.

## STRICT Tables

Neuere SQLite-Versionen unterstützen `STRICT`-Tabellen.

```sql
CREATE TABLE Beispiel (
    ID INTEGER PRIMARY KEY,
    Name TEXT NOT NULL
) STRICT;
```

Damit werden Datentypen strenger kontrolliert.
