# Was ist SQL?

SQL steht für **Structured Query Language**.

SQL wird verwendet, um relationale Datenbanken zu definieren, Daten zu speichern, abzufragen, zu verändern und zu löschen.

Typische Aufgaben:

```sql
CREATE TABLE ...
INSERT INTO ...
SELECT ...
UPDATE ...
DELETE ...
```

SQL beschreibt in erster Linie **was** mit Daten geschehen soll. Das Datenbanksystem entscheidet intern, **wie** die Anweisung möglichst effizient ausgeführt wird.

## Wichtige Sprachbereiche

### DDL – Data Definition Language

Struktur der Datenbank:

```sql
CREATE TABLE
ALTER TABLE
DROP TABLE
```

### DML – Data Manipulation Language

Daten verändern:

```sql
INSERT
UPDATE
DELETE
```

### DQL – Data Query Language

Daten lesen:

```sql
SELECT
```

## Merke

> SQL ist eine Sprache zur Arbeit mit relationalen Datenbanken. Konkrete Datenbanksysteme können zusätzliche Funktionen oder abweichende Details besitzen.
