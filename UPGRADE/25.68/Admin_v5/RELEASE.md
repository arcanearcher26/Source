# Admin_v5 — 25.68 (candidate)

Baseline: `NinjaSchoolOffline_v1.25.68.jar`

Status: **TESTING / PARTIAL**

This candidate contains:

- an `Admin` entry added to the F1 menu;
- `Xóa Hành Trang` wired to the native 25.68 inventory slot reset routine;
- the normal save and refresh calls after every inventory slot is reset;
- the message `Đã xóa toàn bộ hành trang.` after the save path completes.

The original Admin_v4 binary is not present in the repository, so `Thêm Item`
has not been claimed as restored in this candidate. It currently remains a
visible placeholder while the 25.68 input callback contract is reconstructed.

This artifact must be tested on AngelChip with a disposable RMS/save state.
It is not an accepted release until the runtime test confirms startup, F1 →
Admin, inventory clearing (including locked/upgraded items), save persistence,
and return/exit behavior.
