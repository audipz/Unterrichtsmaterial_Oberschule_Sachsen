# Lehrerband – SQL Praxisauftrag

## Ziel

Die Lernenden setzen den gesamten Weg vom Datenmodell bis zu CRUD praktisch um.

## Zeitbedarf

Je nach Vorerfahrung etwa 1–2 Unterrichtsstunden.

## Erwartetes Modell

```text
Schueler
  │
  └──< Teilnahme >──┐
                    │
                   Kurs
```

`Teilnahme` bildet die n:m-Beziehung ab.

## Mindestanforderungen

- drei Tabellen,
- Primärschlüssel,
- zwei Fremdschlüssel in `Teilnahme`,
- Eindeutigkeit der Kombination,
- Beispieldaten,
- CRUD vollständig durchgeführt.

## Bewertungsschwerpunkte

- fachlich korrektes Datenmodell,
- kontrollierter Umgang mit verändernden SQL-Befehlen,
- nachvollziehbare Schlüsselwahl,
- korrekte CRUD-Zuordnung.

## Differenzierung

Erweiterung:

- zusätzliche Attribute,
- `UNIQUE`,
- `NOT NULL`,
- `CHECK`,
- Join-Abfrage.
