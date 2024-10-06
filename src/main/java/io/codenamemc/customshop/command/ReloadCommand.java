package io.codenamemc.customshop.command;

import io.codenamemc.customshop.Main;
import io.codenamemc.customshop.handler.FileHandler;
import io.codenamemc.customshop.shop.ShopLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private Main plugin;
    private FileHandler fileHandler;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            sender.sendMessage("상점이 리로드되었습니다.");
            ShopLoader.loadAllShops();
        } else {
            sender.sendMessage("해당 명령어를 사용하기 위해선 권한이 필요합니다!");
            return true;
        }
        return false;
    }
}
