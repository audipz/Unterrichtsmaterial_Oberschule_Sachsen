#!/usr/bin/env bash
set -euo pipefail

DRY_RUN=0
EXPLICIT_ROOT=""

while [[ $# -gt 0 ]]; do
  case "$1" in
    --dry-run|-n)
      DRY_RUN=1
      shift
      ;;
    --root)
      [[ $# -ge 2 ]] || { echo "Fehler: --root benötigt einen Pfad." >&2; exit 2; }
      EXPLICIT_ROOT="$2"
      shift 2
      ;;
    --help|-h)
      cat <<'EOF'
Verwendung:
  migrate_lernkontrollen_json.sh [--dry-run] [--root PFAD]

Ziel:
  In allen Ordnern namens 09_Lernkontrollen werden .json-Dateien
  in den Unterordner json/ verschoben.

Beispiel vorher:
  09_Lernkontrollen/
  ├── 01_Test.md
  ├── 01_Test.json
  └── README.md

Nachher:
  09_Lernkontrollen/
  ├── 01_Test.md
  ├── README.md
  └── json/
      └── 01_Test.json

Optionen:
  --dry-run, -n   Nur anzeigen, nichts verändern.
  --root PFAD     Repository-Root explizit angeben.
  --help, -h      Hilfe anzeigen.
EOF
      exit 0
      ;;
    *)
      echo "Unbekannte Option: $1" >&2
      exit 2
      ;;
  esac
done

if [[ -n "$EXPLICIT_ROOT" ]]; then
  REPO_ROOT="$(cd "$EXPLICIT_ROOT" && pwd)"
else
  REPO_ROOT="$(git rev-parse --show-toplevel 2>/dev/null || true)"
  if [[ -z "$REPO_ROOT" ]]; then
    echo "Fehler: Kein Git-Repository gefunden." >&2
    echo "Starte das Skript innerhalb des Repositories oder verwende --root PFAD." >&2
    exit 1
  fi
fi

if [[ -d "$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen" ]]; then
  SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
else
  SOURCE_ROOT="$REPO_ROOT"
fi

echo "Repository:"
echo "  $REPO_ROOT"
echo
echo "Quellbaum:"
echo "  $SOURCE_ROOT"
echo

run() {
  if [[ "$DRY_RUN" -eq 1 ]]; then
    printf '[DRY] '
    printf '%q ' "$@"
    printf '\n'
  else
    "$@"
  fi
}

unique_conflict_path() {
  local target="$1"
  local dir name stem ext candidate n
  dir="$(dirname "$target")"
  name="$(basename "$target")"
  n=1

  if [[ "$name" == *.* ]]; then
    stem="${name%.*}"
    ext=".${name##*.}"
  else
    stem="$name"
    ext=""
  fi

  candidate="$dir/${stem}_MIGRATIONSKONFLIKT${ext}"
  while [[ -e "$candidate" ]]; do
    candidate="$dir/${stem}_MIGRATIONSKONFLIKT_${n}${ext}"
    ((n++))
  done

  printf '%s\n' "$candidate"
}

folders=0
moved=0
identical=0
conflicts=0

while IFS= read -r -d '' dir; do
  folders=$((folders + 1))
  rel="${dir#$REPO_ROOT/}"

  echo "[$folders] $rel"

  mapfile_cmd_available=0
  if builtin help mapfile >/dev/null 2>&1; then
    mapfile_cmd_available=1
  fi

  # Nur JSON-Dateien direkt im Lernkontrollen-Ordner.
  # Bereits vorhandene json/-Unterordner werden damit nicht erneut verarbeitet.
  json_files=()
  while IFS= read -r -d '' file; do
    json_files+=("$file")
  done < <(find "$dir" -maxdepth 1 -type f -name '*.json' -print0)

  if [[ "${#json_files[@]}" -eq 0 ]]; then
    echo "  keine JSON-Dateien auf oberster Ebene"
    echo
    continue
  fi

  json_dir="$dir/json"

  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "  [DRY] mkdir -p $json_dir"
  else
    mkdir -p "$json_dir"
  fi

  for file in "${json_files[@]}"; do
    name="$(basename "$file")"
    target="$json_dir/$name"

    if [[ ! -e "$target" ]]; then
      echo "  MOVE  $name -> json/$name"
      run mv "$file" "$target"
      moved=$((moved + 1))
      continue
    fi

    if cmp -s "$file" "$target"; then
      echo "  IDENTISCH: $name bereits in json/"
      run rm -f "$file"
      identical=$((identical + 1))
      continue
    fi

    conflict="$(unique_conflict_path "$target")"
    echo "  KONFLIKT: $name"
    echo "    vorhandene Datei bleibt: json/$name"
    echo "    zusätzliche Datei wird:  json/$(basename "$conflict")"
    run mv "$file" "$conflict"
    conflicts=$((conflicts + 1))
  done

  echo
done < <(
  find "$SOURCE_ROOT" \
    -type d \
    \( -name .git -o -name Ausgabe -o -name Build -o -name Dokumentation \) -prune \
    -o -type d -name '09_Lernkontrollen' -print0
)

echo "--------------------------------------------------"
echo "Gefundene 09_Lernkontrollen-Ordner: $folders"
echo "Verschobene JSON-Dateien:           $moved"
echo "Identische Duplikate:               $identical"
echo "Konflikte:                          $conflicts"

if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "Modus:                              DRY-RUN"
  echo "Es wurden keine Dateien verändert."
else
  echo "Modus:                              MIGRATION"
fi

echo

if [[ "$conflicts" -gt 0 ]]; then
  echo "ACHTUNG: Konflikte vorhanden."
  echo "Prüfen mit:"
  echo "  find \"$SOURCE_ROOT\" -type f -name '*_MIGRATIONSKONFLIKT*.json'"
  echo
fi

if [[ "$DRY_RUN" -eq 0 ]]; then
  echo "Danach prüfen:"
  echo "  git status"
  echo
  echo "Wenn alles korrekt ist:"
  echo "  git add -A"
  echo '  git commit -m "refactor(lernkontrollen): JSON-Dateien in Unterordner verschoben"'
  echo "  git push origin main"
fi
