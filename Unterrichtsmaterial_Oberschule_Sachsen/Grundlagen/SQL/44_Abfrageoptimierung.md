# Abfrageoptimierung

Einige grundlegende Regeln:

## Nur benötigte Spalten

Statt:

```sql
SELECT *
FROM Schueler;
```

wenn nur der Name benötigt wird:

```sql
SELECT Name
FROM Schueler;
```

## Geeignete Filter

```sql
WHERE SchuelerID = 1001
```

reduziert die zu verarbeitende Datenmenge.

## Indizes

Häufig gesuchte oder für Joins verwendete Spalten können geeignete Indexkandidaten sein.

## Unnötige Joins vermeiden

Nur Tabellen verbinden, deren Daten tatsächlich benötigt werden.

## Denormalisierung

Bei speziellen Lese- oder Reportinganforderungen kann bewusst redundante Speicherung sinnvoll sein.

> Optimierung sollte auf einem tatsächlichen Problem beruhen, nicht nur auf Vermutungen.
