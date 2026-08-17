# Lösungen – Kann man eine Sprache nur aus 0 und 1 schreiben?

## Aufgabe 1 – Zwei Zustände finden

Mögliche Lösungen:

| Beispiel | Zustand 0 | Zustand 1 |
|---|---|---|
| Lichtschalter | aus | an |
| Türkontakt | offen | geschlossen |
| Aussage | falsch | wahr |
| Parkplatzsensor | frei | belegt |

Andere eindeutig beschreibbare Zwei-Zustands-Beispiele sind möglich.

---

## Aufgabe 2 – Kombinationen

| Anzahl Bits | mögliche Kombinationen |
|---:|---:|
| 1 | 2 |
| 2 | 4 |
| 3 | 8 |
| 4 | 16 |
| 5 | 32 |

Beobachtung:

> Mit jedem zusätzlichen Bit verdoppelt sich die Zahl der möglichen Kombinationen.

---

## Aufgabe 3 – eigener Binärcode

Eine mögliche Lösung:

```text
00 → JA
01 → NEIN
10 → STOPP
11 → WEITER
```

Andere eindeutige Zuordnungen sind ebenfalls korrekt.

Für vier verschiedene Nachrichten werden mindestens **zwei Bits** benötigt, weil zwei Bits genau vier verschiedene Kombinationen ermöglichen.

---

## Sicherung

Ein Bit kann die Werte **0** oder **1** besitzen.

Mit zwei Bits gibt es **4** verschiedene Kombinationen.

Computer können mit Bits unterschiedliche Informationen darstellen, weil **vereinbarte Regeln festlegen, welche Bedeutung eine Bitfolge besitzt**.
