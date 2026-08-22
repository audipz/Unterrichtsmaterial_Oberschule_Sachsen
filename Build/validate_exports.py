#!/usr/bin/env python3
from pathlib import Path

from run_export import load_manifest

root = Path(__file__).resolve().parent
manifests = sorted((root / "export-manifests").glob("*.json"))
if not manifests:
    raise SystemExit("Keine Export-Manifeste gefunden.")

ids: set[str] = set()
artifacts: set[str] = set()
for path in manifests:
    manifest = load_manifest(path)
    if manifest["id"] in ids:
        raise ValueError(f"Doppelte Export-ID: {manifest['id']}")
    if manifest["artifact"] in artifacts:
        raise ValueError(f"Doppelter Artefaktname: {manifest['artifact']}")
    ids.add(manifest["id"])
    artifacts.add(manifest["artifact"])
    print(f"OK: {path.name} -> {manifest['artifact']}")

print(f"{len(manifests)} Export-Manifeste erfolgreich validiert.")
