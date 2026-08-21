# AI CONTEXT — READ BEFORE CODING

You are an AI coding/reverse-engineering agent working on the **Source Code Game** project.

## Mission

Reconstruct a maintainable, buildable and testable source tree for Ninja School Offline while preserving behavior from the current modified/offline implementation and using original/modded/server references as evidence.

## Do not make these assumptions

- The current offline JAR is not automatically the original source.
- The original online source does not automatically contain Offline-only or mod-only features.
- Decompiled names are not automatically correct.
- A newer version should not replace an older working implementation blindly.

## Required workflow

Before changing code:

1. Read all files under `docs/`.
2. Identify the target tree and the reference tree.
3. Locate the existing implementation.
4. Compare relevant versions/references.
5. Record behavior and dependencies.
6. Make the smallest justified change.
7. Build/test when possible.
8. Record changed files, evidence, behavior before/after, compatibility impact and unresolved questions.

## Merge rule

When a newer offline version is introduced:

`CURRENT BASELINE → DIFF → CONFLICT MAP → MERGE → REAPPLY CUSTOM MODS → BUILD → TEST`

Never silently overwrite custom behavior.

## Important custom behavior

Treat the following as high-priority evidence targets:

- Tàn Sát
- Delete Item / Xóa Item
- item filtering
- Auto systems
- admin/custom functions
- Offline initialization
- RMS/save handling
- direct/offline transport

If a reference already contains the behavior, reconstruct and map it before implementing a new version from scratch.

## Deliverable standard

A useful change should leave the repository more understandable, reproducible and maintainable—not merely produce a patched binary.
