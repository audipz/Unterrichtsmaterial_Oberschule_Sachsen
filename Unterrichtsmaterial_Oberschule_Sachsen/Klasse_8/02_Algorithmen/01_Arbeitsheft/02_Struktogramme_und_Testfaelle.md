# 02 – Algorithmen darstellen und testen

## Rückblick

Algorithmen bestehen aus Sequenzen, Entscheidungen und Wiederholungen.

> **Leitfrage:** Wie können wir einen Algorithmus so darstellen, dass andere ihn schnell verstehen und prüfen können?

## Strukturiertes Darstellen

Für Klasse 8 genügt eine klare blockartige Darstellung:

```text
START
WIEDERHOLE 5 MAL
    WENN vorne frei
        Schritt
    SONST
        links drehen
    ENDE WENN
ENDE WIEDERHOLUNG
ENDE
```

## Testfälle

Ein Testfall beschreibt:

1. Ausgangssituation,
2. erwartetes Ergebnis,
3. tatsächliches Ergebnis,
4. Bewertung.

| Testfall | Start | Erwartet | Tatsächlich | Ergebnis |
|---|---|---|---|---|
| 1 | Weg frei | 5 Schritte | | |
| 2 | Wand voraus | Drehung | | |

## Aufgabe 1

Übertrage einen Alltagsalgorithmus deiner Wahl in eine strukturierte Darstellung.

## Aufgabe 2

Erstelle mindestens drei Testfälle für einen Algorithmus, der eine Zahl prüft und bei positiven Zahlen „positiv“, bei 0 „null“ und sonst „negativ“ ausgibt.

## Aufgabe 3 – Fehler suchen

Ein Roboter soll bis zur Wand laufen:

```text
WIEDERHOLE IMMER
    Schritt
ENDE
```

Warum ist dieser Algorithmus ungeeignet? Formuliere eine bessere Variante.

## Aufgabe 4 – Verschachtelung

Erkläre in eigenen Worten, was es bedeutet, wenn eine Bedingung innerhalb einer Wiederholung liegt.

## Merke

> Ein Algorithmus wird nicht nur geschrieben, sondern mit geeigneten Testfällen systematisch überprüft.
