# 3 Robot Karol

## Programmieren in einer Modellwelt

**Robot Karol** ist eine Lernumgebung für den Einstieg in die Programmierung. Karol bewegt sich in einer vereinfachten Welt aus Feldern. Er kann sich bewegen, sich drehen, Ziegel hinlegen oder aufnehmen und Marken setzen. Außerdem kann er bestimmte Eigenschaften seiner Umgebung abfragen, beispielsweise ob vor ihm eine Wand steht.

Die Modellwelt ist absichtlich überschaubar. Dadurch kann man sich auf grundlegende Ideen der Programmierung konzentrieren: **Sequenzen, Wiederholungen, Bedingungen, Auswahlen, eigene Anweisungen und das systematische Testen von Programmen**.

Ein Karol-Programm besteht aus eindeutigen Anweisungen. Karol führt sie in der festgelegten Reihenfolge aus. Dadurch wird ein zuvor geplanter Algorithmus zu einem ausführbaren Programm.

> **Merke:** Robot Karol ist nicht nur eine Figur, die Befehle ausführt. Die Karol-Welt ist ein Modell, an dem sich grundlegende Prinzipien der Programmierung beobachten und ausprobieren lassen.

## Programm und Welt

In einer typischen Robot-Karol-Umgebung gibt es einen **Editor** für den Programmtext und eine Darstellung von **Karols Welt**. Im Editor wird das Programm geschrieben. Beim Ausführen kann beobachtet werden, wie Karol die Anweisungen Schritt für Schritt umsetzt.

Für die Fehlersuche ist besonders die **Einzelschrittausführung** hilfreich: Dabei wird immer nur eine Anweisung ausgeführt. So lässt sich genau verfolgen, an welcher Stelle sich Karol anders verhält als erwartet.

Je nach eingesetzter Robot-Karol-Version können Oberfläche und Schreibweise etwas unterschiedlich aussehen. Die grundlegenden Programmierideen bleiben jedoch gleich.

## Grundbefehle

Karol besitzt vordefinierte Anweisungen. Zu den häufig verwendeten gehören:

| Anweisung | Wirkung |
|---|---|
| `Schritt` | Karol geht ein Feld in Blickrichtung vorwärts. |
| `LinksDrehen` | Karol dreht sich um 90° nach links. |
| `RechtsDrehen` | Karol dreht sich um 90° nach rechts. |
| `Hinlegen` | Karol legt vor sich einen Ziegel hin. |
| `Aufheben` | Karol hebt einen Ziegel vor sich auf. |
| `MarkeSetzen` | Karol setzt auf seinem Feld eine Marke. |
| `MarkeLöschen` | Karol entfernt eine Marke auf seinem Feld. |

Nicht jede Anweisung kann in jeder Situation erfolgreich ausgeführt werden. Steht beispielsweise direkt vor Karol eine Wand, kann er nicht einfach mit `Schritt` hindurchgehen.

### Eine einfache Sequenz

Mehrere Anweisungen hintereinander bilden eine **Sequenz**:

```text
Schritt
Schritt
LinksDrehen
Schritt
MarkeSetzen
```

Karol geht zunächst zwei Felder vorwärts, dreht sich nach links, geht ein weiteres Feld und setzt dort eine Marke.

Die Reihenfolge ist wichtig. Werden zwei Anweisungen vertauscht, kann ein völlig anderer Weg entstehen.

## Parameter

Einige Karol-Versionen erlauben bei bestimmten Anweisungen einen **Parameter**. Ein Parameter übergibt einer Anweisung einen zusätzlichen Wert.

Statt beispielsweise dreimal hintereinander

```text
Schritt
Schritt
Schritt
```

zu schreiben, kann je nach Version auch folgende Kurzform möglich sein:

```text
Schritt(3)
```

Der Wert `3` ist hier der Parameter. Er legt fest, wie oft die Aktion ausgeführt werden soll. Entsprechende Parameter können beispielsweise auch bei `Hinlegen` oder `Aufheben` vorkommen.

> **Merke:** Ein Parameter macht eine Anweisung genauer. Er liefert beim Aufruf einen Wert, den die Anweisung verwendet.

## Wiederholungen

Wenn derselbe Ablauf mehrfach ausgeführt werden soll, ist es meist ungünstig, identische Anweisungen immer wieder zu kopieren. Dafür gibt es **Wiederholungen oder Schleifen**.

