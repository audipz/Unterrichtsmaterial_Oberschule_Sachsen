# Lehrerband – 03 Wie speichert ein Computer Texte?

## Einordnung

Die Schülerinnen und Schüler haben zuvor Binärzahlen kennengelernt. Nun wird die zentrale Idee erweitert: Auch Zeichen werden nicht „als Buchstaben“, sondern über vereinbarte Zahlenwerte gespeichert.

Die Stunde bildet damit den Übergang von Zahlendarstellung zu allgemeiner Informationsdarstellung.

## Leitfrage

> **Wie kann ein Computer Buchstaben speichern, wenn er eigentlich nur Zahlen kennt?**

## Lernziele

Die Schülerinnen und Schüler können

- erklären, dass Zeichen über Zahlen codiert werden,
- ASCII als Beispiel einer Zeichencodierung beschreiben,
- den Unterschied zwischen ASCII, Unicode und UTF-8 auf elementarem Niveau erklären,
- erkennen, dass die Bedeutung einer Bitfolge von ihrer Interpretation abhängt,
- einfache Zeichencodes in Binärdarstellung überführen.

## Benötigte Materialien

- Arbeitsheft Kapitel 03
- Präsentation Kapitel 03
- Material `M03_Zeichencodes.md`
- optional Karten mit Zeichen und Zahlencodes

## Rückblick (ca. 5 Minuten)

Fragen:

- Welche Ziffern gibt es im Binärsystem?
- Wie wird `5₁₀` binär dargestellt?
- Was ist ein Bit?

Dann die Leitfrage stellen.

## Einstieg (ca. 5 Minuten)

An die Tafel:

```text
01000001
```

Frage:

> Was bedeutet diese Bitfolge?

Mögliche Antworten:

- 65
- eine Zahl
- ein Buchstabe
- „weiß man nicht“

Die letzte Antwort ist didaktisch besonders wertvoll: Ohne Vereinbarung ist die Bedeutung nicht eindeutig.

## Erarbeitung I – Zeichen als Zahlen (ca. 10 Minuten)

Beispiel:

```text
A → 65 → 01000001
```

Dann `B`, `C` und Leerzeichen ergänzen.

Wichtig ist nicht das Auswendiglernen von ASCII-Werten, sondern das Prinzip der Zuordnung.

## Erarbeitung II – ASCII und Unicode (ca. 10 Minuten)

ASCII knapp einführen:

- standardisierte Zuordnung,
- ursprünglich 128 Codes,
- ausreichend für einen kleinen Zeichenvorrat.

Anschließend Problemfrage:

> Was machen wir mit `ä`, `€`, chinesischen Schriftzeichen oder Emojis?

Unicode als Antwort einführen.

UTF-8 nur als Speicherform erklären, nicht technisch über Bitmuster zerlegen.

## Übung (ca. 10 Minuten)

Arbeitsheft Aufgaben 1–4 bzw. Material M03.

Aufgabe 5 eignet sich als Partnerarbeit zur Erkenntnis:

> Eine Codierung funktioniert nur, wenn Sender und Empfänger dieselbe Vereinbarung kennen.

## Sicherung (ca. 5 Minuten)

Gemeinsam festhalten:

```text
Zeichen
  ↓ Codierung
Zahl
  ↓ Binärdarstellung
Bits
```

Tafelmerksatz:

> **Bits haben keine eingebaute Bedeutung. Eine vereinbarte Codierung legt fest, was sie darstellen.**

## Typische Fehlvorstellungen

### „ASCII ist Binärschrift.“

Nein. ASCII ordnet Zeichen Zahlenwerte zu. Diese Zahlen können anschließend binär gespeichert werden.

### „Jeder Buchstabe braucht immer genau 8 Bit.“

Für einfache ASCII-Zeichen in vielen heutigen Systemen ist ein Byte typisch. Unicode/UTF-8 kann jedoch mehrere Bytes pro Zeichen benötigen.

### „Unicode und UTF-8 sind dasselbe.“

Vereinfachte Trennung:

- Unicode: Welches Zeichen hat welche Nummer?
- UTF-8: Wie wird diese Nummer als Bytes gespeichert?

## Differenzierung

### Basis

- ASCII-Tabelle verwenden
- Zeichen ↔ Dezimalzahl zuordnen
- einfache Dezimalzahlen binär darstellen

### Erweiterung

- Speicherbedarf einfacher Zeichenketten bestimmen
- Unterschied zwischen Codepunkt und Codierung diskutieren
- erklären, warum verschiedene Codierungen zu Darstellungsfehlern führen können

## Prüfungsrelevanz

Geeignet für die spätere Leistungskontrolle:

- Prinzip der Zeichencodierung
- ASCII als Beispiel
- grobe Rolle von Unicode
- Zeichen → Zahl → Binär
- Bedeutung einer Bitfolge hängt von Interpretation ab

Nicht erforderlich:

- konkrete Unicode-Codepunkte auswendig lernen
- UTF-8-Bitstruktur berechnen

## Wenn die Zeit knapp wird

Aufgabe 5 als Haus-/Zusatzaufgabe auslassen. ASCII, Unicode und der Merksatz zur Interpretation sollten unbedingt gesichert werden.

## Ausblick

> **Wenn Texte als Zahlen gespeichert werden können – wie werden dann Bilder zu Zahlen?**