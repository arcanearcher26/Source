# PROJECT CONTEXT — Source Code Game / Ninja School Offline

## 1. Dự án là gì?

Đây là dự án nghiên cứu, phục hồi, tái cấu trúc và phát triển một hệ thống **Ninja School Online / Ninja School Offline** quy mô nhỏ, với mục tiêu cuối cùng là có một codebase có thể hiểu, chỉnh sửa và phát triển tiếp thay vì chỉ phụ thuộc vào JAR đã build.

Tên làm việc trong hội thoại: **Source Code Game**.

Đối tượng chính hiện tại là **Ninja School Offline 2.1.7 / v1.25.27** và các server/source/reference liên quan.

## 2. Mục tiêu dài hạn

- Hiểu cấu trúc client/server của Ninja School.
- Tìm/khôi phục source code cơ bản có thể build/chạy.
- Dùng nhiều bản JAR/server khác nhau để đối chiếu hành vi và logic.
- Khôi phục các phần quan trọng từ bytecode khi source không có.
- Giữ được các thay đổi/mod đã tồn tại trong các bản offline/modded.
- Sau khi có nền tảng code hiểu được, tiếp tục thực hiện các yêu cầu chỉnh sửa trực tiếp như tính năng admin, gameplay, dữ liệu, item, auto, v.v.
- Không phá hủy những modification đã có khi cập nhật một phiên bản mới.

## 3. Thứ tự tham chiếu bắt buộc

### Reference A — Original online source
Đây là nguồn tham chiếu cho kiến trúc/logic gốc khi source có sẵn.

- Dùng để hiểu cách hệ thống vốn được thiết kế.
- Không được mặc định source online đã có tất cả các tính năng của bản offline/modded.
- Không được lấy việc thiếu một tính năng trong source online làm bằng chứng rằng tính năng đó không tồn tại ở bản offline.

### Reference B — Các JAR/mod JAR lâu đời
Đây là nguồn tham chiếu rất quan trọng để tìm các modification đã được thêm vào theo thời gian.

Các bản mod/offline có thể chứa:
- Tàn Sát.
- Delete Item/Xóa item.
- Lọc item/filter item.
- Các menu Auto.
- Các chức năng admin/tiện ích khác.
- Những thay đổi không có trong original online source.

Khi truy tìm một tính năng bị thiếu trong source gốc, phải kiểm tra các JAR mod trước khi kết luận là không tồn tại.

### Reference C — Current offline JAR
File hiện tại:
`NinjaSchoolOffline_2.1.7_v1.25.27.jar`

Manifest xác nhận:
- MIDlet: `NinjaSchoolOffline`
- Version: `1.25.27`
- Offline server: `embedded-cldc`
- Offline transport: `direct-cldc-rms`
- Obfuscation: `ProGuard-name-only`

JAR có 219 `.class` entries và 534 non-class entries theo inventory ngày 2026-08-21.

Đây là **bản offline đã được rebuild/modify**, không được coi là bản online gốc.

### Reference D — Ninja Server Termux Offline 2.17
File:
`Ninja_Server_Termux-offline_217.zip`

Đây là package server/Termux dùng làm reference cho môi trường chạy offline, script cài đặt, data/config và các thành phần native.

README trong package mô tả Ninja School Server on Termux, hỗ trợ ARM 32/64 và x86_64; package có các thư mục `CONF_FILE`, `bin32`, `binx64`, `info`, `lib`, `tamp` và các script cài đặt/menu/update.

### Reference E — Suite NinjaSchoolOffline
File:
`suite-NinjaSchoolOffline.rar`

Đây là reference liên quan đến suite/AngelChip/.microemulator và đặc biệt quan trọng khi nghiên cứu dữ liệu lưu cục bộ/RMS.

Trong các trao đổi trước, mục tiêu đã được xác định là hiểu dữ liệu mà AngelChip đã lưu để có thể phân biệt dữ liệu cũ với dữ liệu của JAR mới.

## 4. Dữ liệu lưu và RMS

Current JAR có các dấu hiệu trực tiếp của offline/RMS storage, gồm:
- `javax/microedition/rms/RecordStore`
- `ninja_school_offline_v3`
- `nso_offline_e72_v2`
- `/offline/`
- `/offline/admin_seed.rms`
- `/offline/version.bin`
- `direct-cldc-rms`

Có các class tên:
- `KhanhNguyen9872`
- `KhanhNguyen9872SaveState`
- `KhanhNguyen9872$k0`

Điều này phải được giữ trong mental model của dự án: **offline state, save/restore, RMS và embedded server là một phần của kiến trúc hiện tại**, không chỉ là dữ liệu phụ.

## 5. Một vấn đề đã được xác định với AngelChip/.microemulator

Người dùng đã xác định rằng AngelChip lưu dữ liệu của JAR đã chạy. Khi muốn chọn JAR mới mà không để emulator đồng bộ/nhặt lại dữ liệu cũ, dữ liệu lưu cũ cần được xóa/reset.

Đây là một phần của workflow reverse-engineering/test:
1. Chạy bản JAR/reference sạch.
2. Xác định nơi emulator/RMS lưu state.
3. Xóa/reset state khi cần.
4. Chạy lại JAR mới.
5. So sánh hành vi/data.

Không được giả định rằng việc thay JAR tự động đồng nghĩa với việc state cũ đã biến mất.
