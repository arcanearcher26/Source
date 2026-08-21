# CODEX START HERE

You are taking over an existing reverse-engineering/reconstruction project named **Source Code Game**, focused on **Ninja School Online / Ninja School Offline**.

## Immediate instructions

1. Read all files in `project_context/` before modifying anything.
2. Treat the three files in `references/` as immutable reference inputs until their contents are intentionally imported into a working tree.
3. Do not assume the current offline JAR is the original game source.
4. Do not assume the original online source contains the offline/mod features.
5. Maintain a clear source/reference hierarchy.
6. Before applying an upstream/new offline version, produce a diff against the current baseline and list conflicts.
7. Preserve old modifications and reapply them explicitly.
8. Never silently overwrite a working version.
9. For obfuscated classes, rename only when supported by evidence.
10. Keep a changelog of reconstructed mappings and confidence level.

## First technical pass

### A. Inventory
- Enumerate all JAR classes/resources.
- Identify packages/classes that are server, client, UI, map, item, skill, auto, admin, save/RMS, networking and data loaders.
- Extract strings and resources.
- Identify entry points (`GameMidlet`, main bootstrap, server startup).

### B. Offline architecture
Trace:
- startup;
- offline server initialization;
- transport (`direct-cldc-rms`);
- packet/command dispatch;
- save/load;
- RMS record stores;
- map/world initialization;
- admin seed and version files.

### C. Modification discovery
Search current and historical/modded JARs for:
- Tàn Sát;
- Delete Item / Xóa Item;
- item filter;
- Auto features;
- admin features;
- other non-original-online functions.

Do not implement these from scratch if the behavior already exists in a reference JAR. First reconstruct the existing behavior and mapping.

### D. Server comparison
Compare the offline server package against the client JAR assumptions:
- data versions;
- item versions;
- map versions;
- skill versions;
- account/save structures;
- event data;
- scripts.

### E. RMS/emulator
Reproduce a clean-state test workflow. Distinguish:
- JAR bundled data;
- RMS data;
- emulator cached state;
- server-side data.

## Expected working style

For every change, report:
- files changed;
- reason;
- source/reference used;
- old behavior;
- new behavior;
- compatibility impact;
- test performed;
- unresolved questions.

## Definition of success

A successful result is not merely a decompiled JAR. The project should move toward a maintainable, buildable, testable source tree whose behavior matches the required offline/modded version and whose historical modifications can survive future updates.
