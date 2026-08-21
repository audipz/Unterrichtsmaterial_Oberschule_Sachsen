# 13 Werkzeuge

## Informatikwerkzeuge passend auswählen

Programme sind **Werkzeuge**. Entscheidend ist nicht, möglichst viele Menüpunkte auswendig zu kennen, sondern zu verstehen:

- welche Aufgabe gelöst werden soll,
- welches Werkzeug dafür geeignet ist,
- in welchem Dateiformat gearbeitet wird,
- wie das Ergebnis gespeichert und weitergegeben wird.

Für dieselbe Aufgabe können unterschiedliche Programme geeignet sein. Die Grundideen bleiben oft ähnlich, auch wenn Schaltflächen und Menüs anders aussehen.

> **Merke:** Lerne nicht nur, wo ein Knopf liegt. Verstehe, welche Funktion du benötigst und warum.

## Dateimanager

Ein **Dateimanager** dient zum Verwalten von Dateien und Ordnern.

Typische Funktionen sind:

- Ordner erstellen,
- Dateien kopieren,
- Dateien verschieben,
- Dateien umbenennen,
- Dateien löschen,
- Eigenschaften anzeigen,
- suchen,
- sortieren.

### Kopieren und Verschieben unterscheiden

Beim **Kopieren** bleibt das Original erhalten und eine zusätzliche Kopie entsteht.

```text
Original → bleibt erhalten
       ↘ Kopie an anderem Ort
```

Beim **Verschieben** soll sich die Datei anschließend an einem anderen Ort befinden.

```text
alter Ort → neuer Ort
```

### Papierkorb ist kein Backup

Gelöschte Dateien landen bei vielen Systemen zunächst in einem Papierkorb und können eventuell wiederhergestellt werden. Das ist aber keine zuverlässige Datensicherung.

Ein Papierkorb kann geleert werden, und nicht jeder Löschvorgang verwendet überhaupt einen Papierkorb.

→ Dateien, Ordner und Pfade werden in **Kapitel 9** ausführlich erklärt; Backup und Speicher in **Kapitel 8**.

## Texteditor und Textverarbeitung sind nicht dasselbe

Ein **Texteditor** bearbeitet vor allem reinen Text. Er eignet sich beispielsweise für:

- Quellcode,
- Markdown,
- Konfigurationsdateien,
- einfache Textdateien.

Eine **Textverarbeitung** ist dagegen auf gestaltete Dokumente ausgerichtet, beispielsweise Briefe, Berichte oder längere Arbeiten.

| Texteditor | Textverarbeitung |
|---|---|
| arbeitet häufig mit reinem Text | arbeitet mit formatierten Dokumenten |
| geeignet für Code und Markdown | geeignet für Berichte und Briefe |
| Formatierung oft durch Textzeichen/Syntax | Formatierung meist direkt sichtbar |
| Beispiel-Dateien: `.txt`, `.md`, Quellcode | beispielsweise `.docx`, `.odt` |

## Textverarbeitung

Eine Textverarbeitung dient zum Erstellen strukturierter Dokumente.

Typische Funktionen sind:

- Überschriften und Absätze formatieren,
- Listen erstellen,
- Tabellen einfügen,
- Bilder einfügen und beschriften,
- Kopf- und Fußzeilen verwenden,
- Seitenzahlen einfügen,
- Rechtschreibung prüfen,
- Inhaltsverzeichnisse erzeugen,
- als PDF exportieren.

## Formatvorlagen

Statt jede Überschrift einzeln beispielsweise auf `16 pt, fett, blau` zu setzen, sollte eine **Formatvorlage** wie `Überschrift 1` verwendet werden.

Das hat mehrere Vorteile:

- einheitliches Aussehen,
- schnelle Änderungen im gesamten Dokument,
- automatische Gliederung,
- bessere Grundlage für Inhaltsverzeichnisse,
- häufig bessere Zugänglichkeit für Hilfstechniken.

> **Merke:** Die Bedeutung eines Textteils sollte möglichst über seine Struktur festgelegt werden, nicht nur über sein Aussehen.

## Präsentationsprogramme

Eine Präsentation unterstützt einen Vortrag. Die Folien sind normalerweise **nicht der vollständige Vortragstext**.

Eine gute Folie besitzt beispielsweise:

- eine klare Aussage,
- gut lesbare Schrift,
- ausreichend Kontrast,
- wenige wesentliche Elemente,
- sinnvolle Bilder oder Diagramme,
- Quellenangaben bei fremden Inhalten.

### Folie und Handzettel haben unterschiedliche Aufgaben

Eine Präsentationsfolie muss aus einiger Entfernung schnell verständlich sein. Ein Handzettel oder Nachschlagewerk darf dagegen deutlich mehr Text enthalten.

Darum ist es selten sinnvoll, eine vollständige Textseite unverändert auf eine Folie zu kopieren.

### Animationen gezielt verwenden

Animationen und Übergänge können Zusammenhänge verdeutlichen, beispielsweise wenn ein Ablauf schrittweise erklärt wird.

Zu viele Effekte können jedoch von der eigentlichen Information ablenken.

## Tabellenkalkulation

Eine **Tabellenkalkulation** ordnet Daten in Zellen. Die Zellen sind in Zeilen und Spalten angeordnet.

Eine Zelladresse kann beispielsweise lauten:

```text
B4
```

Dabei bezeichnet `B` die Spalte und `4` die Zeile.

### Werte und Formeln

Eine Zelle kann beispielsweise enthalten:

```text
25
```

oder eine Formel:

```text
=A1+B1
```

Formeln beginnen in vielen Tabellenkalkulationen mit `=`.

Weitere Beispiele:

```text
=SUMME(B2:B8)
=MITTELWERT(C2:C10)
```

Funktionsnamen können je nach Programm und Sprache unterschiedlich heißen.

### Zellbezüge

Statt Zahlen direkt in eine Formel einzutragen, kann auf andere Zellen verwiesen werden.

Beispiel:

```text
A1 = 5
B1 = 7
C1 = A1+B1
```

Ändert sich später `A1`, wird `C1` normalerweise automatisch neu berechnet.

Das ist ein wesentlicher Vorteil gegenüber dem manuellen Ausrechnen und Eintippen jedes Ergebnisses.

## Daten sinnvoll tabellarisch organisieren

Eine Tabelle sollte eine klare Struktur besitzen.

Beispiel:

| Datum | Temperatur in °C | Niederschlag in mm |
|---|---:|---:|
| Montag | 18 | 0 |
| Dienstag | 16 | 4 |
| Mittwoch | 20 | 1 |

Eine Spalte sollte möglichst eine eindeutige Bedeutung besitzen. Überschriften und Einheiten helfen beim Verstehen der Daten.

## Diagramme

Tabellenkalkulationen können Daten als Diagramm darstellen.

Welches Diagramm sinnvoll ist, hängt von der Aussage ab.

Beispiele:

- **Säulen-/Balkendiagramm:** Werte von Kategorien vergleichen,
- **Liniendiagramm:** Entwicklung über eine geordnete Folge, häufig über die Zeit,
- **Kreisdiagramm:** Anteile eines Ganzen in geeigneten Fällen.

Ein Diagramm sollte Achsen, Einheiten und eine verständliche Beschriftung besitzen.

→ Darstellungsformen werden in **Kapitel 10** genauer verglichen.

## Browser

Ein **Webbrowser** ist ein Programm zum Abrufen und Darstellen von Webinhalten.

Typische Browserfunktionen sind:

- Internetadressen öffnen,
- Tabs verwenden,
- Lesezeichen speichern,
- Dateien herunterladen,
- Verlauf anzeigen,
- Webseiten durchsuchen.

Ein Browser ist nicht dasselbe wie eine Suchmaschine.

```text
Browser      → Programm auf dem Gerät
Suchmaschine → Webdienst zum Suchen von Inhalten
```

Eine Suchmaschine wird normalerweise innerhalb eines Browsers aufgerufen.

## Internetadresse und Suchbegriff unterscheiden

In die Adresszeile eines Browsers kann häufig sowohl eine Webadresse als auch ein Suchbegriff eingegeben werden.

Trotzdem sind beide Dinge fachlich verschieden.

```text
https://example.org   → Adresse
Binärsystem Erklärung → Suchanfrage
```

Der Browser kann eine Suchanfrage an die eingestellte Suchmaschine weitergeben.

## Recherche

Bei einer Recherche sollte nicht nur der erste Treffer übernommen werden.

Hilfreiche Fragen sind:

- Wer veröffentlicht die Information?
- Welche Fachkenntnis oder Verantwortung besitzt die Quelle?
- Wann wurde sie veröffentlicht oder aktualisiert?
- Werden Belege oder Quellen genannt?
- Ist die Information für meine konkrete Frage geeignet?
- Passt die Aussage zu weiteren verlässlichen Quellen?
- Handelt es sich um Information, Meinung, Satire oder Werbung?

### Primär- und Sekundärquellen – erste Einordnung

Eine **Primärquelle** stammt möglichst nah vom untersuchten Gegenstand oder Ereignis selbst, beispielsweise eine offizielle Veröffentlichung oder ein Originaldokument.

Eine **Sekundärquelle** beschreibt, ordnet oder bewertet Informationen aus anderen Quellen.

Beide können nützlich sein. Entscheidend ist, welche Quelle für die konkrete Frage geeignet und zuverlässig ist.

## Suchen im Web

Eine Suchanfrage sollte möglichst die wichtigen Begriffe enthalten.

Statt:

```text
computer
```

ist beispielsweise:

```text
Unterschied RAM SSD einfach erklärt
```

genauer.

Suchmaschinen können zusätzliche Suchoperatoren unterstützen. Ein häufiges Beispiel sind Anführungszeichen für die Suche nach einer genauen Wortfolge:

```text
"Scalable Vector Graphics"
```

Welche Operatoren unterstützt werden, hängt von der verwendeten Suchmaschine ab.

## Downloads

Eine heruntergeladene Datei stammt aus einer externen Quelle. Vor dem Öffnen sollte deshalb überlegt werden:

- Ist die Quelle vertrauenswürdig?
- Habe ich diese Datei erwartet?
- Passt Dateityp und Dateiname zur erwarteten Datei?
- Ist das System aktuell?

Besondere Vorsicht ist bei unerwarteten ausführbaren Dateien oder Anhängen geboten.

## Screenshots

Screenshots können beispielsweise:

- einen Programmzustand dokumentieren,
- einen Fehler zeigen,
- eine Anleitung verständlicher machen,
- einen Ausschnitt einer Oberfläche festhalten.

Vor dem Teilen müssen persönliche oder vertrauliche Informationen geprüft werden.

Dazu gehören beispielsweise:

- Namen,
- E-Mail-Adressen,
- Nachrichten,
- Benutzernamen,
- Kontonummern oder Kennungen,
- Browser-Tabs,
- Dateipfade mit persönlichen Namen.

Ein einfaches Übermalen sensibler Daten muss so erfolgen, dass die ursprünglichen Informationen in der tatsächlich weitergegebenen Datei nicht mehr sichtbar beziehungsweise wiederherstellbar sind.

## PDF

**PDF (Portable Document Format)** ist für die Weitergabe von Dokumenten verbreitet, weil das Seitenlayout weitgehend erhalten bleibt.

Geeignet ist PDF beispielsweise für:

- fertige Berichte,
- Handreichungen,
- Formulare,
- Ausdrucke.

Eine PDF-Datei ist jedoch nicht automatisch die beste Arbeitsdatei zum späteren Bearbeiten.

Deshalb sollte die bearbeitbare Quelldatei häufig zusätzlich aufbewahrt werden.

## Exportieren und Speichern

**Speichern** bedeutet meist, eine Datei im Arbeitsformat des verwendeten Programms abzulegen.

**Exportieren** bedeutet häufig, eine zusätzliche Datei für einen bestimmten Verwendungszweck zu erzeugen.

Beispiel:

```text
Arbeitsdatei: Referat.odt
Export:       Referat.pdf
```

oder:

```text
Arbeitsdatei: Grafik.svg
Export:       Grafik.png
```

Beim Export können Informationen verloren gehen, die nur für die spätere Bearbeitung wichtig sind.

## Zwischenablage

Beim Kopieren und Ausschneiden werden Daten häufig vorübergehend in der **Zwischenablage** gespeichert.

```text
Kopieren → Zwischenablage → Einfügen
```

Beim **Kopieren** bleibt das Original normalerweise erhalten.

Beim **Ausschneiden** soll das ausgewählte Element beim anschließenden Einfügen an eine andere Stelle verschoben werden.

Die Zwischenablage kann je nach System Text, Bilder, Dateien oder andere Daten enthalten.

## Rückgängig und Wiederholen

Viele Programme besitzen die Funktionen:

```text
Rückgängig
Wiederholen
```

Damit lassen sich Bearbeitungsschritte zurücknehmen oder erneut anwenden.

