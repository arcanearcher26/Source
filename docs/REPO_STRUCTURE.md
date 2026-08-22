# Repository structure and version policy

## Top-level layout

```text
BASELINES/     Clean, untouched game JAR baselines by version.
UPGRADE/       Tested/accepted upgraded builds. Never replace BASELINES here.
DATA/          Extracted game data, item/option tables, RMS notes, manifests.
PROMPTS/       AI prompts, handoff prompts, task specifications and checklists.
reference/     Historical/reference packages that are not the active baseline.
source/        Reconstructed/decompiled source trees.
server/        Server-side source/data/reference.
client/        Client-side source/reference.
docs/          Project documentation, architecture, changelogs and evidence.
scripts/       Analysis/build/helper scripts.
```

## Version policy

### 1. New game JAR received

A newly supplied game JAR is first treated as the **clean upstream baseline**.

Example:

```text
25.27 clean  -> BASELINES/25.27/
25.47 clean  -> BASELINES/25.47/
25.57 clean  -> BASELINES/25.57/
```

The newest clean version becomes the active baseline, but older baselines remain immutable historical references.

### 2. Upgraded build

Any JAR that contains project modifications goes under `UPGRADE/` and never replaces the clean baseline.

Example:

```text
UPGRADE/25.47/Admin_v4/
    NinjaSchoolOffline_v1.25.47_Admin_v4_Fixed.jar
    RELEASE.md
    SHA256.txt
```

### 3. Accepted/completed status

Only a build explicitly confirmed by the user as working is marked `STATUS: ACCEPTED`.

Unconfirmed or broken builds stay in their versioned upgrade folder with their test status preserved.

### 4. Old versions

Never delete an old JAR/source/reference just because a newer version exists. Historical files are evidence used for diffing and reapplying modifications.

## Binary storage

Large JAR/ZIP/RAR files should use Git LFS. Text metadata must remain in Git so an AI agent can understand the artifact without downloading it.

## Required metadata for every important build

- version
- artifact name
- SHA-256
- baseline used
- modifications included
- status: `UNTESTED`, `BROKEN`, `TESTING`, `ACCEPTED`
- AngelChip/device test result
- known limitations
- date

## Current project status

- Clean baseline: **25.47**
- Accepted upgrade: **25.47 Admin_v4** — `Thêm Item` confirmed working by user.
- `Xóa Hành Trang`: not yet completed; do not mark as accepted.
