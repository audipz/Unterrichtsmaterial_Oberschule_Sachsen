# Lehrerband – CRUD: Create und Read

## Ziel

Die Lernenden führen `INSERT` und `SELECT` praktisch aus und ordnen beide Befehle dem CRUD-Modell zu.

## Kernkompetenz

```text
Create → INSERT
Read   → SELECT
```

## Typischer Begriffsfehler

`CREATE TABLE` wird häufig fälschlich als CRUD-Create eingeordnet.

Klarstellen:

- DDL `CREATE TABLE` → Struktur anlegen
- CRUD Create → Datensatz anlegen → `INSERT`

## Arbeitsweise

Jede `INSERT`-Anweisung sollte unmittelbar mit `SELECT` kontrolliert werden.

## Differenzierung

Unterstützung:

- Spaltennamen vorgeben,
- nur einen Datensatz einfügen.

Erweiterung:

- mehrere Datensätze in einer `INSERT`-Anweisung,
- gezielte `SELECT`-Abfragen mit `WHERE`.
