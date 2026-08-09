#!/usr/bin/env bash
set -euo pipefail

DRY_RUN=0
[[ "${1:-}" == "--dry-run" ]] && DRY_RUN=1

REPO_ROOT="$(git rev-parse --show-toplevel)"
TARGET="$REPO_ROOT/Unterrichtsmaterial_Oberschule_Sachsen/Klasse_9/01_Informationen_und_Daten"

if [[ ! -d "$TARGET" ]]; then
  echo "Fehler: Ziel nicht gefunden:"
  echo "  $TARGET" >&2
  exit 1
fi

run() {
  if [[ "$DRY_RUN" -eq 1 ]]; then
    printf '[DRY] '
    printf '%q ' "$@"
    printf '\n'
  else
    "$@"
  fi
}

move_dir() {
  local old="$1"
  local new="$2"
  local src="$TARGET/$old"
  local dst="$TARGET/$new"

  if [[ ! -e "$src" ]]; then
    echo "Übersprungen: $old existiert nicht."
    return
  fi

  if [[ -e "$dst" ]]; then
    echo "FEHLER: Ziel existiert bereits: $new" >&2
    echo "Keine automatische Zusammenführung durchgeführt." >&2
    exit 1
  fi

  echo "$old -> $new"
  run mv "$src" "$dst"
}

echo "Ziel:"
echo "  $TARGET"
echo
echo "Migration auf die verbindliche Werkteilstruktur:"
echo

# Von hinten nach vorn, damit keine Zielnamen kollidieren.
move_dir "08_Lernkontrollen"  "09_Lernkontrollen"
move_dir "06_Bilder"          "08_Bilder"
move_dir "05_Dateien"         "06_Dateien"
move_dir "04_Praesentationen" "05_Praesentationen"
move_dir "03_Loesungen"       "04_Loesungen"

# 07_Quellen bleibt unverändert.

MATERIAL="$TARGET/03_Material"
if [[ ! -d "$MATERIAL" ]]; then
  echo "Erzeuge 03_Material"
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "[DRY] mkdir -p '$MATERIAL'"
    echo "[DRY] README.md in 03_Material anlegen"
  else
    mkdir -p "$MATERIAL"
    cat > "$MATERIAL/README.md" <<'EOF'
# Material

Dieser Ordner enthält ergänzende Unterrichtsmaterialien zum Lernbereich **Informationen und Daten**.

Hier werden Materialien abgelegt, die weder zum Arbeitsheft noch zum Lehrerband gehören, zum Beispiel:

- Karten,
- Vorlagen,
- Gruppenmaterial,
- Beobachtungsbögen,
- Differenzierungsmaterial.

Der Ordner kann im weiteren Review gezielt ergänzt werden.
EOF
  fi
fi

echo
echo "Zielstruktur:"
for d in \
  01_Arbeitsheft \
  02_Lehrerband \
  03_Material \
  04_Loesungen \
  05_Praesentationen \
  06_Dateien \
  07_Quellen \
  08_Bilder \
  09_Lernkontrollen
do
  if [[ -d "$TARGET/$d" ]]; then
    echo "[OK]    $d"
  else
    echo "[FEHLT] $d"
  fi
done

echo
if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "Dry-Run abgeschlossen. Keine Änderungen vorgenommen."
else
  echo "Migration abgeschlossen."
  echo "Bitte jetzt prüfen:"
  echo "  git status"
fi
