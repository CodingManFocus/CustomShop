package io.codenamemc.customshop.event;

import io.codenamemc.customshop.handler.ShopHandler;
import io.codenamemc.customshop.shop.Shop;
import io.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EventListener implements Listener {
    private final ShopHandler shopHandler;

    public EventListener(ShopHandler shopHandler) {
        this.shopHandler = shopHandler;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        Inventory clickedInventory = event.getClickedInventory();
        String value;
        //player.sendMessage("플레이어가 클릭함! 클릭한 상점 : " + inventoryName);

        if (inventory.getSize() != 54) { return; }
        if (inventory.getItem(48) == null) { return; }

        // 현재 열린 인벤토리가 상점인지 확인
        NamespacedKey key = new NamespacedKey(ShopLoader.plugin, "shopID");
        ItemStack shopIdentifier = inventory.getItem(48);
        PersistentDataContainer container = shopIdentifier.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            value = container.get(key, PersistentDataType.STRING);
        } else {
            return;
        }

        Shop shop = shopHandler.getShopLoader().getShop(value);
        if (shop == null) { return; }

        //player.sendMessage("현재 상점 감지 되었습니다");

        int slot = event.getSlot();
        if (event.getClick().isLeftClick() && clickedInventory != null && clickedInventory.getHolder() == null) {
            shopHandler.buyItem(player, value, slot); // 상점 이름과 슬롯으로 구매 처리
            event.setCancelled(true);
        } else if (event.getClick().isRightClick() && clickedInventory != null && clickedInventory.getHolder() == null) {
            shopHandler.sellItem(player, value, slot); // 상점 이름과 슬롯으로 판매 처리
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        String value;

        if (inventory.getSize() != 54) { return; }
        if (inventory.getItem(48) == null) { return; }

        // 현재 열린 인벤토리가 상점인지 확인
        NamespacedKey key = new NamespacedKey(ShopLoader.plugin, "shopID");
        ItemStack shopIdentifier = inventory.getItem(48);
        PersistentDataContainer container = shopIdentifier.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            value = container.get(key, PersistentDataType.STRING);
        } else {
            return;
        }

        Shop shop = shopHandler.getShopLoader().getShop(value);
        if (shop == null) { return; }

        inventory.clear();
        inventory.close();
        System.out.println("인벤토리 초기화 됨.");
    }
}

