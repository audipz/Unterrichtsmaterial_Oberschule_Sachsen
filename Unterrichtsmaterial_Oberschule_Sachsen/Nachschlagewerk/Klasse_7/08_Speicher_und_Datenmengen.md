# 8 Speicher und Datenmengen

## Warum Datenmengen wichtig sind

Jede digitale Datei benötigt Speicherplatz. Auch beim Übertragen über ein Netzwerk spielt die Datenmenge eine Rolle: Eine größere Datei benötigt bei gleicher Übertragungsgeschwindigkeit länger.

Damit man Datenmengen sinnvoll vergleichen und berechnen kann, verwendet die Informatik Einheiten wie Bit, Byte, Kilobyte, Megabyte, Gigabyte und Terabyte.

## Bit und Byte

Ein **Bit** kann zwei Zustände darstellen, meist `0` und `1`.

Acht Bit werden zu einem **Byte** zusammengefasst:

```text
1 Byte = 8 Bit
```

Ein Byte kann `2^8 = 256` verschiedene Bitmuster besitzen.

Beispiele:

```text
00000000
00000001
01000001
11111111
```

Welche Bedeutung ein solches Muster besitzt, hängt von der verwendeten Codierung ab.

> **Merke:** Bit wird mit kleinem `b`, Byte mit großem `B` abgekürzt. `8 Mb` und `8 MB` sind deshalb nicht dasselbe.

## Dezimale Speichereinheiten

Bei Speicherkapazitäten werden häufig dezimale Einheiten mit dem Faktor 1000 verwendet.

| Einheit | Kurzzeichen | Bedeutung |
|---|---|---:|
| Byte | B | 8 Bit |
| Kilobyte | kB | 1 000 B |
| Megabyte | MB | 1 000 kB = 1 000 000 B |
| Gigabyte | GB | 1 000 MB = 1 000 000 000 B |
| Terabyte | TB | 1 000 GB = 1 000 000 000 000 B |

Man kann sich die Umrechnung als Treppe vorstellen:

```text
B → kB → MB → GB → TB
     ÷1000 bei jeder Stufe nach rechts
```

In die andere Richtung wird mit 1000 multipliziert.

Beispiel:

```text
3,5 GB = 3 500 MB
```

und

```text
750 MB = 0,75 GB
```

## Binäre Speichereinheiten

Computer arbeiten binär. Deshalb begegnen auch Einheiten auf Basis von 1024.

| Einheit | Kurzzeichen | Bedeutung |
|---|---|---:|
| Kibibyte | KiB | 1 024 B |
| Mebibyte | MiB | 1 024 KiB |
| Gibibyte | GiB | 1 024 MiB |
| Tebibyte | TiB | 1 024 GiB |

Warum 1024?

```text
1024 = 2^10
```

1024 passt deshalb gut zu binären Größen.

Früher wurden die Bezeichnungen KB, MB und GB teilweise auch für 1024er-Schritte verwendet. Das führte zu Missverständnissen. Die Namen **KiB, MiB, GiB und TiB** machen deutlich, dass binäre Einheiten gemeint sind.

## Warum zeigt ein Betriebssystem manchmal eine andere Größe an?

Ein Hersteller kann beispielsweise eine Speicherkapazität dezimal angeben:

```text
1 GB = 1 000 000 000 Byte
```

Ein Programm, das mit binären Größen rechnet, teilt dieselbe Bytezahl durch `1024^3`.

Dadurch entsteht eine kleinere Zahlenangabe, obwohl physisch nicht plötzlich Speicher verschwunden ist.

Zusätzlich benötigt ein Datenträger selbst Platz für Verwaltungsstrukturen und Dateisysteminformationen. Auch deshalb steht die gesamte beworbene Kapazität normalerweise nicht vollständig für eigene Dateien zur Verfügung.

## Einheiten richtig umrechnen

### Byte in Kilobyte

Beispiel:

```text
25 000 B : 1000 = 25 kB
```

### Megabyte in Byte

```text
4 MB × 1 000 000 = 4 000 000 B
```

### Byte in Bit

```text
200 B × 8 = 1600 bit
```

### Bit in Byte

```text
8000 bit : 8 = 1000 B
```

> **Merke:** Vor einer Rechnung immer prüfen, ob Bit oder Byte und ob dezimale oder binäre Einheiten verwendet werden.

## Typische Größenordnungen

Die tatsächliche Größe hängt stark von Inhalt, Qualität, Dauer und Dateiformat ab. Als grobe Orientierung:

