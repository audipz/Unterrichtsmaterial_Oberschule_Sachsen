#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
PUBLISH_ROOT="$REPO_ROOT/.publish"
LEHRER_ROOT="$PUBLISH_ROOT/Lehrer"
ZIP_PATH="$PUBLISH_ROOT/Unterrichtsmaterial_Oberschule_Sachsen.zip"

rm -rf "$PUBLISH_ROOT"
mkdir -p "$LEHRER_ROOT"

copy_doc() {
  local src="$1"
  local rel="${src#$OUTPUT_ROOT/}"
  mkdir -p "$LEHRER_ROOT/$(dirname "$rel")"
  cp "$src" "$LEHRER_ROOT/$rel"
}

is_document() {
  case "$1" in
    *.pdf|*.docx|*.pptx) return 0 ;;
    *) return 1 ;;
  esac
}

is_class_7_to_10() {
  local rel="$1"
  [[ "$rel" == Klasse_7/* || "$rel" == Klasse_8/* || "$rel" == Klasse_9/* || "$rel" == Klasse_10/* ]]
}

document_part() {
  local base
  base="$(basename "$1")"
  base="${base%.pdf}"
  base="${base%.docx}"
  base="${base%.pptx}"
  printf '%s' "$base"
}

is_teacher_path() {
  local rel="$1" part
  is_class_7_to_10 "$rel" || return 1
  if [[ "$rel" == */05_Praesentationen/*.pptx ]]; then
    part="05_Praesentationen"
  else
    part="$(document_part "$rel")"
  fi
  case "$part" in
    01_Arbeitsheft|02_Lehrerband|03_Material|04_Loesungen|05_Praesentationen|06_Dateien|08_Bilder|09_Lernkontrollen|04_Leistungskontrollen|05_Leistungskontrollen) return 0 ;;
    *) return 1 ;;
  esac
}

lehrer_docs=0
lehrer_pptx=0

while IFS= read -r -d '' file; do
  is_document "$file" || continue
  rel="${file#$OUTPUT_ROOT/}"
  if is_teacher_path "$rel"; then
    copy_doc "$file"
    lehrer_docs=$((lehrer_docs + 1))
    [[ "$file" == *.pptx ]] && lehrer_pptx=$((lehrer_pptx + 1))
  fi
done < <(find "$OUTPUT_ROOT" -type f -print0)

[[ "$lehrer_docs" -gt 0 ]] || { echo "FEHLER: Keine Lehrerdokumente gefunden." >&2; exit 1; }
[[ "$lehrer_pptx" -gt 0 ]] || { echo "FEHLER: Keine PowerPoint-Masterdateien gefunden." >&2; exit 1; }

# Die vollständige Build-Ausgabe zusätzlich als ein direkt herunterladbares
# Archiv bereitstellen. Das ZIP liegt absichtlich außerhalb von Ausgabe/,
# damit es sich nicht selbst in das Archiv einschließt.
(
  cd "$OUTPUT_ROOT"
  zip -qr "$ZIP_PATH" .
)

[[ -s "$ZIP_PATH" ]] || { echo "FEHLER: ZIP-Ausgabe wurde nicht erzeugt." >&2; exit 1; }

echo "Lehrerartefakt: $lehrer_docs Unterrichtsdokumente, davon $lehrer_pptx PowerPoint-Präsentationen"
echo "ZIP-Ausgabe: $ZIP_PATH"
