# Source Code Game

Dự án reconstruction / reverse-engineering / development cho Ninja School Online / Ninja School Offline.

## 🗂️ Repository layout

```text
BASELINES/     Các bản JAR game GỐC/CHƯA CHỈNH SỬA, chia theo version.
UPGRADE/       Các bản đã mod/nâng cấp/test. Không thay thế BASELINES.
DATA/          Dữ liệu game, Item ID, Option ID, RMS, manifests, extracted data.
PROMPTS/       Prompt cho AI/Codex, yêu cầu, handoff và task đang thực hiện.
source/        Source reconstructed/decompiled và source đang phát triển.
reference/     Reference cũ: mod JAR, original references, archive packages.
server/        Server-side source/data/reference.
client/        Client-side source/reference.
docs/          Context, architecture, changelog, evidence, build notes.
scripts/       Script phân tích/build/helper.
```

Xem chi tiết quy tắc tại `docs/REPO_STRUCTURE.md`.

## ⭐ Current status

### Active clean baseline

**25.47** — `NinjaSchoolOffline_v1.25.47.jar`

Đây là bản gốc sạch để làm nền cho các nâng cấp tiếp theo. Không chỉnh sửa trực tiếp bản này.

### Latest accepted upgrade

**25.47 — Admin_v4**

`UPGRADE/25.47/Admin_v4/`

User đã xác nhận trên AngelChip:
- Game chạy bình thường.
- F1 → Admin không đơ/crash.
- `Thêm Item` hoàn chỉnh và hoạt động đúng yêu cầu.
- `Xóa Hành Trang` chưa hoàn thành và đang để pending.

## 🔄 Quy trình cập nhật version

Khi nhận JAR mới:

```text
JAR mới
  ↓
BASELINES/<version>/        ← giữ nguyên bản sạch
  ↓
DIFF với baseline cũ
  ↓
phân tích modification/conflict
  ↓
reapply chức năng đã hoàn thiện
  ↓
UPGRADE/<version>/<feature>/
  ↓
AngelChip test
  ↓
ACCEPTED / TESTING / BROKEN
```

**Không bao giờ dùng bản upgrade để ghi đè bản baseline.**

Nếu 25.47 là bản mới nhất được người dùng gửi, nó trở thành `ACTIVE BASELINE`; 25.27 vẫn giữ lại như historical reference để diff/research.

## 🤖 AI onboarding

AI/agent phải đọc:

1. `docs/PROJECT_CONTEXT.md`
2. `docs/USER_REQUIREMENTS_AND_RULES.md`
3. `docs/CONVERSATION_CONTEXT.md`
4. `docs/REFERENCE_FILES.md`
5. `docs/CODEX_START_HERE.md`
6. `docs/AI_CONTEXT.md`
7. `docs/REPO_STRUCTURE.md`

## 🔒 Core rules

1. Không coi Offline JAR là original source duy nhất.
2. Không trộn original, offline, modded, server và emulator/RMS data thành một reference.
3. Không overwrite bản cũ khi có version mới.
4. Mọi baseline quan trọng phải có version, SHA-256, nguồn và trạng thái.
5. Modification đã được xác nhận phải được ghi lại và có thể reapply sang version mới.
6. Obfuscated symbol chỉ rename khi có evidence.
7. Mỗi upgrade phải ghi rõ behavior trước/sau, compatibility impact và kết quả test.
8. Binary lớn nên dùng Git LFS; metadata luôn để dạng text để AI dễ đọc.

## 📦 Binary policy

JAR/ZIP/RAR lớn không nên được biến thành file text hoặc overwrite trực tiếp qua tài liệu. Khi Git LFS được bật, binary sẽ nằm đúng thư mục version tương ứng; README/RELEASE metadata mô tả artifact và checksum.

## 📚 Reference hierarchy

- `reference/modded/` — chức năng mod cũ cần nghiên cứu.
- `reference/original/` — original source/reference.
- `reference/archives/` — package/archive lịch sử.
- `BASELINES/` — game baseline sạch theo version.
- `UPGRADE/` — build đã chỉnh sửa.
- `DATA/` — dữ liệu trích xuất/phân tích.
- `PROMPTS/` — yêu cầu/handoff cho AI.
