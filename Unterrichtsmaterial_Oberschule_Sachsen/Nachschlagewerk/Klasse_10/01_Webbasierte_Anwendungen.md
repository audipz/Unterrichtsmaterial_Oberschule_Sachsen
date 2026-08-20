# 1 Webbasierte Anwendungen

## Vom lokalen Programm zur Webanwendung

Eine **lokale Anwendung** wird auf einem Gerät installiert und verarbeitet Daten hauptsächlich dort. Eine **webbasierte Anwendung** wird über einen Browser oder eine App genutzt und arbeitet meist mit Diensten im Internet zusammen. Beispiele sind Lernplattformen, Online-Office, Karten- und Nachrichtendienste, Mediatheken oder Cloud-Speicher.

Der wichtigste Unterschied liegt nicht im Aussehen, sondern in der Verteilung der Aufgaben: Ein Teil läuft auf dem eigenen Gerät, ein anderer Teil auf einem Server. Dadurch können Daten von verschiedenen Orten aus genutzt, gemeinsam bearbeitet und ständig aktualisiert werden.

| Aspekt | Lokale Anwendung | Webbasierte Anwendung |
|---|---|---|
| Start | installiertes Programm | Browser oder App |
| Daten | oft lokal gespeichert | häufig auf Servern |
| Aktualisierung | Installation/Update nötig | oft zentral aktualisiert |
| Zusammenarbeit | eher zusätzlich eingerichtet | häufig eingebaut |
| Abhängigkeit | funktioniert oft offline | meist Netzwerk nötig |

> **Merke:** Eine Webanwendung ist nicht einfach „eine Internetseite“. Sie verarbeitet Eingaben, speichert Daten und stellt Funktionen bereit, die über ein Netzwerk erreichbar sind.

## Client, Server und Protokoll

Bei webbasierten Anwendungen arbeiten **Client** und **Server** zusammen. Der Client ist das Programm auf dem eigenen Gerät, zum Beispiel ein Browser. Der Server stellt Daten, Webseiten oder Dienste bereit.

```text
Client  ── Anfrage ──>  Server
Client  <─ Antwort ───  Server
```

Damit beide Seiten sich verstehen, verwenden sie **Protokolle**. Ein Protokoll legt Regeln für die Kommunikation fest. Für Webseiten sind besonders HTTP und HTTPS wichtig. HTTPS verschlüsselt die Verbindung und hilft, die Echtheit der Gegenstelle zu prüfen.

Ein typischer Ablauf:

1. Der Browser fordert eine Adresse an.
2. Der Server sendet HTML, CSS, Skripte, Bilder oder Daten zurück.
3. Der Browser stellt die Oberfläche dar.
4. Weitere Aktionen lösen neue Anfragen aus, zum Beispiel Speichern, Suchen oder Laden.

## Bausteine einer Webseite

Webseiten bestehen aus mehreren Arten von Dateien und Daten. Sie erfüllen unterschiedliche Aufgaben.

| Baustein | Aufgabe | Beispiel |
|---|---|---|
| HTML | Struktur und Bedeutung | Überschrift, Absatz, Liste |
| CSS | Gestaltung | Farben, Abstände, Layout |
| JavaScript | Verhalten | Menü öffnen, Eingaben prüfen |
| Medien | Inhalte | Bild, Ton, Video |
| Daten | veränderliche Inhalte | Profilname, Nachrichten, Suchergebnisse |

HTML beschreibt, was etwas ist. CSS beschreibt, wie es aussieht. JavaScript beschreibt, wie sich etwas verhält. Moderne Webanwendungen laden Daten oft nach, ohne die ganze Seite neu aufzubauen.

## Öffentlicher und privater Bereich

Viele Webanwendungen unterscheiden zwischen einem **öffentlichen Bereich** und einem **privaten Bereich**.

Der öffentliche Bereich ist ohne Anmeldung sichtbar. Dazu gehören zum Beispiel Startseiten, Hilfetexte oder frei zugängliche Informationen. Der private Bereich ist an ein Benutzerkonto gebunden. Dort liegen persönliche Einstellungen, Nachrichten, Dokumente oder Zahlungsdaten.

