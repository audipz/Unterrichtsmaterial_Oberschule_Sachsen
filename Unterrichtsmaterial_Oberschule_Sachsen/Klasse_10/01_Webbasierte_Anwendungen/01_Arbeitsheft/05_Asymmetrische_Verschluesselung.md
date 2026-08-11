# 05 – Zwei Schlüssel statt einem

## Leitfrage

> **Wie kann man verschlüsselt kommunizieren, ohne vorher heimlich denselben Schlüssel auszutauschen?**

## Symmetrische Verschlüsselung – Rückblick

Bei symmetrischer Verschlüsselung wird derselbe geheime Schlüssel zum Ver- und Entschlüsseln verwendet. Das funktioniert gut, wirft aber eine Frage auf: Wie kommt der geheime Schlüssel sicher zur anderen Person?

## Asymmetrische Verschlüsselung

Bei asymmetrischen Verfahren gibt es ein zusammengehöriges Schlüsselpaar:

- **öffentlicher Schlüssel** – darf verteilt werden
- **privater Schlüssel** – bleibt geheim

Vereinfacht kann eine Nachricht mit dem öffentlichen Schlüssel des Empfängers verschlüsselt werden. Zum Entschlüsseln wird der passende private Schlüssel benötigt.

```text
öffentlicher Schlüssel → verschlüsseln
privater Schlüssel      → entschlüsseln
```

## Einwegidee

Die zugrunde liegenden mathematischen Operationen sind so gewählt, dass die Vorwärtsrichtung praktisch leicht berechnet werden kann, die Umkehrung ohne zusätzliches Geheimnis bei geeigneter Schlüssellänge aber mit realistischen Rechenmitteln nicht praktikabel sein soll.

> **Wichtig:** Sicherheit bedeutet hier nicht „mathematisch unmöglich“, sondern beruht auf Verfahren, Schlüssellängen, korrekter Umsetzung und begrenzter Rechenleistung.

## Beispiel mit Briefkästen

Stellt euch einen Briefkasten vor:

- Jeder darf etwas durch den Schlitz einwerfen.
- Nur der Besitzer mit dem passenden Schlüssel kann den Kasten öffnen.

Das Bild ist nicht vollständig technisch korrekt, hilft aber beim Verständnis der Rollen von öffentlichem und privatem Schlüssel.

## Aufgabe 1

Erkläre den Unterschied zwischen symmetrischer und asymmetrischer Verschlüsselung in drei Sätzen.

## Aufgabe 2

Alice möchte Bob eine vertrauliche Nachricht senden. Welchen Schlüssel von Bob benötigt Alice zum Verschlüsseln? Welcher Schlüssel darf niemals öffentlich werden?

## Aufgabe 3

Warum wäre es problematisch, wenn jemand Bobs privaten Schlüssel kopiert?

## Aufgabe 4

Erkläre, warum „öffentlicher Schlüssel“ nicht bedeutet, dass die verschlüsselte Nachricht öffentlich lesbar ist.

## Merksatz

> Bei asymmetrischer Verschlüsselung werden öffentlicher und privater Schlüssel getrennt verwendet. Der private Schlüssel muss geheim bleiben.
