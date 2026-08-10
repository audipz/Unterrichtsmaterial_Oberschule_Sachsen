# Lösung – Lernkontrolle 09 SQL und CRUD

## Aufgabe 1 – 6 Punkte

```sql
CREATE TABLE Fach (
    FachID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(100) NOT NULL UNIQUE
);
```

Bewertung:

- `CREATE TABLE`: 1
- Tabellenname: 1
- `FachID`: 1
- `PRIMARY KEY`: 1
- `NOT NULL`: 1
- `UNIQUE`: 1

## Aufgabe 2 – 4 Punkte

| SQL | CRUD |
|---|---|
| INSERT | Create |
| SELECT | Read |
| UPDATE | Update |
| DELETE | Delete |

## Aufgabe 3 – 4 Punkte

```sql
UPDATE Schueler
SET Name = 'Mia Müller'
WHERE SchuelerID = 1001;
```

## Aufgabe 4 – 3 Punkte

Ohne `WHERE` werden alle Datensätze aus `Schueler` gelöscht.

## Aufgabe 5 – 3 Punkte

Ein Fremdschlüssel verweist auf einen Datensatz einer anderen Tabelle und unterstützt die Sicherung gültiger Beziehungen bzw. referenzieller Integrität.
