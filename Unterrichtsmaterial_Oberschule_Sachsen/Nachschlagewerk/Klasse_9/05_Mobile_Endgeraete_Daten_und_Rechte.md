# 5 Mobile Endgeräte und mobile Informatik

## Warum mobile Endgeräte ein eigenes Informatikthema sind

Smartphones, Tablets, Smartwatches und andere mobile Geräte sind vollständige Computersysteme, die zusätzlich besonders eng mit ihrer Umgebung verbunden sind. Sie besitzen Sensoren, Funktechniken, Kameras, Mikrofone und häufig einen ständig verfügbaren Netzwerkzugang. Gleichzeitig werden sie getragen, entsperrt, bewegt und an vielen Orten benutzt.

Dadurch entstehen besondere informatische Fragen:

- Wie erkennt ein Gerät seine Umgebung?
- Wie bestimmt es seinen Standort?
- Wie kommuniziert es unterwegs?
- Wie verwaltet ein mobiles Betriebssystem Apps und knappe Ressourcen?
- Wie werden persönliche Daten auf einem Gerät geschützt, das leicht verloren gehen kann?
- Welche Informationen können Sensoren und Apps über eine Person ableiten?

Allgemeine Webthemen wie Cookies, personalisierte Werbung oder Netiquette gehören dagegen **nicht speziell zu mobilen Endgeräten**. Sie können auf einem Smartphone auftreten, gelten aber genauso auf einem Notebook oder Desktop-PC. Dieses Kapitel konzentriert sich deshalb auf die Besonderheiten **mobiler Informatik**.

> **Merke:** Ein Smartphone ist nicht nur ein kleines Telefon. Es ist ein vernetztes Computersystem mit zahlreichen Sensoren und persönlichen Daten.

## Typische Bestandteile eines Smartphones

Ein modernes Smartphone enthält viele Komponenten, die auch aus anderen Computersystemen bekannt sind.

| Bestandteil | Aufgabe |
|---|---|
| CPU | führt allgemeine Programmbefehle aus |
| GPU | berechnet Grafik und viele parallele Operationen |
| Arbeitsspeicher | hält aktuell benötigte Programme und Daten bereit |
| Flash-Speicher | speichert Betriebssystem, Apps und Dateien dauerhaft |
| Mobilfunkmodem | stellt Mobilfunkverbindungen her |
| WLAN/Bluetooth | Kommunikation mit lokalen Netzen und Geräten |
| GNSS-Empfänger | empfängt Signale von Satellitennavigationssystemen |
| Kamera/Mikrofon | erfassen Bild und Ton |
| Sensoren | erfassen Bewegung, Lage, Helligkeit und weitere Umgebungswerte |
| Akku | versorgt das mobile System mit Energie |

Viele dieser Bestandteile sind in hochintegrierten Chips zusammengefasst. Dadurch können mobile Geräte leistungsfähig und gleichzeitig klein sein.

## Sensoren: Das Gerät nimmt seine Umgebung wahr

Mobile Geräte besitzen Sensoren, die physikalische Größen messen und als Daten für Programme bereitstellen.

### Beschleunigungssensor

Ein **Beschleunigungssensor** misst Beschleunigungen entlang mehrerer Achsen. Damit kann ein Gerät beispielsweise Bewegungen und Änderungen seiner Lage erkennen.

Anwendungen sind unter anderem:

- automatische Drehung der Anzeige,
- Schritt- und Bewegungsanalyse,
- Spiele,
- Erkennung bestimmter Gesten.

### Gyroskop

Ein **Gyroskop** misst Drehbewegungen. Zusammen mit dem Beschleunigungssensor lässt sich die Orientierung eines Geräts genauer bestimmen.

### Magnetometer

Ein **Magnetometer** misst Magnetfelder und kann unter geeigneten Bedingungen als Teil einer digitalen Kompassfunktion dienen.

### Näherungs- und Helligkeitssensor

Ein **Näherungssensor** kann beispielsweise erkennen, dass sich das Smartphone beim Telefonieren am Ohr befindet. Der Bildschirm kann dann deaktiviert werden.

Ein **Umgebungslichtsensor** misst die Helligkeit der Umgebung und ermöglicht eine automatische Anpassung der Displayhelligkeit.

