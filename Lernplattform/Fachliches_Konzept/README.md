# Fachliches Konzept

## Grundidee

Die Plattform verbindet drei bisher beziehungsweise künftig vorhandene Lernformen:

1. **Nachschlagewerk** – Wissen verstehen und nachschlagen
2. **Arbeitsheft** – Wissen anwenden und eigene Ergebnisse festhalten
3. **Lernkontrolle** – Lernstand nach einem Themenbereich überprüfen

Ergänzend können interaktive **Übungen** angeboten werden.

Ein typischer Lernweg kann damit so aussehen:

```text
Themenbereich
    ↓
Nachschlagewerk
    ↓
Arbeitsheft
    ↓
Üben
    ↓
Lernkontrolle
    ↓
Feedback / weiterer Lernbedarf
```

Die Reihenfolge kann durch die Lehrkraft gesteuert werden. Eine Lernkontrolle muss nicht zwingend automatisch gesperrt bleiben, bis alle vorherigen Schritte abgeschlossen sind.

## Schulen

Eine Schule bildet die organisatorische Grenze der Plattform.

Ein `SYSTEM_ADMIN` legt eine Schule und mindestens einen ersten `SCHOOL_ADMIN` an. Danach soll die Schule ihre Benutzer weitgehend selbst verwalten können.

## Lehrer und Schul-Administratoren

Lehrer gehören einer Schule an und können Schüler fachlich betreuen.

Einzelne Lehrer können zusätzlich als `SCHOOL_ADMIN` eingetragen werden. Nur Schul-Administratoren dürfen kritische organisatorische Aktionen wie das Löschen und Reaktivieren von Konten oder Klassen durchführen.

Ein normaler Lehrer soll keine Schülerkonten endgültig aus der Schulorganisation entfernen können.

## Schüler anlegen

Schülerkonten werden innerhalb einer Schule angelegt. Vorgesehen sind mindestens:

- Benutzername,
- Schule,
- Passwort beziehungsweise initiales Startpasswort.

Zusätzliche schulorganisatorische Angaben können ergänzt werden, soweit sie für den Betrieb tatsächlich benötigt werden.

Für größere Klassen soll eine Massenanlage, beispielsweise über CSV, vorgesehen werden.

Ein initial erzeugtes Passwort soll bei der ersten Anmeldung geändert werden können beziehungsweise müssen.

## Klassen

Eine Klasse ist eine organisatorische Gruppierung von Schülern, beispielsweise:

```text
7a
7b
8a
```

Schülerkonten und Klassen werden getrennt betrachtet.

Das Löschen einer Klasse löscht daher **nicht** automatisch die darin enthaltenen Schülerkonten.

Ein Schüler kann aus einer Klasse entfernt und einer anderen Klasse zugeordnet werden, ohne dass sein Konto oder seine Lernstände verloren gehen.

## Kurse

Ein Kurs beschreibt den konkreten Unterricht in einem Zeitraum beziehungsweise Schuljahr, beispielsweise:

```text
Informatik 7a – Schuljahr 2026/27
```

Dadurch können organisatorische Klasse und tatsächlicher Unterricht voneinander getrennt werden. Das ermöglicht später auch klassenübergreifende Kurse oder mehrere unterrichtende Lehrer.

## Schüler verlässt die Schule

Für diesen Fall gibt es eine eigene administrative Aktion.

Sie bewirkt insbesondere:

- Anmeldung sofort sperren,
- aktive organisatorische Mitgliedschaften beenden,
- Schülerkonto soft-löschen,
- dreimonatige Wiederherstellungsfrist starten.

Während der Frist kann ein `SCHOOL_ADMIN` das Konto reaktivieren.

Nach Ablauf der drei Monate werden die personenbezogenen Daten kontrolliert endgültig bereinigt.

## Klasse löschen

Nur ein `SCHOOL_ADMIN` darf eine Klasse löschen.

Die Klasse wird zunächst soft-gelöscht und kann innerhalb von drei Monaten reaktiviert werden.

Die Schülerkonten bleiben grundsätzlich bestehen.

Vor einer Löschung muss die Oberfläche die Auswirkungen verständlich anzeigen.

## Papierkorb / gelöschte Elemente

Schul-Administratoren erhalten eine Ansicht für soft-gelöschte Elemente, beispielsweise:

- Schüler,
- Lehrer,
- Klassen,
- gegebenenfalls Kurse.

Angezeigt werden mindestens:

- Objekt,
- Löschdatum,
- löschender Administrator,
- geplantes endgültiges Löschdatum.

Innerhalb der Frist kann ein berechtigtes Objekt reaktiviert werden.

Bei Konflikten, beispielsweise einem inzwischen erneut vergebenen Benutzernamen, darf eine Reaktivierung nicht stillschweigend fehlschlagen oder Daten überschreiben.

## Nachschlagewerk

Das Nachschlagewerk ist der Wissensbereich der Plattform.

Es soll:

- ausführliche Erklärungen enthalten,
- Beispiele und Grafiken darstellen,
- Begriffe nachschlagbar machen,
- Querverweise zwischen Themen ermöglichen.

