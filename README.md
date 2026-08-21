# Source Code Game

Dự án reconstruction/reverse-engineering Ninja School Online / Ninja School Offline.

> **Quan trọng:** Đây không phải dự án tạo một game mới từ đầu. Mục tiêu là xây dựng một source tree có thể duy trì, build và test được, dựa trên nhiều tầng reference, đồng thời bảo toàn các modification đã tồn tại trong bản Offline/Modded.

## AI onboarding

AI/agent phải đọc theo thứ tự:

1. `docs/PROJECT_CONTEXT.md`
2. `docs/USER_REQUIREMENTS_AND_RULES.md`
3. `docs/CONVERSATION_CONTEXT.md`
4. `docs/REFERENCE_FILES.md`
5. `docs/CODEX_START_HERE.md`
6. `docs/AI_CONTEXT.md`

## Source hierarchy

- `source/reconstructed/` — **target codebase**, nơi source được phục hồi và phát triển.
- `source/original/` — original/base source nếu có.
- `source/offline/` — source extracted/reconstructed từ offline implementation.
- `reference/modded/` — các bản mod/reference dùng để tìm hiểu chức năng custom.
- `reference/original/` — original references.
- `reference/archives/` — binary/reference packages được bàn giao ban đầu; không chỉnh sửa trực tiếp.
- `server/` — server-side working tree.
- `client/` — client-side working tree.

## Core rules

1. Không coi Offline JAR hiện tại là original source duy nhất.
2. Không giả định original online source có các chức năng mod của Offline.
3. Trước khi cập nhật version: **diff → phân tích conflict → merge → reapply modification → build/test**.
4. Không âm thầm overwrite working version.
5. Tàn Sát, Delete Item, item filtering, Auto và các chức năng custom khác là behavior cần được bảo toàn khi có bằng chứng.
6. Obfuscated symbols chỉ được rename khi có evidence.
7. Mỗi thay đổi phải ghi rõ reference, lý do, compatibility impact và test.

## Reference binaries

Các binary ban đầu nằm trong `reference/archives/`. SHA-256 được lưu trong `docs/REFERENCE_MANIFEST.json`.

Nếu dùng GitHub cho cộng tác AI, nên bật Git LFS trước khi push các binary lớn.