### Kamera und Mikrofon sind ebenfalls Sensoren

Auch Kamera und Mikrofon wandeln physikalische Erscheinungen in digitale Daten um:

- Licht → Bilddaten,
- Schall → Audiodaten.

Sie sind deshalb aus informatischer Sicht besonders leistungsfähige Sensoren – und gleichzeitig datenschutzrelevant.

## Sensordaten werden kombiniert

Ein einzelner Sensor ist nicht immer ausreichend genau. Mobile Systeme kombinieren deshalb häufig mehrere Messquellen. Diese Zusammenführung wird **Sensorfusion** genannt.

Beispiel Navigation:

```text
Satellitennavigation
       +
Beschleunigungssensor
       +
Gyroskop
       +
Magnetometer
       +
Netzwerkinformationen
       ↓
verbesserte Schätzung von Position und Bewegung
```

> **Merke:** Ein Smartphone „weiß“ seine Lage oder Bewegung nicht direkt. Programme berechnen sie aus Messwerten verschiedener Sensoren.

## Standortbestimmung

Die Standortanzeige eines Smartphones stammt nicht einfach aus „GPS“. Moderne Geräte können mehrere Verfahren kombinieren.

### Satellitennavigation

**GNSS** ist der Oberbegriff für globale Satellitennavigationssysteme. Dazu gehören beispielsweise GPS, Galileo, GLONASS und BeiDou.

Satelliten senden sehr genaue Zeit- und Bahndaten. Aus den Signallaufzeiten mehrerer Satelliten kann ein Empfänger seine Position berechnen. Dafür muss das Smartphone die Entfernung zu mehreren Satelliten bestimmen.

Satellitennavigation funktioniert besonders gut mit ausreichender Sicht zum Himmel. In Gebäuden, Tunneln oder zwischen hohen Gebäuden können Signale schwächer oder durch Reflexionen ungenauer werden.

### WLAN und Mobilfunk

Ein Gerät kann seine Position zusätzlich anhand bekannter WLAN-Zugangspunkte und Informationen aus Mobilfunknetzen abschätzen. Das kann insbesondere dort helfen, wo Satellitensignale schlecht empfangbar sind.

### Warum Standortdaten sensibel sind

Ein einzelner Standort kann bereits viel verraten. Eine längere Folge von Standortdaten kann beispielsweise Rückschlüsse ermöglichen auf:

- Wohnort,
- Schule oder Arbeitsplatz,
- regelmäßige Wege,
- Freizeitorte,
- soziale Kontakte,
- Tagesabläufe.

Deshalb sollte Standortzugriff nur dort freigegeben werden, wo er für eine gewünschte Funktion sinnvoll ist.

## Funkverbindungen eines mobilen Geräts

Ein Smartphone kann gleichzeitig mehrere Funktechniken verwenden.

| Technik | typische Aufgabe |
|---|---|
| Mobilfunk | Internetzugang und Telefonie über ein Mobilfunknetz |
| WLAN | Verbindung mit lokalen Funknetzen |
| Bluetooth | Verbindung mit Geräten in der näheren Umgebung |
| NFC | Kommunikation über sehr kurze Entfernung |
| GNSS | Empfang von Navigationssignalen; keine normale Internetverbindung |

Die Techniken unterscheiden sich in Reichweite, Energiebedarf, Datenrate und Einsatzzweck.

### Mobilfunk und Funkzellen

Ein Mobilfunknetz ist in **Funkzellen** gegliedert. Ein mobiles Gerät verbindet sich mit geeigneter Netzinfrastruktur in seiner Umgebung. Bewegt sich das Gerät, kann die Verbindung an eine andere Funkzelle übergeben werden. Dieser Übergang wird **Handover** genannt.

Dadurch kann eine Datenverbindung während einer Fahrt grundsätzlich weiterbestehen, obwohl sich das Gerät durch verschiedene Funkzellen bewegt.

### WLAN

WLAN verbindet ein Gerät typischerweise über einen Access Point mit einem lokalen Netzwerk. Ein öffentliches WLAN ist nicht automatisch vertrauenswürdig. Schutz durch verschlüsselte Anwendungsprotokolle wie HTTPS bleibt wichtig.

### Bluetooth

