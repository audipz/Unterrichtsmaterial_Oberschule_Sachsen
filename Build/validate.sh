#!/usr/bin/env bash
set -euo pipefail

STRICT=0

if [[ "${1:-}" == "--strict" ]]; then
  STRICT=1
fi

REPO_ROOT="$(git rev-parse --show-toplevel)"
SOURCE_ROOT="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen"

if [[ ! -d "$SOURCE_ROOT" ]]; then
  echo "Fehler: Quellverzeichnis nicht gefunden:"
  echo "  $SOURCE_ROOT"
  exit 1
fi

warnings=0
errors=0
files=0

echo "Validiere Markdown-Dateien ..."
echo

while IFS= read -r -d '' file; do
  files=$((files + 1))

  relative="${file#$REPO_ROOT/}"
  basename="$(basename "$file")"

  # --------------------------------------------------
  # H1 prüfen
  # --------------------------------------------------

  h1_count="$(grep -Ec '^# [^#]' "$file" || true)"

  if [[ "$h1_count" -eq 0 ]]; then
    echo "WARNUNG: Keine H1: $relative"
    warnings=$((warnings + 1))

    if [[ "$STRICT" -eq 1 ]]; then
      errors=$((errors + 1))
    fi
  elif [[ "$h1_count" -gt 1 ]]; then
    echo "WARNUNG: Mehrere H1-Überschriften: $relative"
    warnings=$((warnings + 1))

    if [[ "$STRICT" -eq 1 ]]; then
      errors=$((errors + 1))
    fi
  fi

  # --------------------------------------------------
  # Leerzeichen im Dateinamen
  # --------------------------------------------------

  if [[ "$basename" == *" "* ]]; then
    echo "WARNUNG: Leerzeichen im Dateinamen: $relative"
    warnings=$((warnings + 1))

    if [[ "$STRICT" -eq 1 ]]; then
      errors=$((errors + 1))
    fi
  fi

  # --------------------------------------------------
  # Merge-Konflikte
  # --------------------------------------------------

  if grep -Eq '^(<<<<<<<|=======|>>>>>>>)' "$file"; then
    echo "FEHLER: Git-Merge-Konflikt: $relative"
    errors=$((errors + 1))
  fi

done < <(
  find "$SOURCE_ROOT" \
    -type d \( \
      -name '.git' \
      -o -name 'Ausgabe' \
      -o -name '.build' \
    \) -prune \
    -o -type f -name '*.md' -print0
)

echo
echo "----------------------------------------"
echo "Dateien geprüft: $files"
echo "Warnungen:       $warnings"
echo "Fehler:          $errors"

if [[ "$errors" -gt 0 ]]; then
  echo
  echo "Validierung fehlgeschlagen."
  exit 1
fi

echo
echo "Validierung erfolgreich."

if [[ "$warnings" -gt 0 ]]; then
  echo "Warnungen können im späteren QS-Durchlauf behoben werden."
fi