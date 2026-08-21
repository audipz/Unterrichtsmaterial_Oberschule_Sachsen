# Projektstruktur

## Ziel

Die Lernplattform wird als sauberes Maven-Multi-Module-Projekt aufgebaut. Deployment-Artefakte liegen getrennt davon in einem Helm-Chart.

Die Struktur soll lokale Entwicklung, CI/CD und Betrieb auf K3s möglichst klar voneinander trennen.

## Vorgesehene Verzeichnisstruktur

```text
Lernplattform/
├── pom.xml
├── README.md
├── Architektur/
├── Fachliches_Konzept/
│
├── backend/
│   ├── pom.xml
│   ├── app/
│   │   └── pom.xml
│   ├── domain/
│   │   └── pom.xml
│   ├── persistence/
│   │   └── pom.xml
│   ├── security/
│   │   └── pom.xml
│   └── material-import/
│       └── pom.xml
│
├── frontend/
│   ├── pom.xml
│   └── angular-app/
│       ├── package.json
│       ├── angular.json
│       └── src/
│
├── deployment/
│   └── helm/
│       └── lernplattform/
│           ├── Chart.yaml
│           ├── values.yaml
│           ├── values-dev.yaml
│           ├── values-prod.yaml
│           └── templates/
│               ├── backend-deployment.yaml
│               ├── backend-service.yaml
│               ├── frontend-deployment.yaml
│               ├── frontend-service.yaml
│               ├── ingress.yaml
│               ├── configmap.yaml
│               ├── secret.yaml
│               └── serviceaccount.yaml
│
└── docker/
    ├── backend.Dockerfile
    └── frontend.Dockerfile
```

Die genaue Modulschnittgrenze kann während der Implementierung noch angepasst werden. Die Trennung zwischen Backend, Frontend und Deployment bleibt jedoch bestehen.

## Maven Root

`Lernplattform/pom.xml` ist der zentrale Maven-Aggregator.

Vorgesehene Module:

```xml
<modules>
    <module>backend</module>
    <module>frontend</module>
</modules>
```

Damit kann der komplette Build mit einem Befehl gestartet werden:

```text
mvn clean verify
```

## Backend als Maven-Multi-Module

Das Backend wird modular aufgebaut.

### backend/domain

Enthält fachliche Kernmodelle und Regeln, beispielsweise:

- Schulen,
- Benutzer und Rollen,
- Klassen und Kurse,
- Arbeitshefte,
- Übungen,
- Antworten,
- Soft-Delete-Lebenszyklen.

Dieses Modul soll möglichst wenig technische Abhängigkeiten besitzen.

### backend/persistence

Enthält:

- JPA-Entities beziehungsweise Persistenzabbildung,
- Repository-Implementierungen,
- Datenbankmigrationen,
- PostgreSQL-spezifische Konfiguration.

### backend/security

Enthält:

- Spring Security,
- OAuth2/OIDC-Konfiguration,
- Rollen- und Mandantenprüfung,
- Authentifizierungsintegration.

### backend/material-import

Enthält:

- Markdown-Import,
- Auswertung strukturierter Aufgabenblöcke,
- Validierung stabiler IDs,
- Materialversionierung.

### backend/app

Ist die ausführbare Spring-Boot-Anwendung.

Sie verbindet die übrigen Module und stellt die REST-API bereit.

Das ausführbare Artefakt ist nur dieses Modul.

## Frontend und Maven

Die Angular-Anwendung bleibt technisch ein npm-Projekt, wird aber in den Maven-Gesamtbuild integriert.

`frontend/pom.xml` steuert beispielsweise:

- Installation der definierten Node-Version,
- `npm ci`,
- Angular-Build,
- Frontend-Tests,
- Übergabe des Build-Artefakts für den Container-Build.

Damit wird vermieden, dass CI-Umgebungen zufällig unterschiedliche globale Node-/Angular-Versionen verwenden.

Der Angular-Quellcode bleibt trotzdem eine normale Angular-Projektstruktur.

## Angular-Struktur

Vorgesehen ist eine featureorientierte Struktur, beispielsweise:

