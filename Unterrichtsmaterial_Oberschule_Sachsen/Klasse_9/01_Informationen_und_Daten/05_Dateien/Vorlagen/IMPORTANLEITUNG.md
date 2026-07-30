# Importanleitung

## CSV in LibreOffice Calc

1. Datei über **Datei → Öffnen** auswählen.
2. Zeichensatz **Unicode (UTF-8)** wählen.
3. Als Trennzeichen **Komma** aktivieren.
4. Vorschau prüfen und Import bestätigen.

## CSV in Microsoft Excel

1. **Daten → Aus Text/CSV** öffnen.
2. Datei auswählen.
3. Dateiursprung **UTF-8** wählen.
4. Trennzeichen **Komma** wählen.
5. Datentypen prüfen und laden.

## SQLite-Datenbank

### DB Browser for SQLite

1. `festival.db` öffnen.
2. Im Bereich **Daten durchsuchen** Tabellen auswählen.
3. Unter **SQL ausführen** Abfragen aus `beispielabfragen.sql` testen.

### SQLiteStudio

1. **Datenbank → Datenbank hinzufügen** wählen.
2. `festival.db` auswählen.
3. Tabellen, Views und Indizes im Strukturbaum untersuchen.

## JSON

JSON-Dateien können in einem Texteditor, Visual Studio Code oder einer Programmierumgebung geöffnet werden. Für die Validierung stehen zwei Schema-Dateien bereit.

## JSON Lines

In `systemprotokoll.jsonl` steht pro Zeile genau ein vollständiges JSON-Objekt. Die Datei eignet sich besonders für zeilenweises Einlesen mit Python.