| Daten | mögliche Größenordnung |
|---|---|
| kurze reine Textdatei | wenige kB |
| Foto | häufig einige MB |
| Musikstück | häufig einige MB |
| kurze Videodatei | häufig viele MB bis mehrere GB |
| Computerspiel | mehrere GB bis weit über 100 GB möglich |
| SSD | häufig hunderte GB oder mehrere TB |

Diese Werte sind keine festen Regeln. Ein stark komprimiertes Foto kann wesentlich kleiner sein als ein hochauflösendes Bild im verlustfreien Format.

## Wovon hängt eine Dateigröße ab?

### Text

Bei Text spielen unter anderem eine Rolle:

- Anzahl und Art der Zeichen,
- Zeichencodierung,
- zusätzliche Formatierung,
- eingebettete Bilder,
- Kompression.

### Bilder

Bei Rasterbildern sind wichtig:

- Breite und Höhe in Pixeln,
- Farbtiefe,
- Dateiformat,
- Kompressionsverfahren und -stärke.

Ein unkomprimiertes RGB-Bild kann vereinfacht abgeschätzt werden:

```text
Breite × Höhe × Byte pro Pixel
```

Beispiel mit 1000 × 1000 Pixel und 3 Byte pro Pixel:

```text
1000 × 1000 × 3
= 3 000 000 Byte
≈ 3 MB
```

### Audio

Bei unkomprimiertem Audio hängt die Datenmenge von Abtastrate, Bittiefe, Anzahl der Kanäle und Dauer ab.

```text
Abtastrate × Bittiefe × Kanäle × Dauer
```

→ Die Rechnung wird in **Kapitel 6: Audio im Computer** ausführlich erklärt.

### Video

Video besteht vereinfacht aus vielen Bildern pro Sekunde und meist zusätzlichem Ton. Unkomprimierte Videodaten wären deshalb sehr groß. In der Praxis werden Videos fast immer komprimiert.

## Kompression

**Kompression** verringert die Datenmenge.

Bei **verlustfreier Kompression** können die ursprünglichen digitalen Daten vollständig wiederhergestellt werden.

Bei **verlustbehafteter Kompression** werden Informationen dauerhaft vereinfacht oder entfernt, um eine stärkere Verkleinerung zu ermöglichen.

Welche Art sinnvoll ist, hängt von den Daten und vom Zweck ab.

Beispiele:

- Programmdateien dürfen nicht einfach Informationen verlieren.
- Fotos und Musik können häufig sinnvoll verlustbehaftet komprimiert werden.

## Speicherarten

Nicht jeder Speicher erfüllt dieselbe Aufgabe.

### Arbeitsspeicher – RAM

Der **Arbeitsspeicher (RAM)** hält Daten und Programme bereit, die der Computer aktuell benötigt.

Typische Eigenschaften:

- sehr schneller Zugriff,
- direkt für laufende Programme wichtig,
- Inhalt geht bei normalem RAM ohne Strom verloren.

Wenn ein Computer „16 GB RAM“ besitzt, bedeutet das nicht, dass dort dauerhaft 16 GB persönliche Dateien gespeichert werden sollen.

### SSD

Eine **SSD** dient zur dauerhaften Speicherung von Betriebssystem, Programmen und Dateien.

Sie besitzt keine mechanisch bewegten Schreib-/Leseköpfe wie eine klassische Festplatte.

### Festplatte – HDD

Eine **HDD** speichert Daten magnetisch auf rotierenden Scheiben. Sie kann große Kapazitäten vergleichsweise günstig bereitstellen, besitzt aber bewegliche mechanische Teile.

### USB-Speicher und Speicherkarte

USB-Sticks und Speicherkarten verwenden Flash-Speicher. Sie sind klein und transportabel, sollten aber nicht mit einer vollständigen Datensicherungsstrategie verwechselt werden.

### Cloud-Speicher

Bei **Cloud-Speicher** liegen Daten auf entfernten Servern und werden über ein Netzwerk genutzt.

Dabei sind unter anderem wichtig:

- Internetzugang,
- Kontosicherheit,
- Datenschutz,
- Synchronisation,
- Sicherungskonzept.

## Speicher ist nicht gleich Sicherung

Eine Datei auf einer SSD ist gespeichert, aber damit noch nicht automatisch **gesichert**.

Wenn die einzige SSD ausfällt, kann die Datei verloren sein.

