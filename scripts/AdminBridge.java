/** Runtime bridge injected into the 25.68 client.  Kept in the default
 * package because the game classes are obfuscated and also use the default
 * package. */
public final class AdminBridge implements bd {
    private static final int ADMIN = 110022;
    private static final int ADD_ITEM = 1100221;
    private static final int CLEAR_INVENTORY = 1100222;

    private AdminBridge() {}

    public static void show() {
        AdminBridge listener = new AdminBridge();
        ax.a("Admin",
            new y("Thêm Item", listener, ADD_ITEM, null),
            new y("Xóa Hành Trang", listener, CLEAR_INVENTORY, null));
    }

    public void a(int command, Object ignored) {
        if (command == CLEAR_INVENTORY) {
            clearInventory();
        } else if (command == ADD_ITEM) {
            // The 25.68 item database/receive pipeline is retained.  The
            // numeric form is added in a follow-up bridge once its callback
            // contract is mapped; do not mutate inventory on an incomplete
            // input path.
            ax.a("Thêm Item: luồng nhận item gốc đang được giữ nguyên.");
        }
    }

    public static void clearInventory() {
        KhanhNguyen9872$k0 player = KhanhNguyen9872.J();
        if (player == null || player.T == null) {
            ax.a("Không thể truy cập hành trang.");
            return;
        }
        for (int slot = 0; slot < player.T.length; slot++) {
            KhanhNguyen9872.c(player, slot);
        }
        KhanhNguyen9872.P();
        KhanhNguyen9872.R();
        ax.a("Đã xóa toàn bộ hành trang.");
    }
}
