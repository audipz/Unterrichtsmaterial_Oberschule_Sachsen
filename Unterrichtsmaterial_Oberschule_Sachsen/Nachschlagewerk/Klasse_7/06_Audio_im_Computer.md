# 6 Audio im Computer

## Vom Schall zu digitalen Daten

Schall entsteht durch Schwingungen. Diese breiten sich beispielsweise durch Luft aus und verändern dort den Luftdruck.

Ein **Mikrofon** wandelt die Schallschwingungen zunächst in ein elektrisches Signal um. Dieses Signal verändert sich kontinuierlich mit der Zeit und ist damit zunächst analog.

Damit ein Computer es speichern und verarbeiten kann, wird es **digitalisiert**.

![Abtastung eines analogen Audiosignals in regelmäßigen Zeitabständen](grafiken/audio_digitalisierung.svg)

Vereinfacht:

```text
Schall
  ↓
Mikrofon
  ↓
analoges elektrisches Signal
  ↓ Abtastung und Quantisierung
digitale Zahlenwerte
  ↓
Audiodatei
```

> **Merke:** Digitales Audio ist keine im Computer gespeicherte Schallwelle. Es besteht aus Zahlenwerten, aus denen ein Audiosignal wieder erzeugt werden kann.

## Abtastung – wann wird gemessen?

Bei der **Abtastung** wird der momentane Wert des analogen Signals in regelmäßigen Zeitabständen gemessen.

Die **Abtastrate** gibt an, wie oft pro Sekunde gemessen wird. Sie wird in **Hertz (Hz)** angegeben.

Beispiele:

```text
8 000 Hz  = 8 000 Messungen pro Sekunde
44 100 Hz = 44 100 Messungen pro Sekunde
48 000 Hz = 48 000 Messungen pro Sekunde
```

Eine höhere Abtastrate erzeugt mehr Messwerte pro Sekunde und benötigt deshalb bei ansonsten gleichen Bedingungen mehr Daten.

## Quantisierung – wie genau wird gemessen?

Ein analoges Signal kann sehr viele Zwischenwerte besitzen. Ein Computer muss einen gemessenen Wert jedoch mit einer begrenzten Anzahl von Bits darstellen.

Bei der **Quantisierung** wird der gemessene Wert deshalb einer verfügbaren digitalen Stufe zugeordnet.

Je mehr Bits pro Messwert zur Verfügung stehen, desto mehr verschiedene Stufen können dargestellt werden.

Beispiele:

| Bits pro Messwert | mögliche Stufen |
|---:|---:|
| 8 Bit | 256 |
| 16 Bit | 65 536 |
| 24 Bit | 16 777 216 |

Mehr Stufen ermöglichen eine feinere Darstellung des Messwertes, benötigen aber mehr Speicher.

> **Merke:** Abtastrate beschreibt, **wie oft** gemessen wird. Bittiefe beschreibt, **wie fein** ein Messwert dargestellt werden kann.

## Warum geht beim Digitalisieren Information verloren?

Ein analoges Signal besitzt theoretisch beliebig viele Zeitpunkte und Zwischenwerte. Bei der Digitalisierung werden nur bestimmte Zeitpunkte gemessen und die Werte auf verfügbare Stufen abgebildet.

Die digitale Darstellung ist deshalb eine Annäherung an das ursprüngliche analoge Signal.

Mit geeigneter Abtastrate und Bittiefe kann diese Annäherung für den gewünschten Zweck sehr genau sein.

## Kanäle

Eine **Monoaufnahme** besitzt einen Audiokanal.

Bei **Stereo** werden zwei getrennte Kanäle gespeichert, meist für links und rechts.

```text
Mono:   1 Kanal
Stereo: 2 Kanäle
```

Mehr Kanäle bedeuten bei gleicher Abtastrate und Bittiefe auch mehr Daten.

Mehrkanalton kann beispielsweise für räumliche Wiedergabesysteme verwendet werden.

## Datenmenge unkomprimierter Audiodaten

Für unkomprimierte Audiodaten lässt sich die Datenmenge vereinfacht berechnen:

```text
Abtastrate × Bittiefe × Kanäle × Dauer
```

Beispiel für eine Sekunde Stereo mit 44 100 Messungen pro Sekunde und 16 Bit:

```text
44 100 × 16 × 2
= 1 411 200 Bit pro Sekunde
```

Das entspricht:

```text
1 411 200 : 8
= 176 400 Byte pro Sekunde
```

Für eine Minute wären es ohne zusätzliche Dateiinformationen bereits ungefähr:

```text
176 400 × 60
= 10 584 000 Byte
```

also rund 10,6 MB in dezimaler Schreibweise.

Dieses Beispiel zeigt, warum Audiokompression praktisch wichtig ist.

## Bitrate

Die **Bitrate** gibt an, wie viele Bits pro Sekunde übertragen oder gespeichert werden.

Sie wird häufig in `kbit/s` angegeben.

Bei unkomprimiertem Audio ergibt sie sich direkt aus Abtastrate, Bittiefe und Kanalzahl. Bei komprimiertem Audio hängt sie zusätzlich vom verwendeten Verfahren und den Einstellungen ab.

