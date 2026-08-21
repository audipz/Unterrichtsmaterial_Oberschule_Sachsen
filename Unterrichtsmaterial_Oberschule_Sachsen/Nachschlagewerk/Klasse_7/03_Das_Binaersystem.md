# 3 Das Binärsystem

## Zwei Ziffern reichen aus

Das **Binärsystem** oder **Dualsystem** verwendet nur die Ziffern 0 und 1. Computer können damit Daten darstellen, weil technische Bauteile zwei unterscheidbare Zustände besonders zuverlässig verarbeiten können.

Unser gewohntes **Dezimalsystem** verwendet die Ziffern 0 bis 9. Das Binärsystem verwendet nur 0 und 1. Trotzdem kann man mit beiden Systemen beliebig große ganze Zahlen darstellen.

## Stellenwerte verstehen

Bei einer Zahl hängt der Wert einer Ziffer davon ab, **an welcher Stelle** sie steht.

Im Dezimalsystem bedeutet die Zahl `352`:

| Stelle | Hunderter | Zehner | Einer |
|---|---:|---:|---:|
| Stellenwert | 100 | 10 | 1 |
| Ziffer | 3 | 5 | 2 |

Also:

```text
352 = 3 · 100 + 5 · 10 + 2 · 1
```

Im Binärsystem funktioniert das genauso. Der Unterschied ist nur: Die Stellenwerte sind nicht `1, 10, 100, 1000, ...`, sondern `1, 2, 4, 8, 16, 32, ...`.

Von rechts nach links verdoppelt sich also jeder Stellenwert:

```text
... 128   64   32   16   8   4   2   1
```

![Binär-Stellenwerttafel mit dem Beispiel 00101101₂ = 45₁₀](grafiken/binaer_stellenwert.svg)

Für die Binärzahl `00101101₂` gilt:

| Stellenwert | 128 | 64 | 32 | 16 | 8 | 4 | 2 | 1 |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Ziffer | 0 | 0 | 1 | 0 | 1 | 1 | 0 | 1 |

Nur die Stellen mit einer `1` zählen mit:

```text
32 + 8 + 4 + 1 = 45
```

Damit gilt:

```text
00101101₂ = 45₁₀
```

> **Merke:** Eine `1` bedeutet: Dieser Stellenwert wird mitgezählt. Eine `0` bedeutet: Dieser Stellenwert wird nicht mitgezählt.

## Binärzahl in Dezimalzahl umwandeln

Um eine Binärzahl in eine Dezimalzahl umzuwandeln, gehst du so vor:

1. Schreibe die passenden Stellenwerte über die Binärzahl.
2. Markiere alle Stellen, an denen eine `1` steht.
3. Addiere genau diese Stellenwerte.

Beispiel `1101₂`:

| Stellenwert | 8 | 4 | 2 | 1 |
|---|---:|---:|---:|---:|
| Ziffer | 1 | 1 | 0 | 1 |

Also:

```text
8 + 4 + 1 = 13
```

Damit gilt:

```text
1101₂ = 13₁₀
```

## Dezimalzahl in Binärzahl umwandeln – mit Stellenwerten

Bei kleinen Zahlen kann man überlegen, aus welchen Zweierpotenzen die Zahl zusammengesetzt ist.

Beispiel `13₁₀`:

```text
13 = 8 + 4 + 1
```

Daraus folgt:

```text
8  4  2  1
1  1  0  1
```

Also:

```text
13₁₀ = 1101₂
```

Diese Methode ist für kleine Zahlen sehr anschaulich. Für größere Zahlen gibt es ein systematisches Verfahren: die **fortgesetzte Division durch 2**.

## Dezimalzahl in Binärzahl umwandeln – Division durch 2

Teile die Dezimalzahl wiederholt durch 2. Notiere bei jeder Division den **Rest**. Da durch 2 geteilt wird, kann der Rest nur `0` oder `1` sein.

Beispiel: `45₁₀` soll in eine Binärzahl umgewandelt werden.

| Rechnung | Ergebnis | Rest |
|---|---:|---:|
| 45 : 2 | 22 | 1 |
| 22 : 2 | 11 | 0 |
| 11 : 2 | 5 | 1 |
| 5 : 2 | 2 | 1 |
| 2 : 2 | 1 | 0 |
| 1 : 2 | 0 | 1 |

Jetzt werden die Reste **von unten nach oben** gelesen:

```text
101101
```

Damit gilt:

```text
45₁₀ = 101101₂
```

Warum funktioniert das? Bei jeder Division durch 2 wird entschieden, ob die Zahl gerade oder ungerade ist. Der Rest zeigt deshalb jeweils die nächste Binärstelle von rechts nach links.

> **Merke:** Bei der Division-durch-2-Methode werden die Reste von **unten nach oben** gelesen.

## Binärzahlen addieren

