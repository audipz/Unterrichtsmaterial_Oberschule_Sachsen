# 6 Mobile Endgeräte

## Was macht ein Gerät mobil?

Smartphones, Tablets, Smartwatches und andere tragbare Computersysteme begleiten Menschen an unterschiedliche Orte. Sie müssen deshalb andere Anforderungen erfüllen als ein typischer stationärer Desktop-PC.

Ein mobiles Gerät soll beispielsweise mit einem Akku arbeiten, leicht und kompakt sein, unterwegs Daten übertragen können, seine Umgebung über Sensoren wahrnehmen und persönliche Daten trotz Verlust oder Diebstahl schützen.

> **Merke:** Mobile Endgeräte sind vollständige Computersysteme, die besonders auf Funkkommunikation, Sensoren und sparsamen Energieverbrauch angewiesen sind.

## Typische mobile Endgeräte

| Gerät | typische Besonderheit |
|---|---|
| Smartphone | Mobilfunk, Touchscreen, Kameras und viele Sensoren |
| Tablet | großer Touchscreen, häufig WLAN und teilweise Mobilfunk |
| Smartwatch | sehr klein, körpernah getragen, Sensoren und begrenzter Akku |
| E-Book-Reader | stromsparende Anzeige, lange Akkulaufzeit |
| Notebook | mobile Nutzung, aber meist größer und leistungsfähiger |
| Fitness-Tracker | Bewegungs- und Umgebungssensoren |

## Hardware eines Smartphones

Ein Smartphone enthält viele Bestandteile, die bereits aus dem EVA(S)-Prinzip bekannt sind.

| Bestandteil | Aufgabe |
|---|---|
| Prozessor/CPU | führt Programmbefehle aus |
| Arbeitsspeicher | hält aktuell benötigte Programme und Daten bereit |
| Flash-Speicher | speichert Apps, Betriebssystem und Dateien dauerhaft |
| Touchscreen | Ein- und Ausgabe |
| Kamera und Mikrofon | erfassen Bild, Video und Ton |
| Funkmodule | WLAN, Bluetooth und Mobilfunk |
| Sensoren | erfassen Lage, Bewegung, Helligkeit und weitere Größen |
| Akku | versorgt das Gerät mit Energie |

Viele Funktionen befinden sich auf hochintegrierten Chips. Dadurch kann ein Smartphone trotz kleiner Bauform sehr leistungsfähig sein.

## Mobiles Betriebssystem und Apps

Ein **Betriebssystem** verwaltet die Hardware und stellt grundlegende Dienste für Apps bereit. Es startet und beendet Apps, verwaltet Speicher und Dateien, steuert Netzwerkverbindungen, vermittelt Sensorzugriffe, kontrolliert Berechtigungen und versucht Energie zu sparen.

Eine **App** ist ein Anwendungsprogramm. Sie verwendet Funktionen des Betriebssystems, statt jede Hardwarekomponente direkt selbst zu steuern.

```text
Benutzer → App → Betriebssystem → Hardware
```

## Sensoren

Mobile Geräte können ihre Umgebung und Bewegung über verschiedene Sensoren erfassen.

**Beschleunigungssensor:** misst Beschleunigungen entlang mehrerer Achsen und hilft, Bewegung oder Lageänderungen zu erkennen.

**Gyroskop:** misst Drehbewegungen und unterstützt die Bestimmung der Geräteorientierung.

**Helligkeitssensor:** misst die Umgebungshelligkeit, beispielsweise für die automatische Displayhelligkeit.

**Näherungssensor:** erkennt, dass sich etwas sehr nah am Gerät befindet; beim Telefonieren kann dadurch das Display am Ohr ausgeschaltet werden.

**Magnetometer:** misst Magnetfelder und kann als Teil einer digitalen Kompassfunktion verwendet werden.

Auch Kamera und Mikrofon sind Sensoren: Sie wandeln Licht beziehungsweise Schall in digitale Daten um.

## Standortbestimmung

Viele Apps benötigen Informationen über den Standort, beispielsweise Navigation oder Wetterdienste.

Ein Smartphone kann dafür unterschiedliche Informationsquellen kombinieren:

- Satellitennavigation wie GPS oder Galileo,
- WLAN-Informationen,
- Mobilfunkinformationen,
- Bewegungssensoren.

Der Oberbegriff für globale Satellitennavigationssysteme lautet **GNSS**.

