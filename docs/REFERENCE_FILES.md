# REFERENCE FILE INVENTORY

## 1. NinjaSchoolOffline_2.1.7_v1.25.27.jar

**SHA-256:** `90fd7ca412f2a448aa8af3d4fae3c90152874f45bfecdca296ed34b7358f0dc9`

Manifest:
- `MIDlet-Name: NinjaSchoolOffline`
- `MIDlet-Version: 1.25.27`
- `MIDlet-Vendor: KhanhNguyen9872`
- `Offline-Server: embedded-cldc`
- `Offline-Transport: direct-cldc-rms`
- `Code-Obfuscation: ProGuard-name-only`

Inventory:
- 219 class files
- 534 non-class entries

Notable strings/resources found:
- `javax/microedition/rms/RecordStore`
- `ninja_school_offline_v3`
- `nso_offline_e72_v2`
- `/offline/admin_seed.rms`
- `/offline/version.bin`
- `offlineCommand`
- `offlineReceive`
- `serverLogin`
- `MenuAuto`
- `Auto Nhi`
- `Auto T`
- `Auto Reconnect`
- `Auto TTT`
- `Auto recover`
- `AutoTTC`

This is evidence of embedded offline server/RMS/state and a substantial auto/offline layer.

## 2. Ninja_Server_Termux-offline_217.zip

**SHA-256:** `85bfdec00f9e50cb89d13dd170e41b5352328bb2c57d93814f79c314b1e6677c`

Package contents include:
- `README.md`
- `DEMO.md`
- `install.sh`
- `script_install.sh`
- `khanhupdate.sh`
- `menu.sh`
- `CONF_FILE/`
- `bin32/`
- `binx64/`
- `info/`
- `lib/`
- `tamp/`
- Termux APK
- MySQL/mysql dump binaries
- native `.so` libraries
- game/config/event data under `info/`

README identifies it as **Ninja School Server on Termux** and documents Android/architecture compatibility and installation.

Package info reports version `10.6` and update date `08/12/2022`; its internal `info/version` is `106`.

## 3. suite-NinjaSchoolOffline.rar

**SHA-256:** `6feb78551065066168d8847f119b689cd37aeaddea600b034113a06d371a0e5f`

Reference for the NinjaSchoolOffline suite/emulator environment. In the project history it is associated with AngelChip/.microemulator and RMS/save-state investigation.

The current runtime did not have a RAR extraction binary available during package creation, so the archive is preserved byte-for-byte and should be inspected in Codex/appropriate tooling if its internal files are needed.
