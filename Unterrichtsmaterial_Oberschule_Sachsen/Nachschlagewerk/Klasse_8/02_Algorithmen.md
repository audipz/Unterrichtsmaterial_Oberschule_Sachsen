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

Eine **Wiederholung** oder **Schleife** führt eine oder mehrere Anweisungen mehrfach aus. Welche Schleifenart geeignet ist, hängt davon ab, wann geprüft wird und ob die Anzahl der Wiederholungen vorher bekannt ist.

#### Kopfgesteuerte Schleife

Bei einer **kopfgesteuerten Schleife** wird die Bedingung **vor** jedem Schleifendurchlauf geprüft.

```text
SOLANGE Bedingung gilt
    wiederhole Anweisung
```

Ist die Bedingung bereits am Anfang falsch, wird die Schleife **kein einziges Mal** ausgeführt.

Beispiel:

```text
SOLANGE akku > 20
    sende Messwert
```

#### Fußgesteuerte Schleife

Bei einer **fußgesteuerten Schleife** wird die Bedingung **nach** dem Schleifendurchlauf geprüft. Deshalb wird der Schleifenrumpf mindestens einmal ausgeführt.

```text
WIEDERHOLE
    Anweisung
SOLANGE Bedingung gilt
```

Beispiel:

```text
WIEDERHOLE
    frage Passwort ab
SOLANGE Passwort falsch ist
```

Die Abfrage findet mindestens einmal statt.

#### Zählschleife

Eine **Zählschleife** verwendet man, wenn die Anzahl der Wiederholungen vorher bekannt ist.

```text
WIEDERHOLE 5 MAL
    Anweisung
```

oder in einer Programmiersprache sinngemäß:

```text
FÜR i VON 1 BIS 5
    Anweisung
```

Beispiel: Eine Spielfigur soll genau zehn Schritte vorwärtsgehen.

#### Bedingte Wiederholung

Bei einer **bedingten Wiederholung** ist die Anzahl der Durchläufe vorher nicht unbedingt bekannt. Die Schleife endet erst, wenn eine bestimmte Bedingung erfüllt oder nicht mehr erfüllt ist.

Beispiele:

```text
SOLANGE feldFrei
    geheVor()
```

oder

```text
WIEDERHOLE
    würfle
BIS eine 6 fällt
```

#### Endlosschleife

Eine **Endlosschleife** besitzt keine wirksame Abbruchbedingung und läuft deshalb unbegrenzt weiter, solange das Programm nicht von außen beendet wird.

```text
WIEDERHOLE IMMER
    prüfe Sensor
```

Endlosschleifen können **beabsichtigt** sein, zum Beispiel bei einem eingebetteten System, das dauerhaft auf Sensordaten reagieren soll. Sie können aber auch **unbeabsichtigt** entstehen, wenn sich eine Abbruchbedingung niemals ändert.

Beispiel für einen Fehler:

```text
zahl = 1
SOLANGE zahl < 10
    zeige zahl
```

Wenn `zahl` innerhalb der Schleife nie verändert wird, bleibt die Bedingung immer wahr. Die Schleife endet nicht.

#### Vergleich der Schleifenarten

| Schleifenart | Prüfung | Mindestens ein Durchlauf? | Typischer Einsatz |
|---|---|---:|---|
| kopfgesteuert | vor dem Durchlauf | nein | solange eine Bedingung gilt |
| fußgesteuert | nach dem Durchlauf | ja | etwas mindestens einmal ausführen |
| Zählschleife | über Zähler/Anzahl | abhängig von der Anzahl | bekannte Wiederholungszahl |
| bedingte Wiederholung | über Bedingung | abhängig von der Form | unbekannte Wiederholungszahl |
| Endlosschleife | keine wirksame Beendigung | ja | dauerhafte Prozesse oder Programmfehler |

> **Merke:** Kopfgesteuert bedeutet „erst prüfen, dann ausführen“. Fußgesteuert bedeutet „erst ausführen, dann prüfen“. Eine Zählschleife ist sinnvoll, wenn die Anzahl der Wiederholungen feststeht.

## Verschachtelungen

Grundstrukturen können **ineinander verschachtelt** werden. Das bedeutet: Innerhalb einer Auswahl oder Schleife befindet sich eine weitere Auswahl oder Schleife.

### Verschachtelte Bedingungen

Eine Bedingung kann innerhalb einer anderen Bedingung stehen.

```text
WENN alter >= 18
    DANN
        WENN fuehrerscheinVorhanden
            DANN zeige "Fahren erlaubt"
        SONST
            zeige "Kein Führerschein"
SONST
    zeige "Noch nicht volljährig"
```

Hier wird die zweite Bedingung nur geprüft, wenn die erste Bedingung wahr ist.

