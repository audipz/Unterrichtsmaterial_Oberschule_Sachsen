# Präsentation – Lernbereich 1: Webbasierte Anwendungen

## Zweck und Einsatz

Diese Datei ist die inhaltliche Masterquelle für die PowerPoint-Präsentation zum Lernbereich. Die Präsentation begleitet mehrere Unterrichtsstunden und ist nicht als Vortrag aller Folien in einer Einzelstunde gedacht.

Roter Faden: **Webanwendung als Gesamtsystem verstehen → Architektur → Cloud → Verantwortung → IT-Security → Datenschutz → KI → verantwortungsvoller Betrieb.**

Durchgängiges Beispiel ist eine Webanwendung für ein **Schülerfestival**: Schülerinnen und Schüler melden sich an, wählen Workshops und erhalten einen persönlichen Zeitplan.

## Fachliche Leitgedanken

- Eine Webanwendung ist nicht nur das Frontend im Browser, sondern kann aus Frontend, Backend, Datenhaltung und weiteren Diensten bestehen.
- „Webbasiert“ legt nicht fest, wo die gesamte Programmlogik ausgeführt wird.
- Client/Server und 3-Tier beschreiben unterschiedliche Aspekte eines Systems.
- Ein Tier ist nicht automatisch ein einzelner Rechner oder Server.
- Cloud ist keine zusätzliche Schicht des 3-Tier-Modells, sondern eine Form der Bereitstellung von IT-Ressourcen und Diensten.
- IT-Sicherheit und Datenschutz überschneiden sich, sind aber nicht dasselbe.
- Eine technisch funktionierende Anwendung ist nicht automatisch sicher, datenschutzgerecht oder verantwortungsvoll betrieben.
- KI kann sowohl Angreifer als auch Verteidiger unterstützen. KI-generierte Ergebnisse müssen geprüft werden.

---

## Folie 1 – Einstieg: Wo läuft eine Webanwendung?

**Leitfrage:**

> Du öffnest eine Webanwendung im Browser. Wo läuft sie eigentlich?

Zunächst nur mögliche Antworten zeigen bzw. sammeln:

- im Browser?
- auf meinem Rechner?
- auf einem Server?
- in einem Rechenzentrum?
- in der Cloud?

**Didaktik:** Noch nicht auflösen. Schüler begründen Vermutungen.

**Lehrerhinweis:** Mehrere Antworten können gleichzeitig richtig sein. Genau diese Mehrdeutigkeit eröffnet die Architekturbetrachtung.

---

## Folie 2 – Was gehört zur Webanwendung?

Beispiel Schülerfestival:

1. anmelden
2. Workshops ansehen
3. Workshop wählen
4. persönlichen Zeitplan erhalten

**Frage:** Welche Teile eines IT-Systems brauchen wir dafür?

Nach Klick einzeln einblenden:

- Benutzeroberfläche
- Programmlogik
- Daten
- Benutzer- und Rechteverwaltung
- Netzwerkkommunikation
- ggf. externe Dienste

**Merksatz:**

> Eine Webanwendung ist ein Anwendungssystem – nicht nur die sichtbare Webseite.

---

## Folie 3 – Das 3-Tier-Modell

```text
Presentation Tier
Benutzerschnittstelle
        ↓
Application Tier
Anwendungslogik
        ↓
Data Tier
Datenhaltung
```

Beispiele:

- Presentation Tier: Browseroberfläche, HTML, CSS, JavaScript
- Application Tier: Backend, API, Geschäfts-/Anwendungslogik
- Data Tier: relationale Datenbank, andere Datenbanken, Datei-/Objektspeicher

**Lehrerhinweis:** Ein Tier ist eine logische bzw. Bereitstellungsebene. Drei Tiers bedeuten nicht zwingend drei physische Rechner. Mehrere Tiers können auf einem System laufen oder ein Tier kann auf viele Systeme verteilt sein.

---

## Folie 4 – Client/Server ist nicht dasselbe wie 3-Tier

**Client/Server:** Wer kommuniziert mit wem und wer fordert einen Dienst an?

**3-Tier:** Wie werden Verantwortlichkeiten einer Anwendung strukturiert?

