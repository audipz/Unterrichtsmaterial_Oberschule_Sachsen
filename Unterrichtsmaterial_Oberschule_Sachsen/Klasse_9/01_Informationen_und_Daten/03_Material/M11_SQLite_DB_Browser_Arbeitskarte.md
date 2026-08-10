# M11 – DB Browser for SQLite – Arbeitskarte

## Ablauf

1. Datenbank öffnen oder neu anlegen.
2. Register **SQL ausführen** öffnen.
3. SQL-Anweisung eingeben.
4. Anweisung ausführen.
5. Ergebnis kontrollieren.
6. Änderungen speichern.

## Übung

Führt aus:

```sql
SELECT *
FROM Schueler;
```

Danach:

```sql
INSERT INTO Schueler
    (SchuelerID, Name)
VALUES
    (2001, 'Alex');
```

Prüft anschließend erneut:

```sql
SELECT *
FROM Schueler;
```

## Wichtig

Die grafische Oberfläche unterstützt euch beim Arbeiten.

> SQL soll trotzdem bewusst geschrieben und verstanden werden.
