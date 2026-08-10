#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
BASE="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen/Klasse_9"

remove_if_exists() {
  local f="$1"
  if [[ -e "$f" ]]; then
    echo "Entferne: ${f#$REPO_ROOT/}"
    rm -f "$f"
  else
    echo "Nicht vorhanden: ${f#$REPO_ROOT/}"
  fi
}

# Eindeutig veraltete/leere Dateien
remove_if_exists "$BASE/01_Informationen_und_Daten/01_Arbeitsheft/31_Chancen_und_Grenzen_von_KI.md"
remove_if_exists "$BASE/02_Komplexaufgabe_zur_Algorithmierung/01_Arbeitsheft/Neue Datei"
remove_if_exists "$BASE/02_Komplexaufgabe_zur_Algorithmierung/01_Arbeitsheft/README_2.txt"

# Generiertes Artefakt gehört nicht in die Markdown-Quellen
remove_if_exists "$BASE/02_Komplexaufgabe_zur_Algorithmierung/01_Arbeitsheft/Arbeitsheft_Lineare_Algorithmen.docx"

echo
echo "Nicht automatisch verändert:"
echo "  Klasse_9/01_Informationen_und_Daten/01_Arbeitsheft/14_Tabellen_verbinden.md"
echo "Grund: inhaltlich brauchbar, aber ohne eindeutige Position in der neuen Kapitelstruktur."

echo
echo "Danach:"
echo "  git status"
