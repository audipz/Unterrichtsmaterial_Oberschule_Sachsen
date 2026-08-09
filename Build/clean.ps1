$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path

foreach ($name in @("Ausgabe", ".build")) {
    $path = Join-Path $RepoRoot $name
    if (Test-Path $path) {
        Remove-Item $path -Recurse -Force
        Write-Host "Gelöscht: $path"
    }
}
