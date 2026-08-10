# 04 – Wie speichert ein Computer Musik?

## Rückblick

Texte und Bilder werden im Computer als Zahlen gespeichert. Für Töne gilt dasselbe.

> **Leitfrage:** Wie wird aus einer Schallwelle eine Folge aus 0 und 1?

## Schall ist eine Schwingung

Ein Mikrofon wandelt Luftschwingungen in ein elektrisches Signal um. Dieses Signal verändert sich kontinuierlich. Ein Computer speichert dagegen einzelne Zahlenwerte.

## Abtasten

Beim **Abtasten** wird das Signal in kurzen Zeitabständen gemessen. Jeder Messwert beschreibt die Stärke des Signals zu diesem Zeitpunkt.

Je häufiger gemessen wird, desto genauer kann der Verlauf beschrieben werden. Die Zahl der Messungen pro Sekunde heißt **Abtastrate**.

Beispiel: `44 100 Hz` bedeutet 44 100 Messungen pro Sekunde.

## Bittiefe

Jeder Messwert muss als Zahl gespeichert werden. Die **Bittiefe** bestimmt, wie viele unterschiedliche Werte möglich sind.

- 8 Bit: 256 mögliche Werte
- 16 Bit: 65 536 mögliche Werte

Mehr Bits ermöglichen feinere Abstufungen, benötigen aber mehr Speicher.

## Mono und Stereo

Bei **Mono** wird ein Kanal gespeichert. Bei **Stereo** werden zwei Kanäle gespeichert, zum Beispiel links und rechts.

## Speicherbedarf

Vereinfacht gilt:

`Speicherbedarf = Abtastrate × Bittiefe × Kanäle × Dauer`

Das Ergebnis wird zunächst in Bit berechnet.

### Beispiel

Eine Sekunde Stereo-Ton mit 44 100 Hz und 16 Bit benötigt:

`44 100 × 16 × 2 = 1 411 200 Bit`

Das sind `176 400 Byte` pro Sekunde.

## Kompression

Unkomprimierte Audiodateien können groß werden. Deshalb werden Daten häufig komprimiert.

- **verlustfrei:** Die ursprünglichen Daten lassen sich vollständig wiederherstellen.
- **verlustbehaftet:** Weniger wichtige Informationen werden entfernt, um stärker zu verkleinern.

MP3 ist ein bekanntes Beispiel für verlustbehaftete Audiokompression.

## Aufgaben

1. Erkläre mit eigenen Worten den Begriff **Abtastrate**.
2. Warum verbessert eine höhere Bittiefe die Darstellung eines Tons?
3. Was unterscheidet Mono und Stereo?
4. Berechne den Speicherbedarf für 2 Sekunden Mono-Ton bei 8 000 Hz und 8 Bit.
5. Warum werden Audiodateien komprimiert?
6. Begründe: Auch Musik besteht im Computer letztlich aus Zahlen.

## Das Wichtigste

- Schall wird in regelmäßigen Abständen gemessen.
- Die Abtastrate gibt die Messungen pro Sekunde an.
- Die Bittiefe bestimmt die Anzahl möglicher Messwerte.
- Digitale Audiodaten können komprimiert werden.

## Ausblick

> Texte, Bilder und Musik sind jetzt digitale Informationen. Aber was macht ein Computer eigentlich mit diesen Informationen?