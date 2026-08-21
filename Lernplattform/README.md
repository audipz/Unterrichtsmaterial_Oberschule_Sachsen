# Interaktive Lernplattform Informatik

Dieses Verzeichnis enthält Konzept, Architektur und später die Implementierung einer webbasierten Lernplattform für die Unterrichtsmaterialien dieses Repositories.

## Ziel

Die vorhandenen Materialien sollen in einer einheitlichen Plattform genutzt werden können:

- **Nachschlagewerk:** Wissen lesen und nachschlagen
- **Arbeitsheft:** Aufgaben interaktiv bearbeiten, automatisch speichern und später fortsetzen
- **Übungen:** Inhalte selbstständig trainieren und Rückmeldungen erhalten
- **Lernkontrollen:** Lernstand nach einem Themenbereich überprüfen

Markdown und die vorhandenen Grafiken bleiben wichtige fachliche Quellen. Die individuellen Schülerdaten werden davon getrennt serverseitig gespeichert.

## Geplante technische Basis

- Frontend: Angular, aktuelle stabile Version
- Frontend-Zustand: Angular Signals
- Backend: Java mit Spring Boot
- Authentifizierung/Autorisierung: OAuth 2 / OpenID Connect
- Datenbank: PostgreSQL
- Betrieb: Kubernetes/K3s

## Verzeichnisstruktur

```text
Lernplattform/
├── README.md
├── Architektur/
│   └── README.md
└── Fachliches_Konzept/
    └── README.md
```

Weitere Unterverzeichnisse für Backend, Frontend, Deployment, Datenbankmigrationen und Tests werden erst angelegt, wenn die Architektur ausreichend festgelegt ist.

## Grundsätze

1. Schule ist der zentrale Mandant.
2. Benutzer besitzen Rollen innerhalb einer Schule.
3. Lehrer können zusätzlich die Rolle `SCHOOL_ADMIN` erhalten.
4. Kritische Verwaltungs- und Löschfunktionen sind Schul-Admins vorbehalten.
5. Löschen erfolgt zunächst als Soft Delete.
6. Soft-gelöschte Daten können innerhalb von drei Monaten durch Schul-Admins reaktiviert werden.
7. Nach drei Monaten erfolgt ein kontrollierter automatischer Purge.
8. Klassen, Kurse, Schülerkonten und Lernstände werden fachlich getrennt modelliert.
9. Schülerantworten werden nicht in die Markdown-Quelldateien geschrieben.
10. Nachschlagewerk, Arbeitsheft und Lernkontrolle sollen fachlich miteinander verknüpft werden.
