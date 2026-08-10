# 03 – Bedingungen und Sensoren in Robot Karol

## Leitfrage

> Wie kann Karol auf seine Umgebung reagieren?

Ein starres Programm funktioniert nur, wenn die Welt genau wie erwartet aufgebaut ist. Bedingungen machen Programme flexibler.

## Sensoren als Fragen

Karol kann seine Umgebung prüfen. Je nach Version stehen Abfragen sinngemäß zur Verfügung, zum Beispiel:

- Ist vorne frei?
- Ist links frei?
- Liegt hier ein Ziegel?
- Ist hier eine Marke?

## Entscheidung

```text
wenn vorne frei dann
    Schritt
sonst
    LinksDrehen
endewenn
```

## Aufgabe 1

Beschreibe in Worten, was der Algorithmus macht.

## Aufgabe 2

Karol soll einen Schritt gehen, wenn vor ihm frei ist. Ist dort eine Wand, soll er sich links drehen. Formuliere den Ablauf.

## Bedingte Wiederholung

```text
solange vorne frei
    Schritt
endesolange
```

Damit kann Karol unabhängig von der Länge eines Weges bis zur Wand laufen.

## Aufgabe 3

Vergleiche feste Wiederholung und bedingte Wiederholung. Wann ist welche Variante besser?

## Aufgabe 4 – Ziegel suchen

Plane einen Algorithmus, der Karol vorwärts laufen lässt, bis er auf einem Feld mit Ziegel steht.

## Aufgabe 5 – Fehlerfall

Was passiert, wenn ein Algorithmus voraussetzt, dass irgendwann eine Wand kommt, die Welt aber unendlich bzw. sehr lang ist? Welche Rolle spielt die Endlichkeit eines Algorithmus?

## Merke

> Bedingungen und Sensoren machen Programme abhängig von der aktuellen Situation statt nur von einer vorher bekannten Welt.