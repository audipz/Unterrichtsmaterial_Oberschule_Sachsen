# Quellen – 03 Texte im Computer

## Fachliche Referenzen

- The Unicode Consortium: Unicode Standard und Code Charts
- RFC 3629: UTF-8, a transformation format of ISO 10646
- ANSI X3.4 / ASCII als historischer Standard für Zeichenkodierung

## Didaktische Reduktion

Für Klasse 7 wird bewusst unterschieden zwischen:

- **Zeichencodierung**: Zuordnung von Zeichen zu Zahlen,
- **Unicode**: universeller Zeichenvorrat mit Codepunkten,
- **UTF-8**: konkrete Codierung dieser Codepunkte in Bytes.

Die interne Bitstruktur von UTF-8 ist nicht Bestandteil der Stunde.

## Hinweise

Konkrete ASCII-Zahlenwerte dienen nur als Beispiele und müssen nicht auswendig gelernt werden. Zentral ist das Prinzip:

```text
Zeichen → Zahl → Binärdarstellung → Bits
```

Die Angabe zum typischen UTF-8-Speicherbedarf (`A` 1 Byte, `ä` 2 Byte, `€` 3 Byte, `😊` 4 Byte) dient der Veranschaulichung unterschiedlich langer Codierungen.