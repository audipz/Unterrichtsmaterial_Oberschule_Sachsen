# Lösung – Steuern und Regeln

Helligkeitssensor/Temperaturfühler = Sensoren; Lampe/Motor = Aktoren. Eine zeitgesteuerte Lampe folgt einem Ablauf; eine Regelung vergleicht fortlaufend Ist- und Sollwert und erfasst die Wirkung zurück.

Heizung: Sollwert gewünschte Temperatur, Istwert Messwert, Sensor Temperaturfühler, Verarbeitung Vergleich, Aktor Heizung/Ventil.

```text
wenn Helligkeit < 40 dann Lampe EIN
wenn Helligkeit > 55 dann Lampe AUS
```
Zwei Grenzwerte verhindern häufiges Umschalten nahe einem einzigen Schwellwert.
