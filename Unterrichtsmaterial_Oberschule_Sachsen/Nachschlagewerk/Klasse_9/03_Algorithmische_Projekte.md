# 3 Algorithmische Projekte

## Von der Aufgabe zum Projekt

Komplexere Informatikprobleme lassen sich selten mit einer einzelnen Anweisung lösen. Sie müssen zunächst verstanden, in kleinere Teile zerlegt, geplant, umgesetzt, getestet und verbessert werden.

Ein Informatikprojekt besteht deshalb nicht nur aus Programmcode. Zum Projekt gehören unter anderem:

- Problem und Ziel verstehen,
- Anforderungen klären,
- Fachbegriffe und Daten verstehen,
- Teilprobleme und Verantwortlichkeiten erkennen,
- Algorithmen entwerfen,
- Software schrittweise umsetzen,
- testen und Fehler beheben,
- Ergebnisse dokumentieren und präsentieren.

![Vom Problem zum algorithmischen Entwurf, zur Implementierung und zu Tests](grafiken/algorithmischer_entwurf.svg)

Die Schritte laufen nicht immer genau einmal nacheinander. Neue Erkenntnisse aus Tests oder Rückmeldungen können dazu führen, dass Anforderungen oder Entwurf angepasst werden.

> **Merke:** Gute Software entsteht nicht dadurch, dass man möglichst schnell mit dem Programmieren beginnt. Zuerst muss klar sein, **welches Problem** gelöst werden soll und **woran man erkennt**, dass die Lösung richtig ist.

## Anforderungen: Was soll die Lösung leisten?

Eine **Anforderung** beschreibt, was ein System leisten oder welche Eigenschaft es besitzen soll. Anforderungen bilden die Grundlage für Entwurf und Tests.

Unklare Formulierungen wie

```text
Das Programm soll gut funktionieren.
```

helfen kaum weiter. Besser sind überprüfbare Aussagen:

```text
Das Programm soll drei Zahlen einlesen,
den größten Wert bestimmen
und das Ergebnis verständlich ausgeben.
```

### Funktionale Anforderungen

**Funktionale Anforderungen** beschreiben Funktionen und Verhalten des Systems.

Beispiele:

- Das Programm soll drei Zahlen einlesen.
- Das Programm soll den größten Wert bestimmen.
- Eine gespeicherte Aufgabe soll wieder geladen werden können.
- Ein Benutzer soll nach einem Titel suchen können.

Sie beantworten vor allem die Frage: **Was soll das System tun?**

### Nichtfunktionale Anforderungen

**Nichtfunktionale Anforderungen** beschreiben Eigenschaften und Rahmenbedingungen.

Beispiele:

- Eine Eingabe soll innerhalb kurzer Zeit verarbeitet werden.
- Fehlermeldungen sollen verständlich formuliert sein.
- Daten sollen nach einem Neustart erhalten bleiben.
- Personenbezogene Daten sollen nur für berechtigte Benutzer sichtbar sein.

Sie beantworten eher Fragen wie: **Wie gut, wie sicher oder unter welchen Bedingungen soll das System arbeiten?**

### Muss-, Soll- und Kann-Anforderungen

| Priorität | Bedeutung | Beispiel |
|---|---|---|
| Muss | ohne diese Funktion erfüllt das Projekt sein Ziel nicht | größtes Element bestimmen |
| Soll | wichtig, aber notfalls später umsetzbar | Ergebnisse speichern |
| Kann | zusätzliche Verbesserung | verschiedene Farbschemata |

### Abnahmekriterium

Zu einer guten Anforderung gehört möglichst ein **Abnahmekriterium**: eine überprüfbare Bedingung, die zeigt, ob die Anforderung erfüllt ist.

```text
Anforderung:
Das Programm soll die größte von drei eingegebenen Zahlen ausgeben.

Abnahmekriterium:
Für die Eingaben 4, 9 und 2 wird 9 ausgegeben.
```

