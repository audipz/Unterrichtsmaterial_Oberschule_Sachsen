#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
OUTPUT_ROOT="$REPO_ROOT/Ausgabe"
WORK_ROOT="$REPO_ROOT/.build"
TEMPLATE_ROOT="$REPO_ROOT/Build/templates"
FILTER_ROOT="$REPO_ROOT/Build/filters"
AREA="${1:-}"

command -v pandoc >/dev/null 2>&1 || { echo "Pandoc fehlt." >&2; exit 1; }

SOURCE_BASE="$SOURCE_ROOT"
[[ -n "$AREA" ]] && SOURCE_BASE="$SOURCE_ROOT/$AREA"
[[ -d "$SOURCE_BASE" ]] || { echo "Bereich nicht gefunden: $SOURCE_BASE" >&2; exit 1; }

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
  local pptx rel target
  while IFS= read -r -d '' pptx; do
    rel="${pptx#$SOURCE_ROOT/}"
    target="$OUTPUT_ROOT/$rel"
    mkdir -p "$(dirname "$target")"
    cp "$pptx" "$target"
    echo "COPY PPTX MASTER $rel"
  done < <(find "$SOURCE_BASE" -type f -path '*/05_Praesentationen/*.pptx' -print0)
}

build_dir() {
  local dir="$1" md_files=()
  while IFS= read -r file; do md_files+=("$file"); done < <(find "$dir" -maxdepth 1 -type f -name '*.md' ! -name 'README.md' -print | sort)
  [[ "${#md_files[@]}" -gt 0 ]] || return 0

  local relative="${dir#$SOURCE_ROOT/}" parent name out_dir safe combined class_no topic part title subtitle version build_date footer_text footer_tex
  parent="$(dirname "$relative")"; name="$(basename "$relative")"; out_dir="$OUTPUT_ROOT"
  [[ "$parent" != "." ]] && out_dir="$OUTPUT_ROOT/$parent"
  mkdir -p "$out_dir"
  safe="$(printf '%s' "$relative" | tr '/\\:*?\"<>|' '_')"; combined="$WORK_ROOT/$safe.md"; : > "$combined"
  [[ -f "$dir/README.md" ]] && { cat "$dir/README.md" >> "$combined"; printf '\n\n' >> "$combined"; }
  for file in "${md_files[@]}"; do cat "$file" >> "$combined"; printf '\n\n' >> "$combined"; done

  class_no="$(find_class "$relative")"; part="$(humanize "$name")"; topic="$part"
  [[ "$parent" != "." ]] && topic="$(humanize "$(basename "$parent")")"
  title="$topic"; subtitle="$part"; [[ -n "$class_no" ]] && subtitle="Informatik · Klasse $class_no · $part"
  version="$(git -C "$REPO_ROOT" describe --tags --always --dirty 2>/dev/null || echo unversioniert)"; build_date="$(date +%d.%m.%Y)"
  footer_text="Unterrichtsmaterial Oberschule Sachsen · Version $version · Build $build_date"
  echo "BUILD $relative"

  pandoc "$combined" --from markdown --to docx --standalone --toc --toc-depth=2 --reference-doc="$TEMPLATE_ROOT/reference.docx" --lua-filter="$FILTER_ROOT/pagebreak.lua" --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$name.docx"
  python3 "$REPO_ROOT/Build/set_docx_footer.py" "$out_dir/$name.docx" --text "$footer_text"

  pandoc "$combined" --from markdown --to html5 --standalone --toc --toc-depth=2 --lua-filter="$FILTER_ROOT/pagebreak.lua" --css="$TEMPLATE_ROOT/publishing.css" --embed-resources --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$name.html"

  # PPTX werden nicht aus Markdown neu erzeugt. Die gepflegten Masterdateien
  # enthalten Layout, Visualisierungen und Sprechernotizen und werden unverändert kopiert.

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
    pandoc "$combined" --from markdown --to pdf --standalone --toc --toc-depth=2 --lua-filter="$FILTER_ROOT/pagebreak.lua" --pdf-engine=xelatex --include-in-header="$footer_tex" --variable=classoption:titlepage --variable=geometry:margin=22mm --variable=mainfont:"DejaVu Sans" --variable=monofont:"DejaVu Sans Mono" --metadata "title=$title" --metadata "subtitle=$subtitle" --metadata "toc-title=Inhaltsverzeichnis" --metadata "lang=de-DE" --resource-path="$dir:$SOURCE_ROOT:$REPO_ROOT" --output "$out_dir/$name.pdf" || echo "WARNUNG: PDF fehlgeschlagen: $relative" >&2
  fi
}

while IFS= read -r -d '' dir; do build_dir "$dir"; done < <(find "$SOURCE_BASE" -type d -print0)
copy_pptx_masters
python3 "$REPO_ROOT/Build/generate_index.py"
rm -rf "$WORK_ROOT"
echo "Build abgeschlossen: $OUTPUT_ROOT"
