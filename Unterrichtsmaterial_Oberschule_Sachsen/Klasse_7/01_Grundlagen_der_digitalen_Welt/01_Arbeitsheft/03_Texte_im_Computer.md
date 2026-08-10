# 03 – Wie speichert ein Computer Texte?

## Rückblick

In der letzten Stunde habt ihr gelernt:

- Computer stellen Zahlen mit `0` und `1` dar.
- Jede Stelle im Binärsystem besitzt einen Stellenwert.
- Binärzahlen lassen sich umrechnen und addieren.

Heute geht es um die nächste Frage:

> **Wie kann ein Computer Buchstaben speichern, wenn er eigentlich nur Zahlen kennt?**

---

## Eine Vereinbarung macht aus Zahlen Zeichen

Ein Computer speichert nicht direkt den Buchstaben `A`.

Stattdessen wird einem Zeichen eine Zahl zugeordnet.

Beispiel:

| Zeichen | Zahl |
|---|---:|
| `A` | 65 |
| `B` | 66 |
| `C` | 67 |
| `a` | 97 |
| `b` | 98 |
| `0` | 48 |
| Leerzeichen | 32 |

Diese Zuordnung nennt man **Zeichencodierung**.

> **Merke:** Ein Zeichen wird durch eine Zahl dargestellt. Diese Zahl wird anschließend binär gespeichert.

---

## Vom Buchstaben zum Bitmuster

Für `A` ist beispielsweise die Zahl 65 vereinbart.

```text
A → 65₁₀ → 01000001₂
```

Für `B`:

```text
B → 66₁₀ → 01000010₂
```

Damit kann derselbe Computer Zahlen, Buchstaben und andere Zeichen als Bitfolgen speichern.

### Aufgabe 1

Wandle die folgenden Zeichencodes in Binärzahlen um:

1. `A → 65₁₀ → ____________________₂`
2. `B → 66₁₀ → ____________________₂`
3. `C → 67₁₀ → ____________________₂`
4. Leerzeichen `→ 32₁₀ → ____________________₂`

---

## ASCII – eine frühe gemeinsame Vereinbarung

Damit Computer Texte austauschen können, müssen sie dieselben Zahlen für dieselben Zeichen verwenden.

Eine wichtige frühe Zeichencodierung ist **ASCII**.

ASCII enthält unter anderem:

- lateinische Groß- und Kleinbuchstaben,
- Ziffern,
- Satzzeichen,
- Steuerzeichen.

Mit sieben Bits lassen sich `2⁷ = 128` verschiedene Werte darstellen. Der ursprüngliche ASCII-Zeichensatz verwendet diese 128 möglichen Codes.

### Aufgabe 2 – Eine geheime Nachricht

Entschlüssele mit der Tabelle:

| Zahl | Zeichen |
|---:|:---|
| 72 | H |
| 65 | A |
| 76 | L |
| 79 | O |

```text
72 65 76 76 79
```

Nachricht: __________________________________________

Was fällt dir am Zahlenwert des gleichen Buchstabens auf?

____________________________________________________

---

## Warum ASCII allein nicht reicht

ASCII wurde für eine begrenzte Menge von Zeichen entwickelt.

Doch Menschen schreiben zum Beispiel mit:

```text
ä ö ü ß
é ñ
€
Ω
中
😊
```

Für die Sprachen und Zeichen der Welt reichen 128 Möglichkeiten nicht aus.

Dafür gibt es **Unicode**.

Unicode ordnet sehr vielen Zeichen der Welt eindeutige Nummern zu.

Beispiele:

| Zeichen | Unicode-Codepunkt |
|:---:|:---|
| `A` | U+0041 |
| `€` | U+20AC |
| `😊` | U+1F60A |

> **Wichtig:** Unicode beschreibt, welches Zeichen welche Nummer erhält. Wie diese Nummer in Bytes gespeichert wird, regelt eine Codierung wie UTF-8.

---

## UTF-8 – unterschiedlich viele Bytes

**UTF-8** ist heute eine sehr verbreitete Möglichkeit, Unicode-Zeichen als Bytes zu speichern.

Einige Zeichen benötigen nur ein Byte, andere mehrere.

Vereinfacht:

| Beispiel | typischer Speicherbedarf in UTF-8 |
|---|---:|
| `A` | 1 Byte |
| `ä` | 2 Byte |
| `€` | 3 Byte |
| `😊` | 4 Byte |

### Aufgabe 3

Ein Text enthält nur die fünf ASCII-Buchstaben `HALLO`.

Wenn jedes Zeichen ein Byte benötigt:

1. Wie viele Zeichen enthält der Text? ______
2. Wie viele Byte werden mindestens benötigt? ______
3. Wie viele Bit sind das? ______

---

## Gleiche Bits – unterschiedliche Bedeutung

Die Bitfolge

```text
01000001
```

entspricht als Zahl dem Dezimalwert 65.

Wenn wir vereinbaren, dass 65 als ASCII-Zeichen gelesen wird, bedeutet dieselbe Bitfolge:

```text
A
```

Das zeigt etwas Wichtiges:

> **Bits besitzen nicht von selbst eine Bedeutung. Erst die Vereinbarung über ihre Interpretation macht daraus eine Zahl, einen Buchstaben, eine Farbe oder etwas anderes.**

### Aufgabe 4 – Erklären

Erkläre mit eigenen Worten:

> Warum kann dieselbe Bitfolge sowohl eine Zahl als auch ein Buchstabe sein?

____________________________________________________

____________________________________________________

---

## Aufgabe 5 – Eigene Codierung

Ihr dürft nur die Zeichen `A`, `B`, `C` und Leerzeichen verwenden.

Entwickelt zu zweit eine eigene Codierung mit zwei Bits.

| Bitmuster | Zeichen |
|---|---|
| `00` | |
| `01` | |
| `10` | |
| `11` | |

Codiert anschließend das Wort:

```text
AB CAB
```

Bitfolge:

____________________________________________________

Tauscht eure Bitfolge mit einem anderen Team. Kann das andere Team sie ohne eure Codiertabelle lesen?

Was zeigt das?

____________________________________________________

---

# Das Wichtigste

- Computer speichern Zeichen als Zahlen.
- Eine Zeichencodierung legt fest, welche Zahl zu welchem Zeichen gehört.
- ASCII ist eine frühe standardisierte Zeichencodierung.
- Unicode stellt Zeichen vieler Sprachen und Schriftsysteme bereit.
- UTF-8 speichert Unicode-Zeichen in einem oder mehreren Bytes.
- Eine Bitfolge erhält ihre Bedeutung erst durch die vereinbarte Interpretation.

## Ausblick

> **Texte können wir nun als Zahlen speichern. Aber wie kann ein Computer ein Foto speichern, obwohl ein Bild aus Farben besteht?**