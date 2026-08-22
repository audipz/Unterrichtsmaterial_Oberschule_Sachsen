# Content-Publishing und Zielgruppen

## Grundentscheidung

Die Lernplattform liest Unterrichtsinhalte **nicht direkt aus dem Git-Repository** und liefert auch keine Repository-Herkunft an Browser oder API-Clients aus.

Markdown-Dateien sind ausschließlich eine Autoren- und Buildquelle. Vor der Nutzung in der Lernplattform werden sie validiert, in ein internes Content-Modell überführt und als veröffentlichte Version in PostgreSQL gespeichert.

```text
Autorenquelle / Markdown
        ↓
Build + Validierung
        ↓
Content Import / Publishing
        ↓
PostgreSQL
        ↓
Spring Boot API
        ↓
Angular
```

Die Laufzeitanwendung benötigt für die Auslieferung der Inhalte keinen Zugriff auf das Repository.

## Keine Repository-Informationen für Benutzer

Folgende Informationen sind rein intern und werden über Schüler-/Lehrer-APIs nicht ausgeliefert:

- Repository-URL,
- Branch,
- Commit-SHA,
- Dateipfad der Markdown-Quelle,
- Build-Artefaktpfade,
- interne Importprotokolle.

Für Diagnosezwecke können technische Herkunftsdaten intern gespeichert werden. Sie gehören jedoch nicht in öffentliche DTOs oder Browserantworten.

## Stabile Content-ID und Version

Jede logische Inhaltseinheit besitzt eine stabile fachliche ID, beispielsweise:

```text
k7-binaersystem-einfuehrung
```

Eine Änderung der Quelle erzeugt bei verändertem Inhalt eine neue veröffentlichte Version. Der Lernfortschritt referenziert die stabile fachliche Einheit beziehungsweise Lernperiode und wird nicht wegen redaktioneller Änderungen zurückgesetzt.

```text
content_item
  stable_key

content_release
  content_item_id
  version
  content_hash
  published_at
  status
```

## Speicherung in PostgreSQL

Für veröffentlichte Inhalte wird ein strukturiertes internes Modell gespeichert. Vorgesehen sind mindestens:

```text
content_item
content_release
content_target
content_section
content_asset
```

Der eigentliche Inhalt kann je nach Struktur als normalisierte Tabellen und/oder validiertes `jsonb` gespeichert werden. Entscheidend ist, dass die Laufzeitanwendung nicht erneut Markdown parsen muss.

## Öffentliche Inhalte und interne Lösungen strikt trennen

Lösungen, Kontrollinformationen und interne Auswertungsdaten werden **nicht** gemeinsam mit dem Schüler-Content ausgeliefert.

Empfohlen wird eine physische/logische Trennung im Datenmodell:

```text
content_release
  → sichtbarer Lerninhalt

content_solution
  → interne Lösung / Prüflogik
```

Schüler-Endpunkte lesen ausschließlich aus dem freigegebenen Lerninhalt. Ein Schüler darf auch bei direkter API-Nutzung keine Lösung oder interne Prüfinformation erhalten.

Bei automatisch prüfbaren Aufgaben sollte das Backend die Antwort serverseitig prüfen und nur das zulässige Feedback zurückgeben. Die Musterlösung selbst wird nicht an Angular übertragen.

## Cache

PostgreSQL ist die fachlich maßgebliche Laufzeitquelle. Häufig gelesene veröffentlichte Inhalte dürfen zusätzlich im Spring-Boot-Prozess gecacht werden.

Der Cache-Key enthält mindestens:

```text
contentReleaseId
```

Beim Publishing einer neuen Version wird eine neue Release-ID erzeugt. Dadurch müssen bestehende Cache-Einträge nicht mutiert werden; neue Requests verwenden die neue veröffentlichte Version.

Redis ist für die erste Version nicht erforderlich. Ein lokaler Cache pro Backend-Pod ist ausreichend, solange PostgreSQL die zentrale Quelle bleibt.

## Publishing statt Live-Synchronisation

Änderungen an Markdown-Dateien werden nicht automatisch unmittelbar sichtbar.

```text
Änderung
  ↓
Commit / PR
  ↓
Build
  ↓
Validierung
  ↓
Import als DRAFT
  ↓
Publishing
  ↓
neuer PUBLISHED Release
```

Ein fehlerhaftes Release kann zurückgezogen werden, ohne Schülerdaten zu verändern.

## Zielgruppenmodell

Inhalte werden nicht nur einer Klassenstufe zugeordnet, sondern einer fachlichen Zielgruppe.

Mindestens vorgesehen:

```text
Bundesland
Schultyp
Klassenstufe
Fach
```

Beispiele:

```text
SACHSEN + OBERSCHULE + 7 + INFORMATIK
SACHSEN + GYMNASIUM + 7 + INFORMATIK
```

Dafür wird `content_target` vorgesehen:

```text
id
content_release_id
federal_state
school_type
grade_level
subject
```

Ein Release kann mehreren Zielgruppen zugeordnet werden, falls Inhalte identisch verwendet werden können.

## Schultyp

Eine Schule besitzt einen fachlichen Schlüssel `school_type`.

Initial vorgesehen:

```text
OBERSCHULE
GYMNASIUM
```

Der Wert wird nicht als dauerhaft geschlossene Java-Enum verstanden. Weitere Schularten sollen später ergänzt werden können, ohne das Account- oder Membership-Modell umzubauen.

## Bundesland

Eine Schule besitzt ebenfalls `federal_state`.

Für den Start:

```text
SACHSEN
```

Die Architektur behandelt das Bundesland bereits als eigenes Zielgruppenmerkmal, damit später weitere Bundesländer mit abweichenden Lehrplänen aufgenommen werden können.

## Sichtbarkeit von Materialien

Der Materialkatalog eines Schülers wird serverseitig anhand der aktuellen Schule bestimmt:

```text
School
  ├── federal_state
  └── school_type
        +
Class / grade_level
        ↓
content_target
        ↓
zulässige veröffentlichte Inhalte
```

Das Frontend erhält nur die Inhalte, die für diesen Kontext freigegeben sind.

## Sicherheitsgrundsatz

Das Verbergen der Repository-Herkunft ist sinnvoll, ersetzt aber keine Zugriffskontrolle. Lösungen und interne Kontrollinformationen müssen serverseitig getrennt und autorisiert werden. Sicherheit darf nicht davon abhängen, dass ein Schüler einen Repository-Namen oder eine URL nicht kennt.
