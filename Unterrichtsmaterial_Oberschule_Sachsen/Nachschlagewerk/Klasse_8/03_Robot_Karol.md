# 3 Robot Karol

## Programmieren in einer Modellwelt

**Robot Karol** ist eine Lernumgebung, in der ein Roboter in einer vereinfachten Welt gesteuert wird. Dadurch lassen sich grundlegende Programmierideen untersuchen, ohne dass zunächst eine umfangreiche Programmiersprache benötigt wird.

## Befehle

Karol versteht festgelegte Anweisungen. Je nach verwendeter Version gehören dazu beispielsweise Bewegungs- und Aktionsbefehle. Ein Programm besteht aus einer geordneten Folge solcher Anweisungen.

```text
Schritt
Schritt
LinksDrehen
Schritt
```

Die genaue Schreibweise muss zur eingesetzten Karol-Version passen.

## Wiederholungen

Statt denselben Befehl häufig zu kopieren, werden Wiederholungen verwendet.

```text
wiederhole 4 mal
    Schritt
endewiederhole
```

Dadurch werden Programme kürzer und leichter zu ändern.

## Bedingungen und Entscheidungen

Sensorabfragen beziehungsweise Bedingungen erlauben es Karol, auf seine Umgebung zu reagieren. Damit können beispielsweise Hindernisse erkannt und unterschiedliche Wege gewählt werden.

## Eigene Anweisungen

Wiederkehrende Abläufe können als eigene Anweisungen zusammengefasst werden. Dadurch wird ein großes Problem in kleinere Teilprobleme zerlegt.

Beispielidee:

```text
Anweisung Quadrat
    ...
EndeAnweisung
```

## Fehlersuche

Beim **Debugging** wird untersucht, warum ein Programm nicht das erwartete Ergebnis liefert. Hilfreich ist es, das Programm schrittweise auszuführen und nach jedem Schritt Welt und Roboterzustand zu prüfen.

Typische Fehler sind:

- falsche Reihenfolge von Befehlen,
- eine Wiederholung läuft zu oft oder zu selten,
- eine Bedingung prüft nicht den gewünschten Zustand,
- ein Teilproblem wurde nicht für alle Situationen bedacht.

> **Merke:** Robot Karol ist eine Modellwelt. Die dort gelernten Prinzipien wie Sequenz, Auswahl, Wiederholung und Zerlegung gelten auch in anderen Programmiersprachen.

## Begriffe zum Nachschlagen

**Debugging:** systematische Suche und Behebung von Fehlern.

**Modellwelt:** vereinfachte Umgebung zur Untersuchung bestimmter Zusammenhänge.

**Prozedur/Anweisung:** benannter, wiederverwendbarer Programmabschnitt.

→ Siehe **Kapitel 2: Algorithmen**.
