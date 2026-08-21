# Fachliches Konzept

## Grundidee

Die Plattform verbindet drei Lernformen:

1. **Nachschlagewerk** – Wissen verstehen und nachschlagen
2. **Arbeitsheft** – Wissen anwenden und eigene Ergebnisse festhalten
3. **Übungen** – Inhalte selbstständig trainieren und Rückmeldungen erhalten

Ein typischer Lernweg kann so aussehen:

```text
Themenbereich
    ↓
Nachschlagewerk
    ↓
Arbeitsheft
    ↓
Üben
    ↓
Rückmeldung und gezieltes Nacharbeiten
```

Die Plattform ist eine **reine Lernplattform**. Lernkontrollen, Prüfungen, Benotung und formale Leistungsbewertung gehören nicht zum Funktionsumfang.

## Datenschutzgrundsatz für Schüler

Für Schüler sollen so wenig personenbezogene Daten wie möglich gespeichert werden.

Ein Schülerkonto benötigt innerhalb der Lernplattform nur:

- Schule,
- Benutzername,
- Passwort,
- organisatorische Zuordnungen wie Klasse oder Kurs,
- optional einen selbst gewählten Anzeigenamen.

Der Anzeigename darf ausdrücklich ein **Fantasiename beziehungsweise Pseudonym** sein.

Echte Vor- und Nachnamen sind für Schüler **keine Pflichtangaben** und sollen von der Plattform nicht verlangt werden.

Der technische Benutzername muss ebenfalls keinen Rückschluss auf den echten Namen zulassen. Er kann beispielsweise von der Schule erzeugt werden.

Beispiel:

```text
Schule:       OS-Musterstadt
Benutzername: s7a-0184
Anzeigename:  PixelFuchs
Passwort:     ************
```

Die Schule kann außerhalb der Lernplattform organisatorisch wissen, welchem Schüler ein Konto zugeordnet wurde. Diese reale Zuordnung muss nicht zusätzlich in der Lernplattform gespeichert werden.

> **Grundsatz:** Die Lernplattform muss einen Schüler zum Speichern seines Lernstands technisch wiedererkennen können, aber dafür nicht zwingend seine reale Identität kennen.

## Schulen

Eine Schule bildet die organisatorische Grenze der Plattform.

Ein `SYSTEM_ADMIN` legt eine Schule und mindestens einen ersten `SCHOOL_ADMIN` an. Danach soll die Schule ihre Benutzer weitgehend selbst verwalten können.

## Lehrer und Schul-Administratoren

Lehrer gehören einer Schule an und können Schüler fachlich betreuen.

Einzelne Lehrer können zusätzlich als `SCHOOL_ADMIN` eingetragen werden. Nur Schul-Administratoren dürfen kritische organisatorische Aktionen wie das Löschen und Reaktivieren von Konten oder Klassen durchführen.

Für Lehrkräfte können reale Namen erforderlich sein, damit Schüler und Kollegen Ansprechpartner eindeutig erkennen können. Diese Daten werden getrennt von der minimalen technischen Benutzeridentität betrachtet.

## Schüler anlegen

Schülerkonten werden innerhalb einer Schule angelegt.

Pflichtangaben sind grundsätzlich nur:

- Benutzername,
- Schule,
- Passwort beziehungsweise initiales Startpasswort.

Optional kann der Schüler später einen Fantasienamen als Anzeigenamen festlegen.

Nicht erforderlich sind insbesondere:

- echter Vorname,
- echter Nachname,
- private E-Mail-Adresse,
- private Telefonnummer,
- Wohnanschrift.

Für größere Klassen soll eine Massenanlage, beispielsweise über CSV, vorgesehen werden. Dabei reichen technische Benutzernamen und gegebenenfalls eine Klassenzuordnung aus.

Ein initial erzeugtes Passwort soll bei der ersten Anmeldung geändert werden können beziehungsweise müssen.

## Klassen

Eine Klasse ist eine organisatorische Gruppierung von Schülern, beispielsweise `7a`, `7b` oder `8a`.

Schülerkonten und Klassen werden getrennt betrachtet. Das Löschen einer Klasse löscht daher nicht automatisch die darin enthaltenen Schülerkonten.

Ein Schüler kann aus einer Klasse entfernt und einer anderen Klasse zugeordnet werden, ohne dass sein Konto oder seine Lernstände verloren gehen.

## Kurse

Ein Kurs beschreibt den konkreten Unterricht in einem Zeitraum beziehungsweise Schuljahr, beispielsweise `Informatik 7a – Schuljahr 2026/27`.

Dadurch können organisatorische Klasse und tatsächlicher Unterricht voneinander getrennt werden.

## Schüler verlässt die Schule

