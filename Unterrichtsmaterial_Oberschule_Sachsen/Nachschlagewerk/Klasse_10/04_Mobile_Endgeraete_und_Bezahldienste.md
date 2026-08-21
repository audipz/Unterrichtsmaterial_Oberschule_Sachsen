# 4 Digitale Bezahlsysteme und Transaktionen

## Bezahlen ist Informationsverarbeitung

Bei einer digitalen Zahlung werden nicht „digitale Geldscheine“ vom Smartphone zum Händler übertragen. Stattdessen tauschen mehrere Informatiksysteme Nachrichten aus: Wer möchte welchen Betrag an wen zahlen? Ist das Zahlungsmittel gültig? Ist die Zahlung erlaubt? Wie wird sie bestätigt und später abgerechnet?

Digitale Zahlungssysteme sind deshalb ein gutes Beispiel für das Zusammenspiel von **Client und Server, Netzwerken, Datenbanken, Authentisierung, Autorisierung, Kryptografie und Transaktionen**.

> **Merke:** Eine digitale Zahlung ist ein verteilter, abgesicherter Datenverarbeitungsprozess.

## Beteiligte Systeme

Je nach Zahlungsverfahren unterscheiden sich die Beteiligten. Bei einer Kartenzahlung können beispielsweise beteiligt sein:

- zahlende Person,
- Karte, Smartphone oder Wearable,
- Händler und Kassensystem,
- Zahlungsterminal,
- Zahlungsdienstleister/Acquirer,
- Kartennetzwerk,
- kartenausgebende Bank/Issuer.

Nicht jede Zahlung läuft technisch genau über diese Kette. Sie zeigt aber, warum eine Zahlung mehr ist als die Kommunikation zwischen zwei Geräten.

## Autorisierung, Clearing und Settlement

Eine Zahlung kann vereinfacht in mehrere Phasen zerlegt werden.

### Autorisierung

Bei der **Autorisierung** wird geprüft, ob die konkrete Zahlung zugelassen wird. Dazu können Gültigkeit des Zahlungsmittels, verfügbare Mittel, Betrugsindikatoren und erforderliche Authentisierung gehören.

Eine erfolgreiche Autorisierung bedeutet noch nicht zwingend, dass das Geld in diesem Moment endgültig beim Händler angekommen ist.

### Clearing

Beim **Clearing** werden Zahlungsinformationen zwischen beteiligten Stellen abgeglichen und für die Abrechnung vorbereitet.

### Settlement

Beim **Settlement** erfolgt die finanzielle Verrechnung beziehungsweise endgültige Übertragung der entsprechenden Beträge zwischen beteiligten Finanzinstituten.

> **Merke:** **Autorisierung = darf die Zahlung stattfinden? Clearing/Settlement = wie wird sie anschließend abgerechnet und verrechnet?**

## Kontaktlose Zahlung und NFC

Viele Karten, Smartphones und Wearables können für kontaktlose Zahlungen **NFC (Near Field Communication)** verwenden. NFC ist eine Funktechnik für sehr kurze Entfernungen.

NFC allein ist jedoch **kein Bezahldienst**. Es stellt lediglich eine Kommunikationsmöglichkeit zwischen Geräten bereit. Die eigentliche Zahlung benötigt darüber hinaus Protokolle, Zahlungsdaten, Prüfungen und Backend-Systeme.

```text
Smartphone/Karte
      │
      │ NFC
      ▼
Terminal
      │
      │ Zahlungsnetzwerk
      ▼
weitere Zahlungs- und Banksysteme
```

Die kurze Funkreichweite ist eine nützliche Eigenschaft, ersetzt aber keine weiteren Sicherheitsmaßnahmen.

## Karte, Smartphone und Wallet

Eine **digitale Wallet** ist eine Software beziehungsweise ein Dienst zur Verwaltung digitaler Zahlungsinformationen und gegebenenfalls weiterer digitaler Nachweise.

Beim Smartphone kommen mehrere Sicherheitskomponenten zusammen:

- Gerätesperre,
- Betriebssystem und App-Sandbox,
- biometrische oder andere lokale Authentisierung,
- kryptografisch geschützte Schlüssel beziehungsweise Sicherheitsbereiche,
- Kommunikation mit Zahlungsdiensten.

Die Smartphone-Grundlagen selbst wurden in früheren Klassen behandelt. In Klasse 10 steht hier die **Zahlung als verteilte Transaktion** im Mittelpunkt.

## Authentisierung und Autorisierung unterscheiden

**Authentisierung** prüft einen Nachweis für eine behauptete Identität oder Berechtigung. **Autorisierung** entscheidet, ob eine bestimmte Handlung erlaubt wird.

Bei einer Zahlung können mehrere Autorisierungen vorkommen: Das Gerät kann lokal prüfen, ob der Benutzer die Zahlung freigibt; zusätzlich entscheidet das Zahlungssystem, ob die Transaktion angenommen wird.

→ Siehe Kapitel 3, **Sicherheit in der Informationsverarbeitung**.

## Tokenisierung