Bluetooth wird beispielsweise für Kopfhörer, Tastaturen, Wearables oder Datenaustausch über kurze Entfernungen eingesetzt. Geräte müssen häufig zunächst gekoppelt beziehungsweise **gepairt** werden.

### NFC

**Near Field Communication** arbeitet über sehr kurze Distanzen. NFC kann beispielsweise für kontaktlose Karten, Tags oder Zahlungsverfahren verwendet werden.

NFC selbst ist aber weder „Bezahlen“ noch automatisch ein Sicherheitsverfahren. Es ist zunächst eine Kommunikationstechnik. Digitale Bezahlsysteme werden in Klasse 10 ausführlicher betrachtet.

## Apps und mobiles Betriebssystem

Ein mobiles Betriebssystem verwaltet Hardware, Dateien, Netzwerkzugriffe und Apps. Apps laufen nicht einfach mit unbegrenztem Zugriff auf das gesamte Gerät.

Moderne Systeme versuchen Anwendungen voneinander zu trennen. Eine App erhält einen eigenen Bereich und muss für bestimmte geschützte Funktionen besondere Zugriffe erhalten. Diese Trennung wird häufig als **Sandboxing** bezeichnet.

Vereinfacht:

```text
App A ─┐
       ├─ Betriebssystem ─ Hardware / Sensoren / Dateien
App B ─┤
App C ─┘
```

Das Betriebssystem vermittelt Zugriffe und kann sie erlauben oder verweigern.

## App-Berechtigungen

Eine **Berechtigung** erlaubt einer App den Zugriff auf bestimmte Daten oder Gerätefunktionen, beispielsweise:

- Kamera,
- Mikrofon,
- Standort,
- Kontakte,
- Fotos und Dateien,
- Bluetooth-Geräte.

Eine Berechtigung sollte zum Zweck der gewünschten Funktion passen.

Beispiel: Eine Navigations-App benötigt plausibel einen Standortzugriff. Für eine einfache Taschenlampenfunktion wäre ein dauerhafter Zugriff auf Kontakte dagegen nicht nachvollziehbar.

Betriebssysteme können Berechtigungen unterschiedlich fein steuern, beispielsweise:

- nur während der Nutzung,
- einmalig,
- immer,
- genauer oder ungefährer Standort,
- Zugriff nur auf ausgewählte Fotos.

> **Merke:** Eine technisch mögliche Datennutzung ist nicht automatisch für die gewünschte Funktion notwendig.

## Hintergrundaktivität

Apps können teilweise auch arbeiten, wenn sie nicht sichtbar im Vordergrund geöffnet sind. Hintergrundaktivitäten werden beispielsweise benötigt für:

- Nachrichtenbenachrichtigungen,
- Musik- oder Navigationsfunktionen,
- Synchronisation,
- bestimmte Standortfunktionen.

Unbegrenzte Hintergrundarbeit würde jedoch Akku, Datenvolumen und Rechenleistung stark belasten. Mobile Betriebssysteme begrenzen sie deshalb und versetzen Anwendungen oder Geräte in Energiesparzustände.

## Push-Benachrichtigungen

Bei vielen Apps wartet das Gerät nicht ständig aktiv auf jede mögliche neue Nachricht. Stattdessen können zentrale **Push-Dienste** des Betriebssystems Benachrichtigungen vermitteln.

Vereinfacht:

```text
App-Server
    ↓
Push-Dienst
    ↓
Smartphone
    ↓
Benachrichtigung / App
```

Damit lassen sich Netzwerk- und Energieverbrauch besser steuern, als wenn jede App dauerhaft eine eigene aktive Verbindung unterhalten müsste.

## Energie ist eine knappe Ressource

Bei einem Desktop-PC ist Stromversorgung meist ständig verfügbar. Bei einem Smartphone bestimmt der Akku, wie lange das System mobil nutzbar bleibt.

Besonders energieintensiv können sein:

- helles Display,
- hohe Rechen- oder Grafiklast,
- dauerhafte Funkübertragung,
- schlechte Mobilfunkversorgung,
- kontinuierliche Standortbestimmung,
- Kamera und Videoverarbeitung,
- viele Hintergrundprozesse.

Software für mobile Geräte muss deshalb nicht nur korrekt, sondern häufig auch **energieeffizient** sein.

## Speicher und Cloud-Synchronisation

