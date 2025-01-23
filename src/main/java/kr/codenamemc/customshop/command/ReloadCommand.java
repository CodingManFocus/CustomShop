package kr.codenamemc.customshop.command;

import kr.codenamemc.customshop.Main;
import kr.codenamemc.customshop.handler.FileHandler;
import kr.codenamemc.customshop.shop.ShopLoader;
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
