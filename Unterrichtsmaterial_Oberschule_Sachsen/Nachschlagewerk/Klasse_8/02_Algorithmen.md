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

Eine Auswahl kann auch **mit SONST-Zweig** formuliert werden:

```text
WENN Bedingung
    DANN Anweisung A
SONST
    Anweisung B
```

> **Merke:** Ein `SONST` ist nicht bei jeder Auswahl notwendig.

### Wiederholung

Eine **Wiederholung** oder **Schleife** führt eine oder mehrere Anweisungen mehrfach aus.

#### Kopfgesteuerte Schleife

Die Bedingung wird vor jedem Durchlauf geprüft. Die Schleife kann deshalb auch kein einziges Mal ausgeführt werden.

```text
SOLANGE Bedingung gilt
    wiederhole Anweisung
```

#### Fußgesteuerte Schleife

Die Bedingung wird nach dem Schleifendurchlauf geprüft. Der Schleifenrumpf wird deshalb mindestens einmal ausgeführt.

```text
WIEDERHOLE
    Anweisung
SOLANGE Bedingung gilt
```

#### Zählschleife

Eine Zählschleife eignet sich, wenn die Anzahl der Wiederholungen vorher bekannt ist.

```text
FÜR i VON 1 BIS 5
    Anweisung
```

#### Bedingte Wiederholung und Endlosschleife

Bei einer bedingten Wiederholung hängt das Ende von einer Bedingung ab. Eine Endlosschleife besitzt keine wirksame Abbruchbedingung und läuft weiter, bis das Programm von außen beendet wird.

| Schleifenart | Prüfung | Mindestens ein Durchlauf? | Typischer Einsatz |
|---|---|---:|---|
| kopfgesteuert | vor dem Durchlauf | nein | solange eine Bedingung gilt |
| fußgesteuert | nach dem Durchlauf | ja | etwas mindestens einmal ausführen |
| Zählschleife | über Zähler/Anzahl | abhängig von der Anzahl | bekannte Wiederholungszahl |
| bedingte Wiederholung | über Bedingung | abhängig von der Form | unbekannte Wiederholungszahl |
| Endlosschleife | keine wirksame Beendigung | ja | dauerhafte Prozesse oder Programmfehler |

## Verschachtelungen

Grundstrukturen können **ineinander verschachtelt** werden.

```text
FÜR zeile VON 1 BIS 3
    FÜR spalte VON 1 BIS 4
        setze Punkt
```

Die innere Schleife läuft viermal für jede der drei Zeilen. Insgesamt wird `setze Punkt` also `3 · 4 = 12` Mal ausgeführt.

## Bedingungen und Vergleichsoperatoren

Eine **Bedingung** ist ein Ausdruck, dessen Ergebnis **wahr** oder **falsch** ist. Um Werte zu vergleichen, werden **Vergleichsoperatoren** verwendet.

Typische Schreibweisen sind:

| Bedeutung | häufige Schreibweise |
|---|---|
| gleich | `==` |
| ungleich | `!=` |
| kleiner als | `<` |
| größer als | `>` |
| kleiner oder gleich | `<=` |
| größer oder gleich | `>=` |

Beispiel:

```text
WENN punkte == 10
    DANN zeige "Ziel erreicht"
```

`punkte == 10` **vergleicht**, ob der gespeicherte Wert von `punkte` gleich 10 ist. Das Ergebnis des Vergleichs ist wahr oder falsch.

### `=`, `==` und `:=` sind nicht dasselbe

Diese Zeichen werden leicht verwechselt, können aber unterschiedliche Bedeutungen haben:

| Schreibweise | typische Bedeutung |
|---|---|
| `:=` | Zuweisung in Pseudocode: „bekommt den Wert“ |
| `=` | in vielen Programmiersprachen Zuweisung; in Mathematik Gleichheit |
| `==` | in vielen Programmiersprachen Vergleich auf Gleichheit |

Beispiel:

```text
punkte := 10
```

bedeutet in unserem Pseudocode: Speichere 10 in `punkte`.

```text
punkte == 10
```

bedeutet: Prüfe, ob `punkte` den Wert 10 besitzt.

In einer Sprache wie Python oder JavaScript wird eine Zuweisung dagegen häufig mit `=` geschrieben:

```text
punkte = 10
```

und der Vergleich mit:

```text
punkte == 10
```

> **Merke:** Ob `=`, `==` oder `:=` verwendet wird, hängt von der Sprache beziehungsweise Darstellungsform ab. Entscheidend ist, zwischen **einen Wert speichern** und **zwei Werte vergleichen** zu unterscheiden.

## Variablen

### Wozu braucht man Variablen?

Eine **Variable** ist ein benannter Speicherplatz für einen Wert, den ein Algorithmus während seiner Ausführung benötigt. Variablen speichern beispielsweise Zwischenwerte, Eingaben, Messwerte, Zähler oder Zustände.

### Zuweisung

Mit einer **Zuweisung** erhält eine Variable einen Wert. In diesem Nachschlagewerk verwenden wir im Pseudocode dafür `:=`:

