package io.codenamemc.customshop.command;

import io.codenamemc.customshop.handler.ShopHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage("사용법: /shop <상점 이름>");
                return true;
            }
            String shopName = args[0];
            if (ShopHandler.openShop((Player) sender, shopName)) {
            } else {
                sender.sendMessage("상점 '" + shopName + "' 는/은 존재 하지 않습니다!");
            }
            return true;
        } else {
            System.out.println("해당 명령어는 플레이어인 상태로만 실행 가능합니다!");
        }
        return false;
    }
}
