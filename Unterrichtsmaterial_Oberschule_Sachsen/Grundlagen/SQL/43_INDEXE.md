# Indizes

Ein Index beschleunigt das Finden von Datensätzen.

```sql
CREATE INDEX idx_schueler_name
ON Schueler(Name);
```

## Idee

Ohne geeigneten Index muss ein Datenbanksystem möglicherweise viele oder alle Zeilen durchsuchen.

Ein Index erzeugt eine zusätzliche Suchstruktur.

## Vorteile

- schnellere Suchabfragen,
- schnellere Joins in geeigneten Fällen.

## Nachteile

- zusätzlicher Speicher,
- INSERT, UPDATE und DELETE können aufwendiger werden.

## Merke

> Ein Index beschleunigt Lesen nicht kostenlos. Er verursacht Speicher- und Pflegeaufwand.
