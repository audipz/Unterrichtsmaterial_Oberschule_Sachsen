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

Jede Stelle links ist im Dezimalsystem zehnmal so viel wert wie die Stelle rechts daneben:

```text
1 → 10 → 100 → 1000 → ...
```

Man kann diese Stellenwerte auch als Zehnerpotenzen schreiben:

```text
10⁰ = 1
10¹ = 10
10² = 100
10³ = 1000
```

Im Binärsystem funktioniert dasselbe Stellenwertprinzip mit der Basis **2**. Jede Stelle links ist deshalb doppelt so viel wert wie die Stelle rechts daneben:

```text
1 → 2 → 4 → 8 → 16 → 32 → 64 → 128 → ...
```

Als Zweierpotenzen:

```text
2⁰ = 1
2¹ = 2
2² = 4
2³ = 8
2⁴ = 16
2⁵ = 32
```

Die Potenzschreibweise musst du für die ersten Umrechnungen nicht unbedingt verwenden. Wichtig ist zunächst die Regel: **Von rechts nach links verdoppelt sich der Stellenwert.**

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

Der entscheidende Unterschied: Im Binärsystem gibt es nur `0` und `1`. Sobald beim Addieren der Wert **2** entsteht, kann er nicht als einzelne Binärziffer an derselben Stelle geschrieben werden. Zwei Einheiten eines Stellenwertes ergeben stattdessen eine Einheit des nächsthöheren Stellenwertes.

### Was bedeutet ein Übertrag?

Im Dezimalsystem kennst du den Übertrag beispielsweise von:

```text
  8
+ 7
---
 15
```

Acht Einer plus sieben Einer ergeben 15 Einer. Davon bleiben fünf Einer an der Einerstelle und ein Zehner wird nach links übertragen.

Im Binärsystem passiert dasselbe Prinzip bereits bei **zwei** Einheiten:

```text
1 Einer + 1 Einer = 2 Einer = 1 Zweier
```

Darum gilt:

```text
1₂ + 1₂ = 10₂
```

An der Einerstelle wird `0` geschrieben. Die `1` links davon steht für **einen Zweier** und ist der Übertrag.

Dasselbe gilt an jeder weiteren Stelle:

```text
1 Zweier + 1 Zweier = 1 Vierer
1 Vierer  + 1 Vierer  = 1 Achter
1 Achter  + 1 Achter  = 1 Sechzehner
```

> **Merksatz:** Zwei gleiche Stellenwerte werden im Binärsystem zu einem Stellenwert der nächsten Stelle links.

### Die vier Grundfälle

Diese vier Regeln reichen zunächst aus:

| Rechnung | Ergebnis | Was geschieht? |
|---|---|---|
| `0 + 0` | `0` | `0` schreiben, kein Übertrag |
| `0 + 1` | `1` | `1` schreiben, kein Übertrag |
| `1 + 0` | `1` | `1` schreiben, kein Übertrag |
| `1 + 1` | `10₂` | `0` schreiben, `1` übertragen |

Die letzte Zeile ist besonders wichtig. `10₂` bedeutet hier nicht „zehn“:

```text
10₁₀ = zehn
10₂  = zwei
```

### Erstes Beispiel ohne Übertrag

Wir berechnen:

```text
  0101₂
+ 0010₂
-------
  0111₂
```

Jetzt betrachten wir die Rechnung **Spalte für Spalte von rechts nach links**:

```text
1. Spalte: 1 + 0 = 1       → 1 schreiben
2. Spalte: 0 + 1 = 1       → 1 schreiben
3. Spalte: 1 + 0 = 1       → 1 schreiben
4. Spalte: 0 + 0 = 0       → 0 schreiben
```

Damit ergibt sich `0111₂`.

Kontrolle im Dezimalsystem:

```text
0101₂ = 5₁₀
0010₂ = 2₁₀
0111₂ = 7₁₀

5 + 2 = 7
```

### Beispiel mit Überträgen – vollständig kommentiert

Nun berechnen wir:

```text
  0101₂
+ 0011₂
-------
  1000₂
```

Die einzelnen Rechenschritte sind:

```text
1. Spalte von rechts:
1 + 1 = 10₂
→ 0 schreiben
→ 1 zur nächsten Spalte übertragen

2. Spalte von rechts:
0 + 1 + 1 Übertrag = 10₂
→ 0 schreiben
→ 1 zur nächsten Spalte übertragen

3. Spalte von rechts:
1 + 0 + 1 Übertrag = 10₂
→ 0 schreiben
→ 1 zur nächsten Spalte übertragen

4. Spalte von rechts:
0 + 0 + 1 Übertrag = 1
→ 1 schreiben
→ kein weiterer Übertrag
```

So entsteht das Ergebnis Schritt für Schritt:

```text
0101₂ + 0011₂ = 1000₂
```

Kontrolle:

```text
0101₂ = 5₁₀
0011₂ = 3₁₀
1000₂ = 8₁₀

5 + 3 = 8
```

