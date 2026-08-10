# DELETE – Daten löschen

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1001;
```

Ohne `WHERE`:

```sql
DELETE FROM Schueler;
```

werden alle Datensätze gelöscht.

Die Tabelle bleibt jedoch bestehen.

## CRUD

`DELETE` entspricht dem **Delete** in CRUD.