Bei manchen Zahlungssystemen wird nicht bei jeder Transaktion die eigentliche Kartennummer verwendet. Stattdessen kann ein **Token** als Ersatzkennung eingesetzt werden.

Ein Token kann so gestaltet sein, dass er nur in einem bestimmten Zusammenhang sinnvoll nutzbar ist. Dadurch muss die ursprüngliche Zahlungskennung nicht überall weitergegeben werden.

> **Merke:** Tokenisierung bedeutet nicht einfach „Verschlüsselung“. Ein Token **ersetzt** einen sensiblen Wert durch eine andere Kennung; Verschlüsselung transformiert Daten mithilfe eines kryptografischen Schlüssels.

## Kryptografie bei Zahlungen

Digitale Zahlungssysteme benötigen mehrere Schutzziele gleichzeitig:

| Schutzziel | Bedeutung bei einer Zahlung |
|---|---|
| Vertraulichkeit | sensible Daten sollen nicht von Unbefugten gelesen werden |
| Integrität | Betrag und Empfänger dürfen nicht unbemerkt verändert werden |
| Authentizität | Kommunikationspartner und Nachrichtenherkunft müssen überprüfbar sein |
| Verfügbarkeit | Zahlungssysteme müssen zuverlässig erreichbar sein |
| Nachweisbarkeit | Transaktionen müssen für Abrechnung und Streitfälle nachvollziehbar sein |

Dafür können je nach System Verschlüsselung, kryptografische Prüfcodes, Zertifikate, Signaturen beziehungsweise Authentisierungscodes und geschützte Schlüssel eingesetzt werden.

## Was ist eine Transaktion?

In der Informatik ist eine **Transaktion** eine zusammengehörige Folge von Operationen, die als logische Einheit betrachtet wird. Das ist nicht nur bei Geldzahlungen wichtig, sondern auch bei Datenbanken.

Beispiel: Eine Überweisung darf nicht so enden, dass der Betrag beim einen Konto abgezogen wurde, beim anderen aber wegen eines Fehlers nie ankommt.

Bei Datenbanktransaktionen werden häufig die **ACID-Eigenschaften** genannt:

- **Atomicity (Atomarität):** ganz oder gar nicht,
- **Consistency (Konsistenz):** definierte Regeln bleiben erfüllt,
- **Isolation:** parallele Transaktionen beeinflussen sich nicht unkontrolliert,
- **Durability (Dauerhaftigkeit):** bestätigte Änderungen bleiben gespeichert.

Für Zahlungssysteme ist die reale Gesamtarchitektur komplexer als eine einzelne Datenbanktransaktion. Die ACID-Idee erklärt aber sehr gut, warum zusammengehörige Änderungen kontrolliert verarbeitet werden müssen.

→ Vorwissen: Klasse 9, **Datenbanken**.

## Beispiel: Zahlung im Geschäft

Ein stark vereinfachter Ablauf kann so aussehen:

1. Das Kassensystem übergibt Betrag und Zahlungsanforderung an das Terminal.
2. Karte oder mobiles Gerät kommuniziert mit dem Terminal.
3. Benötigte Zahlungsdaten beziehungsweise Token und kryptografische Informationen werden ausgetauscht.
4. Die Anfrage gelangt über beteiligte Zahlungsdienste zur zuständigen Stelle.
5. Risiken und Berechtigungen werden geprüft.
6. Die Zahlung wird genehmigt oder abgelehnt.
7. Das Ergebnis gelangt zum Terminal und Kassensystem zurück.
8. Später folgen Abrechnung und Verrechnung.

Der konkrete Ablauf hängt von Kartenart, Wallet, Land, Zahlungsnetz und Betriebsart ab.

## Onlinezahlung

Bei einer Onlinezahlung fehlt das physische Kartenterminal. Stattdessen kommunizieren Browser oder App mit Händler- und Zahlungsdiensten.

Dabei ist besonders wichtig, dass der Benutzer erkennen kann, **welchem Händler und Zahlungsdienst er Daten übermittelt**. Phishing-Seiten können echte Bezahloberflächen imitieren.

Zusätzliche Authentisierungsschritte können erforderlich sein. Dabei sollte ein Benutzer niemals einen Freigabecode bestätigen, ohne Betrag und Empfänger zu prüfen.

## QR-Code-Zahlungen

Bei manchen Verfahren enthält ein QR-Code Zahlungsinformationen oder einen Verweis auf einen Zahlungsvorgang. Ein QR-Code ist jedoch nur eine maschinenlesbare Darstellung von Daten.

Ein manipuliertes oder überklebtes QR-Schild kann deshalb auf einen falschen Empfänger oder eine betrügerische Webseite führen.

> **Merke:** Ein QR-Code ist nicht automatisch vertrauenswürdig, nur weil eine Kamera ihn problemlos lesen kann.

## Offline und online

Nicht jede kontaktlose Zahlung benötigt in jedem Moment denselben Online-Ablauf. Systeme können abhängig von Verfahren, Betrag und Risikoregeln bestimmte Prüfungen lokal beziehungsweise zeitversetzt durchführen.

Dadurch entsteht ein Zielkonflikt zwischen **Verfügbarkeit, Geschwindigkeit und Sicherheit**. Ein System muss festlegen, welche Risiken bei fehlender Verbindung akzeptabel sind.

