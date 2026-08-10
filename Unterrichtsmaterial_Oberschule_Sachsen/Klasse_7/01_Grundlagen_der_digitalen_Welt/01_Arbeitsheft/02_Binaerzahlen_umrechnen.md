# 02 – Wie schreibt ein Computer Zahlen?

## Rückblick

In der letzten Stunde habt ihr herausgefunden:

- Computer unterscheiden zwei Zustände.
- Diese Zustände können als `0` und `1` dargestellt werden.
- Eine einzelne Binärstelle nennt man **Bit**.

Heute beantworten wir die nächste Frage:

> **Wie kann ein Computer mit nur 0 und 1 Zahlen darstellen?**

---

## Zwei Zahlensysteme

Im Alltag verwenden wir das **Dezimalsystem**. Es besitzt zehn Ziffern:

```text
0 1 2 3 4 5 6 7 8 9
```

Computer verwenden häufig das **Binärsystem**. Es besitzt nur zwei Ziffern:

```text
0 1
```

Der Unterschied liegt nicht darin, *ob* man große Zahlen darstellen kann, sondern darin, welche **Stellenwerte** verwendet werden.

---

## Stellenwerte im Dezimalsystem

Bei der Zahl `347` hat jede Stelle einen anderen Wert:

| Stelle | Potenz | Wert |
|---:|---:|---:|
| Hunderter | 10² | 100 |
| Zehner | 10¹ | 10 |
| Einer | 10⁰ | 1 |

Damit gilt:

```text
347 = 3 · 100 + 4 · 10 + 7 · 1
```

---

## Stellenwerte im Binärsystem

Im Binärsystem werden die Stellenwerte mit Potenzen von 2 gebildet:

| Potenz | 2⁷ | 2⁶ | 2⁵ | 2⁴ | 2³ | 2² | 2¹ | 2⁰ |
|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| Wert | 128 | 64 | 32 | 16 | 8 | 4 | 2 | 1 |

Diese Reihe solltest du sicher kennen:

```text
1, 2, 4, 8, 16, 32, 64, 128, ...
```

Jeder Wert ist doppelt so groß wie der vorherige.

> **Merke:** Im Binärsystem entspricht jede Stelle einer Zweierpotenz.

---

# Methode 1 – Mit Zweierpotenzen

Wir wollen die Dezimalzahl `13` binär darstellen.

Zuerst suchen wir die größten Zweierpotenzen, aus denen sich 13 zusammensetzen lässt:

```text
13 = 8 + 4 + 1
```

Die Stellenwerte lauten:

| 8 | 4 | 2 | 1 |
|---:|---:|---:|---:|
| 1 | 1 | 0 | 1 |

Also:

```text
13₁₀ = 1101₂
```

Die tiefgestellten Zahlen zeigen das verwendete Zahlensystem an.

---

## Beispiel: 22

```text
22 = 16 + 4 + 2
```

| 16 | 8 | 4 | 2 | 1 |
|---:|---:|---:|---:|---:|
| 1 | 0 | 1 | 1 | 0 |

Damit:

```text
22₁₀ = 10110₂
```

---

## Aufgabe 1 – Mit Zweierpotenzen

Wandle ins Binärsystem um.

1. `5₁₀ = ____________________₂`
2. `9₁₀ = ____________________₂`
3. `12₁₀ = ____________________₂`
4. `18₁₀ = ____________________₂`
5. `27₁₀ = ____________________₂`

Notiere bei mindestens zwei Aufgaben zusätzlich die Zerlegung in Zweierpotenzen.

---

# Methode 2 – Wiederholt durch 2 teilen

Es gibt noch einen zweiten Weg.

Wir teilen die Zahl immer wieder durch 2 und notieren den Rest.

Beispiel mit `13`:

| Rechnung | Ergebnis | Rest |
|---|---:|---:|
| 13 : 2 | 6 | 1 |
| 6 : 2 | 3 | 0 |
| 3 : 2 | 1 | 1 |
| 1 : 2 | 0 | 1 |

Jetzt lesen wir die Reste **von unten nach oben**:

```text
1101
```

Also wieder:

```text
13₁₀ = 1101₂
```

> **Merke:** Bei der Divisionsmethode werden die Reste von unten nach oben gelesen.

---

## Aufgabe 2 – Division durch 2

Wandle mit der Divisionsmethode um.

1. `10₁₀`
2. `15₁₀`
3. `20₁₀`
4. `25₁₀`

Zeige jeweils alle Rechenschritte.

---

# Binär zurück ins Dezimalsystem

Nun gehen wir den umgekehrten Weg.

Beispiel:

```text
10110₂
```

| Stelle | 16 | 8 | 4 | 2 | 1 |
|---|---:|---:|---:|---:|---:|
| Bit | 1 | 0 | 1 | 1 | 0 |

Wir addieren nur die Stellenwerte, bei denen eine `1` steht:

```text
16 + 4 + 2 = 22
```

Also:

```text
10110₂ = 22₁₀
```

---

## Aufgabe 3 – Binär nach Dezimal

Wandle ins Dezimalsystem um.

1. `101₂ = ______₁₀`
2. `1001₂ = ______₁₀`
3. `1110₂ = ______₁₀`
4. `10000₂ = ______₁₀`
5. `11001₂ = ______₁₀`
6. `111111₂ = ______₁₀`

---

## Aufgabe 4 – Fehler finden

Jemand behauptet:

```text
14₁₀ = 1111₂
```

Stimmt das?

Prüfe die Aussage und korrigiere sie gegebenenfalls.

____________________________________________________

____________________________________________________

---

## Aufgabe 5 – Welche Methode passt zu dir?

Vergleiche beide Verfahren.

### Zweierpotenzen

Vorteil:

____________________________________________________

### Division durch 2

Vorteil:

____________________________________________________

Welche Methode würdest du bei der Zahl `73` verwenden? Begründe.

____________________________________________________

---

# Knobelaufgabe

Mit vier Bits können genau die Stellenwerte

```text
8 4 2 1
```

verwendet werden.

1. Welche kleinste Zahl kann dargestellt werden?
2. Welche größte Zahl kann dargestellt werden?
3. Wie viele verschiedene Bitmuster gibt es insgesamt?

____________________________________________________

---

# Das Wichtigste

- Das Dezimalsystem arbeitet mit Potenzen von 10.
- Das Binärsystem arbeitet mit Potenzen von 2.
- Dezimalzahlen können über Zweierpotenzen oder durch wiederholte Division durch 2 ins Binärsystem umgerechnet werden.
- Binärzahlen werden ins Dezimalsystem zurückgerechnet, indem die Stellenwerte mit `1` addiert werden.

## Ausblick

> **Wenn Zahlen nur aus 0 und 1 bestehen können – wie speichert ein Computer dann Buchstaben und ganze Texte?**