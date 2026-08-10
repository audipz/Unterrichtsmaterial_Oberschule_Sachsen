# CRUD – Daten ändern und löschen

Jetzt folgen die beiden übrigen CRUD-Operationen:

- Update
- Delete

---

## Update

```sql
UPDATE Schueler
SET Name = 'Mia Müller'
WHERE SchuelerID = 1001;
```

### Wichtig

`WHERE` bestimmt, **welcher** Datensatz geändert wird.

Ohne `WHERE`:

```sql
UPDATE Schueler
SET KlasseID = 2;
```

werden alle Datensätze geändert.

---

## Delete

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1002;
```

Auch hier gilt:

Ohne `WHERE`

```sql
DELETE FROM Schueler;
```

werden alle Datensätze gelöscht.

Die Tabelle selbst bleibt bestehen.

---

## Aufgabe 1

Ändere `Lea` in `Lea Schneider`.

```sql
____________________________________________________
____________________________________________________
____________________________________________________
```

---

## Aufgabe 2

Verschiebe den Schüler mit `SchuelerID = 1001` in die Klasse mit `KlasseID = 2`.

```sql
____________________________________________________
____________________________________________________
____________________________________________________
```

---

## Aufgabe 3

Lösche den Datensatz mit `SchuelerID = 1002`.

```sql
____________________________________________________
____________________________________________________
```

---

## Sicherheitscheck

Bevor du `UPDATE` oder `DELETE` ausführst:

1. Formuliere zuerst ein passendes `SELECT`.
2. Prüfe, ob genau die gewünschten Datensätze gefunden werden.
3. Übernimm dieselbe `WHERE`-Bedingung.
4. Führe erst dann `UPDATE` oder `DELETE` aus.

Beispiel:

```sql
SELECT *
FROM Schueler
WHERE SchuelerID = 1002;
```

erst danach:

```sql
DELETE FROM Schueler
WHERE SchuelerID = 1002;
```

> 💡 **Merke:** Bei verändernden SQL-Befehlen ist eine falsche oder fehlende WHERE-Bedingung besonders gefährlich.
