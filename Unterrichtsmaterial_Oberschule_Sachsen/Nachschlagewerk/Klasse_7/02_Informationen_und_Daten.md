# 2 Informationen und Daten

## Information und Daten sind nicht dasselbe

Eine **Information** hat für einen Menschen eine Bedeutung. **Daten** sind Zeichen, Zahlen, Bilder oder andere Darstellungen, mit denen Informationen gespeichert, verarbeitet oder übertragen werden können.

Beispiel: Die Zeichenfolge `21 °C` sind Daten. Erst der Zusammenhang macht daraus eine konkrete Information:

- `21 °C` als **Lufttemperatur in einem Klassenzimmer** kann bedeuten: Der Raum ist angenehm warm.
- `21 °C` als **Temperatur einer Suppe oder eines warmen Essens** kann bedeuten: Das Essen ist kalt geworden.
- `21 °C` als **Wassertemperatur in einem Freibad** kann je nach Person als kühl oder angenehm empfunden werden.

Die Daten `21 °C` sind in allen Fällen gleich. Die daraus gewonnene Information hängt davon ab, **was gemessen wurde, in welcher Situation der Wert vorkommt und welche Frage beantwortet werden soll**.

> **Merke:** Daten sind eine Darstellung. Information entsteht, wenn Daten in einem Zusammenhang gedeutet werden.

## Daten brauchen Kontext

Dieselben Daten können unterschiedliche Informationen bedeuten. **Kontext** beschreibt den Zusammenhang, der zur richtigen Deutung benötigt wird.

Die Zeichenfolge `10` könnte zum Beispiel bedeuten:

- die Dezimalzahl zehn,
- die Binärzahl zwei,
- eine Hausnummer,
- den Tag eines Monats,
- eine Trikotnummer.

Damit Daten richtig verstanden werden, braucht man also zusätzlich Kontext und Regeln zur Interpretation.

Ein weiteres Beispiel:

```text
17
```

Ohne Zusatzinformation wissen wir nicht, ob damit 17 Jahre, 17 °C, 17 Euro oder etwas ganz anderes gemeint ist.

Selbst eine Einheit reicht nicht immer vollständig aus. Auch bei `21 °C` müssen wir noch wissen, **wessen oder welche Temperatur** gemeint ist. Erst dann lässt sich der Wert sinnvoll beurteilen.

## Daten besitzen eine Struktur

Daten werden häufig nicht ungeordnet gespeichert, sondern nach einer festgelegten Struktur.

Beispiel Schülerdatensatz:

| Feld | Wert |
|---|---|
| Vorname | Lena |
| Klasse | 7a |
| Alter | 13 |

`Vorname`, `Klasse` und `Alter` beschreiben, **welche Bedeutung** die gespeicherten Werte besitzen.

In späteren Klassen wird diese Idee bei Datenbanken wichtig.

## Datenarten

In Informatiksystemen begegnen viele Arten von Daten:

- Zahlen,
- Texte,
- Wahrheitswerte wie wahr/falsch,
- Bilder,
- Audio,
- Video,
- Messwerte,
- Standortdaten,
- Zeitangaben.

Auch wenn diese Daten für Menschen sehr unterschiedlich aussehen, werden sie in digitalen Systemen letztlich durch Bitfolgen dargestellt.

## Bit und Byte

Digitale Informatiksysteme arbeiten besonders gut mit zwei unterscheidbaren Zuständen. Diese werden meist als **0** und **1** dargestellt.

Ein **Bit** kann genau zwei Zustände annehmen:

```text
0 oder 1
```

Acht Bit werden zu einem **Byte** zusammengefasst:

```text
1 Byte = 8 Bit
```

Mit mehreren Bits lassen sich viele Kombinationen bilden.

| Anzahl Bits | mögliche Kombinationen |
|---:|---:|
| 1 | 2 |
| 2 | 4 |
| 3 | 8 |
| 4 | 16 |
| 8 | 256 |

Warum verdoppelt sich die Anzahl? Für jedes zusätzliche Bit gibt es wieder zwei Möglichkeiten.

```text
1 Bit:   0, 1
2 Bit:   00, 01, 10, 11
```

Allgemein können mit `n` Bits `2^n` verschiedene Kombinationen dargestellt werden.

> **Wichtig:** Ein Bit ist nicht automatisch eine bestimmte Information. Erst eine Codierung legt fest, was eine Bitfolge bedeutet.

## Codierung

Eine **Codierung** ist eine Vereinbarung darüber, wie Informationen durch Daten dargestellt werden.

Beispiele:

- Zeichen werden Zahlenwerten zugeordnet,
- Zahlen werden binär dargestellt,
- Bilder werden durch Pixel und Farbwerte beschrieben,
- Audiosignale werden durch Messwerte beschrieben.

Die Bitfolge

```text
01000001
```

hat für sich allein noch keine eindeutige Bedeutung. Bei einer passenden Zeichencodierung kann sie beispielsweise für den Buchstaben `A` stehen. In einem anderen Zusammenhang könnte dieselbe Bitfolge als Zahl interpretiert werden.

→ Zeichencodierung wird in **Kapitel 4: Texte im Computer** genauer erklärt.

## Analog und digital

Ein **analoges Signal** kann innerhalb eines Bereiches kontinuierlich viele Zwischenwerte annehmen.

Beispiele:

- elektrische Spannung eines Mikrofons,
- Temperatur,
- Helligkeit.

Ein digitales System verwendet dagegen festgelegte unterscheidbare Werte.

Beim **Digitalisieren** wird ein Ausschnitt der Wirklichkeit in digitale Daten überführt.

Beispiele:

```text
Schall → Mikrofon → Messwerte → Audiodaten
Licht  → Kamera   → Pixelwerte → Bilddaten
Temperatur → Sensor → Zahlenwert
```

