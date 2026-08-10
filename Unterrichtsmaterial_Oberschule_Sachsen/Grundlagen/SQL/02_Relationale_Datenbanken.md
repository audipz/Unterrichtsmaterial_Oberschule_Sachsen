# Relationale Datenbanken

Eine relationale Datenbank speichert Daten in Tabellen.

Beispiel:

| SchuelerID | Name | Klasse |
|---:|---|---|
| 1 | Mia | 9a |
| 2 | Tim | 9a |

Eine Zeile beschreibt einen Datensatz.

Eine Spalte beschreibt ein Attribut.

## Beziehungen

Tabellen können miteinander verbunden werden.

```text
Klasse
  │
  └──< Schueler
```

Eine Klasse kann viele Schülerinnen und Schüler enthalten.

## Warum mehrere Tabellen?

Mehrere Tabellen helfen,

- Wiederholungen zu vermeiden,
- Daten konsistent zu halten,
- Beziehungen sichtbar zu machen,
- Änderungen einfacher durchzuführen.
