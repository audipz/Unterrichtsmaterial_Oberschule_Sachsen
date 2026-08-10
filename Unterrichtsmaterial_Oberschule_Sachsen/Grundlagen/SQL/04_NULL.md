# NULL

`NULL` bedeutet:

> Kein Wert ist vorhanden.

`NULL` ist nicht dasselbe wie:

```text
0
""
false
```

## Prüfen auf NULL

```sql
SELECT *
FROM Schueler
WHERE Telefonnummer IS NULL;
```

Nicht:

```sql
WHERE Telefonnummer = NULL
```

## NOT NULL

Wenn ein Wert zwingend vorhanden sein muss:

```sql
Name VARCHAR(100) NOT NULL
```

## Merke

> NULL bedeutet fehlender oder unbekannter Wert.
