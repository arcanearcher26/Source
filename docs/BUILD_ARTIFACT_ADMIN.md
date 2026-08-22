# Build artifact — Admin menu (25.47)

Baseline: `NinjaSchoolOffline_v1.25.47.jar` supplied for the current test cycle.

## Native item flow

The offline JAR already contains the intended Admin/NPC item-receive pipeline in `k1f`:

1. The UI opens input command `30014` with the native prompt `ID item [SL] [nâng cấp] [hệ] [khóa] [hạn ngày]`.
2. The response handler validates the Item ID against the JAR item DB (`k17.H(id)`).
3. Quantity defaults to 1, upgrade defaults to 0, system defaults to 0, lock defaults to false, expiry defaults to permanent.
4. The handler then opens command `30015` with `Option id:param, id:param (trống = theo DB)`.
5. The option handler accepts up to 20 entries, validates each option ID through the JAR option DB (`k17.I(id)`), and creates the item through the native item factory.

The MOD Admin `Thêm Item` therefore calls the same native command `30014`; it does not use MenuAuto/Auto Nhặt and does not implement a parallel item serializer.

## Inventory wipe

`Xóa Hành Trang` obtains the current player with `KhanhNguyen9872.E()`, loops over the real inventory array `player.f`, and calls the game's native slot reset `KhanhNguyen9872.b(player, slot)` for every slot. That reset clears item ID, quantity/upgrade-related state, lock state, expiry and option arrays. The normal save path is then invoked.

## External Item-ID cross-check

Public catalogs independently list ID `325` as **Huyền Thiết Tuyến**. One current catalog exposes 1,255 items and supports lookup by ID/name; older community catalogs also agree on the 325 mapping. External catalogs are only cross-check references: the JAR's own DB remains authoritative at runtime. citeturn1search0turn1search2

## Current artifact

`NinjaSchoolOffline_v1.25.47_Admin_v3.jar`

- SHA-256: `65ca94122905f54c32e47cbc8e3e724a11553257e97bb5253ce20ec86925326c`
- JAR archive test: PASS.
- Manifest/MIDlet preserved from 25.47.
- Patched class-file major version: 47.
- `AdminBridge` has no reflection.
- The native prompt text was aligned with the exact 25.47 NPC/Admin item prompt rather than the shortened earlier `ID Item` label.

## Device verification

The JAR is structurally and bytecode validated here. AngelChip must be used for the final UI interaction test because this environment does not contain the AngelChip/J2ME runtime.