Eine Datensicherung benötigt zusätzliche Kopien beziehungsweise geeignete Sicherungsverfahren.

```text
Originaldatei ≠ Backup
```

Auch ein synchronisierter Cloud-Ordner ist nicht zwingend ein vollständiges Backup: Eine versehentliche Löschung kann synchronisiert werden.

→ Datensicherung wird in **Kapitel 9: Dateien, Ordner und Pfade** weiter behandelt.

## Kapazität und freier Speicher

Die **Speicherkapazität** beschreibt, wie viel ein Datenträger insgesamt aufnehmen kann.

Der **freie Speicherplatz** beschreibt, wie viel davon aktuell noch nicht belegt ist.

Beispiel:

```text
Kapazität:       512 GB
belegt:          380 GB
frei:            132 GB
```

Vereinfacht gilt:

```text
frei = Kapazität - belegt
```

## Datenmenge und Übertragungszeit

Daten müssen nicht nur gespeichert, sondern häufig auch übertragen werden.

Netzwerkgeschwindigkeiten werden oft in **Bit pro Sekunde** angegeben, beispielsweise `Mbit/s`. Dateigrößen werden dagegen meist in **Byte** angegeben.

Deshalb ist die Unterscheidung zwischen `b` und `B` besonders wichtig.

Beispiel, stark vereinfacht und ohne Protokollaufwand:

Eine Datei besitzt 100 MB.

```text
100 MB × 8 = 800 Mbit
```

Bei einer Datenrate von 100 Mbit/s ergibt sich theoretisch:

```text
800 Mbit : 100 Mbit/s = 8 s
```

In der Praxis kann die Übertragung länger dauern, beispielsweise wegen Protokollinformationen, schwankender Funkverbindung oder ausgelasteter Systeme.

## Warum ein 64-GB-Speicher nicht 64 GB frei hat

Ein neues Gerät kann weniger freien Speicher anzeigen als die angegebene Gesamtkapazität. Gründe sind beispielsweise:

- Betriebssystem,
- vorinstallierte Programme,
- Dateisystem und Verwaltungsdaten,
- reservierte Speicherbereiche.

Gesamtkapazität und für eigene Dateien verfügbarer Speicher sind deshalb nicht dasselbe.

## Datenmenge und Qualität

Mehr Daten können eine genauere Darstellung ermöglichen, beispielsweise mehr Pixel bei einem Bild oder mehr Messwerte bei Audio. Das bedeutet aber nicht automatisch bessere wahrgenommene Qualität.

Beispiele:

- Ein unscharfes Foto wird durch bloßes Vergrößern der Pixelzahl nicht plötzlich detailreich.
- Eine schlechte Audioaufnahme wird durch Speichern in einer riesigen Datei nicht gut.
- Zu starke verlustbehaftete Kompression kann dagegen sichtbare oder hörbare Fehler erzeugen.

> **Merke:** Dateigröße, Qualität und Informationsgehalt hängen zusammen, sind aber nicht dasselbe.

## Begriffe zum Nachschlagen

**Arbeitsspeicher (RAM):** schneller, normalerweise flüchtiger Speicher für aktuell benötigte Daten und Programme.

**Bit:** kleinste digitale Einheit mit zwei möglichen Zuständen; Kurzzeichen `bit` beziehungsweise häufig `b` bei Datenraten.

**Byte:** Gruppe aus acht Bit; Kurzzeichen `B`.

**Datenträger:** Medium beziehungsweise Gerät zur dauerhaften Speicherung von Daten.

**Datenrate:** Datenmenge, die in einer bestimmten Zeit übertragen wird, beispielsweise in Mbit/s.

**GiB:** Gibibyte; binäre Einheit mit `1024^3` Byte.

**Kompression:** Verringerung der benötigten Datenmenge.

**MB:** Megabyte; dezimale Einheit mit 1 000 000 Byte.

**MiB:** Mebibyte; binäre Einheit mit `1024^2` Byte.

**Speicherkapazität:** gesamte Datenmenge, die ein Speicher aufnehmen kann.

**SSD:** dauerhafter elektronischer Massenspeicher ohne mechanisch bewegte Schreib-/Leseköpfe.

→ Siehe auch **Kapitel 2: Informationen und Daten**, **Kapitel 3: Das Binärsystem**, **Kapitel 4: Texte im Computer**, **Kapitel 5: Bilder im Computer**, **Kapitel 6: Audio im Computer** und **Kapitel 9: Dateien, Ordner und Pfade**.