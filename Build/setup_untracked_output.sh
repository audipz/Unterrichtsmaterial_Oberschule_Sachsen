#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
cd "$REPO_ROOT"

if ! grep -qxF '/Ausgabe/' .gitignore 2>/dev/null; then
  printf '\n# Generierte Build-Artefakte\n/Ausgabe/\n' >> .gitignore
  echo "/Ausgabe/ zu .gitignore hinzugefügt."
fi

if git ls-files --error-unmatch Ausgabe >/dev/null 2>&1 || git ls-files Ausgabe | grep -q .; then
  git rm -r --cached Ausgabe
  echo "Ausgabe aus dem Git-Index entfernt (lokale Dateien bleiben erhalten)."
else
  echo "Ausgabe ist bereits nicht versioniert."
fi

echo
echo "Danach prüfen:"
echo "  git status"
