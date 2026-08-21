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

Das **Client-Server-Prinzip** ist nicht auf Webseiten beschränkt. Es beschreibt allgemein eine Rollenverteilung in einem Netzwerk:

- Ein **Client** fordert einen Dienst oder Daten an.
- Ein **Server** stellt einen Dienst oder Daten bereit.
- Ein **Protokoll** legt fest, nach welchen Regeln beide Seiten miteinander kommunizieren.

Welches Protokoll verwendet wird, hängt deshalb von der **Anwendung beziehungsweise dem Dienst** ab. Ein Browser kommuniziert anders mit einem Webserver als ein Mailprogramm mit einem Mailserver.

```text
Client  ── Anfrage / Nachricht ──>  Server
Client  <── Antwort / Daten ──────  Server
```

> **Merke:** **Client und Server beschreiben Rollen. Das Anwendungsprotokoll bestimmt, wie diese Rollen für einen bestimmten Dienst miteinander sprechen.** HTTP und HTTPS sind nur zwei Beispiele.

### Beispiele für Clients, Server und Protokolle

| Anwendung/Dienst | typischer Client | Server/Dienst | wichtige Protokolle |
|---|---|---|---|
| Webseite/Webanwendung | Browser | Webserver | HTTP, HTTPS |
| E-Mail versenden | Mailclient oder Mailserver | Mailserver | SMTP |
| E-Mail abrufen/synchronisieren | Mailclient | Mailserver | IMAP, POP3 |
| Namensauflösung | Betriebssystem/Resolver | DNS-Server | DNS |
| Dateiübertragung | FTP-/SFTP-Client | Datei-/SSH-Server | FTP, FTPS, SFTP |
| Fernzugriff auf Kommandozeile | SSH-Client | SSH-Server | SSH |
| automatische Netzwerkkonfiguration | Netzwerkgerät | DHCP-Server | DHCP |
| Zeitsynchronisation | Betriebssystem/Gerät | Zeitserver | NTP |

Die Tabelle zeigt auch, dass eine Anwendung **mehrere Protokolle** verwenden kann. Ein Mailclient verwendet beispielsweise SMTP zum Senden und IMAP oder POP3 zum Abrufen von Nachrichten.

## E-Mail als Client-Server-Beispiel

Beim E-Mail-Verkehr ist besonders gut zu erkennen, dass „ein Dienst“ aus mehreren Kommunikationsschritten bestehen kann.

Vereinfacht:

```text
Mailclient des Absenders
        │
        │ SMTP
        ▼
Mailserver des Absenders
        │
        │ SMTP
        ▼
Mailserver des Empfängers
        │
        │ IMAP oder POP3
        ▼
Mailclient des Empfängers
```

### SMTP

**SMTP (Simple Mail Transfer Protocol)** dient vor allem zum **Versenden und Weiterleiten von E-Mails**. Ein Mailprogramm übergibt eine Nachricht per SMTP an einen Mailserver; Mailserver können SMTP ebenfalls zur Weitergabe von Nachrichten verwenden.

### IMAP

**IMAP (Internet Message Access Protocol)** dient dazu, Nachrichten auf einem Mailserver zu verwalten und mit einem Client zu synchronisieren. Nachrichten und Ordner bleiben typischerweise auf dem Server, sodass beispielsweise Smartphone und Notebook denselben Postfachstand verwenden können.

### POP3

**POP3 (Post Office Protocol Version 3)** dient ebenfalls zum Abrufen von E-Mails. Das klassische Modell ist stärker auf das Herunterladen von Nachrichten auf einen Client ausgerichtet. Moderne Nutzungsszenarien mit mehreren Geräten verwenden deshalb häufig IMAP.

> **Merke:** SMTP ist vor allem für den **Transport/Versand**, IMAP und POP3 für den **Zugriff auf ein Postfach** zuständig.

## Protokolle arbeiten auf verschiedenen Ebenen zusammen

Wenn ein Browser eine Webseite per HTTPS lädt, reicht HTTPS allein nicht aus. Mehrere Protokolle und Dienste greifen ineinander.

