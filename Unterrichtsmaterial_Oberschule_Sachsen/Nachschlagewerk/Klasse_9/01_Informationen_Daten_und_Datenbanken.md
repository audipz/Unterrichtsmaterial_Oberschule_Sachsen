# 1 Informationen, Daten und Datenbanken

## Von einzelnen Daten zu großen Datensammlungen

In Klasse 7 wurden Daten als Darstellung von Informationen eingeführt. In großen Informationssystemen müssen sehr viele zusammengehörige Daten so gespeichert werden, dass sie gezielt gesucht, verändert und ausgewertet werden können. Dafür werden **Datenbanken** verwendet.

## Datenbanksystem

Ein **Datenbanksystem** besteht vereinfacht aus zwei Teilen:

- der **Datenbasis**, in der die Daten strukturiert gespeichert sind,
- dem **Datenbankmanagementsystem (DBMS)**, das Zugriffe, Änderungen und Abfragen organisiert.

```text
Benutzer / Anwendung
        ↓
Datenbankmanagementsystem
        ↓
     Datenbasis
```

## Tabellen, Datensätze und Datenfelder

Relationale Datenbanken stellen Daten häufig in Tabellen dar. Eine Zeile entspricht einem **Datensatz**, eine Spalte einem **Datenfeld** beziehungsweise Attribut.

| ID | Titel | Jahr | verfügbar |
|---:|---|---:|---|
| 101 | Informatik entdecken | 2024 | ja |
| 102 | Daten verstehen | 2025 | nein |

Der Datensatz mit der ID 101 beschreibt hier ein bestimmtes Buch. `Titel`, `Jahr` und `verfügbar` sind Datenfelder.

## Datentypen

Ein **Datentyp** legt fest, welche Art von Werten in einem Feld gespeichert wird und welche Operationen sinnvoll sind.

Typische Datentypen sind:

- Text,
- ganze oder gebrochene Zahl,
- Datum/Zeit,
- Währung,
- Wahrheitswert (`wahr/falsch`).

Eine Postleitzahl wird trotz ihrer Ziffern oft besser als Text behandelt, weil mit ihr normalerweise nicht gerechnet wird und führende Nullen erhalten bleiben sollen.

## Sortieren, Filtern und Abfragen

**Sortieren** verändert die Reihenfolge der angezeigten Datensätze. **Filtern** zeigt nur Datensätze, die bestimmte Bedingungen erfüllen.

Beispiel:

```text
Zeige alle Bücher,
deren Jahr >= 2024 ist
UND die verfügbar sind.
```

Eine Datenbankabfrage kann mehrere Bedingungen verbinden und Ergebnisse anschließend sortieren oder zusammenfassen.

## Schlüssel

Ein **Primärschlüssel** identifiziert einen Datensatz eindeutig. In der Beispieltabelle eignet sich die ID dafür besser als der Titel, weil mehrere Bücher denselben Titel besitzen könnten.

Ein **Fremdschlüssel** kann auf einen Datensatz einer anderen Tabelle verweisen. Dadurch lassen sich Beziehungen zwischen Tabellen darstellen.

Diese Schlüsselbegriffe sind eine Vertiefung: Entscheidend ist zunächst das Verständnis, warum Datensätze eindeutig identifizierbar sein müssen.

## Datenqualität

Auswertungen können nur so zuverlässig sein wie die verwendeten Daten. Probleme entstehen beispielsweise durch:

- fehlende Werte,
- Tippfehler,
- doppelte Datensätze,
- unterschiedliche Schreibweisen,
- veraltete Daten,
- ungeeignete Datentypen.

> **Merke:** Eine große Datenmenge ist nicht automatisch eine gute Datenbasis. Struktur und Datenqualität sind entscheidend.

## Begriffe zum Nachschlagen

**Datenbank:** strukturierte Sammlung zusammengehöriger Daten.

**Datenbankmanagementsystem (DBMS):** Software zum Verwalten einer Datenbank.

**Datenfeld:** einzelne Eigenschaft innerhalb eines Datensatzes.

**Datensatz:** zusammengehörige Werte zu einem Objekt oder Sachverhalt.

**Datentyp:** Festlegung der Art zulässiger Werte.

**Primärschlüssel:** Merkmal, das einen Datensatz eindeutig identifiziert.

→ Vorwissen: Nachschlagewerk Klasse 7, **Informationen und Daten**.