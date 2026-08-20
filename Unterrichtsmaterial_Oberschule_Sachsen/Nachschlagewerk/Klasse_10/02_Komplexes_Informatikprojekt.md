# 2 Komplexes Informatikprojekt

## Warum Projekte in der Informatik wichtig sind

Informatiklösungen entstehen selten in einem einzigen Schritt. Häufig arbeiten mehrere Personen über längere Zeit an einem gemeinsamen Ziel. Ein **Informatikprojekt** verbindet fachliche Ideen, Planung, Gestaltung, Programmierung, Dokumentation, Testen und Präsentation.

Ein komplexes Projekt ist nicht nur größer, sondern besteht aus mehreren abhängigen Teilen. Deshalb müssen Ziele, Schnittstellen und Zuständigkeiten klar beschrieben werden.

> **Merke:** Gute Informatikprojekte entstehen nicht durch „einfach anfangen“, sondern durch Verstehen, Planen, Umsetzen, Prüfen und Verbessern.

## Vom Problem zum Projektziel

Am Anfang steht ein Problem oder ein Bedarf. Daraus wird ein Projektziel formuliert. Ein gutes Ziel beschreibt verständlich, welches Ergebnis entstehen soll und woran man erkennt, dass es brauchbar ist.

| Frage | Bedeutung |
|---|---|
| Welches Problem soll gelöst werden? | Ausgangslage verstehen |
| Für wen ist die Lösung gedacht? | Zielgruppe bestimmen |
| Welche Daten werden benötigt? | Informationsgrundlage klären |
| Welche Funktionen sind wichtig? | Anforderungen sammeln |
| Welche Grenzen gibt es? | Zeit, Technik, Datenschutz beachten |

Beispiel: Aus „Wir brauchen eine Übersicht für Schulveranstaltungen“ kann das Ziel werden: „Es entsteht eine einfache Webübersicht, in der Termine mit Datum, Ort und Beschreibung angezeigt und nach Monat gefiltert werden können.“

## Anforderungen

Eine **Anforderung** beschreibt, was eine Lösung leisten oder einhalten soll. Man unterscheidet häufig fachliche und technische Anforderungen.

| Art | Beispiel |
|---|---|
| fachlich | Termine sollen nach Datum sortiert erscheinen. |
| technisch | Die Darstellung soll im Browser funktionieren. |
| gestalterisch | Wichtige Informationen sollen gut lesbar sein. |
| rechtlich/ethisch | Es werden keine unnötigen personenbezogenen Daten veröffentlicht. |
| organisatorisch | Zwischenergebnisse werden regelmäßig gesichert. |

Nicht jede gewünschte Funktion ist gleich wichtig. Deshalb werden Anforderungen oft in „muss“, „soll“ und „kann“ eingeteilt.

## Teilaufgaben und Rollen

Komplexe Projekte werden in Teilaufgaben zerlegt. Das macht den Arbeitsstand überschaubar und ermöglicht Zusammenarbeit.

Typische Teilaufgaben sind:

- Recherche und fachliches Konzept,
- Datenmodell oder Objektmodell,
- Gestaltung der Oberfläche,
- Umsetzung einzelner Funktionen,
- Testen,
- Dokumentation,
- Präsentation.

Rollen können helfen, Verantwortung sichtbar zu machen. Eine Rolle bedeutet nicht, dass nur eine Person alles allein macht. Sie zeigt vor allem, wer den Überblick behält.

## Schnittstellen

Eine **Schnittstelle** legt fest, wie zwei Teile zusammenarbeiten. Das kann eine technische Verbindung sein, aber auch eine Vereinbarung zwischen Gruppen.

Beispiel für eine einfache Datenschnittstelle:

```text
Termin:
- titel: Text
- datum: Datum
- ort: Text
- beschreibung: Text
```

Wenn eine Gruppe die Daten vorbereitet und eine andere Gruppe die Darstellung erstellt, müssen beide dieselben Feldnamen und Bedeutungen verwenden. Sonst passen die Teile später nicht zusammen.

> **Merke:** Eine Schnittstelle ist eine Vereinbarung. Je klarer sie beschrieben ist, desto leichter lassen sich Teilergebnisse zusammenführen.

## Projektverlauf dokumentieren

Eine Projektdokumentation hält wichtige Entscheidungen fest. Sie ist kein Tagebuch aller Kleinigkeiten, sondern erklärt, wie die Lösung aufgebaut ist und warum sie so entstanden ist.

Wichtige Bestandteile:

| Bestandteil | Inhalt |
|---|---|
| Ziel | Was soll entstehen? |
| Anforderungen | Was muss die Lösung leisten? |
| Modell | Daten, Objekte, Abläufe |
| Umsetzung | wichtige Dateien, Funktionen, Werkzeuge |
| Tests | Was wurde geprüft? |
| Reflexion | Was funktioniert gut, was könnte verbessert werden? |

Dokumentation hilft besonders dann, wenn man nach einiger Zeit weiterarbeitet oder ein anderes Teammitglied etwas verstehen muss.

## Versionen und Sicherung

In Projekten entstehen viele Zwischenstände. Eine gute Sicherungsstrategie verhindert, dass Arbeit verloren geht. Dateinamen wie `neu_final_wirklich_final2` sind unübersichtlich. Besser sind klare Versionen, Ordnerstrukturen oder Versionsverwaltung.

| Situation | Sinnvolle Maßnahme |
|---|---|
| täglicher Arbeitsstand | speichern und sichern |
| größere Änderung | vorher Kopie oder Version anlegen |
| Zusammenarbeit | gemeinsame Ablage mit Regeln nutzen |
| Fehler nach Änderung | auf ältere Version zurückgehen |
| Präsentation | fertigen Stand zusätzlich exportieren |

## Testen und Verbessern

Testen bedeutet nicht nur, Fehler zu suchen. Es prüft, ob die Lösung zu den Anforderungen passt. Gute Tests betrachten typische Fälle, Grenzfälle und mögliche Fehleingaben.

Beispiele:

- Werden leere Eingaben sinnvoll behandelt?
- Ist die Darstellung auf verschiedenen Bildschirmgrößen lesbar?
- Funktionieren Links und Medien?
- Sind private Daten geschützt?
- Sind Beschriftungen verständlich?

Fehler sind im Projekt normal. Wichtig ist, sie nachvollziehbar zu finden, zu beschreiben und zu beheben.

## Begriffe zum Nachschlagen

**Anforderung:** Beschreibung dessen, was eine Lösung leisten oder beachten soll.

**Schnittstelle:** Vereinbarung, wie Teile eines Systems zusammenarbeiten.

**Projektziel:** verständliche Beschreibung des angestrebten Ergebnisses.

**Dokumentation:** geordnete Beschreibung von Ziel, Aufbau, Entscheidungen und Ergebnissen.

**Version:** bestimmter gespeicherter Stand eines Arbeitsergebnisses.

→ Vorwissen: Klasse 8, **Algorithmen** und **Objektorientierte Modellierung**.
