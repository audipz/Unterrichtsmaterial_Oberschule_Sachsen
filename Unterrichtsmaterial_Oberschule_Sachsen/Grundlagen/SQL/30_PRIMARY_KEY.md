# PRIMARY KEY

Ein Primärschlüssel identifiziert jeden Datensatz eindeutig.

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);
```

## Eigenschaften

Ein Primärschlüssel:

- ist eindeutig,
- darf nicht fehlen,
- sollte möglichst stabil bleiben.

Eine Tabelle besitzt genau einen Primärschlüssel. Dieser kann aus einer oder mehreren Spalten bestehen.
