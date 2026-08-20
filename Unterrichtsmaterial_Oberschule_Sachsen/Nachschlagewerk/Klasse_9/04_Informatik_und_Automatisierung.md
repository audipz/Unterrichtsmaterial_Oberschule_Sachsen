# 4 Informatik und Automatisierung

## Automatisierte Systeme

Ein automatisiertes System führt Abläufe nach vorgegebenen Regeln selbstständig aus. Häufig erfasst es seine Umgebung, verarbeitet Daten und beeinflusst anschließend einen Prozess.

```text
Sensor → Verarbeitung → Aktor
```

Dieses Modell erweitert die aus früheren Klassen bekannte Vorstellung von Eingabe, Verarbeitung und Ausgabe.

## Sensoren und Aktoren

Ein **Sensor** erfasst eine physikalische Größe, beispielsweise Temperatur, Helligkeit, Abstand oder Bewegung. Ein **Aktor** wirkt auf die Umgebung ein, beispielsweise durch einen Motor, eine Lampe, ein Ventil oder einen Lautsprecher.

## Steuerung und Regelung

Bei einer **Steuerung** wird ein Ablauf beeinflusst, ohne dass das Ergebnis ständig zurückgemeldet werden muss.

Bei einer **Regelung** wird ein Istwert gemessen und mit einem Sollwert verglichen. Das System reagiert auf Abweichungen.

```text
Sollwert
   ↓
Vergleich ← Istwert ← Sensor
   ↓
Verarbeitung
   ↓
Aktor → Prozess
```

Ein Thermostat ist ein typisches Beispiel: Die Temperatur wird gemessen und die Heizung abhängig von Soll- und Istwert geschaltet.

## Smart Home

In einem Smart Home können Sensoren, Aktoren und vernetzte Steuerungen zusammenarbeiten. Beispiele sind Heizungsregelung, Beleuchtung oder Rollläden.

Dabei entstehen Sicherheits- und Datenschutzfragen: Wer kann auf Geräte zugreifen? Welche Daten werden gespeichert? Funktioniert das System bei einem Netzausfall?

## Bots

Ein **Bot** ist ein Programm, das bestimmte Aufgaben automatisiert ausführt. Bots können nützlich sein, beispielsweise für Auskünfte oder wiederkehrende Verwaltungsaufgaben. In sozialen Netzwerken können sie aber auch eingesetzt werden, um Inhalte massenhaft zu verbreiten oder künstlich Zustimmung vorzutäuschen.

## Automatisierte Auswahl von Inhalten

Plattformen verwenden Algorithmen, um Inhalte zu sortieren oder zu empfehlen. Dadurch sieht nicht jeder Nutzer dieselben Beiträge. Kriterien und Datenbasis solcher Auswahlverfahren beeinflussen, welche Informationen sichtbar werden.

## Verantwortung

Automatisierte Systeme können Fehler verursachen. Deshalb muss geklärt werden:

- Wie werden Fehler erkannt?
- Gibt es eine sichere Rückfalllösung?
- Kann ein Mensch eingreifen?
- Sind Entscheidungen nachvollziehbar?
- Welche Folgen hat ein falsches Ergebnis?

> **Merke:** Automatisierung überträgt Aufgaben an Informatiksysteme, nicht automatisch die Verantwortung für ihre Folgen.

## Begriffe zum Nachschlagen

**Aktor:** Bauteil, das auf einen Prozess oder die Umgebung einwirkt.

**Automatisierung:** selbstständige Durchführung von Abläufen durch technische Systeme.

**Bot:** Programm zur automatisierten Ausführung bestimmter Aufgaben.

**Regelung:** Beeinflussung eines Prozesses unter laufender Rückmeldung des Istwertes.

**Sensor:** Bauteil zur Erfassung einer physikalischen Größe.

**Steuerung:** Beeinflussung eines Ablaufs ohne notwendige laufende Rückmeldung des Ergebnisses.