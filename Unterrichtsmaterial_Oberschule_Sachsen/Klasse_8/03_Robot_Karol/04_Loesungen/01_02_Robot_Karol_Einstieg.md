# Lösungen – Robot Karol Einstieg

## Vorhersage

Nach `Schritt, Schritt, LinksDrehen, Schritt` steht Karol zwei Felder in ursprünglicher Richtung und danach ein Feld links davon; seine Blickrichtung ist nach der Linksdrehung um 90° verändert.

## Rechteck

Eine mögliche Lösung nutzt für jede Seite eine Wiederholung mit 4 bzw. 2 Schritten und jeweils eine Drehung.

## Rechtsdrehung

Dreimal links drehen entspricht einer Rechtsdrehung um 90°.

## Drei Ziegel

Möglich: `Hinlegen, Schritt, Hinlegen, Schritt, Hinlegen`.

## Quadrat mit Wiederholung

```text
wiederhole 4 mal
    wiederhole 4 mal
        Schritt
    endewiederhole
    LinksDrehen
endewiederhole
```

## Ziegelreihe

```text
wiederhole 5 mal
    Hinlegen
    Schritt
endewiederhole
```

Je nach gewünschter Endposition kann der letzte Schritt außerhalb der Wiederholung behandelt werden.
