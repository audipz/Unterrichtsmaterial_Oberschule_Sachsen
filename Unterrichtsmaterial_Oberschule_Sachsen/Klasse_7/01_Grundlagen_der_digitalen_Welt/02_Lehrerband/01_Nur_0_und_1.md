# Lehrerband – 01 Kann man eine Sprache nur aus 0 und 1 schreiben?

## Einordnung

Diese Stunde knüpft an den historischen Einstieg an. Die Schülerinnen und Schüler wechseln von der Frage, **wie Computer entstanden sind**, zur Frage, **warum digitale Systeme mit zwei Zuständen arbeiten können**.

Die Stunde führt bewusst noch **nicht** in die Umrechnung von Binärzahlen ein. Ziel ist zunächst das Verständnis des Grundprinzips: Zwei Zustände können durch Kombinationen viele verschiedene Informationen darstellen.

**Dauer:** 45 Minuten  
**Stunde:** 2  
**Prüfungsrelevant:** ja – Grundbegriffe Bit, binär, zwei Zustände

---

## Rückblick – ca. 3 Minuten

Impuls:

> Was haben wir letzte Stunde gemacht?

Mögliche Schülerantworten:

- Menschen haben schon lange Rechenhilfen entwickelt.
- verschiedene Erfinder lösten unterschiedliche Probleme.
- Computer sind schrittweise entstanden.
- Leibniz beschäftigte sich mit einem Zahlensystem aus zwei Zeichen.

Der letzte Punkt bietet den direkten Übergang.

---

## Leitfrage

> **Könnt ihr euch vorstellen, eine ganze Sprache nur mit den Zeichen 0 und 1 zu gestalten?**

Die Frage zunächst offen diskutieren lassen. Keine Definition vorwegnehmen.

Anschließend folgende Bitfolge zeigen:

```text
01001000 01100001 01101100 01101100 01101111
```

Sie kann – bei passender Zeichencodierung – für `Hallo` stehen. Entscheidend ist nicht, ASCII oder UTF-8 bereits fachlich zu behandeln, sondern die Erkenntnis:

> Eine Bitfolge erhält ihre Bedeutung durch eine vereinbarte Interpretation.

---

## Lernziele

Die Schülerinnen und Schüler können

- zwei Zustände als Grundlage binärer Darstellung beschreiben,
- den Begriff **Bit** erklären,
- mögliche Kombinationen aus wenigen Bits systematisch bestimmen,
- erklären, dass Bitfolgen erst durch Regeln eine Bedeutung erhalten,
- den Begriff **Binärsystem/Dualsystem** einordnen.

---

## Benötigtes Material

- Arbeitsheft Kapitel 01
- Präsentation Kapitel 01
- optional: Karten aus `03_Material/01_Binaercode_Karten.md`
- Tafel/Whiteboard

---

## Unterrichtsverlauf

| Zeit | Phase | Inhalt |
|---:|---|---|
| 3 min | Rückblick | Geschichte der Rechentechnik aktivieren |
| 5 min | Einstieg | Leitfrage und Bitfolge `Hallo` |
| 8 min | Erarbeitung I | Zwei Zustände, Bit, Alltagssysteme |
| 10 min | Erarbeitung II | Kombinationen mit 1, 2 und 3 Bits |
| 12 min | Anwendung | eigenen Binärcode entwickeln |
| 5 min | Sicherung | Begriffe und Kernaussagen |
| 2 min | Ausblick | Zahl 13 als nächste Herausforderung |

---

## Erarbeitung I – Zwei Zustände

Alltagsbeispiele sammeln:

```text
Licht:        aus / an
Türkontakt:   offen / geschlossen
Aussage:      falsch / wahr
Signal:       nein / ja
```

Dann zur technischen Darstellung überleiten:

```text
0 / 1
```

Wichtig ist eine sprachlich saubere Formulierung. Nicht sagen:

> Im Computer ist 0 immer „kein Strom“ und 1 immer „Strom“.

Für Klasse 7 genügt:

> Digitale Schaltungen unterscheiden zwei klar definierte Zustände, die wir mit 0 und 1 bezeichnen.

So wird keine technisch zu grobe Fehlvorstellung verfestigt.

---

## Erarbeitung II – Kombinationen

Gemeinsam an der Tafel:

```text
1 Bit:
0
1

2 Bits:
00
01
10
11
```

Frage:

> Wie können wir sicher sein, dass wir keine Kombination vergessen haben?

Danach drei Bits durch die Schülerinnen und Schüler ergänzen lassen.

Erwartete Erkenntnis:

| Bits | Kombinationen |
|---:|---:|
| 1 | 2 |
| 2 | 4 |
| 3 | 8 |
| 4 | 16 |
| 5 | 32 |

Die Potenzschreibweise `2^n` darf als Ausblick erwähnt werden, wird aber erst im Kapitel zu Binärzahlen systematisch benötigt.

---

## Anwendung – eigener Code

Die Aufgabe `JA / NEIN / STOPP / WEITER` eignet sich als Partnerarbeit.

Die Schülerinnen und Schüler sollen selbst entdecken, dass für vier eindeutig unterscheidbare Nachrichten mindestens zwei Bits erforderlich sind.

Eine mögliche Lösung:

```text
00 → JA
01 → NEIN
10 → STOPP
11 → WEITER
```

Andere eindeutige Zuordnungen sind gleichwertig.

---

## Typische Fehlvorstellungen

### „0 bedeutet immer aus, 1 bedeutet immer an.“

Das ist eine hilfreiche Modellvorstellung, aber keine universelle technische Definition. Besser von **zwei unterscheidbaren Zuständen** sprechen.

### „Eine Bitfolge hat immer dieselbe Bedeutung.“

Nein. Die Interpretation entscheidet, ob eine Bitfolge beispielsweise Zahl, Zeichen, Farbe oder Steuerinformation ist.

### „Mit zwei Bits kann ich nur die Zahlen 0 und 1 darstellen.“

Zwei Bits erlauben vier unterschiedliche Kombinationen.

---

## Differenzierung

### Unterstützung

Für die Kombinationsaufgabe Karten mit `0` und `1` verwenden. Lernende legen alle Möglichkeiten physisch aus.

### Erweiterung

Schnelle Schülerinnen und Schüler beantworten:

> Wie viele Kombinationen sind mit 6, 7 oder 8 Bits möglich?

Noch keine Umrechnung konkreter Binärzahlen verlangen.

---

## Sicherung / Tafelbild

```text
Zwei Zustände
     ↓
   0 / 1
     ↓
    Bit
     ↓
Kombinationen mehrerer Bits
     ↓
viele mögliche Informationen
```

Merksatz:

> **Ein Bit kann zwei Zustände darstellen. Mehrere Bits können zu vielen verschiedenen Kombinationen zusammengesetzt werden. Ihre Bedeutung entsteht durch eine Vereinbarung.**

---

## Wenn heute weniger Zeit ist

- Aufgabe zu vier Nachrichten als Haus-/Startaufgabe für die nächste Stunde nutzen.
- Die Tabelle bis 5 Bits kann in der folgenden Stunde beim Einstieg in Potenzen wieder aufgenommen werden.
- Die Sicherung zu `Bit` und `zwei Zustände` sollte nicht entfallen.

---

## Ausblick

> Wir können mit mehreren Bits viele verschiedene Zeichenfolgen bilden. Aber wie wird daraus eine Zahl? Wie schreibt ein Computer zum Beispiel die **13** nur mit 0 und 1?
