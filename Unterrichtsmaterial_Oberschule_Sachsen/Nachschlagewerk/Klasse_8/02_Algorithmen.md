# 2 Algorithmen

## Was ist ein Algorithmus?

Ein **Algorithmus** ist eine eindeutige Handlungsvorschrift zur Lösung einer Aufgabe. Er besteht aus einzelnen Schritten, die in einer festgelegten Weise ausgeführt werden.

Alltagsanleitungen können ähnlich aufgebaut sein, aber Informatikalgorithmen müssen so eindeutig formuliert sein, dass ihre Schritte zuverlässig ausgeführt werden können.

## Grundstrukturen

Viele Algorithmen lassen sich aus drei grundlegenden Strukturen zusammensetzen.

![Sequenz, Auswahl und Wiederholung als Grundstrukturen von Algorithmen](grafiken/algorithmus_strukturen.svg)

### Sequenz

Anweisungen werden nacheinander ausgeführt.

```text
Schritt A
Schritt B
Schritt C
```

### Auswahl

Bei einer **Auswahl** entscheidet eine Bedingung, ob eine Anweisung ausgeführt wird oder welcher von mehreren möglichen Wegen gewählt wird.

Eine Auswahl kann **ohne SONST-Zweig** formuliert werden:

```text
WENN Bedingung
    DANN Anweisung A
```

Ist die Bedingung wahr, wird `Anweisung A` ausgeführt. Ist sie falsch, wird diese Anweisung übersprungen und der Algorithmus läuft danach weiter.

Beispiel:

```text
WENN temperatur < 0
    DANN zeige "Frostgefahr"
```

Es gibt hier keine besondere Aktion für Temperaturen ab 0 °C.

Eine Auswahl kann auch **mit SONST-Zweig** formuliert werden:

```text
WENN Bedingung
    DANN Anweisung A
SONST
    Anweisung B
```

Ist die Bedingung wahr, wird `Anweisung A` ausgeführt. Ist sie falsch, wird stattdessen `Anweisung B` ausgeführt.

Beispiel:

```text
WENN alter >= 18
    DANN zeige "volljährig"
SONST
    zeige "minderjährig"
```

> **Merke:** Ein `SONST` ist nicht bei jeder Auswahl notwendig. Es wird gebraucht, wenn auch für den Fall „Bedingung ist falsch“ eine eigene Anweisung ausgeführt werden soll.

### Wiederholung

Anweisungen werden mehrfach ausgeführt.

```text
SOLANGE Bedingung gilt
    wiederhole Anweisung
```

## Bedingungen

Eine Bedingung kann wahr oder falsch sein. Beispiele sind `zahl > 10`, `tuerOffen` oder `farbe == rot`. Bedingungen ermöglichen Entscheidungen und steuern Wiederholungen.

## Variablen

Eine **Variable** besitzt einen Namen und speichert während der Ausführung einen Wert. Dieser Wert kann sich ändern.

```text
punkte = 0
punkte = punkte + 1
```

## Algorithmen darstellen

Algorithmen können als Text, Pseudocode, Struktogramm, Ablaufdiagramm oder Programmcode dargestellt werden. Eine gute Darstellung macht Reihenfolge, Entscheidungen und Wiederholungen eindeutig erkennbar.

## Testen

Ein Algorithmus sollte mit unterschiedlichen Eingaben getestet werden. Besonders wichtig sind Grenzfälle und ungewöhnliche Situationen. Ein einzelnes erfolgreiches Beispiel beweist noch nicht, dass ein Algorithmus immer korrekt arbeitet.

> **Merke:** Programmieren bedeutet nicht nur Befehle zu schreiben. Zuerst muss klar sein, welches Verfahren das Problem löst.

## Begriffe zum Nachschlagen

**Algorithmus:** eindeutige Handlungsvorschrift zur Lösung einer Aufgabe.

**Auswahl:** Entscheidung darüber, ob eine Anweisung ausgeführt wird oder welcher von mehreren Abläufen gewählt wird.

**Bedingung:** Ausdruck mit dem Ergebnis wahr oder falsch.

**Sequenz:** aufeinanderfolgende Ausführung von Anweisungen.

**Variable:** benannter Speicherplatz für einen veränderlichen Wert.

**Wiederholung/Schleife:** mehrfache Ausführung von Anweisungen.

→ Siehe auch **Kapitel 3: Robot Karol**.
