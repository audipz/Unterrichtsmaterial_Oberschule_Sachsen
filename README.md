# Informatik-Unterrichtsmaterial Sachsen (OER)

[**Aktuelle Unterrichtsmaterialien als ZIP herunterladen**](https://github.com/audipz/Unterrichtsmaterial_Oberschule_Sachsen/releases/download/unterrichtsmaterial-aktuell/Unterrichtsmaterial_Oberschule_Sachsen.zip)

Dieses Repository enthält ein frei verfügbares, lehrplanorientiertes Unterrichtswerk für das Fach **Informatik**. Der aktuelle Schwerpunkt liegt auf der **Oberschule in Sachsen**. Die Architektur und die Inhalte werden jedoch so weiterentwickelt, dass künftig auch **Gymnasium**, weitere Schularten, weitere Bundesländer und mehrsprachige Varianten unterstützt werden können.

Das Projekt entsteht als **Open Educational Resource (OER)** und kann frei genutzt, angepasst und weiterentwickelt werden.

---

# Aktueller Stand

Die Materialien werden inzwischen für mehrere Klassenstufen aufgebaut und über eine gemeinsame Build-Infrastruktur erzeugt. Zum Projekt gehören insbesondere:

- Nachschlagewerke
- Arbeitshefte und Arbeitsblätter
- Lehrerbände
- Lösungen
- Präsentationen
- Beispieldateien
- Lernkontrollen
- Quellen und Abbildungen
- technische und didaktische Dokumentation

Neben den klassischen Unterrichtsmaterialien entsteht im Ordner `Lernplattform/` eine eigenständige Lernplattform. Sie soll Schülerinnen und Schülern ermöglichen, Übungen und Arbeitsmaterialien selbstständig zu bearbeiten, ihren Stand zu speichern und später weiterzuarbeiten.

Die Lernplattform ist **keine Plattform zur Überwachung von Schülerantworten**. Lehrkräfte sollen den Bearbeitungsstand und die Vollständigkeit sehen können, nicht jedoch die konkreten Inhalte der Schülerantworten.

---

# Didaktisches Konzept

Das Unterrichtswerk folgt einem problemorientierten Ansatz. Die Schülerinnen und Schüler

- entdecken Zusammenhänge,
- entwickeln eigene Lösungsstrategien,
- lernen informatische Begriffe aus konkreten Situationen heraus,
- wenden ihr Wissen in realitätsnahen Aufgaben und Projekten an und
- können zentrale Inhalte in Nachschlagewerken erneut aufrufen.

Eine typische Lernstruktur ist:

```text
Problem
↓
Fragen stellen
↓
Informationen sammeln
↓
Lösungen entwickeln
↓
Begriff einführen
↓
Anwenden
↓
Reflektieren
↓
Transfer
```

Definitionen sollen möglichst nicht isoliert am Anfang stehen, sondern aus nachvollziehbaren Situationen und Erfahrungen entstehen.

---

# Repository und Materialien

Die Unterrichtsmaterialien sind nach Klassenstufen und Lernbereichen organisiert. Innerhalb eines Lernbereichs können beispielsweise folgende Bestandteile vorkommen:

```text
Klasse_7/
Klasse_8/
Klasse_9/
Klasse_10/
    └── Lernbereich/
        ├── Arbeitsheft/
        ├── Lehrerband/
        ├── Loesungen/
        ├── Praesentationen/
        ├── Dateien/
        ├── Bilder/
        ├── Quellen/
        └── Lernkontrollen/

Lernplattform/
    ├── Architektur/
    ├── Fachlich/
    ├── backend/
    └── ...

Dokumentation/
Build/
```

Die konkrete Struktur einzelner Klassenstufen kann sich während der weiteren Vereinheitlichung noch verändern.

---

# Build und Ausgaben

Die Quellen werden automatisiert geprüft und in veröffentlichbare Unterrichtsmaterialien überführt. Dazu gehören unter anderem PDF-Ausgaben der verschiedenen Dokumenttypen und ein Release-Paket mit den erzeugten Materialien.

Der Build soll sicherstellen, dass beispielsweise

- benötigte Dateien vorhanden sind,
- Dokumente reproduzierbar erzeugt werden,
- Quellen und Lizenzen nachvollziehbar bleiben und
- fehlerhafte Änderungen nicht ungeprüft veröffentlicht werden.

Die erzeugten Dateien sind Ausgaben des Builds. Die fachlichen Quellen bleiben die maßgebliche Grundlage für Änderungen.

---

# Geplante Content-Architektur

Für die weitere Entwicklung sollen Unterrichtsinhalte stärker als **wiederverwendbare Themenbausteine** verstanden werden. Markdown-Dateien bilden dabei einen redaktionellen Themenpool. Manifest-Dateien sollen später festlegen, welche Bausteine für eine konkrete Kombination verwendet werden, beispielsweise:

```text
Bundesland
+ Schulart
+ Fach
+ Klassenstufe
+ Sprache
= konkreter Lernpfad / konkretes Unterrichtswerk
```

Damit kann ein gemeinsamer Inhalt sowohl in der Oberschule als auch im Gymnasium verwendet werden, während schulartspezifische Vertiefungen gezielt ergänzt werden können. Eine spätere Mehrsprachigkeit ist dabei von Anfang an vorgesehen.

Die Markdown-Quellen selbst sollen **nicht von der Lernplattform zur Laufzeit aus dem Git-Repository geladen werden**. Für die Lernplattform werden freigegebene Inhalte über einen Publishing-Prozess validiert und in die Datenbank übernommen. Repository-Pfade, interne Quellenstrukturen sowie Lösungen und interne Kontrollinformationen werden nicht an Schülerinnen und Schüler ausgeliefert.

Details zur geplanten Architektur befinden sich unter `Lernplattform/Architektur/`.

---

# Lernplattform

Die Lernplattform wird als eigenständige Webanwendung konzipiert. Der aktuelle technische Zielrahmen umfasst:

- Java mit Spring Boot im Backend
- Angular im Frontend
- PostgreSQL
- Maven
- Containerbetrieb auf Kubernetes/K3s
- Helm für das Deployment
- OAuth2/OIDC-basierte Authentifizierung
- regelmäßige Datenbank-Backups

Schulen bilden getrennte fachliche Kontexte. Lehrkräfte können mehreren Schulen zugeordnet sein; Rechte wie `SCHOOL_ADMIN` gelten ausschließlich innerhalb der jeweiligen Schule. Klassen besitzen mindestens eine zugewiesene Lehrkraft, mehrere zugewiesene Lehrkräfte sind gleichberechtigt.

Schülerkonten sind so ausgelegt, dass Klassenwechsel und perspektivisch auch Schulwechsel möglich sind, ohne persönliche Lernstände unnötig zu verlieren. Datenschutz und Datensparsamkeit sind zentrale Architekturziele.

---

# Zielgruppen

Das Projekt richtet sich insbesondere an

- Schülerinnen und Schüler,
- Informatiklehrkräfte,
- Referendarinnen und Referendare,
- Lehramtsstudierende,
- Schulen und
- OER-Projekte.

Aktuell steht die **Oberschule in Sachsen** im Mittelpunkt. Als nächster größerer Ausbau ist das **Gymnasium** vorgesehen. Die Struktur soll anschließend auch die Erweiterung auf weitere Bundesländer und Schularten ermöglichen.

---

# Verwendete Formate und Werkzeuge

Die Materialien sollen möglichst unabhängig von einzelnen proprietären Programmen bleiben. Wo sinnvoll, werden offene oder gut dokumentierte Formate eingesetzt, unter anderem:

- Markdown
- CSV
- SVG
- PNG
- PDF

Weitere technische Details befinden sich in der Projektdokumentation und den jeweiligen Build-Dateien.

---

# Mitarbeit

Beiträge sind willkommen. Besonders hilfreich sind beispielsweise:

- Fehlerkorrekturen
- fachliche und didaktische Verbesserungen
- neue Aufgaben und Beispiele
- Präsentationen
- Übersetzungen
- Verbesserungen der Barrierefreiheit
- Hinweise aus der Unterrichtspraxis
- Beiträge zur technischen Lernplattform

Vor größeren Änderungen sollten die Dokumente unter `Dokumentation/` sowie bei Arbeiten an der Lernplattform die Beschreibungen unter `Lernplattform/` berücksichtigt werden.

---

# Lizenz

Alle selbst erstellten Inhalte stehen, sofern nicht anders angegeben, unter der

**Creative Commons Namensnennung 4.0 International (CC BY 4.0)**.

Materialien Dritter unterliegen ihren jeweiligen Lizenzen. Quellen-, Urheber- und Lizenzangaben müssen bei der Verwendung externer Materialien erhalten bleiben.

---

# Projektentwicklung

Das Projekt wird schrittweise ausgebaut. Der derzeitige Schwerpunkt liegt auf

1. der fachlichen und didaktischen Qualität der Unterrichtsmaterialien,
2. einer reproduzierbaren Build- und PDF-Erzeugung,
3. der Vereinheitlichung über die Klassenstufen hinweg,
4. dem Aufbau der Lernplattform und
5. einer langfristig wiederverwendbaren Content-Struktur für unterschiedliche Schularten, Bundesländer und Sprachen.

---

# Kontakt

Fehler, Verbesserungsvorschläge und Ideen können über die GitHub-Issues eingebracht werden.

Ziel ist ein frei nutzbares, nachvollziehbar entwickeltes Informatik-Unterrichtswerk, das sich langfristig für unterschiedliche schulische Kontexte weiterentwickeln lässt.
