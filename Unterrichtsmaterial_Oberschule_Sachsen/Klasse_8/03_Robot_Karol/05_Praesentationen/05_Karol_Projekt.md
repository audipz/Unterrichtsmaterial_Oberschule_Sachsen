# Präsentation – Karol-Projekt: Muster programmatisch erzeugen

## Folie 1 – Projektauftrag
Programmiere Karol so, dass er auf einer leeren Fläche ein **4 × 4-Ziegelfeld** erzeugt.

Die Aufgabe steht direkt in der Präsentation; keine externe Datei muss gesucht werden.

## Folie 2 – Was soll dein Programm zeigen?
- sinnvolle Wiederholungen
- mindestens eine verschachtelte Wiederholung
- mindestens eine eigene Anweisung für eine wiederkehrende Teilaufgabe
- saubere Einrückung
- verständliche Namen

## Folie 3 – Zerlege das Problem
- Wie baust du eine einzelne Reihe?
- Wie wechselst du zur nächsten Reihe?
- Welche Schritte wiederholen sich?
- Welche Teilaufgabe lohnt sich als eigene Anweisung?

## Folie 4 – Prüfe deinen Code
Checkliste:
- Habe ich eine Wiederholung verwendet?
- Habe ich eine verschachtelte Wiederholung verwendet?
- Habe ich eine eigene Anweisung definiert?
- Wird diese Anweisung mehrfach aufgerufen?
- Ist mein Code sinnvoll eingerückt?
- Habe ich unnötig kopierten Code vermieden?
- Erzeugt mein Programm genau das Zielmuster?

## Folie 5 – Konkrete Testfragen
- Werden genau 16 Ziegel gelegt?
- Stimmt die Position nach der ersten Reihe?
- Funktioniert der Reihenwechsel in die richtige Richtung?
- Endet Karol nach der vierten Reihe an der erwarteten Stelle?

## Folie 6 – So soll das Ergebnis aussehen
Großes Zielbild des 4 × 4-Ziegelfeldes zeigen.

### Lehrerhinweis – kopierfertige Musterlösung
Der endgültige Code muss vor dem Unterricht mit der tatsächlich verwendeten Karol-Version und Startwelt getestet werden. Entscheidend sind Startposition, Blickrichtung und gewünschter Endzustand.

Beispielstruktur:
```text
Anweisung ReiheBauen
    wiederhole 4 mal
        Hinlegen
        wenn NichtIstWand dann
            Schritt
        *wenn
    *wiederhole
*Anweisung
```

Für den Reihenwechsel eine eigene Anweisung verwenden und passend zur festgelegten Startlage implementieren.

Typische Fehler:
- Schritt nach dem letzten Ziegel
- falsche Drehrichtung
- Reihenwechsel nach der letzten Reihe
- eigene Anweisung definiert, aber nicht aufgerufen

## Folie 7 – Eine mögliche Lösung als Struktogramm
Schüler sehen die Lösungsstruktur als Struktogramm statt vollständigem Lösungscode:
- äußere Wiederholung = Reihen
- innere Wiederholung = Felder/Ziegel einer Reihe
- Reihenwechsel als eigene Teilaufgabe

## Folie 8 – Reflexion am eigenen Code
- Wo ist die äußere Schleife?
- Wo ist die innere Schleife?
- Welche eigene Anweisung hast du definiert?
- Wo wird sie aufgerufen?
- Warum muss die Definition nur einmal geschrieben werden?

## Folie 9 – Transfer
- 3 × 6-Feld erzeugen
- Welche Wiederholungszahlen ändern sich?
- Welche eigene Anweisung kann unverändert bleiben?
- Erweiterung: Rahmen- oder Wechselmuster

## Quellen
- Robot Karol: https://www.schule.bayern.de/karol
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Zielmuster und Struktogramme: eigene didaktische Darstellung.
