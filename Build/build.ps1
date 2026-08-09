param(
    [string]$Bereich = "",
    [switch]$Clean
)

$ErrorActionPreference = "Stop"

$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$SourceRoot = Join-Path $RepoRoot "Unterrichtsmaterial_Oberschule_Sachsen"
$OutputRoot = Join-Path $RepoRoot "Ausgabe"
$WorkRoot = Join-Path $RepoRoot ".build"

if (-not (Get-Command pandoc -ErrorAction SilentlyContinue)) {
    throw "Pandoc wurde nicht gefunden. Bitte Pandoc installieren und erneut ausführen."
}

if ($Clean) {
    if (Test-Path $OutputRoot) { Remove-Item $OutputRoot -Recurse -Force }
    if (Test-Path $WorkRoot) { Remove-Item $WorkRoot -Recurse -Force }
}

New-Item -ItemType Directory -Force -Path $OutputRoot | Out-Null
New-Item -ItemType Directory -Force -Path $WorkRoot | Out-Null

$SourceBase = $SourceRoot
if ($Bereich) {
    $SourceBase = Join-Path $SourceRoot $Bereich
    if (-not (Test-Path $SourceBase)) {
        throw "Bereich nicht gefunden: $SourceBase"
    }
}

function Get-RelativePath([string]$Base, [string]$Path) {
    return [System.IO.Path]::GetRelativePath($Base, $Path)
}

function Build-MarkdownDirectory([System.IO.DirectoryInfo]$Dir) {
    $mdFiles = Get-ChildItem $Dir.FullName -File -Filter "*.md" |
        Where-Object { $_.Name -notmatch '^README\.md$' } |
        Sort-Object Name

    if ($mdFiles.Count -eq 0) {
        return
    }

    $relative = Get-RelativePath $SourceRoot $Dir.FullName
    $relativeParent = Split-Path $relative -Parent
    $docName = Split-Path $relative -Leaf

    if ([string]::IsNullOrWhiteSpace($relativeParent)) {
        $outDir = $OutputRoot
    } else {
        $outDir = Join-Path $OutputRoot $relativeParent
    }

    New-Item -ItemType Directory -Force -Path $outDir | Out-Null

    $combined = Join-Path $WorkRoot (($relative -replace '[\\/:*?"<>|]', '_') + ".md")
    $builder = New-Object System.Text.StringBuilder

    $readme = Join-Path $Dir.FullName "README.md"
    if (Test-Path $readme) {
        [void]$builder.AppendLine((Get-Content $readme -Raw -Encoding UTF8))
        [void]$builder.AppendLine()
        [void]$builder.AppendLine("\newpage")
        [void]$builder.AppendLine()
    }

    foreach ($file in $mdFiles) {
        [void]$builder.AppendLine((Get-Content $file.FullName -Raw -Encoding UTF8))
        [void]$builder.AppendLine()
        [void]$builder.AppendLine("\newpage")
        [void]$builder.AppendLine()
    }

    [System.IO.File]::WriteAllText($combined, $builder.ToString(), [System.Text.UTF8Encoding]::new($false))

    $docx = Join-Path $outDir "$docName.docx"
    $html = Join-Path $outDir "$docName.html"
    $pdf  = Join-Path $outDir "$docName.pdf"

    Write-Host "BUILD $relative"

    & pandoc $combined `
        --from markdown `
        --to docx `
        --standalone `
        --toc `
        --metadata lang=de-DE `
        --output $docx

    if ($LASTEXITCODE -ne 0) { throw "DOCX-Build fehlgeschlagen: $relative" }

    & pandoc $combined `
        --from markdown `
        --to html5 `
        --standalone `
        --toc `
        --metadata lang=de-DE `
        --output $html

    if ($LASTEXITCODE -ne 0) { throw "HTML-Build fehlgeschlagen: $relative" }

    $pdfEngine = $null
    foreach ($engine in @("xelatex", "lualatex", "pdflatex")) {
        if (Get-Command $engine -ErrorAction SilentlyContinue) {
            $pdfEngine = $engine
            break
        }
    }

    if ($pdfEngine) {
        & pandoc $combined `
            --from markdown `
            --to pdf `
            --standalone `
            --toc `
            --metadata lang=de-DE `
            --pdf-engine=$pdfEngine `
            --output $pdf

        if ($LASTEXITCODE -ne 0) {
            Write-Warning "PDF-Build fehlgeschlagen: $relative"
        }
    }
}

$directories = Get-ChildItem $SourceBase -Directory -Recurse
foreach ($dir in $directories) {
    Build-MarkdownDirectory $dir
}

# Falls der gewählte Bereich selbst Markdown-Dateien enthält
if ((Get-Item $SourceBase) -is [System.IO.DirectoryInfo]) {
    Build-MarkdownDirectory (Get-Item $SourceBase)
}

if (Test-Path $WorkRoot) {
    Remove-Item $WorkRoot -Recurse -Force
}

Write-Host ""
Write-Host "Build abgeschlossen."
Write-Host "Ausgabe: $OutputRoot"
