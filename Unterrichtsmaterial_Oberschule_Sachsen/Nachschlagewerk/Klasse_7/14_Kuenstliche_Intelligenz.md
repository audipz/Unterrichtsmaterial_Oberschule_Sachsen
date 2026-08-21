# 14 Künstliche Intelligenz

## Was bedeutet Künstliche Intelligenz?

**Künstliche Intelligenz (KI)** ist ein Sammelbegriff für Informatikverfahren, mit denen Systeme Aufgaben bearbeiten, für die bei Menschen Fähigkeiten wie Erkennen, Entscheiden, Lernen oder Sprachverarbeitung nötig erscheinen.

KI ist also **kein einzelnes Programm** und auch keine bestimmte Maschine. Sehr unterschiedliche Verfahren werden unter diesem Begriff zusammengefasst.

Wichtig ist außerdem: Wenn ein KI-System eine Aufgabe gut löst, folgt daraus nicht, dass es wie ein Mensch denkt, versteht oder bewusst handelt.

## Wo begegnet KI im Alltag?

KI-Verfahren können beispielsweise eingesetzt werden bei:

- Sprachassistenten und Spracherkennung,
- automatischer Übersetzung,
- Erkennung von Gegenständen auf Bildern,
- Empfehlungen für Musik oder Videos,
- Erkennung unerwünschter E-Mails,
- Fahrerassistenzsystemen,
- Suchmaschinen,
- medizinischer Bildauswertung,
- generativer KI für Texte, Bilder, Audio oder Programmcode.

Nicht jede Automatisierung ist jedoch KI. Ein einfacher Taschenrechner oder eine Zeitschaltuhr arbeitet ebenfalls automatisch, wird deshalb aber nicht automatisch zu einem KI-System.

## Klassische Programme und lernende Systeme

Bei einem klassischen einfachen Programm legt ein Mensch Regeln direkt fest.

Vereinfacht:

```text
Daten + vom Menschen festgelegte Regeln → Ergebnis
```

Beispiel:

```text
WENN temperatur < 0
DANN frostwarnung ausgeben
```

Bei vielen Verfahren des **maschinellen Lernens** werden Regeln beziehungsweise Muster nicht vollständig einzeln von Menschen formuliert. Stattdessen wird ein Modell anhand vieler Beispieldaten angepasst.

Vereinfacht:

```text
Trainingsdaten → Training → Modell
Modell + neue Eingabe → Vorhersage/Ergebnis
```

Diese Gegenüberstellung ist bewusst vereinfacht: Auch lernende Systeme werden von Menschen entwickelt, programmiert, ausgewählt und bewertet.

## Maschinelles Lernen

**Maschinelles Lernen** ist ein Teilgebiet der KI. Dabei werden Modelle mithilfe von Daten so angepasst, dass sie Muster erkennen und auf neue Daten anwenden können.

Ein Bilderkennungssystem könnte beispielsweise viele Bilder erhalten, die mit `Katze` oder `Hund` beschriftet sind. Während des Trainings werden innere Modellparameter angepasst.

Später erhält das trainierte Modell ein neues Bild und berechnet beispielsweise, welche Kategorie wahrscheinlicher passt.

Das System hat dabei nicht einfach alle Katzenbilder auswendig als Liste gespeichert. Es verwendet während des Trainings gelernte statistische Zusammenhänge.

## Trainingsdaten

Die verwendeten Daten sind für ein lernendes System sehr wichtig.

Trainingsdaten sollten möglichst:

- zur Aufgabe passen,
- ausreichend vielfältig sein,
- korrekt beziehungsweise sinnvoll aufbereitet sein,
- problematische Verzerrungen möglichst vermeiden.

Sind bestimmte Fälle in den Daten kaum vertreten, kann das Modell gerade bei diesen Fällen schlechter funktionieren.

> **Merke:** Ein KI-Modell lernt nicht automatisch „die Wahrheit“. Es wird anhand von Daten und einem festgelegten Verfahren angepasst.

## Training und Nutzung unterscheiden

Zwei Phasen sollte man auseinanderhalten.

### Training

Beim **Training** wird das Modell anhand von Daten angepasst. Das kann sehr rechenaufwendig sein.

### Anwendung beziehungsweise Inferenz

Danach kann das trainierte Modell neue Eingaben verarbeiten. Diese Nutzung eines trainierten Modells wird häufig **Inferenz** genannt.

Beispiel:

```text
Training:
viele Beispielbilder → Modell wird angepasst

Anwendung:
neues Bild → trainiertes Modell → geschätzte Kategorie
```

## Wahrscheinlichkeit statt Gewissheit

Viele KI-Systeme berechnen keine unumstößliche Wahrheit, sondern Wahrscheinlichkeiten oder statistisch passende Ergebnisse.

