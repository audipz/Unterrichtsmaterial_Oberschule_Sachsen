# TRUNCATE TABLE

`TRUNCATE TABLE` entfernt alle Datensätze einer Tabelle, lässt die Tabellenstruktur jedoch bestehen.

```sql
TRUNCATE TABLE Schueler;
```

## Unterschied zu DELETE

```sql
DELETE FROM Schueler;
```

und

```sql
TRUNCATE TABLE Schueler;
```

können beide alle Datensätze entfernen, besitzen aber unterschiedliche Eigenschaften.

Typischerweise gilt:

- `DELETE` kann mit `WHERE` einzelne Datensätze löschen.
- `TRUNCATE` löscht immer den gesamten Tabelleninhalt.
- `TRUNCATE` ist in vielen Systemen für das vollständige Leeren einer Tabelle optimiert.
- Details zu Transaktionen, Triggern und Identitätsspalten sind systemabhängig.

## Besondere Rolle

`TRUNCATE TABLE` ist kein gewöhnlicher CRUD-Befehl für einzelne Datensätze.

Er ist ein administrativer bzw. struktureller Massenbefehl und sollte entsprechend vorsichtig eingesetzt werden.

## Merke

> DELETE löscht Daten gezielt. TRUNCATE leert eine Tabelle vollständig.
