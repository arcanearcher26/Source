# Build artifact — Admin menu

Generated from the supplied offline client JAR `NinjaSchoolOffline_2.1.7_v1.25.27.jar`.

## Implemented

- Existing `Fake` catalog entry is relabeled to `Admin`.
- Admin opens a dedicated menu containing:
  - `Thêm Item` — routes to the existing item-add flow (`1100076`).
  - `Xóa Hành Trang` — clears the active player's normal inventory slots and triggers the existing save path.
- The implementation is packaged as `NinjaSchoolOffline_2.1.7_v1.25.27_Admin.jar`.

## Verification

- JAR archive test passes with `unzip -t`.
- Classes remain Java class-file major version 47, matching the original CLDC-era bytecode.
- The patched `ba` dispatch table routes catalog action `110007930` to `AdminBridge.show()` and action `110051` to `AdminBridge.clearInventory()`.

## Important limitation

This environment cannot launch the original CLDC/J2ME runtime, so runtime UI interaction cannot be executed here. The artifact was bytecode-validated, archive-validated, and cross-checked against the original dispatch table.
