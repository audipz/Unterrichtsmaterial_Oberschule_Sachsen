# CSV importieren

## DB Browser for SQLite

Typischer Ablauf:

1. Datenbank öffnen.
2. Importfunktion wählen.
3. CSV-Datei auswählen.
4. Trennzeichen prüfen.
5. Spaltennamen prüfen.
6. Datentypen prüfen.
7. Import durchführen.
8. Ergebnis kontrollieren.

## SQLite CLI

```text
.mode csv
.import daten.csv Schueler
```

## Wichtig

Vor dem Import prüfen:

- Zeichencodierung
- Trennzeichen
- Kopfzeile
- Datentypen
- leere Werte
- eindeutige Schlüssel

Ein erfolgreicher Import bedeutet nicht automatisch, dass die Daten fachlich korrekt sind.
