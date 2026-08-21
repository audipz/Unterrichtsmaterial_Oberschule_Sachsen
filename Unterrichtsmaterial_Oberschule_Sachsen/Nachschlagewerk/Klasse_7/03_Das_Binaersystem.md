# 3 Das Binärsystem

## Zwei Ziffern reichen aus

Das **Binärsystem** oder **Dualsystem** verwendet nur die Ziffern 0 und 1. Computer können damit Daten darstellen, weil technische Bauteile zwei unterscheidbare Zustände besonders zuverlässig verarbeiten können.

Unser gewohntes **Dezimalsystem** besitzt zehn Ziffern von 0 bis 9. Beide Systeme sind Stellenwertsysteme.

## Stellenwerte

Im Dezimalsystem steigt der Stellenwert von rechts nach links jeweils um den Faktor 10. Im Binärsystem steigt er jeweils um den Faktor 2.

![Binär-Stellenwerttafel mit dem Beispiel 00101101₂ = 45₁₀](grafiken/binaer_stellenwert.svg)

| Binär-Stelle | 128 | 64 | 32 | 16 | 8 | 4 | 2 | 1 |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Beispiel `00101101` | 0 | 0 | 1 | 0 | 1 | 1 | 0 | 1 |

Für `00101101₂` werden die Stellenwerte mit einer 1 addiert:

```text
32 + 8 + 4 + 1 = 45
```

Damit gilt:

```text
00101101₂ = 45₁₀
```

## Dezimalzahl in Binärzahl umwandeln

Für kleine Zahlen kann man prüfen, welche Zweierpotenzen benötigt werden. Die Dezimalzahl 13 besteht beispielsweise aus:

```text
13 = 8 + 4 + 1
```

In der Stellenwerttafel stehen deshalb bei 8, 4 und 1 Einsen:

```text
8  4  2  1
1  1  0  1
```

Also gilt `13₁₀ = 1101₂`.

## Führende Nullen

`1101₂` und `00001101₂` besitzen denselben Zahlenwert. Zusätzliche Nullen links verändern den Wert nicht. In Informatiksystemen werden trotzdem oft feste Bitlängen verwendet, beispielsweise 8 Bit.

## Warum ist das wichtig?

Binärzahlen sind nicht nur zum Rechnen da. Bitfolgen können je nach Codierung Zahlen, Buchstaben, Farben, Töne oder andere Daten darstellen. Die gleiche Folge aus Nullen und Einsen kann deshalb unterschiedliche Bedeutungen haben, wenn eine andere Codierung verwendet wird.

> **Merke:** Im Binärsystem besitzt jede Stelle von rechts nach links den doppelten Wert der vorherigen Stelle.

## Begriffe zum Nachschlagen

**Binärsystem/Dualsystem:** Stellenwertsystem mit den Ziffern 0 und 1.

**Dezimalsystem:** Stellenwertsystem mit den Ziffern 0 bis 9.

**Stellenwert:** Wert, den eine Position innerhalb einer Zahl besitzt.

**Zweierpotenz:** Zahl der Form 1, 2, 4, 8, 16, 32, 64, …

→ Siehe auch **Kapitel 2: Informationen und Daten** und **Kapitel 8: Speicher und Datenmengen**.