## Betrugserkennung

Zahlungsdienste können Transaktionen automatisiert auf Auffälligkeiten untersuchen. Hinweise können beispielsweise ungewöhnlicher Ort, ungewöhnlicher Betrag, sehr viele Versuche oder stark abweichendes Nutzungsverhalten sein.

Solche Systeme liefern Risikobewertungen, keine perfekte Wahrheit. Es kann zu **False Positives** kommen: Eine legitime Zahlung wird fälschlich als verdächtig eingestuft. Ebenso können betrügerische Vorgänge unerkannt bleiben.

Damit besteht ein ähnliches Problem wie bei anderen Klassifikationssystemen: Sicherheit und Benutzerfreundlichkeit müssen gegeneinander abgewogen werden.

## Datenschutz bei Zahlungsdaten

Zahlungsdaten können viel über Menschen verraten: Händler, Zeitpunkt, Betrag und gegebenenfalls Ort ergeben zusammen ein detailliertes Nutzungsprofil.

Deshalb sind Fragen wichtig wie:

- Welche Daten sind für die Zahlung notwendig?
- Wer erhält welche Informationen?
- Wie lange werden sie gespeichert?
- Welche Daten werden für Betrugserkennung oder andere Zwecke ausgewertet?
- Welche gesetzlichen Aufbewahrungspflichten bestehen?

**Datensparsamkeit** bedeutet, nur die für den jeweiligen Zweck erforderlichen Daten zu verarbeiten, soweit dies technisch und rechtlich möglich ist.

## Typische Angriffs- und Fehlerszenarien

| Szenario | mögliche Schutzidee |
|---|---|
| gestohlenes Smartphone | Gerätesperre, Wallet sperren, Fernverwaltung |
| Phishing-Zahlungsseite | Domain/Empfänger prüfen, Browserwarnungen beachten |
| gestohlene Zugangsdaten | MFA, ungewöhnliche Anmeldung erkennen |
| manipuliertes Terminal/QR-Code | Empfänger und Betrag prüfen |
| abgefangene Netzwerkdaten | kryptografisch geschützte Verbindung |
| veränderte Transaktionsdaten | Integritäts- und Authentizitätsschutz |
| Systemausfall | Redundanz und Wiederanlaufverfahren |

## Verlust eines Zahlungsgeräts

Bei Verlust eines Smartphones oder Wearables mit Zahlungsfunktion sind je nach Dienst sinnvoll:

- Gerät über Hersteller-/Kontofunktionen sperren,
- Wallet beziehungsweise Zahlungsmittel deaktivieren,
- Bank oder Zahlungsanbieter informieren,
- Kontobewegungen kontrollieren,
- bei möglichem Kontozugriff Zugangsdaten absichern.

Ein gesperrtes und verschlüsseltes Gerät reduziert Risiken, macht eine Reaktion auf Verlust aber nicht überflüssig.

## Bargeld und digitale Zahlung vergleichen

Beide Verfahren besitzen unterschiedliche Eigenschaften.

| Eigenschaft | Bargeld | digitale Zahlung |
|---|---|---|
| technische Infrastruktur | bei Übergabe gering | mehrere IT-Systeme beteiligt |
| Nachvollziehbarkeit | typischerweise geringer | Transaktionsdaten entstehen |
| Zahlung bei Netzausfall | häufig möglich | abhängig vom Verfahren |
| Verlust | Bargeld meist direkt verloren | Zahlungsmittel kann oft gesperrt werden |
| Automatisierbarkeit | begrenzt | sehr hoch |

Die Tabelle ist keine Wertung. Welches Verfahren sinnvoll ist, hängt vom Anwendungsfall ab.

## Begriffe zum Nachschlagen

**Acquirer:** Zahlungsdienst beziehungsweise Finanzinstitut auf Händlerseite, das Kartenzahlungen verarbeitet beziehungsweise in das Zahlungsnetz einbindet.

**Autorisierung:** Prüfung und Freigabe einer konkreten Handlung beziehungsweise Zahlung.

**Clearing:** Austausch und Abgleich von Transaktionsinformationen zur Vorbereitung der finanziellen Verrechnung.

**Digitale Wallet:** Software/Dienst zur Verwaltung digitaler Zahlungsmittel oder Nachweise.

**Issuer:** Stelle beziehungsweise Bank, die ein Zahlungsmittel wie eine Zahlungskarte herausgibt.

**NFC:** Funktechnik für Kommunikation über sehr kurze Entfernung.

**Settlement:** finanzielle Verrechnung und Übertragung von Beträgen zwischen beteiligten Stellen.

**Tokenisierung:** Ersetzen eines sensiblen Wertes durch eine Ersatzkennung.

**Transaktion:** zusammengehöriger Verarbeitungsvorgang, der als logische Einheit behandelt wird.

→ Vorwissen: Klasse 9, **Mobile Endgeräte** und **Datenbanken**.  
→ Siehe Kapitel 1, **Webbasierte Anwendungen**, und Kapitel 3, **Sicherheit in der Informationsverarbeitung**.
