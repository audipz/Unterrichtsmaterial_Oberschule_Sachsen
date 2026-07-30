# Kapitel 3 – Der richtige Primärschlüssel

## Einstieg

Nicht jedes Attribut eignet sich als Primärschlüssel.

Ein guter Primärschlüssel muss jeden Datensatz eindeutig kennzeichnen.

Ob ein Merkmal geeignet ist, muss deshalb sorgfältig geprüft werden.

---

## Aufgabe 1 – Geeignet oder nicht?

Kreuzt an und begründet eure Entscheidung.

| Tabelle | Vorgeschlagener Primärschlüssel | Geeignet? |
|----------|---------------------------------|:---------:|
| Band | Name | ☐ Ja ☐ Nein |
| Band | Band-ID | ☐ Ja ☐ Nein |
| Raum | Raumname | ☐ Ja ☐ Nein |
| Buch | ISBN | ☐ Ja ☐ Nein |
| Fahrrad | Rahmennummer | ☐ Ja ☐ Nein |

Begründet eure Entscheidungen.

____________________________________________________________

____________________________________________________________

---

## Aufgabe 2 – Ein Attribut reicht nicht immer

Betrachtet die Tabelle.

| Helfer | Datum | Schicht |
|---------|--------|-----------|
| Mia | 12.06. | Vormittag |
| Mia | 12.06. | Nachmittag |
| Leon | 12.06. | Vormittag |
| Mia | 13.06. | Vormittag |

Welche Möglichkeiten gibt es?

| Merkmal | Eindeutig? |
|----------|:----------:|
| Helfer | ☐ Ja ☐ Nein |
| Datum | ☐ Ja ☐ Nein |
| Schicht | ☐ Ja ☐ Nein |
| Helfer + Datum | ☐ Ja ☐ Nein |
| Helfer + Datum + Schicht | ☐ Ja ☐ Nein |

Was fällt euch auf?

____________________________________________________________

____________________________________________________________

---

## Aufgabe 3 – Eigene Beispiele

Überlegt gemeinsam.

Wo begegnen euch im Alltag eindeutige Kennzeichnungen?

| Objekt | Kennzeichnung |
|---------|---------------|
| | |
| | |
| | |
| | |

---

## Aufgabe 4 – Nachdenken

Warum sollte ein Primärschlüssel möglichst **nicht geändert** werden?

Diskutiert.

- Welche Probleme könnten entstehen?
- Wer wäre davon betroffen?

____________________________________________________________

____________________________________________________________

____________________________________________________________

---

## Merkkasten

Ein guter Primärschlüssel

- ist eindeutig,
- kommt nur einmal vor,
- bleibt möglichst unverändert und
- identifiziert genau einen Datensatz.

Manchmal reicht **ein Attribut** aus.

Manchmal müssen **mehrere Attribute gemeinsam** verwendet werden.
Dann spricht man von einem **zusammengesetzten Primärschlüssel**.

---

## Das nehmen wir mit

Primärschlüssel sorgen dafür, dass Datensätze eindeutig erkannt werden können.

Sie bilden die Grundlage für das Verbinden von Tabellen.