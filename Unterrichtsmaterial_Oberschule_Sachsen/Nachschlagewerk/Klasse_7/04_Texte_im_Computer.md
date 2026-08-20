# 4 Texte im Computer

## Zeichen müssen codiert werden

Computer speichern Buchstaben nicht als kleine Bilder eines Buchstabens. Jedem Zeichen wird nach einer vereinbarten **Zeichencodierung** ein Zahlenwert zugeordnet. Dieser Zahlenwert kann anschließend binär gespeichert werden.

Vereinfacht entsteht folgende Kette:

```text
Zeichen → Codewert → Binärdarstellung → Speicherung
```

## Warum braucht man Standards?

Wenn zwei Systeme dieselben Daten unterschiedlich deuten, entstehen falsche Zeichen. Deshalb werden standardisierte Zeichencodierungen verwendet.

Ein historisch wichtiger Standard ist **ASCII**. Er enthält unter anderem lateinische Buchstaben, Ziffern und Steuerzeichen, reicht aber für die vielen Schriftsysteme der Welt nicht aus.

**Unicode** ordnet Zeichen aus sehr vielen Sprachen und Zeichensystemen eindeutige Codepunkte zu. **UTF-8** ist eine weit verbreitete Methode, Unicode-Zeichen als Bytes zu speichern.

## Zeichen, Schriftart und Darstellung

Die Codierung legt fest, **welches Zeichen** gemeint ist. Eine Schriftart legt dagegen fest, **wie dieses Zeichen aussieht**. Derselbe Buchstabe kann deshalb in unterschiedlichen Schriftarten verschieden aussehen, obwohl der gespeicherte Text gleich bleibt.

## Textdateien

Eine reine Textdatei speichert hauptsächlich Zeichen. Dokumentformate einer Textverarbeitung enthalten zusätzlich Informationen über Formatierungen, Bilder, Tabellen oder Seitenaufbau.

Die Dateiendung gibt häufig einen Hinweis auf das Format, beispielsweise `.txt`, `.odt` oder `.docx`.

## Dateigröße

Die Größe eines Textes hängt unter anderem von der Anzahl und Art der Zeichen sowie vom Dateiformat ab. Bei UTF-8 benötigen nicht alle Zeichen gleich viele Bytes. Ein formatiertes Dokument kann außerdem deutlich mehr Daten enthalten als derselbe Inhalt in einer einfachen Textdatei.

> **Merke:** Damit Text zuverlässig zwischen Geräten ausgetauscht werden kann, müssen Sender und Empfänger die verwendete Codierung verstehen.

## Begriffe zum Nachschlagen

**ASCII:** ältere Zeichencodierung mit einem begrenzten Zeichenvorrat.

**Codepunkt:** eindeutige Nummer eines Zeichens im Unicode-Standard.

**Unicode:** Standard zur eindeutigen Zuordnung sehr vieler Zeichen aus unterschiedlichen Schriftsystemen.

**UTF-8:** weit verbreitete Codierung von Unicode-Zeichen als Folge von Bytes.

**Zeichencodierung:** Regel, die Zeichen Zahlenwerten beziehungsweise Bitfolgen zuordnet.

→ Siehe auch **Kapitel 2: Informationen und Daten** und **Kapitel 9: Dateien, Ordner und Pfade**.