Eine höhere Bitrate kann mehr Audioinformation enthalten, bedeutet aber nicht automatisch, dass jede Aufnahme hörbar besser ist.

## Audiodateien und Codecs

Ein **Dateiformat** beschreibt, wie eine Datei aufgebaut ist. Ein **Codec** beschreibt ein Verfahren zum Codieren und Decodieren von Audio- oder Videodaten.

Diese Begriffe werden im Alltag häufig vermischt.

Beispiele, die Schülerinnen und Schüler häufig sehen:

- WAV,
- FLAC,
- MP3,
- AAC.

Eine WAV-Datei enthält häufig unkomprimierte Audiodaten, kann technisch aber unterschiedliche Audioformate aufnehmen. MP3 bezeichnet ein verlustbehaftetes Audiokompressionsverfahren. FLAC ist für verlustfreie Audiokompression bekannt.

## Verlustfreie Kompression

Bei **verlustfreier Kompression** werden Daten so gespeichert, dass die ursprünglichen digitalen Audiodaten beim Decodieren vollständig wiederhergestellt werden können.

Beispiel: FLAC.

Das ist sinnvoll, wenn die ursprünglichen digitalen Daten erhalten bleiben sollen.

## Verlustbehaftete Kompression

Bei **verlustbehafteter Kompression** werden Teile der Information dauerhaft entfernt beziehungsweise vereinfacht.

Verfahren versuchen dabei unter anderem Eigenschaften der menschlichen Wahrnehmung auszunutzen. Informationen, die weniger auffallen, können stärker vereinfacht werden.

Beispiele: MP3 und AAC.

Vorteil:

- deutlich kleinere Dateien möglich.

Nachteil:

- die entfernte Information lässt sich nicht vollständig zurückholen,
- zu starke Kompression kann hörbare Artefakte erzeugen.

> **Merke:** Eine MP3-Datei wird durch Zurückspeichern als WAV nicht wieder zur ursprünglichen Aufnahme. Verlorene Information wird dadurch nicht wiederhergestellt.

## Analog zurück: vom Computer zum Lautsprecher

Beim Abspielen geschieht vereinfacht der umgekehrte Weg.

```text
Audiodatei
  ↓ decodieren
digitale Messwerte
  ↓ Digital-Analog-Wandlung
elektrisches Signal
  ↓
Lautsprecher
  ↓
Schall
```

Der Lautsprecher bewegt eine Membran. Dadurch entstehen wieder Druckschwankungen in der Luft, die wir als Schall wahrnehmen können.

## Aufnahmequalität hängt von mehr als Dateiwerten ab

Eine hohe Abtastrate und Bittiefe können eine gute technische Grundlage sein. Sie retten aber keine schlechte Aufnahme.

Die Qualität hängt beispielsweise auch ab von:

- Mikrofon,
- Aufnahmeraum,
- Abstand zur Schallquelle,
- Hintergrundgeräuschen,
- Aussteuerung,
- späterer Bearbeitung,
- Kompression.

Eine riesige Audiodatei ist deshalb nicht automatisch eine gute Aufnahme.

## Clipping

Ist ein Eingangssignal zu stark, kann der darstellbare Bereich überschritten werden. Spitzen des Signals werden dann abgeschnitten. Dieser Effekt heißt **Clipping** und kann deutlich hörbare Verzerrungen erzeugen.

Mehr Lautstärke bei der Aufnahme ist deshalb nicht unbegrenzt besser.

## Audio bearbeiten

Digitale Audiodaten können durch Programme verarbeitet werden.

Typische Bearbeitungen sind:

- schneiden,
- Lautstärke verändern,
- Ein- und Ausblenden,
- mehrere Spuren mischen,
- Störgeräusche reduzieren,
- Effekte anwenden,
- in ein anderes Format exportieren.

Wie bei Bildern ist es sinnvoll, zwischen einer bearbeitbaren Projektdatei und einer exportierten Datei zur Wiedergabe zu unterscheiden.

## Begriffe zum Nachschlagen

**Abtastrate:** Anzahl der Messungen eines Signals pro Sekunde.

**Audiokanal:** getrennte Tonspur innerhalb einer Aufnahme, beispielsweise links oder rechts bei Stereo.

**Bitrate:** Anzahl der pro Sekunde gespeicherten oder übertragenen Bits.

**Bittiefe:** Anzahl der Bits, die für die Darstellung eines einzelnen Messwertes verwendet werden.

**Clipping:** Verzerrung durch Überschreiten des darstellbaren Signalbereiches.

**Codec:** Verfahren beziehungsweise technische Festlegung zum Codieren und Decodieren von Medieninformationen.

**Digitalisierung:** Umwandlung eines analogen Signals in digitale Daten.

**Kompression:** Verringerung der benötigten Datenmenge.

**Quantisierung:** Zuordnung eines Messwertes zu einer begrenzten Anzahl digital darstellbarer Stufen.

**Sampling/Abtastung:** regelmäßiges Messen eines analogen Signals.

→ Siehe auch **Kapitel 2: Informationen und Daten**, **Kapitel 3: Das Binärsystem** und **Kapitel 8: Speicher und Datenmengen**.