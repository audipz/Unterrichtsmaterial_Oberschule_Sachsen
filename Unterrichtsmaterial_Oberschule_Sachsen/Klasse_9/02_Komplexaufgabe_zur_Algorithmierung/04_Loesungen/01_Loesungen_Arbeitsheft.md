# Lösungen zum Arbeitsheft

## 00 Einstieg

### Kakao-Anleitung

Typische Rückfragen von KAI:

- Welche Milch?
- Wie viel Milch?
- Wie viel Kakaopulver?
- Welcher Becher?
- Womit soll umgerührt werden?
- Wie lange soll umgerührt werden?
- Soll die Milch warm oder kalt sein?

Kernaussage:

Menschen ergänzen fehlende Informationen häufig aus Erfahrung. Ein Computer kann dies nur, wenn entsprechende Regeln oder Daten vorgesehen sind.

---

## 01 Entdecken

Typische Probleme der fehlerhaften Anleitung:

- falsche Reihenfolge,
- fehlende Voraussetzungen,
- ungenaue Mengen,
- nicht eindeutig beschriebene Handlungsschritte.

Eine mögliche Verbesserung:

1. Stelle einen leeren Becher auf den Tisch.
2. Fülle 250 ml Milch in den Becher.
3. Gib zwei gestrichene Teelöffel Kakaopulver hinzu.
4. Rühre 20 Sekunden mit einem Löffel um.

---

## 02 Erarbeiten

### Eigenschaften guter Algorithmen

- **eindeutig:** Jede Anweisung lässt möglichst nur eine sinnvolle Interpretation zu.
- **vollständig:** Alle für die Lösung notwendigen Schritte und Informationen sind vorhanden.
- **richtige Reihenfolge:** Die Handlungsschritte bauen logisch aufeinander auf.
- **ausführbar:** Jeder einzelne Schritt kann vom vorgesehenen Ausführenden tatsächlich durchgeführt werden.

### Zuordnung

„Gib etwas Zucker dazu.“

→ nicht eindeutig

„Backe den Kuchen.“ ohne weitere Anweisungen

→ nicht vollständig

„Setze den Deckel auf.“ bevor das Gefäß gefüllt wurde

→ falsche Reihenfolge

„Hole den Regenbogen.“

→ nicht ausführbar

---

## 03 Merke

Merksatz:

> Ein Algorithmus ist eine eindeutige, vollständige und ausführbare Folge von Handlungsschritten zur Lösung eines Problems.

Die Handlungsschritte müssen in einer sinnvollen Reihenfolge stehen.

---

## 04 Beispiele

### Zähneputzen

Grundsätzlich als Algorithmus geeignet, wenn notwendige Schritte eindeutig und vollständig beschrieben werden.

### Ampel überqueren

Die einfache lineare Version funktioniert nur für eine entsprechend vereinfachte Situation. In der Realität hängt der Ablauf von Bedingungen ab. Dies ist eine gute Überleitung zu Verzweigungen.

### Schulranzen packen

Die Beschreibung ist nur dann vollständig, wenn eindeutig feststeht, welche Materialien benötigt werden.

### „Mach dich fertig. Beeile dich. Tu das Richtige.“

Kein geeigneter Algorithmus:

- mehrdeutig,
- nicht operationalisiert,
- nicht eindeutig ausführbar.

---

## 05 Alltag und Computer

### Mensch oder Computer

„Kann fehlende Informationen ergänzen.“

→ eher Mensch

„Führt beschriebene Anweisungen exakt aus.“

→ Computer

„Erkennt aus Erfahrung häufig, was gemeint ist.“

→ Mensch

„Benötigt explizit beschriebene Regeln.“

→ Computer

Wichtig:

Moderne KI-Systeme können ebenfalls plausible Ergänzungen erzeugen. Das ändert jedoch nicht die grundlegende didaktische Aussage: Ein klassisches Programm arbeitet nach den vorgesehenen Daten, Regeln und Algorithmen.

---

## 06 Scratch HowTo

Erwartetes Minimalprogramm:

```text
Wenn grüne Flagge angeklickt
sage [Willkommen auf unserem Schulfest!]
```

Mögliche Erweiterung:

```text
Wenn grüne Flagge angeklickt
sage [Willkommen auf unserem Schulfest!] für 2 Sekunden
sage [Ich bin KAI.] für 2 Sekunden
```

