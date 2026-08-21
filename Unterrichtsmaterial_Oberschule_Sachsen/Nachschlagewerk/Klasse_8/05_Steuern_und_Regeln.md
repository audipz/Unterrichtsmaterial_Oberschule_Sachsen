# 5 Steuern und Regeln

## Warum Steuern und Regeln wichtig sind

Viele Informatiksysteme sollen nicht nur Daten anzeigen, sondern **etwas in der wirklichen Welt beeinflussen**. Eine Heizung verändert die Raumtemperatur, eine Ampel gibt Verkehrsrichtungen frei, ein Rollladenmotor verändert die Stellung eines Rollladens und ein Bewässerungssystem öffnet ein Ventil.

Damit technische Systeme sinnvoll reagieren können, müssen Eingaben erfasst, verarbeitet und Ausgaben erzeugt werden.

Typisch ist:

```text
Eingabe → Verarbeitung → Ausgabe
```

Bei technischen Systemen stammen Eingaben häufig von **Sensoren** und Ausgaben wirken über **Aktoren** auf die Umwelt.

![Steuerung ohne Rückkopplung und Regelung mit Sensor-Rückkopplung](grafiken/steuerung_regelung.svg)

## Sensoren und Aktoren

Ein **Sensor** erfasst eine physikalische Größe oder einen Zustand und liefert daraus ein Signal beziehungsweise Daten.

Beispiele:

| Sensor | erfasst beispielsweise |
|---|---|
| Temperatursensor | Temperatur |
| Helligkeitssensor | Lichtstärke |
| Abstandssensor | Entfernung |
| Bewegungssensor | Bewegung beziehungsweise Änderung in seiner Umgebung |
| Regensensor | Feuchtigkeit beziehungsweise Regentropfen |
| Taster | Betätigung durch einen Menschen |
| Tür-/Fensterkontakt | offen oder geschlossen |

Ein **Aktor** setzt ein Steuersignal in eine Wirkung um.

| Aktor | mögliche Wirkung |
|---|---|
| Motor | bewegen oder drehen |
| Lampe/LED | Licht erzeugen |
| Lautsprecher | Ton erzeugen |
| Heizventil | Wärmezufuhr beeinflussen |
| Magnetventil | Flüssigkeits- oder Gasfluss öffnen/schließen |
| Rollladenmotor | Rollladen bewegen |

> **Merke:** Sensoren liefern Informationen an das System. Aktoren lassen das System auf seine Umwelt einwirken.

## Was ist eine Steuerung?

Bei einer **Steuerung** beeinflussen Eingaben beziehungsweise ein festgelegtes Programm den Ablauf. Das tatsächlich erreichte Ergebnis muss nicht fortlaufend zurückgemessen und zur Korrektur verwendet werden.

Vereinfacht:

```text
Eingabe / Programm → Steuergerät → Aktor → Wirkung
```

Es gibt **keine notwendige Rückkopplung des Ergebnisses**.

### Beispiel: zeitgesteuerte Gartenbeleuchtung

```text
Wenn Uhrzeit = 20:00
    Licht einschalten
Wenn Uhrzeit = 23:00
    Licht ausschalten
```

Das System prüft nicht, ob es draußen tatsächlich dunkel ist. Es folgt der Zeitvorgabe.

### Beispiel: einfache Ampelsteuerung

```text
Rot → Rot-Gelb → Grün → Gelb → Rot
```

Der Grundablauf kann nach festen Zeiten erfolgen. Das System muss dafür nicht messen, ob die gewünschte Verkehrswirkung tatsächlich erreicht wurde.

### Beispiel: Rollladen nach Uhrzeit

```text
07:00 → Rollladen hoch
21:00 → Rollladen herunter
```

Auch das ist eine Steuerung, solange das Ergebnis nicht über einen Messwert zurückgeführt und korrigiert wird.

## Was ist eine Regelung?

Bei einer **Regelung** wird das Ergebnis beziehungsweise eine wichtige Ausgangsgröße gemessen und zurückgeführt. Das System vergleicht den aktuellen **Istwert** mit einem gewünschten **Sollwert** und beeinflusst den Prozess abhängig von der Abweichung.

Vereinfacht:

```text
Sollwert
   ↓
Vergleich → Regler → Aktor → Strecke/Prozess
   ↑                         ↓
   └──── Sensor ← Istwert ───┘
```

Dieser geschlossene Zusammenhang heißt **Regelkreis**.

### Beispiel: Raumtemperatur

```text
Sollwert: 21 °C
Istwert: 19 °C
→ stärker heizen

später:
Istwert: 21 °C
→ Heizung reduzieren oder ausschalten
```

Die Temperatur wird erneut gemessen. Die Wirkung der Heizung beeinflusst also den nächsten Messwert.

