# Kann man eine Sprache nur aus 0 und 1 schreiben?

## Rückblick

Was haben wir in der letzten Stunde über die Geschichte der Rechentechnik herausgefunden?

Notiere zwei Gedanken:

1. ________________________________________________
2. ________________________________________________

---

## Leitfrage

> **Könnt ihr euch vorstellen, eine ganze Sprache nur mit den Zeichen 0 und 1 zu gestalten?**

Betrachte diese Zeichenfolge:

```text
01001000 01100001 01101100 01101100 01101111
```

Was könnte sie bedeuten?

____________________________________________________

Welche Vermutung hast du, warum ein Computer überhaupt mit `0` und `1` arbeitet?

____________________________________________________

---

## Zwei Zustände reichen aus

Elektronische Bauteile können Zustände unterscheiden. Für den Einstieg reichen zwei Möglichkeiten:

```text
AN    AUS
1     0
```

Auch viele Dinge aus dem Alltag besitzen zwei klar unterscheidbare Zustände:

- Licht an / Licht aus
- Schalter geschlossen / Schalter offen
- Frage wahr / Frage falsch
- Signal vorhanden / Signal nicht vorhanden

Ein Computer kann solche zwei Zustände sehr zuverlässig unterscheiden.

> **Merke:** Ein einzelner binärer Zustand wird als **Bit** bezeichnet. Ein Bit kann den Wert `0` oder `1` besitzen.

---

## Aufgabe 1 – Zwei Zustände finden

Finde vier Beispiele aus deinem Alltag, die sich mit genau zwei Zuständen beschreiben lassen.

| Beispiel | Zustand 0 | Zustand 1 |
|---|---|---|
| Lichtschalter | aus | an |
| | | |
| | | |
| | | |
| | | |

---

## Wie viele Möglichkeiten entstehen?

Mit **einem Bit** gibt es zwei Möglichkeiten:

```text
0
1
```

Mit **zwei Bits** entstehen schon vier Kombinationen:

```text
00
01
10
11
```

Mit **drei Bits** gibt es acht Kombinationen:

```text
000
001
010
011
100
101
110
111
```

### Aufgabe 2

Vervollständige die Tabelle.

| Anzahl Bits | mögliche Kombinationen |
|---:|---:|
| 1 | 2 |
| 2 | 4 |
| 3 | 8 |
| 4 | |
| 5 | |

Was fällt dir auf?

____________________________________________________

---

## Eine Vereinbarung macht aus Bits Information

Die Zeichenfolge

```text
01000001
```

ist zunächst nur eine Folge von Bits.

Erst eine **Vereinbarung** legt fest, was sie bedeutet. Eine solche Vereinbarung kann zum Beispiel sagen:

```text
01000001 → A
```

Eine andere Vereinbarung könnte dieselbe Bitfolge als Zahl interpretieren.

> **Merke:** Bits bekommen ihre Bedeutung durch Regeln und Vereinbarungen. Deshalb können Computer Zahlen, Texte, Bilder, Musik und viele andere Informationen darstellen.

---

## Aufgabe 3 – Erfindet einen eigenen Binärcode

Ihr dürft nur die Zeichen `0` und `1` verwenden.

Erfindet einen Code für vier Nachrichten:

```text
JA
NEIN
STOPP
WEITER
```

| Nachricht | euer Code |
|---|---|
| JA | |
| NEIN | |
| STOPP | |
| WEITER | |

### Prüft euren Code

1. Ist jede Nachricht eindeutig?
2. Kann eine andere Gruppe euren Code benutzen, wenn ihr die Vereinbarung erklärt?
3. Wie viele Bits benötigt ihr mindestens für vier verschiedene Nachrichten?

---

## Warum nicht zehn Zustände?

Menschen rechnen im Alltag meist mit zehn Ziffern:

```text
0 1 2 3 4 5 6 7 8 9
```

Computer verwenden intern häufig nur zwei Zustände. Zwei klar unterscheidbare Zustände lassen sich technisch besonders zuverlässig speichern und verarbeiten.

Das Zahlensystem mit den Ziffern `0` und `1` heißt **Binärsystem** oder **Dualsystem**.

---

## Sicherung

Ergänze die Sätze.

Ein Bit kann die Werte ______ oder ______ besitzen.

Mit zwei Bits gibt es ______ verschiedene Kombinationen.

Computer können mit Bits unterschiedliche Informationen darstellen, weil

____________________________________________________

---

## Ausblick

Wir wissen jetzt, warum zwei Zustände reichen können.

Die nächste Frage lautet:

> **Wie kann ein Computer mit 0 und 1 die Zahl 13 darstellen?**
