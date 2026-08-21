# USER REQUIREMENTS & PROJECT RULES

## 1. Quy tắc cập nhật phiên bản

**Offline upstream được cập nhật liên tục.** Vì vậy khi có JAR/offline version mới:

1. Không ghi đè ngay bản cũ.
2. Diff/compare bản mới với bản đang có trước.
3. Xác định modification cũ.
4. Báo các conflict.
5. Reapply các modification cũ vào bản mới.
6. Chỉ sau khi xác nhận giữ được modification mới được coi bản mới là baseline.

Mục tiêu là **update mà không mất modification**.

## 2. Phân biệt các nguồn

Luôn phân biệt rõ:
- original online source
- original/older online JAR nếu có
- modded JAR
- current offline JAR
- server package
- suite/emulator/RMS data

Không trộn tất cả thành một source duy nhất.

## 3. Tính năng mod là dữ liệu tham chiếu, không phải lỗi

Các tính năng như:
- Tàn Sát
- Delete Item
- Item filter/lọc item
- Auto/menu auto
- admin utility

có thể chỉ tồn tại trong mod/offline JAR. Đây là các modification cần bảo tồn và/hoặc phục hồi, không được tự ý loại bỏ vì original online source không có.

## 4. Yêu cầu làm việc của người dùng

Người dùng muốn có khả năng yêu cầu Codex:
- chỉnh source trực tiếp;
- chỉnh data trực tiếp;
- thêm/sửa chức năng admin;
- giữ nguyên modification cũ;
- cập nhật từ JAR/server version mới;
- phân tích bytecode/JAR khi chưa có source;
- tái dựng code theo behavior đã quan sát.

## 5. Khi chưa chắc

Không được đoán một class/method/data structure là gì chỉ dựa vào tên obfuscated.

Phải ưu tiên:
- bytecode/decompiler;
- cross-reference giữa các JAR;
- string/resource analysis;
- server data/config;
- runtime behavior;
- RMS/state inspection;
- original source nếu có.

Kết luận phải phân biệt rõ:
- đã xác minh;
- suy luận mạnh;
- giả thuyết cần kiểm chứng.

## 6. Bảo toàn lịch sử

Không xóa các bản reference cũ chỉ vì đã có bản mới.

Mỗi baseline quan trọng nên có:
- version;
- nguồn;
- hash nếu có;
- modification list;
- diff/changelog;
- trạng thái build/test.

## 7. Mục tiêu codebase cuối cùng

Không dừng ở việc decompile một JAR thành code khó đọc. Mục tiêu là một codebase có:
- cấu trúc rõ ràng;
- tên class/method/field có ý nghĩa khi có đủ bằng chứng;
- build được;
- test được;
- có thể tiếp tục phát triển;
- bảo toàn behavior của bản offline/mod cần thiết.
