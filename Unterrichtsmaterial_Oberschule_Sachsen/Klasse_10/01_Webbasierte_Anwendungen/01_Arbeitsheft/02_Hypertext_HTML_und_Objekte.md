# 02 – Hypertext, HTML und Objekte

## Leitfrage

> **Wie wird aus strukturiertem Text eine Webseite?**

Webseiten bestehen aus strukturierten Inhalten. Ein einfaches Dokument kann mit HTML beschrieben werden.

```html
<!doctype html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <title>Meine Seite</title>
</head>
<body>
  <h1>Willkommen</h1>
  <p>Das ist ein Absatz.</p>
  <a href="https://example.org">Ein Link</a>
</body>
</html>
```

## Hypertext

Hypertext verbindet Inhalte durch Verweise. Ein Link führt von einem Dokument, Abschnitt oder Medium zu einem anderen Ziel.

## Struktur und Gestaltung trennen

HTML beschreibt vor allem Struktur und Bedeutung. CSS kann die Gestaltung übernehmen.

```html
<p class="hinweis">Wichtige Information</p>
```

```css
.hinweis {
  font-weight: bold;
}
```

## Objektorientierte Sicht

Auch in einer Webseite können wir bekannte Begriffe wiederfinden:

- **Klasse:** z. B. alle Elemente mit der CSS-Klasse `hinweis`
- **Objekt:** ein konkreter Absatz auf der Seite
- **Attribut:** z. B. `href`, `class`, `src`
- **Attributwert:** z. B. `class="hinweis"`
- **Methode/Operation:** eine Aktion, die ein Element oder seinen Zustand verändert

Diese Sicht hilft, Strukturen zu analysieren. Sie ist ein Modell und nicht mit einer vollständigen objektorientierten Programmiersprache gleichzusetzen.

## Aufgabe 1

Markiere im HTML-Beispiel Überschrift, Absatz und Link.

## Aufgabe 2

Erstelle eine einfache Seite mit:

- einer Überschrift,
- zwei Absätzen,
- einer Liste,
- einem Link.

## Aufgabe 3

Nenne zu einem Link-Element ein mögliches Attribut und den zugehörigen Attributwert.

## Aufgabe 4

Erkläre, warum die Trennung von Inhalt und Gestaltung bei größeren Webseiten sinnvoll ist.

## Merksatz

> HTML strukturiert Inhalte. Links machen Dokumente zu Hypertext. Attribute beschreiben Eigenschaften von Elementen.