### Bedingung innerhalb einer Schleife

In einer Schleife kann bei jedem Durchlauf geprüft werden, ob eine bestimmte Situation eingetreten ist.

```text
FÜR i VON 1 BIS 10
    WENN i ist gerade
        DANN zeige i
```

Die Schleife läuft zehnmal. Die Ausgabe erfolgt aber nur bei geraden Zahlen.

### Verschachtelte Schleifen

Auch Schleifen können ineinander liegen. Bei jedem Durchlauf der äußeren Schleife wird die innere Schleife vollständig ausgeführt.

```text
FÜR zeile VON 1 BIS 3
    FÜR spalte VON 1 BIS 4
        setze Punkt
```

Die innere Schleife läuft viermal für jede der drei Zeilen. Insgesamt wird `setze Punkt` also `3 · 4 = 12` Mal ausgeführt.

Verschachtelte Schleifen werden häufig bei Gittern, Tabellen, Bildern oder Spielfeldern verwendet.

### Verschachtelungen übersichtlich halten

Mit jeder weiteren Verschachtelung wird ein Algorithmus schwieriger zu lesen. Deshalb sollten Einrückungen konsequent verwendet und unnötig tiefe Verschachtelungen vermieden werden.

> **Merke:** Bei verschachtelten Schleifen wird die innere Schleife für jeden Durchlauf der äußeren Schleife erneut vollständig ausgeführt.

## Bedingungen

Eine Bedingung kann wahr oder falsch sein. Beispiele sind `zahl > 10`, `tuerOffen` oder `farbe == rot`. Bedingungen ermöglichen Entscheidungen und steuern Wiederholungen.

Bei Schleifen ist besonders wichtig, dass sich die für die Bedingung verwendeten Werte sinnvoll verändern. Sonst kann eine unbeabsichtigte Endlosschleife entstehen.

## Variablen

Eine **Variable** besitzt einen Namen und speichert während der Ausführung einen Wert. Dieser Wert kann sich ändern.

```text
punkte = 0
punkte = punkte + 1
```

Variablen werden häufig als **Zähler** in Schleifen verwendet. Ein Zähler merkt sich beispielsweise, wie viele Durchläufe bereits ausgeführt wurden.

## Algorithmen darstellen

Algorithmen können als Text, Pseudocode, Struktogramm, Ablaufdiagramm oder Programmcode dargestellt werden. Eine gute Darstellung macht Reihenfolge, Entscheidungen und Wiederholungen eindeutig erkennbar.

## Testen

Ein Algorithmus sollte mit unterschiedlichen Eingaben getestet werden. Besonders wichtig sind Grenzfälle und ungewöhnliche Situationen. Ein einzelnes erfolgreiches Beispiel beweist noch nicht, dass ein Algorithmus immer korrekt arbeitet.

Bei Schleifen sollte zusätzlich geprüft werden:

- Kann die Schleife auch nullmal durchlaufen werden?
- Muss sie mindestens einmal durchlaufen werden?
- Wird die Abbruchbedingung irgendwann erreicht?
- Ist die Zahl der Wiederholungen sinnvoll begrenzt?
- Wie oft wird eine innere Schleife insgesamt ausgeführt?

> **Merke:** Programmieren bedeutet nicht nur Befehle zu schreiben. Zuerst muss klar sein, welches Verfahren das Problem löst.

## Begriffe zum Nachschlagen

**Algorithmus:** eindeutige Handlungsvorschrift zur Lösung einer Aufgabe.

**Auswahl:** Entscheidung darüber, ob eine Anweisung ausgeführt wird oder welcher von mehreren Abläufen gewählt wird.

**Bedingung:** Ausdruck mit dem Ergebnis wahr oder falsch.

**bedingte Wiederholung:** Schleife, deren Laufzeit von einer Bedingung abhängt.

**Endlosschleife:** Schleife ohne wirksame Beendigung.

**fußgesteuerte Schleife:** Schleife, deren Bedingung nach dem Schleifenrumpf geprüft wird.

**kopfgesteuerte Schleife:** Schleife, deren Bedingung vor dem Schleifenrumpf geprüft wird.

**Sequenz:** aufeinanderfolgende Ausführung von Anweisungen.

**Variable:** benannter Speicherplatz für einen veränderlichen Wert.

**Verschachtelung:** Einbetten einer Kontrollstruktur in eine andere Kontrollstruktur.

**Wiederholung/Schleife:** mehrfache Ausführung von Anweisungen.

**Zählschleife:** Schleife für eine vorher bekannte Anzahl von Wiederholungen.

**Zähler:** Variable, die beispielsweise die Anzahl von Schleifendurchläufen speichert.

→ Siehe auch **Kapitel 3: Robot Karol**.
