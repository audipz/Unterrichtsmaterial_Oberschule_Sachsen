#!/usr/bin/env python3
from pathlib import Path
from html import escape

root = Path(__file__).resolve().parents[1]
out = root / "Ausgabe"
out.mkdir(exist_ok=True)

extensions = {".html", ".pdf", ".docx"}
files = sorted(
    p for p in out.rglob("*")
    if p.is_file() and p.suffix.lower() in extensions and p.name != "index.html"
)

groups = {}
for p in files:
    rel = p.relative_to(out)
    group = str(rel.parent)
    groups.setdefault(group, []).append(rel)

parts = [
    "<!doctype html>",
    '<html lang="de">',
    "<head>",
    '<meta charset="utf-8">',
    '<meta name="viewport" content="width=device-width, initial-scale=1">',
    "<title>Unterrichtsmaterial Oberschule Sachsen</title>",
    "<style>",
    "body{font-family:Arial,sans-serif;max-width:1100px;margin:0 auto;padding:2rem;line-height:1.45}",
    "h1{margin-bottom:.2rem}h2{margin-top:2rem;border-bottom:1px solid #ddd;padding-bottom:.3rem}",
    "ul{line-height:1.8}.ext{display:inline-block;min-width:3.5rem;font-weight:bold}",
    "a{text-decoration:none}a:hover{text-decoration:underline}",
    "</style>",
    "</head><body>",
    "<h1>Unterrichtsmaterial Oberschule Sachsen</h1>",
    "<p>Automatisch erzeugte Veröffentlichungsformate aus den Markdown-Quellen.</p>",
]
for group, entries in groups.items():
    parts.append(f"<h2>{escape(group)}</h2><ul>")
    for rel in entries:
        parts.append(
            f'<li><span class="ext">{escape(rel.suffix[1:].upper())}</span> '
            f'<a href="{escape(rel.as_posix())}">{escape(rel.name)}</a></li>'
        )
    parts.append("</ul>")
parts.append("</body></html>")
(out/"index.html").write_text("\n".join(parts), encoding="utf-8")
