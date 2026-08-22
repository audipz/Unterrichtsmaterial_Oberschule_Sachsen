#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path

ALLOWED_AUDIENCES = {"STUDENT", "TEACHER", "INTERNAL"}
ALLOWED_TYPES = {"TOPIC", "SECTION", "EXERCISE", "WORKSHEET", "REFERENCE", "RESOURCE", "COLLECTION"}


def load_catalog(path: Path) -> dict:
    data = json.loads(path.read_text(encoding="utf-8"))
    if data.get("schemaVersion") != 1:
        raise ValueError("Content-Pool: schemaVersion muss 1 sein")
    items = data.get("items")
    if not isinstance(items, list):
        raise ValueError("Content-Pool: items muss eine Liste sein")

    seen: set[str] = set()
    for item in items:
        key = str(item.get("contentKey", "")).strip()
        if not key:
            raise ValueError("Content-Pool: contentKey fehlt")
        if key in seen:
            raise ValueError(f"Content-Pool: doppelter contentKey: {key}")
        seen.add(key)

        item_type = str(item.get("type", "TOPIC")).upper()
        if item_type not in ALLOWED_TYPES:
            raise ValueError(f"Content-Pool {key}: unbekannter type {item_type}")

        sources = item.get("sources", [])
        resources = item.get("resources", [])
        if not isinstance(sources, list) or not isinstance(resources, list):
            raise ValueError(f"Content-Pool {key}: sources/resources müssen Listen sein")
        if not sources and not resources and item_type != "COLLECTION":
            raise ValueError(f"Content-Pool {key}: mindestens source oder resource erforderlich")

        for source in sources:
            _validate_locator(key, source, "source")
        for resource in resources:
            _validate_locator(key, resource, "resource")
            audience = str(resource.get("audience", "TEACHER")).upper()
            if audience not in ALLOWED_AUDIENCES:
                raise ValueError(f"Content-Pool {key}: ungültige audience {audience}")

        children = item.get("children", [])
        if not isinstance(children, list):
            raise ValueError(f"Content-Pool {key}: children muss eine Liste sein")

    unknown_children = sorted({child for item in items for child in item.get("children", []) if child not in seen})
    if unknown_children:
        raise ValueError("Content-Pool: unbekannte children: " + ", ".join(unknown_children))
    return data


def _validate_locator(key: str, value: dict, kind: str) -> None:
    if not isinstance(value, dict):
        raise ValueError(f"Content-Pool {key}: {kind} muss ein Objekt sein")
    path = str(value.get("path", "")).strip()
    if not path or path.startswith("/") or ".." in Path(path).parts:
        raise ValueError(f"Content-Pool {key}: ungültiger {kind}-Pfad: {path}")


def index_by_key(catalog: dict) -> dict[str, dict]:
    return {item["contentKey"]: item for item in catalog["items"]}


def resolve_keys(catalog: dict, keys: list[str]) -> list[dict]:
    index = index_by_key(catalog)
    resolved: list[dict] = []
    visiting: set[str] = set()
    emitted: set[str] = set()

    def visit(key: str) -> None:
        if key in emitted:
            return
        if key in visiting:
            raise ValueError(f"Content-Pool: Zyklus bei {key}")
        item = index.get(key)
        if item is None:
            raise ValueError(f"Content-Pool: unbekannter contentKey: {key}")
        visiting.add(key)
        for child in item.get("children", []):
            visit(child)
        visiting.remove(key)
        if item.get("type", "TOPIC").upper() != "COLLECTION":
            resolved.append(item)
        emitted.add(key)

    for key in keys:
        visit(key)
    return resolved


def output_patterns_for(items: list[dict], audience: set[str] | None = None) -> list[str]:
    patterns: list[str] = []
    for item in items:
        for source in item.get("sources", []):
            patterns.extend(source.get("outputs", []))
        for resource in item.get("resources", []):
            resource_audience = str(resource.get("audience", "TEACHER")).upper()
            if audience is None or resource_audience in audience:
                patterns.extend(resource.get("outputs", []))
    return list(dict.fromkeys(patterns))


def validate_catalog(repo: Path) -> dict:
    return load_catalog(repo / "ContentPool" / "catalog.json")
