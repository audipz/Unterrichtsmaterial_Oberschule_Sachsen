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

is_student_path() {
  local rel="$1"
  [[ "$rel" == Klasse_7/* || "$rel" == Klasse_8/* || "$rel" == Klasse_9/* || "$rel" == Klasse_10/* ]] || return 1

  case "/$rel/" in
    */01_Arbeitsheft/*|*/03_Material/*) return 0 ;;
    *) return 1 ;;
  esac
}

is_teacher_path() {
  local rel="$1"
  [[ "$rel" == Klasse_7/* || "$rel" == Klasse_8/* || "$rel" == Klasse_9/* || "$rel" == Klasse_10/* ]] || return 1

  case "/$rel/" in
    */01_Arbeitsheft/*|*/02_Lehrerband/*|*/03_Material/*|*/04_Loesungen/*|*/05_Praesentationen/*|*/09_Lernkontrollen/*|*/04_Leistungskontrollen/*) return 0 ;;
    *) return 1 ;;
  esac
}

while IFS= read -r -d '' file; do
  is_document "$file" || continue
  rel="${file#$OUTPUT_ROOT/}"

  if is_student_path "$rel"; then
    copy_doc "$file" "$SCHUELER_ROOT"
  fi

  if is_teacher_path "$rel"; then
    copy_doc "$file" "$LEHRER_ROOT"
  fi
done < <(find "$OUTPUT_ROOT" -type f -print0)

cat > "$SCHUELER_ROOT/README.txt" <<'EOF'
Schülerunterlagen Informatik Klassen 7–10

Enthalten sind nur für Schülerinnen und Schüler benötigte Dokumente:
- Arbeitshefte
- Arbeits- und Zusatzmaterialien

Nicht enthalten sind Lösungen, Lehrerhinweise, interne Dokumentation, Quellenverwaltung oder Lernkontroll-Lösungen.
EOF

cat > "$LEHRER_ROOT/README.txt" <<'EOF'
Lehrerunterlagen Informatik Klassen 7–10

Enthalten sind die für den Unterricht benötigten Dokumente:
- Arbeitshefte
- Lehrerband
- Materialien
- Lösungen
- Präsentationsunterlagen
- Leistungskontrollen und Erwartungshorizonte

Nicht enthalten sind interne Audits, Entwicklungsdokumentation, Quellenverwaltung und technische JSON-Metadaten.
EOF

schueler_count="$(find "$SCHUELER_ROOT" -type f | wc -l | tr -d ' ')"
lehrer_count="$(find "$LEHRER_ROOT" -type f | wc -l | tr -d ' ')"

echo "Schülerartefakt: $schueler_count Dateien"
echo "Lehrerartefakt: $lehrer_count Dateien"
