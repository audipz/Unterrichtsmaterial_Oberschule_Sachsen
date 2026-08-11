# Lösung – Leistungskontrolle 3 Robot Karol

## Aufgabe 1

Karol geht zwei Schritte geradeaus, dreht sich um 90° nach links und legt auf dem aktuellen Feld einen Ziegel ab.

## Aufgabe 2

```text
wiederhole 4 mal
    Schritt
endewiederhole
LinksDrehen
```

## Aufgabe 3

```text
wenn vorne frei dann
    Schritt
sonst
    LinksDrehen
endewenn
```

## Aufgabe 4

```text
solange vorne frei
    Schritt
endesolange
```

Eine feste Wiederholung setzt voraus, dass die Entfernung zur Wand vorher bekannt ist. Die bedingte Wiederholung passt sich an unterschiedliche Welten an.

## Aufgabe 5

Beispiele: `BaueSeite` legt Ziegel entlang einer Seite; `DreheRechts` bzw. `DreheEcke` übernimmt die Richtungsänderung an einer Ecke.

## Aufgabe 6

Erwartetes Verhalten festlegen, Fehler reproduzieren, Fehlerstelle eingrenzen, gezielt ändern, erneut mit demselben und weiteren Testfällen testen.

## Punkte

30 Punkte insgesamt; funktional gleichwertige Syntaxvarianten akzeptieren.