# Build artifact — Admin menu

Generated from the supplied offline client JAR `NinjaSchoolOffline_2.1.7_v1.25.27.jar`.

## Implemented

- Existing `Fake` catalog entry (`110007930`) is relabeled to `Admin`.
- Admin opens a dedicated menu containing:
  - `Thêm Item` (`110007931`) — routes into the existing item-receive/add flow (`1100076`), preserving the JAR's native item form and validation.
  - `Xóa Hành Trang` (`110007932`) — clears every normal inventory slot through the game's own inventory-slot reset routine, including locked slots, then invokes the existing save path.
- A small `AdminBridge.class` was added to keep the bytecode patch minimal and isolated.

## Verification

- JAR archive test passes with `unzip -t`.
- All classes remain Java class-file major version 47, matching the original CLDC-era bytecode.
- The patched `ba` dispatch table routes `110007930`, `110007931`, and `110007932` to the bridge methods.
- SHA-256: `bbaf6f5a29f0fa1ad66db50466227abbf09070649be705b18aeb09ff147ed99a`.

## Runtime note

This environment cannot launch the original CLDC/J2ME runtime, so physical UI interaction cannot be executed here. The artifact was bytecode-validated, archive-validated, and cross-checked against the original dispatch table and inventory reset implementation.