## Anforderungen verstehen: die Fachdomäne

Programme lösen Aufgaben aus einem bestimmten **Fachbereich**. Dieser Fachbereich wird häufig als **Domäne** bezeichnet. Eine Bibliothekssoftware hat beispielsweise eine andere Domäne als eine Wetterstation oder ein Computerspiel.

Bevor ein Programm entworfen wird, sollte man verstehen:

- Welche Begriffe gibt es in diesem Fachbereich?
- Welche Objekte sind wichtig?
- Welche Regeln gelten?
- Welche Abläufe kommen vor?
- Welche Begriffe dürfen nicht verwechselt werden?

Bei einer Bibliothek könnten beispielsweise `Buch`, `Exemplar`, `Ausleihe`, `Leser` und `Rückgabe` wichtige Begriffe sein.

### Domain-Driven Design – ein kurzer Ausblick

**Domain-Driven Design (DDD)** ist ein Ansatz für größere Softwaresysteme, bei dem die Fachdomäne und ihre Begriffe besonders wichtig genommen werden. Entwickler und Fachleute versuchen dabei, eine gemeinsame, möglichst eindeutige Sprache für das Problemgebiet zu verwenden.

> **Merke:** Bevor man Software sinnvoll zerlegt, sollte man die **fachliche Welt** verstehen, die die Software abbildet.

Begriffe wie **Bounded Context**, Aggregate oder Domain Events gehören zur professionellen Vertiefung und müssen hier noch nicht beherrscht werden.

## Ein Problem zerlegen

Ein großes Problem wird in kleinere **Teilprobleme** zerlegt. Dieses Vorgehen nennt man auch **Dekomposition**.

Beispiel: Eine kleine Aufgabenverwaltung soll Aufgaben erfassen, anzeigen und als erledigt markieren können.

Mögliche Teilprobleme sind:

1. Aufgabe eingeben,
2. Eingabe prüfen,
3. Aufgabe speichern,
4. Aufgaben anzeigen,
5. Aufgabe auswählen,
6. Status ändern,
7. Daten dauerhaft sichern.

### Nach Funktionen zerlegen

```text
Aufgabe erfassen
Aufgabe prüfen
Aufgabe speichern
Aufgabe anzeigen
Aufgabe ändern
```

### Nach Daten und Fachobjekten zerlegen

```text
Aufgabe
Benutzer
Kategorie
Status
```

Ein gutes Teilproblem sollte möglichst eine klar erkennbare Aufgabe, verständliche Ein- und Ausgaben und wenig unnötige Abhängigkeiten besitzen sowie getrennt testbar sein.

## Schnittstellen zwischen Teilproblemen

Wer ein Problem zerlegt, muss auch festlegen, **wie die Teile zusammenarbeiten**.

```text
Eingabe prüfen
    Eingabe: Text des Benutzers
    Ausgabe: gültige Zahl oder Fehlermeldung
```

Eine solche Beschreibung ist bereits eine einfache **Schnittstelle**. Sie legt unter anderem fest, welche Daten ein Teil erhält und zurückliefert und welche Fehler auftreten können.

## Algorithmischer Entwurf

Beim **algorithmischen Entwurf** wird aus der fachlichen Aufgabe ein genauer Lösungsweg.

### 1. Eingaben und Ausgaben bestimmen

```text
Eingabe: drei Zahlen a, b, c
Ausgabe: größte Zahl
```

### 2. Sonderfälle und Bedingungen erkennen

Beim größten Wert muss beispielsweise bedacht werden, dass Werte verschieden oder gleich sowie positiv, null oder negativ sein können.

### 3. Verarbeitungsschritte festlegen

```text
maximum := a

WENN b > maximum
    maximum := b

WENN c > maximum
    maximum := c

AUSGABE maximum
```

### 4. Kontrollstrukturen auswählen

