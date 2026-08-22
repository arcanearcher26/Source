# Implementation Plan - Thêm Admin Menu vào Ninja School Offline MOD 25.27

## Tổng Quan

Yêu cầu: Thêm menu **Admin** vào F1 Menu với 2 chức năng con:
1. **Thêm Item** - Nhập ID Item, Nâng cấp, Hạn sử dụng, Số lượng, ID chỉ số
2. **Xóa Hành Trang** - Xóa toàn bộ item (kể cả khóa)

## Phân Tích Hiện Trạng

### Cấu Trúc Menu Hiện Có (từ ba.class)
- Menu IDs: 110001 - 111101 (đã được xác định)
- **MenuAuto**: ID 110021 (CP[54])
- **Fake**: ID 110007930
- **Lật Hình**: ID 11000804

### Menu Switch Table
- Vị trí: Offset 15618-15898 trong ba.class
- Cấu trúc: Dãy LDC instructions tải menu IDs từ constant pool
- Mỗi menu ID cách nhau 5 bytes (LDC opcode + 1 byte index)

### Menu IDs Đã Sử Dụng
- 110001: Bản thân
- 110002: Bản đồ
- 110003: Tổng hợp
- 110004: Thoát
- 110006: Tính năng
- 110014: Thủ khố (MOD)
- 110016: Khu Vực (MOD)
- 110020: Tự sát (MOD)
- 110021: MenuAuto (MOD)
- 110007930: Fake (MOD)
- 11000804: Lật Hình (MOD)

## Kế Hoạch Triển Khai

### 1. Thêm Menu Admin

#### Menu ID
- **Admin**: 110022 (ID mới, không xung đột)

#### Cấu Trúc Menu
```
Admin [110022]
├── Thêm Item [1100221]
└── Xóa Hành Trang [1100222]
```

### 2. Thêm String Constants

Cần thêm vào constant pool của ba.class:
- CP[X]: "Admin"
- CP[X+1]: "Thêm Item"
- CP[X+2]: "Xóa Hành Trang"
- CP[X+3]: "ID Item:"
- CP[X+4]: "Nâng cấp (1-16):"
- CP[X+5]: "Hạn sử dụng (để trống = vĩnh viễn):"
- CP[X+6]: "Số lượng (để trống = 1):"
- CP[X+7]: "ID chỉ số (để trống = gốc):"
- CP[X+8]: "Thêm"
- CP[X+9]: "Hủy"
- CP[X+10]: "Xác nhận"
- CP[X+11]: "Xóa TẤT CẢ item trong hành trang? (kể cả khóa)"
- CP[X+12]: "Thành công"
- CP[X+13]: "Item đã được thêm vào hành trang"
- CP[X+14]: "Lỗi"
- CP[X+15]: "Thông tin không hợp lệ: "
- CP[X+16]: "Hành trang đã được xóa sạch"

### 3. Thêm Integer Constants

Cần thêm vào constant pool:
- CP[Y]: 110022 (Admin menu ID)
- CP[Y+1]: 1100221 (Thêm Item ID)
- CP[Y+2]: 1100222 (Xóa Hành Trang ID)
- CP[Y+3]: 1 (Default upgrade)
- CP[Y+4]: 16 (Max upgrade)
- CP[Y+5]: 1 (Default quantity)
- CP[Y+6]: -1 (Default option = gốc)
- CP[Y+7]: 87 (Option ID for Tấn công)

### 4. Chỉnh Sửa Bytecode

#### a. Thêm Admin vào menu switch table
- Vị trí: Sau CP[54] (MenuAuto) trong dãy menu IDs
- Thêm: LDC CP[Y] (110022)

#### b. Thêm handler cho Admin menu
- Sau khi load menu ID 110022, thêm code gọi AdminHandler.showAdminMenu()

#### c. Thêm handler cho Thêm Item (1100221)
- Thêm case: LDC CP[Y+1] -> call AdminHandler.addItem()

#### d. Thêm handler cho Xóa Hành Trang (1100222)
- Thêm case: LDC CP[Y+2] -> call AdminHandler.clearInventory()

### 5. Triển Khai Chức Năng

#### AdminHandler Class

