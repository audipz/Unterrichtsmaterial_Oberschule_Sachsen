# 3 Sicherheit in der Informationsverarbeitung

## Sicherheit bedeutet mehr als Geheimhaltung

Informationssicherheit beschreibt den Schutz von Daten und Systemen. Drei Schutzziele sind besonders wichtig:

| Schutzziel | Bedeutung | Beispiel |
|---|---|---|
| Vertraulichkeit | Nur Berechtigte dürfen Daten sehen. | private Nachricht |
| Integrität | Daten dürfen nicht unbemerkt verändert werden. | unveränderte Datei |
| Verfügbarkeit | Daten und Dienste sollen nutzbar bleiben. | erreichbarer Cloud-Speicher |

Diese Ziele können miteinander in Spannung stehen. Eine sehr sichere Lösung kann unbequem sein. Eine sehr bequeme Lösung kann unsicher sein. Gute Sicherheit sucht einen sinnvollen Ausgleich.

## Digitaler Fußabdruck

Der **digitale Fußabdruck** besteht aus Spuren, die bei der Nutzung digitaler Dienste entstehen. Manche Spuren werden bewusst erzeugt, andere nebenbei.

| Spur | Beispiel |
|---|---|
| aktive Angabe | Profilbild, Kommentar, hochgeladenes Foto |
| technische Spur | IP-Adresse, Geräteinformationen |
| Nutzungsverhalten | Suchbegriffe, Klicks, Verweildauer |
| Standortbezug | Ortsfreigabe, WLAN, GPS |
| soziale Spur | Kontakte, Markierungen, Gruppen |

Aus einzelnen Spuren können Muster entstehen. Solche Muster können für Empfehlungen, Werbung, Sicherheitsprüfungen oder Bewertungen genutzt werden.

> **Merke:** Auch harmlose Einzeldaten können in Kombination viel über eine Person verraten.

## Tracking und Datensammlungen

**Tracking** bedeutet, Nutzung über eine oder mehrere Seiten, Apps oder Zeitpunkte hinweg wiederzuerkennen. Dafür werden unterschiedliche Techniken genutzt, zum Beispiel Cookies, Anmeldekonten, Gerätekennungen oder eingebettete Inhalte.

Tracking kann nützliche Funktionen ermöglichen, etwa Warenkörbe, Spracheinstellungen oder Betrugserkennung. Es kann aber auch dazu führen, dass Verhalten detailliert ausgewertet wird.

Datensparsame Einstellungen helfen:

- nur notwendige Cookies zulassen,
- Berechtigungen regelmäßig prüfen,
- nicht jedes Konto mit jedem Dienst verbinden,
- öffentliche Profile sparsam ausfüllen,
- alte Konten löschen oder sichern,
- getrennte Passwörter verwenden.

## Passwörter und Mehr-Faktor-Anmeldung

Ein gutes Passwort ist lang, nicht leicht zu erraten und wird nicht mehrfach verwendet. Noch besser sind Passphrasen, also mehrere zufällig wirkende Wörter mit zusätzlichen Zeichen.

Schwache Beispiele:

```text
passwort123
Max2009
Sommerferien
```

Bessere Idee:

```text
Lampe-Wolke-47-Fahrrad!
```

Eine **Mehr-Faktor-Anmeldung** ergänzt das Passwort durch einen weiteren Faktor, zum Beispiel eine Bestätigung auf einem anderen Gerät oder einen Sicherheitsschlüssel. Dadurch reicht ein gestohlenes Passwort allein nicht aus.

## Backups

Ein **Backup** ist eine Sicherungskopie wichtiger Daten. Es schützt besonders vor Verlust durch Defekt, Diebstahl, versehentliches Löschen oder Schadsoftware.

| Backup-Frage | Gute Antwort |
|---|---|
| Was wird gesichert? | wichtige Dokumente, Bilder, Projektdateien |
| Wohin? | zweiter Speicherort oder seriöser Cloud-Dienst |
| Wie oft? | regelmäßig und nach wichtigen Änderungen |
| Wird geprüft? | Wiederherstellung gelegentlich testen |

Ein Backup ist nur dann hilfreich, wenn es im Ernstfall wiederhergestellt werden kann.

## Cyberkriminalität

Cyberkriminalität umfasst Straftaten mit digitalen Mitteln oder gegen digitale Systeme. Beispiele sind Phishing, Identitätsdiebstahl, Erpressungssoftware, Betrug in Online-Shops oder unbefugter Zugriff auf Konten.

**Phishing** versucht, Menschen zur Preisgabe von Daten zu bringen. Typische Warnzeichen sind ungewöhnlicher Druck, merkwürdige Absender, unerwartete Anhänge, verkürzte Links oder sprachliche Auffälligkeiten.

> **Merke:** Sicherheit hängt nicht nur von Technik ab. Auch Aufmerksamkeit, Gewohnheiten und klare Regeln sind Teil des Schutzes.

## Asymmetrische Verschlüsselung

Bei symmetrischer Verschlüsselung verwenden beide Seiten denselben geheimen Schlüssel. Bei **asymmetrischer Verschlüsselung** gibt es ein Schlüsselpaar:

- einen öffentlichen Schlüssel,
- einen privaten Schlüssel.

Der öffentliche Schlüssel darf weitergegeben werden. Der private Schlüssel muss geheim bleiben. Vereinfacht kann man sich vorstellen: Was mit dem öffentlichen Schlüssel verschlüsselt wird, kann nur mit dem passenden privaten Schlüssel entschlüsselt werden.

Dieses Prinzip hilft zum Beispiel bei sicheren Verbindungen und digitalen Signaturen. In der Praxis arbeiten moderne Verfahren oft mit einer Kombination aus asymmetrischer und symmetrischer Verschlüsselung, weil das effizienter ist.

## Begriffe zum Nachschlagen

**Informationssicherheit:** Schutz von Daten und Systemen.

**Vertraulichkeit:** Schutz vor unberechtigtem Lesen.

**Integrität:** Schutz vor unbemerkter Veränderung.

**Verfügbarkeit:** Nutzbarkeit von Daten und Diensten.

**Tracking:** Wiedererkennen und Auswerten von Nutzung.

**Phishing:** Täuschungsversuch zum Erlangen von Daten oder Zugriff.

**asymmetrische Verschlüsselung:** Verfahren mit öffentlichem und privatem Schlüssel.

→ Querverweis: Kapitel 1, **Webbasierte Anwendungen**.
