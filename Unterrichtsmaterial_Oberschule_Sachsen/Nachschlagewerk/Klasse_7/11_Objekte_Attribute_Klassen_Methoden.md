# 11 Objekte, Attribute, Klassen und Methoden

## Warum beschreibt Informatik Dinge als Modelle?

Informatiksysteme müssen Informationen über Dinge, Personen oder Vorgänge so darstellen, dass Programme damit arbeiten können. Die Wirklichkeit ist dafür meist viel zu komplex.

Deshalb verwendet die Informatik **Modelle**. Ein Modell bildet nur diejenigen Merkmale ab, die für einen bestimmten Zweck wichtig sind.

Ein Fahrrad besitzt in der Wirklichkeit sehr viele Eigenschaften: Material jeder Schraube, Kratzer im Lack, genaue Form des Sattels und vieles mehr. Für einen Fahrradverleih reichen möglicherweise wenige Angaben:

```text
Nummer
Farbe
Rahmengröße
verfügbar
```

Für eine Werkstatt wären dagegen vielleicht Reifengröße, Bremsentyp und letzter Wartungstermin wichtiger.

> **Merke:** Ein Modell ist eine zweckbezogene Vereinfachung der Wirklichkeit. Welche Eigenschaften wichtig sind, hängt von der Aufgabe ab.

## Objekt

Ein **Objekt** ist ein konkretes einzelnes Element, über das ein Informatiksystem Informationen verwaltet.

Beispiel:

```text
Objekt: Fahrrad_17
Farbe: blau
Rahmengröße: 48 cm
verfügbar: ja
```

`Fahrrad_17` meint also ein bestimmtes Fahrrad und nicht Fahrräder allgemein.

Weitere mögliche Objekte wären:

```text
Fahrrad_18
Fahrrad_19
Fahrrad_20
```

Jedes Objekt kann eigene Werte besitzen.

## Attribute und Attributwerte

Ein **Attribut** beschreibt eine Eigenschaft, die bei einem Objekt betrachtet wird.

Der **Attributwert** ist der konkrete Wert dieser Eigenschaft bei einem bestimmten Objekt.

| Attribut | Attributwert bei Fahrrad_17 |
|---|---|
| Farbe | blau |
| Rahmengröße | 48 cm |
| verfügbar | ja |

Der Unterschied ist wichtig:

```text
Attribut:      Farbe
Attributwert:  blau
```

Ein anderes Fahrrad kann dasselbe Attribut besitzen, aber einen anderen Wert:

```text
Fahrrad_18.Farbe = rot
```

## Mehrere Objekte vergleichen

| Objekt | Farbe | Rahmengröße | verfügbar |
|---|---|---:|---|
| Fahrrad_17 | blau | 48 cm | ja |
| Fahrrad_18 | rot | 52 cm | nein |
| Fahrrad_19 | grün | 48 cm | ja |

Alle drei Fahrräder besitzen dieselben Arten von Eigenschaften. Die konkreten Werte unterscheiden sich.

Genau diese Gemeinsamkeit führt zum Begriff **Klasse**.

## Klasse

Eine **Klasse** beschreibt gemeinsame Merkmale gleichartiger Objekte. Man kann sie sich zunächst wie einen Bauplan oder eine gemeinsame Beschreibung vorstellen.

Für Fahrräder könnte eine Klasse festlegen:

```text
Klasse: Fahrrad

Attribute:
- Farbe
- Rahmengröße
- verfügbar

Methoden:
- ausleihen()
- zurückgeben()
```

![Zusammenhang von Klasse, Attributen, Methoden und einem konkreten Objekt](grafiken/objekt_klasse_attribute_methoden.svg)

Die Klasse `Fahrrad` beschreibt also, **welche Eigenschaften und Verhaltensmöglichkeiten Fahrräder im Modell besitzen**. Sie beschreibt nicht die konkreten Werte eines bestimmten Fahrrads.