### Wiederholung mit fester Anzahl

Ist vorher bekannt, wie oft etwas wiederholt werden soll, kann eine gezählte Wiederholung verwendet werden.

```text
wiederhole 4 mal
    Schritt
endewiederhole
```

Karol geht vier Schritte vorwärts.

Ein Quadrat lässt sich beispielsweise so beschreiben:

```text
wiederhole 4 mal
    wiederhole 3 mal
        Schritt
    endewiederhole
    LinksDrehen
endewiederhole
```

Die innere Wiederholung erzeugt eine Seite mit drei Schritten. Die äußere Wiederholung sorgt dafür, dass vier Seiten durchlaufen werden.

Hier sind also zwei Schleifen **verschachtelt**.

### Bedingte Wiederholung

Manchmal kennt man die notwendige Anzahl der Schritte vorher nicht. Karol soll beispielsweise so lange laufen, bis er eine Wand erreicht. Dann wird eine Bedingung verwendet:

```text
wiederhole solange NichtIstWand
    Schritt
endewiederhole
```

Vor jedem Durchlauf wird geprüft, ob vor Karol **keine Wand** steht. Solange `NichtIstWand` wahr ist, geht Karol weiter.

Diese Form ist besonders wichtig, wenn ein Programm unabhängig von der genauen Größe oder Startposition in einer Welt funktionieren soll.

## Bedingungen – Karol untersucht seine Welt

Eine **Bedingung** liefert einen Wahrheitswert: **wahr** oder **falsch**. Man kann sich Bedingungen wie einfache Sensorfragen vorstellen, die Karol über seine Umgebung beantworten kann.

Häufig verwendete Bedingungen sind beispielsweise:

| Bedingung | wahr, wenn ... |
|---|---|
| `IstWand` | vor Karol eine Wand beziehungsweise ein entsprechendes Hindernis liegt |
| `NichtIstWand` | vor Karol keine Wand liegt |
| `IstZiegel` | Karol vor einem Ziegel steht |
| `NichtIstZiegel` | vor Karol kein Ziegel liegt |
| `IstMarke` | Karol auf einer Marke steht |
| `NichtIstMarke` | Karol nicht auf einer Marke steht |
| `IstNorden` | Karol nach Norden schaut |
| `IstOsten` | Karol nach Osten schaut |
| `IstSüden` | Karol nach Süden schaut |
| `IstWesten` | Karol nach Westen schaut |

Je nach Robot-Karol-Version stehen weitere Bedingungen und Varianten mit Parametern zur Verfügung.

## Auswahl mit WENN

Mit einer Bedingung kann Karol entscheiden, ob eine Anweisung ausgeführt wird.

### Auswahl ohne SONST

```text
wenn IstZiegel dann
    Aufheben
endewenn
```

Nur wenn vor Karol ein Ziegel liegt, wird `Aufheben` ausgeführt. Ist die Bedingung falsch, wird der Block übersprungen.

### Auswahl mit SONST

Soll Karol für beide Fälle unterschiedlich reagieren, wird ein `SONST`-Zweig verwendet:

```text
wenn IstWand dann
    LinksDrehen
sonst
    Schritt
endewenn
```

Steht Karol vor einer Wand, dreht er sich nach links. Andernfalls geht er einen Schritt vorwärts.

Damit wird die abstrakte Auswahl aus **Kapitel 2: Algorithmen** in einem konkreten Programm sichtbar.

## Bedingungen und Schleifen kombinieren

Besonders nützlich werden Kontrollstrukturen, wenn sie kombiniert werden. Karol kann beispielsweise bis zu einer Wand laufen und dabei auf jedem Feld eine Marke setzen:

```text
wiederhole solange NichtIstWand
    MarkeSetzen
    Schritt
endewiederhole
MarkeSetzen
```

Die letzte Anweisung nach der Schleife ist wichtig: Ohne sie würde auf dem Feld direkt vor der Wand keine Marke gesetzt, weil die Schleife dort bereits beendet wird.

Dieses Beispiel zeigt auch, warum Programme getestet werden müssen. Man sollte genau überlegen, was **vor dem ersten**, **während jedes** und **nach dem letzten Schleifendurchlauf** geschieht.

## Verschachtelte Entscheidungen

