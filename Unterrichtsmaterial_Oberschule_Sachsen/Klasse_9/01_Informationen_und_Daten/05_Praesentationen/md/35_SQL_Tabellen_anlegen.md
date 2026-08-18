---
title: "SQL Tabellen anlegen"
lang: de-DE
---

# SQL – Tabellen anlegen

## Vom Modell zur Datenbank

```sql
CREATE TABLE ...
```

---

# Eine Tabelle braucht

- Namen
- Spalten
- Datentypen
- Schlüssel
- Regeln

---

# Beispiel

```sql
CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(10) NOT NULL UNIQUE
);
```

---

# Beziehung

```sql
FOREIGN KEY (KlasseID)
REFERENCES Klasse(KlasseID)
```