Dazu gehören Sequenz, Auswahl, Wiederholung, Variablen, Bedingungen und Funktionen beziehungsweise Prozeduren.

### 5. Datenstrukturen auswählen

Je nach Aufgabe eignen sich einzelne Variablen, Listen/Arrays, Tabellen, Datensätze/Objekte oder Datenbanken.

### 6. Teilalgorithmen bilden

```text
liesEingabe()
pruefeEingabe()
berechneMaximum()
zeigeErgebnis()
```

### 7. Entwurf darstellen

Mögliche Darstellungen sind natürliche Sprache, Pseudocode, Struktogramm und Ablaufdiagramm.

→ Siehe Nachschlagewerk Klasse 8, **Algorithmen darstellen**.

> **Merke:** Ein algorithmischer Entwurf legt Eingaben, Ausgaben, Fälle, Datenstrukturen, Kontrollstrukturen und Teilalgorithmen fest.

## Vom Entwurf zum Programmcode

Bei der **Implementierung** wird der Entwurf in eine konkrete Programmiersprache übertragen. Dabei entstehen zusätzliche Entscheidungen, etwa zu Variablennamen, Datentypen, Funktionen, Parametern, Bibliotheken und Fehlerbehandlung.

## Iterativ und inkrementell arbeiten

Die Begriffe **iterativ** und **inkrementell** beschreiben zwei verschiedene Ideen, die häufig gemeinsam verwendet werden.

![Iteratives Verbessern und inkrementelles Erweitern](grafiken/iterativ_inkrementell.svg)

### Iterativ: einen Stand wiederholt verbessern

Bei **iterativem Arbeiten** wird eine vorhandene Lösung mehrfach überarbeitet. Ein typischer Zyklus lautet `entwerfen → umsetzen → testen → verbessern`.

### Inkrementell: die Lösung schrittweise erweitern

Bei **inkrementellem Arbeiten** wächst die Lösung um weitere funktionsfähige Teile. Bei einer Aufgabenverwaltung könnte zunächst das Erfassen, dann das Erledigen, anschließend das Speichern und schließlich das Filtern hinzukommen.

| Begriff | Kernidee | typische Frage |
|---|---|---|
| iterativ | Vorhandenes wiederholt verbessern | Wie können wir diesen Stand verbessern? |
| inkrementell | schrittweise neue Funktionalität ergänzen | Welcher nutzbare Teil kommt als Nächstes hinzu? |

> **Merke:** **Iteration = verbessern. Inkrement = erweitern.** Moderne Projekte kombinieren häufig beides.

## Prototypen

Ein **Prototyp** ist ein früher, vereinfachter Lösungsstand. Er dient dazu, Ideen auszuprobieren und Unsicherheiten zu verringern. Er muss noch kein vollständiges Produkt sein.

## Testen in algorithmischen Projekten

Testen ist kein letzter Schritt nach dem Programmieren. Tests begleiten ein Projekt von den Anforderungen bis zur fertigen Lösung.

Ein **Testfall** enthält mindestens Ausgangssituation beziehungsweise Eingabe, erwartetes Ergebnis, tatsächliches Ergebnis und die Bewertung bestanden/nicht bestanden.

### Warum wird getestet?

Tests sollen zeigen, ob Anforderungen erfüllt werden, normale und ungewöhnliche Eingaben funktionieren, Grenzfälle korrekt behandelt werden, Programmzweige durchlaufen werden und Änderungen ältere Funktionen nicht beschädigen.

> **Merke:** Ein Programm, das ohne Fehlermeldung läuft, ist nicht automatisch korrekt.

## Tests aus Anforderungen ableiten

