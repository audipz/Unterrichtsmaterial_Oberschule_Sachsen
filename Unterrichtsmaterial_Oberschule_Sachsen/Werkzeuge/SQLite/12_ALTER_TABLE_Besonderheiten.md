# ALTER TABLE – Besonderheiten

SQLite unterstützt wichtige `ALTER TABLE`-Operationen.

Beispiele:

## Tabelle umbenennen

```sql
ALTER TABLE Schueler
RENAME TO Lernende;
```

## Spalte umbenennen

```sql
ALTER TABLE Schueler
RENAME COLUMN Name TO Nachname;
```

## Spalte hinzufügen

```sql
ALTER TABLE Schueler
ADD COLUMN Email TEXT;
```

## Einschränkung

Komplexe Schemaänderungen können je nach SQLite-Version und gewünschter Änderung eine neue Tabelle erfordern.

Typischer Ablauf:

1. neue Tabelle anlegen,
2. Daten übertragen,
3. alte Tabelle löschen,
4. neue Tabelle umbenennen.

Vor solchen Änderungen immer ein Backup erstellen.