Vereinfacht kann der Ablauf so aussehen:

```text
Domainname
   ↓
DNS: Welche IP-Adresse gehört zum Namen?
   ↓
IP: Zu welchem Rechner müssen Pakete gelangen?
   ↓
TCP: zuverlässige Verbindung zwischen Programmen
   ↓
TLS: verschlüsselte und authentisierte Verbindung
   ↓
HTTP: Welche Webressource wird angefordert?
```

Bei modernen Webverbindungen können technische Details anders aussehen, beispielsweise HTTP/3 über QUIC. Entscheidend ist hier die Grundidee: **Netzwerkkommunikation besteht aus mehreren zusammenwirkenden Protokollen, die unterschiedliche Aufgaben übernehmen.**

### Anwendung, Transport und Netzwerk

Für eine erste Einordnung kann man Protokolle nach ihrer Aufgabe gruppieren:

| Bereich | Aufgabe | Beispiele |
|---|---|---|
| Anwendung | Regeln eines konkreten Dienstes | HTTP, SMTP, IMAP, DNS |
| Transport | Kommunikation zwischen Anwendungen/Ports | TCP, UDP |
| Netzwerk | Weiterleitung zwischen Rechnern/Netzen | IP |

Das ist eine vereinfachte Sicht auf einen **Protokollstapel**. Ein Protokoll einer höheren Ebene nutzt Dienste darunterliegender Ebenen.

## Ports: Welcher Dienst ist gemeint?

Eine IP-Adresse identifiziert einen Netzwerkanschluss beziehungsweise ein Gerät im Netz. Auf einem Server können aber gleichzeitig viele Dienste laufen. **Portnummern** helfen dabei, Netzwerkverkehr dem richtigen Dienst beziehungsweise Programm zuzuordnen.

Beispiele bekannter Standardports sind:

| Dienst | typischer Port |
|---|---:|
| HTTP | 80 |
| HTTPS | 443 |
| SMTP | 25 |
| DNS | 53 |
| SSH | 22 |

In der Praxis können Dienste auch andere Ports verwenden. Außerdem existieren bei einigen Protokollen unterschiedliche Ports für verschiedene Betriebs- und Verschlüsselungsvarianten.

> **Merke:** **IP-Adresse → welcher Netzwerkendpunkt? Port → welcher Dienst beziehungsweise welche Anwendung dort?**

## HTTP und HTTPS im Web

Für Webseiten und viele Web-APIs ist **HTTP (Hypertext Transfer Protocol)** das zentrale Anwendungsprotokoll. Ein Client sendet eine HTTP-Anfrage und ein Server antwortet mit einer HTTP-Antwort.

Ein typischer Ablauf:

1. Der Browser erhält eine URL.
2. Falls nötig wird über DNS die passende IP-Adresse ermittelt.
3. Eine Netzwerkverbindung zum Server wird aufgebaut.
4. Bei HTTPS wird die Kommunikation kryptografisch abgesichert.
5. Der Browser sendet eine HTTP-Anfrage.
6. Der Server antwortet beispielsweise mit HTML, CSS, JavaScript, Bildern oder JSON-Daten.
7. Weitere Aktionen können zusätzliche Anfragen auslösen.

### HTTPS ist HTTP mit Schutz der Übertragung

**HTTPS** bedeutet vereinfacht HTTP über eine kryptografisch geschützte Verbindung mit **TLS**. Dadurch werden insbesondere Vertraulichkeit und Integrität der übertragenen Daten geschützt und die Identität der Gegenstelle kann anhand von Zertifikaten geprüft werden.

HTTPS bedeutet jedoch nicht automatisch, dass eine Webseite vertrauenswürdig ist. Auch eine betrügerische Webseite kann ein gültiges Zertifikat und damit eine verschlüsselte HTTPS-Verbindung besitzen.

## TCP und der Verbindungsaufbau

Viele klassische Anwendungsprotokolle verwenden **TCP (Transmission Control Protocol)**. TCP stellt eine zuverlässige, geordnete Übertragung zwischen zwei Anwendungen bereit.

