# Release status

| Version | Type | Build | Status | Confirmed behavior |
|---|---|---|---|---|
| 25.27 | baseline | clean | HISTORICAL | Original project reference |
| 25.47 | baseline | clean | ACTIVE | Current upstream/clean baseline |
| 25.47 | upgrade | Admin_v4 | ACCEPTED | Admin + Thêm Item works on AngelChip |
| 25.47 | upgrade | Admin_v4 | PENDING feature | Xóa Hành Trang not completed |

## Status meanings

- `ACTIVE`: newest clean game baseline.
- `HISTORICAL`: retained for comparison; never deleted.
- `TESTING`: modified build awaiting device confirmation.
- `ACCEPTED`: user confirmed the requested behavior works.
- `BROKEN`: known failing build retained for debugging/history.
- `PENDING`: build works generally but a requested feature remains unfinished.

## Artifact rule

A clean baseline and its upgrade are always separate artifacts. A newer clean version never overwrites the history of an older clean version, and an upgrade never replaces its clean baseline.
