# JOINs

JOINs verbinden Datensätze aus mehreren Tabellen.

## INNER JOIN

```sql
SELECT Schueler.Name, Klasse.Bezeichnung
FROM Schueler
INNER JOIN Klasse
    ON Schueler.KlasseID = Klasse.KlasseID;
```

Es erscheinen nur passende Datensätze.

## LEFT JOIN

```sql
SELECT Schueler.Name, Klasse.Bezeichnung
FROM Schueler
LEFT JOIN Klasse
    ON Schueler.KlasseID = Klasse.KlasseID;
```

Alle Schülerinnen und Schüler erscheinen, auch wenn keine passende Klasse vorhanden ist.

## Weitere JOIN-Arten

Der SQL-Standard kennt weitere Varianten. Die konkrete Unterstützung kann je Datenbanksystem variieren.