## Objekt und Klasse unterscheiden

Ein häufiger Anfängerfehler ist, Klasse und Objekt gleichzusetzen.

```text
Klasse: Fahrrad
Objekt: Fahrrad_17
```

Die Klasse ist die allgemeine Beschreibung. `Fahrrad_17` ist ein konkretes Objekt nach dieser Beschreibung.

Weitere Beispiele:

| Klasse | mögliches konkretes Objekt |
|---|---|
| Schüler | Schüler_Lena |
| Buch | Buch_0042 |
| Auto | Auto_17 |
| Spielfigur | Figur_Ritter1 |

> **Merke:** Die Klasse beschreibt Gemeinsamkeiten. Das Objekt ist ein konkretes Exemplar mit konkreten Attributwerten.

## Instanz

Ein konkretes Objekt einer Klasse wird auch **Instanz** dieser Klasse genannt.

```text
Fahrrad_17 ist eine Instanz der Klasse Fahrrad.
```

Für den Einstieg kann man sich merken:

```text
Klasse → allgemeine Beschreibung
Objekt/Instanz → konkretes Exemplar
```

## Methoden

Objekte besitzen nicht nur Eigenschaften. In objektorientierten Modellen können auch mögliche Aktionen beziehungsweise Verhaltensweisen beschrieben werden. Diese heißen **Methoden**.

Für das Fahrrad im Verleihsystem könnten das sein:

```text
ausleihen()
zurückgeben()
```

Eine Methode kann Attributwerte verändern.

Vor dem Ausleihen:

```text
Fahrrad_17.verfügbar = ja
```

Nach `ausleihen()`:

```text
Fahrrad_17.verfügbar = nein
```

Die Methode beschreibt also nicht einfach nur eine Information, sondern eine mögliche Aktion im Modell.

## Attribute und Methoden unterscheiden

Eine einfache Frage hilft:

- **Wie ist das Objekt? / Welche Daten besitzt es?** → Attribut
- **Was kann mit dem Objekt geschehen beziehungsweise was kann es tun?** → Methode

Beispiel Spielfigur:

| Art | Beispiele |
|---|---|
| Attribute | Name, Lebenspunkte, Position |
| Methoden | bewegen(), springen(), SchadenNehmen() |

Beispiel Musikstück:

| Art | Beispiele |
|---|---|
| Attribute | Titel, Interpret, Dauer |
| Methoden | abspielen(), pausieren() |

Welche Methoden sinnvoll sind, hängt wieder vom Modell und dessen Zweck ab.

## Zustandsänderung

Die Gesamtheit wichtiger Attributwerte beschreibt den **Zustand** eines Objekts zu einem bestimmten Zeitpunkt.

Beispiel:

```text
Fahrrad_17
verfügbar = ja
```

Nach dem Ausleihen:

```text
Fahrrad_17
verfügbar = nein
```

Das Objekt ist weiterhin dasselbe Fahrrad, aber sein Zustand hat sich verändert.

Auch bei einer Spielfigur kann sich der Zustand ändern:

```text
Lebenspunkte: 100 → 80
Position:      (4,3) → (5,3)
```

## Datentypen von Attributen – ein erster Blick

Attributwerte können unterschiedliche Arten von Daten enthalten.

Beispiele:

| Attribut | möglicher Wert | Art des Wertes |
|---|---|---|
| Farbe | `blau` | Text |
| Rahmengröße | `48` | Zahl |
| verfügbar | `ja` | Wahrheitswert |

In Programmiersprachen werden solche Arten von Werten durch **Datentypen** genauer festgelegt. Das wird in späteren Kapiteln und Klassen vertieft.

## Ein zweites vollständiges Beispiel: Spielfigur

Für ein Computerspiel könnte eine vereinfachte Klasse so aussehen:

```text
Klasse: Spielfigur

Attribute:
- name
- lebenspunktzahl
- xPosition
- yPosition

Methoden:
- bewegen()
- springen()
- schadenNehmen()
```

Ein konkretes Objekt könnte sein:

```text
Objekt: Ritter_1

name = "Aron"
lebenspunktzahl = 100
xPosition = 5
yPosition = 3
```

Nach einer Bewegung können sich `xPosition` und `yPosition` ändern. Nach einem Treffer kann `lebenspunktzahl` sinken.

Dieses Beispiel zeigt, warum die Trennung nützlich ist: Die Klasse legt gemeinsame Merkmale fest, während viele konkrete Spielfiguren unterschiedliche Werte besitzen können.

## Beziehungen zwischen Objekten

Objekte stehen häufig miteinander in Beziehung.

Beispiele:

```text
Schüler → leiht → Buch
Fahrer → benutzt → Fahrrad
Spielfigur → besitzt → Gegenstand
```

Solche Beziehungen können in einem Modell ebenfalls wichtig sein. Wie sie genau dargestellt und gespeichert werden, wird in späteren Klassen vertieft.

## Ein Modell kann bewusst Informationen weglassen

Angenommen, eine Schulbibliothek verwaltet Bücher. Für die Ausleihe könnten wichtig sein:

```text
Titel
Inventarnummer
ausgeliehen
```

Die genaue Dicke des Papiers jeder Buchseite wäre vermutlich unnötig.

Ein Online-Buchhandel benötigt dagegen vielleicht zusätzlich:

```text
Preis
Versandgewicht
Lagerbestand
```

Dasselbe reale Buch kann also in zwei Informatiksystemen unterschiedlich modelliert werden.

## Modell und Wirklichkeit sind nicht identisch

Ein Objekt in einem Programm ist nicht automatisch das reale Ding selbst.

`Fahrrad_17` im Verleihprogramm ist eine **digitale Darstellung** des realen Fahrrads. Wenn im Programm `verfügbar = ja` gespeichert ist, das Fahrrad aber tatsächlich gestohlen wurde, stimmt das Modell nicht mehr mit der Wirklichkeit überein.

Daten müssen deshalb korrekt erfasst und bei Änderungen aktualisiert werden.

## Warum sind diese Begriffe wichtig?

Objekte, Attribute, Klassen und Methoden helfen dabei, komplexe Systeme übersichtlich zu beschreiben. Die Grundidee begegnet später unter anderem bei:

- objektorientierter Programmierung,
- Datenbanken und Datenmodellen,
- Simulationen,
- Spielen,
- grafischen Benutzeroberflächen.

In Klasse 7 steht zunächst das **Verstehen und Beschreiben** im Mittelpunkt. Wie Klassen und Objekte in einer konkreten Programmiersprache umgesetzt werden, wird später genauer betrachtet.

## Begriffe zum Nachschlagen

**Attribut:** Eigenschaft, die bei Objekten eines Modells beschrieben wird.

**Attributwert:** konkreter Wert eines Attributes bei einem bestimmten Objekt.

**Instanz:** konkretes Objekt einer Klasse.

**Klasse:** gemeinsame Beschreibung gleichartiger Objekte mit ihren Attributen und möglichen Methoden.

**Methode:** mögliche Aktion beziehungsweise Operation im Zusammenhang mit einem Objekt.

**Modell:** zweckbezogene, vereinfachte Darstellung eines Ausschnitts der Wirklichkeit.

**Objekt:** konkretes Element eines Modells mit eigenen Attributwerten und gegebenenfalls Verhalten.

**Zustand:** Gesamtheit der betrachteten Attributwerte eines Objekts zu einem bestimmten Zeitpunkt.

→ Siehe auch **Kapitel 2: Informationen und Daten**, **Kapitel 10: Darstellung von Informationen** sowie die späteren Kapitel zu **Algorithmen**, **Programmierung** und **Datenbanken**.