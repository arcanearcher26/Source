# GitHub workflow

## First setup

```bash
git clone <YOUR_REPOSITORY_URL>
cd Source-Code-Game
git lfs install
git lfs pull
```

## Recommended branches

- `main`: stable baseline
- `develop`: integration branch
- `codex/*`: isolated AI work

## Typical AI change

```text
issue/task
  ↓
create codex/* branch
  ↓
read docs + inspect references
  ↓
implement/reconstruct
  ↓
build/test
  ↓
commit
  ↓
Pull Request
  ↓
review
  ↓
merge to develop/main
```

## Large files

This repository tracks JAR/ZIP/RAR references with Git LFS. Run `git lfs install` before pushing.
