package kr.codenamemc.customshop.handler;

import kr.codenamemc.customshop.shop.Shop;
import kr.codenamemc.customshop.shop.ShopItem;
import kr.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ItemHandler {

    private final MoenyHandler moneyHandler = new MoenyHandler();
    public void buyItem(Player player, String shopName, int slot) {
        Shop shop = ShopLoader.loadShop(shopName);
        if (shop == null) {
            player.sendMessage("해당 상점을 찾을수 없습니다!");
            return;
        }

        ShopItem shopItem = shop.getItems().get(slot);
        if (shopItem == null) {
            player.sendMessage("해당 슬릇에는 아이템이 없습니다!");
            return;
        }

        Inventory inventory = player.getInventory();
        int goldCount = moneyHandler.countMoney(inventory);

        if (goldCount >= shopItem.getBuyPrice()) {
            moneyHandler.removeMoney(inventory, shopItem.getBuyPrice());
            inventory.addItem(shopItem.getItem());
            player.sendMessage("아이템을 구매 하였습니다!");
        } else {
            player.sendMessage("금액이 부족합니다. " + shopItem.getBuyPrice() + "만큼 필요합니다!");
        }
    }

    // 플레이어가 아이템을 판매하는 메소드
    public void sellItem(Player player, String shopName, int slot) {
        Shop shop = ShopLoader.loadShop(shopName);
        if (shop == null) {
            player.sendMessage("해당 상점을 찾을수 없습니다");
            return;
        }

        ShopItem shopItem = shop.getItems().get(slot);
        if (shopItem == null) {
            player.sendMessage("해당 슬릇에는 아이템이 없습니다!");
            return;
        }

        Inventory inventory = player.getInventory();
        if (inventory.containsAtLeast(shopItem.getItem(), shopItem.getAmount())) {
            inventory.removeItem(shopItem.getItem());
            moneyHandler.addMoney(inventory, shopItem.getSellPrice());
            player.sendMessage("아이템을 판매하였습니다!");
        } else {
            player.sendMessage("판매할 아이템이 없습니다!");
        }
    }
}