```java
public class AdminHandler {
    
    // Menu IDs
    public static final int ADMIN_MENU_ID = 110022;
    public static final int ADD_ITEM_ID = 1100221;
    public static final int CLEAR_INVENTORY_ID = 1100222;
    
    // Default values
    public static final int DEFAULT_UPGRADE = 1;
    public static final int MAX_UPGRADE = 16;
    public static final int DEFAULT_QUANTITY = 1;
    public static final int DEFAULT_OPTION = -1; // -1 = root/default
    public static final int OPTION_ATTACK = 87;
    
    public static void showAdminMenu(Displayable prev, GameMidlet midlet) {
        // Create menu with 2 items
        String[] items = {"Thêm Item", "Xóa Hành Trang"};
        List menu = new List("Admin", Choice.IMPLICIT, items, null);
        menu.setCommandListener(new AdminMenuListener(prev, midlet));
        Display.getDisplay(midlet).setCurrent(menu);
    }
    
    public static void showAddItemForm(Displayable prev, GameMidlet midlet) {
        Form form = new Form("Thêm Item");
        
        // Input fields
        TextField itemIdField = new TextField("ID Item:", "", 10, TextField.NUMERIC);
        TextField upgradeField = new TextField("Nâng cấp (1-16):", "1", 2, TextField.NUMERIC);
        TextField expiryField = new TextField("Hạn sử dụng (trống=vĩnh viễn):", "", 20, TextField.ANY);
        TextField quantityField = new TextField("Số lượng (trống=1):", "1", 10, TextField.NUMERIC);
        TextField optionField = new TextField("ID chỉ số (trống=gốc):", "", 10, TextField.NUMERIC);
        
        form.append(itemIdField);
        form.append(upgradeField);
        form.append(expiryField);
        form.append(quantityField);
        form.append(optionField);
        
        // Commands
        form.addCommand(new Command("Thêm", Command.OK, 0));
        form.addCommand(new Command("Hủy", Command.BACK, 0));
        
        form.setCommandListener(new AddItemListener(prev, midlet, 
            itemIdField, upgradeField, expiryField, quantityField, optionField));
        
        Display.getDisplay(midlet).setCurrent(form);
    }
    
    public static void processAddItem(GameMidlet midlet, 
        TextField itemIdField, TextField upgradeField, TextField expiryField,
        TextField quantityField, TextField optionField, Displayable prev) {
        
        try {
            // Parse inputs
            int itemId = Integer.parseInt(itemIdField.getString());
            
            int upgrade = DEFAULT_UPGRADE;
            if (upgradeField.getString().length() > 0) {
                upgrade = Integer.parseInt(upgradeField.getString());
                if (upgrade < 1) upgrade = 1;
                if (upgrade > MAX_UPGRADE) upgrade = MAX_UPGRADE;
            }
            
            long expiry = 0; // 0 = no expiry (permanent)
            if (expiryField.getString().length() > 0) {
                expiry = Long.parseLong(expiryField.getString());
            }
            
            int quantity = DEFAULT_QUANTITY;
            if (quantityField.getString().length() > 0) {
                quantity = Integer.parseInt(quantityField.getString());
                if (quantity < 1) quantity = 1;
            }
            
            int optionId = DEFAULT_OPTION; // -1 = default/root
            if (optionField.getString().length() > 0) {
                optionId = Integer.parseInt(optionField.getString());
            }
            
            // Call native method to add item
            boolean success = addItemToInventory(midlet, itemId, upgrade, expiry, quantity, optionId);
            
            if (success) {
                Alert alert = new Alert("Thành công", "Item đã được thêm vào hành trang", null, AlertType.INFO);
                alert.setTimeout(2000);
                alert.setCommandListener(new BackToPrevListener(prev, midlet));
                Display.getDisplay(midlet).setCurrent(alert);
            } else {
                Alert alert = new Alert("Lỗi", "Không thể thêm item", null, AlertType.ERROR);
                alert.setTimeout(2000);
                alert.setCommandListener(new BackToPrevListener(prev, midlet));
                Display.getDisplay(midlet).setCurrent(alert);
            }
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert("Lỗi", "Thông tin không hợp lệ: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(3000);
            alert.setCommandListener(new BackToPrevListener(prev, midlet));
            Display.getDisplay(midlet).setCurrent(alert);
        }
    }
    
    public static void showClearInventoryConfirm(Displayable prev, GameMidlet midlet) {
        Alert confirm = new Alert("Xác nhận", 
            "Xóa TẤT CẢ item trong hành trang? (kể cả khóa)", 
            null, AlertType.WARNING);
        confirm.setTimeout(Alert.FOREVER);
        confirm.addCommand(new Command("Xóa", Command.OK, 0));
        confirm.addCommand(new Command("Hủy", Command.BACK, 0));
        confirm.setCommandListener(new ClearInventoryListener(prev, midlet));
        Display.getDisplay(midlet).setCurrent(confirm);
    }
    
    public static void processClearInventory(GameMidlet midlet, Displayable prev) {
        // Clear all items from inventory
        int count = clearAllItems(midlet);
        
        Alert success = new Alert("Thành công", 
            "Hành trang đã được xóa sạch (" + count + " item)", 
            null, AlertType.INFO);
        success.setTimeout(2000);
        success.setCommandListener(new BackToPrevListener(prev, midlet));
        Display.getDisplay(midlet).setCurrent(success);
    }
    
    // Native methods - to be implemented in main game class
    private static native boolean addItemToInventory(GameMidlet midlet, 
        int itemId, int upgrade, long expiry, int quantity, int optionId);
    
    private static native int clearAllItems(GameMidlet midlet);
    
    // Command Listeners
    static class AdminMenuListener implements CommandListener {
        Displayable prev;
        GameMidlet midlet;
        
        AdminMenuListener(Displayable prev, GameMidlet midlet) {
            this.prev = prev;
            this.midlet = midlet;
        }
        
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.BACK) {
                Display.getDisplay(midlet).setCurrent(prev);
            } else if (c.getCommandType() == Command.ITEM) {
                int index = ((List)d).getSelectedIndex();
                switch (index) {
                    case 0: // Thêm Item
                        showAddItemForm(prev, midlet);
                        break;
                    case 1: // Xóa Hành Trang
                        showClearInventoryConfirm(prev, midlet);
                        break;
                }
            }
        }
    }
    
    static class AddItemListener implements CommandListener {
        Displayable prev;
        GameMidlet midlet;
        TextField itemIdField, upgradeField, expiryField, quantityField, optionField;
        
        AddItemListener(Displayable prev, GameMidlet midlet,
            TextField itemId, TextField upgrade, TextField expiry,
            TextField quantity, TextField option) {
            this.prev = prev;
            this.midlet = midlet;
            this.itemIdField = itemId;
            this.upgradeField = upgrade;
            this.expiryField = expiry;
            this.quantityField = quantity;
            this.optionField = option;
        }
        
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.OK) {
                processAddItem(midlet, itemIdField, upgradeField, expiryField,
                    quantityField, optionField, prev);
            } else if (c.getCommandType() == Command.BACK) {
                Display.getDisplay(midlet).setCurrent(prev);
            }
        }
    }
    
    static class ClearInventoryListener implements CommandListener {
        Displayable prev;
        GameMidlet midlet;
        
        ClearInventoryListener(Displayable prev, GameMidlet midlet) {
            this.prev = prev;
            this.midlet = midlet;
        }
        
        public void commandAction(Command c, Displayable d) {
            if (c.getCommandType() == Command.OK) {
                processClearInventory(midlet, prev);
            } else if (c.getCommandType() == Command.BACK) {
                Display.getDisplay(midlet).setCurrent(prev);
            }
        }
    }
    
    static class BackToPrevListener implements CommandListener {
        Displayable prev;
        GameMidlet midlet;
        
        BackToPrevListener(Displayable prev, GameMidlet midlet) {
            this.prev = prev;
            this.midlet = midlet;
        }
        
        public void commandAction(Command c, Displayable d) {
            Display.getDisplay(midlet).setCurrent(prev);
        }
    }
}
```

