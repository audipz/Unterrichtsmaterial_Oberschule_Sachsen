# Views in SQLite

SQLite unterstützt Views.

```sql
CREATE VIEW SchuelerMitKlasse AS
SELECT
    Schueler.SchuelerID,
    Schueler.Name,
    Klasse.Bezeichnung
FROM Schueler
JOIN Klasse
    ON Schueler.KlasseID = Klasse.KlasseID;
```

Abfrage:

```sql
SELECT *
FROM SchuelerMitKlasse;
```

Löschen:

```sql
DROP VIEW SchuelerMitKlasse;
```

## Einordnung

Views sind ein fortgeschrittenes Thema.

Die allgemeinen Konzepte gehören in den SQL-Grundlagenbereich, sobald sie dort eingeführt werden.
