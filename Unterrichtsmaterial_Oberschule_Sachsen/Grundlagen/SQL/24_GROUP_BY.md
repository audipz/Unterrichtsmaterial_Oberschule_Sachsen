# GROUP BY – Daten gruppieren

`GROUP BY` wird häufig mit Aggregatfunktionen verwendet.

```sql
SELECT KlasseID, COUNT(*) AS Anzahl
FROM Schueler
GROUP BY KlasseID;
```

Typische Aggregatfunktionen:

- `COUNT`
- `SUM`
- `AVG`
- `MIN`
- `MAX`
