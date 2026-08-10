# CSV exportieren

## SQLite CLI

```text
.headers on
.mode csv
.output schueler.csv
SELECT * FROM Schueler;
.output stdout
```

## DB Browser for SQLite

Abfragen oder Tabellen können über die Exportfunktionen als CSV gespeichert werden.

## Einsatz

CSV eignet sich besonders für:

- Tabellenkalkulation,
- Datenaustausch,
- kleinere Analysen,
- Sicherung einzelner Abfrageergebnisse.
