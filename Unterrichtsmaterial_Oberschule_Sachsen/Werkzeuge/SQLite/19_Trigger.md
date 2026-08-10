# Trigger in SQLite

SQLite unterstützt Trigger.

Ein Trigger führt automatisch SQL aus, wenn ein bestimmtes Ereignis eintritt.

Beispiel:

```sql
CREATE TRIGGER Beispiel
AFTER INSERT ON Schueler
BEGIN
    -- weitere SQL-Anweisungen
END;
```

## Einsatzgebiete

- Protokollierung
- automatische Folgeänderungen
- zusätzliche Prüfungen

## Hinweis

Trigger sind ein fortgeschrittenes Thema und sollten erst eingesetzt werden, wenn normale SQL-Operationen sicher beherrscht werden.