**Frage:** Muss das Presentation Tier vollständig auf dem Client laufen?

Antwort erst nach Diskussion: **Nein.**

**Lehrerhinweis:** Die Begriffe Frontend = Client und Backend = Server sind als grobes Einstiegsmodell nützlich, aber nicht allgemeingültig.

---

## Folie 5 – Beispiel: clientseitige Webanwendung

Beispiel Angular-SPA:

```text
Webserver
  │
  ├── HTML
  ├── CSS
  └── JavaScript
        │ Download
        ▼
      Browser
        │
        │ API-Anfragen
        ▼
      Backend
        │
        ▼
    Datenhaltung
```

**Kernaussage:** Ein großer Teil des Frontends und seiner Logik wird vom Server bereitgestellt und anschließend lokal im Browser ausgeführt.

**Lehrerhinweis:** Angular dient als konkretes Beispiel, nicht als verpflichtendes Frameworkwissen.

---

## Folie 6 – Beispiel: serverseitiges Rendering

Beispiel klassische PHP-Anwendung:

```text
Browser
   │ Anfrage
   ▼
Webserver / PHP
   │
   ├── Anwendungslogik
   ├── ggf. Datenbankzugriff
   │
   ▼
erzeugtes HTML
   │
   ▼
Browser
```

**Kernaussage:** Der Server kann HTML dynamisch erzeugen. JavaScript kann trotzdem zusätzlich clientseitig eingesetzt werden.

---

## Folie 7 – Moderne Anwendungen sind oft Mischformen

Client und Server können jeweils Teile der Verarbeitung übernehmen.

**Leitfrage:**

> Wer erledigt welche Arbeit – und warum?

Mögliche Kriterien:

- Rechenaufwand
- Datenzugriff
- Sicherheit
- Reaktionsgeschwindigkeit
- Offline-Fähigkeit
- Wartbarkeit

**Merksatz:**

> „Webanwendung“ sagt nicht automatisch, wo ihre gesamte Programmlogik ausgeführt wird.

---

## Folie 8 – Wie kommunizieren die Teile?

Vereinfachtes Kommunikationsmodell:

```text
Browser / Client ↔ Netzwerk ↔ Webserver / API ↔ weitere Dienste
```

Begriffe:

- Request / Anfrage
- Response / Antwort
- HTTP / HTTPS
- API als definierte Schnittstelle

**Lehrerhinweis:** Protokolle als gemeinsame Regeln der Kommunikation wiederholen. HTTPS nicht mit „Website ist vertrauenswürdig“ gleichsetzen; es schützt insbesondere die Übertragung und bindet sie an eine authentisierte Gegenstelle über Zertifikate.

---

## Folie 9 – Hypertext und HTML

**Internet ≠ World Wide Web.**

Das Web nutzt u. a. Hypertext, URLs und HTTP(S) auf der Internet-Infrastruktur.

Kleines HTML-Beispiel:

```html
<h1>Schülerfestival</h1>
<p>Programm am Freitag</p>
<a href="programm.html">Zum Programm</a>
```

Daneben in der PPT die gerenderte Ansicht zeigen.

**Lehrerhinweis:** HTML ist eine Auszeichnungssprache, keine Programmiersprache im engeren Sinn.

---

## Folie 10 – Webseite als strukturierte Elemente

Brücke zu bekannten Objekt-/Datenvorstellungen:

- Überschrift
- Link
- Bild
- Formularfeld

Elemente besitzen Eigenschaften bzw. Attribute.

**Hinweis:** Nicht behaupten, dass HTML-Elemente automatisch dasselbe sind wie Objekte eines objektorientierten Domänenmodells. Die Analogie dient der Strukturbetrachtung.

---

## Folie 11 – Und wo steht der Server?

Möglichkeiten sammeln:

- eigener Rechner/Server
- schulisches Rechenzentrum
- Hosting-Anbieter
- Cloud-Infrastruktur

**Frage:**

> Ist „Cloud“ eine Anwendungsarchitektur wie 3-Tier?

Nach Klick: **Nein.**

Eine 3-Tier-Anwendung kann lokal, in einem Rechenzentrum, in der Cloud oder verteilt betrieben werden.

---