```text
frontend/angular-app/src/app/
├── core/
│   ├── auth/
│   ├── http/
│   └── layout/
├── shared/
├── features/
│   ├── learning/
│   ├── workbook/
│   ├── exercises/
│   ├── progress/
│   ├── classes/
│   └── administration/
└── app.routes.ts
```

Angular Signals sind der bevorzugte Zustand für Komponenten und Feature-Stores.

## Helm

Das Deployment erfolgt über ein eigenes Helm-Chart:

```text
Lernplattform/deployment/helm/lernplattform
```

Das Chart deployt mindestens:

- Backend,
- Frontend,
- Services,
- Ingress,
- Konfiguration,
- ServiceAccount.

PostgreSQL soll nicht fest in das Anwendungs-Chart eingebaut werden. Die Plattform erhält eine externe konfigurierbare PostgreSQL-Verbindung. Dadurch kann im K3s-Cluster beispielsweise ein separat betriebenes PostgreSQL beziehungsweise ein PostgreSQL-Operator verwendet werden.

## Helm Values

Konfiguration erfolgt über `values.yaml` und umgebungsspezifische Overrides.

Beispiele:

```yaml
backend:
  image:
    repository: registry.example/lernplattform-backend
    tag: latest
  replicas: 2

frontend:
  image:
    repository: registry.example/lernplattform-frontend
    tag: latest

postgresql:
  host: postgres.database.svc.cluster.local
  port: 5432
  database: lernplattform

 ingress:
  enabled: true
  host: lernen.example.de
```

Passwörter und andere Secrets gehören nicht im Klartext in `values.yaml`.

## Secrets

Das Chart soll Kubernetes-Secrets referenzieren können.

Für Produktion wird bevorzugt, Secrets außerhalb des Git-Repositories bereitzustellen, beispielsweise über:

- bestehende Kubernetes-Secrets,
- External Secrets,
- Sealed Secrets,
- einen anderen im Cluster vorhandenen Secret-Mechanismus.

Das Chart erhält daher Werte wie:

```text
existingSecret
```

statt verpflichtend Passwörter selbst zu erzeugen.

## Health Checks

Das Spring-Boot-Backend stellt über Actuator geeignete Endpunkte für Kubernetes bereit.

Vorgesehen sind mindestens:

- Liveness Probe,
- Readiness Probe.

Helm konfiguriert diese Probes im Deployment.

## Datenbankmigrationen

Datenbankschemaänderungen werden versioniert ausgeführt. Vorgesehen ist Flyway oder Liquibase; eine konkrete Auswahl erfolgt vor Implementierungsbeginn.

Migrationen sind Bestandteil des Backend-Artefakts und werden gemeinsam mit der Anwendung versioniert.

## Container

Backend und Frontend erhalten getrennte Container-Images.

```text
lernplattform-backend:<version>
lernplattform-frontend:<version>
```

Das Backend-Image enthält nur die ausführbare Spring-Boot-Anwendung.

Das Frontend wird als statische Angular-Anwendung gebaut und über einen kleinen Webserver ausgeliefert.

## Versionierung

Maven-Version, Container-Tag und Helm-App-Version sollen möglichst zusammenpassen.

Beispiel:

```text
Maven:       0.1.0-SNAPSHOT
Image:       0.1.0
Helm Chart:  0.1.0
appVersion:  0.1.0
```

Für CI-Builds können zusätzlich Commit-SHAs als unveränderliche Image-Tags verwendet werden.

## Build-Reihenfolge

Zielablauf:

```text
mvn clean verify
        ↓
Backend-Tests
        ↓
Frontend-Tests + Angular-Build
        ↓
Container-Images
        ↓
helm lint
        ↓
helm template
        ↓
Deployment nach K3s
```

## Grundsatz

Maven organisiert Quellcode und reproduzierbaren Build. Docker erzeugt deploybare Laufzeit-Artefakte. Helm beschreibt das Kubernetes/K3s-Deployment.

Diese Verantwortlichkeiten werden bewusst getrennt gehalten.