> **Tipp:** Wenn die Überträge am Anfang verwirrend sind, schreibe nicht nur das Endergebnis hin. Kommentiere jede Spalte mit „hinschreiben“ und „übertragen“ wie im Beispiel.

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

Dabei wird die **rechte** `1` an die aktuelle Stelle geschrieben und die **linke** `1` zur nächsten Stelle übertragen.

Die vollständige Übersicht lautet damit:

| Summe in einer Spalte | Binärdarstellung | hinschreiben | Übertrag |
|---:|---:|---:|---:|
| 0 | `0₂` | `0` | `0` |
| 1 | `1₂` | `1` | `0` |
| 2 | `10₂` | `0` | `1` |
| 3 | `11₂` | `1` | `1` |

### Ausführliches Beispiel mit `1 + 1 + 1`

Wir berechnen:

```text
  1011₂
+ 0111₂
-------
 10010₂
```

Die Rechnung wird wieder vollständig kommentiert:

```text
1. Spalte von rechts:
1 + 1 = 10₂
→ 0 schreiben
→ 1 übertragen

2. Spalte von rechts:
1 + 1 + 1 Übertrag = 11₂
→ 1 schreiben
→ 1 übertragen

3. Spalte von rechts:
0 + 1 + 1 Übertrag = 10₂
→ 0 schreiben
→ 1 übertragen

4. Spalte von rechts:
1 + 0 + 1 Übertrag = 10₂
→ 0 schreiben
→ 1 übertragen

Es gibt links keine weitere vorhandene Spalte mehr:
→ den letzten Übertrag 1 als neue Stelle links anschreiben
```

Damit entsteht:

```text
10010₂
```

Kontrolle:

```text
1011₂  = 11₁₀
0111₂  =  7₁₀
10010₂ = 18₁₀

11 + 7 = 18
```

### Ein festes Schema für die schriftliche Binäraddition

Für jede Spalte kannst du immer dieselben vier Fragen verwenden:

1. Welche beiden Ziffern stehen in dieser Spalte?
2. Gibt es einen Übertrag aus der vorherigen Spalte?
3. Welche Ziffer muss ich **hinschreiben**?
4. Muss ich eine `1` **übertragen**?

Dann gehst du eine Spalte nach links und wiederholst das Verfahren.

> **Merksatz:** Rechne von rechts nach links. Berücksichtige zuerst beide Ziffern und einen möglichen Übertrag. Schreibe dann die Ergebnisziffer hin und nimm einen neuen Übertrag mit, falls die Summe 2 oder 3 beträgt.

## Addition kontrollieren

Gerade am Anfang ist eine Kontrolle über das Dezimalsystem hilfreich:

1. Beide Binärzahlen in Dezimalzahlen umwandeln.
2. Dezimal addieren.
3. Das binäre Ergebnis ebenfalls in Dezimal umwandeln.
4. Prüfen, ob beide Ergebnisse gleich sind.

So kann man sowohl die Binäraddition als auch die Umrechnung üben und Fehler leichter entdecken.

## Häufige Fehler bei der Binäraddition

### Übertrag vergessen

Aus

```text
1 + 1 = 10₂
```

wird `0` geschrieben **und** `1` übertragen. Wird der Übertrag vergessen, sind die folgenden Stellen falsch.

### Übertrag doppelt verwenden

Ein Übertrag gehört genau zur **nächsten Spalte links**. Nachdem er dort mitgerechnet wurde, ist er verbraucht. Nur wenn in dieser neuen Spalte wieder eine Summe von 2 oder 3 entsteht, gibt es erneut einen Übertrag.

### Von links anfangen

Wie bei der schriftlichen Dezimaladdition beginnt man rechts bei der kleinsten Stelle.

### `10₂` als Dezimalzahl zehn lesen

Die tiefgestellte `2` erinnert daran, dass die Zahl im Binärsystem geschrieben ist:

```text
10₂ = 2₁₀
```

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

**Basis:** Anzahl unterschiedlicher Ziffern eines Stellenwertsystems. Das Dezimalsystem hat die Basis 10, das Binärsystem die Basis 2.

**Binäraddition:** schriftliche Addition von Binärzahlen nach Stellenwerten und mit Überträgen.

**Binärsystem/Dualsystem:** Stellenwertsystem mit den Ziffern 0 und 1.

**Dezimalsystem:** Stellenwertsystem mit den Ziffern 0 bis 9.

**Stellenwert:** Wert, den eine Position innerhalb einer Zahl besitzt.

**Überlauf/Overflow:** Situation, in der ein Ergebnis mehr Stellen beziehungsweise Bits benötigt als der vorgesehene Zahlenbereich bereitstellt.

**Übertrag:** Wert, der beim Addieren in die nächsthöhere Stelle übernommen wird.

**Zweierpotenz:** Zahl der Form `2ⁿ`, beispielsweise 1, 2, 4, 8, 16, 32 oder 64.

→ Siehe auch **Kapitel 4: Zeichen, Codes und Bits** und **Kapitel 8: Speicher und Datenmengen**.