### Beispiel: Kühlschrank

Ein Temperatursensor misst die Innentemperatur. Wird es zu warm, wird die Kühlung aktiviert. Ist die Temperatur ausreichend niedrig, wird sie wieder abgeschaltet.

### Beispiel: Geschwindigkeitsregelanlage

Ein Fahrzeug soll beispielsweise 100 km/h halten. Bergauf sinkt ohne zusätzliche Leistung die Geschwindigkeit. Das System misst die tatsächliche Geschwindigkeit und erhöht die Antriebsleistung. Bergab kann es die Leistung reduzieren oder je nach System bremsend eingreifen.

## Sollwert, Istwert und Regelabweichung

Der **Sollwert** beschreibt den gewünschten Wert.

Der **Istwert** ist der aktuell gemessene Wert.

Die Differenz wird als **Regelabweichung** bezeichnet.

```text
Regelabweichung = Sollwert - Istwert
```

Beispiel:

```text
Sollwert = 22 °C
Istwert = 19 °C
Regelabweichung = 3 °C
```

Das Regelungssystem entscheidet anhand dieser Abweichung, wie es reagieren soll.

## Steuerung und Regelung vergleichen

| Merkmal | Steuerung | Regelung |
|---|---|---|
| Ablauf wird beeinflusst | ja | ja |
| Ergebnis wird zwingend zurückgemessen | nein | ja |
| Soll-/Ist-Vergleich | nicht notwendig | typisch |
| Rückkopplung | nicht notwendig | wesentlich |
| Beispiel | Rollladen nach Uhrzeit | Heizung nach Raumtemperatur |

> **Merke:** Entscheidend ist nicht, ob ein System einen Sensor besitzt. Entscheidend ist, ob der gemessene Ausgang beziehungsweise Istwert zur laufenden Korrektur des Ergebnisses zurückgeführt wird.

## Ein Sensor macht noch keine Regelung

Ein häufiger Denkfehler lautet: „Wenn ein Sensor verwendet wird, ist es eine Regelung.“ Das stimmt nicht.

Beispiel Außenlicht:

```text
Wenn Helligkeit < Grenzwert
    Lampe einschalten
```

Der Helligkeitssensor entscheidet zwar über das Einschalten. Wird aber nicht geprüft, **wie hell es durch die Lampe tatsächlich geworden ist**, wird der Ausgang nicht zurückgeführt. Das kann weiterhin als Steuerung betrachtet werden.

Eine Regelung würde beispielsweise die tatsächlich erreichte Beleuchtungsstärke messen und die Lampenleistung laufend anpassen.

## Grenzwerte und Zweipunktregelung

Viele einfache Regelungen kennen nur zwei Zustände: ein und aus.

Beispiel Heizung:

```text
Wenn Temperatur zu niedrig
    Heizung EIN
sonst
    Heizung AUS
```

Würde genau bei 21,0 °C ständig zwischen Ein und Aus gewechselt, könnte das System sehr häufig schalten. Deshalb werden häufig unterschiedliche Ein- und Ausschaltgrenzen verwendet.

Beispiel:

```text
unter 20,5 °C → Heizung EIN
über 21,5 °C  → Heizung AUS
```

Der Abstand zwischen den Schaltgrenzen verhindert unnötig häufiges Umschalten. Dieser Bereich wird **Hysterese** genannt.

## Störungen

Ein technischer Prozess wird oft von äußeren Einflüssen gestört.

Bei einer Raumheizung können das sein:

- geöffnetes Fenster,
- Sonneneinstrahlung,
- kalte Außentemperatur,
- viele Personen im Raum.

Eine Regelung kann auf die dadurch veränderte Isttemperatur reagieren. Genau darin liegt eine wichtige Stärke der Rückkopplung.

## Beispiele aus dem Alltag

### Haus: Heizung

**Regelung:**

```text
Temperatursensor → Vergleich mit Solltemperatur → Heizventil
        ↑                                      ↓
        └──────── neue Raumtemperatur ─────────┘
```

### Haus: Rollladen

**Steuerung nach Uhrzeit:**

```text
Uhrzeit → Programm → Rollladenmotor
```

Eine komplexere Anlage könnte zusätzlich Helligkeit, Wind oder Temperatur berücksichtigen.

### Haus: Regen und Fenster

Ein automatisches Dachfenster könnte bei erkanntem Regen geschlossen werden:

```text
Regensensor meldet Regen
→ Steuerung prüft Zustand
→ Fenstermotor schließt Fenster
```

Das ist zunächst eine ereignisabhängige Steuerung. Wird zusätzlich über einen Positionssensor geprüft und korrigiert, ob die gewünschte Fensterposition tatsächlich erreicht wurde, enthält das System einen rückgekoppelten Teil.

