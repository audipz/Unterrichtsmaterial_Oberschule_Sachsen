# SQLite installieren

## macOS

SQLite ist auf vielen macOS-Systemen bereits vorhanden.

Prüfen:

```bash
sqlite3 --version
```

Falls SQLite nicht vorhanden ist oder eine aktuelle Version benötigt wird:

```bash
brew install sqlite
```

## Linux

Beispiel Debian/Ubuntu:

```bash
sudo apt update
sudo apt install sqlite3
```

## Windows

SQLite kann als Kommandozeilenwerkzeug installiert werden.

Alternativ kann DB Browser for SQLite verwendet werden.

## Prüfung

```bash
sqlite3 --version
```

Wenn eine Versionsnummer erscheint, ist das Werkzeug verfügbar.
