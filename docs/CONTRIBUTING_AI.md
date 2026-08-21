# AI Contribution Rules

## Before work

- Read `docs/AI_CONTEXT.md`.
- Inspect the relevant source and references.
- Do not edit files under `reference/archives/` directly.

## During work

- Prefer evidence-backed, minimal changes.
- Keep client/server concerns separate.
- Preserve existing custom behavior.
- Do not mass-rename obfuscated classes without a mapping/evidence record.
- Do not delete code just because it looks unused until dependencies are checked.

## After work

Report:

- Changed files
- Why they changed
- References consulted
- Previous behavior
- New behavior
- Compatibility impact
- Build/test result
- Remaining uncertainty

## Git discipline

Use a feature branch for substantial AI work. Keep `main` stable.
Recommended branch examples:

- `codex/reconstruct-client`
- `codex/reconstruct-server`
- `codex/merge-offline-version`
- `codex/rms-analysis`

Do not force-push or rewrite shared history unless explicitly requested.
