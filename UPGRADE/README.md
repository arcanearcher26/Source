# UPGRADE — accepted and test builds

This folder contains **modified builds**, separate from clean `BASELINES/`.

Current accepted build:

```text
UPGRADE/25.47/Admin_v4/
```

Status:
- `Admin_v4`: **ACCEPTED** for `Thêm Item` — user confirmed it runs on AngelChip and the feature works.
- `Xóa Hành Trang`: **PENDING** — user has explicitly deferred this feature.

Versioned upgrade builds must never replace the corresponding clean baseline.

Each release folder should contain:
- JAR
- `RELEASE.md`
- SHA-256
- baseline/version used
- feature list
- test result
- known limitations
