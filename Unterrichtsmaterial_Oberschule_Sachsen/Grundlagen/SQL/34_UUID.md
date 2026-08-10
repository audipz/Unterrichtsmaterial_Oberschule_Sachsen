# UUID

Eine UUID ist eine sehr große Kennung.

Beispiel:

```text
550e8400-e29b-41d4-a716-446655440000
```

UUIDs werden häufig als künstliche Schlüssel verwendet.

## Vorteile

- können dezentral erzeugt werden,
- sehr geringe Kollisionswahrscheinlichkeit,
- gut für verteilte Systeme.

## Nachteile

- schlechter lesbar,
- größer als einfache Integer-Schlüssel,
- können bei Indizes mehr Speicher benötigen.

## Merke

> UUIDs sind eine mögliche Form künstlicher Schlüssel, aber nicht automatisch die beste Wahl für jede Tabelle.