Mobile Geräte speichern Daten lokal und verwenden häufig zusätzlich Cloud-Dienste. Dadurch können beispielsweise Fotos, Kontakte oder Dokumente zwischen mehreren Geräten synchronisiert werden.

**Synchronisation ist jedoch nicht automatisch dasselbe wie Backup.** Wird eine Datei synchron gelöscht, kann die Löschung auch auf andere Geräte übertragen werden. Ein echtes Sicherungskonzept benötigt gegebenenfalls zusätzliche Versionen oder Sicherungskopien.

→ Siehe Klasse 7: Dateien, Ordner und Datensicherung.

## Datenschutz und Datensicherheit unterscheiden

**Datenschutz** richtet den Blick besonders auf Menschen und die rechtmäßige, zweckgebundene Verarbeitung personenbezogener Daten.

**Datensicherheit** betrachtet Schutzmaßnahmen für Daten und Systeme, beispielsweise gegen Verlust, unbefugten Zugriff oder Veränderung.

Beispiele:

| Maßnahme/Frage | Schwerpunkt |
|---|---|
| Welche App benötigt meinen Standort? | Datenschutz |
| Gerätespeicher verschlüsseln | Datensicherheit |
| nur notwendige personenbezogene Daten erfassen | Datenschutz |
| Sicherheitsupdates installieren | Datensicherheit |
| Berechtigungen auf den Zweck begrenzen | beides |

Beide Bereiche hängen eng zusammen, sind aber nicht identisch.

## Schutz bei Verlust oder Diebstahl

Mobile Geräte sind besonders gefährdet, verloren zu gehen oder gestohlen zu werden. Deshalb sind mehrere Schutzschichten sinnvoll.

### Gerätesperre

PIN, Passwort oder biometrische Entsperrung erschweren den direkten Zugriff. Biometrische Verfahren ersetzen dabei nicht sämtliche Sicherheitsmechanismen; intern existiert weiterhin ein kryptografischer Schutz und meist ein Gerätecode als Rückfallmöglichkeit.

### Gerätespeicherverschlüsselung

Moderne mobile Betriebssysteme können gespeicherte Daten kryptografisch schützen. Eine wirksame Displaysperre ist dabei ein wichtiger Teil des Gesamtschutzes.

### Ortung und Fernfunktionen

Herstellerdienste können je nach Konfiguration ermöglichen,

- ein verlorenes Gerät zu orten,
- es zu sperren,
- eine Nachricht anzuzeigen,
- Daten aus der Ferne zu löschen.

Solche Funktionen müssen häufig vorher aktiviert sein und sind von Netzwerkverbindung und Gerätezustand abhängig.

### Konten absichern

Auf einem Smartphone befinden sich oft Zugänge zu E-Mail, Cloud, sozialen Diensten und weiteren Konten. Deshalb können starke einzigartige Passwörter und Mehr-Faktor-Authentisierung den Schaden eines Geräteverlusts begrenzen.

## Updates und Lebensdauer

Sicherheitslücken werden häufig erst nach Veröffentlichung eines Geräts entdeckt. Betriebssystem- und App-Updates schließen bekannte Fehler und Sicherheitslücken.

Bei der langfristigen Beurteilung eines mobilen Geräts ist deshalb nicht nur die Hardware wichtig, sondern auch:

- Wie lange gibt es Sicherheitsupdates?
- Wie schnell werden bekannte Schwachstellen geschlossen?
- Lassen sich Apps noch sicher aktualisieren?

Ein technisch noch funktionierendes Gerät kann problematisch werden, wenn sicherheitsrelevante Software nicht mehr gepflegt wird.

## Verlust, Reparatur und Nachhaltigkeit

Mobile Informatik hat auch eine materielle Seite. Geräte benötigen Rohstoffe, Energie bei Herstellung und Betrieb und werden irgendwann zu Elektroschrott.

Die Nutzungsdauer kann beeinflusst werden durch:

- Reparierbarkeit,
- austauschbaren beziehungsweise ersetzbaren Akku,
- Ersatzteilversorgung,
- Softwareupdates,
- robuste Bauweise,
- Wiederverwendung und Recycling.

Eine längere sinnvolle Nutzungsdauer kann Ressourcen sparen.

## Welche Daten können aus Sensoren abgeleitet werden?