---

## 07 Python HowTo

Minimalprogramm:

```python
print("Willkommen auf unserem Schulfest!")
```

Erweiterung:

```python
print("Willkommen auf unserem Schulfest!")
print("Ich bin KAI.")
print("Viel Spaß!")
```

---

## 08 Übungen

### Aufgabe 1

A: Algorithmus

B: kein geeigneter Algorithmus; die Schritte sind nicht eindeutig ausführbar.

C: Algorithmus

### Aufgabe 2

Mögliche Probleme:

- Umrühren erfolgt vor dem Bereitstellen der Zutaten.
- Der Becher wird zu spät genannt.
- Reihenfolge ist ungeeignet.
- Informationen können fehlen.

### Aufgabe 3

Mögliche Reihenfolge:

1. Starte den Computer.
2. Schalte den Monitor ein.
3. Melde dich mit deinem Benutzerkonto an.
4. Öffne die benötigte Anwendung.
5. Bearbeite die Aufgabe.

Je nach Hardware kann das Einschalten des Monitors auch vor dem Start des Computers erfolgen. Wichtig ist die begründete Entscheidung.

### Aufgabe 4

Mögliche Ergänzungen:

- Adresse auf den Umschlag schreiben,
- Brief ausreichend frankieren,
- Umschlag verschließen,
- geeigneten Briefkasten aufsuchen.

### Aufgabe 5

Beispiel A:

„Nimm das Arbeitsblatt `Algorithmus_01` vom oberen rechten Stapel.“

Beispiel B:

„Gehe zur geschlossenen Tür neben der Tafel.“

Beispiel C:

„Gib 200 ml Wasser in den Messbecher.“

Beispiel D:

„Warte 30 Sekunden.“

### Aufgabe 6

Eine mögliche Lösung:

1. Stelle die leere Trinkflasche unter den Wasserhahn.
2. Öffne den Deckel der Trinkflasche.
3. Öffne den Wasserhahn.
4. Fülle die Flasche bis zur gewünschten Markierung.
5. Schließe den Wasserhahn.
6. Nimm die Flasche weg.
7. Verschließe die Flasche.

### Aufgabe 8

Algorithmus B ist eindeutiger und vollständiger.

Algorithmus B ist dennoch nicht automatisch für jede Situation optimal. Beispielsweise ist „etwa 2 cm unter den Rand“ weiterhin situationsabhängig.

### Aufgabe 9

Gleich bleibt:

- Problem,
- Reihenfolge,
- beabsichtigte Wirkung.

Anders ist:

- Syntax bzw. Darstellung,
- Art der Programmbefehle.

### Aufgabe 10

Wenn KAI exakt ausgeführt hat, liegt der Fehler wahrscheinlich im Algorithmus, in den Anforderungen oder in den verwendeten Daten.

### Plus – Effizienz

Weniger Schritte bedeuten nicht automatisch einen besseren Algorithmus.

Weitere Kriterien:

- Verständlichkeit,
- Korrektheit,
- Robustheit,
- Erweiterbarkeit,
- notwendige Voraussetzungen.

---

## 09 Transfer

Offene Lösungen.

Bewertungsschwerpunkte:

- nachvollziehbare Problemanalyse,
- sinnvoller Algorithmus,
- Testmöglichkeit,
- begründete Verbesserung.

---

## 10 Projekt

Es existiert keine einzige Musterlösung.

Wichtig ist der Entwicklungsprozess:

1. Problem verstehen,
2. Lösung entwerfen,
3. Algorithmus beschreiben,
4. testen,
5. Rückmeldung auswerten,
6. verbessern,
7. Ergebnis vorstellen.

---

## 11 Reflexion

Keine Musterlösung.

Die Antworten dienen der Selbsteinschätzung.

---

## 12 Kompetenzcheck

### Aufgabe 1

A: Algorithmus

B: kein geeigneter Algorithmus

### Aufgabe 2

Mindestens zwei nachvollziehbar begründete Probleme.

### Aufgabe 3

Offene Lösung anhand der vier Qualitätsmerkmale bewerten.

### Aufgabe 4

Kernaussage:

> Derselbe Algorithmus kann in unterschiedlichen Programmiersprachen umgesetzt werden. Die Programmiersprache ist ein Werkzeug zur Formulierung bzw. Ausführung der Lösung.