| Fall | Eingabe | Erwartung | Zweck |
|---|---|---:|---|
| normal | 3, 8, 5 | 8 | typischer Fall |
| größter Wert zuerst | 9, 3, 2 | 9 | Reihenfolge prüfen |
| größter Wert zuletzt | 1, 4, 10 | 10 | letzten Vergleich prüfen |
| gleiche Maximalwerte | 8, 8, 2 | 8 | Gleichheit prüfen |
| alle gleich | 4, 4, 4 | 4 | Sonderfall |
| negative Werte | −2, −7, −1 | −1 | Vorzeichen prüfen |

## Grenzwerte und Grenzwertanalyse

Ein **Grenzwert** ist ein Wert, an dem sich das Verhalten des Programms ändert oder ein erlaubter Bereich beginnt beziehungsweise endet. Bei einem zulässigen Alter von 12 bis 16 Jahren sind beispielsweise `11, 12, 13` sowie `15, 16, 17` besonders wichtige Testwerte.

> **Merke:** Bei Grenzen möglichst **knapp darunter, genau darauf und knapp darüber** testen.

## Gültige und ungültige Eingaben

Erwartet ein Programm eine ganze Zahl von 1 bis 100, sind neben normalen Werten auch `1`, `100`, `0`, `101`, negative Zahlen, Dezimalzahlen, Text und leere Eingaben interessante Tests.

## Blackbox- und Whitebox-Tests

Beim **Blackbox-Test** wird das Programm von außen anhand von Eingaben und erwarteten Ausgaben geprüft. Beim **Whitebox-Test** ist die interne Programmstruktur bekannt; Tests werden gezielt für Anweisungen, Bedingungen und Zweige gewählt.

## Testabdeckung

**Anweisungsabdeckung** fragt, ob relevante Anweisungen ausgeführt wurden. **Zweigabdeckung** betrachtet die Alternativen von Entscheidungen. **Pfadabdeckung** betrachtet mögliche Ausführungswege. Vollständige Pfadabdeckung kann bei komplexen Programmen sehr aufwendig oder praktisch unmöglich sein.

## Schleifen testen

Bei Schleifen sind kein Durchlauf, genau ein Durchlauf, mehrere Durchläufe und Werte direkt an der Abbruchgrenze wichtige Fälle. So findet man beispielsweise **Off-by-one-Fehler**.

## Unit-, Integrations- und Systemtests

Ein **Unit-Test** prüft einen kleinen Programmteil, ein **Integrationstest** das Zusammenspiel mehrerer Teile und ein **Systemtest** das vollständige System gegenüber seinen Anforderungen.

## Regressionstest

Ein **Regressionstest** wiederholt bereits bestandene Tests nach einer Änderung. Dadurch wird geprüft, ob die Änderung versehentlich vorhandene Funktionen beschädigt hat.

## Fehler systematisch untersuchen

Bei einem fehlgeschlagenen Test werden Eingabe, erwartetes und tatsächliches Ergebnis festgehalten, die erste Abweichung im Ablauf gesucht, die Ursache korrigiert und anschließend ursprüngliche sowie bestehende Regressionstests erneut ausgeführt.

## Zusammenarbeit im Projekt

Softwareprojekte entstehen häufig im Team. Zusammenarbeit bedeutet dabei mehr als „mehrere Personen programmieren gleichzeitig“. Ein Team muss Informationen austauschen, Entscheidungen treffen, Arbeit verteilen, Änderungen nachvollziehen und Ergebnisse zusammenführen.

### Zusammenarbeit vor Ort

Bei der **Zusammenarbeit vor Ort** befinden sich Teammitglieder am selben Ort. Fragen können direkt besprochen, Skizzen gemeinsam betrachtet und Probleme schnell geklärt werden.

Typische Formen sind:

- gemeinsames Planungsgespräch,
- Arbeit in Kleingruppen,
- **Pair Programming**, bei dem zwei Personen gemeinsam an einem Programmteil arbeiten,
- kurze tägliche Abstimmungen,
- gemeinsames Testen oder eine Code-Durchsicht.

