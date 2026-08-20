# 4 Mobile Endgeräte und Bezahldienste

## Mobile Endgeräte als persönliche Informatiksysteme

Smartphones, Tablets und Wearables sind mobile Informatiksysteme. Sie enthalten Sensoren, Speicher, Funkverbindungen, Apps und persönliche Daten. Viele Funktionen sind mit Benutzerkonten und Cloud-Diensten verbunden.

Typische Daten auf mobilen Endgeräten:

| Datenart | Beispiele |
|---|---|
| persönliche Daten | Kontakte, Fotos, Nachrichten |
| technische Daten | Gerätekennung, Betriebssystem, App-Version |
| Standortdaten | GPS, WLAN-Orte, Bewegungsmuster |
| Zahlungsdaten | Kartenkennung, Transaktionsdaten |
| Gesundheitsdaten | Schritte, Puls, Schlafdaten |

Je persönlicher ein Gerät ist, desto wichtiger sind Sperre, Updates, Berechtigungen und Backups.

## Mobile Bezahldienste

Mobile Bezahldienste ermöglichen Zahlungen mit Smartphone oder Wearable. Dabei arbeiten mehrere Beteiligte zusammen. Nicht jeder Beteiligte kennt automatisch alle Daten.

```text
Käufergerät ──> Terminal ──> Zahlungsdienst ──> Bank/Kartenanbieter
       ↑             │              │
  Authentisierung    └── Händler erhält Zahlungsbestätigung
```

Beteiligte können sein:

- zahlende Person,
- Gerät und App,
- Händler,
- Terminal,
- Zahlungsdienst,
- Bank oder Kartenanbieter,
- Netzbetreiber oder Internetverbindung.

## Authentisierung und Autorisierung

Bei Zahlungen sind zwei Fragen entscheidend:

| Frage | Fachbegriff | Bedeutung |
|---|---|---|
| Wer bist du? | Authentisierung | Identität prüfen |
| Darfst du das? | Autorisierung | Handlung erlauben |

Die Authentisierung kann über Gerätecode, Fingerabdruck, Gesichtserkennung oder einen zweiten Faktor erfolgen. Die Autorisierung entscheidet anschließend, ob eine bestimmte Zahlung zugelassen wird.

> **Merke:** Authentisierung prüft die Identität. Autorisierung prüft die Berechtigung für eine Handlung.

## Datenfluss bei einer Zahlung

Bei einer mobilen Zahlung werden nicht einfach „Geldscheine digital übertragen“. Es werden Daten ausgetauscht, geprüft und bestätigt.

Vereinfacht:

1. Das Gerät startet den Zahlungsvorgang.
2. Das Terminal erhält Zahlungsinformationen.
3. Der Zahlungsdienst prüft die Anfrage.
4. Bank oder Kartenanbieter entscheiden über die Freigabe.
5. Der Händler erhält eine Bestätigung.
6. Die Transaktion wird dokumentiert.

Welche Daten genau übertragen werden, hängt vom Dienst und Verfahren ab. Wichtig ist das Grundprinzip: Eine Zahlung besteht aus mehreren Prüfschritten.

## Schutzmechanismen

Mobile Bezahldienste verwenden mehrere Schutzmechanismen. Dazu gehören Gerätesperre, verschlüsselte Kommunikation, Transaktionsgrenzen, Sperrmöglichkeiten und manchmal Ersatzkennungen statt echter Kartennummern.

| Risiko | Schutzidee |
|---|---|
| Gerät verloren | Fernsperre, Gerätesperre, Konto abmelden |
| unbefugte Zahlung | Authentisierung, Betragsgrenzen |
| Datenabgriff | verschlüsselte Übertragung |
| falsche App | Installation aus vertrauenswürdigen Quellen |
| unsichere Konten | starke Passwörter, Mehr-Faktor-Anmeldung |

Kein Schutz ist absolut. Deshalb sind mehrere Schutzschichten wichtig.

## Verlustszenario

Wenn ein Gerät mit Zahlungsfunktion verloren geht, sind schnelle Schritte wichtig:

- Gerät sperren oder orten, wenn möglich,
- Zahlungsdienst oder Konto sperren,
- Bank/Kartenanbieter informieren,
- Passwörter ändern, wenn Kontozugriff möglich war,
- Transaktionen prüfen,
- Verlust dokumentieren.

Ein sicher eingerichtetes Gerät reduziert das Risiko deutlich. Ohne Sperre und ohne Kontoschutz kann ein Verlust schwerwiegender sein.

## Berechtigungen von Apps

Apps fordern Berechtigungen an, zum Beispiel für Kamera, Kontakte, Standort oder Benachrichtigungen. Nicht jede Berechtigung ist für jede Funktion nötig.

| Berechtigung | sinnvoll bei | kritisch, wenn |
|---|---|---|
| Kamera | QR-Code scannen | dauerhaft ohne Grund |
| Standort | Navigation | Hintergrundzugriff unnötig |
| Kontakte | Messenger | App braucht nur Anzeige |
| Speicher/Fotos | Bildauswahl | Zugriff auf alles statt Auswahl |
| Mikrofon | Sprachaufnahme | keine erkennbare Funktion |

> **Merke:** Berechtigungen sollten zum Zweck einer App passen und regelmäßig überprüft werden.

## Begriffe zum Nachschlagen

**mobiles Endgerät:** tragbares Informatiksystem wie Smartphone, Tablet oder Smartwatch.

**mobile Zahlung:** Zahlung mit einem mobilen Gerät oder Wearable.

**Authentisierung:** Nachweis der Identität.

**Autorisierung:** Erlaubnis für eine bestimmte Aktion.

**Transaktion:** einzelner Zahlungsvorgang oder Datenvorgang.

**Berechtigung:** Erlaubnis einer App, auf eine Funktion oder Datenart zuzugreifen.

→ Vorwissen: Klasse 9, **Mobile Endgeräte, Daten und Rechte**.
