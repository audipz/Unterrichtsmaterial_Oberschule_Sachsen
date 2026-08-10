# Audit Klasse 9

Stand: bereitgestelltes Repository-Archiv

## Zusammenfassung

Die Architektur von Klasse 9 ist grundsätzlich tragfähig. Die wichtigsten Probleme liegen aktuell nicht in fehlenden Werkteilen, sondern in Altbeständen und einer teilweise nicht vollständig synchronisierten Umstrukturierung.

## Rot – vor weiterem Ausbau korrigieren

### 1. Informationen und Daten: zwei Kapitelstände parallel

Im Arbeitsheft existieren doppelte Nummern:

- `14_Eindeutige_Ordnungsmerkmale.md`
- `14_Tabellen_verbinden.md`

sowie:

- `31_Verschiedene_Arten_von_KI.md`
- `31_Chancen_und_Grenzen_von_KI.md`

Der Lehrerband enthält jeweils nur die neueren Varianten:

- `14_Eindeutige_Ordnungsmerkmale.md`
- `31_Verschiedene_Arten_von_KI.md`

Die beiden zusätzlichen Arbeitsheftdateien sind daher sehr wahrscheinlich Altbestände und müssen fachlich geprüft und entweder entfernt oder sauber neu eingeordnet werden.

### 2. Lösungen und Präsentationen nicht mit neuer Arbeitsheftgliederung synchron

Arbeitsheft und Lehrerband verwenden ab Kapitel 4 die neue Gliederung.

Beispiele:

- Arbeitsheft `04_Informationen_vergleichen.md`
- Lösung/Präsentation `04_Sind_alle_Informationen_zuverlaessig.md`

- Arbeitsheft `05_Daten_oder_Information.md`
- Lösung/Präsentation `05_Wie_vergleichen_wir_Moeglichkeiten.md`

Diese Abweichung zieht sich über einen großen Teil des Lernbereichs bis zum KI-/Datenschutzblock.

Die neuen SQL-Kapitel 35–39 sind dagegen wieder synchron.

Folge:
Eine Lehrkraft kann anhand der Kapitelnummer derzeit nicht zuverlässig die passende Lösung bzw. Präsentation zum Arbeitsheft finden.

### 3. Generiertes DOCX liegt in den Quellen

Datei:

`Klasse_9/02_Komplexaufgabe_zur_Algorithmierung/01_Arbeitsheft/Arbeitsheft_Lineare_Algorithmen.docx`

Das widerspricht der festgelegten Single-Source-of-Truth-Regel: Markdown ist Quelle, DOCX ist Build-Artefakt.

Die Datei sollte aus dem Quellbereich entfernt werden.

### 4. Leere Altdateien in der Komplexaufgabe

Vorhanden und leer:

- `01_Arbeitsheft/Neue Datei`
- `01_Arbeitsheft/README_2.txt`

Beide sollten entfernt werden.

### 5. Root-README des Lehrwerks ist leer

`Unterrichtsmaterial_Oberschule_Sachsen/README.md` besitzt 0 Byte.

Das ist der zentrale Einstiegspunkt innerhalb des Lehrwerks und sollte eine kurze Struktur- und Nutzungsübersicht enthalten.

---

## Gelb – im nächsten Review prüfen

### 6. `Arbeitsheft_Lineare_Algorithmen.md` als zusätzlicher Altbestand

Neben den modularen Dateien `00_Einstieg.md` bis `12_Kompetenzcheck.md` existiert zusätzlich:

`Arbeitsheft_Lineare_Algorithmen.md`

Die Datei ist ein kurzes eigenständiges Arbeitsheft und überschneidet sich mit den modularen Inhalten.

Prüfen:
- Wird sie noch bewusst benötigt?
- Falls nein: entfernen.
- Falls ja: außerhalb des zu aggregierenden Werkteilordners ablegen, damit der Build sie nicht zusätzlich in das Sammeldokument aufnimmt.

### 7. Arbeitsheft und Lehrerband im Wahlbereich sind bewusst unterschiedlich strukturiert

Arbeitsheft:
- elf fachliche Schülerkapitel

Lehrerband:
- Unterrichtsverlauf
- didaktische Hinweise
- Lösungshinweise
- Differenzierung
- Bewertung

Das ist kein Fehler. Hier sollte ausdrücklich **keine** 1:1-Dateizuordnung erzwungen werden.

### 8. Komplexaufgabe ebenfalls nicht vollständig 1:1 aufgebaut

Das Arbeitsheft besitzt einen Lernweg mit vielen Einzelkapiteln, während Lehrerband, Lösungen und Präsentationen teilweise zusammenfassend organisiert sind.

Auch dies kann didaktisch sinnvoll sein. Prüfkriterium sollte hier nicht gleicher Dateiname, sondern vollständige Abdeckung sein.

---

## Grün – positiv

### Werkteilstruktur

Alle drei Lernbereiche der Klasse 9 besitzen die vereinbarte Werkteilstruktur:

- `01_Arbeitsheft`
- `02_Lehrerband`
- `03_Material`
- `04_Loesungen`
- `05_Praesentationen`
- `06_Dateien`
- `07_Quellen`
- `08_Bilder`
- `09_Lernkontrollen`

### SQL-/CRUD-Erweiterung

Die neuen Kapitel 35–39 sind zwischen

- Arbeitsheft
- Lehrerband
- Lösungen
- Präsentationen

sauber synchronisiert.

### Lernkontrollen

`Informationen und Daten` besitzt neun Lernkontrollen; die technische JSON-Ablage ist getrennt.

Die Komplexaufgabe und der Wahlbereich besitzen ebenfalls eigene Lernkontrollen.

### H1

Im geprüften Bereich Klasse 9 wurden keine Markdown-Dateien ohne H1 gefunden.

---

# Empfohlene Reihenfolge der Bereinigung

1. Leere Dateien und generiertes DOCX entfernen.
2. Die beiden doppelten Arbeitsheftkapitel `14` und `31` fachlich entscheiden.
3. Arbeitsheft/Lehrerband gegen Lösungen/Präsentationen in `Informationen und Daten` neu zuordnen.
4. Root-README füllen.
5. Erst danach Feinaudit auf Sprache, Didaktik und Querverweise.

## Wichtig

Die größte Baustelle ist aktuell nicht fehlender Unterrichtsinhalt, sondern die historische Umstrukturierung des Lernbereichs `Informationen und Daten`.

Bevor weitere Kapitel ergänzt werden, sollte dort eine eindeutige verbindliche Kapitelreihenfolge hergestellt werden.
