# Fremdschlüssel in SQLite

SQLite unterstützt Fremdschlüssel.

Sie müssen jedoch in vielen Nutzungssituationen explizit aktiviert werden.

## Aktivieren

```sql
PRAGMA foreign_keys = ON;
```

## Status prüfen

```sql
PRAGMA foreign_keys;
```

Ergebnis:

```text
1
```

bedeutet aktiviert.

## Beispiel

```sql
CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung TEXT NOT NULL
);

CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Name TEXT NOT NULL,
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
);
```

## Wichtig

> Ein definierter Fremdschlüssel schützt nur dann zuverlässig, wenn die Fremdschlüsselprüfung aktiviert ist.
