# Build artifact — Admin menu (25.47)

Baseline: `NinjaSchoolOffline_v1.25.47.jar`.

## v4 runtime fix

The previous v3 bridge placed direct references to the item/inventory implementation in the same class that is loaded merely to open the Admin menu. On AngelChip/J2ME this caused the game to hang while opening Admin.

v4 separates the runtime implementation:

- `AdminBridge.show()` contains only the lightweight Admin-menu UI construction.
- `AdminBridge.addItem()` and `AdminBridge.clearInventory()` lazily delegate to `AdminAction`, so the heavy game/item references are not part of the menu-opening path.
- `AdminAction.addItem()` invokes the native item-input command `30014`.
- `AdminAction.clearInventory()` resets every real inventory slot and then uses the normal save path.

## Native item flow

The offline JAR already contains the intended Admin/NPC item-receive pipeline in `k1f`:

1. Native input command `30014` uses `ID item [SL] [nâng cấp] [hệ] [khóa] [hạn ngày]`.
2. The response handler validates the Item ID against the JAR item DB.
3. The native option step `30015` accepts `id:param,id:param` and an empty value means the item's DB/default options.
4. The native item factory creates the item and puts it into inventory.

The MOD therefore reuses the game's existing pipeline rather than MenuAuto/Auto Nhặt or a parallel serializer.

## Inventory wipe

`AdminAction.clearInventory()` gets the current player, loops through the actual inventory array, calls the native slot reset routine for every slot, then calls the normal save functions. This is intended to remove normal, locked and upgraded items rather than opening the player-information screen.

## Verification

- JAR archive test: PASS (`unzip -t`).
- Manifest/MIDlet preserved from 25.47.
- Added classes are Java class-file major version 47.
- `AdminBridge.show()` has no direct item/inventory implementation references.
- `AdminAction` contains the native item and inventory operations.
- Final v4 artifact SHA-256: `c4cbabbdef93c9c363aa457aeb70c77acc249e107df21cf424011f8cbc1e9a80`.

## Device verification

The structural/bytecode checks pass here. AngelChip remains the required final runtime test.