Diese administrative Aktion bewirkt insbesondere:

- Anmeldung sofort sperren,
- aktive organisatorische Mitgliedschaften beenden,
- Schülerkonto soft-löschen,
- dreimonatige Wiederherstellungsfrist starten.

Während der Frist kann ein `SCHOOL_ADMIN` das Konto reaktivieren. Nach Ablauf der drei Kalendermonate werden personenbezogene Daten kontrolliert endgültig bereinigt.

## Klasse löschen

Nur ein `SCHOOL_ADMIN` darf eine Klasse löschen.

Die Klasse wird zunächst soft-gelöscht und kann innerhalb von drei Monaten reaktiviert werden. Die Schülerkonten bleiben grundsätzlich bestehen.

## Papierkorb / gelöschte Elemente

Schul-Administratoren erhalten eine Ansicht für soft-gelöschte Schüler, Lehrer, Klassen und gegebenenfalls Kurse.

Angezeigt werden mindestens Objekt, Löschdatum, löschender Administrator und geplantes endgültiges Löschdatum.

## Nachschlagewerk

Das Nachschlagewerk ist der Wissensbereich der Plattform. Es enthält ausführliche Erklärungen, Beispiele, Grafiken, Nachschlagebegriffe und Querverweise. Der Inhalt ist für Schüler grundsätzlich nicht direkt veränderbar.

## Arbeitsheft

Das Arbeitsheft ist der persönliche Bearbeitungsbereich des Schülers.

Schüler sollen:

- Antworten direkt eingeben,
- automatisch speichern,
- die Bearbeitung unterbrechen,
- später am gespeicherten Stand weiterarbeiten,
- gegebenenfalls Dateien oder Bilder ergänzen können.

Die Antworten gehören zur individuellen Schülerinstanz und nicht zur Markdown-Quelldatei.

## Aufgabentypen

Geplant sind unter anderem:

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

Übungen dienen ausschließlich dem Lernen und der Selbstkontrolle.

Möglich sind beispielsweise:

- beliebig viele Versuche,
- unmittelbare Rückmeldung,
- fachliche Hinweise,
- erneutes Bearbeiten nach einem Fehler,
- Verweis auf den passenden Abschnitt des Nachschlagewerks,
- optionales Anzeigen einer Lösung nach mehreren Versuchen.

Aus Übungsdaten entstehen keine Noten und keine formalen Prüfungsleistungen.

## Lernfortschritt

Die Plattform kann Fortschritt nach fachlichen Bereichen darstellen, beispielsweise:

```text
Binärsystem
- Stellenwerte
- Binär → Dezimal
- Dezimal → Binär
- Binäraddition
```

Dabei muss zwischen **Bearbeitungsfortschritt** und fachlicher Rückmeldung unterschieden werden. Eine ausgefüllte Freitextaufgabe ist beispielsweise bearbeitet, aber nicht automatisch fachlich richtig.

## Lehreransicht

Lehrer sollen für ihre betreuten Klassen beziehungsweise Kurse beispielsweise sehen können:

- Bearbeitungsfortschritt,
- offene Bereiche,
- abgeschlossene Arbeitsheftteile,
- Übungsaktivität und erkennbare Lernschwierigkeiten,
- Antworten, soweit dies fachlich vorgesehen ist,
- eigenes Lehrerfeedback.

In Lehreransichten wird für Schüler primär Benutzername beziehungsweise gewählter Anzeigename verwendet. Die Lernplattform soll nicht unnötig reale Schülernamen speichern oder anzeigen.

Die Plattform soll nicht unnötig jede einzelne Schüleraktion überwachen, sondern lern- und unterrichtsrelevante Informationen darstellen.

## Materialversionen

Fachliche Materialien können sich weiterentwickeln. Schülerantworten dürfen dadurch nicht unkontrolliert verändert oder unbrauchbar werden.

Eine Zuweisung muss deshalb nachvollziehbar auf eine veröffentlichte Materialfassung Bezug nehmen können.

## Datenschutzprinzipien

Bereits im fachlichen Entwurf gelten:

- Datenminimierung und Pseudonymisierung für Schülerkonten,
- keine Pflicht zur Speicherung echter Schülernamen,
- keine privaten Kontaktdaten von Schülern ohne fachliche Notwendigkeit,
- Schuldaten strikt voneinander trennen,
- Rechte serverseitig prüfen,
- Passwörter nur als geeignete Passwort-Hashes speichern,
- kritische Aktionen auf berechtigte Administratoren begrenzen,
- gelöschte Konten sofort sperren,
- Wiederherstellung während der Soft-Delete-Frist ermöglichen,
- personenbezogene Daten nach Ablauf der vorgesehenen Frist kontrolliert löschen.