Der Inhalt ist für Schüler grundsätzlich nicht direkt veränderbar.

## Arbeitsheft

Das Arbeitsheft wird interaktiv bearbeitet.

Schüler sollen:

- Antworten direkt eingeben,
- automatisch speichern,
- die Bearbeitung unterbrechen,
- später am gespeicherten Stand weiterarbeiten,
- gegebenenfalls Dateien oder Bilder ergänzen können.

Die Antworten gehören zum Schüler beziehungsweise zu seiner Arbeitsheftinstanz und nicht zur Markdown-Quelldatei.

## Aufgabentypen

Die Plattform soll unterschiedliche Aufgabentypen unterstützen. Geplant sind unter anderem:

- Kurzantwort,
- mehrzeiliger Freitext,
- Zahlenantwort,
- Single Choice,
- Multiple Choice,
- Lückentext,
- Zuordnung,
- Reihenfolge,
- Tabelle,
- Code,
- Datei-/Bildupload,
- gegebenenfalls einfache grafische Aufgaben.

Nicht jeder Aufgabentyp muss in der ersten Version umgesetzt werden.

## Übungen

Übungen dienen dem Lernen und dürfen sich von Lernkontrollen unterscheiden.

Bei Übungen können beispielsweise möglich sein:

- mehrere Versuche,
- Hinweise,
- unmittelbare Rückmeldung,
- erneutes Bearbeiten nach einem Fehler,
- Verweis auf den passenden Abschnitt des Nachschlagewerks.

## Lernkontrollen

Nach einem fachlichen Bereich kann eine Lernkontrolle angeboten werden.

Beispiel:

```text
Binärsystem
├── Nachschlagewerk
├── Arbeitsheft
├── Üben
└── Lernkontrolle
```

Eine Lernkontrolle kann durch die Lehrkraft konfiguriert werden, beispielsweise mit:

- Freigabezeitpunkt,
- Endzeitpunkt,
- erlaubter Anzahl von Versuchen,
- optionalem Zeitlimit,
- Entscheidung, wann Ergebnisse sichtbar werden,
- Entscheidung, wann Lösungen sichtbar werden.

## Automatische und manuelle Auswertung

Geeignete strukturierte Aufgaben können automatisch ausgewertet werden, beispielsweise:

- Auswahlfragen,
- Zahlenwerte,
- eindeutige Kurzantworten,
- Zuordnungen.

Freitext, komplexe Erklärungen, Zeichnungen oder größere Programmierlösungen können eine Bewertung beziehungsweise Rückmeldung durch die Lehrkraft erfordern.

Automatische Bewertung und fachliches Lehrerfeedback sind daher getrennte Konzepte.

## Aufgabenpool

Langfristig soll eine Lernkontrolle Aufgaben aus einem Aufgabenpool beziehen können.

Aufgaben können Metadaten besitzen, beispielsweise:

- Themenbereich,
- Kompetenz/Lernziel,
- Klassenstufe,
- Schwierigkeit,
- Aufgabentyp,
- erreichbare Punkte.

Damit können später unterschiedliche, aber fachlich vergleichbare Varianten einer Lernkontrolle erzeugt werden.

Für die erste Version ist dafür kein RDF/SHACL-System erforderlich. Die Informationen können relational beziehungsweise in strukturierten Materialmetadaten modelliert werden.

## Lernfortschritt

Die Plattform soll nicht nur eine Gesamtpunktzahl anzeigen.

Sinnvoll sind fachliche Bereiche, beispielsweise:

```text
Binärsystem
- Stellenwerte
- Binär → Dezimal
- Dezimal → Binär
- Binäraddition
```

Dadurch kann später sichtbar werden, in welchen Teilbereichen ein Schüler sicher ist und wo noch Lernbedarf besteht.

## Lehreransicht

Lehrer sollen für ihre betreuten Klassen beziehungsweise Kurse beispielsweise sehen können:

- Bearbeitungsfortschritt,
- offene Bereiche,
- abgeschlossene Arbeitsheftteile,
- Lernkontrollstatus,
- Ergebnisse,
- Antworten, soweit dies für die Aufgabe vorgesehen ist,
- eigenes Lehrerfeedback.

Die Plattform soll dabei nicht unnötig jede einzelne Schüleraktion überwachen, sondern lern- und unterrichtsrelevante Informationen darstellen.

## Materialversionen

Fachliche Materialien können sich weiterentwickeln. Schülerantworten dürfen dadurch nicht unkontrolliert verändert oder unbrauchbar werden.

Eine Zuweisung muss deshalb nachvollziehbar auf eine Materialversion beziehungsweise veröffentlichte Fassung Bezug nehmen können.

## Datenschutzprinzipien

Bereits im fachlichen Entwurf gelten:

- nur notwendige personenbezogene Daten speichern,
- Schuldaten strikt voneinander trennen,
- Rechte serverseitig prüfen,
- kritische Aktionen auf berechtigte Administratoren begrenzen,
- gelöschte Konten sofort sperren,
- Wiederherstellung während der Soft-Delete-Frist ermöglichen,
- personenbezogene Daten nach Ablauf der vorgesehenen Frist kontrolliert löschen.
