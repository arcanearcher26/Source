#!/usr/bin/env bash
set -euo pipefail
python3 - <<'PY'
import hashlib, json, pathlib
root=pathlib.Path('.')
manifest=json.loads((root/'docs/REFERENCE_MANIFEST.json').read_text())
for item in manifest['references']:
    p=root/'reference'/'archives'/item['file']
    if not p.exists(): raise SystemExit(f'Missing: {p}')
    h=hashlib.sha256()
    with p.open('rb') as f:
        for chunk in iter(lambda:f.read(1024*1024), b''): h.update(chunk)
    actual=h.hexdigest()
    expected=item['sha256'].lower()
    if actual != expected: raise SystemExit(f'SHA256 mismatch: {p}\nExpected: {expected}\nActual:   {actual}')
    print(f'OK {p} {actual}')
PY
