# CSV-Komplettpaket – Informationen und Daten

Dieses Paket gehört in:

`Klasse_9/01_Informationen_und_Daten/05_Dateien/`

## Technische Konventionen

- Zeichencodierung: UTF-8 mit BOM
- Trennzeichen: Semikolon
- Dezimaltrennzeichen: Komma
- Datum: TT.MM.JJJJ
- Erste Zeile: eindeutige Spaltennamen
- Personen- und Vorgangsdaten sind vollständig fiktiv.

## Enthaltene Dateien

| Datei | Datensätze | Unterrichtlicher Schwerpunkt |
|---|---:|---|
| `bestellungen.csv` | 220 | Filtern, Sortieren, Summen, Statusauswertung |
| `lager.csv` | 16 | Bedingungen, Mindestbestand, Preisvergleich |
| `mensa_verkauf.csv` | 150 | Gruppieren, Durchschnitt, Umsatz, Diagramme |
| `bibliothek.csv` | 180 | Verknüpfte Attribute, Datumslogik, Datenschutz |
| `sensor_temperatur.csv` | 1008 | Zeitreihen, Extremwerte, Korrelationen |
| `sensor_luftfeuchte.csv` | 1008 | Zusammenführen über Datum/Uhrzeit, Klimaauswertung |
| `netzwerk_logs.csv` | 500 | Logdateien, Protokolle, Auffälligkeiten |
| `fehlermeldungen.csv` | 160 | Häufigkeiten, Prioritäten, Lösungszeiten |
| `schuelerprojekte.csv` | 8 | Projektvergleich, Budget und Fortschritt |

## Konsistenzhinweise

- `artikelnummer` verbindet `bestellungen.csv` und `lager.csv`.
- `datum`, `uhrzeit` und `raum` verbinden die beiden Sensordateien.
- Geldwerte sind als deutsch formatierte Dezimalzahlen gespeichert.
- Leere Felder sind fachlich begründet, etwa bei offenen Bestellungen oder ungelösten Fehlern.

## Beispielaufgaben

1. Ermittle alle Artikel, deren Bestand unter dem Mindestbestand liegt.
2. Berechne den Umsatz der Mensa je Menükategorie.
3. Untersuche, ob CO₂-Werte und Personenzahl zusammenhängen.
4. Bestimme die drei häufigsten blockierten Netzwerkdienste.
5. Vergleiche geplantes Budget und tatsächliche Ausgaben der Projekte.
6. Prüfe, welche Bibliotheksausleihen verspätet zurückgegeben wurden.

## Lizenz

Soweit nicht anders angegeben, steht dieses Datenpaket unter CC BY 4.0. Die Datensätze wurden eigens für Unterrichtszwecke erzeugt und enthalten keine realen personenbezogenen Daten.