Diese Trennung ist wichtig, weil nicht alle Daten für alle Personen sichtbar sein dürfen. Eine Webanwendung muss deshalb prüfen:

- Wer greift zu?
- Ist die Person angemeldet?
- Welche Rechte hat sie?
- Welche Daten dürfen angezeigt oder verändert werden?

> **Merke:** Anmeldung allein reicht nicht. Eine Anwendung muss zusätzlich prüfen, welche Berechtigungen eine angemeldete Person besitzt.

## Cookies, Sitzungen und Standortdaten

Ein **Cookie** ist ein kleiner Datensatz, den eine Webseite im Browser speichern kann. Cookies können nützlich sein, zum Beispiel um eine Sitzung wiederzuerkennen oder Einstellungen zu speichern. Sie können aber auch zum Wiedererkennen über mehrere Seiten hinweg genutzt werden.

Eine **Sitzung** beschreibt den Zeitraum, in dem eine angemeldete Person mit einer Webanwendung arbeitet. Häufig merkt sich der Server über eine Sitzungskennung, welche Anfragen zu derselben angemeldeten Person gehören.

**Standortdaten** können sehr nützlich sein, etwa für Navigation oder lokale Suchergebnisse. Gleichzeitig sind sie besonders sensibel, weil sie Rückschlüsse auf Aufenthaltsorte, Gewohnheiten und Bewegungsmuster ermöglichen.

| Datenart | Nutzen | Risiko |
|---|---|---|
| Cookie | Anmeldung, Sprache, Warenkorb | Wiedererkennung und Tracking |
| Standort | Karten, lokale Dienste | Bewegungsprofil |
| Profilangabe | Personalisierung | zu viel öffentliche Information |
| Nutzungsdaten | Verbesserung des Dienstes | Auswertung von Verhalten |

## Klassen, Objekte, Attribute und Methoden in Webanwendungen

Auch Webanwendungen lassen sich mit Begriffen der objektorientierten Modellierung beschreiben. Ein Benutzerkonto kann als Objekt betrachtet werden. Es besitzt Attribute und Methoden.

| Objekt | Attribute | Methoden |
|---|---|---|
| Benutzerkonto | Benutzername, Rolle, E-Mail | anmelden(), abmelden(), passwortAendern() |
| Nachricht | Absender, Empfänger, Text, Zeitpunkt | senden(), löschen(), markieren() |
| Datei | Name, Größe, Besitzer, Freigabe | öffnen(), teilen(), herunterladen() |

Diese Sicht hilft, komplexe Anwendungen zu ordnen. Sie zeigt, welche Daten zu einem Objekt gehören und welche Handlungen mit ihm möglich sind.

## Sicherheit bei webbasierten Anwendungen

Webanwendungen sind erreichbar und deshalb besonders schutzbedürftig. Wichtige Schutzideen sind:

- starke Passwörter und möglichst Mehr-Faktor-Anmeldung,
- verschlüsselte Verbindungen,
- regelmäßige Sicherung wichtiger Daten,
- sparsame Freigabe persönlicher Informationen,
- vorsichtiger Umgang mit Links und Dateianhängen,
- aktuelle Geräte und Programme.

Ein Backup schützt nicht vor jedem Problem, aber es hilft bei Geräteverlust, versehentlichem Löschen, Schadsoftware oder beschädigten Dateien.

## Begriffe zum Nachschlagen

**Client:** Programm oder Gerät, das eine Anfrage an einen Server stellt.

**Server:** Rechner oder Dienst, der Daten und Funktionen bereitstellt.

**Webanwendung:** Anwendung, die über Browser oder App genutzt wird und meist mit Servern zusammenarbeitet.

**Protokoll:** Regelwerk für die Datenübertragung.

**HTTP/HTTPS:** Protokolle zum Übertragen von Webinhalten; HTTPS verwendet Verschlüsselung.

**Cookie:** kleiner Datensatz im Browser, zum Beispiel für Sitzungen oder Einstellungen.

**Sitzung:** zusammenhängende Nutzung einer Anwendung, häufig mit Anmeldung.

→ Vorwissen: Klasse 8, **Netzwerke**; Klasse 7, **Dateien, Ordner und Pfade**.