## Folie 12 – Was bedeutet Cloud?

```text
mein Gerät
    │
 Internet
    │
Rechenzentrum
├─ Rechenleistung
├─ Speicher
├─ Netzwerk
└─ Dienste
```

**Merksatz:**

> Cloud bedeutet nicht, dass Daten „irgendwo schweben“. Reale IT-Infrastruktur wird über Netze flexibel als Dienst bereitgestellt.

Diskussionsfragen:

- Wo liegen die Daten tatsächlich?
- Wer betreibt die Infrastruktur?
- Was passiert bei einem Ausfall?
- Was passiert ohne Netzverbindung?
- Wie können Daten gesichert oder migriert werden?

---

## Folie 13 – SaaS, PaaS und IaaS

Nur als Orientierungsmodell, nicht als reine Lernwortliste:

**SaaS:** Ich nutze eine fertige Anwendung.

**PaaS:** Ich entwickle/deploye meine Anwendung; die Plattform übernimmt wesentliche Laufzeit- und Betriebsaufgaben.

**IaaS:** Ich erhalte Infrastrukturressourcen und betreibe darauf mehr Komponenten selbst.

**Aufgabe:** Bei welchem Modell muss der Nutzer/Kunde typischerweise mehr technische Betriebsverantwortung übernehmen?

**Lehrerhinweis:** Die Grenzen realer Angebote können unscharf sein.

---

## Folie 14 – Shared Responsibility

Einstiegsaussage:

> „Wenn die Anwendung in der Cloud läuft, kümmert sich der Cloudanbieter um die Sicherheit.“

Abstimmung: richtig / falsch / zu einfach.

Nach Klick: **Zu einfach.**

Zuordnungsbeispiele:

- physisches Rechenzentrum
- Hardware
- Netzwerk
- Betriebssystem
- Anwendung
- Konfiguration
- Benutzerkonten
- Rollen und Berechtigungen
- Daten

**Lehrerhinweis:** Die konkrete Verantwortungsverteilung hängt vom Dienstmodell und Vertrag ab. Shared Responsibility nicht als universell identische Tabelle darstellen.

---

## Folie 15 – Unsere Anwendung funktioniert. Ist sie sicher?

Zunächst nur:

> Die Anmeldung funktioniert. Workshops können gewählt werden. Der Zeitplan wird angezeigt.
>
> **Ist die Anwendung damit gut?**

Nach Diskussion einblenden:

- Funktionalität
- Sicherheit
- Datenschutz
- Zuverlässigkeit
- Wartbarkeit
- verantwortungsvoller Betrieb

---

## Folie 16 – IT-Security: Was bedeutet „sicher“?

CIA-Schutzziele:

- **Confidentiality / Vertraulichkeit**
- **Integrity / Integrität**
- **Availability / Verfügbarkeit**

Beispiele zeilenweise nach Klick:

| Vorfall | Schutzziel |
|---|---|
| Fremde lesen Teilnehmerdaten | Vertraulichkeit |
| Workshop-Zuordnungen werden manipuliert | Integrität |
| Anmeldung ist nicht erreichbar | Verfügbarkeit |

**Lehrerhinweis:** Ein Vorfall kann mehrere Schutzziele gleichzeitig betreffen.

---

## Folie 17 – Wo liegt die Angriffsfläche?

Das bekannte Architekturdiagramm erneut verwenden:

```text
Mensch
  ↕
Browser ↔ Netzwerk ↔ Frontend ↔ Backend/API ↔ Datenhaltung
                                      ↕
                               Cloud/Infrastruktur
```

**Auftrag:** Markiert Stellen, an denen Fehler, Fehlkonfigurationen oder Angriffe möglich sind.

Mögliche Kategorien erst anschließend einblenden:

- Benutzer und Identitäten
- Software
- Schnittstellen
- Daten
- Konfiguration
- Abhängigkeiten/Lieferkette
- Infrastruktur

---

## Folie 18 – Wer greift IT-Systeme an?

Zunächst Motive sammeln:

- Geld
- Datendiebstahl
- Spionage
- Sabotage
- Protest
- Neugier
- Lernen/Forschung

Dann zentrale Unterscheidung:

> **Motivation oder gute Absicht ersetzt keine Berechtigung.**

