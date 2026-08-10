# HAVING

`WHERE` filtert einzelne Datensätze vor der Gruppierung.

`HAVING` filtert Gruppen nach der Gruppierung.

```sql
SELECT KlasseID, COUNT(*) AS Anzahl
FROM Schueler
GROUP BY KlasseID
HAVING COUNT(*) > 20;
```