### 6. Triển Khai Native Methods

Cần thêm vào class chính (KhanhNguyen9872.class):

```java
// Thêm vào class KhanhNguyen9872

// Native method declarations
public boolean addItemToInventory(int itemId, int upgrade, long expiry, int quantity, int optionId) {
    // Triển khai thêm item vào hành trang
    // 1. Tạo item object với thông số
    // 2. Thêm vào inventory
    // 3. Return true nếu thành công
    return true;
}

public int clearAllItems() {
    // Triển khai xóa toàn bộ item
    // 1. Lấy tất cả item từ inventory
    // 2. Xóa tất cả (kể cả khóa)
    // 3. Return số item đã xóa
    int count = 0;
    // ... implementation
    return count;
}
```

## Item Database Tham Khảo

Dựa trên phân tích JAR:
- **Option 87**: Tấn công (Attack)
- Các option khác: Cần tham khảo từ game data

Khi optionId = -1: Sử dụng chỉ số gốc/mặc định của item
Khi expiry = 0: Item vĩnh viễn (không hết hạn)

## Thứ Tự Thực Hiện

1. ✅ Phân tích cấu trúc JAR và menu system
2. ✅ Xác định menu IDs và vị trí thêm Admin
3. ⏳ Tạo AdminHandler class
4. ⏳ Chỉnh sửa ba.class để thêm Admin vào menu
5. ⏳ Thêm handler cho Thêm Item và Xóa Hành Trang
6. ⏳ Triển khai native methods
7. ⏳ Build và test JAR
8. ⏳ Gửi file JAR hoàn chỉnh

## Rủi Ro Và Giải Pháp

### Rủi ro 1: Bytecode modification errors
- **Giải pháp**: Sử dụng bytecode manipulation libraries (ASM, BCEL)
- **Giải pháp thay thế**: Decompile → Modify → Recompile

### Rủi ro 2: Class format mismatch
- **Giải pháp**: Đảm bảo version và constant pool size đúng

### Rủi ro 3: Missing dependencies
- **Giải pháp**: Sử dụng các class có sẵn trong JAR

## Công Cụ Cần Thiết

1. **Decompiler**: CFR, Procyon, or FernFlower
2. **Compiler**: javac (J2ME compatible)
3. **Bytecode manipulator**: ASM library
4. **JAR tool**: jar command or similar

## Ghi Chú

Do môi trường hiện tại không có các công cụ cần thiết (javac, decompiler, ASM), 
việc chỉnh sửa trực tiếp bytecode là rất khó khăn.

**Giải pháp đề xuất**:
1. Sử dụng môi trường local với đầy đủ công cụ
2. Decompile JAR → Modify source → Recompile → Repack JAR
3. Hoặc sử dụng bytecode manipulation libraries

---

*Tài liệu: Implementation Plan*
*Ngày: 2024*
*Dự án: Ninja School Offline MOD 25.27 + Admin*
