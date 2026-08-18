# Zusatzpräsentation – Kryptografie

> Optionale Vertiefung. Kann unabhängig von der Pflichtpräsentation „Netzwerke in der Informatik“ eingesetzt werden.

## Folie 1 – Leitfrage
Wie können zwei Personen über ein Netzwerk kommunizieren, ohne dass Mitlesende den Inhalt verstehen?

## Folie 2 – Was ist Kryptografie?
- Kryptografie schützt Informationen mit mathematischen/regelgeleiteten Verfahren.
- Klartext → Verschlüsselung → Geheimtext
- Geheimtext → Entschlüsselung → Klartext
- ein Schlüssel steuert das Verfahren

## Folie 3 – Caesar-Verschlüsselung
Historisches, bewusst unsicheres Modell:
- Schlüssel +3
- `HALLO → KDOOR`
- dient nur zum Verständnis der Grundidee

## Folie 4 – Symmetrische Verschlüsselung
- Sender und Empfänger verwenden denselben geheimen Schlüssel.
- sehr effizient und schnell
- deshalb gut für laufenden Datenverkehr
- Problem: gemeinsamer Schlüssel muss sicher vereinbart werden

## Folie 5 – Asymmetrische Kryptografie
- zwei zusammengehörende Schlüssel
- öffentlicher Schlüssel darf bekannt sein
- privater Schlüssel bleibt geheim
- Grundlage für Authentisierung, Signaturen und sicheren Schlüsselaustausch

## Folie 6 – Symmetrisch vs. asymmetrisch
| Merkmal | Symmetrisch | Asymmetrisch |
|---|---|---|
| Schlüssel | gemeinsames Geheimnis | öffentlich + privat |
| Geschwindigkeit | sehr effizient | rechenaufwendiger |
| typische Rolle | Datenverkehr | Authentisierung/Schlüsselaushandlung |

## Folie 7 – RSA: Grundidee
RSA ist ein asymmetrisches Verfahren. Für den Unterricht werden absichtlich kleine und damit unsichere Zahlen verwendet.

Beispiel:
- `p = 5`, `q = 11`
- `n = p · q = 55`
- `φ(n) = (p−1)(q−1) = 40`

## Folie 8 – RSA: Schlüssel berechnen
- wähle `e = 3`
- bestimme `d` so, dass `e · d ≡ 1 (mod 40)`
- `3 · 27 = 81 ≡ 1 (mod 40)` → `d = 27`
- öffentlich: `(n=55, e=3)`
- privat: `d=27`

## Folie 9 – RSA: Verschlüsseln
Nachricht als Zahl `m = 7`:
- `c = m^e mod n`
- `c = 7³ mod 55`
- `c = 343 mod 55 = 13`

Geheimtext: `13`

## Folie 10 – RSA: Entschlüsseln
- `m = c^d mod n`
- `m = 13²⁷ mod 55`
- Ergebnis: `m = 7`

Lehrerhinweis: Die große Potenz nicht vollständig ausmultiplizieren; modulare Potenzierung bzw. Rechner verwenden.

## Folie 11 – Hashwerte und digitale Signaturen
- Hashwert = digitaler Fingerabdruck von Daten
- digitale Signatur hilft Herkunft und Unverändertheit zu prüfen
- Signieren ist nicht dasselbe wie Verschlüsseln

## Folie 12 – HTTPS und TLS
- **HTTPS = Hypertext Transfer Protocol Secure**
- **TLS = Transport Layer Security** schützt die Verbindung
- **SSL = Secure Sockets Layer** ist die ältere Technik/Bezeichnung; heute wird TLS verwendet

## Folie 13 – Warum HTTPS beide Welten kombiniert
1. Public-Key-/asymmetrische Kryptografie für Authentisierung und sichere Aushandlung von Schlüsselmaterial
2. danach gemeinsame Sitzungsschlüssel
3. laufender Datenverkehr wird symmetrisch geschützt

Merksatz:
**Asymmetrisch für den sicheren Aufbau – symmetrisch für den schnellen Datenverkehr.**

Lehrerhinweis: Modernes TLS 1.3 nutzt typischerweise (EC)DHE für die Schlüsselaushandlung und Public-Key-Signaturen für Authentisierung; die Nutzdaten werden anschließend symmetrisch geschützt. Nicht vereinfachend behaupten, RSA verschlüssele den gesamten HTTPS-Datenstrom.

## Folie 14 – Sicherung
Schüler können erklären:
- Klartext, Geheimtext, Schlüssel
- symmetrisch vs. asymmetrisch
- RSA-Grundidee an kleinen Zahlen
- warum HTTPS/TLS für den Datenverkehr symmetrische Verfahren verwendet

## Quellen
- Bundesamt für Sicherheit in der Informationstechnik: https://www.bsi.bund.de
- IETF RFC 8446 – TLS 1.3: https://www.rfc-editor.org/rfc/rfc8446
- Sächsischer Lehrplan Oberschule Informatik, Klassenstufe 8: https://www.schulportal.sachsen.de/lplandb/lehrplan/514
- Grafiken und vereinfachte Unterrichtsbeispiele: eigene didaktische Darstellung.
