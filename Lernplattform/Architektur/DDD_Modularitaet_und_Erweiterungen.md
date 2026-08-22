# DDD, Modularität und Erweiterungsarchitektur

## Ziel

Die Lernplattform wird als **DDD-basierter modularer Monolith** entwickelt. Die fachlichen Grenzen werden so gestaltet, dass einzelne Bereiche später bei Bedarf als eigene Services deployt werden können, ohne die Anwendung von Beginn an mit der Komplexität verteilter Systeme zu belasten.

Der Schwerpunkt liegt auf:

- klaren Bounded Contexts,
- kleinen öffentlichen APIs zwischen Modulen,
- Java 25 und JPMS-fähigen Modulgrenzen,
- funktionalen Spring-Endpunkten,
- AOT-/Native-freundlichem Code,
- stateless Runtime-Pods für schnelles Skalieren,
- externen Erweiterungen über Services/Sidecars statt Runtime-JAR-Plugins,
- einer Angular-Shell mit später ladbaren Microfrontends.

## Bounded Contexts

Der fachliche Zielzuschnitt ist:

```text
Identity & Access
School Administration
Learning
Content
Notification
Publishing
```

### Identity & Access

Verantwortet globale Accounts, Authentifizierung, schulbezogene Memberships und schulbezogene Rechte.

### School Administration

Verantwortet Schulen, Klassen, Lehrerzuordnungen, Schülerzuordnungen, Klassenwechsel und organisatorische Schulwechsel.

### Learning

Verantwortet Lernperioden, Bearbeitungsfortschritt, Vollständigkeit und den kontrollierten Neustart einer Lernperiode.

### Content

Verantwortet veröffentlichte Themenbausteine, Sprachvarianten, Zielgruppen, Manifeste und die Runtime-Bereitstellung der Lerninhalte.

### Notification

Verantwortet Benachrichtigungen, beispielsweise wenn ein nicht zuständiger Lehrer relevante Änderungen an einer Klasse vornimmt.

### Publishing

Verantwortet die Validierung und Übernahme redaktioneller Markdown-/Manifest-Quellen in veröffentlichte Content-Releases. Publishing ist nicht Bestandteil des normalen Request-Pfades der Lernplattform.

## Modulregel

Ein Bounded Context darf nicht direkt auf interne Implementierungen oder Tabellen eines anderen Contexts zugreifen.

Kommunikation erfolgt über:

1. öffentliche Java-APIs/Ports für synchrone Abfragen,
2. Domain-/Application-Events für fachliche Ereignisse,
3. später optional HTTP/gRPC/Messaging, wenn ein Context als eigener Service extrahiert wird.

Datenbanktabellen erhalten fachliches Ownership. Ein Modul schreibt ausschließlich in seine eigenen Tabellen.

## Java 25 und JPMS

JPMS soll die fachlichen Modulgrenzen zusätzlich technisch absichern.

Zielbild:

```text
de.schule.lernplattform.identity
 de.schule.lernplattform.school
 de.schule.lernplattform.learning
 de.schule.lernplattform.content
 de.schule.lernplattform.notification
 de.schule.lernplattform.publishing
```

Jedes Modul exportiert nur seine öffentliche API. Implementierungspakete werden nicht exportiert.

Beispiel:

```java
module de.schule.lernplattform.school {
    exports de.schule.informatik.lernplattform.school.api;

    requires de.schule.lernplattform.identity;
}
```

Spring-relevante Reflection wird nur gezielt über `opens ... to ...` erlaubt. Es soll kein pauschales `open module` verwendet werden, sofern dies nicht zwingend erforderlich ist.

JPMS wird schrittweise eingeführt. Bestehende Spring-/Persistenzmodule werden zuerst fachlich bereinigt; `module-info.java` wird anschließend modulweise ergänzt und über den Maven-Build verifiziert.

## Spring-Komposition

Spring bleibt für Runtime-Komposition und Dependency Injection zuständig.

Technische Varianten werden über stabile Ports und bedingte Adapter bereitgestellt, beispielsweise:

```text
ContentRepository
├── PostgreSQL
└── InMemory/Test

NotificationPort
├── Mail
├── Outbox
└── NoOp
```

Verwendet werden können unter anderem:

- `@ConditionalOnProperty`,
- `@ConditionalOnBean`,
- `@ConditionalOnMissingBean`,
- Profile für Entwicklungs-/Betriebsvarianten.

Damit bleibt die Anwendung konfigurierbar, ohne Runtime-Plugins laden zu müssen.

## Keine Runtime-JAR-Plugins

Der Backend-Core lädt keine unbekannten JARs während des Betriebs nach.

Gründe:

- schnellerer und deterministischer Start,
- bessere AOT-/Native-Eignung,
- weniger Classloader-Komplexität,
- kleinere Angriffsfläche,
- reproduzierbare Deployments,
- einfachere Skalierung von 0..n.

Kleine Varianten werden als bekannte Adapter beim Build bereitgestellt und zur Startzeit per Konfiguration gewählt.

Größere Erweiterungen werden als eigener Service oder Sidecar deployt und über eine definierte Schnittstelle angebunden.

## Funktionale HTTP-Endpunkte

Neue HTTP-Endpunkte werden bevorzugt als Spring MVC Functional Endpoints implementiert:

```text
RouterFunction
    ↓
HandlerFunction
    ↓
Application Service
    ↓
Domain
```

Die Anwendung bleibt auf dem Servlet-/JDBC-Modell. Funktionale Endpunkte bedeuten ausdrücklich nicht, dass die gesamte Anwendung auf WebFlux/Reactor umgestellt wird.

Vorteile:

- explizite Routen,
- wenig Annotation-Magie,
- klare Trennung zwischen Routing und Handler,
- geringe Reflection-Abhängigkeit,
- gute Testbarkeit.

## Skalierung und schneller Start

Runtime-Pods sollen stateless sein.

Nicht Bestandteil des normalen Pod-Starts sind:

- Flyway-Migrationen,
- Content-Import,
- Markdown-Verarbeitung,
- Repository-Zugriffe.

Diese Aufgaben werden über separate Deployment-/Publishing-Jobs ausgeführt.

Der Runtime-Pod benötigt im Wesentlichen:

```text
Spring Context
OAuth2/OIDC
PostgreSQL-Verbindung
Runtime-Contentzugriff
HTTP-Router
```

Für Produktion werden JVM/jlink/AOT und GraalVM Native benchmarked. Architektur und Code sollen beide Varianten ermöglichen.

## Angular-Shell und Microfrontends

Das Frontend erhält eine zentrale Shell für technische Querschnittsfunktionen:

```text
Shell
├── Authentifizierung
├── aktiver Schulkontext
├── Sprache
├── Navigation
├── Design-System
└── Feature Registry
```

Fachliche Bereiche können zunächst als lokale Features und später als separat deploybare Remotes betrieben werden.

Mögliche Remotes:

```text
learning
administration
classes
content-editor
exams
```

Insbesondere ein späteres Modul zum Erstellen/Durchführen von Arbeiten oder Prüfungen soll ohne grundlegenden Umbau der Shell ergänzt werden können.

Die Shell stellt einem Remote nur einen kleinen stabilen Plattformkontext zur Verfügung, beispielsweise:

```text
Account-ID
School-Slug
Locale
Feature-/Permission-Informationen
```

Fachlogik verbleibt im jeweiligen Remote.

## Externe Erweiterungen

Eine größere Erweiterung kann später aus Frontend-Remote und Backend-Service bestehen:

```text
Angular Shell
   ↓ lädt optional
Exam Remote
   ↓
Exam Service / Sidecar
   ↓
öffentliche Core-APIs oder Events
```

Der Hauptprozess muss dafür keine Erweiterungs-JARs laden.

## Leitentscheidung

Die Lernplattform optimiert zunächst auf **klare fachliche Grenzen, schnellen Start und einfache Betriebsführung**. Microservices und Microfrontends sind mögliche Deploymentformen vorhandener Modulgrenzen, keine Voraussetzung für die erste produktive Version.
