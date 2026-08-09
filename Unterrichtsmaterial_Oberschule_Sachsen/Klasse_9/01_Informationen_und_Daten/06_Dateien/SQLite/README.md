# SQLite-Komplettpaket: Schulfestival

Die Datenbank `festival.db` bildet ein fiktives, aber realistisch geplantes Schulfestival ab.
Sie eignet sich für den Informatikunterricht der Klassenstufe 9 zu relationalen Datenbanken,
SQL-Abfragen, Schlüsseln, Beziehungen, Filtern, Gruppieren und Auswerten.

## Enthaltene Tabellen

| Tabelle | Datensätze | Inhalt |
|---|---:|---|
| `raeume` | 8 | Veranstaltungsorte und Kapazitäten |
| `veranstaltungen` | 12 | Programm des Festivals |
| `besucher` | 120 | anonymisierte Beispieldaten |
| `tickets` | 240 | Käufe, Reservierungen und Stornierungen |
| `helfer` | 24 | Helferinnen und Helfer |
| `anmeldungen` | 72 | Helfereinsätze bei Veranstaltungen |
| `artikel` | 10 | Getränke, Speisen und Merchandise |
| `verkaeufe` | 274 | Verkäufe während des Festivals |

## Datenbankobjekte

- Primär- und Fremdschlüssel
- CHECK- und UNIQUE-Bedingungen
- sechs Indizes
- Views `veranstaltung_auslastung`, `umsatz_nach_artikel` und `helfer_einsaetze`
- aktivierte Fremdschlüsselprüfung

## Dateien

- `festival.db` – direkt nutzbare SQLite-Datenbank
- `schema.sql` – Tabellen, Indizes und Views
- `beispielabfragen.sql` – zehn kommentierte Unterrichtsabfragen
- `arbeitsauftraege.md` – differenzierte Aufgaben
- `datenuebersicht.json` – maschinenlesbare Übersicht

## Empfohlene Programme

- DB Browser for SQLite
- SQLiteStudio
- Python mit dem Modul `sqlite3`

Alle Namen, Kontaktdaten und Verkäufe sind vollständig fiktiv.
