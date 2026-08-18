---
title: "CRUD Daten aendern und loeschen"
lang: de-DE
---

# CRUD – Update und Delete

```text
Update → UPDATE
Delete → DELETE
```

---

# UPDATE

```sql
UPDATE Schueler
SET Name = 'Neu'
WHERE SchuelerID = 1;
```

---

# DELETE

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1;
```

---

# Sicherheitsregel

Erst `SELECT` mit derselben WHERE-Bedingung.

Dann ändern oder löschen.
