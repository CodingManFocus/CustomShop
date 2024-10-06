package io.codenamemc.customshop;

import io.codenamemc.customshop.command.ReloadCommand;
import io.codenamemc.customshop.command.ShopCommand;

import io.codenamemc.customshop.event.EventListener;
import io.codenamemc.customshop.handler.FileHandler;
import io.codenamemc.customshop.handler.ShopHandler;

import io.codenamemc.customshop.shop.ShopLoader;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private FileHandler fileHandler;
    private ShopLoader shopLoader;
    private ShopHandler shopHandler;

    @Override
    public void onEnable() {
        getLogger().info("_______________________________________________________________________");
        getLogger().info("   ______           __                 _____ __                        ");
        getLogger().info("  / ____/_  _______/ /_____  ____ ___ / ___// /_  ____  ____           ");
        getLogger().info(" / /   / / / / ___/ __/ __ \\/ __ `__ \\\\__ \\/ __ \\/ __ \\/ __ \\   ");
        getLogger().info("/ /___/ /_/ (__  ) /_/ /_/ / / / / / /__/ / / / / /_/ / /_/ /          ");
        getLogger().info("\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/____/_/ /_/\\____/ .___/      ");
        getLogger().info("                                                    /_/                ");
        getLogger().info(" *CodenameMC* Thanks for download! Version 0.0.1                       ");
        getLogger().info("_______________________________________________________________________");
        getLogger().info("CustomShop is Enabled!");
        fileHandler = new FileHandler(this);
        saveConfig();
        shopLoader = new ShopLoader(fileHandler, this);
        shopHandler = new ShopHandler(shopLoader);

        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("shopreload").setExecutor(new ReloadCommand());

        getServer().getPluginManager().registerEvents(new EventListener(shopHandler), this);
        shopLoader.loadAllShops();
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomShop is Disabled!");
    }
}
