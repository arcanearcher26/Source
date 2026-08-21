# CONVERSATION CONTEXT — Project History

> Đây là bản tổng hợp ngữ nghĩa từ các cuộc trao đổi dự án mà hệ thống còn lưu được. Nó không phải transcript đầy đủ/ẩn nguyên văn. Không đưa thông tin cá nhân không cần thiết vào source code.

## 2026-08-19 — Đọc/khảo sát file JAR

Người dùng cung cấp các file liên quan đến Ninja School Offline để phân tích.

Các điểm đã xác định trong quá trình trao đổi:
- Current offline JAR là một bản đã được rebuild/offline và có modification.
- Không được coi nó là original online source.
- Các mod JAR cũ rất quan trọng vì chứa những chức năng bổ sung mà source online không có.
- Mục tiêu là tìm hiểu logic để sau đó chỉnh source/data bằng Codex.

## 2026-08-19 — AngelChip Emulator / dữ liệu cũ

Người dùng cung cấp `AngelChipEmulatorEXE.zip` và muốn xóa dữ liệu mà AngelChip đã lưu để khi chọn JAR mới, emulator không đồng bộ/khôi phục dữ liệu cũ.

Người dùng sau đó đã xác định được vị trí dữ liệu cần xóa trong emulator và xác nhận workflow: khi cần reset dữ liệu cũ thì xóa vùng lưu đó rồi chạy JAR mới.

Điểm cần nhớ cho Codex:
- Emulator state không đồng nhất với JAR.
- Test JAR phải kiểm soát cả RMS/emulator state.

## 2026-08-19/20 — Mục tiêu lấy/khôi phục source

Người dùng hỏi về khả năng lấy source từ các game online nhỏ như Ninja School Online.

Kết luận định hướng của dự án:
- Game dạng này có thể có client Java ME/J2ME và server Java/database.
- Nếu chỉ có JAR thì có thể decompile/reconstruct ở mức đáng kể, nhưng không đảm bảo lấy lại source gốc 100%.
- Mục tiêu thực tế là khôi phục codebase có behavior tương đương và có thể phát triển tiếp.

## 2026-08-20 — Thứ tự tham chiếu được chốt

Người dùng làm rõ:
1. Current offline JAR là bản rebuilt/offline đã modified.
2. Original online source không chứa các extra mod functions.
3. Các modified JAR/modded versions là reference quan trọng để hiểu các mechanics như Tàn Sát, Delete Item, item filtering.
4. Original source vẫn quan trọng để hiểu phần logic/architecture gốc.
5. Các nguồn phải được giữ tách biệt và so sánh với nhau.

## 2026-08-20 — Quy tắc update

Người dùng yêu cầu khi offline upstream cập nhật:
- diff JAR mới trước;
- bảo toàn modification cũ;
- report conflict;
- reapply modification lên bản mới;
- không overwrite bản cũ một cách mù quáng.

## 2026-08-20 — Làm việc trực tiếp bằng Codex

Người dùng muốn Codex là công cụ chính để tiếp tục chỉnh code/data theo các yêu cầu admin và development.

Do đó context phải giúp Codex biết:
- đâu là baseline;
- đâu là reference;
- đâu là modification;
- đâu là state/emulator data;
- workflow update/diff/reapply;
- mục tiêu cuối cùng là source code có thể tiếp tục phát triển.

## 2026-08-20 — Handoff trước đó

Đã từng tạo một gói `Source-Code-Game-Codex-Pack.zip` gồm context như:
- `AGENTS.md`
- `PROJECT_CONTEXT.md`
- `ARCHITECTURE.md`
- version/merge/history docs

Gói trước **không bao gồm binaries**. Gói hiện tại được tạo để bổ sung context và các reference files hiện có.
