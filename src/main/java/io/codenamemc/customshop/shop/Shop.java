package io.codenamemc.customshop.shop;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final String name;
    private final Map<Integer, ShopItem> items; // 슬릇과 아이템을 매핑
    private String shopName;

    public Shop(String name) {
        this.name = name;
        this.items = new HashMap<>();
    }

    public String getShopID() {
        return name;
    }

    public Map<Integer, ShopItem> getItems() {
        return items;
    }

    // 상점에 아이템을 추가하는 메소드
    public void addItem(int slot, ShopItem item) {
        items.put(slot, item);
    }

    public void setName(String shopName) {
        this.shopName = shopName;
    }

    public String getName() {
        return shopName;
    }
}