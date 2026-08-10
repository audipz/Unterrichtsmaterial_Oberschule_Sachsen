# Lehrerband – CRUD: Update und Delete

## Ziel

Die Lernenden ändern und löschen Datensätze kontrolliert.

## Sicherheitsprinzip

Vor `UPDATE` oder `DELETE` zunächst dieselbe `WHERE`-Bedingung mit `SELECT` prüfen.

Beispiel:

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1002;
```

Danach erst:

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1002;
```

## Typischer Fehler

Fehlendes `WHERE`.

Die Konsequenz sollte praktisch an einer Übungsdatenbank gezeigt werden, nicht an produktiven Daten.

## Lernziel

Die Schülerinnen und Schüler verstehen, dass syntaktisch korrektes SQL trotzdem fachlich gefährlich sein kann.