---

## Folie 19 – White Hat, Black Hat, Grey Hat

**White Hat:** autorisiert; untersucht Systeme im vereinbarten Rahmen, um Sicherheit zu verbessern.

**Black Hat:** nicht autorisierte bzw. schädliche Angriffe.

**Grey Hat:** gebräuchlicher Zwischenbegriff; Handlungen können ohne ausreichende Autorisierung erfolgen, auch wenn keine klassische Schädigungsabsicht behauptet wird.

**Lehrerhinweis:** Farben sind vereinfachte populäre Kategorien. Rechtlich und ethisch entscheidend sind insbesondere Autorisierung, Handlung und Kontext.

---

## Folie 20 – Penetration Testing

**Leitfrage:**

> Darf man eine Anwendung absichtlich angreifen, um sie sicherer zu machen?

Antwort: Ja, **wenn der Test autorisiert ist und Umfang sowie Regeln eindeutig festgelegt sind.**

Perspektiven:

- Black Box: wenig Vorwissen
- Grey Box: teilweise Informationen/Zugänge
- White Box: umfangreiche interne Informationen, ggf. Architektur/Quellcode

**Lehrerhinweis:** Keine Anleitung zum Angriff fremder Systeme. Schwerpunkt sind Auftrag, Scope, Dokumentation, kontrolliertes Testen und verantwortliche Meldung von Befunden.

---

## Folie 21 – Security by Design

```text
Planung
  ↓
Entwicklung
  ↓
Test
  ↓
Bereitstellung
  ↓
Betrieb / Monitoring
  ↓
Updates / Verbesserung
  ↺
```

**Merksatz:**

> Sicherheit wird nicht am Ende „angeschraubt“, sondern begleitet den Lebenszyklus.

Beispiele: minimale Berechtigungen, sichere Voreinstellungen, Updates, Logging, Backup, Wiederherstellung, Tests.

---

## Folie 22 – Authentifizierung und Autorisierung

Zwei Fragen:

> **Wer bist du?** → Authentifizierung

> **Was darfst du?** → Autorisierung

Schülerfestival:

- Schüler darf eigene Workshopwahl sehen/ändern.
- Lehrkraft darf Teilnehmerlisten für betreute Workshops sehen.
- Administration besitzt weitergehende Rechte.

**Lehrerhinweis:** Rollenmodell als vereinfachtes Beispiel. Prinzip der geringsten notwendigen Rechte ansprechen.

---

## Folie 23 – Kontoschutz

Sinnvolle Maßnahmen:

- einzigartige Passwörter
- Passwortmanager
- Mehrfaktor-Authentisierung
- sichere Wiederherstellungsverfahren
- verdächtige Anmeldeversuche erkennen

**Lehrerhinweis:** Keine überholte Pauschalregel „Sonderzeichen = sicheres Passwort“. Länge, Einzigartigkeit, sichere Speicherung und MFA sind wichtiger als formale Zeichenvorgaben allein.

---

## Folie 24 – Phishing und Social Engineering

Nicht nur Rechtschreibfehler suchen.

Prüfkriterien:

- Passt die Nachricht zum erwarteten Kontext?
- Wird künstlicher Zeitdruck erzeugt?
- Stimmt Absender/Domain/Linkziel?
- Werden ungewöhnliche Daten oder Handlungen verlangt?
- Kann ich die Behauptung über einen unabhängigen Weg prüfen?

**Merksatz:**

> Gute Fälschungen können sprachlich perfekt sein.

---

## Folie 25 – Backup: Wogegen hilft es?

Beispiele:

- Hardwareausfall
- versehentliches Löschen
- beschädigte Daten
- bestimmte Schadensfälle durch Schadsoftware

**Aber:**

> Backup ≠ Zugriffsschutz ≠ Hochverfügbarkeit.

**Lehrerhinweis:** Getrennte Kopien und Wiederherstellungstests als Prinzip ansprechen.

---

## Folie 26 – Verschlüsselung: Welches Problem lösen wir?

Einstiegsfrage:

> Wie können Daten so übertragen oder gespeichert werden, dass Unbefugte sie nicht einfach lesen können?