Binärzahlen können ähnlich wie Dezimalzahlen **schriftlich addiert** werden. Man schreibt die Zahlen stellenweise untereinander und beginnt ganz rechts.

Der entscheidende Unterschied: Im Binärsystem gibt es nur `0` und `1`. Sobald beim Addieren der Wert **2** entsteht, kann er nicht als einzelne Binärziffer geschrieben werden. Deshalb entsteht ein **Übertrag** in die nächste Stelle.

### Die vier Grundfälle

Diese vier Regeln reichen zunächst aus:

| Rechnung | Ergebnis | Erklärung |
|---|---|---|
| `0 + 0` | `0` | nichts wird hinzugefügt |
| `0 + 1` | `1` | ergibt eins |
| `1 + 0` | `1` | ergibt eins |
| `1 + 1` | `10` | zwei ist binär `10`: `0` schreiben, `1` übertragen |

Die letzte Zeile ist besonders wichtig:

```text
1 + 1 = 10₂
```

Das bedeutet nicht „eins plus eins ist zehn“. `10₂` ist die Binärdarstellung der Dezimalzahl 2.

Vergleiche:

```text
10₁₀ = zehn
10₂  = zwei
```

> **Merke:** Bei `1 + 1` schreibst du `0` und nimmst `1` als Übertrag zur nächsten Stelle mit.

### Erstes Beispiel ohne Übertrag

Wir addieren:

```text
  0101₂
+ 0010₂
-------
```

Von rechts nach links:

1. `1 + 0 = 1`
2. `0 + 1 = 1`
3. `1 + 0 = 1`
4. `0 + 0 = 0`

Damit:

```text
  0101₂
+ 0010₂
-------
  0111₂
```

Kontrolle im Dezimalsystem:

```text
0101₂ = 5₁₀
0010₂ = 2₁₀
0111₂ = 7₁₀

5 + 2 = 7
```

### Beispiel mit einem Übertrag

Nun:

```text
  0101₂
+ 0011₂
-------
```

Wir beginnen wieder rechts.

**Erste Stelle:**

```text
1 + 1 = 10₂
```

Wir schreiben unten eine `0` und übertragen eine `1` zur nächsten Stelle.

**Zweite Stelle:** Dort stehen `0` und `1`; zusätzlich kommt der Übertrag `1` hinzu.

```text
0 + 1 + 1 = 10₂
```

Wieder schreiben wir `0` und übertragen `1`.

**Dritte Stelle:**

```text
1 + 0 + 1 = 10₂
```

Wieder `0` schreiben und `1` übertragen.

**Vierte Stelle:**

```text
0 + 0 + 1 = 1
```

Das Ergebnis lautet:

```text
    111   ← Überträge zur Erklärung
  0101₂
+ 0011₂
-------
  1000₂
```

Kontrolle:

```text
0101₂ = 5₁₀
0011₂ = 3₁₀
1000₂ = 8₁₀

5 + 3 = 8
```

### Was bedeutet `1 + 1 + 1`?

Beim schriftlichen Addieren kann zu zwei Einsen noch ein Übertrag hinzukommen:

```text
1 + 1 + 1
```

Im Dezimalsystem ist das 3. Die Binärdarstellung von 3 ist:

```text
11₂
```

Deshalb gilt:

```text
1 + 1 + 1 = 11₂
```

Man schreibt an der aktuellen Stelle die rechte `1` und überträgt die linke `1`.

Damit ergänzen wir die Regeln:

| Summe an einer Stelle | hinschreiben | Übertrag |
|---|---:|---:|
| `0` | `0` | `0` |
| `1` | `1` | `0` |
| `2 = 10₂` | `0` | `1` |
| `3 = 11₂` | `1` | `1` |

### Ausführliches Beispiel mit mehreren Überträgen

Wir berechnen:

```text
1011₂ + 0111₂
```

Das entspricht zur späteren Kontrolle `11 + 7`.

Von rechts nach links:

**1. Stelle:**

```text
1 + 1 = 10₂
```

`0` schreiben, `1` übertragen.

**2. Stelle:**

```text
1 + 1 + 1 Übertrag = 11₂
```

`1` schreiben, `1` übertragen.

**3. Stelle:**

```text
0 + 1 + 1 Übertrag = 10₂
```

`0` schreiben, `1` übertragen.

**4. Stelle:**

```text
1 + 0 + 1 Übertrag = 10₂
```

`0` schreiben. Der letzte Übertrag wird als neue Stelle links davor geschrieben.

Ergebnis:

```text
  1011₂
+ 0111₂
-------
 10010₂
```

Kontrolle:

```text
1011₂  = 11₁₀
0111₂  =  7₁₀
10010₂ = 18₁₀

11 + 7 = 18
```

## Warum funktioniert der Übertrag?

