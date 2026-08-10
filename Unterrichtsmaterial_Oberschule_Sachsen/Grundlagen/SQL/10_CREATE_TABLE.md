# CREATE TABLE

Mit `CREATE TABLE` wird eine neue Tabelle angelegt.

```sql
CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(10) NOT NULL
);
```

## Zweite Tabelle

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
);
```

## Reihenfolge

Wenn ein Fremdschlüssel auf eine andere Tabelle verweist, sollte die referenzierte Tabelle bereits existieren.
