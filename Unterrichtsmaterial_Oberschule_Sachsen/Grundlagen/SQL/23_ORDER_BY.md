# ORDER BY – Daten sortieren

Aufsteigend:

```sql
SELECT Name
FROM Schueler
ORDER BY Name ASC;
```

Absteigend:

```sql
SELECT Name
FROM Schueler
ORDER BY Name DESC;
```

Mehrere Kriterien:

```sql
SELECT *
FROM Schueler
ORDER BY KlasseID ASC, Name ASC;
```