### Straßenbeleuchtung

Zeitabhängig:

```text
22:00 → Beleuchtung einschalten
```

→ Steuerung.

Auf eine gewünschte Beleuchtungsstärke geregelt:

```text
Lichtsensor misst tatsächliche Helligkeit
→ Lampenleistung wird angepasst
```

→ Regelung.

### Waschmaschine

Eine Waschmaschine enthält mehrere Steuerungs- und Regelungsaufgaben. Das Programm bestimmt beispielsweise die Reihenfolge von Wasseraufnahme, Waschen, Spülen und Schleudern. Gleichzeitig kann die Wassertemperatur über einen Temperatursensor geregelt werden.

> **Merke:** Ein reales Gerät kann gleichzeitig gesteuerte und geregelte Teilprozesse enthalten.

## Automatisierung

**Automatisierung** bedeutet, dass technische Abläufe ganz oder teilweise selbstständig nach festgelegten Regeln, Programmen und Messwerten durchgeführt werden.

Steuerungen und Regelungen sind wichtige Bestandteile automatisierter Systeme.

Beispiele:

- Aufzug,
- Verkehrsampel,
- Heizungsanlage,
- Bewässerung,
- Produktionsmaschine,
- Roboter,
- Smart Home.

Ein automatisiertes System kann aus vielen miteinander verbundenen Steuerungen und Regelkreisen bestehen.

## Beispiel Smart Home

Ein Haus kann verschiedene Datenquellen und Aktoren kombinieren:

```text
Temperatur ───────→ Heizung
Helligkeit ───────→ Rollladen
Regen ────────────→ Dachfenster
Fensterkontakt ───→ Heizungslogik
Uhrzeit ──────────→ Beleuchtung
```

Dabei muss für jede Funktion einzeln betrachtet werden, ob sie eine **Steuerung** oder eine **Regelung** darstellt.

Beispiel Heizung bei offenem Fenster:

```text
Wenn Fenster offen
    Heizventil schließen
sonst
    Raumtemperatur regeln
```

Hier werden Steuerungslogik und Temperaturregelung kombiniert.

## Sicherheit und Fehlverhalten

Automatisierte Systeme wirken auf die reale Welt. Fehler können deshalb Folgen haben.

Beispiele:

- Ein defekter Temperatursensor meldet einen falschen Wert.
- Ein Rollladen fährt trotz Hindernis weiter.
- Ein Bewässerungssystem erkennt einen Rohrbruch nicht.
- Eine Türsteuerung reagiert falsch auf einen Sensor.

Deshalb können zusätzliche Maßnahmen erforderlich sein:

- Grenzwerte,
- Plausibilitätsprüfungen,
- Not-Aus-Funktionen,
- mehrere Sensoren,
- sichere Standardzustände,
- Fehlermeldungen.

## Vom Messwert zum Programm

Ein Sensor liefert einen Messwert. Ein Programm muss daraus eine Entscheidung ableiten.

Beispiel Lüftersteuerung:

```text
temperatur := Sensorwert

WENN temperatur > 30 DANN
    Lüfter einschalten
SONST
    Lüfter ausschalten
ENDE WENN
```

Hier werden Kenntnisse aus **Kapitel 2 Algorithmen** praktisch verwendet: Variable, Vergleich, Auswahl und Ein-/Ausgabe beziehungsweise Aktorsteuerung.

## Begriffe zum Nachschlagen

**Aktor:** Bauteil, das ein Signal in eine physische Wirkung umsetzt.

**Automatisierung:** selbstständiges Ausführen technischer Abläufe nach festgelegten Programmen, Regeln und Messwerten.

**Hysterese:** Abstand zwischen unterschiedlichen Schaltgrenzen, der häufiges Hin- und Herschalten verhindern kann.

**Istwert:** aktuell gemessener Wert einer betrachteten Größe.

**Regelabweichung:** Differenz zwischen Sollwert und Istwert.

**Regelkreis:** geschlossener Wirkungsablauf einer Regelung mit Rückführung des Istwerts.

**Regelung:** Beeinflussung eines Prozesses mithilfe einer Rückkopplung des gemessenen Ergebnisses.

**Sensor:** Bauteil zur Erfassung einer physikalischen Größe oder eines Zustands.

**Sollwert:** gewünschter Zielwert.

**Steuerung:** gezielte Beeinflussung eines Ablaufs ohne notwendige Rückkopplung des tatsächlich erreichten Ergebnisses.

**Störgröße:** äußerer Einfluss, der den betrachteten Prozess verändert.

→ Siehe auch **Kapitel 2 Algorithmen** zu Bedingungen und Variablen sowie **Kapitel 3 Robot Karol** zu programmierten Abläufen. In Klasse 9 wird Automatisierung weiter vertieft.