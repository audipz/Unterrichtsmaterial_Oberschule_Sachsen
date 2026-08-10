# 02 – Methoden und Zustände

## Rückblick

Objekte besitzen Attribute und konkrete Attributwerte. Klassen beschreiben gemeinsame Eigenschaften gleichartiger Objekte.

> **Leitfrage:** Wie können Objekte ihren Zustand verändern?

## Zustand eines Objekts

Die Gesamtheit der aktuellen Attributwerte beschreibt den **Zustand** eines Objekts.

Beispiel `Ampel_A`:

| Attribut | Wert |
|---|---|
| farbe | rot |
| aktiv | ja |

Ändert sich `farbe` von `rot` auf `grün`, ändert sich der Zustand.

## Methoden

Eine **Methode** beschreibt eine Aktion, die ein Objekt ausführen kann.

Beispiel Klasse `Lampe`:

- Attribute: `farbe`, `helligkeit`, `eingeschaltet`
- Methoden: `einschalten()`, `ausschalten()`, `heller()`, `dunkler()`

Die Methode `einschalten()` kann beispielsweise den Attributwert `eingeschaltet` von `nein` auf `ja` ändern.

## Aufgabe 1

Ergänze für die Klasse `Roboter` mindestens vier Attribute und fünf Methoden.

## Aufgabe 2

Das Objekt `Tuer_1` besitzt:

- `offen = nein`
- `verschlossen = ja`

Welche Zustandsänderungen könnten die Methoden `aufschliessen()` und `oeffnen()` bewirken?

## Aufgabe 3 – Objektkarte

Erstelle eine vollständige Objektkarte für eine Spielfigur:

- Klassenname
- Objektname
- mindestens fünf Attribute mit Attributwerten
- mindestens vier Methoden

## Aufgabe 4 – Reihenfolge

Kann `Tuer_1.oeffnen()` sinnvoll ausgeführt werden, solange `verschlossen = ja` gilt? Formuliere eine mögliche Bedingung.

## Verbindung zu Algorithmen

Methoden bestehen in Programmen wiederum aus Anweisungen. Damit entsteht die Brücke zu Algorithmen: Ein Objekt führt einen genau beschriebenen Ablauf aus.

## Merksatz

> Methoden beschreiben Verhalten. Durch Methoden können sich Attributwerte und damit der Zustand eines Objekts ändern.