Vor der eigentlichen Datenübertragung wird eine TCP-Verbindung typischerweise mit dem **Three-Way Handshake** aufgebaut:

```text
Client                         Server
  │──── SYN ────────────────────>│
  │<─── SYN + ACK ───────────────│
  │──── ACK ────────────────────>│
  │                              │
  │       Verbindung steht       │
```

- **SYN** signalisiert den Wunsch, eine Verbindung aufzubauen und synchronisiert Verbindungsinformationen.
- **ACK** bestätigt empfangene Informationen.
- Der Server antwortet auf ein SYN normalerweise mit `SYN + ACK`; der Client bestätigt wiederum mit `ACK`.

Ein einzelnes SYN ist daher **kein Trick, um ohne Bestätigung „in ein Netz zu gelangen“**. Allerdings kann der Verbindungsaufbau missbraucht werden: Bei einem **SYN-Flood-Angriff** werden sehr viele Verbindungsanforderungen begonnen und absichtlich nicht vollständig abgeschlossen. Dadurch können Ressourcen eines Servers für halboffene Verbindungen belegt werden. Schutzmechanismen begrenzen diesen Effekt.

## UDP

Nicht jede Anwendung benötigt TCP. **UDP (User Datagram Protocol)** arbeitet verbindungslos und besitzt weniger Mechanismen für zuverlässige, geordnete Übertragung. Dadurch ist es für bestimmte Anwendungen besonders geeignet.

UDP wird beispielsweise bei DNS-Anfragen häufig eingesetzt. Auch Echtzeitanwendungen können UDP oder darauf aufbauende moderne Protokolle verwenden, wenn geringe Verzögerung wichtiger ist als die klassische TCP-Arbeitsweise.

> **Merke:** TCP und UDP sind keine „Webprotokolle“. Sie sind Transportprotokolle, auf denen unterschiedliche Anwendungsprotokolle aufbauen können.

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

**Client:** Programm oder Gerät, das einen Dienst eines Servers nutzt beziehungsweise Anfragen an ihn sendet.

**Server:** Programm, Dienst oder Rechner, der anderen Systemen Funktionen oder Daten bereitstellt.

**Protokoll:** Regelwerk, das festlegt, wie Kommunikationspartner Daten austauschen.

**Anwendungsprotokoll:** Protokoll für einen bestimmten Netzwerkdienst, beispielsweise HTTP, SMTP oder IMAP.

**HTTP/HTTPS:** Protokolle zum Übertragen von Webinhalten; HTTPS schützt die HTTP-Kommunikation mit TLS.

**SMTP:** Anwendungsprotokoll zum Versenden und Weiterleiten von E-Mails.

**IMAP:** Anwendungsprotokoll zum Zugriff auf und zur Synchronisation von E-Mail-Postfächern auf einem Server.

**POP3:** Anwendungsprotokoll zum Abrufen von E-Mails, traditionell stärker auf das Herunterladen zum Client ausgerichtet.

**DNS:** System und Protokoll zur Namensauflösung, beispielsweise von Domainnamen zu IP-Adressen.

**TCP:** verbindungsorientiertes Transportprotokoll für zuverlässige, geordnete Datenübertragung.

**UDP:** verbindungsloses Transportprotokoll mit geringerem Protokollaufwand.

**Port:** Nummer zur Zuordnung von Netzwerkkommunikation zu Diensten beziehungsweise Anwendungen auf einem Endsystem.

**TLS:** kryptografisches Protokoll zum Schutz von Netzwerkkommunikation.

**Webanwendung:** Anwendung, die über Browser oder App genutzt wird und meist mit Servern zusammenarbeitet.

**Cookie:** kleiner Datensatz im Browser, zum Beispiel für Sitzungen oder Einstellungen.

**Sitzung:** zusammenhängende Nutzung einer Anwendung, häufig mit Anmeldung.

→ Vorwissen: Klasse 8, **Netzwerke**; Klasse 7, **Dateien, Ordner und Pfade**.
