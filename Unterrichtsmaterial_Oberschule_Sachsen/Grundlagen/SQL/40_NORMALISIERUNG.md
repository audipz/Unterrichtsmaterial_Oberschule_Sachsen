# Normalisierung

Normalisierung reduziert unnötige Datenwiederholungen.

## Ausgangstabelle

| Schueler | Klasse | Klassenlehrer |
|---|---|---|
| Mia | 9a | Frau Müller |
| Tim | 9a | Frau Müller |
| Lea | 9a | Frau Müller |

`9a` und `Frau Müller` werden mehrfach gespeichert.

## Aufteilung

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

## Vorteile

- weniger Redundanz,
- weniger Änderungsfehler,
- klarere Beziehungen.

## Normalformen

Als grundlegende Orientierung:

### 1NF

Werte sind atomar und Tabellenstrukturen eindeutig.

### 2NF

Nicht-Schlüsselattribute hängen vom gesamten Schlüssel ab.

### 3NF

Nicht-Schlüsselattribute hängen nicht voneinander ab.

Für den Einstieg ist wichtiger, die zugrunde liegenden Redundanzprobleme zu verstehen als Definitionen auswendig zu lernen.
