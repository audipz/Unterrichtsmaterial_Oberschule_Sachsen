# Zentraler Content-Pool

Der Content-Pool enthält fachlich stabile Inhalte und Ressourcen. Export- und Curriculum-Manifeste sollen langfristig nur `contentKey`-Werte referenzieren und keine Repository-Pfade kennen.

## Trennung

- **Content-Pool**: Was existiert fachlich?
- **Curriculum-Manifest**: Welche Inhalte gehören für Schulart, Bundesland, Jahrgang und Sprache zusammen?
- **Export-Manifest**: Welche Ausgabe soll für welche Zielgruppe erzeugt werden?

## Katalog

`catalog.json` ist der zentrale Resolververtrag. Ein Eintrag kann Quellen, geschützte Ressourcen oder weitere Pool-Einträge als `children` enthalten.

Beispiel:

```json
{
  "contentKey": "informatik.algorithmen.schleifen",
  "type": "TOPIC",
  "sources": [
    {
      "path": "ContentPool/informatik/algorithmen/schleifen.md",
      "outputs": ["Klasse_*/**/Schleifen.*"]
    }
  ],
  "resources": [
    {
      "path": "ContentPool/informatik/algorithmen/loesung.pdf",
      "audience": "TEACHER",
      "outputs": ["Klasse_*/**/Loesung_Schleifen.pdf"]
    }
  ]
}
```

`path` bleibt intern. Ein Curriculum- oder Export-Manifest sieht nur `informatik.algorithmen.schleifen`.

## Migration

Die vorhandenen Unterrichtsmaterial-Verzeichnisse bleiben während der Migration bestehen. Neue oder migrierte Inhalte erhalten einen stabilen `contentKey` und werden im Katalog registriert. Danach können ihre physischen Pfade geändert werden, ohne referenzierende Manifeste anzupassen.

Legacy-Exportprofile mit `include`-Globs bleiben vorübergehend funktionsfähig. Neue Profile sollen `contentKeys` verwenden.
