# Präsentation – Robot Karol Einstieg

## Folie 1 – Leitfrage
Wie wird aus einem Algorithmus ein ausführbares Programm?

> Layout-Hinweis: Leitfragenkasten nicht durch Grafiken überdecken. Karol-Welt darunter platzieren.

## Folie 2 – Die Karol-Welt
- Karol bewegt sich auf Kacheln.
- Wände, Ziegel und Marken können Teil der Welt sein.
- Karol besitzt Position und Blickrichtung.
- Blickrichtung kann mit Norden, Osten, Süden und Westen beschrieben werden; sie hängt von der jeweiligen Welt ab.

## Folie 3 – Grundbefehle
Beispiele: `Schritt`, `LinksDrehen`, `Hinlegen`, `Aufheben`, `MarkeSetzen`.

## Folie 4 – Vom Algorithmus zum Struktogramm
- Struktogramm = grafische Darstellung eines Algorithmus
- zeigt Sequenz, Wiederholung und später Bedingungen
- Ablauf wird anschließend in Karol-Code übertragen

## Folie 5 – Problem: zehn gleiche Schritte
Zehnmal `Schritt` ist lang und fehleranfällig. Wiederkehrendes Muster erkennen lassen.

## Folie 6 – Lösung: Wiederholung
Konkretes Beispiel:
```text
wiederhole 10 mal
    Schritt
*wiederhole
```

## Folie 7 – Quadrat mit Wiederholung
Konkreter Karol-Code:
```text
wiederhole 4 mal
    wiederhole 4 mal
        Schritt
    *wiederhole
    LinksDrehen
*wiederhole
```
Daneben ein passendes Struktogramm zeigen.

## Folie 8 – Fehler sind Informationen
Als geschlossenen Lifecycle darstellen:
**Beobachten → Eingrenzen → Ändern → Testen → Beobachten → …**

Merksatz: nur eine gezielte Änderung durchführen und danach erneut testen.

## Folie 9 – Konkrete Testfälle
- 3 freie Kacheln bis zur Wand → bleibt Karol rechtzeitig stehen?
- Wand direkt vor Karol → kein ungültiger Schritt?
- 6 freie Kacheln → funktioniert derselbe Algorithmus ohne Änderung?

## Folie 10 – Ausblick
Karol soll als Nächstes auf seine Umgebung reagieren: Sensorfragen, Bedingungen und eigene Anweisungen.

## Quellen
- Robot Karol: https://www.schule.bayern.de/karol
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Grafiken und Struktogramme: eigene didaktische Darstellung.
