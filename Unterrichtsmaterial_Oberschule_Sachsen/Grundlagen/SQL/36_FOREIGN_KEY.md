# FOREIGN KEY

Ein Fremdschlüssel verweist auf einen Schlüssel einer anderen Tabelle.

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Name VARCHAR(100),
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
);
```

Dadurch entsteht eine Beziehung:

```text
Klasse
  1
  │
  │
  n
Schueler
```

## Zweck

Fremdschlüssel helfen, ungültige Verweise zu verhindern.
