# PROJECT STATUS — 2026-08-21

## Current known assets

- Current offline client JAR: `NinjaSchoolOffline_2.1.7_v1.25.27.jar`
- Offline Termux server package: `Ninja_Server_Termux-offline_217.zip`
- Offline suite/emulator package: `suite-NinjaSchoolOffline.rar`
- Earlier Codex handoff pack existed, but did not include binaries.

## Current known architecture signals

The JAR explicitly identifies an embedded CLDC offline server and direct CLDC RMS transport. It contains save-state and offline error/startup markers, as well as auto/menu strings.

## Known project direction

The project is moving from "inspect a JAR" toward:

**reference collection → diff → behavior reconstruction → source reconstruction → modification preservation → maintainable codebase → future updates/admin changes**

## Next priority

Do a structured baseline reconstruction of the current JAR and server package before making new feature changes. Build a mapping of important obfuscated classes/methods and record confidence.
