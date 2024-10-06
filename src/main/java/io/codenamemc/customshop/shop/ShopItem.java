package io.codenamemc.customshop.shop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItem {
    private final ItemStack item;
    private final int sellPrice;
    private final int buyPrice;
    private final int amount;
    private final List<String> itemLore;

    public ShopItem(ItemStack item, int sellPrice, int buyPrice, int amount, List<String> itemLore) {
        this.item = item;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.amount = amount;
        this.itemLore = itemLore;
    }

    public ItemStack getItem() { return item; }

    public int getSellPrice() { return sellPrice; }

    public int getBuyPrice() { return buyPrice; }

    public int getAmount() { return amount; }

    public List<String> getLore() { return itemLore; }
}
