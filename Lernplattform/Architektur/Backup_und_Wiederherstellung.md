# Backup und Wiederherstellung

## Ziel

Die Lernplattform benötigt regelmäßige, automatisierte und überprüfbare Sicherungen. Ein Backup gilt erst dann als belastbar, wenn der Wiederherstellungsweg dokumentiert und regelmäßig getestet ist.

## Zu sichernde Bereiche

Mindestens zu sichern sind:

1. PostgreSQL-Datenbank
2. Datei-/Object-Storage mit Schüleruploads und sonstigen persistenten Dateien
3. relevante Konfigurationen, soweit sie nicht vollständig aus Git und Secrets reproduzierbar sind

Nicht als Backup gelten Container-Images oder Pods, da diese aus Build und Deployment reproduzierbar sein sollen.

## Trennung vom Produktiv-Storage

Backups dürfen nicht ausschließlich auf demselben Persistent Volume wie die produktiven Daten liegen.

Geeignete Ziele sind beispielsweise:

- S3-kompatibler Object Storage,
- separates NAS,
- anderer Server,
- getrenntes Storage-System.

Ziel ist, dass ein Ausfall eines Nodes, Volumes oder Storage-Systems nicht gleichzeitig Produktivdaten und Sicherungen zerstört.

## PostgreSQL-Backup

Für die erste Version wird ein logisches PostgreSQL-Backup vorgesehen, beispielsweise auf Basis von `pg_dump`.

Ablauf:

```text
PostgreSQL
    ↓
pg_dump
    ↓
Kompression
    ↓
Verschlüsselung
    ↓
getrennter Backup-Storage
```

Der Prozess wird regelmäßig als Kubernetes `CronJob` ausgeführt.

## Standard-Zeitplan

Als Ausgangswert wird ein tägliches Backup in einem ruhigen Zeitfenster vorgesehen, beispielsweise nachts.

Helm-Beispiel:

```yaml
backup:
  enabled: true
  schedule: "0 2 * * *"
```

Der konkrete Zeitplan bleibt konfigurierbar.

## Aufbewahrung

Vorgeschlagene Standard-Retention:

```text
7 tägliche Backups
4 wöchentliche Backups
12 monatliche Backups
```

Die konkrete Aufbewahrung kann je nach Speicherplatz und organisatorischen Anforderungen angepasst werden.

## Verschlüsselung

Backups werden verschlüsselt übertragen und verschlüsselt gespeichert.

Backup-Zugangsdaten und Verschlüsselungsschlüssel liegen nicht im Repository und nicht im Backup selbst.

Die Konfiguration erfolgt über Kubernetes Secrets beziehungsweise einen geeigneten Secret-Management-Mechanismus.

## Object-/Datei-Storage

Wenn Schüler Bilder oder Dateien hochladen, reicht ein Datenbankbackup allein nicht aus.

Für den Datei-Storage wird ein eigener Sicherungsmechanismus vorgesehen. Datenbank und Datei-Backup müssen zeitlich nachvollziehbar zusammenpassen, damit Referenzen nach einem Restore nicht ins Leere zeigen.

## Helm-Konfiguration

Das Helm-Chart soll Backups konfigurierbar machen, ohne Zugangsdaten in `values.yaml` zu speichern.

Beispiel:

```yaml
backup:
  enabled: true
  schedule: "0 2 * * *"

  retention:
    daily: 7
    weekly: 4
    monthly: 12

  storage:
    type: s3
    existingSecret: lernplattform-backup
```

## Fehlerverhalten

Ein fehlgeschlagenes Backup darf nicht stillschweigend ignoriert werden.

Der CronJob muss mit einem Fehlerstatus enden. Zusätzlich soll der Betrieb erkennen können, wenn über einen definierten Zeitraum kein erfolgreiches Backup erzeugt wurde.

Eine spätere Monitoring-Integration kann darauf einen Alert auslösen.

## Restore-Prozess

Der Wiederherstellungsweg wird dokumentiert und reproduzierbar gehalten.

Grundablauf:

```text
1. geeignetes Backup auswählen
2. neue/leere PostgreSQL-Instanz bereitstellen
3. Backup entschlüsseln
4. Datenbank wiederherstellen
5. Datei-Storage wiederherstellen
6. Schema-/Flyway-Stand prüfen
7. Integritätsprüfungen ausführen
8. Anwendung zunächst isoliert starten
9. fachliche Stichprobe durchführen
10. erst danach produktiv freigeben
```

Ein Restore wird nicht direkt ungeprüft über eine laufende Produktionsdatenbank geschrieben.

## Restore-Tests

Backups werden regelmäßig testweise wiederhergestellt.

Als Ziel wird mindestens ein monatlicher Restore-Test vorgesehen. Dieser kann zunächst administrativ durchgeführt und später automatisiert werden.

Geprüft werden dabei mindestens:

- Backup ist lesbar und entschlüsselbar,
- PostgreSQL-Restore funktioniert,
- Flyway erkennt einen konsistenten Schema-Stand,
- zentrale Tabellen sind vorhanden,
- referenzierte Dateien sind verfügbar,
- Anwendung kann mit der wiederhergestellten Datenbank starten.

## Soft Delete und Backups

Die dreimonatige Soft-Delete-Frist und Backup-Aufbewahrung sind zwei unterschiedliche Mechanismen.

Ein bereits endgültig gelöschter Benutzer kann technisch noch in einem älteren Backup enthalten sein. Deshalb dürfen Backups nicht als regulärer Papierkorb oder als Benutzer-Reaktivierungsmechanismus verwendet werden.

Restore von Backups erfolgt nur für Betriebs- und Katastrophenfälle und muss die geltenden Löschregeln organisatorisch berücksichtigen.

## Recovery-Ziele

Für die erste Version werden noch keine harten SLA-Werte festgelegt. Die Architektur soll aber später zwei Werte explizit konfigurierbar beziehungsweise dokumentierbar machen:

- **RPO** – wie viel Datenverlust im schlimmsten Fall akzeptabel ist
- **RTO** – wie lange die Wiederherstellung maximal dauern soll

Bei täglichem Backup wäre ohne zusätzliche Maßnahmen zunächst ein RPO von bis zu etwa 24 Stunden möglich. Falls das später nicht ausreicht, kann die Strategie um häufigere Backups oder Point-in-Time-Recovery erweitert werden.

## Spätere Erweiterung: Point-in-Time-Recovery

Falls höhere Anforderungen entstehen, kann PostgreSQL später mit WAL-Archivierung und Point-in-Time-Recovery betrieben werden.

Das ist für Version 1 nicht zwingend erforderlich, die Architektur soll diese Erweiterung aber nicht verhindern.

## Grundsatz

> Ein Backup ist nur dann erfolgreich, wenn es außerhalb des Produktiv-Storage liegt, verschlüsselt ist, überwacht wird und nachweislich wiederhergestellt werden kann.
