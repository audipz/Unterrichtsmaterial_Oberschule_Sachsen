#!/usr/bin/env python3
from pathlib import Path

from content_pool import load_catalog, output_patterns_for, resolve_keys
from run_export import load_manifest

root = Path(__file__).resolve().parent
repo = root.parent
catalog = load_catalog(repo / "ContentPool" / "catalog.json")

for item in catalog["items"]:
    for locator in [*item.get("sources", []), *item.get("resources", [])]:
        source_path = repo / locator["path"]
        if not source_path.exists():
            raise ValueError(f"Content-Pool {item['contentKey']}: Quelle fehlt: {locator['path']}")
print(f"Content-Pool: {len(catalog['items'])} Einträge und Quellen validiert.")

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
    if manifest.get("contentKeys"):
        items = resolve_keys(catalog, manifest["contentKeys"])
        audience = {str(value).upper() for value in manifest.get("audience", [])}
        patterns = output_patterns_for(items, audience or None)
        if not patterns:
            raise ValueError(f"Export {manifest['id']}: keine Pool-Ausgaben für Audience {sorted(audience)}")
    ids.add(manifest["id"])
    artifacts.add(manifest["artifact"])
    mode = "contentKeys" if manifest.get("contentKeys") else "legacy include"
    print(f"OK: {path.name} -> {manifest['artifact']} ({mode})")

print(f"{len(manifests)} Export-Manifeste erfolgreich validiert.")
