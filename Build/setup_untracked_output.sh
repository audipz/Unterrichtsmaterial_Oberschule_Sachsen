#!/usr/bin/env bash
set -euo pipefail
REPO_ROOT="$(git rev-parse --show-toplevel)"
cd "$REPO_ROOT"

grep -qxF '/Ausgabe/' .gitignore 2>/dev/null || printf '\n# Generierte Build-Artefakte\n/Ausgabe/\n' >> .gitignore

if git ls-files Ausgabe | grep -q .; then
  git rm -r --cached Ausgabe
fi

echo "Ausgabe ist jetzt ein lokales/CI-Build-Artefakt und wird nicht mehr auf main versioniert."
