# Backup

Eine SQLite-Datenbank ist häufig nur eine Datei.

Trotzdem sollte sie nicht beliebig während laufender Schreibzugriffe kopiert werden.

## SQLite CLI

```text
.backup sicherung.db
```

## Wiederherstellung

Eine Sicherungsdatei kann anschließend wie jede andere SQLite-Datenbank geöffnet werden.

## Empfehlung

Vor größeren Schemaänderungen:

1. Datenbank schließen oder konsistent sichern.
2. Backup erzeugen.
3. Änderung durchführen.
4. Ergebnis prüfen.

## Merke

> Eine Änderung ohne Backup ist nur so lange harmlos, bis sie schiefgeht.
