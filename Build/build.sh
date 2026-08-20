#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
WORK_ROOT="$REPO_ROOT/.build"
TEMPLATE_ROOT="$REPO_ROOT/Build/templates"
FILTER_ROOT="$REPO_ROOT/Build/filters"
AREA="${1:-}"
ALLOWED_AREAS=(Klasse_7 Klasse_8 Klasse_9 Klasse_10 Grundlagen Werkzeuge)

command -v pandoc >/dev/null 2>&1 || { echo "Pandoc fehlt." >&2; exit 1; }

is_allowed_area() {
  local candidate="$1" allowed
  for allowed in "${ALLOWED_AREAS[@]}"; do
    [[ "$candidate" == "$allowed" ]] && return 0
  done
  return 1
}

if [[ -n "$AREA" ]]; then
  is_allowed_area "$AREA" || {
    echo "Bereich '$AREA' wird nicht veröffentlicht. Erlaubt: ${ALLOWED_AREAS[*]}" >&2
    exit 1
  }
  [[ -d "$SOURCE_ROOT/$AREA" ]] || { echo "Bereich nicht gefunden: $SOURCE_ROOT/$AREA" >&2; exit 1; }
fi

rm -rf "$OUTPUT_ROOT" "$WORK_ROOT"
mkdir -p "$OUTPUT_ROOT" "$WORK_ROOT"

humanize() { local s="$1"; s="${s#??_}"; s="${s//_/ }"; printf '%s' "$s"; }
find_class() {
  local rel="$1" segment
  IFS='/' read -r -a parts <<< "$rel"
  for segment in "${parts[@]}"; do
    if [[ "$segment" =~ ^Klasse_([0-9]+)$ ]]; then printf '%s' "${BASH_REMATCH[1]}"; return; fi
  done
}

copy_pptx_masters() {
  local source_base="$1" pptx rel target
  while IFS= read -r -d '' pptx; do
    rel="${pptx#$SOURCE_ROOT/}"
    target="$OUTPUT_ROOT/$rel"
    mkdir -p "$(dirname "$target")"
    cp "$pptx" "$target"
    echo "COPY PPTX MASTER $rel"
  done < <(find "$source_base" -type f -path '*/05_Praesentationen/*.pptx' -print0)
}

render_document() {
  local input="$1" resource_dir="$2" out_dir="$3" out_name="$4" title="$5" subtitle="$6" safe="$7"
  local version build_date footer_text footer_tex
  version="$(git -C "$REPO_ROOT" describe --tags --always --dirty 2>/dev/null || echo unversioniert)"
  build_date="$(date +%d.%m.%Y)"
  footer_text="Unterrichtsmaterial Oberschule Sachsen · Version $version · Build $build_date"
  mkdir -p "$out_dir"

  pandoc "$input" --from markdown --to docx --standalone --toc --toc-depth=2 --reference-doc="$TEMPLATE_ROOT/reference.docx" --lua-filter="$FILTER_ROOT/pagebreak.lua" --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$resource_dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$out_name.docx"
  python3 "$REPO_ROOT/Build/set_docx_footer.py" "$out_dir/$out_name.docx" --text "$footer_text"

  pandoc "$input" --from markdown --to html5 --standalone --toc --toc-depth=2 --lua-filter="$FILTER_ROOT/pagebreak.lua" --css="$TEMPLATE_ROOT/publishing.css" --embed-resources --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$resource_dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$out_name.html"

  if command -v xelatex >/dev/null 2>&1; then
    footer_tex="$WORK_ROOT/$safe-footer.tex"
    cat > "$footer_tex" <<EOF
\\usepackage{fancyhdr}
\\pagestyle{fancy}
\\fancyhf{}
\\fancyfoot[C]{\\scriptsize Unterrichtsmaterial Oberschule Sachsen · Version $version · Build $build_date}
\\renewcommand{\\headrulewidth}{0pt}
\\renewcommand{\\footrulewidth}{0pt}
EOF
    pandoc "$input" --from markdown --to pdf --standalone --toc --toc-depth=2 --lua-filter="$FILTER_ROOT/pagebreak.lua" --lua-filter="$FILTER_ROOT/pdf_emoji_fallback.lua" --pdf-engine=xelatex --include-in-header="$footer_tex" --variable=classoption:titlepage --variable=geometry:margin=22mm --variable=mainfont:"DejaVu Sans" --variable=monofont:"DejaVu Sans Mono" --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$resource_dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$out_name.pdf" || echo "WARNUNG: PDF fehlgeschlagen: $input" >&2
  fi
}

