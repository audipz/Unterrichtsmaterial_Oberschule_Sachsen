# Manifestgesteuerte Dokumentexporte

Die Dokumenterzeugung ist vom normalen Push-Build entkoppelt. Ein Dokumentexport wird explizit über den GitHub-Actions-Workflow `Dokumentexport` gestartet.

## Architektur

- `Build/build.sh` bleibt der Renderer für PDF, DOCX, HTML und vorhandene PPTX-Dateien.
- `Build/export-manifests/*.json` beschreiben, welche Bereiche und Ausgabearten zu einem Export gehören.
- `Build/run_export.py` baut die benötigten Bereiche, filtert die erzeugten Dateien anhand des Manifests und erstellt ein ZIP unter `.publish/`.
- Das ZIP enthält zusätzlich `export-manifest.json` mit den wesentlichen Metadaten des erzeugten Exports.
- Normale Pushes erzeugen keine Dokumente. `Export-Manifeste prüfen` validiert lediglich Python-Syntax und Manifeste und benötigt weder Pandoc noch LaTeX.

## Vorhandene Profile

- `complete`: vollständiger Export aller Bereiche und Formate.
- `teacher`: Lehrer-Zusatzmaterialien wie Lehrerband, Material, Lösungen, Präsentationen und Kontrollen.
- `student`: Arbeitshefte, Nachschlagewerke, Grundlagen und Werkzeuge ohne Lehrer-/Lösungsinhalte.

## Neues Exportprofil

Ein neues Profil benötigt nur eine neue Datei unter `Build/export-manifests/`, zum Beispiel `gymnasium.json`. Der Dokumentexport-Workflow muss dafür nicht geändert werden.

Pflichtfelder:

```json
{
  "schemaVersion": 1,
  "id": "beispiel",
  "title": "Beispielexport",
  "audience": ["TEACHER"],
  "areas": ["Klasse_7"],
  "formats": ["pdf", "docx"],
  "include": ["Klasse_7/**/*"],
  "exclude": [],
  "minimumFiles": 1,
  "artifact": "Beispiel.zip"
}
```

`include` und `exclude` arbeiten auf den relativen Pfaden unter `Ausgabe/`.

## Lokaler Aufruf

```bash
python3 Build/validate_exports.py
python3 Build/run_export.py Build/export-manifests/teacher.json
```

Ist `Ausgabe/` bereits vollständig gebaut, kann nur neu paketiert werden:

```bash
python3 Build/run_export.py Build/export-manifests/teacher.json --package-only
```

## Nächster Architektur-Schritt

Die Export-Manifeste sind bewusst von der heutigen Verzeichnisstruktur getrennt gedacht. Beim späteren Umbau auf einen zentralen Content-Pool kann der Renderer aus Pool-Items statt direkt aus den bisherigen Verzeichnissen gespeist werden. Die Exportebene und die Downloadprofile können dabei bestehen bleiben.
