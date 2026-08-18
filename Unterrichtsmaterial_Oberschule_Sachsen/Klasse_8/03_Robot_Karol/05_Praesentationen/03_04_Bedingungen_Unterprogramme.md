# Präsentation – Robot Karol: Bedingungen und Unterprogramme

## Folie 1 – Leitfrage
Wie kann Karol auf unterschiedliche Situationen reagieren und Programmteile wiederverwenden?

> Layout-Hinweis: Leitfragenkasten frei halten; Grafik darunter.

## Folie 2 – Sensorfragen als Bedingungen
- Sensorfragen liefern Informationen über Karols Umgebung.
- Die hier verwendeten Sensorfragen liefern **WAHR oder FALSCH (JA oder NEIN)**.
- Dadurch können sie direkt als Bedingungen verwendet werden.

Lehrerhinweis: `if` wertet Bedingungen wahr/falsch aus. Mehrfachauswahlen wie `switch/case` sind andere Auswahlstrukturen und werden hier nicht benötigt.

## Folie 3 – WENN – DANN – SONST
Beispiel:
```text
wenn IstWand dann
    LinksDrehen
sonst
    Schritt
*wenn
```
Daneben ein passendes Struktogramm.

## Folie 4 – Feste vs. bedingte Wiederholung
Fest: „Gehe genau 5 Felder.“
```text
wiederhole 5 mal
    Schritt
*wiederhole
```

Bedingt: „Gehe bis zur Wand.“
```text
solange NichtIstWand
    Schritt
*solange
```

Merksatz: **Fest = Wie oft? · Bedingt = Wie lange?**

## Folie 5 – Verschachtelung
- Schleife in Schleife
- Bedingung in Schleife
- mehrere Bedingungen und Schleifen können verschachtelt werden
- Einrückung zeigt Zugehörigkeiten

Beispielidee: 4 × 4-Ziegelfeld mit innerer Schleife für eine Reihe und äußerer Schleife für mehrere Reihen.

## Folie 6 – Wie machen wir Programme übersichtlich?
Schlecht eingerückten und sauber eingerückten Karol-Code vergleichen.

Beispiel:
```text
solange NichtIstWand
    wenn IstZiegel
        Aufheben
    sonst
        Schritt
    *wenn
*solange
```

## Folie 7 – Teilprobleme bilden – konkret
Aufgabe: drei Reihen mit je vier Ziegeln bauen.
- Teilproblem 1: eine Reihe bauen
- Teilproblem 2: zur nächsten Reihe wechseln
- Teilproblem 3: Teilprobleme passend wiederholen

Lehrerhinweis: Fachbegriff **Dekomposition** kann ergänzend genannt werden.

## Folie 8 – Eigene Anweisungen definieren
Eine Befehlsfolge erhält einen Namen.
```text
Anweisung ReiheBauen
    wiederhole 4 mal
        Hinlegen
        Schritt
    *wiederhole
*Anweisung
```

## Folie 9 – Definition ≠ Aufruf
- Definition beschreibt den Ablauf.
- Definition wird nicht automatisch ausgeführt.
- Erst ein Aufruf führt den Ablauf aus.

Beispiel:
```text
ReiheBauen
LinksDrehen
ReiheBauen
```

Frage: Wie viele Ziegel werden gelegt, wenn `ReiheBauen` nur definiert, aber nie aufgerufen wird? → **keine**.

## Folie 10 – Wiederverwendbarkeit
- einmal definieren – mehrfach aufrufen
- weniger kopierter Code
- Änderungen nur an einer Stelle
- Teilaufgaben separat testbar

## Folie 11 – Bekannten Debugging-Zyklus anwenden
Nicht erneut vollständig einführen, sondern auf die neue Aufgabe anwenden:
**Beobachten → Eingrenzen → Ändern → Testen**

Beispiel: In der zweiten Reihe fehlt ein Ziegel. Welche eigene Anweisung würdest du zuerst prüfen?

## Folie 12 – Sicherung
Schüler erklären an einem Codebeispiel:
- Bedingung
- feste/bedingte Wiederholung
- Verschachtelung
- Definition
- Aufruf
- Wiederverwendung

## Quellen
- Robot Karol: https://www.schule.bayern.de/karol
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Grafiken, Code und Struktogramme: eigene didaktische Darstellung.
