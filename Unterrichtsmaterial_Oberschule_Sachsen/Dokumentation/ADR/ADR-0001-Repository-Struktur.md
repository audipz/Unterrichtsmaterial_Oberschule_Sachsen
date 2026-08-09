# ADR-0001 – Repository-Struktur

## Status

Akzeptiert

## Kontext

Das Lehrwerk enthält Materialien für mehrere Klassenstufen sowie klassenübergreifende Grundlagen. Inhalte müssen schnell auffindbar, einzeln austauschbar und unabhängig versionierbar sein.

## Entscheidung

Das Repository trennt klassenübergreifende Grundlagen, Klassenstufen, Dokumentation, Lizenzen und Vorlagen.

Grundlegende Werkzeuge und Nachschlageinhalte werden außerhalb der Klassenstufen abgelegt, da sie für mehrere Klassenstufen relevant sind.

Lehrplanbezogene Inhalte verbleiben innerhalb der jeweiligen Klassenstufe.

## Konsequenzen

### Vorteile

- Keine unnötige Duplizierung von Grundlagen.
- Klassenstufen bleiben lehrplannah.
- Materialien können unabhängig gepflegt werden.
- Lehrkräfte finden Werkzeughilfen zentral.

### Nachteile

- Fachkapitel müssen teilweise auf Grundlagen verweisen.
- Querverweise müssen bei Umstrukturierungen geprüft werden.
