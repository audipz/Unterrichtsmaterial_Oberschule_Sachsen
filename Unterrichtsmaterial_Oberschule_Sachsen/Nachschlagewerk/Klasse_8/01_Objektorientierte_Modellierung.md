# 1 Objektorientierte Modellierung

## Modelle helfen beim Verstehen

Informatiksysteme bilden häufig Ausschnitte der Wirklichkeit ab. Dabei werden nur die Merkmale berücksichtigt, die für eine Aufgabe wichtig sind. Ein solches vereinfachtes Abbild nennt man **Modell**.

Die objektorientierte Modellierung beschreibt einen Anwendungsbereich mit **Objekten**, **Klassen**, **Attributen** und **Methoden**.

## Objekt und Klasse

Ein **Objekt** ist ein konkretes Element, beispielsweise ein bestimmtes Fahrrad oder eine bestimmte Spielfigur. Eine **Klasse** beschreibt gemeinsame Merkmale gleichartiger Objekte.

![Klasse Fahrrad mit Attributen und Methoden sowie das konkrete Objekt fahrrad1](grafiken/objekt_klasse.svg)

Die Klasse `Fahrrad` legt fest, welche Attribute und Methoden Fahrräder im Modell besitzen. Ein konkretes Objekt wie `fahrrad1` übernimmt diese Struktur und besitzt eigene Attributwerte.

| Attribut | Wert |
|---|---|
| farbe | blau |
| gang | 3 |
| geschwindigkeit | 12 km/h |

## Methoden verändern Zustände

Methoden beschreiben mögliche Aktionen. Die Methode `schalten()` kann beispielsweise den Wert des Attributes `gang` verändern.

Der aktuelle Zustand eines Objekts wird durch seine Attributwerte beschrieben.

## Beziehungen

Objekte stehen häufig miteinander in Beziehung. Ein Objekt der Klasse `Schueler` kann beispielsweise einer `Klasse` zugeordnet sein. Solche Beziehungen sind wichtig, wenn ein Modell aus mehreren Objektarten besteht.

## Abstraktion

Bei der **Abstraktion** werden unwichtige Einzelheiten weggelassen. Für eine Schulbibliothek sind bei einem Buch beispielsweise Titel und Ausleihstatus wichtig, die Farbe des Papiers dagegen meist nicht.

> **Merke:** Ein gutes Modell enthält nicht möglichst viele Details, sondern die für die Aufgabe richtigen Details.

## Begriffe zum Nachschlagen

**Abstraktion:** bewusstes Weglassen unwichtiger Einzelheiten.

**Attribut:** Eigenschaft eines Objekts.

**Klasse:** gemeinsame Beschreibung gleichartiger Objekte.

**Methode:** mögliche Operation eines Objekts.

**Objekt:** konkretes Element einer modellierten Welt.

**Zustand:** Gesamtheit der aktuellen Attributwerte eines Objekts.
