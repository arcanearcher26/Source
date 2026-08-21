$ErrorActionPreference = 'Stop'
$manifest = Get-Content "docs/REFERENCE_MANIFEST.json" | ConvertFrom-Json
foreach ($item in $manifest.references) {
    $path = Join-Path "reference/archives" $item.file
    if (-not (Test-Path $path)) { Write-Error "Missing: $path" }
    $actual = (Get-FileHash $path -Algorithm SHA256).Hash.ToLower()
    $expected = $item.sha256.ToLower()
    if ($actual -ne $expected) { Write-Error "SHA256 mismatch: $($item.file)`nExpected: $expected`nActual:   $actual" }
    Write-Host "OK $($item.file) $actual"
}
