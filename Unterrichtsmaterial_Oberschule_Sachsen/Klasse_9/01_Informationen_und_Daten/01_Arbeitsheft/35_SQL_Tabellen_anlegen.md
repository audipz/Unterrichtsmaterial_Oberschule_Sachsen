# SQL – Tabellen anlegen

## Vom Datenmodell zur echten Tabelle

Ihr habt bereits Tabellen, Attribute sowie Primär- und Fremdschlüssel kennengelernt.

Jetzt setzt ihr ein Datenmodell mit SQL praktisch um.

Für die allgemeine Syntax könnt ihr nachschlagen unter:

```text
Grundlagen/SQL/
```

Für SQLite:

```text
Werkzeuge/SQLite/
```

---

## Beispiel: Klasse

```sql
CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(10) NOT NULL UNIQUE
);
```

### Bedeutung

| Teil | Bedeutung |
|---|---|
| `CREATE TABLE` | neue Tabelle anlegen |
| `Klasse` | Tabellenname |
| `KlasseID` | Attribut/Spalte |
| `INTEGER` | Datentyp |
| `PRIMARY KEY` | eindeutiger Primärschlüssel |
| `NOT NULL` | Wert muss vorhanden sein |
| `UNIQUE` | Wert darf nicht doppelt vorkommen |

---

## Zweite Tabelle

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Schuelernummer VARCHAR(20) NOT NULL UNIQUE,
    Name VARCHAR(100) NOT NULL,
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
);
```

---

## Aufgabe 1

Markiere im SQL-Code:

- Tabellenname
- Primärschlüssel
- Fremdschlüssel
- UNIQUE Constraint
- Pflichtfelder

---

## Aufgabe 2

Entwirf eine Tabelle `Fach` mit:

- `FachID`
- `Bezeichnung`
- eindeutigem Primärschlüssel
- eindeutiger Bezeichnung

```sql
CREATE TABLE Fach (
    ________________________________________________
    ________________________________________________
    ________________________________________________
);
```

---

## Aufgabe 3

Lege die Tabellen in SQLite an.

Prüfe danach, ob sie existieren.

### CLI

```text
.tables
```

### DB Browser

Register **Datenbankstruktur** öffnen.

---

## Merke

> Das Datenmodell beschreibt die Struktur fachlich. `CREATE TABLE` setzt diese Struktur technisch um.
