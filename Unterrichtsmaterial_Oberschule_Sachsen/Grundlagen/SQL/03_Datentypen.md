# Datentypen

Spalten besitzen Datentypen.

Häufige SQL-Datentypen sind:

| Typ | Verwendung |
|---|---|
| INTEGER | ganze Zahlen |
| DECIMAL | genaue Dezimalzahlen |
| VARCHAR | Texte begrenzter Länge |
| TEXT | längere Texte |
| DATE | Datum |
| TIMESTAMP | Datum und Uhrzeit |
| BOOLEAN | Wahr/Falsch |

## Beispiel

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER,
    Name VARCHAR(100),
    Geburtsdatum DATE
);
```

## Warum Datentypen wichtig sind

Datentypen helfen bei:

- Validierung,
- Speicherbedarf,
- Sortierung,
- Berechnungen,
- Datenqualität.
