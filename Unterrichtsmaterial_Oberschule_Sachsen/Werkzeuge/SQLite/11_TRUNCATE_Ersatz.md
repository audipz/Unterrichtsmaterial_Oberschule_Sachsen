# TRUNCATE TABLE in SQLite

SQLite unterstützt `TRUNCATE TABLE` nicht.

Folgendes ist deshalb ungültig:

```sql
TRUNCATE TABLE Schueler;
```

## SQLite-Variante

Alle Datensätze löschen:

```sql
DELETE FROM Schueler;
```

Die Tabelle bleibt bestehen.

## Achtung

`DELETE FROM` und `TRUNCATE TABLE` sind konzeptionell nicht identisch.

Das SQL-Grundlagenkapitel behandelt die allgemeine Bedeutung von `TRUNCATE TABLE`.

Dieses Kapitel beschreibt nur die SQLite-spezifische Umsetzung.
