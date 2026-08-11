# Lösungen – Algorithmen

## Qualitätsmerkmale

Ein geeigneter Algorithmus muss eindeutig, ausführbar und endlich sein. „Gehe ein Stück“ oder „irgendwann“ sind nicht eindeutig.

## Beispiel Sequenz

```text
WIEDERHOLE 3 MAL
    Schritt
ENDE
links drehen
WIEDERHOLE 2 MAL
    Schritt
ENDE
```

## Bis zur Wand

```text
SOLANGE vorne frei
    Schritt
ENDE
links drehen
```

## Fehleralgorithmus

Eine endlose Wiederholung von `Schritt` berücksichtigt eine Wand nicht. Der Ablauf benötigt eine Bedingung oder eine bedingte Wiederholung.

## Testfall Zahl

Mögliche Testfälle: `5 → positiv`, `0 → null`, `-3 → negativ`.

## Verschachtelung

Liegt eine Bedingung in einer Wiederholung, wird die Bedingung bei jedem Durchlauf erneut geprüft.