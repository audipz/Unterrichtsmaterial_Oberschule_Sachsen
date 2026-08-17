---
title: "Kann man eine Sprache nur aus 0 und 1 schreiben?"
lang: de-DE
---

# Was haben wir letzte Stunde gemacht?

- Wie entstanden Rechenmaschinen?
- Welche Probleme wollten Erfinder lösen?
- Welche Idee hatte Leibniz?

---

# Leitfrage

> **Könnt ihr euch vorstellen, eine ganze Sprache nur mit den Zeichen 0 und 1 zu gestalten?**

---

# Was könnte das sein?

```text
01001000 01100001 01101100 01101100 01101111
```

Noch nicht auflösen.

---

# Zwei Zustände

```text
AUS    AN
 0      1
```

Beispiele sammeln:

- Licht
- Türkontakt
- Ja/Nein
- Signal

---

# Ein Bit

> Ein **Bit** kann genau zwei Werte besitzen:

```text
0
1
```

---

# Zwei Bits

Welche Kombinationen sind möglich?

```text
__
__
__
__
```

---

# Auflösung

```text
00
01
10
11
```

**4 Kombinationen**

---

# Drei Bits

Wie viele Kombinationen findet ihr?

```text
000
...
111
```

---

# Mehr Bits – mehr Möglichkeiten

| Bits | Kombinationen |
|---:|---:|
| 1 | 2 |
| 2 | 4 |
| 3 | 8 |
| 4 | 16 |
| 5 | 32 |

> Mit jedem zusätzlichen Bit verdoppelt sich die Zahl der Möglichkeiten.

---

# Aber was bedeutet `01000001`?

Zunächst:

> nur eine Bitfolge

Erst eine Vereinbarung gibt ihr Bedeutung.

---

# Eine mögliche Vereinbarung

```text
01000001 → A
```

Eine andere Regel könnte dieselbe Folge anders interpretieren.

---

# Gruppenauftrag

Erfindet einen Code für:

```text
JA
NEIN
STOPP
WEITER
```

Nur `0` und `1` sind erlaubt.

---

# Was haben wir herausgefunden?

- Computer können zwei Zustände unterscheiden.
- Ein Bit ist `0` oder `1`.
- Mehrere Bits bilden viele Kombinationen.
- Regeln bestimmen die Bedeutung einer Bitfolge.

---

# Nächste Frage

> **Wie kann ein Computer die Zahl 13 nur mit 0 und 1 schreiben?**
