package me.foaler.beta.commands;

import com.google.gson.internal.$Gson$Types;
import me.foaler.beta.Mount;
import me.foaler.beta.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("mountmessages.message") || sender.hasPermission("mountmessages.*") || sender.isOp()) {
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);

                if(target != null) {
                    String message = args[1];
                    if(target.getName().equalsIgnoreCase(sender.getName())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You cannot message yourself!"));
                    } else {
                        if(!sender.hasPermission("mountmessages.message.admin") || !sender.hasPermission("mountmessages.*") || !sender.isOp()) {
                            if(sender instanceof Player) {
                                Player player = (Player) sender;
                                PlayerData playerData = Mount.getInstance().getDataManager().getData(player.getUniqueId());
                                PlayerData targetData = Mount.getInstance().getDataManager().getData(player.getUniqueId());

                                if(playerData.messages == false) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eYou have your private messages turned &4off"));
                                } else if(targetData.messages == false) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eThis player has private messages turned &4off"));
                                } else {
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eFrom &c" + sender.getName() + ") &e" + message));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eTo &c" + target.getName() + ") &e" + message));
                                }
                            } else {
                                PlayerData data = Mount.getInstance().getDataManager().getData(target.getUniqueId());

                                if(data.messages == false) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eThis player has private messages turned &4off"));
                                } else {
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eFrom &c" + sender.getName() + ") &e" + message));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eTo &c" + target.getName() + ") &e" + message));
                                }
                            }
                        } else {
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eFrom &c" + sender.getName() + ") &e" + message));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(&eTo &c" + target.getName() + ") &e" + message));
                        }
                    }
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player is currently offline!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eUsage&7: &a/" + cmd.getName() + " &7(&cplayer&7) &7(&ctext&7)"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You dont have permissions to execute this command!"));
        }
        return false;
    }
}