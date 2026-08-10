# M03 – Zeichencodes

## Teil A – Zeichen und Zahlen

Nutze die Tabelle.

| Zeichen | Dezimalcode |
|:---:|---:|
| A | 65 |
| B | 66 |
| C | 67 |
| D | 68 |
| H | 72 |
| I | 73 |
| L | 76 |
| O | 79 |
| Leerzeichen | 32 |

### Aufgabe 1

Entschlüssele:

```text
72 65 76 76 79
```

Text: ______________________________________________

### Aufgabe 2

Codiere `HALLO` als Dezimalzahlen.

____________________________________________________

### Aufgabe 3

Wandle die Codes für `A`, `B` und `H` ins Binärsystem um.

| Zeichen | Dezimal | Binär |
|---|---:|---|
| A | 65 | |
| B | 66 | |
| H | 72 | |

---

## Teil B – Ohne Tabelle geht es nicht

Team A erhält nur diese Nachricht:

```text
00 01 10 10 11
```

Team B besitzt diese Tabelle:

| Code | Zeichen |
|---|---|
| 00 | H |
| 01 | A |
| 10 | L |
| 11 | O |

### Aufgabe 4

Warum kann Team A die Nachricht ohne die Tabelle nicht sicher entschlüsseln?

____________________________________________________

### Aufgabe 5

Welche Nachricht ergibt sich mit der Tabelle?

____________________________________________________

---

## Teil C – Eigene Codierung

Entwickelt eine Codierung für vier Zeichen eurer Wahl. Ihr dürft genau zwei Bits pro Zeichen verwenden.

| Bitmuster | Zeichen |
|---|---|
| 00 | |
| 01 | |
| 10 | |
| 11 | |

Schreibt damit eine Nachricht aus mindestens sechs Zeichen.

Nachricht als Bits:

____________________________________________________

Tauscht nur die Bitfolge, nicht die Tabelle. Kann das andere Team die Nachricht lesen?

Was folgt daraus?

____________________________________________________

---

## Teil D – Speicherbedarf

Ein ASCII-Zeichen wird hier vereinfacht mit einem Byte gespeichert.

1. `INFO` benötigt ______ Byte = ______ Bit.
2. `HALLO` benötigt ______ Byte = ______ Bit.
3. Ein Text mit 100 ASCII-Zeichen benötigt ______ Byte.

### Zusatz

Warum kann ein Emoji in UTF-8 mehr Speicher benötigen als ein einfacher Buchstabe wie `A`?

____________________________________________________