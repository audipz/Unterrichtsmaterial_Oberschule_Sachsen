#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
PUBLISH_ROOT="$REPO_ROOT/.publish"
SCHUELER_ROOT="$PUBLISH_ROOT/Schueler"
LEHRER_ROOT="$PUBLISH_ROOT/Lehrer"

rm -rf "$PUBLISH_ROOT"
mkdir -p "$SCHUELER_ROOT" "$LEHRER_ROOT"

copy_doc() {
  local src="$1"
  local target_root="$2"
  local rel="${src#$OUTPUT_ROOT/}"
  mkdir -p "$target_root/$(dirname "$rel")"
  cp "$src" "$target_root/$rel"
}

is_document() {
  case "$1" in
    *.pdf|*.docx) return 0 ;;
    *) return 1 ;;
  esac
}

is_class_7_to_10() {
  local rel="$1"
  [[ "$rel" == Klasse_7/* || "$rel" == Klasse_8/* || "$rel" == Klasse_9/* || "$rel" == Klasse_10/* ]]
}

document_part() {
  local rel="$1"
  local base
  base="$(basename "$rel")"
  base="${base%.pdf}"
  base="${base%.docx}"
  printf '%s' "$base"
}

is_student_path() {
  local rel="$1"
  local part
  is_class_7_to_10 "$rel" || return 1
  part="$(document_part "$rel")"

  # Schülerartefakt: ausschließlich Unterrichtsdokumente für Schülerinnen und Schüler.
  # Keine Leistungskontrollen, Lösungen oder Lehrerunterlagen.
  case "$part" in
    01_Arbeitsheft|03_Material|06_Dateien|08_Bilder) return 0 ;;
    *) return 1 ;;
  esac
}

is_teacher_path() {
  local rel="$1"
  local part
  is_class_7_to_10 "$rel" || return 1
  part="$(document_part "$rel")"

  # Lehrerartefakt: sämtliche für die Unterrichtsdurchführung benötigten Dokumente.
  case "$part" in
    01_Arbeitsheft|02_Lehrerband|03_Material|04_Loesungen|05_Praesentationen|06_Dateien|08_Bilder|09_Lernkontrollen|04_Leistungskontrollen) return 0 ;;
    *) return 1 ;;
  esac
}

schueler_docs=0
lehrer_docs=0

while IFS= read -r -d '' file; do
  is_document "$file" || continue
  rel="${file#$OUTPUT_ROOT/}"

  if is_student_path "$rel"; then
    copy_doc "$file" "$SCHUELER_ROOT"
    schueler_docs=$((schueler_docs + 1))
  fi

  if is_teacher_path "$rel"; then
    copy_doc "$file" "$LEHRER_ROOT"
    lehrer_docs=$((lehrer_docs + 1))
  fi
done < <(find "$OUTPUT_ROOT" -type f -print0)

# Keine README-, Audit-, Quellen- oder sonstigen Verwaltungsdateien in den
# Veröffentlichungsartefakten. Die ZIPs enthalten ausschließlich Unterrichtsdokumente.

if [[ "$schueler_docs" -eq 0 ]]; then
  echo "FEHLER: Keine Schülerdokumente für das Veröffentlichungsartefakt gefunden." >&2
  exit 1
fi

if [[ "$lehrer_docs" -eq 0 ]]; then
  echo "FEHLER: Keine Lehrerdokumente für das Veröffentlichungsartefakt gefunden." >&2
  exit 1
fi

echo "Schülerartefakt: $schueler_docs Unterrichtsdokumente"
echo "Lehrerartefakt: $lehrer_docs Unterrichtsdokumente"
