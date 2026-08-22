# Admin Implementation Guide
## Hướng dẫn chi tiết để thêm chức năng Admin vào Ninja School Offline MOD 25.27

---

## 📋 Tóm Tắt Yêu Cầu

Thêm menu **Admin** vào F1 Menu với 2 chức năng:
1. **Thêm Item**: Nhập ID Item, Nâng cấp (1-16), Hạn sử dụng, Số lượng, ID chỉ số
2. **Xóa Hành Trang**: Xóa toàn bộ item (kể cả khóa)

---

## 🔍 Phân Tích Đã Hoàn Thành

### Cấu trúc JAR
- **File**: `NinjaSchoolOffline_2.1.7_v1.25.27.jar` (10MB)
- **Main class**: `KhanhNguyen9872` (GameMidlet)
- **Menu handler**: `ba.class` (257KB)
- **Obfuscation**: ProGuard (name-only)

### Menu System
- **Menu IDs**: 110001 - 111101
- **Switch table**: Offset 15618-15898 trong ba.class
- **Pattern**: Dãy LDC instructions tải menu IDs

### Menu IDs Hiện Có
```
110001: Bản thân
110002: Bản đồ  
110003: Tổng hợp
110004: Thoát
110006: Tính năng
110014: Thủ khố (MOD)
110016: Khu Vực (MOD)
110020: Tự sát (MOD)
110021: MenuAuto (MOD)
110007930: Fake (MOD)
11000804: Lật Hình (MOD)
```

---

## 🎯 Kế Hoạch Thực Hiện

### Bước 1: Thêm Menu IDs Mới

```
Admin: 110022
├── Thêm Item: 1100221
└── Xóa Hành Trang: 1100222
```

### Bước 2: Thêm String Constants

Cần thêm các strings sau vào constant pool:

**Menu Strings:**
- "Admin"
- "Thêm Item"
- "Xóa Hành Trang"

**Form Strings:**
- "ID Item:"
- "Nâng cấp (1-16):"
- "Hạn sử dụng (để trống = vĩnh viễn):"
- "Số lượng (để trống = 1):"
- "ID chỉ số (để trống = gốc):"
- "Thêm"
- "Hủy"
- "Xác nhận"
- "Xóa TẤT CẢ item trong hành trang? (kể cả khóa)"

**Message Strings:**
- "Thành công"
- "Item đã được thêm vào hành trang"
- "Lỗi"
- "Thông tin không hợp lệ: "
- "Hành trang đã được xóa sạch"

### Bước 3: Thêm Integer Constants

- 110022 (Admin menu ID)
- 1100221 (Thêm Item ID)
- 1100222 (Xóa Hành Trang ID)
- 1 (Default upgrade)
- 16 (Max upgrade)
- 1 (Default quantity)
- -1 (Default option = gốc)
- 87 (Option ID for Tấn công)

### Bước 4: Chỉnh Sửa Bytecode

#### a. Thêm Admin vào menu switch table

Trong ba.class, tại offset ~15678 (sau MenuAuto):
```
LDC CP[54]  ; 110021 (MenuAuto)
LDC CP[132] ; 110022 (Admin) <- THÊM
LDC CP[55]  ; 110051
```

#### b. Thêm handler cho Admin

Sau khi load menu ID 110022:
```
LDC CP[132]      ; Push 110022
IF_ICMPEQ        ; Compare with current menu ID
...              ; Existing code
INVOKESTATIC     ; Call AdminHandler.showAdminMenu()
```

#### c. Thêm handlers cho submenus

Tương tự cho 1100221 và 1100222.

---

## 📂 File Cần Tạo

### 1. AdminHandler.java

