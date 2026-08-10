# Referenzielle Integrität

Referenzielle Integrität bedeutet:

> Ein Fremdschlüssel darf nicht auf einen nicht vorhandenen Datensatz verweisen.

Beispiel:

Wenn `Schueler.KlasseID = 5` gespeichert wird, sollte die Klasse mit `KlasseID = 5` existieren.

## Löschen referenzierter Datensätze

Mögliche Strategien sind:

- `RESTRICT`
- `CASCADE`
- `SET NULL`

Beispiel:

```sql
FOREIGN KEY (KlasseID)
REFERENCES Klasse(KlasseID)
ON DELETE SET NULL
```

Die genaue Unterstützung und Voreinstellung hängt vom Datenbanksystem ab.
