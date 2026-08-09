# Publishing Build v2

## Änderungen

- generierte Dateien werden nicht mehr auf `main` committed
- GitHub Pages veröffentlicht direkt aus einem Actions-Artefakt
- DOCX beginnt mit Titelseite
- Inhaltsverzeichnis beginnt auf einer neuen Seite
- H1-Kapitel beginnen auf einer neuen Seite
- zentrales `reference.docx`
- HTML erhält ein einheitliches Stylesheet
- PDF wird mit XeLaTeX erzeugt
- automatisch erzeugte Download-Startseite `Ausgabe/index.html`

## Einmalige Migration

Nach dem Einspielen:

```bash
./Build/setup_untracked_output.sh
git status
```

Danach:

```bash
git add .gitignore Build .github/workflows/build.yml
git add -u Ausgabe
git commit -m "feat(build): Publishing-Pipeline mit Pages, Titelseiten und PDF eingeführt"
git push origin main
```

## GitHub Pages

Im Repository einmal unter

`Settings → Pages → Build and deployment → Source`

**GitHub Actions** auswählen.

Danach veröffentlicht jeder erfolgreiche Push auf `main` die erzeugten DOCX-, PDF- und HTML-Dateien über GitHub Pages.
