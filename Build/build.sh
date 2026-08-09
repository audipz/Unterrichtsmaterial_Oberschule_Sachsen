#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
WORK_ROOT="$REPO_ROOT/.build"
AREA="${1:-}"

if ! command -v pandoc >/dev/null 2>&1; then
  echo "Pandoc wurde nicht gefunden." >&2
  exit 1
fi

SOURCE_BASE="$SOURCE_ROOT"
if [[ -n "$AREA" ]]; then
  SOURCE_BASE="$SOURCE_ROOT/$AREA"
fi

if [[ ! -d "$SOURCE_BASE" ]]; then
  echo "Bereich nicht gefunden: $SOURCE_BASE" >&2
  exit 1
fi

mkdir -p "$OUTPUT_ROOT" "$WORK_ROOT"

build_dir() {
  local dir="$1"

  mapfile -t md_files < <(
    find "$dir" -maxdepth 1 -type f -name '*.md' ! -name 'README.md' -print | sort
  )

  [[ "${#md_files[@]}" -gt 0 ]] || return 0

  local relative="${dir#$SOURCE_ROOT/}"
  local parent
  local name
  parent="$(dirname "$relative")"
  name="$(basename "$relative")"

  local out_dir="$OUTPUT_ROOT"
  if [[ "$parent" != "." ]]; then
    out_dir="$OUTPUT_ROOT/$parent"
  fi
  mkdir -p "$out_dir"

  local safe
  safe="$(echo "$relative" | tr '/\\:*?"<>|' '_')"
  local combined="$WORK_ROOT/$safe.md"
  : > "$combined"

  if [[ -f "$dir/README.md" ]]; then
    cat "$dir/README.md" >> "$combined"
    printf '\n\n\\newpage\n\n' >> "$combined"
  fi

  for file in "${md_files[@]}"; do
    cat "$file" >> "$combined"
    printf '\n\n\\newpage\n\n' >> "$combined"
  done

  echo "BUILD $relative"

  pandoc "$combined" \
    --from markdown \
    --to docx \
    --standalone \
    --toc \
    --metadata lang=de-DE \
    --output "$out_dir/$name.docx"

  pandoc "$combined" \
    --from markdown \
    --to html5 \
    --standalone \
    --toc \
    --metadata lang=de-DE \
    --output "$out_dir/$name.html"

  local engine=""
  for candidate in xelatex lualatex pdflatex; do
    if command -v "$candidate" >/dev/null 2>&1; then
      engine="$candidate"
      break
    fi
  done

  if [[ -n "$engine" ]]; then
    pandoc "$combined" \
      --from markdown \
      --to pdf \
      --standalone \
      --toc \
      --metadata lang=de-DE \
      --pdf-engine="$engine" \
      --output "$out_dir/$name.pdf" || \
      echo "WARNUNG: PDF-Build fehlgeschlagen: $relative" >&2
  fi
}

while IFS= read -r -d '' dir; do
  build_dir "$dir"
done < <(find "$SOURCE_BASE" -type d -print0)

rm -rf "$WORK_ROOT"

echo
echo "Build abgeschlossen."
echo "Ausgabe: $OUTPUT_ROOT"
