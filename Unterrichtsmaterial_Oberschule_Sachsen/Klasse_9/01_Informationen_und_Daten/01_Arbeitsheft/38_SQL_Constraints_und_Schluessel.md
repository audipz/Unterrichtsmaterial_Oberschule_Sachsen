# SQL – Constraints und Schlüssel

Eine Datenbank soll nicht nur Daten speichern.

Sie soll auch verhindern, dass offensichtlich ungültige Daten gespeichert werden.

Dafür gibt es **Constraints**.

---

## PRIMARY KEY

```sql
SchuelerID INTEGER PRIMARY KEY
```

Jeder Datensatz erhält eine eindeutige Identität.

---

## UNIQUE

```sql
Schuelernummer VARCHAR(20) UNIQUE
```

Der Wert darf nicht doppelt vorkommen.

Eine Tabelle kann mehrere `UNIQUE`-Constraints besitzen.

---

## NOT NULL

```sql
Name VARCHAR(100) NOT NULL
```

Ein Wert muss vorhanden sein.

---

## FOREIGN KEY

```sql
FOREIGN KEY (KlasseID)
    REFERENCES Klasse(KlasseID)
```

Die Klasse muss existieren.

---

## Zusammengesetzter Schlüssel

Bei einer Zuordnungstabelle:

```sql
CREATE TABLE Teilnahme (
    SchuelerID INTEGER,
    KursID INTEGER,
    PRIMARY KEY (SchuelerID, KursID)
);
```

Die Kombination aus beiden Werten ist eindeutig.

---

## Alternative Schlüssel

Eine Tabelle kann mehrere mögliche eindeutige Merkmale besitzen.

Beispiel:

```text
SchuelerID
Schuelernummer
```

`SchuelerID` wird Primärschlüssel.

`Schuelernummer` bleibt ebenfalls eindeutig:

```sql
Schuelernummer VARCHAR(20) UNIQUE
```

---

## Aufgabe

Welche Regel passt?

| Anforderung | Constraint |
|---|---|
| Name muss vorhanden sein | |
| E-Mail darf nicht doppelt vorkommen | |
| ID identifiziert Datensatz | |
| Klasse muss existieren | |