build_lernkontrollen() {
  local dir="$1" relative parent class_no file base out_name out_dir title subtitle safe
  relative="${dir#$SOURCE_ROOT/}"
  parent="$(dirname "$relative")"
  class_no="$(find_class "$relative")"
  out_dir="$OUTPUT_ROOT/$parent/Lernkontrollen"

  while IFS= read -r file; do
    base="$(basename "$file" .md)"
    if [[ "$base" =~ ^([0-9]+)_LK_(.+)$ ]]; then
      out_name="Lernkontrolle_${BASH_REMATCH[1]}_${BASH_REMATCH[2]}"
    else
      out_name="Lernkontrolle_${base}"
    fi
    title="$(humanize "$out_name")"
    subtitle="Lernkontrolle"
    [[ -n "$class_no" ]] && subtitle="Informatik · Klasse $class_no · Lernkontrolle"
    safe="$(printf '%s' "$relative/$out_name" | tr '/\\:*?\"<>|' '_')"
    echo "BUILD EINZELN $relative/$(basename "$file") -> Lernkontrollen/$out_name"
    render_document "$file" "$dir" "$out_dir" "$out_name" "$title" "$subtitle" "$safe"
  done < <(find "$dir" -maxdepth 1 -type f -name '*.md' ! -name 'README.md' -print | sort)
}

build_dir() {
  local dir="$1" md_files=()
  local relative="${dir#$SOURCE_ROOT/}" name="$(basename "$dir")"

  if [[ "$name" == "09_Lernkontrollen" || "$name" == "04_Leistungskontrollen" || "$name" == "05_Leistungskontrollen" ]]; then
    build_lernkontrollen "$dir"
    return 0
  fi

  while IFS= read -r file; do md_files+=("$file"); done < <(find "$dir" -maxdepth 1 -type f -name '*.md' ! -name 'README.md' -print | sort)
  [[ "${#md_files[@]}" -gt 0 ]] || return 0

  local parent out_dir safe combined class_no topic part title subtitle
  parent="$(dirname "$relative")"; out_dir="$OUTPUT_ROOT"
  [[ "$parent" != "." ]] && out_dir="$OUTPUT_ROOT/$parent"
  safe="$(printf '%s' "$relative" | tr '/\\:*?\"<>|' '_')"; combined="$WORK_ROOT/$safe.md"; : > "$combined"
  [[ -f "$dir/README.md" ]] && { cat "$dir/README.md" >> "$combined"; printf '\n\n' >> "$combined"; }
  for file in "${md_files[@]}"; do cat "$file" >> "$combined"; printf '\n\n' >> "$combined"; done

  class_no="$(find_class "$relative")"; part="$(humanize "$name")"; topic="$part"
  [[ "$parent" != "." ]] && topic="$(humanize "$(basename "$parent")")"
  title="$topic"; subtitle="$part"; [[ -n "$class_no" ]] && subtitle="Informatik · Klasse $class_no · $part"
  echo "BUILD $relative"
  render_document "$combined" "$dir" "$out_dir" "$name" "$title" "$subtitle" "$safe"
}

build_area() {
  local area="$1" source_base="$SOURCE_ROOT/$area"
  [[ -d "$source_base" ]] || { echo "WARNUNG: Bereich fehlt und wird übersprungen: $area" >&2; return 0; }

  while IFS= read -r -d '' dir; do
    build_dir "$dir"
  done < <(find "$source_base" -mindepth 1 -type d -print0)

  copy_pptx_masters "$source_base"
}

if [[ -n "$AREA" ]]; then
  build_area "$AREA"
else
  for area in "${ALLOWED_AREAS[@]}"; do
    build_area "$area"
  done
fi

rm -rf "$WORK_ROOT"
echo "Build abgeschlossen: $OUTPUT_ROOT"