```java
package com.ninjaschool;

import javax.microedition.lcdui.*;

public class AdminHandler {
    
    // Menu IDs
    public static final int ADMIN_MENU_ID = 110022;
    public static final int ADD_ITEM_ID = 1100221;
    public static final int CLEAR_INVENTORY_ID = 1100222;
    
    // Default values
    public static final int DEFAULT_UPGRADE = 1;
    public static final int MAX_UPGRADE = 16;
    public static final int DEFAULT_QUANTITY = 1;
    public static final int DEFAULT_OPTION = -1;
    public static final int OPTION_ATTACK = 87;
    
    private static GameMidlet midlet;
    
    public static void init(GameMidlet m) {
        midlet = m;
    }
    
    public static void showAdminMenu(Displayable prev) {
        String[] items = {"Thêm Item", "Xóa Hành Trang"};
        List menu = new List("Admin", Choice.IMPLICIT, items, null);
        menu.setCommandListener(new AdminMenuListener(prev));
        Display.getDisplay(midlet).setCurrent(menu);
    }
    
    public static void showAddItemForm(Displayable prev) {
        Form form = new Form("Thêm Item");
        
        TextField itemIdField = new TextField("ID Item:", "", 10, TextField.NUMERIC);
        TextField upgradeField = new TextField("Nâng cấp (1-16):", "1", 2, TextField.NUMERIC);
        TextField expiryField = new TextField("Hạn sử dụng:", "", 20, TextField.ANY);
        TextField quantityField = new TextField("Số lượng:", "1", 10, TextField.NUMERIC);
        TextField optionField = new TextField("ID chỉ số:", "", 10, TextField.NUMERIC);
        
        form.append(itemIdField);
        form.append(upgradeField);
        form.append(expiryField);
        form.append(quantityField);
        form.append(optionField);
        
        form.addCommand(new Command("Thêm", Command.OK, 0));
        form.addCommand(new Command("Hủy", Command.BACK, 0));
        
        form.setCommandListener(new AddItemListener(prev, 
            itemIdField, upgradeField, expiryField, quantityField, optionField));
        
        Display.getDisplay(midlet).setCurrent(form);
    }
    
    public static void processAddItem(
        TextField itemIdField, TextField upgradeField, TextField expiryField,
        TextField quantityField, TextField optionField, Displayable prev) {
        
        try {
            int itemId = Integer.parseInt(itemIdField.getString());
            
            int upgrade = DEFAULT_UPGRADE;
            if (upgradeField.getString().length() > 0) {
                upgrade = Integer.parseInt(upgradeField.getString());
                upgrade = Math.max(1, Math.min(upgrade, MAX_UPGRADE));
            }
            
            long expiry = 0; // 0 = permanent
            if (expiryField.getString().length() > 0) {
                expiry = Long.parseLong(expiryField.getString());
            }
            
            int quantity = DEFAULT_QUANTITY;
            if (quantityField.getString().length() > 0) {
                quantity = Integer.parseInt(quantityField.getString());
                quantity = Math.max(1, quantity);
            }
            
            int optionId = DEFAULT_OPTION; // -1 = default
            if (optionField.getString().length() > 0) {
                optionId = Integer.parseInt(optionField.getString());
            }
            
            // Call native method
            boolean success = midlet.addItemToInventory(itemId, upgrade, expiry, quantity, optionId);
            
            Alert alert;
            if (success) {
                alert = new Alert("Thành công", "Item đã được thêm vào hành trang", null, AlertType.INFO);
            } else {
                alert = new Alert("Lỗi", "Không thể thêm item", null, AlertType.ERROR);
            }
            alert.setTimeout(2000);
            alert.setCommandListener(new BackListener(prev));
            Display.getDisplay(midlet).setCurrent(alert);
            
        } catch (Exception e) {
            Alert alert = new Alert("Lỗi", "Thông tin không hợp lệ", null, AlertType.ERROR);
            alert.setTimeout(3000);
            alert.setCommandListener(new BackListener(prev));
            Display.getDisplay(midlet).setCurrent(alert);
        }
    }
    
    public static void showClearConfirm(Displayable prev) {
        Alert confirm = new Alert("Xác nhận", 
            "Xóa TẤT CẢ item trong hành trang? (kể cả khóa)", 
            null, AlertType.WARNING);
        confirm.setTimeout(Alert.FOREVER);
        confirm.addCommand(new Command("Xóa", Command.OK, 0));
        confirm.addCommand(new Command("Hủy", Command.BACK, 0));
        confirm.setCommandListener(new ClearListener(prev));
        Display.getDisplay(midlet).setCurrent(confirm);
    }
    
    public static void processClearInventory(Displayable prev) {
        int count = midlet.clearAllItems();
        Alert alert = new Alert("Thành công", 
            "Hành trang đã được xóa sạch (" + count + " item)", 
            null, AlertType.INFO);
        alert.setTimeout(2000);
        alert.setCommandListener(new BackListener(prev));
        Display.getDisplay(midlet).setCurrent(alert);
    }
    
    // Command Listeners
    static class AdminMenuListener implements CommandListener {
        Displayable prev;
        AdminMenuListener(Displayable prev) { this.prev = prev; }
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.BACK) {
                Display.getDisplay(midlet).setCurrent(prev);
            } else if (c.getCommandType() == Command.ITEM) {
                int index = ((List)d).getSelectedIndex();
                switch (index) {
                    case 0: showAddItemForm(prev); break;
                    case 1: showClearConfirm(prev); break;
                }
            }
        }
    }
    
    static class AddItemListener implements CommandListener {
        Displayable prev;
        TextField itemId, upgrade, expiry, quantity, option;
        AddItemListener(Displayable prev, TextField... fields) {
            this.prev = prev;
            this.itemId = fields[0];
            this.upgrade = fields[1];
            this.expiry = fields[2];
            this.quantity = fields[3];
            this.option = fields[4];
        }
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.OK) {
                processAddItem(itemId, upgrade, expiry, quantity, option, prev);
            } else if (c.getCommandType() == Command.BACK) {
                Display.getDisplay(midlet).setCurrent(prev);
            }
        }
    }
    
    static class ClearListener implements CommandListener {
        Displayable prev;
        ClearListener(Displayable prev) { this.prev = prev; }
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.OK) {
                processClearInventory(prev);
            } else {
                Display.getDisplay(midlet).setCurrent(prev);
            }
        }
    }
    
    static class BackListener implements CommandListener {
        Displayable prev;
        BackListener(Displayable prev) { this.prev = prev; }
        public void commandAction(Command c, Displayable d) {
            Display.getDisplay(midlet).setCurrent(prev);
        }
    }
}
```

