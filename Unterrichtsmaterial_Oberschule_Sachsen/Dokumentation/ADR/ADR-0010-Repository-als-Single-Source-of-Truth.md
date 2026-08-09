# ADR-0010 – Repository als Single Source of Truth

## Status

Akzeptiert

## Kontext

Während der Entwicklung entstehen viele Dateien und Versionen. Doppelarbeit und widersprüchliche Strukturen müssen vermieden werden.

## Entscheidung

Der aktuelle Stand auf `main` ist die verbindliche Referenz für die weitere Entwicklung.

Vor neuen Arbeitspaketen wird geprüft:

- Was existiert bereits?
- Was fehlt?
- Was ist unvollständig?
- Gibt es offensichtliche Inkonsistenzen?

Neue Inhalte werden nur ergänzt, wenn sie im aktuellen Repository fehlen oder bewusst überarbeitet werden sollen.

## Konsequenzen

Der Entwicklungsprozess bleibt nachvollziehbar.

Nach abgeschlossenen Kapiteln wird der aktuelle Stand gepusht.

Reviews und Qualitätskorrekturen erfolgen auf Basis der vorhandenen Markdown-Dateien.
