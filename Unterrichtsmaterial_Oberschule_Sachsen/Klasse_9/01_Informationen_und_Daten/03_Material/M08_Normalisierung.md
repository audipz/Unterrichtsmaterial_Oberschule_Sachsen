# M08 – Normalisierung

## Ausgangstabelle

| SchuelerID | Name | Klasse | Klassenlehrer |
|---:|---|---|---|
| 1001 | Mia | 9a | Frau Müller |
| 1002 | Tim | 9a | Frau Müller |
| 1003 | Lea | 9b | Herr Weber |

## Aufgaben

1. Welche Daten werden mehrfach gespeichert?
2. Welche Probleme entstehen bei Änderungen?
3. Zerlegt die Tabelle in:

```text
Schueler
Klasse
```

4. Ergänzt Primär- und Fremdschlüssel.

## Ziel

Eine mögliche Struktur:

```text
Klasse
- KlasseID
- Bezeichnung
- Klassenlehrer

Schueler
- SchuelerID
- Name
- KlasseID
```
