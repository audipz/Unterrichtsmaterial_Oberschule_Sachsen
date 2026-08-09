#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

rm -rf "$REPO_ROOT/Ausgabe"
rm -rf "$REPO_ROOT/.build"

echo "Build-Ausgaben gelöscht."
