# Lösung – SQL Constraints und Schlüssel

| Anforderung | Constraint |
|---|---|
| Name muss vorhanden sein | `NOT NULL` |
| E-Mail darf nicht doppelt vorkommen | `UNIQUE` |
| ID identifiziert Datensatz | `PRIMARY KEY` |
| Klasse muss existieren | `FOREIGN KEY` |

Ein Primärschlüssel kann aus mehreren Spalten bestehen.

Ein alternatives eindeutiges Merkmal kann zusätzlich mit `UNIQUE` abgesichert werden.