Begriffe zunächst problemorientiert einführen, nicht als reine Definitionen.

---

## Folie 27 – Symmetrische Verschlüsselung

Ein gemeinsamer geheimer Schlüssel wird zum Ver- und Entschlüsseln verwendet.

**Leitproblem:**

> Wie erhalten beide Seiten den gemeinsamen Schlüssel sicher?

**Lehrerhinweis:** Reale Protokolle kombinieren häufig verschiedene kryptografische Verfahren.

---

## Folie 28 – Asymmetrische Kryptografie

Vereinfachtes Modell:

- öffentlicher Schlüssel
- privater Schlüssel

Öffentlicher und privater Schlüssel erfüllen unterschiedliche Funktionen.

**Lehrerhinweis:** Das oft verwendete „Briefkastenmodell“ ist nur eine Analogie. Nicht suggerieren, dass jede HTTPS-Nutzlast direkt asymmetrisch verschlüsselt wird.

---

## Folie 29 – Verschlüsselung ≠ automatisch Vertrauen

**Frage:**

> Eine Verbindung ist verschlüsselt. Weiß ich damit automatisch, wem die Gegenstelle gehört?

Nach Klick: **Nicht allein durch Verschlüsselung.**

Ausblick:

- Zertifikate
- Vertrauensketten
- Authentisierung der Gegenstelle

**Lehrerhinweis:** Nur konzeptionell behandeln; keine PKI-Detailtiefe erforderlich.

---

## Folie 30 – Datenschutz ist nicht dasselbe wie IT-Sicherheit

Beispiel:

> Unsere Datenbank ist hervorragend verschlüsselt. Wir speichern aber Geburtsdatum, Privatadresse und Telefonnummer aller Teilnehmenden, obwohl wir diese Daten für die Workshopwahl nicht benötigen.

**Frage:** Ist das Problem damit gelöst, dass die Daten sicher gespeichert sind?

Nach Klick: **Nein.**

Datenschutzfragen:

- Welche personenbezogenen Daten benötigen wir überhaupt?
- Für welchen Zweck?
- Wer darf sie nutzen?
- Wie lange werden sie benötigt?
- Wann werden sie gelöscht?

---

## Folie 31 – DSGVO und NIS2: unterschiedliche Perspektiven

Keine Paragraphen-Lernfolie.

**DSGVO:** Rahmen für den rechtmäßigen und verantwortungsvollen Umgang mit personenbezogenen Daten.

**NIS2:** europäischer Cybersicherheitsrahmen für bestimmte erfasste Einrichtungen und Unternehmen; Schwerpunkte sind u. a. Risikomanagement, Sicherheitsmaßnahmen, Vorfallbehandlung, Lieferketten und Meldepflichten.

**Lehrerhinweis:** NIS2 nicht als allgemeines Gesetz für jede private Schülerwebsite darstellen. Nationale Umsetzung und konkrete Betroffenheit können sich ändern; bei vertiefter Behandlung aktuelle offizielle Quellen verwenden.

---

## Folie 32 – Jetzt bekommt der Angreifer KI

Zunächst nur Titel zeigen und sammeln lassen.

Danach einzeln einblenden:

- überzeugendere und personalisierte Phishingtexte
- Übersetzung und Variation von Nachrichten
- Unterstützung bei Informationsauswertung
- Deepfakes und Social Engineering
- Unterstützung bei Analyse von Software und möglichen Schwachstellen
- Automatisierung von Arbeitsschritten

**Lehrerhinweis:** Fähigkeiten nicht dramatisieren und keine operativen Angriffsanleitungen geben. KI verändert Geschwindigkeit und Skalierung, ersetzt aber nicht automatisch Fachwissen.

---

## Folie 33 – Der Verteidiger hat ebenfalls KI

Mögliche Anwendungen einzeln einblenden:

- Logdaten zusammenfassen und analysieren
- Anomalien priorisieren
- verdächtige Nachrichten klassifizieren
- Code auf mögliche Schwachstellen prüfen
- Dokumentation unterstützen
- Security-Teams bei Analyse und Recherche unterstützen

**Kernaussage:**

> KI kann sowohl Angriff als auch Verteidigung unterstützen.

---

