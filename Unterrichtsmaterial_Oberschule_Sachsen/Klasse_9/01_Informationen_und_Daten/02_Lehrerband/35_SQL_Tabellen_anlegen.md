# Lehrerband – SQL Tabellen anlegen

## Ziel

Die Lernenden übertragen ein vorhandenes relationales Datenmodell in `CREATE TABLE`-Anweisungen.

## Vorkenntnisse

- Tabelle
- Attribut
- Primärschlüssel
- Fremdschlüssel
- Datentyp

## Verweise

```text
Grundlagen/SQL/10_CREATE_TABLE.md
Werkzeuge/SQLite/
```

## Didaktischer Hinweis

SQL wird hier nicht als komplett neue Sprache eingeführt. Die Lernenden kennen das Datenmodell bereits und setzen es nun technisch um.

## Erwartung

Die Lernenden können:

- Tabellenname bestimmen,
- Attribute als Spalten anlegen,
- einfache Datentypen wählen,
- `PRIMARY KEY`, `UNIQUE`, `NOT NULL` und `FOREIGN KEY` einsetzen.

## Typischer Fehler

Schülerinnen und Schüler versuchen, Beziehungen nur über gleiche Spaltennamen herzustellen.

Betonen:

> Ein gemeinsamer Name erzeugt noch keine referenzielle Beziehung. Dafür ist ein Fremdschlüssel nötig.
