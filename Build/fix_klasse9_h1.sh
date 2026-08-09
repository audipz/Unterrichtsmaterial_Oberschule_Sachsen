#!/usr/bin/env bash
set -euo pipefail

DRY_RUN=0
[[ "${1:-}" == "--dry-run" ]] && DRY_RUN=1

REPO_ROOT="$(git rev-parse --show-toplevel)"
ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen/Klasse_9"

if [[ ! -d "$ROOT" ]]; then
  echo "Klasse_9 nicht gefunden: $ROOT" >&2
  exit 1
fi

humanize() {
  local value="$1"
  value="${value%.md}"
  value="${value#??_}"
  value="${value//_/ }"
  value="${value//ae/ä}"
  value="${value//oe/ö}"
  value="${value//ue/ü}"
  printf '%s' "$value"
}

count=0

while IFS= read -r -d '' file; do
  if grep -Eq '^# [^#]' "$file"; then
    continue
  fi

  name="$(basename "$file")"

  if [[ "$name" == "README.md" ]]; then
    parent="$(basename "$(dirname "$file")")"
    title="$(humanize "$parent")"
  elif [[ "$name" == "Quellen.md" ]]; then
    title="Quellen"
  else
    title="$(humanize "$name")"
  fi

  rel="${file#$REPO_ROOT/}"
  echo "$rel"
  echo "  + # $title"

  count=$((count + 1))

  if [[ "$DRY_RUN" -eq 0 ]]; then
    tmp="${file}.tmp"
    {
      printf '# %s\n\n' "$title"
      cat "$file"
    } > "$tmp"
    mv "$tmp" "$file"
  fi
done < <(find "$ROOT" -type f -name '*.md' -print0)

echo
echo "Dateien ohne H1: $count"

if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "Dry-Run: keine Dateien verändert."
else
  echo "H1-Überschriften ergänzt."
  echo
  echo "Jetzt prüfen:"
  echo "  ./Build/validate.sh --strict"
  echo "  git diff --stat"
  echo "  git diff"
fi
