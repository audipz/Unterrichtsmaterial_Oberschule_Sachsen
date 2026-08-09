#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"
TARGET="$SOURCE_ROOT/${1:-}"

if [[ ! -d "$TARGET" ]]; then
  echo "Bereich nicht gefunden: $TARGET" >&2
  exit 1
fi

errors=0

while IFS= read -r -d '' file; do
  h1_count="$(grep -Ec '^# [^#]' "$file" || true)"

  if [[ "$h1_count" -eq 0 ]]; then
    echo "Keine H1: $file"
    errors=1
  fi

  base="$(basename "$file")"
  if [[ "$base" == *" "* ]]; then
    echo "Leerzeichen im Dateinamen: $file"
    errors=1
  fi
done < <(find "$TARGET" -type f -name '*.md' -print0)

if [[ "$errors" -ne 0 ]]; then
  echo "Validierung fehlgeschlagen." >&2
  exit 1
fi

echo "Validierung erfolgreich."
