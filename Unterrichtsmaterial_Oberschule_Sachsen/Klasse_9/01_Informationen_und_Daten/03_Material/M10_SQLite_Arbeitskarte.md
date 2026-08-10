# M10 – SQLite-Arbeitskarte

## Ziel

Eine Datenbank öffnen, SQL ausführen und CRUD anwenden.

## 1. Datenbank öffnen

```bash
sqlite3 schule.db
```

## 2. Übersicht einschalten

```text
.headers on
.mode column
```

## 3. Fremdschlüssel aktivieren

```sql
PRAGMA foreign_keys = ON;
```

## 4. Tabellen anzeigen

```text
.tables
```

## 5. Daten lesen

```sql
SELECT *
FROM Schueler;
```

## 6. CRUD durchführen

- INSERT
- SELECT
- UPDATE
- DELETE

## 7. Beenden

```text
.quit
```

## Hilfe

Ausführliche Anleitung:

```text
Werkzeuge/SQLite/
```
