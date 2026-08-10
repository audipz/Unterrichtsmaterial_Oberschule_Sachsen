# WHERE – Daten filtern

Mit `WHERE` werden Datensätze anhand einer Bedingung ausgewählt.

```sql
SELECT *
FROM Schueler
WHERE KlasseID = 1;
```

Vergleichsoperatoren:

```text
=
<>
<
>
<=
>=
```

Mehrere Bedingungen:

```sql
SELECT *
FROM Schueler
WHERE KlasseID = 1
  AND Name = 'Mia';
```