### 2. Chỉnh Sửa KhanhNguyen9872.java

Thêm các native methods sau:

```java
// Thêm vào class KhanhNguyen9872

public boolean addItemToInventory(int itemId, int upgrade, long expiry, int quantity, int optionId) {
    try {
        // Get player inventory
        Inventory inv = getPlayerInventory();
        
        // Create new item
        Item item = createItem(itemId);
        if (item == null) return false;
        
        // Set item properties
        item.setUpgrade(upgrade);
        item.setExpiry(expiry == 0 ? Long.MAX_VALUE : expiry);
        item.setQuantity(quantity);
        
        // Set option if specified
        if (optionId != -1) {
            item.setOption(optionId);
        }
        
        // Add to inventory
        inv.addItem(item);
        return true;
        
    } catch (Exception e) {
        return false;
    }
}

public int clearAllItems() {
    Inventory inv = getPlayerInventory();
    int count = inv.getItemCount();
    inv.clearAll();
    return count;
}

// Helper methods
private Inventory getPlayerInventory() {
    // Triển khai lấy inventory của player
    return player.inventory;
}

private Item createItem(int itemId) {
    // Triển khai tạo item từ ID
    return ItemDatabase.getItem(itemId);
}
```

### 3. Chỉnh Sửa ba.java (Menu Handler)

Thêm case cho Admin menu:

```java
// Trong method xử lý menu
switch (menuId) {
    case 110001: // Bản thân
        showSelfMenu();
        break;
    // ... other cases ...
    case 110021: // MenuAuto
        showAutoMenu();
        break;
    case 110022: // Admin
        AdminHandler.showAdminMenu(currentDisplayable);
        break;
    case 1100221: // Thêm Item
        AdminHandler.showAddItemForm(currentDisplayable);
        break;
    case 1100222: // Xóa Hành Trang
        AdminHandler.showClearConfirm(currentDisplayable);
        break;
    // ... rest of cases ...
}
```

---

## 🔧 Cách Thực Hiện (Step-by-Step)

### Phương Án 1: Sử Dụng Decompiler + Recompiler (Recommended)

#### Bước 1: Decompile JAR
```bash
# Sử dụng CFR decompiler
java -jar cfr.jar NinjaSchoolOffline_2.1.7_v1.25.27.jar --outputdir decompiled/

# Hoặc sử dụng Procyon
java -jar procyon-decompiler.jar -o decompiled/ NinjaSchoolOffline_2.1.7_v1.25.27.jar
```

#### Bước 2: Chỉnh Sửa Source
- Mở file `decompiled/KhanhNguyen9872.java`
- Thêm native methods (xem ở trên)
- Mở file `decompiled/ba.java`
- Thêm case cho Admin menu (xem ở trên)
- Tạo file mới `decompiled/AdminHandler.java`

#### Bước 3: Recompile
```bash
# Compile với J2ME (MIDP/CLDC)
# Sử dụng Wireless Toolkit hoặc MicroEmulator

# Compile tất cả class files
javac -bootclasspath <wtk_lib> -d compiled/ decompiled/*.java

# Hoặc compile từng file
javac -bootclasspath <wtk_lib> -d compiled/ decompiled/KhanhNguyen9872.java
javac -bootclasspath <wtk_lib> -d compiled/ decompiled/ba.java
javac -bootclasspath <wtk_lib> -d compiled/ decompiled/AdminHandler.java
```

#### Bước 4: Repack JAR
```bash
# Copy tất cả class files vào thư mục
cp compiled/*.class temp/

# Copy resources từ JAR gốc
unzip NinjaSchoolOffline_2.1.7_v1.25.27.jar -d temp/

# Tạo JAR mới
jar cvf NinjaSchoolOffline_2.1.7_v1.25.27_MOD.jar -C temp/ .
```

