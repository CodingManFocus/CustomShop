package io.codenamemc.customshop.handler;

import java.io.File;
import java.io.IOException;

import io.codenamemc.customshop.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class FileHandler {
    private static Main plugin;
    private static Logger logger;

    public FileHandler(Main plugin) {
        FileHandler.plugin = plugin;
        FileHandler.logger = plugin.getLogger();
    }

    // 특정 상점의 YAML 파일을 로드하는 메소드
    public YamlConfiguration loadShopFile(String shopName) {
        File shopFile = new File(plugin.getDataFolder() + "/shops", shopName + ".yml");
        if (!shopFile.exists()) {
            logger.warning("상점 파일 " + shopName + " 은 존재하지 않습니다");
            return null;
        }

        return YamlConfiguration.loadConfiguration(shopFile);
    }

    public static YamlConfiguration loadConfigFile() {
        File shopFile = new File(plugin.getDataFolder() + "config.yml");
        if (!shopFile.exists()) {
            logger.warning("상점 Config 를 찾을수 없습니다. ");
            return null;
        }

        return YamlConfiguration.loadConfiguration(shopFile);
    }
}
