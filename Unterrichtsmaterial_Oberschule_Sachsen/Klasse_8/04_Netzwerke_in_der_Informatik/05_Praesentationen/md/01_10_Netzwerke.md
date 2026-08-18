# Präsentation – Netzwerke in der Informatik

> Pflichtpräsentation ohne Kryptografie. Kryptografie wird als separate optionale Zusatzpräsentation geführt.

## Folie 1 – Leitfrage
Wie gelangen Daten von unserem Gerät zu einem Dienst im Netzwerk oder Internet?

> Layout-Hinweis: Leitfragenkasten nicht durch Grafiken überdecken.

## Folie 2 – Was ist ein Netzwerk?
- verbindet Informatiksysteme
- ermöglicht Datenaustausch
- ermöglicht gemeinsame Nutzung von Diensten
- Beispiele: Schulnetz, Heimnetz, Internet

## Folie 3 – LAN und WLAN
- **LAN = Local Area Network**: lokales Netzwerk in begrenztem Bereich
- **WLAN = Wireless Local Area Network**: drahtloser Zugang zu einem lokalen Netzwerk
- WLAN ist nicht dasselbe wie Internet

## Folie 4 – Client, Server, Switch, Access Point, Router
- Client: nutzt einen Dienst
- Server: stellt einen Dienst bereit
- Switch: verbindet Geräte im lokalen kabelgebundenen Netz
- Access Point: ermöglicht WLAN-Zugang
- Router: verbindet unterschiedliche Netze

## Folie 5 – IP-Adresse und DNS
- **IP = Internet Protocol**
- IP-Adresse dient der Adressierung im Netzwerk
- **DNS = Domain Name System**
- DNS hilft, Namen wie `www.example.org` passenden IP-Adressen zuzuordnen

## Folie 6 – Übertragungsmedien
- Kupferkabel
- Glasfaser
- Funk/WLAN
- Auswahl nach Reichweite, Mobilität, Umgebung und Zweck

## Folie 7 – Was ist ein Protokoll?
Ein Protokoll legt Regeln für Kommunikation fest, z. B. Aufbau und Austausch von Nachrichten.

## Folie 8 – Wichtige Protokolle ausgeschrieben
- **HTTP = Hypertext Transfer Protocol** → Webseiten übertragen
- **HTTPS = Hypertext Transfer Protocol Secure** → Webseiten geschützt übertragen
- **SMTP = Simple Mail Transfer Protocol** → E-Mails versenden
- **IMAP = Internet Message Access Protocol** → E-Mails auf dem Server abrufen/verwalten
- **SFTP = SSH File Transfer Protocol** → Dateien über eine geschützte Verbindung übertragen

## Folie 9 – Was ist ein Netzwerkdienst?
Ein Netzwerkdienst stellt über ein Netzwerk eine bestimmte Funktion bereit, die andere Geräte oder Programme nutzen können.

Wichtig: Dienst, Programm und Gerät sind nicht dasselbe.

## Folie 10 – Dienste im Alltag
- Webseite öffnen → **World Wide Web (WWW)**: Webserver liefert Inhalte an Browser
- E-Mail senden → E-Mail-Dienst: Mailserver nimmt Nachricht an und leitet sie weiter
- Datei hochladen → Dateiübertragung
- Schulcloud nutzen → Cloud-Dienst: entfernte Server speichern Daten

## Folie 11 – Datenwege untersuchen: Wie prüfe ich?
Beispielproblem: Lernplattform öffnet sich nicht.

1. Gerät verbunden?
   - Kabel eingesteckt?
   - richtiges WLAN verbunden?
2. Lokales Netz erreichbar?
   - gegebenenfalls `ping` zu einem von der Lehrkraft vorgegebenen Testziel
3. Andere bekannte Webseite erreichbar?
4. Namensauflösung/DNS als mögliche Fehlerstelle prüfen
5. Funktioniert nur der gewünschte Dienst nicht?

Lehrerhinweis: Ein fehlgeschlagener `ping` beweist nicht zwingend Unerreichbarkeit, da Ping-Antworten blockiert werden können.

## Folie 12 – Sicherung
Das solltest du erklären können:
- Datenweg Endgerät → lokales Netz → Router → Internet → Server
- Aufgaben von Router, Switch und Access Point
- IP-Adresse und DNS
- Unterschied Protokoll und Netzwerkdienst

## Quellen
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Grafiken und Diagnosebeispiele: eigene didaktische Darstellung.
