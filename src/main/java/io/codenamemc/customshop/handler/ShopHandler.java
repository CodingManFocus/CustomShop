package io.codenamemc.customshop.handler;

import io.codenamemc.customshop.shop.Shop;
import io.codenamemc.customshop.shop.ShopItem;
import io.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShopHandler {
    private static ShopLoader shopLoader;

    public ShopHandler(ShopLoader shopLoader) {
        ShopHandler.shopLoader = shopLoader;
    }

    public ShopLoader getShopLoader() {
        return shopLoader;
    }

    private static Material moenyItem = Material.valueOf((ShopLoader.plugin.getConfig().getString("money")).toUpperCase());

    public static boolean openShop(Player player, String shopName) {
        Shop shop = shopLoader.getShop(shopName);
        if (shop == null) {
            return false; // 상점이 존재하지 않음
        }

        // 상점의 아이템과 슬롯 정보를 기반으로 인벤토리 생성
        Inventory shopInventory = Bukkit.createInventory(null, 54, shop.getName()); // 9칸 기본 상

        // 상점 아이템을 인벤토리에 추가
        for (int slot : shop.getItems().keySet()) {
            ShopItem shopItem = shop.getItems().get(slot);

            ItemStack item = shopItem.getItem();
            ItemMeta itemMeta = item.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add("§r§e구매가격 :" + shopItem.getBuyPrice() + "/ 판매가격 :" + shopItem.getSellPrice());
            lore.add("§r§e구매 : 좌클릭 / 판매 : 우클릭");
            lore.addAll(shopItem.getLore());
            itemMeta.setLore(lore);
            item.setAmount(shopItem.getAmount());

            item.setItemMeta(itemMeta);

            shopInventory.setItem(slot, item);
            lore = new ArrayList<>();
        }

        NamespacedKey key = new NamespacedKey(ShopLoader.plugin, "shopID");
        ItemStack shopIdentifier = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta shopIdentifierMeta = shopIdentifier.getItemMeta();
        shopIdentifierMeta.setDisplayName("상점");
        shopIdentifierMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, shop.getShopID());
        shopIdentifier.setItemMeta(shopIdentifierMeta);

        for (int i=45;i<54;i++){
            shopInventory.setItem(i, shopIdentifier); // 46시작
        }

        // 플레이어에게 상점 인벤토리 열기
        player.openInventory(shopInventory);
        return true;
    }

    // 플레이어가 아이템을 구매하는 메소드
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
        int goldCount = countGold(inventory);

        if (goldCount >= shopItem.getBuyPrice()) {
            removeGold(inventory, shopItem.getBuyPrice());
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
        if (inventory.containsAtLeast(shopItem.getItem(), 1)) {
            inventory.removeItem(shopItem.getItem());
            addGold(inventory, shopItem.getSellPrice());
            player.sendMessage("아이템을 판매하였습니다!");
        } else {
            player.sendMessage("판매할 아이템이 없습니다!");
        }
    }

    // 인벤토리에서 금의 개수를 세는 메소드
    private int countGold(Inventory inventory) {
        int count = 0;
        for (ItemStack item : inventory) {
            if (item != null && item.getType() == moenyItem) {
                count += item.getAmount();
            }
        }
        return count;
    }

    // 인벤토리에서 금을 제거하는 메소드
    private void removeGold(Inventory inventory, int amount) {
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
    private void addGold(@NotNull Inventory inventory, int amount) { inventory.addItem(new ItemStack(moenyItem, amount)); }
}

