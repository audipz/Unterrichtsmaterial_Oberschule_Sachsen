# Präsentation – Algorithmen vertiefen

## Folie 1 – Leitfrage
Wann ist eine Beschreibung so genau, dass ein Computer sie ausführen kann?

## Folie 2 – Was ist ein Algorithmus?
- eindeutige Folge ausführbarer Anweisungen
- löst eine Aufgabe oder ein Problem
- Schritte sind nachvollziehbar beschrieben

## Folie 3 – Qualitätsmerkmale
- eindeutig
- ausführbar
- endlich
- liefert ein erwartbares Ergebnis **(in endlicher Zeit)**

## Folie 4 – Abläufe darstellen
- nummerierte Schritte
- Pseudocode
- Struktogramm als grafische Darstellung

## Folie 5 – Struktogramm kurz erklärt
- Sequenz = Blöcke untereinander
- Wiederholung = Block innerhalb eines Wiederholungsrahmens
- Bedingung = Verzweigung mit WAHR/FALSCH
- Verschachtelung wird durch ineinander liegende Blöcke sichtbar

## Folie 6 – Bedingungen mit Beispielen
Eine Bedingung ist eine Aussage, die WAHR oder FALSCH ist.

Beispiele:
- Alltag: WENN es regnet, DANN Schirm mitnehmen.
- Anmeldung: WENN Passwort korrekt, DANN Zugriff, SONST Fehlermeldung.
- Spiel: WENN `leben = 0`, DANN Spiel beenden.
- Robot Karol: WENN vor Karol eine Wand ist, DANN drehen, SONST Schritt.

## Folie 7 – Wiederholungen
- feste Wiederholung: Anzahl vorher bekannt
- bedingte Wiederholung: Bedingung entscheidet über Fortsetzung

Robot-Karol-Beispiele:
```text
wiederhole 5 mal
    Schritt
*wiederhole
```

```text
solange NichtIstWand
    Schritt
*solange
```

## Folie 8 – Verschachtelung
- Kontrollstrukturen können ineinander liegen
- Schleife in Schleife
- Bedingung in Schleife
- Einrückung macht Zugehörigkeiten sichtbar

Beispielidee: 4 × 4-Ziegelfeld mit verschachtelten Wiederholungen.

## Folie 9 – Testfälle konkret
Nicht nur Zahlen nennen, sondern Ausgangslage + Erwartung:
- Wand nach 3 Kacheln → Karol bleibt vor der Wand stehen
- Wand direkt davor → kein ungültiger Schritt
- 6 freie Kacheln → derselbe Algorithmus funktioniert ohne Änderung

## Folie 10 – Ausblick Robot Karol
Algorithmen werden als Programme umgesetzt, getestet und verbessert.

## Lehrerhinweis
`if` wertet Bedingungen als wahr/falsch aus. Mehrfachauswahlen wie `switch/case` können später anhand eines Wertes mehrere Fälle unterscheiden; dies ist für die aktuelle Karol-Sequenz nicht erforderlich.

## Quellen
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Struktogramme und Beispiele: eigene didaktische Darstellung.
