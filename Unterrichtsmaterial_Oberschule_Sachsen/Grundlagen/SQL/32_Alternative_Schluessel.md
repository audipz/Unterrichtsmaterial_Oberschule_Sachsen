# Alternative Schlüssel

Manchmal existieren mehrere Attribute, die einen Datensatz eindeutig identifizieren könnten.

Beispiel:

```text
SchuelerID
Schuelernummer
```

Beide könnten eindeutig sein.

Einer wird als Primärschlüssel gewählt.

Die übrigen geeigneten Kandidaten werden als alternative Schlüssel betrachtet.

In SQL werden alternative Schlüssel häufig mit `UNIQUE` abgesichert.

```sql
Schuelernummer VARCHAR(20) UNIQUE
```