Kontrollstrukturen können ineinander liegen. Karol soll beispielsweise vorwärtsgehen, wenn der Weg frei ist. Steht eine Wand vor ihm, soll er prüfen, ob er nach links ausweichen kann:

```text
wenn NichtIstWand dann
    Schritt
sonst
    LinksDrehen
    wenn NichtIstWand dann
        Schritt
    endewenn
endewenn
```

Die zweite Auswahl befindet sich innerhalb des `SONST`-Zweiges der ersten Auswahl. Man spricht von einer **Verschachtelung**.

Bei vielen Verschachtelungen ist eine saubere Einrückung besonders wichtig, damit erkennbar bleibt, welcher Block zu welcher Bedingung gehört.

## Eigene Anweisungen

Wiederkehrende Abläufe können als **eigene Anweisungen** zusammengefasst werden. Dadurch wird ein größeres Problem in verständliche Teilprobleme zerlegt.

Eine Anweisung zum Umdrehen kann beispielsweise so definiert werden:

```text
Anweisung Umdrehen
    LinksDrehen
    LinksDrehen
EndeAnweisung
```

Danach kann sie im Programm wie ein neuer Befehl verwendet werden:

```text
Schritt
Umdrehen
Schritt
```

Statt überall zweimal `LinksDrehen` zu schreiben, beschreibt der Name `Umdrehen` direkt die Absicht.

Eigene Anweisungen haben mehrere Vorteile:

- häufig benötigter Code muss nur einmal geschrieben werden,
- Programme werden kürzer und übersichtlicher,
- verständliche Namen erklären, was ein Programmteil bewirkt,
- Änderungen müssen nur an einer Stelle vorgenommen werden,
- große Probleme können in kleinere Teilprobleme zerlegt werden.

> **Merke:** Eine gute eigene Anweisung beschreibt eine zusammengehörige Teilaufgabe und erhält einen Namen, der ihre Bedeutung erkennen lässt.

## Ein größeres Beispiel

Karol soll viermal jeweils drei Schritte gehen, eine Marke setzen und sich nach links drehen. Dafür kann zunächst eine eigene Anweisung für eine Seite definiert werden:

```text
Anweisung Seite
    wiederhole 3 mal
        Schritt
    endewiederhole
    MarkeSetzen
    LinksDrehen
EndeAnweisung

wiederhole 4 mal
    Seite
endewiederhole
```

Das Beispiel verbindet mehrere Konzepte:

- `Schritt` und `MarkeSetzen` sind Grundanweisungen,
- `wiederhole 3 mal` ist eine Zählschleife,
- `Seite` ist eine selbst definierte Anweisung,
- `wiederhole 4 mal` verwendet diese Teilaufgabe mehrfach.

Ein längerer Algorithmus wird dadurch in kleinere, verständliche Bausteine gegliedert.

## Kommentare

In Varianten von Robot Karol können Kommentare verwendet werden, um Programmtext für Menschen zu erklären. Eine verbreitete Schreibweise ist beispielsweise:

```text
// bis zur Wand laufen
wiederhole solange NichtIstWand
    Schritt
endewiederhole
```

Der Kommentar wird nicht als Bewegung von Karol ausgeführt. Er dokumentiert, **warum** oder **wozu** ein Programmabschnitt existiert.

Kommentare sollten vor allem zusätzliche Informationen liefern. Ein Kommentar wie `// Schritt` direkt vor `Schritt` erklärt dagegen kaum etwas Neues.

## Programme testen und Fehler finden

Auch ein syntaktisch korrektes Karol-Programm kann logisch falsch sein. Karol kann beispielsweise an der falschen Stelle abbiegen, eine Marke vergessen oder eine Schleife einmal zu früh beenden.

Beim **Debugging** wird untersucht, warum das tatsächliche Verhalten vom erwarteten Verhalten abweicht.

Hilfreich ist folgende Vorgehensweise:

1. Lege fest, was Karol am Ende erreichen soll.
2. Prüfe Ausgangsposition, Blickrichtung und Aufbau der Welt.
3. Führe das Programm möglichst im Einzelschritt aus.
4. Beobachte nach jeder wichtigen Anweisung Position und Blickrichtung von Karol sowie Ziegel und Marken.
5. Suche die erste Stelle, an der tatsächlicher und erwarteter Ablauf voneinander abweichen.
6. Korrigiere die Ursache und teste erneut.

