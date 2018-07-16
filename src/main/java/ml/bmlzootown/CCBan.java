package ml.bmlzootown;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class CCBan extends JavaPlugin {
    String prefix = ChatColor.RED + "[CCBan] " + ChatColor.WHITE;


    public void broadcastMessage(String sender, String bannedPlayer, String banReason) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("cc.notify")) {
                p.sendMessage(ChatColor.GOLD + "Player " + ChatColor.RED + sender + ChatColor.GOLD + " banned " + ChatColor.RED + bannedPlayer + ChatColor.GOLD + " for: " + ChatColor.RED + banReason);
            }
            if (p.getName().equalsIgnoreCase(bannedPlayer)) {
                p.kickPlayer("Banned for reason: " + banReason);
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (!sender.hasPermission("cc.ban")) return false;
            String senderName;
            if (sender instanceof Player) {
                senderName = sender.getName();
            } else {
                senderName = "Console";
            }
            if (args.length == 0) {
                sender.sendMessage(prefix + "Command(s):");
                sender.sendMessage("/ban [user] [reason]");
            }
            if (args.length == 1) {
                getServer().getBanList(BanList.Type.NAME).addBan(args[0], "Appeal at minecraftcc.com", null, senderName);
                broadcastMessage(senderName, args[0], "Appeal at minecraftcc.com");
            }
            if (args.length > 1) {
                String[] array = Arrays.copyOfRange(args, 1, args.length);
                String old = String.join(" ", array);
                getServer().getBanList(BanList.Type.NAME).addBan(args[0], old + " - Appeal at minecraftcc.com", null, senderName);
                broadcastMessage(senderName, args[0], old + " - Appeal at minecraftcc.com");
            }
        }
        return false;
    }
}