Vorteilhaft ist die direkte Kommunikation. Gleichzeitig müssen wichtige Entscheidungen trotzdem dokumentiert werden: Eine mündliche Absprache ist später möglicherweise vergessen oder für abwesende Personen nicht nachvollziehbar.

### Online und verteilt zusammenarbeiten

Bei **verteilter Zusammenarbeit** arbeiten Teammitglieder an unterschiedlichen Orten. Kommunikation kann **synchron** oder **asynchron** erfolgen.

**Synchron** bedeutet gleichzeitig, beispielsweise durch Videokonferenz, Chat oder gemeinsames Bearbeiten eines Dokuments. **Asynchron** bedeutet zeitversetzt, beispielsweise über Tickets, Kommentare, E-Mail, Wiki-Seiten oder Code-Reviews.

Asynchrone Zusammenarbeit ist in Softwareprojekten besonders wichtig, weil nicht alle Beteiligten ständig gleichzeitig verfügbar sind. Informationen müssen deshalb so dokumentiert werden, dass andere sie später verstehen können.

### Hybrid arbeiten

Viele Teams arbeiten **hybrid**: Ein Teil der Zusammenarbeit findet vor Ort statt, ein anderer online. Die technischen Werkzeuge ändern dabei nicht die Grundaufgaben. Das Team muss weiterhin klären:

- Wer arbeitet woran?
- Was ist bereits erledigt?
- Welche Entscheidung wurde getroffen und warum?
- Wo befindet sich die aktuelle Version?
- Welche Probleme oder offenen Fragen gibt es?
- Wie werden Änderungen geprüft und zusammengeführt?

### Aufgaben und Tickets

Arbeit wird in professionellen Projekten häufig über **Tickets** beziehungsweise **Issues** beschrieben. Ein Ticket sollte möglichst eine überschaubare Aufgabe, einen Fehler oder eine gewünschte Änderung enthalten.

Statt

```text
Programm fertigstellen
```

ist beispielsweise besser:

```text
Titel: Eingabeprüfung für Altersfeld ergänzen

Beschreibung:
Nur ganze Zahlen von 12 bis 16 akzeptieren.
Bei ungültiger Eingabe verständliche Fehlermeldung anzeigen.

Abnahmekriterien:
- 12 und 16 werden akzeptiert.
- 11 und 17 werden abgewiesen.
- Texteingaben führen zu einer Fehlermeldung.
```

Tickets können einen Status wie **offen**, **in Arbeit**, **Review** und **erledigt** besitzen. Auf einem **Kanban-Board** werden solche Aufgaben häufig als Karten in entsprechenden Spalten dargestellt.

> **Merke:** Ein gutes Ticket beantwortet möglichst: **Was soll geändert werden? Warum? Woran erkennt man, dass es fertig ist?**

### Versionsverwaltung und Zusammenarbeit am Code

Bei Softwareprojekten muss auch der Quellcode gemeinsam verwaltet werden. Eine **Versionsverwaltung** wie Git speichert nachvollziehbare Entwicklungsstände.

Wichtige Begriffe sind beispielsweise:

- **Repository** – verwalteter Bestand des Projekts,
- **Commit** – gespeicherter Änderungsstand mit Beschreibung,
- **Branch** – Entwicklungszweig für zusammengehörige Änderungen,
- **Merge** – Zusammenführen von Änderungen,
- **Pull Request/Merge Request** – vorgeschlagene Änderung, die vor dem Zusammenführen besprochen und geprüft werden kann,
- **Code Review** – Durchsicht einer Änderung durch andere Teammitglieder.

Gute Commit-Beschreibungen wie `Grenzwertprüfung für Alter ergänzen` sind hilfreicher als Meldungen wie `Änderungen` oder `fertig`.

### Konflikte und Entscheidungen