Typische Fehler sind:

- falsche Reihenfolge von Anweisungen,
- falsche Drehrichtung,
- eine Wiederholung läuft einmal zu oft oder zu wenig,
- eine Bedingung ist umgekehrt formuliert,
- eine Schleife besitzt keine geeignete Abbruchbedingung,
- ein Sonderfall wurde nicht berücksichtigt,
- eine eigene Anweisung funktioniert nur für eine bestimmte Ausgangssituation.

### Unterschiedliche Welten als Testfälle

Ein gutes Karol-Programm sollte – sofern die Aufgabe dies verlangt – nicht nur in genau einer vorbereiteten Welt funktionieren. Für einen Algorithmus „Gehe bis zur Wand“ kann man beispielsweise testen:

- Wand direkt vor Karol,
- Wand nach genau einem Schritt,
- Wand erst nach vielen Schritten,
- unterschiedliche Startpositionen.

Damit werden die Ideen aus **Kapitel 2: Algorithmen und Programme testen** unmittelbar auf Robot Karol übertragen.

## Vom Karol-Programm zu allgemeinen Programmierideen

Die Karol-Sprache ist klein, aber die erlernten Konzepte kommen auch in anderen Programmiersprachen vor:

| Robot Karol | Allgemeines Konzept |
|---|---|
| mehrere Befehle nacheinander | Sequenz |
| `wiederhole ... mal` | Zählschleife |
| `wiederhole solange ...` | bedingte Schleife |
| `wenn ... dann` | einseitige Auswahl |
| `wenn ... dann ... sonst` | zweiseitige Auswahl |
| `IstWand`, `IstMarke` usw. | Bedingungen mit wahr/falsch als Ergebnis |
| eigene `Anweisung` | Prozedur beziehungsweise wiederverwendbarer Programmbaustein |
| Einzelschrittausführung | Hilfsmittel zum Debugging |

Deshalb ist Robot Karol kein Selbstzweck. Die gleichen Denkweisen werden später beispielsweise in Python, JavaScript oder anderen Programmiersprachen benötigt.

## Weiterlernen und Nachschlagen

Für zusätzliche Erklärungen und Beispiele eignet sich der frei zugängliche **Robot-Karol-Lernpfad von ZUM-Unterrichten**. Dort gibt es unter anderem Seiten zur Einführung, zu ersten Programmen, selbst definierten Methoden, Schleifen und Verzweigungen:

<https://unterrichten.zum.de/wiki/Robot_Karol>

Eine weitere Möglichkeit ist **Robot Karol Online**. Diese moderne Variante unterstützt neben Robot-Karol-Code auch andere Darstellungs- beziehungsweise Programmiersprachen und ermöglicht den direkten Vergleich grundlegender Programmierkonzepte:

<https://github.com/Entkenntnis/robot-karol-online>

> **Hinweis:** Online-Angebote können sich verändern. Für die konkrete Schreibweise der Befehle ist immer auch die im Unterricht eingesetzte Robot-Karol-Version maßgeblich.

## Begriffe zum Nachschlagen

**Bedingung:** Abfrage mit dem Ergebnis wahr oder falsch, beispielsweise `IstWand`.

**Debugging:** systematische Suche und Behebung von Fehlern in einem Programm.

**Einzelschrittausführung:** Ausführungsart, bei der jeweils nur eine Programmanweisung ausgeführt wird, damit der Ablauf genau beobachtet werden kann.

**Karol-Welt:** vereinfachte Modellumgebung, in der sich Karol bewegt und mit Wänden, Ziegeln und Marken interagiert.

**Modellwelt:** vereinfachte Umgebung zur Untersuchung bestimmter Zusammenhänge.

**Parameter:** zusätzlicher Wert, der beim Aufruf einer Anweisung übergeben wird und deren Verhalten genauer festlegt.

**Prozedur/eigene Anweisung:** benannter, wiederverwendbarer Programmabschnitt für eine zusammengehörige Teilaufgabe.

**Sequenz:** Folge von Anweisungen, die nacheinander ausgeführt werden.

**Verschachtelung:** Einbetten einer Kontrollstruktur in eine andere.

→ Zu **Sequenz, Auswahl, Wiederholung, Bedingungen, Verschachtelungen und Testverfahren** siehe **Kapitel 2: Algorithmen**.
