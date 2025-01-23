package kr.codenamemc.customshop.handler;

import kr.codenamemc.customshop.shop.Shop;
import kr.codenamemc.customshop.shop.ShopItem;
import kr.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
}