Im Dezimalsystem entsteht bei `8 + 7 = 15` ein Übertrag, weil zehn Einer zu einem Zehner zusammengefasst werden.

Im Binärsystem entsteht der Übertrag bereits bei zwei gleichen Stellenwerten:

```text
1 Einer + 1 Einer = 2 Einer = 1 Zweier
```

Deshalb:

```text
1₂ + 1₂ = 10₂
```

Bei der Zweierstelle gilt genauso:

```text
1 Zweier + 1 Zweier = 2 Zweier = 1 Vierer
```

Bei der Viererstelle:

```text
1 Vierer + 1 Vierer = 2 Vierer = 1 Achter
```

Der Übertrag bedeutet also immer: **Zwei Einheiten eines Stellenwertes werden zu einer Einheit des nächsthöheren Stellenwertes.**

> **Merksatz:** Binär addieren funktioniert wie schriftliches Addieren im Dezimalsystem – nur entsteht der Übertrag schon bei 2 statt erst bei 10.

## Addition kontrollieren

Gerade am Anfang ist eine Kontrolle über das Dezimalsystem hilfreich:

1. Beide Binärzahlen in Dezimalzahlen umwandeln.
2. Dezimal addieren.
3. Das binäre Ergebnis ebenfalls in Dezimal umwandeln.
4. Prüfen, ob beide Ergebnisse gleich sind.

So kann man sowohl die Binäraddition als auch die Umrechnung üben und Fehler leichter entdecken.

## Feste Bitlängen und Überlauf – ein erster Hinweis

Auf Papier können wir bei Bedarf einfach links eine weitere Stelle ergänzen. Computer verwenden für Zahlen jedoch häufig eine festgelegte Anzahl von Bits.

Mit **4 Bit** können ohne Vorzeichen beispielsweise nur diese Werte dargestellt werden:

```text
0000₂ bis 1111₂
```

also dezimal 0 bis 15.

Die Rechnung

```text
1111₂ + 0001₂ = 10000₂
```

benötigt fünf Stellen. Wenn nur vier Bit zur Verfügung stehen, passt das vollständige Ergebnis nicht mehr in den vorgesehenen Speicherbereich. Das nennt man **Überlauf (Overflow)**.

Für Klasse 7 reicht zunächst die Grundidee: Eine feste Anzahl von Bits begrenzt den darstellbaren Zahlenbereich.

## Kontrolle durch Rückumwandlung

Eine Umrechnung kann leicht überprüft werden, indem man die erhaltene Binärzahl wieder in eine Dezimalzahl umwandelt.

Für `101101₂`:

| Stellenwert | 32 | 16 | 8 | 4 | 2 | 1 |
|---|---:|---:|---:|---:|---:|---:|
| Ziffer | 1 | 0 | 1 | 1 | 0 | 1 |

Also:

```text
32 + 8 + 4 + 1 = 45
```

Die Umrechnung stimmt.

## Führende Nullen

`1101₂` und `00001101₂` besitzen denselben Zahlenwert. Zusätzliche Nullen links verändern den Wert nicht. In Informatiksystemen werden trotzdem oft feste Bitlängen verwendet, beispielsweise 8 Bit.

## Warum ist das wichtig?

Binärzahlen sind nicht nur zum Rechnen da. Bitfolgen können je nach Codierung Zahlen, Buchstaben, Farben, Töne oder andere Daten darstellen. Die gleiche Folge aus Nullen und Einsen kann deshalb unterschiedliche Bedeutungen haben, wenn eine andere Codierung verwendet wird.

> **Merke:** Im Binärsystem sind die Stellenwerte von rechts nach links `1, 2, 4, 8, 16, 32, ...`. Jede Stelle ist doppelt so viel wert wie die Stelle rechts daneben.

## Begriffe zum Nachschlagen

**Binäraddition:** schriftliche Addition von Binärzahlen nach Stellenwerten und mit Überträgen.

**Binärsystem/Dualsystem:** Stellenwertsystem mit den Ziffern 0 und 1.

**Dezimalsystem:** Stellenwertsystem mit den Ziffern 0 bis 9.

**Stellenwert:** Wert, den eine Position innerhalb einer Zahl besitzt.

**Überlauf/Overflow:** Situation, in der ein Ergebnis mehr Stellen beziehungsweise Bits benötigt als der vorgesehene Zahlenbereich bereitstellt.

**Übertrag:** Wert, der beim Addieren in die nächsthöhere Stelle übernommen wird.

**Zweierpotenz:** Zahl der Form 1, 2, 4, 8, 16, 32, 64, …

**Rest:** Wert, der bei einer Division übrig bleibt. Bei der Division durch 2 ist der Rest immer 0 oder 1.

→ Siehe auch **Kapitel 2: Informationen und Daten** und **Kapitel 8: Speicher und Datenmengen**.