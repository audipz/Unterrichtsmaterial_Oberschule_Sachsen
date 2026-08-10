---
title: "Wie schreibt ein Computer Zahlen?"
lang: de-DE
---

# Wie schreibt ein Computer Zahlen?

> Wie kann aus nur `0` und `1` die Zahl `13` entstehen?

---

# Rückblick

- Ein Bit kennt zwei Zustände.
- Mehrere Bits können kombiniert werden.

Heute: Zahlen darstellen.

---

# Unser Zahlensystem

Dezimal:

```text
0 1 2 3 4 5 6 7 8 9
```

Zehn Ziffern.

---

# Das Binärsystem

Binär:

```text
0 1
```

Nur zwei Ziffern.

---

# Stellenwerte

```text
128 64 32 16 8 4 2 1
```

Jeder Wert ist doppelt so groß wie der vorherige.

---

# Zweierpotenzen

```text
2⁰ = 1
2¹ = 2
2² = 4
2³ = 8
2⁴ = 16
2⁵ = 32
```

Wichtig:

```text
2⁰ = 1
```

---

# Beispiel 13

```text
13 = 8 + 4 + 1
```

| 8 | 4 | 2 | 1 |
|---:|---:|---:|---:|
| 1 | 1 | 0 | 1 |

```text
13₁₀ = 1101₂
```

---

# Zweiter Weg

## Immer durch 2 teilen

```text
13 : 2 = 6 Rest 1
 6 : 2 = 3 Rest 0
 3 : 2 = 1 Rest 1
 1 : 2 = 0 Rest 1
```

Reste von unten nach oben:

```text
1101
```

---

# Zurückrechnen

```text
10110₂
```

```text
16 + 4 + 2 = 22
```

Also:

```text
10110₂ = 22₁₀
```

---

# Jetzt ihr

Wandelt um:

```text
9₁₀
14₁₀
21₁₀
```

---

# Fehler finden

Stimmt das?

```text
14₁₀ = 1111₂
```

Begründet.

---

# Merksatz

> Im Binärsystem sind die Stellenwerte Zweierpotenzen.

---

# Ausblick

> Zahlen können wir jetzt speichern. Aber wie wird aus einer Zahl ein Buchstabe?