## Folie 34 – KI gegen KI?

| Angriff | Verteidigung |
|---|---|
| Phishing-Unterstützung | Erkennung und Analyse |
| automatisierte Informationsauswertung | Monitoring und Priorisierung |
| Unterstützung bei Codeanalyse | Code- und Konfigurationsprüfung |
| Deepfake/Social Engineering | Erkennungs- und Prüfverfahren |

**Diskussionsfrage:** Macht KI IT-Systeme automatisch unsicherer oder sicherer?

Erwartung: Es hängt von Einsatz, Qualität, Kontrolle, Kontext und Gegenmaßnahmen ab.

---

## Folie 35 – KI programmiert unsere Anwendung

Prompt-Beispiel:

> „Erstelle mir einen Login für meine Webanwendung.“

Frage:

> Der Code läuft. Ist er deshalb sicher?

Nach Klick:

> **Funktioniert ≠ sicher.**

Prüfpunkte:

- Verstehe ich den erzeugten Code?
- Sind Bibliotheken und Verfahren geeignet?
- Werden Passwörter korrekt behandelt?
- Sind Eingaben und Rechte geprüft?
- Gibt es Tests?
- Wurden vertrauliche Daten an einen externen KI-Dienst übermittelt?

---

## Folie 36 – Technisch möglich ≠ erlaubt ≠ sicher ≠ sinnvoll

Große Darstellung:

```text
technisch möglich
      ≠
     erlaubt
      ≠
     sicher
      ≠
     sinnvoll
```

**Lehrerhinweis:** Diese Folie verbindet Technik, Ethik, Recht und Verantwortung, ohne Rechtsberatung zu simulieren.

---

## Folie 37 – Abschlussfall: Schülerfestival

Architektur:

```text
Browser / Angular-Frontend
          ↕ HTTPS
        Backend / API
          ↕
       PostgreSQL

Betrieb in einer Cloudumgebung
```

Gespeicherte Daten beispielsweise:

- Name
- Klasse
- schulische E-Mail-Adresse
- Workshopwahl

**Gruppenauftrag:** Prüft das System aus drei Perspektiven.

### Architekturteam
- Ist die Aufteilung nachvollziehbar?
- Welche Komponente übernimmt welche Aufgabe?
- Welche Schnittstellen gibt es?

### Security-Team
- Welche Schutzziele sind wichtig?
- Welche Angriffsflächen gibt es?
- Welche Schutzmaßnahmen sind angemessen?

### Datenschutzteam
- Welche Daten werden wirklich benötigt?
- Wer benötigt Zugriff?
- Wie lange müssen Daten gespeichert werden?

Anschließend Ergebnisse zusammenführen: Entscheidungen einer Perspektive beeinflussen die anderen.

---

## Folie 38 – Sicherung

> Eine gute Webanwendung muss nicht nur funktionieren.

Nach Klick einzeln einblenden:

- verständlich aufgebaut sein
- zuverlässig funktionieren
- Daten angemessen schützen
- sicher betrieben und aktualisiert werden
- Datenschutz berücksichtigen
- Verantwortlichkeiten klären
- Änderungen und KI-Ergebnisse kritisch prüfen

---

## Folie 39 – Exit-Ticket

1. Erkläre den Unterschied zwischen **Client/Server** und **3-Tier** in eigenen Worten.
2. Warum ist **Cloud** nicht einfach eine vierte Schicht einer 3-Tier-Anwendung?
3. Nenne je ein Beispiel für **Vertraulichkeit, Integrität und Verfügbarkeit**.
4. Warum ist eine sichere Datenbank nicht automatisch datenschutzgerecht?
5. Nenne je eine Möglichkeit, wie KI Angreifer und Verteidiger unterstützen kann.

---

## Übergang zum komplexen Informatikprojekt

Die nächste Unterrichtssequenz greift diese Systemperspektive auf. Bei einem eigenen Informatikprojekt sollen die Schülerinnen und Schüler nicht nur ein funktionierendes Produkt erstellen, sondern Anforderungen, Architektur, Daten, Schnittstellen, Sicherheit, Tests, Dokumentation, Betrieb und ggf. KI-Einsatz nachvollziehbar planen und reflektieren.
