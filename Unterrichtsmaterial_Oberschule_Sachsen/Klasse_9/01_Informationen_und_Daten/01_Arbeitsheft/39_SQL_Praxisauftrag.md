# SQL – Praxisauftrag

## Auftrag

Erstellt eine kleine Datenbank für Schulkurse.

### Tabelle `Schueler`

- SchuelerID
- Schuelernummer
- Name

### Tabelle `Kurs`

- KursID
- Bezeichnung

### Tabelle `Teilnahme`

- SchuelerID
- KursID

---

## Anforderungen

1. Jede Tabelle besitzt einen geeigneten Primärschlüssel.
2. Schülernummern sind eindeutig.
3. `Teilnahme` verwendet Fremdschlüssel.
4. Ein Schüler darf nicht zweimal demselben Kurs zugeordnet werden.
5. Mindestens drei Schülerinnen und Schüler werden gespeichert.
6. Mindestens zwei Kurse werden gespeichert.
7. Mindestens vier Teilnahmen werden gespeichert.

---

## Teil A – Tabellen

Schreibe das benötigte `CREATE TABLE`.

---

## Teil B – Create

Füge Beispieldaten mit `INSERT` ein.

---

## Teil C – Read

Zeige alle Schülerinnen und Schüler an.

Zeige nur einen ausgewählten Schüler an.

---

## Teil D – Update

Ändere einen Namen oder eine Kursbezeichnung.

---

## Teil E – Delete

Lösche einen ausgewählten Datensatz kontrolliert.

Vorher:

```sql
SELECT ...
```

Danach:

```sql
DELETE ...
```

---

## Teil F – Beziehung

Erkläre:

> Warum braucht die Tabelle `Teilnahme` zwei Fremdschlüssel?

____________________________________________________

____________________________________________________

---

## Reflexion

Was schützt eure Datenbank vor ungültigen Daten?

____________________________________________________

Welche SQL-Anweisung war am gefährlichsten und warum?

____________________________________________________
