# NOT NULL, CHECK und DEFAULT

## NOT NULL

```sql
Name VARCHAR(100) NOT NULL
```

Wert muss vorhanden sein.

## CHECK

```sql
CHECK (Punkte >= 0)
```

Wert muss eine Bedingung erfüllen.

## DEFAULT

```sql
Status VARCHAR(20) DEFAULT 'aktiv'
```

Ohne expliziten Wert wird der Standardwert verwendet.

## Zweck

Constraints verlagern wichtige Regeln direkt in die Datenbank und verbessern die Datenqualität.
