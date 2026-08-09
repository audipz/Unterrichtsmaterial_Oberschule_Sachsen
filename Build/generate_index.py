#!/usr/bin/env python3
from pathlib import Path
from html import escape

root = Path(__file__).resolve().parents[1]
out = root / "Ausgabe"
out.mkdir(exist_ok=True)

files = sorted(
    p for p in out.rglob("*")
    if p.is_file() and p.suffix.lower() in {".html", ".pdf", ".docx"} and p.name != "index.html"
)

parts = [
    "<!doctype html><html lang='de'><head><meta charset='utf-8'>",
    "<meta name='viewport' content='width=device-width,initial-scale=1'>",
    "<title>Unterrichtsmaterial Oberschule Sachsen</title>",
    "<style>body{font-family:Arial,sans-serif;max-width:1100px;margin:auto;padding:2rem;line-height:1.5}li{margin:.4rem 0}</style>",
    "</head><body><h1>Unterrichtsmaterial Oberschule Sachsen</h1>",
    "<p>Automatisch erzeugte DOCX-, PDF- und HTML-Dokumente.</p><ul>"
]
for p in files:
    rel = p.relative_to(out)
    parts.append(f"<li><a href='{escape(rel.as_posix())}'>{escape(rel.as_posix())}</a></li>")
parts.append("</ul></body></html>")
(out/"index.html").write_text("\n".join(parts), encoding="utf-8")
