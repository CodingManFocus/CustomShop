package kr.codenamemc.customshop.handler;

import kr.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MoenyHandler {
    private static Material moenyItem = Material.valueOf((ShopLoader.plugin.getConfig().getString("money")).toUpperCase());

    public int countMoney(Inventory inventory) {
        int count = 0;
        for (ItemStack item : inventory) {
            if (item != null && item.getType() == moenyItem) {
                count += item.getAmount();
            }
        }
        return count;
    }

    // 인벤토리에서 금을 제거하는 메소드
    public void removeMoney(Inventory inventory, int amount) {
        int remaining = amount;
        for (ItemStack item : inventory) {
            if (item != null && item.getType() == moenyItem) {
                int stackAmount = item.getAmount();
                if (stackAmount <= remaining) {
                    inventory.remove(item);
                    remaining -= stackAmount;
                } else {
                    item.setAmount(stackAmount - remaining);
                    break;
                }
            }
        }
    }

    // 인벤토리에 금을 추가하는 메소드
    public void addMoney(@NotNull Inventory inventory, int amount) { inventory.addItem(new ItemStack(moenyItem, amount)); }
}