Satellitensignale funktionieren besonders gut mit ausreichender Sicht zum Himmel. In Gebäuden oder zwischen hohen Häusern können sie schwächer oder ungenauer sein.

### Warum Standortdaten sensibel sind

Ein längerer Standortverlauf kann beispielsweise zeigen, wo jemand wohnt, welche Schule besucht wird, welche Wege regelmäßig genutzt werden und welche Orte häufig besucht werden. Deshalb sollte Standortzugriff nur Apps gegeben werden, die ihn für eine gewünschte Funktion tatsächlich benötigen.

## Funkverbindungen

| Technik | typischer Einsatz |
|---|---|
| WLAN | Verbindung mit einem lokalen Netzwerk und Internetzugang |
| Mobilfunk | Internet und Telefonie unterwegs |
| Bluetooth | Verbindung mit Geräten in kurzer Entfernung |
| NFC | Kommunikation über sehr kurze Distanz |
| GNSS | Empfang von Navigationssignalen |

**WLAN** verbindet ein Gerät typischerweise mit einem Access Point. Öffentliche WLAN-Netze sind nicht automatisch vertrauenswürdig.

**Mobilfunk** ermöglicht Kommunikation über ein Netz aus Funkzellen. Bewegt sich ein Gerät, kann die Verbindung von einer Funkzelle zur nächsten weitergegeben werden.

**Bluetooth** wird beispielsweise für Kopfhörer, Lautsprecher, Tastaturen oder Wearables verwendet.

**NFC** bedeutet Near Field Communication und funktioniert nur über sehr kurze Entfernungen. Es kann beispielsweise für Tags, Zugangssysteme oder kontaktlose Zahlungsverfahren eingesetzt werden. NFC ist zunächst eine Übertragungstechnik; ein Bezahlsystem umfasst darüber hinaus weitere Komponenten.

## Berechtigungen

Apps dürfen auf modernen Betriebssystemen nicht automatisch auf alle persönlichen Daten und Sensoren zugreifen. Für bestimmte Funktionen benötigen sie **Berechtigungen**, beispielsweise für Kamera, Mikrofon, Standort, Kontakte, Fotos oder Bluetooth-Geräte.

Eine Berechtigung sollte zum Zweck einer App passen. Eine Navigations-App benötigt plausibel den Standort. Eine einfache Taschenlampen-App benötigt normalerweise keinen Zugriff auf Kontakte.

Betriebssysteme können Zugriffe teilweise einschränken, beispielsweise auf nur während der Nutzung, einmaligen Zugriff, ungefähren statt genauen Standort oder ausgewählte Fotos.

## Apps werden voneinander getrennt

Mobile Betriebssysteme versuchen, Apps in getrennten Bereichen auszuführen. Eine App soll nicht ohne Weiteres auf die privaten Dateien einer anderen App zugreifen können. Diese Abschottung wird häufig als **Sandbox** bezeichnet.

```text
App A ─┐
App B ─┼─ Betriebssystem ─ Hardware und gemeinsame Dienste
App C ─┘
```

Das Betriebssystem kontrolliert die Zugriffe.

## Energieverbrauch

Bei mobilen Geräten ist Energie eine besonders knappe Ressource. Besonders viel Energie können hohe Displayhelligkeit, aufwendige Spiele und Grafik, Videoaufnahme, dauerhafte Standortbestimmung, intensive Datenübertragung und viele Hintergrundaktivitäten benötigen.

Auch schlechte Funkbedingungen können den Energiebedarf erhöhen, weil das Gerät mehr Aufwand für eine stabile Verbindung betreiben muss.

## Hintergrundaktivität und Push

Eine App kann teilweise weiterarbeiten, obwohl sie nicht sichtbar geöffnet ist. Das ist beispielsweise für Musik, Navigation, Synchronisation oder Benachrichtigungen nützlich. Mobile Betriebssysteme begrenzen Hintergrundaktivitäten, damit nicht jede App dauerhaft Akku und Datenverbindung beansprucht.

Viele Apps erhalten neue Informationen über **Push-Dienste**:

```text
Server → Push-Dienst → Smartphone → App/Benachrichtigung
```

Dadurch muss nicht jede App ständig selbst beim eigenen Server nachfragen.

## Lokaler Speicher und Cloud

Daten können auf dem Gerät selbst oder auf entfernten Servern gespeichert werden.

