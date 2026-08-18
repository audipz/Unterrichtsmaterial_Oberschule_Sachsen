# Beispiele

Algorithmen begegnen dir im Alltag und in Computerprogrammen. Entscheidend ist nicht das Thema, sondern dass die Lösung als eindeutige, ausführbare und endliche Folge von Schritten beschrieben werden kann.

## Beispiel 1 – Größere Zahl bestimmen
Eingabe: zwei Zahlen `a` und `b`.

Pseudocode:
```text
wenn a > b dann
    gib a aus
sonst
    gib b aus
ende wenn
```

Teste den Ablauf mit `(8, 3)`, `(2, 9)` und `(5, 5)`. Was fällt beim letzten Test auf? Ergänze den Algorithmus so, dass Gleichheit ausdrücklich behandelt wird.

## Beispiel 2 – Summe von 1 bis n
Für eine positive ganze Zahl `n` soll die Summe `1 + 2 + ... + n` berechnet werden.

```text
summe ← 0
für zahl von 1 bis n
    summe ← summe + zahl
ende für
gib summe aus
```

Führe den Algorithmus für `n = 4` schrittweise in einer Tabelle aus. Notiere nach jedem Schleifendurchlauf `zahl` und `summe`.

## Beispiel 3 – Eingabe prüfen
Ein Programm darf nur Werte von 1 bis 10 akzeptieren.

```text
wiederhole
    lies wert ein
solange wert < 1 oder wert > 10
gib wert aus
```

Erkläre, warum hier eine bedingte Wiederholung sinnvoller ist als eine feste Wiederholungszahl.

## Beispiel 4 – Algorithmus testen
Entwirf für Beispiel 1 mindestens drei Testfälle. Ein guter Testsatz enthält neben typischen Werten auch Rand- oder Sonderfälle.

Nutze die Tabelle:

| Eingabe | erwartetes Ergebnis | tatsächliches Ergebnis | Änderung nötig? |
|---|---|---|---|
| | | | |
| | | | |
| | | | |

## Transfer
Beschreibe einen eigenen kleinen Algorithmus zunächst in Alltagssprache und anschließend als Pseudocode oder Struktogramm. Markiere Sequenz, Auswahl und Wiederholung, soweit sie vorkommen.