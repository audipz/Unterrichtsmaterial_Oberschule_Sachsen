# Lösungen – 03 Texte im Computer

## Arbeitsheft Aufgabe 1

1. `65₁₀ = 01000001₂`
2. `66₁₀ = 01000010₂`
3. `67₁₀ = 01000011₂`
4. `32₁₀ = 00100000₂`

## Arbeitsheft Aufgabe 2

```text
72 65 76 76 79 → HALLO
```

Gleiche Buchstaben besitzen bei derselben Codierung denselben Zahlenwert. Die beiden `L` werden daher beide als `76` dargestellt.

## Arbeitsheft Aufgabe 3

`HALLO` besitzt 5 Zeichen.

- 5 Byte
- `5 · 8 = 40 Bit`

## Arbeitsheft Aufgabe 4

Mögliche Antwort:

Die Bitfolge selbst enthält keine Information darüber, wie sie interpretiert werden soll. `01000001₂` ist als Binärzahl der Wert 65. Wird dieser Wert nach ASCII interpretiert, steht er für `A`.

## Arbeitsheft Aufgabe 5

Individuelle Lösungen. Entscheidend ist:

- vier Zeichen werden eindeutig auf `00`, `01`, `10`, `11` verteilt,
- Sender und Empfänger benötigen dieselbe Codiertabelle,
- ohne Vereinbarung ist die Bitfolge nicht eindeutig lesbar.

## Material M03

### Teil A

Aufgabe 1: `HALLO`

Aufgabe 2:

```text
H A L L O
72 65 76 76 79
```

Aufgabe 3:

| Zeichen | Dezimal | Binär |
|---|---:|---|
| A | 65 | 01000001 |
| B | 66 | 01000010 |
| H | 72 | 01001000 |

### Teil B

Aufgabe 4:

Ohne die Zuordnung zwischen Bitmustern und Zeichen ist nicht bekannt, welche Bedeutung die einzelnen Codes besitzen.

Aufgabe 5:

```text
00 01 10 10 11 → HALLO
```

### Teil C

Individuelle Lösungen. Die Codierung muss eindeutig sein.

### Teil D

1. `INFO`: 4 Byte = 32 Bit
2. `HALLO`: 5 Byte = 40 Bit
3. 100 ASCII-Zeichen: 100 Byte

Zusatz:

UTF-8 verwendet für verschiedene Unicode-Zeichen unterschiedlich viele Bytes. Ein ASCII-Zeichen wie `A` benötigt ein Byte; ein Emoji kann mehrere Bytes benötigen.