Dabei muss entschieden werden, wie genau gemessen beziehungsweise dargestellt wird. Eine feinere Darstellung benötigt häufig mehr Daten.

## Daten erfassen

Daten können auf unterschiedliche Weise entstehen.

**Eingabe durch Menschen:** Tastatur, Touchscreen oder Formular.

**Messung durch Sensoren:** Temperatur, Helligkeit, Bewegung oder Schall.

**Automatische Erzeugung:** Ein Programm berechnet neue Werte oder protokolliert Ereignisse.

**Übertragung:** Ein Gerät erhält Daten von einem anderen System.

Die Herkunft von Daten ist wichtig, weil Fehler bereits bei der Erfassung entstehen können.

## Daten verarbeiten

Typische Operationen mit Daten sind:

- erfassen,
- speichern,
- suchen,
- sortieren,
- filtern,
- vergleichen,
- berechnen,
- verändern,
- übertragen,
- kopieren,
- löschen.

Beispiel Wetterdaten:

```text
Messwerte erfassen
→ speichern
→ nach Datum sortieren
→ Durchschnitt berechnen
→ Diagramm erzeugen
```

Aus vorhandenen Daten können durch Verarbeitung neue Daten und für Menschen neue Informationen entstehen.

## Datenqualität

Nicht alle Daten sind automatisch richtig oder brauchbar.

Wichtige Fragen sind beispielsweise:

- Sind die Daten korrekt?
- Sind sie vollständig?
- Sind sie aktuell?
- Sind sie für die Fragestellung geeignet?
- Wurden Einheiten richtig angegeben?

Beispiel:

```text
Temperatur: 20
```

Ohne Einheit ist der Wert unvollständig. `20 °C` und `20 °F` bedeuten sehr unterschiedliche Temperaturen.

Aber auch `20 °C` kann ohne weiteren Kontext noch unvollständig sein: Handelt es sich um die Lufttemperatur, die Wassertemperatur oder die Temperatur eines Lebensmittels?

Ein falsch eingestellter Sensor kann zwar viele Messwerte liefern, aber trotzdem schlechte Daten erzeugen.

> **Merke:** Viele Daten sind nicht automatisch gute Daten.

## Daten und Metadaten

**Metadaten** sind Daten, die andere Daten beschreiben.

Bei einer Bilddatei können Metadaten beispielsweise enthalten:

- Dateiname,
- Dateigröße,
- Aufnahmezeit,
- Bildabmessungen,
- verwendetes Gerät.

Bei einer Textdatei gehören Dateiname, Änderungszeitpunkt oder Zeichencodierung ebenfalls zu beschreibenden Informationen.

Metadaten helfen beim Ordnen, Suchen und Interpretieren von Dateien.

## Daten übertragen

Beim Übertragen müssen Sender und Empfänger sich darüber einig sein, wie die Daten aufgebaut und codiert sind.

Vereinfacht:

```text
Information
   ↓ codieren
Daten
   ↓ übertragen
Daten
   ↓ decodieren und deuten
Information
```

Wenn Sender und Empfänger unterschiedliche Regeln verwenden, können Daten falsch interpretiert werden.

## Daten kopieren und löschen

Digitale Daten lassen sich sehr leicht kopieren. Eine Kopie kann inhaltlich identisch zum Original sein.

Das hat Folgen:

- Eine Datei kann gleichzeitig an mehreren Orten existieren.
- Das Löschen einer Kopie löscht nicht automatisch alle anderen Kopien.
- Synchronisierte Systeme können Löschungen weitergeben.
- Sicherungskopien können ältere Daten weiterhin enthalten.

Der verantwortungsvolle Umgang mit persönlichen Daten wird in **Kapitel 12** genauer behandelt.

## Daten und Information an einem Beispiel

Ein digitales Thermometer misst eine Temperatur.

```text
physikalische Temperatur
        ↓ Sensor
Messwert
        ↓ Codierung
Binär gespeicherte Daten
        ↓ Programm
Anzeige „21 °C“
        ↓ Mensch deutet Anzeige im Kontext
Information: z. B. „Der Klassenraum ist angenehm warm.“
```

Wird derselbe Messwert bei einer warm erwarteten Suppe angezeigt, entsteht dagegen die Information: **„Die Suppe ist kalt.“**

Dieses Beispiel verbindet Wirklichkeit, Messung, Daten, Verarbeitung, Kontext und menschliche Interpretation.

## Begriffe zum Nachschlagen

**Bit:** kleinste digitale Informationseinheit mit zwei möglichen Zuständen.

**Byte:** Gruppe aus acht Bit.

**Codierung:** Vereinbarung darüber, wie Informationen durch Daten dargestellt werden.

**Daten:** Zeichen oder Werte, die gespeichert, verarbeitet oder übertragen werden können.

**Datenqualität:** beschreibt, wie gut Daten für einen bestimmten Zweck geeignet sind, beispielsweise hinsichtlich Richtigkeit, Vollständigkeit und Aktualität.

**Digitalisierung:** Überführung von Informationen oder analogen Signalen in eine digital verarbeitbare Darstellung.

**Information:** Bedeutung, die Menschen aus Daten in einem Zusammenhang gewinnen.

**Kontext:** Zusammenhang, der benötigt wird, um Daten sinnvoll und richtig zu deuten.

**Metadaten:** Daten, die andere Daten beschreiben.

→ Siehe auch **Kapitel 3: Das Binärsystem**, **Kapitel 4: Texte im Computer**, **Kapitel 5: Bilder im Computer**, **Kapitel 6: Audio im Computer**, **Kapitel 9: Dateien, Ordner und Pfade** und **Kapitel 12: Daten verantwortungsvoll nutzen**.