Zusammenarbeit bedeutet auch, unterschiedliche Vorschläge zu bewerten. Technische Entscheidungen sollten nach nachvollziehbaren Kriterien getroffen werden, beispielsweise Verständlichkeit, Wartbarkeit, Sicherheit, Aufwand oder Erfüllung der Anforderungen.

Wichtige Entscheidungen können in einem Ticket, einer Wiki-Seite oder einer kurzen **Entscheidungsdokumentation** festgehalten werden. Dadurch ist später nicht nur sichtbar, **was** entschieden wurde, sondern auch **warum**.

## Dokumentation: Wissen für andere verständlich festhalten

Dokumentation ist nicht ein einzelnes Dokument am Projektende. In einem Softwareprojekt entstehen unterschiedliche Dokumentationsarten für unterschiedliche Zwecke und Zielgruppen.

### Projektdokumentation und Textdokumente

Ein klassisches Projektdokument kann Problem und Ziel, Anforderungen, Entwurf, Datenstrukturen, Schnittstellen, Tests, Ergebnisse und bekannte Grenzen zusammenfassen. Solche Dokumente eignen sich besonders für einen abgeschlossenen Überblick oder eine Abgabe.

Wichtig ist, dass Dokumentation zum aktuellen Projektstand passt. Veraltete Dokumentation kann irreführender sein als fehlende Dokumentation.

### Wiki und Wissensplattformen

Für Wissen, das während eines Projekts wächst und von vielen Personen gepflegt wird, eignen sich **Wikis** oder Wissensplattformen. Ein bekanntes Beispiel ist **Confluence**.

Dort können beispielsweise dokumentiert werden:

- Projektüberblick und Ziele,
- Fachbegriffe der Domäne,
- Architektur und Komponenten,
- Schnittstellen,
- Installations- und Startanleitungen,
- Entwicklungsregeln,
- Entscheidungen,
- häufige Fehler und Lösungen.

Wiki-Seiten lassen sich verlinken und schrittweise aktualisieren. Entscheidend ist nicht das konkrete Werkzeug, sondern dass Wissen auffindbar, verständlich und aktuell bleibt.

### Tickets dokumentieren Arbeit und Änderungen

Tickets sind nicht nur Aufgabenlisten. Ein gut gepflegtes Ticket dokumentiert auch einen Teil der Projektgeschichte: Problem, Anforderungen, Diskussionen, Entscheidungen, Umsetzung und gegebenenfalls Testnachweise.

Beispielsweise kann ein Fehler-Ticket enthalten:

```text
Fehler:
Alter 16 wird fälschlich abgewiesen.

Ursache:
Bedingung verwendet alter < 16 statt alter <= 16.

Korrektur:
Vergleich angepasst.

Test:
15, 16 und 17 erneut geprüft.
```

### Dokumentation direkt im Quellcode

Auch Quellcode kann und sollte dokumentiert werden. Dabei sind zwei Dinge zu unterscheiden:

1. **verständlicher Code**, der durch gute Namen und klare Struktur möglichst viel selbst erklärt,
2. **Kommentare und Dokumentationskommentare**, die zusätzliche Informationen liefern.

Ein Kommentar sollte nicht nur wiederholen, was der Code ohnehin sichtbar tut.

Wenig hilfreich:

```java
// Erhöhe i um 1
i++;
```

Hilfreicher kann ein Kommentar sein, der den Grund einer ungewöhnlichen Entscheidung erklärt:

```java
// Der Grenzwert gehört zum gültigen Bereich und muss deshalb mit <= geprüft werden.
if (alter <= 16) {
    // ...
}
```

### Dokumentationskommentare für Funktionen, Klassen und Schnittstellen

Viele Programmiersprachen und Werkzeuge kennen besondere **Dokumentationskommentare**. Aus ihnen kann teilweise automatisch eine API-Dokumentation erzeugt werden.

Bei Java ist **Javadoc** verbreitet:

```java
/**
 * Bestimmt den größeren von zwei Werten.
 *
 * @param a erster Wert
 * @param b zweiter Wert
 * @return der größere Wert
 */
public static int maximum(int a, int b) {
    return a > b ? a : b;
}
```

Für TypeScript wird häufig **TSDoc** beziehungsweise eine ähnliche JSDoc-Schreibweise verwendet:

```typescript
/**
 * Bestimmt den größeren von zwei Werten.
 *
 * @param a - Erster Wert.
 * @param b - Zweiter Wert.
 * @returns Der größere Wert.
 */
function maximum(a: number, b: number): number {
    return a > b ? a : b;
}
```

Solche Kommentare sind besonders nützlich an **öffentlichen Schnittstellen**: Welche Aufgabe hat eine Funktion? Welche Parameter erwartet sie? Was liefert sie zurück? Welche Voraussetzungen oder Besonderheiten gelten?

> **Merke:** Kommentare erklären vor allem **Absicht, Vertrag oder Besonderheiten**. Gute Bezeichner und klare Programmstruktur ersetzen sie nicht, sondern ergänzen sie.

### README-Datei

Viele Softwareprojekte besitzen eine Datei namens `README.md`. Sie ist häufig der erste Einstieg in ein Repository und kann beispielsweise enthalten:

- Zweck des Projekts,
- Voraussetzungen,
- Installation beziehungsweise Start,
- grundlegende Bedienung,
- Projektstruktur,
- Hinweise für Mitwirkende,
- Verweise auf ausführlichere Dokumentation.

### Technische Dokumentation und Benutzerdokumentation

Nicht jede Dokumentation richtet sich an dieselbe Zielgruppe.

| Dokumentation | typische Zielgruppe | typische Inhalte |
|---|---|---|
| Benutzerdokumentation | Anwender | Bedienung, Beispiele, Fehlermeldungen |
| Entwicklungsdokumentation | Entwickler | Aufbau, Schnittstellen, Entwicklungsregeln |
| API-Dokumentation | Entwickler/Nutzer einer Schnittstelle | Funktionen, Parameter, Rückgabewerte |
| Betriebsdokumentation | Betrieb/Administration | Installation, Konfiguration, Sicherung, Überwachung |
| Projektdokumentation | Projektbeteiligte/Auftraggeber | Ziele, Anforderungen, Entscheidungen, Tests, Ergebnisse |

### Diagramme als Dokumentation

Nicht alles lässt sich am besten als Fließtext erklären. Je nach Inhalt können Diagramme verständlicher sein, beispielsweise:

- Ablaufdiagramm oder Struktogramm für Algorithmen,
- ER-Diagramm für Datenmodelle,
- Klassendiagramm für Klassen und Beziehungen,
- Architekturdiagramm für Komponenten und Schnittstellen,
- Sequenzdiagramm für den zeitlichen Nachrichtenaustausch zwischen Komponenten.

Eine gute Dokumentation kombiniert Text, Tabellen, Codebeispiele und Grafiken passend zum Inhalt.

### Dokumentation gehört zum Entwicklungsprozess

Dokumentation sollte möglichst **mit der Software geändert werden**. Wird eine Schnittstelle verändert, müssen beispielsweise Beschreibung, Beispiele und gegebenenfalls Tests angepasst werden. Versionsverwaltung hilft dabei, Code und zugehörige Dokumentation gemeinsam nachvollziehbar zu ändern.

> **Merke:** Gute Dokumentation beantwortet nicht nur **was vorhanden ist**, sondern je nach Zielgruppe auch **wie es benutzt wird, warum eine Entscheidung getroffen wurde und welche Grenzen gelten**.

## Begriffe zum Nachschlagen

**Abnahmekriterium:** überprüfbare Bedingung, anhand derer festgestellt werden kann, ob eine Anforderung erfüllt ist.

**Anforderung:** überprüfbare Beschreibung einer gewünschten Funktion, Eigenschaft oder Rahmenbedingung eines Systems.

