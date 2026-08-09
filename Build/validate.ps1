param(
    [string]$Bereich = ""
)

$ErrorActionPreference = "Stop"

$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$SourceRoot = Join-Path $RepoRoot "Unterrichtsmaterial_Oberschule_Sachsen"
$Target = if ($Bereich) { Join-Path $SourceRoot $Bereich } else { $SourceRoot }

if (-not (Test-Path $Target)) {
    throw "Bereich nicht gefunden: $Target"
}

$errors = @()

Get-ChildItem $Target -Recurse -File -Filter "*.md" | ForEach-Object {
    $content = Get-Content $_.FullName -Raw -Encoding UTF8

    $h1Count = ([regex]::Matches($content, '(?m)^# [^#]')).Count
    if ($h1Count -eq 0) {
        $errors += "Keine H1: $($_.FullName)"
    }

    if ($_.Name -match '\s') {
        $errors += "Leerzeichen im Dateinamen: $($_.FullName)"
    }
}

if ($errors.Count -gt 0) {
    Write-Host "Validierung fehlgeschlagen:"
    $errors | ForEach-Object { Write-Host " - $_" }
    exit 1
}

Write-Host "Validierung erfolgreich."