Ein Bilderkennungssystem könnte intern beispielsweise zu dem Ergebnis kommen, dass eine Kategorie deutlich besser zu einem Bild passt als andere Kategorien. Trotzdem kann die Entscheidung falsch sein.

Auch ein sehr selbstsicher formulierter Text einer generativen KI ist deshalb kein Beweis dafür, dass die Aussage stimmt.

## Generative KI

**Generative KI** kann neue Inhalte erzeugen, beispielsweise:

- Texte,
- Bilder,
- Musik oder andere Audiodaten,
- Videos,
- Programmcode.

Sie erzeugt Ausgaben anhand von Mustern, die das zugrunde liegende Modell während seiner Entwicklung beziehungsweise seines Trainings gelernt hat.

### Sprachmodelle

Ein **Sprachmodell** verarbeitet sprachliche Einheiten und berechnet, welche Fortsetzung in einem gegebenen Zusammenhang statistisch passend ist. Moderne große Sprachmodelle können dadurch unter anderem Fragen beantworten, Texte zusammenfassen, formulieren oder Programmcode erzeugen.

Das bedeutet jedoch nicht, dass jede erzeugte Aussage in einer verlässlichen Wissensdatenbank nachgeschlagen wurde.

## Warum kann generative KI falsche Dinge behaupten?

Generative Modelle erzeugen passende Ausgaben. Dabei können auch scheinbar plausible, aber falsche Informationen entstehen.

Beispiele:

- eine nicht existierende Quelle,
- ein falsches Datum,
- eine erfundene Person oder Aussage,
- fehlerhafter Programmcode,
- eine überzeugend klingende, aber falsche Erklärung.

Solche erfundenen oder sachlich falschen Ausgaben werden bei generativer KI häufig als **Halluzinationen** bezeichnet.

> **Merke:** Gute Sprache und sichere Formulierungen sind kein Beweis für sachliche Richtigkeit.

## Prompt und Kontext

Eine Eingabe an ein generatives KI-System wird häufig **Prompt** genannt.

Ein Prompt kann beispielsweise enthalten:

- eine Frage,
- eine Aufgabe,
- Hintergrundinformationen,
- gewünschte Form oder Zielgruppe.

Eine genauere Eingabe kann dem System helfen, eine passendere Antwort zu erzeugen. Trotzdem garantiert auch ein sehr guter Prompt keine richtige Antwort.

## KI-Ergebnisse prüfen

Bei wichtigen Informationen sollte man die Ausgabe nicht einfach übernehmen.

Hilfreiche Prüfungen sind:

1. Passt die Antwort überhaupt zur Frage?
2. Sind Zahlen und Rechnungen nachvollziehbar?
3. Lassen sich wichtige Aussagen mit zuverlässigen Quellen bestätigen?
4. Existieren genannte Quellen tatsächlich?
5. Wurden möglicherweise wichtige Gegenargumente oder Bedingungen ausgelassen?
6. Ist das Ergebnis aktuell genug für die Fragestellung?

Bei wichtigen Entscheidungen reicht es nicht, lediglich dieselbe KI noch einmal zu fragen. Eine unabhängige verlässliche Quelle ist wertvoller.

## KI als Werkzeug

KI kann beim Lernen und Arbeiten unterstützen, beispielsweise beim:

- Erklären eines unbekannten Begriffs,
- Finden von Formulierungsvarianten,
- Strukturieren eigener Gedanken,
- Erzeugen zusätzlicher Übungsbeispiele,
- Überarbeiten eines selbst geschriebenen Textes,
- Erklären von Programmcode.

Dabei sollte man weiterhin selbst verstehen, was im Ergebnis steht.

Ein fertiger Text, den man nicht erklären kann, ist kein guter Ersatz für eigenes Lernen.

## Datenschutz und vertrauliche Informationen

Bevor Daten in einen KI-Dienst eingegeben oder hochgeladen werden, sollte geprüft werden, ob sie dort verarbeitet werden dürfen.

Besonders vorsichtig sollte man sein bei:

- vollständigen Namen und Kontaktdaten,
- privaten Nachrichten,
- Fotos anderer Personen,
- Gesundheitsdaten,
- Zugangsdaten und Passwörtern,
- internen Dokumenten,
- noch nicht veröffentlichten Arbeiten,
- vertraulichen Informationen anderer Menschen.

Welche Daten ein Dienst speichert und wofür er sie verwendet, hängt vom jeweiligen Anbieter, Tarif, Vertrag und den Einstellungen ab.

→ Siehe **Kapitel 12: Daten verantwortungsvoll nutzen**.

## Verzerrungen und Fairness

Trainingsdaten können gesellschaftliche Ungleichheiten, Vorurteile oder unausgewogene Verteilungen enthalten. Ein Modell kann solche Muster übernehmen oder verstärken.

Das wird häufig mit dem englischen Begriff **Bias** bezeichnet.

