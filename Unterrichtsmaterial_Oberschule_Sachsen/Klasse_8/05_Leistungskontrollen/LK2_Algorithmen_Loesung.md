# Lösung – Leistungskontrolle 2 Algorithmen

## Aufgabe 1

Mögliche Eigenschaften: eindeutig, ausführbar, endlich, sinnvoll geordnet, für eine Aufgabenklasse geeignet.

## Aufgabe 2

1. feste Wiederholung oder Sequenz mit fünf Schritten
2. Bedingung/Entscheidung
3. bedingte Wiederholung

## Aufgabe 3

```text
WIEDERHOLE 3 MAL
    Schritt
ENDE
LinksDrehen
SOLANGE vorne frei
    Schritt
ENDE
```

## Aufgabe 4

Die Endlosschleife berücksichtigt die Wand nicht. Besser:

```text
SOLANGE vorne frei
    Schritt
ENDE
```

## Aufgabe 5

Mögliche Testfälle:

- Start direkt vor einer Wand → kein Schritt, Programm endet.
- Start fünf Felder vor einer Wand → fünf Schritte, Halt vor der Wand.

## Punkte

30 Punkte insgesamt; äquivalente fachlich korrekte Formulierungen zulassen.