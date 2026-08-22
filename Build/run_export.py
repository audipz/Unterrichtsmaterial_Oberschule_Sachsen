#!/usr/bin/env python3
from __future__ import annotations

import argparse
import fnmatch
import json
import shutil
import subprocess
import sys
import zipfile
from pathlib import Path

from content_pool import load_catalog, output_patterns_for, resolve_keys

ALLOWED_AREAS = ["Klasse_7", "Klasse_8", "Klasse_9", "Klasse_10", "Grundlagen", "Werkzeuge"]
ALLOWED_FORMATS = {"pdf", "docx", "pptx"}


def load_manifest(path: Path) -> dict:
    data = json.loads(path.read_text(encoding="utf-8"))
    required = ("schemaVersion", "id", "title", "areas", "formats", "artifact")
    missing = [key for key in required if key not in data]
    if missing:
        raise ValueError(f"Export-Manifest unvollständig: {', '.join(missing)}")
    if data["schemaVersion"] != 1:
        raise ValueError(f"Nicht unterstützte schemaVersion: {data['schemaVersion']}")
    if not data["id"].strip() or not data["artifact"].strip():
        raise ValueError("id und artifact dürfen nicht leer sein")

    unknown_areas = sorted(set(data["areas"]) - set(ALLOWED_AREAS))
    if unknown_areas:
        raise ValueError(f"Unbekannte Bereiche: {', '.join(unknown_areas)}")
    unknown_formats = sorted(set(data["formats"]) - ALLOWED_FORMATS)
    if unknown_formats:
        raise ValueError(f"Unbekannte Formate: {', '.join(unknown_formats)}")
    if not data["areas"] or not data["formats"]:
        raise ValueError("areas und formats dürfen nicht leer sein")

    has_include = bool(data.get("include"))
    has_keys = bool(data.get("contentKeys"))
    if has_include == has_keys:
        raise ValueError("Export-Manifest muss genau eines von include oder contentKeys verwenden")
    return data


def resolve_include(repo: Path, manifest: dict) -> list[str]:
    if manifest.get("include"):
        return manifest["include"]

    catalog = load_catalog(repo / "ContentPool" / "catalog.json")
    items = resolve_keys(catalog, manifest["contentKeys"])
    audience = {str(value).upper() for value in manifest.get("audience", [])}
    patterns = output_patterns_for(items, audience or None)
    if not patterns:
        raise ValueError(f"Export '{manifest['id']}' löst keine Ausgabemuster aus dem Content-Pool auf")
    return patterns


def run_build(repo: Path, areas: list[str]) -> None:
    output = repo / "Ausgabe"
    aggregate = repo / ".build-export-aggregate"
    shutil.rmtree(aggregate, ignore_errors=True)

    if areas == ALLOWED_AREAS:
        subprocess.run([str(repo / "Build" / "build.sh")], cwd=repo, check=True)
        return

    aggregate.mkdir(parents=True, exist_ok=True)
    for area in areas:
        print(f"\n=== Renderer: {area} ===", flush=True)
        subprocess.run([str(repo / "Build" / "build.sh"), area], cwd=repo, check=True)
        if output.exists():
            shutil.copytree(output, aggregate, dirs_exist_ok=True)

    shutil.rmtree(output, ignore_errors=True)
    shutil.move(str(aggregate), str(output))


def matches(path: str, patterns: list[str]) -> bool:
    return any(fnmatch.fnmatchcase(path, pattern) for pattern in patterns)


def select_files(repo: Path, output: Path, manifest: dict) -> list[Path]:
    include = resolve_include(repo, manifest)
    exclude = manifest.get("exclude", [])
    formats = set(manifest["formats"])
    selected: list[Path] = []

    for file in sorted(output.rglob("*")):
        if not file.is_file():
            continue
        rel = file.relative_to(output).as_posix()
        suffix = file.suffix.lower().lstrip(".")
        if suffix not in formats:
            continue
        if not matches(rel, include):
            continue
        if exclude and matches(rel, exclude):
            continue
        selected.append(file)

    minimum = int(manifest.get("minimumFiles", 1))
    if len(selected) < minimum:
        raise RuntimeError(
            f"Export '{manifest['id']}' enthält nur {len(selected)} Datei(en), erwartet mindestens {minimum}."
        )
    return selected


def package(repo: Path, manifest_path: Path, manifest: dict, files: list[Path]) -> Path:
    output = repo / "Ausgabe"
    publish = repo / ".publish"
    publish.mkdir(parents=True, exist_ok=True)
    target = publish / manifest["artifact"]
    target.unlink(missing_ok=True)

    metadata = {
        "schemaVersion": 1,
        "exportId": manifest["id"],
        "title": manifest["title"],
        "audience": manifest.get("audience", []),
        "areas": manifest["areas"],
        "formats": manifest["formats"],
        "contentKeys": manifest.get("contentKeys", []),
        "sourceManifest": manifest_path.relative_to(repo).as_posix(),
        "fileCount": len(files),
    }

    with zipfile.ZipFile(target, "w", compression=zipfile.ZIP_DEFLATED) as archive:
        archive.writestr("export-manifest.json", json.dumps(metadata, ensure_ascii=False, indent=2) + "\n")
        for file in files:
            archive.write(file, file.relative_to(output).as_posix())

    if target.stat().st_size == 0:
        raise RuntimeError(f"Leeres Export-Artefakt: {target}")
    print(f"Export {manifest['id']}: {len(files)} Datei(en) -> {target}")
    return target


def main() -> int:
    parser = argparse.ArgumentParser(description="Baut und paketiert einen manifestgesteuerten Dokumentexport.")
    parser.add_argument("manifest", type=Path, help="JSON-Exportmanifest")
    parser.add_argument("--package-only", action="store_true", help="Vorhandene Ausgabe/ nur paketieren")
    args = parser.parse_args()

    repo = Path(subprocess.check_output(["git", "rev-parse", "--show-toplevel"], text=True).strip())
    manifest_path = args.manifest.resolve()
    if not manifest_path.is_relative_to(repo.resolve()):
        raise ValueError("Export-Manifest muss innerhalb des Repositories liegen")
    manifest = load_manifest(manifest_path)

    if not args.package_only:
        run_build(repo, manifest["areas"])

    output = repo / "Ausgabe"
    if not output.is_dir():
        raise RuntimeError("Ausgabe/ fehlt. Dokumente zuerst bauen.")
    files = select_files(repo, output, manifest)
    package(repo, manifest_path, manifest, files)
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except Exception as exc:
        print(f"FEHLER: {exc}", file=sys.stderr)
        raise
