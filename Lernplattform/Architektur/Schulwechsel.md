# Schulwechsel

## Ziel

Ein Schüler kann die Schule wechseln, ohne seine persönlichen Lernstände vollständig zu verlieren.

Ein Schulwechsel ist **kein normaler Klassenwechsel** und darf nicht durch simples Ändern von `school_id` erfolgen. Schulen sind getrennte Mandanten. Der Transfer muss deshalb kontrolliert und nachvollziehbar über die Mandantengrenze stattfinden.

## Grundprinzip

```text
Quellschule
   │
   │ SCHOOL_ADMIN gibt Schüler für Transfer frei
   ▼
Transferanforderung mit einmaligem Transfercode
   │
   │ Zielschule nimmt Transfer an
   ▼
Zielschule
```

Der Vorgang benötigt keine Klarnamen. Die Zuordnung kann über einen einmaligen, zufälligen Transfercode erfolgen.

## Berechtigungen

- Nur ein `SCHOOL_ADMIN` der Quellschule darf einen Schulwechsel initiieren.
- Nur ein `SCHOOL_ADMIN` der Zielschule darf den Transfer annehmen.
- Ein Transfercode ist einmalig, zeitlich begrenzt und nicht erratbar.
- Der Schüler selbst kann keinen Mandantenwechsel erzwingen.
- Ein `SYSTEM_ADMIN` kann bei technischen Ausnahmefällen unterstützen, soll aber nicht zum normalen Schulwechselprozess gehören.

## Warum nicht einfach `school_id` ändern?

Ein bestehendes Konto besitzt Beziehungen zu:

- alter Schule,
- alten Klassen,
- Kursen,
- Lehrerfeedback,
- Uploads,
- Arbeitsständen,
- Auditdaten.

Ein direktes Umschreiben der Schule würde diese historischen Beziehungen über zwei Mandanten hinweg vermischen und könnte zu unzulässiger Sichtbarkeit führen.

## Transfermodell

Für den Schulwechsel wird in der Zielschule eine neue Schulidentität erzeugt. Die technische globale Transferbeziehung bleibt intern nachvollziehbar, wird aber normalen Lehrern nicht angezeigt.

```text
Quellkonto
  user_id = A
  school_id = Schule A
       │
       │ Transfer
       ▼
Zielkonto
  user_id = B
  school_id = Schule B
```

Das Quellkonto wird nach erfolgreicher Übernahme deaktiviert beziehungsweise soft-gelöscht. Damit gelten weiterhin die normalen Lösch- und Purge-Regeln.

## Was wird übertragen?

Übertragen werden nur Daten, die dem Schüler selbst gehören und für das weitere Lernen sinnvoll sind.

Geeignet sind insbesondere:

- persönlicher Bearbeitungsfortschritt,
- Antworten in selbst bearbeiteten Arbeitsblättern,
- persönliche Übungsstände,
- selbst hochgeladene Dateien, soweit fachlich erforderlich,
- Materialversionen beziehungsweise Referenzen auf globale Lernmaterialien.

Nicht automatisch übertragen werden:

- Mitgliedschaft in alten Klassen,
- alte Kurszuordnungen,
- Rollen der alten Schule,
- schulinterne Verwaltungsdaten,
- Lehrerrechte,
- interne Auditinformationen der Quellschule.

Lehrerfeedback muss gesondert betrachtet werden. Standardmäßig sollte es bei der Quellschule verbleiben und nicht automatisch schulübergreifend übertragen werden, weil es schulbezogene personenbezogene Kommunikation enthalten kann.

## Benutzername und Fantasiename

Der technische Benutzername ist nur innerhalb einer Schule eindeutig. Beim Schulwechsel kann er daher grundsätzlich übernommen werden, sofern er in der Zielschule noch frei ist.

Bei Konflikt wird ein neuer technischer Benutzername vergeben.

Der Fantasiename kann ebenfalls übernommen werden, muss aber vor Aufnahme in die Zielklasse gegen die dortigen Eindeutigkeitsregeln geprüft werden. Bei Konflikt muss ein anderer Fantasiename gewählt beziehungsweise automatisch vorgeschlagen werden.

## Passwort

Passwort-Hashes werden nicht zwischen Mandanten übertragen.

Die Zielschule erzeugt ein neues Startpasswort. Beim ersten Login muss der Schüler dieses ändern.

Dadurch erhält die Zielschule keine Authentifizierungsdaten der Quellschule.

## Transferstatus

Vorgesehene Zustände:

```text
REQUESTED
ACCEPTED
COMPLETED
EXPIRED
CANCELLED
```

Ein Transfer darf erst als `COMPLETED` gelten, wenn Zielkonto und gewünschte Lernstände vollständig angelegt wurden.

## Transfercode

Der Transfercode wird nur gehasht gespeichert.

```text
school_transfer
--------------------------------
id
source_school_id
source_user_id
target_school_id
code_hash
status
expires_at
created_at
created_by
accepted_at
accepted_by
completed_at
```

Der Klartextcode wird nur einmal bei der Erstellung angezeigt beziehungsweise ausgegeben.

## Ablauf

### 1. Transfer vorbereiten

Der Schul-Admin der Quellschule wählt:

```text
Schüler → Schulwechsel vorbereiten
```

Das System erzeugt einen zeitlich begrenzten Transfercode.

Das Quellkonto bleibt bis zur erfolgreichen Übernahme aktiv, sofern der Admin es nicht zusätzlich sperrt.

### 2. Transfer annehmen

Der Schul-Admin der Zielschule gibt den Transfercode ein.

Das System zeigt ausschließlich minimale Informationen an, zum Beispiel:

```text
Fantasiename: PixelFuchs42
übertragbare Lernstände: 18
```

Keine Klarnamen sind erforderlich.

### 3. Zielkonto konfigurieren

Die Zielschule legt fest:

- technischen Benutzernamen,
- Zielklasse,
- neues Startpasswort.

Der Fantasiename wird gegen die Zielklasse geprüft.

### 4. Lernstände übertragen

Der Transferdienst kopiert beziehungsweise migriert die zulässigen persönlichen Lernstände auf das neue Zielkonto.

### 5. Quellkonto schließen

Nach erfolgreichem Abschluss wird das Quellkonto soft-gelöscht beziehungsweise als `TRANSFERRED` markiert und der Login gesperrt.

Die normalen Aufbewahrungs- und Purge-Regeln der Quellschule bleiben bestehen.

## Fehlerfall

Der Transfer wird transaktional beziehungsweise mit einem wiederaufnehmbaren Prozess durchgeführt.

Wenn die Zielanlage fehlschlägt:

- bleibt das Quellkonto unverändert,
- wird kein unvollständiges Zielkonto als aktiv freigegeben,
- kann der Transfer erneut versucht oder storniert werden.

## Klassenwechsel innerhalb derselben Schule

Ein Klassenwechsel ist davon getrennt und wesentlich einfacher:

```text
alte Klassenmitgliedschaft → ENDED
neue Klassenmitgliedschaft → ACTIVE
```

Dabei bleibt dasselbe Benutzerkonto erhalten. Lernstände bleiben unverändert erhalten.

Das unterstützt insbesondere:

- reguläre Versetzung,
- Wiederholen einer Klassenstufe,
- Wechsel in eine Parallelklasse,
- organisatorische Umgruppierung.