Die Zahl der gespeicherten Schritte ist je nach Programm begrenzt. Rückgängig ist deshalb kein Ersatz für regelmäßiges Speichern oder eine Datensicherung.

## Tastenkombinationen

Viele Programme bieten Tastenkombinationen für häufige Befehle. Beispiele sind je nach Betriebssystem und Programm:

```text
Kopieren
Einfügen
Speichern
Rückgängig
Suchen
```

Die genauen Tasten können sich unterscheiden. Wichtiger als das Auswendiglernen einer bestimmten Kombination ist zunächst die Funktion dahinter.

## Versionsstände

Bei längeren Arbeiten kann es hilfreich sein, frühere Stände nachvollziehen zu können.

Ein einfaches Verfahren sind bewusst gespeicherte Versionen. Moderne Cloud-Dienste und andere Werkzeuge können teilweise automatisch einen **Versionsverlauf** führen.

Dabei sollte man trotzdem verstehen, dass Versionsverlauf, Synchronisation und Backup unterschiedliche Dinge sein können.

## Barrierearme und verständliche Dokumente

Werkzeuge sollten nicht nur schöne, sondern auch verständliche Ergebnisse erzeugen.

Hilfreich sind beispielsweise:

- echte Überschriften statt nur großer fetter Schrift,
- ausreichender Kontrast,
- verständliche Linktexte,
- Alternativtexte für wichtige Bilder, wenn das verwendete Format dies unterstützt,
- Tabellen mit klaren Überschriften,
- Information nicht ausschließlich durch Farbe vermitteln.

Diese Maßnahmen helfen vielen Menschen und verbessern häufig auch allgemein die Struktur eines Dokuments.

## Welches Werkzeug passt?

| Aufgabe | typisches Werkzeug |
|---|---|
| Dateien organisieren | Dateimanager |
| Quelltext/Markdown bearbeiten | Texteditor |
| Bericht schreiben | Textverarbeitung |
| Vortrag unterstützen | Präsentationsprogramm |
| Daten berechnen | Tabellenkalkulation |
| Webinhalte anzeigen | Browser |
| Informationen suchen | Suchmaschine über einen Browser |
| Rasterbild bearbeiten | Bildbearbeitungsprogramm |
| Vektorgrafik erstellen | Vektorgrafikprogramm |

Die Grenzen sind nicht immer scharf. Manche Programme können mehrere dieser Aufgaben übernehmen.

> **Merke:** Das passende Werkzeug richtet sich nach Aufgabe, Daten und gewünschtem Ergebnis – nicht danach, welches Programm gerade am bekanntesten ist.

## Begriffe zum Nachschlagen

**Browser:** Programm zum Abrufen und Darstellen von Webinhalten.

**Dateimanager:** Programm zum Organisieren von Dateien und Ordnern.

**Export:** Erzeugen einer Datei für einen bestimmten Verwendungszweck oder ein anderes Format.

**Formatvorlage:** gespeicherte Formatierungs- und Strukturregeln für wiederkehrende Dokumentelemente.

**PDF:** Portable Document Format; verbreitetes Format zur weitgehend layouttreuen Weitergabe von Dokumenten.

**Primärquelle:** Quelle, die möglichst unmittelbar vom untersuchten Gegenstand, Urheber oder Ereignis stammt.

**Sekundärquelle:** Quelle, die andere Informationen beschreibt, einordnet oder auswertet.

**Suchmaschine:** Webdienst zum Auffinden von Webinhalten anhand einer Suchanfrage.

**Tabellenkalkulation:** Programm zum Organisieren, Berechnen und Darstellen tabellarischer Daten.

**Texteditor:** Programm zum Bearbeiten von Text, insbesondere reinem Text ohne klassische Seitenformatierung.

**Textverarbeitung:** Programm zum Erstellen und Gestalten strukturierter Textdokumente.

**Versionsverlauf:** gespeicherte Folge früherer Bearbeitungsstände.

**Zwischenablage:** temporärer Speicher für kopierte oder ausgeschnittene Daten.

→ Siehe auch **Kapitel 8: Speicher und Datenmengen**, **Kapitel 9: Dateien, Ordner und Pfade**, **Kapitel 10: Darstellung von Informationen**, **Kapitel 12: Daten verantwortungsvoll nutzen** und **Kapitel 16: Computergrafik**.