**Lokale Speicherung** ermöglicht schnelle Zugriffe und kann auch ohne Internet funktionieren. Bei Geräteverlust oder Defekt können Daten jedoch verloren gehen, wenn keine Sicherung existiert.

**Cloud-Dienste** ermöglichen beispielsweise geräteübergreifenden Zugriff und Synchronisation. Dafür werden Kontosicherheit, Datenschutz und Netzwerkverfügbarkeit wichtig.

## Synchronisation ist nicht automatisch Backup

Bei einer **Synchronisation** werden Datenbestände abgeglichen. Wird eine Datei auf einem Gerät gelöscht, kann diese Löschung auch auf andere Geräte übertragen werden.

Ein **Backup** ist dagegen eine Sicherung, mit der verlorene oder frühere Daten wiederhergestellt werden sollen.

> **Merke:** „In der Cloud“ bedeutet nicht automatisch „sicher gesichert“.

→ Siehe Klasse 7 zu Dateien, Ordnern und Datensicherung.

## Schutz bei Verlust

Ein Smartphone enthält häufig persönliche Fotos, Nachrichten, E-Mails und Zugangsdaten. Sinnvolle Schutzmaßnahmen sind unter anderem eine sichere Gerätesperre, Verschlüsselung des Gerätespeichers, Ortungs- beziehungsweise Fernsperrfunktionen, sichere Konten, regelmäßige Updates und Sicherungen wichtiger Daten.

Fingerabdruck oder Gesichtserkennung können eine bequeme Entsperrung ermöglichen. Typischerweise existiert zusätzlich weiterhin ein PIN- oder Passwortmechanismus.

## Updates und Lebensdauer

Betriebssysteme und Apps enthalten Fehler. Manche davon sind Sicherheitslücken. Updates können bekannte Fehler schließen.

Deshalb ist bei einem mobilen Gerät nicht nur die Hardware wichtig, sondern auch die Frage: **Wie lange erhält es Sicherheitsupdates?**

Ein Gerät kann technisch noch funktionieren, aber problematisch werden, wenn bekannte Sicherheitslücken nicht mehr behoben werden.

## Was gehört nicht speziell zu mobilen Endgeräten?

Einige Themen begegnen häufig auf Smartphones, sind aber allgemeine Internet- oder Webthemen.

| Thema | richtige Einordnung |
|---|---|
| Cookies | Browser und Webanwendungen |
| personalisierte Werbung | Tracking und Geschäftsmodelle digitaler Dienste |
| Netiquette | digitale Kommunikation allgemein |
| Phishing | Informationssicherheit allgemein |
| Urheberrecht beim Teilen von Bildern | Umgang mit digitalen Medien allgemein |

Diese Themen bleiben wichtig, sollten aber nicht mit der technischen Funktionsweise mobiler Geräte verwechselt werden.

## Nachhaltigkeit

Mobile Geräte benötigen Rohstoffe und Energie für Herstellung und Betrieb. Eine längere Nutzungsdauer kann Ressourcen sparen. Dabei spielen Reparierbarkeit, austauschbare beziehungsweise ersetzbare Akkus, Ersatzteilversorgung, Softwareupdates, Wiederverwendung und Recycling eine Rolle.

## Begriffe zum Nachschlagen

**App:** Anwendungsprogramm für ein Computersystem.

**App-Berechtigung:** Erlaubnis für eine App, auf bestimmte geschützte Daten oder Gerätefunktionen zuzugreifen.

**Bluetooth:** Funktechnik für Verbindungen über kurze Entfernungen.

**Cloud:** über ein Netzwerk bereitgestellte Speicher- oder Rechendienste auf entfernten Systemen.

**GNSS:** Oberbegriff für globale Satellitennavigationssysteme wie GPS und Galileo.

**NFC:** Funktechnik für Kommunikation über sehr kurze Entfernung.

**Push-Benachrichtigung:** über einen Vermittlungsdienst an ein Gerät beziehungsweise eine App zugestellte Nachricht.

**Sandbox:** abgeschotteter Bereich, der Zugriffe einer App begrenzt.

**Sensor:** Bauteil, das physikalische Größen erfasst und in verarbeitbare Daten umwandelt.

**Synchronisation:** Abgleich von Datenbeständen zwischen mehreren Systemen.

→ Siehe auch **Kapitel 4 Netzwerke** und **Kapitel 5 Steuern und Regeln**. In Klasse 9 wird mobile Informatik weiter vertieft; digitale Bezahlsysteme folgen in Klasse 10.