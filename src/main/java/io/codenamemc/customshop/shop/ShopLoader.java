package io.codenamemc.customshop.shop;

import dev.lone.itemsadder.api.CustomStack;
import io.codenamemc.customshop.Main;
import io.codenamemc.customshop.handler.FileHandler;
import io.codenamemc.customshop.handler.ShopHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopLoader {
    private static FileHandler fileHandler;
    private static Map<String, Shop> loadedShops; // 로드된 상점을 캐시로 저장
    public static Main plugin;

    public ShopLoader(FileHandler fileHandler, Main plugin) {
        ShopLoader.fileHandler = fileHandler;
        loadedShops = new HashMap<>();
        ShopLoader.plugin = plugin;
    }

    // 모든 상점 이름과 데이터를 로드하는 메소드
    public static void loadAllShops() {
        File shopDir = new File(plugin.getDataFolder() + "/shops");
        if (!shopDir.exists() || !shopDir.isDirectory()) {
            return;
        }

        for (File file : shopDir.listFiles()) {
            if (file.getName().endsWith(".yml")) {
                String shopName = file.getName().replace(".yml", "");
                Shop shop = loadShop(shopName);
                if (shop != null) {
                    loadedShops.put(shopName, shop);
                }
            }
        }
    }

    // 단일 상점을 로드하는 메소드
    public static Shop loadShop(String shopID) {
        YamlConfiguration config = fileHandler.loadShopFile(shopID);
        if (config == null) {
            return null;
        }

        String shopName = config.getString("shopName");

        Shop shop = new Shop(shopID);
        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            int slot = Integer.parseInt(key);
            String materialName = config.getString("items." + key + ".material");
            int buyPrice = config.getInt("items." + key + ".buyPrice");
            int sellPrice = config.getInt("items." + key + ".sellPrice");
            int amount = config.getInt("items." + key + ".amount");
            List<String> lore = config.getStringList("items." + key + ".lore");

            ItemStack item;

            if (Bukkit.getServer().getPluginManager().getPlugin("ItemsAdder") != null) {
                if (CustomStack.isInRegistry(materialName)) {
                    item = CustomStack.getInstance(materialName).getItemStack();
                } else {
                    item = new ItemStack(Material.valueOf(materialName.toUpperCase()));
                }
            } else {
                item = new ItemStack(Material.valueOf(materialName.toUpperCase()));
            }
            ShopItem shopItem = new ShopItem(item, sellPrice, buyPrice, amount, lore);
            shop.addItem(slot, shopItem);
            shop.setName(shopName);
        }

        return shop;
    }

    // 특정 상점을 이름으로 가져오는 메소드
    public Shop getShop(String name) {
        return loadedShops.get(name);
    }

    // 모든 로드된 상점 목록을 가져오는 메소드
    public Map<String, Shop> getLoadedShops() {
        return loadedShops;
    }
}
