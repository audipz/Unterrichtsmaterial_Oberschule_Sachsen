#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
WORK_ROOT="$REPO_ROOT/.build"
TEMPLATE_ROOT="$REPO_ROOT/Build/templates"
FILTER_ROOT="$REPO_ROOT/Build/filters"
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

rm -rf "$OUTPUT_ROOT" "$WORK_ROOT"
mkdir -p "$OUTPUT_ROOT" "$WORK_ROOT"

humanize() {
  local s="$1"
  s="${s#??_}"
  s="${s//_/ }"
  printf '%s' "$s"
}

find_class() {
  local rel="$1"
  local segment
  IFS='/' read -r -a parts <<< "$rel"
  for segment in "${parts[@]}"; do
    if [[ "$segment" =~ ^Klasse_([0-9]+)$ ]]; then
      printf '%s' "${BASH_REMATCH[1]}"
      return
    fi
  done
  printf ''
}

git_version() {
  local tag
  tag="$(git -C "$REPO_ROOT" describe --tags --always --dirty 2>/dev/null || true)"
  [[ -n "$tag" ]] && printf '%s' "$tag" || printf 'unversioniert'
}

build_dir() {
  local dir="$1"
  local md_files=()
  local file

  while IFS= read -r file; do
    md_files+=("$file")
  done < <(find "$dir" -maxdepth 1 -type f -name '*.md' ! -name 'README.md' -print | sort)

  [[ "${#md_files[@]}" -gt 0 ]] || return 0

  local relative="${dir#$SOURCE_ROOT/}"
  local parent name out_dir safe combined
  local class_no topic part title subtitle version build_date

  parent="$(dirname "$relative")"
  name="$(basename "$relative")"

  if [[ "$parent" == "." ]]; then
    out_dir="$OUTPUT_ROOT"
  else
    out_dir="$OUTPUT_ROOT/$parent"
  fi
  mkdir -p "$out_dir"

  safe="$(printf '%s' "$relative" | tr '/\\:*?"<>|' '_')"
  combined="$WORK_ROOT/$safe.md"
  : > "$combined"

  # README becomes the introductory first chapter, if present.
  if [[ -f "$dir/README.md" ]]; then
    cat "$dir/README.md" >> "$combined"
    printf '\n\n' >> "$combined"
  fi

  for file in "${md_files[@]}"; do
    cat "$file" >> "$combined"
    printf '\n\n' >> "$combined"
  done

  class_no="$(find_class "$relative")"
  part="$(humanize "$name")"

  if [[ "$parent" != "." ]]; then
    topic="$(humanize "$(basename "$parent")")"
  else
    topic="$part"
  fi

  # For a typical Werkteil (01_Arbeitsheft etc.), the parent is the topic.
  title="$topic"
  subtitle="$part"
  if [[ -n "$class_no" ]]; then
    subtitle="Informatik - Klasse $class_no - $part"
  fi

  version="$(git_version)"
  build_date="$(date +%d.%m.%Y)"

  echo "BUILD $relative"

  # DOCX: title metadata appears before TOC. In reference.docx the
  # TOC Heading style starts a new page, creating a true title page.
  pandoc "$combined" \
    --from markdown \
    --to docx \
    --standalone \
    --toc \
    --toc-depth=2 \
    --reference-doc="$TEMPLATE_ROOT/reference.docx" \
    --lua-filter="$FILTER_ROOT/pagebreak.lua" \
    --metadata "title=$title" \
    --metadata "subtitle=$subtitle" \
    --metadata "author=Unterrichtsmaterial Oberschule Sachsen" \
    --metadata "date=Version $version - Build $build_date" \
    --metadata "lang=de-DE" \
    --metadata "toc-title=Inhaltsverzeichnis" \
    --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" \
    --output "$out_dir/$name.docx"

  # HTML
  pandoc "$combined" \
    --from markdown \
    --to html5 \
    --standalone \
    --toc \
    --toc-depth=2 \
    --lua-filter="$FILTER_ROOT/pagebreak.lua" \
    --css="$TEMPLATE_ROOT/publishing.css" \
    --embed-resources \
    --metadata "title=$title" \
    --metadata "subtitle=$subtitle" \
    --metadata "author=Unterrichtsmaterial Oberschule Sachsen" \
    --metadata "date=Version $version - Build $build_date" \
    --metadata "lang=de-DE" \
    --metadata "toc-title=Inhaltsverzeichnis" \
    --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" \
    --output "$out_dir/$name.html"

  # PDF
  if command -v xelatex >/dev/null 2>&1; then
    pandoc "$combined" \
      --from markdown \
      --to pdf \
      --standalone \
      --toc \
      --toc-depth=2 \
      --lua-filter="$FILTER_ROOT/pagebreak.lua" \
      --pdf-engine=xelatex \
      --variable=classoption:titlepage \
      --variable=geometry:margin=22mm \
      --variable=mainfont:"DejaVu Sans" \
      --variable=monofont:"DejaVu Sans Mono" \
      --metadata "title=$title" \
      --metadata "subtitle=$subtitle" \
      --metadata "author=Unterrichtsmaterial Oberschule Sachsen" \
      --metadata "date=Version $version - Build $build_date" \
      --metadata "lang=de-DE" \
      --metadata "toc-title=Inhaltsverzeichnis" \
      --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" \
      --output "$out_dir/$name.pdf" || {
        echo "WARNUNG: PDF-Build fehlgeschlagen: $relative" >&2
      }
  else
    echo "INFO: xelatex nicht vorhanden – PDF übersprungen: $relative"
  fi
}

while IFS= read -r -d '' dir; do
  build_dir "$dir"
done < <(find "$SOURCE_BASE" -type d -print0)

python3 "$REPO_ROOT/Build/generate_index.py"

rm -rf "$WORK_ROOT"

echo
echo "Build abgeschlossen."
echo "Ausgabe: $OUTPUT_ROOT"