Deshalb sollte bei KI-Ergebnissen auch gefragt werden:

- Werden bestimmte Gruppen systematisch schlechter behandelt?
- Waren die Trainings- oder Prüfdaten ausreichend vielfältig?
- Ist die Verwendung des Systems für diesen Zweck überhaupt angemessen?

## Urheberrecht und Lizenzen

Auch bei KI-erzeugten oder mit KI bearbeiteten Inhalten können rechtliche Fragen entstehen. Welche Nutzung erlaubt ist, hängt vom konkreten Inhalt, Dienst und Anwendungsfall ab.

Außerdem darf KI nicht dazu verleiten, Quellenangaben zu erfinden oder fremde Inhalte ohne Prüfung als eigene Arbeit auszugeben.

Bei schulischen Arbeiten gelten zusätzlich die Regeln der Schule beziehungsweise der jeweiligen Aufgabe.

## Deepfakes und künstlich erzeugte Medien

KI kann realistisch wirkende Bilder, Stimmen oder Videos erzeugen und verändern. Solche Inhalte können kreativ genutzt, aber auch zur Täuschung eingesetzt werden.

Ein **Deepfake** ist ein künstlich erzeugter oder veränderter Medieninhalt, der beispielsweise den Eindruck erwecken kann, eine Person habe etwas gesagt oder getan, obwohl dies nicht geschehen ist.

Deshalb gilt auch für Bilder, Audio und Videos:

```text
realistisch aussehend ≠ automatisch echt
```

Bei zweifelhaften Inhalten sind Quelle, ursprünglicher Kontext und unabhängige Bestätigung wichtig.

## Menschliche Verantwortung bleibt wichtig

Ein KI-System kann Vorschläge liefern oder Teil einer automatisierten Entscheidung sein. Verantwortung verschwindet dadurch nicht.

Menschen müssen unter anderem entscheiden:

- ob ein KI-System für eine Aufgabe geeignet ist,
- welche Daten verwendet werden dürfen,
- wie Ergebnisse geprüft werden,
- welche Folgen Fehler haben können,
- wann eine menschliche Entscheidung notwendig ist.

Je größer die möglichen Folgen eines Fehlers sind, desto wichtiger sind sorgfältige Prüfung und geeignete Kontrolle.

## KI sinnvoll in der Schule verwenden

Bei schulischen Aufgaben sollte zuerst geklärt werden, ob und wie KI verwendet werden darf.

Eine sinnvolle Arbeitsweise kann sein:

1. Aufgabe selbst verstehen.
2. Eigene Gedanken oder Lösungsansätze entwickeln.
3. KI gezielt als Werkzeug einsetzen, wenn dies erlaubt ist.
4. Ergebnis fachlich prüfen.
5. Fehler und ungeeignete Formulierungen korrigieren.
6. Verwendete Quellen selbst prüfen.
7. KI-Nutzung transparent machen, wenn dies verlangt wird.
8. Nur Inhalte abgeben, die man selbst versteht und erklären kann.

> **Merke:** KI kann ein leistungsfähiges Werkzeug sein. Sie liefert aber weder automatisch Wahrheit noch übernimmt sie Verantwortung für das Ergebnis.

## Begriffe zum Nachschlagen

**Bias:** systematische Verzerrung in Daten, Modellen oder Ergebnissen.

**Deepfake:** künstlich erzeugter oder manipulierter Medieninhalt, der einen echten Vorgang überzeugend vortäuschen kann.

**generative KI:** KI-System, das neue Inhalte wie Text, Bild, Audio oder Programmcode erzeugen kann.

**Halluzination:** plausibel wirkende, aber sachlich falsche oder erfundene Ausgabe eines generativen KI-Systems.

**Inferenz:** Anwendung eines trainierten Modells auf neue Eingaben.

**KI-Modell:** mathematisch beziehungsweise algorithmisch beschriebenes Modell, das zur Bearbeitung bestimmter Aufgaben verwendet wird.

**Künstliche Intelligenz:** Sammelbegriff für Informatikverfahren zur automatisierten Bearbeitung von Aufgaben, die mit Fähigkeiten wie Erkennen, Lernen, Entscheiden oder Sprachverarbeitung verbunden werden.

**maschinelles Lernen:** Teilgebiet der KI, bei dem Modelle anhand von Daten angepasst werden.

**Prompt:** Eingabe beziehungsweise Anweisung an ein generatives KI-System.

**Sprachmodell:** Modell zur Verarbeitung und Erzeugung sprachlicher Daten.

**Trainingsdaten:** Daten, anhand derer ein lernendes System beziehungsweise Modell angepasst wird.

→ Siehe auch **Kapitel 2: Informationen und Daten**, **Kapitel 12: Daten verantwortungsvoll nutzen** und **Kapitel 13: Werkzeuge**.