# Typische Fehler

## no such table

```text
no such table: Schueler
```

Mögliche Ursache:

- Tabelle nicht angelegt,
- falsche Datenbank geöffnet,
- Schreibfehler.

---

## no such column

```text
no such column: Klasse
```

Spaltennamen prüfen.

---

## UNIQUE constraint failed

Ein eindeutiger Wert existiert bereits.

Beispiel:

```text
UNIQUE constraint failed: Benutzer.Email
```

---

## FOREIGN KEY constraint failed

Ein Fremdschlüssel verweist auf einen ungültigen Datensatz oder eine Löschoperation verletzt eine Beziehung.

---

## database is locked

Eine andere Verbindung verwendet die Datenbank gerade für einen Schreibzugriff.

---

## syntax error

SQL-Syntax prüfen:

- Komma,
- Klammern,
- Schlüsselwörter,
- Anführungszeichen,
- Semikolon.

## Vorgehen

1. Fehlermeldung vollständig lesen.
2. kleinste betroffene SQL-Anweisung isolieren.
3. Tabellen- und Spaltennamen prüfen.
4. Schema kontrollieren.
5. Änderung einzeln testen.
