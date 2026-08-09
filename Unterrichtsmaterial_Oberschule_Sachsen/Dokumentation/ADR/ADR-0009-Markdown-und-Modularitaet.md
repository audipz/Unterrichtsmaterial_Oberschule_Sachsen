# ADR-0009 – Markdown und modulare Dateien

## Status

Akzeptiert

## Kontext

Das Lehrwerk soll offen, versionsfähig und leicht überarbeitbar sein.

## Entscheidung

Textinhalte werden bevorzugt in Markdown gepflegt.

Größere Werkteile werden in fachlich sinnvolle Einzeldateien aufgeteilt.

Dateien sollen:

- unabhängig verständlich sein
- klar benannt sein
- gezielt ersetzt werden können
- mit Git sinnvoll diffbar sein

## Konsequenzen

Qualitätssicherung kann einzelne Dateien oder Abschnitte austauschen, ohne komplette Dokumente neu erzeugen zu müssen.

Spätere Exporte nach PDF, HTML oder DOCX können automatisiert werden.