**Asynchrone Zusammenarbeit:** Zusammenarbeit, bei der Beteiligte Informationen zeitversetzt austauschen.

**Blackbox-Test:** Test anhand von Eingaben und erwarteten Ergebnissen, ohne die interne Implementierung für die Testauswahl zu betrachten.

**Branch:** Entwicklungszweig in einer Versionsverwaltung.

**Code Review:** systematische Durchsicht einer Codeänderung durch andere Personen.

**Commit:** gespeicherter und beschriebener Änderungsstand in einer Versionsverwaltung.

**Dekomposition:** Zerlegung eines großen Problems in kleinere Teilprobleme.

**Dokumentationskommentar:** besonders formatierter Kommentar zur Beschreibung von Funktionen, Klassen oder Schnittstellen, aus dem Werkzeuge teilweise Dokumentation erzeugen können.

**Domäne:** Fachbereich beziehungsweise Problemgebiet, das eine Software abbildet oder unterstützt.

**Domain-Driven Design (DDD):** Ansatz für Softwareentwicklung, bei dem Fachdomäne und gemeinsame Fachsprache eine zentrale Rolle spielen.

**Grenzwert:** Wert an einer Grenze, an der sich zulässiger Bereich oder Programmverhalten ändert.

**Inkrement:** funktionsfähige Erweiterung eines bestehenden Systems.

**Inkrementell:** Vorgehen, bei dem ein System schrittweise um weitere nutzbare Teile erweitert wird.

**Integrationstest:** Test des Zusammenspiels mehrerer Programmteile.

**Iteration:** wiederholter Arbeitszyklus zur Prüfung und Verbesserung eines vorhandenen Standes.

**Iterativ:** Vorgehen, bei dem ein vorhandener Stand wiederholt überarbeitet und verbessert wird.

**Javadoc:** Dokumentationssystem für Java, das besonders formatierte Kommentare auswerten und daraus Dokumentation erzeugen kann.

**Kanban-Board:** Darstellung von Aufgaben als Karten in Spalten, die verschiedene Bearbeitungszustände zeigen.

**Meilenstein:** wichtiger überprüfbarer Zwischenstand eines Projekts.

**Pull Request/Merge Request:** Vorschlag, Änderungen aus einem Entwicklungszweig zu prüfen und zusammenzuführen.

**README:** meist zentrale Einstiegsdatei eines Softwareprojekts mit Überblick und Nutzungshinweisen.

**Regressionstest:** erneuter Test bereits funktionierender Eigenschaften nach einer Änderung.

**Repository:** durch eine Versionsverwaltung verwalteter Projektbestand.

**Schnittstelle:** festgelegte Art, wie Programmteile Daten austauschen oder Dienste anbieten.

**Synchrone Zusammenarbeit:** Zusammenarbeit, bei der Beteiligte gleichzeitig miteinander kommunizieren oder arbeiten.

**Systemtest:** Test des vollständigen Systems gegenüber seinen Anforderungen.

**Testfall:** festgelegte Ausgangssituation beziehungsweise Eingabe mit erwartetem Ergebnis.

**Ticket/Issue:** dokumentierte Aufgabe, Anforderung, Änderung oder Fehler in einem Projektverwaltungssystem.

**TSDoc:** Konvention für Dokumentationskommentare in TypeScript-Projekten.

**Unit-Test:** Test eines kleinen abgegrenzten Programmteils, beispielsweise einer Funktion.

**Versionsverwaltung:** System zum Speichern, Vergleichen und Zusammenführen nachvollziehbarer Entwicklungsstände.

**Whitebox-Test:** Test, bei dem die interne Struktur des Programms für die Auswahl von Testfällen berücksichtigt wird.

→ Vorwissen: Nachschlagewerk Klasse 8, **Algorithmen** und **Algorithmen und Programme testen**.
