# ALTER TABLE

Mit `ALTER TABLE` wird die Struktur einer bestehenden Tabelle verändert.

Typische Aufgaben:

```sql
ALTER TABLE Schueler
ADD Email VARCHAR(200);
```

Je nach Datenbanksystem sind außerdem möglich:

```sql
ALTER TABLE ...
DROP COLUMN ...
```

oder

```sql
ALTER TABLE ...
RENAME COLUMN ...
```

## Achtung

Die genaue Unterstützung einzelner `ALTER TABLE`-Varianten unterscheidet sich zwischen Datenbanksystemen.
