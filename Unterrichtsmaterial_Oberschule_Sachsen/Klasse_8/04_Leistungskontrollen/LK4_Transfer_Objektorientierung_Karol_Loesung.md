# Lösung – Leistungskontrolle 4 Transfer

## Aufgabe 1

Mögliche Attribute: `position=(2|3)`, `richtung=Norden`, `hatZiegel=nein`. Mögliche Methoden: `Schritt()`, `LinksDrehen()`, `Aufheben()`.

## Aufgabe 2

Nach `Schritt` ändert sich die Position entsprechend der Blickrichtung, etwa von `(2|3)` auf `(2|4)`. `LinksDrehen` ändert die Richtung von Norden nach Westen. `Aufheben` kann den Zustand `hatZiegel` von `nein` auf `ja` ändern, sofern ein Ziegel vorhanden ist.

## Aufgabe 3

Karol läuft bis zur Wand. Auf jedem besuchten Feld prüft er, ob dort ein Ziegel liegt. Falls ja, hebt er ihn auf, danach geht er weiter. Verwendet werden eine bedingte Wiederholung und eine darin verschachtelte Bedingung.

## Aufgabe 4

Mögliche Verbesserungen: den wiederkehrenden Block in eine Wiederholung setzen oder als benanntes Unterprogramm formulieren. Vorteile: weniger Dopplung, bessere Lesbarkeit, leichtere Änderung und Wiederverwendung.

## Aufgabe 5

Mögliche Testfälle:

1. Weg ohne Ziegel → Karol stoppt vor der Wand, hat nichts eingesammelt.
2. Weg mit einem Ziegel → Karol sammelt ihn ein und stoppt vor der Wand.
3. Weg mit mehreren Ziegeln auf unterschiedlichen Feldern → alle werden eingesammelt, anschließend Halt vor der Wand.

## Punkte

30 Punkte insgesamt. Andere fachlich korrekte Attribute, Methoden und Testfälle sind zulässig.