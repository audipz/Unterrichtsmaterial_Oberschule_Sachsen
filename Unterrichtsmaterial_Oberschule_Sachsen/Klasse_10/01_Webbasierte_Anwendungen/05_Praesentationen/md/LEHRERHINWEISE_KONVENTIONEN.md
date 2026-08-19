# Konventionen für Präsentation und Lehrerhinweise – Klasse 10 Webbasierte Anwendungen

Diese Regeln gelten für die Präsentation `01_05_Webbasierte_Anwendungen` und sollen bei späteren Überarbeitungen konsistent angewendet werden.

## Fachbegriffe und Abkürzungen

- Beim **ersten Auftreten** wird der Begriff ausgeschrieben und die Abkürzung anschließend in Klammern angegeben, z. B. **Server-Sent Events (SSE)**, **Hypertext Transfer Protocol (HTTP)**, **Application Programming Interface (API)** oder **Single Page Application (SPA)**.
- Danach darf die Abkürzung allein verwendet werden.
- Ein Begriff darf auf der Schülerfolie nicht vorausgesetzt werden, wenn er für das Verständnis der Folie notwendig ist.

## Lehrerhinweise

- Lehrerhinweise sollen keine berufspraktische Erfahrung mit Webentwicklung, Cloud oder IT-Security voraussetzen.
- **Jeder neu erklärte Fachbegriff erhält einen eigenen Absatz.**
- Der Begriff steht möglichst am Absatzanfang und wird hervorgehoben; danach folgen verständliche Erklärung und bei Bedarf ein Beispiel.
- Fachliche Vereinfachungen für Schülerinnen und Schüler werden im Lehrerhinweis präzisiert.
- Abkürzungen werden auch im Lehrerhinweis bei ihrer ersten Verwendung ausgeschrieben.

## 3-Tier und Schichten

Ein Tier bezeichnet eine Architektur- bzw. Bereitstellungsebene. Drei Tiers bedeuten nicht zwingend drei physische Rechner. Mehrere Tiers können auf einem System laufen; ein Tier kann auf mehrere Systeme verteilt sein.

**Presentation Tier:** Benutzeroberfläche sowie Ein- und Ausgabe.

**Application Tier:** Anwendungs- bzw. Geschäftslogik (Business Logic).

**Data Tier:** Datenhaltung und Datenzugriff, beispielsweise über eine Datenbank.

**Layer / Schicht:** Im Deutschen wird häufig von einer Drei-Schichten-Architektur gesprochen. Fachlich können Layer und Tier unterschieden werden: Layer beschreibt eher die logische Struktur, Tier eher die Bereitstellung. Im Schulkontext werden die Begriffe häufig vereinfachend ähnlich verwendet.

## Client/Server, Frontend/Backend und Rendering

„Frontend = Client“ und „Backend = Server“ ist nur ein grobes Einstiegsmodell.

**Serverseitiges Rendering:** Der Server erzeugt die anzuzeigende HTML-Seite ganz oder teilweise. Beispiele für Technologien sind PHP, JavaServer Pages (JSP), JavaServer Faces (JSF) oder Oracle Application Development Framework (Oracle ADF). Diese Technologien sind Lehrerwissen und kein Lernziel.

**Single Page Application (SPA):** Bei einer SPA, beispielsweise mit Angular, wird ein größerer Teil der Benutzeroberfläche durch JavaScript im Browser erzeugt und verändert. Das Backend stellt häufig Daten und Funktionen über Schnittstellen bereit.

**Abgrenzung:** Client/Server beschreibt Rollen in einer Kommunikation. Frontend/Backend beschreibt Bereiche bzw. Verantwortlichkeiten einer Anwendung. Die Begriffspaare sind nicht synonym.

## Kommunikation

Die Kommunikationsformen werden schrittweise eingeführt: **HTTP Request/Response → Polling → Server-Sent Events (SSE) → WebSocket → Vergleich**.

**Polling:** Kein eigenes Netzwerkprotokoll, sondern ein Kommunikationsmuster. Im Web häufig wiederholte HTTP-Request-/Response-Zyklen.

**Server-Sent Events (SSE):** Länger bestehende HTTP-Verbindung, über die der Server Ereignisse an den Client senden kann. Für die Gegenrichtung können normale HTTP-Anfragen verwendet werden.

**WebSocket:** Länger bestehende bidirektionale Verbindung. Client und Server können Nachrichten senden. Ein Subscription-Modell ist Anwendungslogik und nicht automatisch Bestandteil des WebSocket-Protokolls.

Bei mehreren Backend-Instanzen wird erklärt, dass eine bestehende WebSocket-Verbindung einer Instanz zugeordnet bleibt. Bei einem Reconnect kann eine andere Instanz gewählt werden. Notwendiger Verbindungszustand soll deshalb reproduzierbar wiederhergestellt werden können. Sticky Sessions sind eine mögliche Option, aber keine notwendige WebSocket-Lösung.