```text
punkte := 0
```

Das bedeutet: **Der Variablen `punkte` wird der Wert 0 zugewiesen.**

Besonders deutlich wird die Bedeutung bei:

```text
punkte := punkte + 1
```

Rechts wird zuerst gerechnet. Anschließend wird das Ergebnis links gespeichert. Hat `punkte` vorher den Wert 4, besitzt die Variable danach den Wert 5.

### Datentypen

Variablen besitzen in vielen Programmiersprachen einen **Datentyp**. Er beschreibt, welche Art von Wert gespeichert wird und welche Operationen damit sinnvoll oder erlaubt sind.

| Datentyp | Bedeutung | Beispiel |
|---|---|---|
| Ganzzahl | ganze Zahlen | `alter := 14` |
| Gleitkommazahl/Dezimalzahl | Zahlen mit Nachkommastellen | `temperatur := 21.5` |
| Zeichen | einzelnes Zeichen | `taste := 'A'` |
| Zeichenkette/Text | Folge von Zeichen | `name := "Mia"` |
| Wahrheitswert/Boolean | wahr oder falsch | `tuerOffen := wahr` |

Datentypen helfen dabei, Werte richtig zu verarbeiten und ungeeignete Operationen zu erkennen.

### Deklaration und Initialisierung

In manchen Programmiersprachen muss eine Variable zunächst **deklariert** werden. Dabei werden beispielsweise Name und Datentyp festgelegt. Die erstmalige Vergabe eines Wertes heißt **Initialisierung**.

### Gute Variablennamen

Variablennamen sollten erkennen lassen, was gespeichert wird, beispielsweise `punkte`, `anzahlSchueler`, `maxTemperatur` oder `tuerOffen`.

> **Merke:** Eine Variable verbindet einen verständlichen Namen mit einem gespeicherten Wert. Ihr Datentyp beschreibt, welche Art von Daten dieser Wert darstellt.

## Lambda-Ausdrücke – ein Ausblick

In manchen Programmiersprachen begegnet später auch das Zeichen **λ (Lambda)** beziehungsweise der Begriff **Lambda-Ausdruck**. Ein Lambda-Ausdruck beschreibt vereinfacht eine kleine Funktion, die häufig direkt dort notiert wird, wo sie benötigt wird, ohne ihr vorher einen eigenen Funktionsnamen zu geben.

Beispielsweise kann eine Sprache sinngemäß eine Funktion „verdopple eine Zahl“ als kurzen Ausdruck darstellen. Die konkrete Schreibweise unterscheidet sich stark zwischen Programmiersprachen.

Lambda-Ausdrücke sind **nicht** dasselbe wie Zuweisungs- oder Vergleichsoperatoren und für die grundlegenden Algorithmen dieses Kapitels nicht erforderlich. Der Begriff ist hier nur als Ausblick genannt, damit das Zeichen beziehungsweise der Name später eingeordnet werden kann.

## Algorithmen darstellen

Algorithmen können als Text, Pseudocode, Struktogramm, Ablaufdiagramm oder Programmcode dargestellt werden. Eine gute Darstellung macht Reihenfolge, Entscheidungen und Wiederholungen eindeutig erkennbar.

## Testen

Ein Algorithmus sollte mit unterschiedlichen Eingaben getestet werden. Besonders wichtig sind Grenzfälle und ungewöhnliche Situationen.

> **Merke:** Programmieren bedeutet nicht nur Befehle zu schreiben. Zuerst muss klar sein, welches Verfahren das Problem löst.

## Begriffe zum Nachschlagen

**Algorithmus:** eindeutige Handlungsvorschrift zur Lösung einer Aufgabe.

**Auswahl:** Entscheidung darüber, ob eine Anweisung ausgeführt wird oder welcher von mehreren Abläufen gewählt wird.

**Bedingung:** Ausdruck mit dem Ergebnis wahr oder falsch.

**Datentyp:** Beschreibung der Art eines gespeicherten Wertes und der möglichen Operationen damit.

**Deklaration:** Bekanntmachen einer Variablen, häufig mit Name und Datentyp.

**Initialisierung:** erstmalige Vergabe eines Wertes an eine Variable.

**Lambda-Ausdruck:** kurze, häufig namenlose Funktionsbeschreibung in Programmiersprachen; die genaue Schreibweise ist sprachabhängig.

**Variable:** benannter Speicherplatz für einen Wert.

**Vergleichsoperator:** Operator zum Vergleichen von Werten; das Ergebnis ist typischerweise wahr oder falsch.

**Verschachtelung:** Einbetten einer Kontrollstruktur in eine andere Kontrollstruktur.

**Wiederholung/Schleife:** mehrfache Ausführung von Anweisungen.

**Zuweisung:** Speichern eines Wertes in einer Variablen; im Pseudocode dieses Nachschlagewerks mit `:=` dargestellt.

→ Siehe auch **Kapitel 3: Robot Karol**.
