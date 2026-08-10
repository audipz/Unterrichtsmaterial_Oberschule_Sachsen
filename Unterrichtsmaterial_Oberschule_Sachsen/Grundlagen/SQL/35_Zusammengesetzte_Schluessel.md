# Zusammengesetzte Schlüssel

Ein Schlüssel kann aus mehreren Spalten bestehen.

Beispiel einer Zuordnungstabelle:

```sql
CREATE TABLE Teilnahme (
    SchuelerID INTEGER,
    KursID INTEGER,
    PRIMARY KEY (SchuelerID, KursID)
);
```

Die Kombination ist eindeutig.

Ein Schüler kann nicht zweimal demselben Kurs zugeordnet werden.

## Alternative

Man könnte zusätzlich eine künstliche `TeilnahmeID` verwenden und die Kombination absichern:

```sql
UNIQUE (SchuelerID, KursID)
```

Welche Variante sinnvoller ist, hängt vom Datenmodell ab.
