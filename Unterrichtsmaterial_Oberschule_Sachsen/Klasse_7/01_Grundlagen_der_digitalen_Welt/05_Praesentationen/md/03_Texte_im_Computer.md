---
title: "Wie speichert ein Computer Texte?"
lang: de-DE
---

# Wie speichert ein Computer Texte?

> Wie kann ein Computer Buchstaben speichern, wenn er nur Zahlen kennt?

---

# Rückblick

- Bits: `0` und `1`
- Binärzahlen
- Zweierpotenzen
- Binäraddition

---

# Was bedeutet diese Bitfolge?

```text
01000001
```

Zahl?

Buchstabe?

Etwas ganz anderes?

---

# Zeichen werden Zahlen

```text
A → 65
B → 66
C → 67
```

Dann:

```text
65 → 01000001₂
```

---

# Der entscheidende Punkt

> Eine Bitfolge besitzt nicht automatisch eine Bedeutung.

Wir brauchen eine **Vereinbarung**.

---

# ASCII

Eine standardisierte Zuordnung von Zeichen zu Zahlen.

Beispiele:

```text
A → 65
H → 72
L → 76
O → 79
```

---

# Rätsel

```text
72 65 76 76 79
```

Was steht dort?

---

# HALLO

```text
H  A  L  L  O
72 65 76 76 79
```

---

# Reicht ASCII für die ganze Welt?

Was ist mit:

```text
ä   €   Ω   中   😊
```

---

# Unicode

Unicode gibt sehr vielen Zeichen der Welt eine eindeutige Nummer.

```text
A  → U+0041
€  → U+20AC
😊 → U+1F60A
```

---

# UTF-8

UTF-8 beschreibt, wie Unicode-Zeichen als Bytes gespeichert werden.

Vereinfacht:

```text
A  → 1 Byte
ä  → 2 Byte
€  → 3 Byte
😊 → 4 Byte
```

---

# Der Weg eines Buchstabens

```text
Zeichen
  ↓
Zahlencode
  ↓
Binärzahl
  ↓
Bits im Speicher
```

---

# Partneraufgabe

Erfindet eine Codierung mit zwei Bits für vier Zeichen.

Könnt ihr eine Nachricht lesen, wenn ihr die Codiertabelle nicht kennt?

---

# Merksatz

> Bits bekommen ihre Bedeutung erst durch die Vereinbarung, wie sie interpretiert werden.

---

# Nächste Frage

> Wenn Texte aus Zahlen werden können – wie wird dann aus Zahlen ein Bild?