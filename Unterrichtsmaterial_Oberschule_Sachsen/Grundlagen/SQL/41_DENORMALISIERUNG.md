# Denormalisierung

Denormalisierung bedeutet, Daten bewusst mehrfach oder weniger stark getrennt zu speichern.

Das kann sinnvoll sein, wenn Lesezugriffe besonders schnell sein müssen.

## Beispiel

Eine normalisierte Bestellung könnte Daten über mehrere Tabellen beziehen:

```text
Bestellung
   │
   ├── Kunde
   │
   └── Adresse
```

Für sehr häufig gelesene Berichte könnten ausgewählte Werte zusätzlich direkt in einer Auswertungstabelle gespeichert werden.

## Vorteile

- weniger Joins,
- schnellere Lesezugriffe möglich,
- vereinfachte Reports.

## Nachteile

- Daten werden doppelt gespeichert,
- höherer Speicherbedarf,
- Änderungen müssen synchron gehalten werden,
- Risiko inkonsistenter Daten.

## Merke

> Normalisierung verbessert Konsistenz. Denormalisierung kann gezielt Performance verbessern. Beides ist eine bewusste Architekturentscheidung.
