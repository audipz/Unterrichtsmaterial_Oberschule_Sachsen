#!/usr/bin/env bash
set -euo pipefail

# ============================================================
# OER Informatik – Oberschule Sachsen
# Erstellt die Grundstruktur des GitHub-Repositories.
#
# Das Skript:
# - legt nur fehlende Ordner und Dateien an,
# - überschreibt keine vorhandenen Materialien,
# - orientiert die Lernbereiche an der Lehrwerksarbeit,
# - trennt Schülerheft, Lehrerband, Lösungen und Präsentationen.
# ============================================================

ROOT="${1:-Unterrichtsmaterial_Oberschule_Sachsen}"

create_module_structure() {
    local module_path="$1"

    mkdir -p \
        "$module_path/01_Arbeitsheft" \
        "$module_path/02_Lehrerband" \
        "$module_path/03_Loesungen" \
        "$module_path/04_Praesentationen" \
        "$module_path/05_Dateien" \
        "$module_path/06_Bilder" \
        "$module_path/07_Quellen" \
        "$module_path/08_Lernkontrollen"

    touch "$module_path/README.md"
    touch "$module_path/07_Quellen/Quellen.md"
}

create_class_structure() {
    local class_number="$1"
    local class_path="$ROOT/Klasse_${class_number}"

    mkdir -p "$class_path/00_Organisation"
    touch "$class_path/00_Organisation/README.md"
}

mkdir -p \
    "$ROOT/.github/workflows" \
    "$ROOT/Vorlagen" \
    "$ROOT/Dokumentation" \
    "$ROOT/Lizenzen" \
    "$ROOT/Skripte"

touch \
    "$ROOT/README.md" \
    "$ROOT/LICENSE.md" \
    "$ROOT/CHANGELOG.md" \
    "$ROOT/CONTRIBUTING.md" \
    "$ROOT/STYLEGUIDE.md" \
    "$ROOT/Dokumentation/README.md" \
    "$ROOT/Vorlagen/README.md" \
    "$ROOT/Lizenzen/README.md"

# ------------------------------------------------------------
# Klasse 7
# Die endgültige inhaltliche Ausgestaltung wird bei der
# Erstellung des jeweiligen Arbeitsheftes geprüft.
# ------------------------------------------------------------
create_class_structure 7
create_module_structure "$ROOT/Klasse_7/01_Grundlagen_der_digitalen_Welt"
create_module_structure "$ROOT/Klasse_7/02_Informationen_und_Daten"
create_module_structure "$ROOT/Klasse_7/03_Algorithmen_und_Programmierung"
create_module_structure "$ROOT/Klasse_7/04_Wahlbereich"

# ------------------------------------------------------------
# Klasse 8
# ------------------------------------------------------------
create_class_structure 8
create_module_structure "$ROOT/Klasse_8/01_Informationen_und_Daten"
create_module_structure "$ROOT/Klasse_8/02_Tabellenkalkulation_und_Datenverarbeitung"
create_module_structure "$ROOT/Klasse_8/03_Algorithmen_und_Programmierung"
create_module_structure "$ROOT/Klasse_8/04_Wahlbereich"

# ------------------------------------------------------------
# Klasse 9
# Orientierung am Lehrplan:
# - Informationen und Daten
# - Komplexaufgabe zur Algorithmierung
# - Wahlbereich Informatik und Automatisierung
# ------------------------------------------------------------
create_class_structure 9
create_module_structure "$ROOT/Klasse_9/01_Informationen_und_Daten"
create_module_structure "$ROOT/Klasse_9/02_Komplexaufgabe_zur_Algorithmierung"
create_module_structure "$ROOT/Klasse_9/03_Wahlbereich_Informatik_und_Automatisierung"

# ------------------------------------------------------------
# Klasse 10
# Die endgültigen Bezeichnungen werden vor der Materialerstellung
# nochmals mit der aktuellen Lehrplanfassung abgeglichen.
# ------------------------------------------------------------
create_class_structure 10
create_module_structure "$ROOT/Klasse_10/01_Informationen_und_Daten"
create_module_structure "$ROOT/Klasse_10/02_Algorithmen_und_Programmierung"
create_module_structure "$ROOT/Klasse_10/03_Informatiksysteme"
create_module_structure "$ROOT/Klasse_10/04_Wahlbereich"

cat <<EOF

Repository-Struktur wurde angelegt:

$ROOT

Hinweise:
- Vorhandene Dateien wurden nicht überschrieben.
- Leere README- und Quellen-Dateien wurden als Platzhalter angelegt.
- Als nächstes kann das Arbeitsheft für Klasse 9,
  Lernbereich 01_Informationen_und_Daten erstellt werden.

EOF