### Phương Án 2: Bytecode Manipulation (Advanced)

Sử dụng ASM library để chỉnh sửa bytecode trực tiếp:

```java
import org.objectweb.asm.*;

public class AdminAdder {
    public static void main(String[] args) throws Exception {
        // Đọc ba.class
        byte[] baBytes = Files.readAllBytes(Paths.get("ba.class"));
        
        ClassReader cr = new ClassReader(baBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        
        cr.accept(new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, 
                String desc, String signature, String[] exceptions) {
                
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                
                if (name.equals("menuHandler") || name.equals("processMenu")) {
                    // Thêm case cho Admin
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "ba", "menuId", "I");
                    mv.visitLdcInsn(110022);
                    Label skipAdmin = new Label();
                    mv.visitJumpInsn(IF_ICMPNE, skipAdmin);
                    
                    // Call AdminHandler
                    mv.visitMethodInsn(INVOKESTATIC, "AdminHandler", "showAdminMenu", 
                        "(Ljavax/microedition/lcdui/Displayable;)V", false);
                    mv.visitJumpInsn(GOTO, skipAdmin);
                    mv.visitLabel(skipAdmin);
                }
                
                return mv;
            }
        }, 0);
        
        // Ghi file mới
        Files.write(Paths.get("ba_mod.class"), cw.toByteArray());
    }
}
```

### Phương Án 3: Hex Editing (Not Recommended)

Chỉ sử dụng nếu không có công cụ nào khác:

1. Mở ba.class với hex editor
2. Tìm vị trí menu switch table (offset ~15678)
3. Thêm byte cho Admin menu ID
4. Chỉnh sửa các offset và jump targets
5. **Rất nguy hiểm, dễ lỗi**

---

## 📊 Item Database Tham Khảo

Dựa trên phân tích JAR MOD 25.27:

### Option IDs
- **87**: Tấn công (Attack)
- **Các option khác**: Cần tham khảo từ game data

### Item Types
- Vật phẩm tiêu hao
- Trang bị
- Nguyên liệu
- Ngọc
- Đá
- Item đặc biệt
- Item event

### Item IDs
Cần tham khảo từ:
- `ItemDatabase` class
- `item.txt` hoặc resource files
- Hoặc từ community documentation

---

## ⚠️ Lưu Ý Quan Trọng

### 1. Tương Thích
- Phải đảm bảo code mới tương thích với J2ME (MIDP/CLDC)
- Không sử dụng Java 8+ features
- Không sử dụng reflection (chậm trên J2ME)

### 2. Bảo Mật
- Chức năng Admin nên có xác thực
- Không để lộ cho tất cả người chơi
- Có thể thêm password hoặc check admin flag

### 3. Error Handling
- Validate tất cả input
- Hiển thị thông báo lỗi rõ ràng
- Không crash game khi input không hợp lệ

### 4. Performance
- Không tạo quá nhiều object
- Reuse object khi có thể
- Tránh memory leaks

### 5. Testing
- Test trên nhiều thiết bị
- Test với nhiều loại item
- Test edge cases (max values, empty inputs, etc.)

---

## 📚 Tài Liệu Tham Khảo

- [J2ME Documentation](https://docs.oracle.com/javame/)
- [MIDP API](https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/)
- [ASM Library](https://asm.ow2.io/)
- [ProGuard Manual](https://www.guardsquare.com/proguard/manual/)

---

## 🎯 Kết Luận

Để thêm chức năng Admin hoàn chỉnh, bạn cần:

1. ✅ **Phân tích**: Đã hoàn thành (xem FUNCTION_TREE_WITH_ADMIN.md)
2. ⏳ **Decompile**: Sử dụng CFR/Procyon để decompile JAR
3. ⏳ **Chỉnh sửa**: Thêm AdminHandler và chỉnh sửa menu handler
4. ⏳ **Recompile**: Compile với J2ME compatibility
5. ⏳ **Repack**: Tạo JAR mới với tất cả files
6. ⏳ **Test**: Kiểm tra trên thiết bị/emulator
7. ⏳ **Deploy**: Gửi file JAR hoàn chỉnh

Do hạn chế của môi trường hiện tại (không có javac, decompiler), 
không thể hoàn thành việc chỉnh sửa JAR trực tiếp.

**Khuyến nghị**: Thực hiện trên local machine với đầy đủ công cụ.

---

*Tài liệu: Admin Implementation Guide*
*Dự án: Ninja School Offline MOD 25.27*
*Ngày: 2024*