Datenschutz betrifft nicht nur Daten, die bewusst eingetippt werden. Auch Mess- und Metadaten können Rückschlüsse ermöglichen.

Beispiele:

| Daten | mögliche Ableitung |
|---|---|
| Standortverlauf | häufig besuchte Orte und Wege |
| Bewegungsdaten | Aktivität und Bewegungsmuster |
| WLAN-/Bluetooth-Umgebung | Aufenthaltsumgebung und nahe Geräte |
| Foto-Metadaten | Aufnahmezeit und gegebenenfalls Aufnahmeort |
| Nutzungszeiten | Tagesrhythmus oder Gewohnheiten |

Eine Ableitung ist nicht automatisch korrekt. Werden viele Datenquellen kombiniert, können jedoch sehr aussagekräftige Profile entstehen.

## Allgemeine Online-Themen richtig einordnen

Einige Themen tauchen häufig bei der Smartphone-Nutzung auf, sind aber **keine Besonderheit mobiler Endgeräte**.

| Thema | richtige Einordnung |
|---|---|
| Cookies | Browser- und Webtechnik |
| personalisierte Werbung | Tracking, Profilbildung und Geschäftsmodelle digitaler Dienste |
| Netiquette | allgemeine digitale Kommunikation |
| Teilen fremder Bilder | Urheber- und Persönlichkeitsrechte |
| Phishing | allgemeine Informationssicherheit |

Diese Themen bleiben wichtig. Sie sollten jedoch nicht mit der Funktionsweise eines Smartphones verwechselt werden.

→ Cookies und Webanwendungen werden in Klasse 10 bei webbasierten Anwendungen eingeordnet.  
→ Sicherheit wird in Klasse 10 systematisch vertieft.  
→ Verantwortlicher Umgang mit Daten und digitalen Inhalten beginnt bereits in Klasse 7.

## Mobile Systeme als Zusammenspiel

Eine Navigations-App zeigt besonders gut, wie viele Informatikbereiche gleichzeitig beteiligt sind:

```text
Sensoren + GNSS
       ↓
Standortschätzung
       ↓
Mobilfunk/WLAN
       ↓
Serveranfrage
       ↓
Karten- und Verkehrsdaten
       ↓
Routenalgorithmus
       ↓
Anzeige + Sprachausgabe
```

Dazu kommen Berechtigungen, Datenschutz, Energieverbrauch, lokale Speicherung und Netzwerksicherheit.

> **Merke:** Mobile Informatik verbindet Hardware, Sensoren, Betriebssysteme, Netzwerke, Algorithmen, Daten und Sicherheit.

## Begriffe zum Nachschlagen

**App-Berechtigung:** vom Betriebssystem kontrollierte Erlaubnis für den Zugriff einer App auf geschützte Daten oder Gerätefunktionen.

**Bluetooth:** Funktechnik für Verbindungen über kurze Entfernungen, beispielsweise zu Kopfhörern und Wearables.

**GNSS:** Oberbegriff für globale Satellitennavigationssysteme wie GPS und Galileo.

**Handover:** Übergabe einer laufenden Mobilfunkverbindung zwischen Funkzellen beziehungsweise Netzelementen.

**NFC:** Funktechnik für Kommunikation über sehr kurze Entfernung.

**Sandbox:** abgeschotteter Ausführungs- beziehungsweise Datenbereich, der Zugriffe einer App begrenzt.

**Sensor:** Bauteil, das eine physikalische Größe erfasst und in verarbeitbare Messdaten umwandelt.

**Sensorfusion:** Kombination mehrerer Sensor- oder Datenquellen, um einen Zustand zuverlässiger zu schätzen.

**Synchronisation:** Abgleich von Datenbeständen zwischen Systemen; nicht automatisch eine unabhängige Sicherung.

**Push-Benachrichtigung:** über einen Vermittlungsdienst zugestellte Information an eine App beziehungsweise ein Gerät.

→ Vorwissen: Klasse 7, **Daten verantwortungsvoll nutzen** und **Dateiverwaltung**.  
→ Vorwissen: Klasse 8, **Netzwerke**.  
→ Weiterführung: Klasse 10, **Webbasierte Anwendungen**, **Informationssicherheit** und **digitale Bezahlsysteme**.