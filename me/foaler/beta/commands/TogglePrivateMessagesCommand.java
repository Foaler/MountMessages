package me.foaler.beta.commands;

import me.foaler.beta.Mount;
import me.foaler.beta.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TogglePrivateMessagesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(player.hasPermission("mountmessages.toggleprivatemessages") || player.hasPermission("mountmessages.*") || player.isOp()) {
                if(args.length == 0) {
                    PlayerData data = Mount.getInstance().getDataManager().getData(player.getUniqueId());

                    if(data.messages == false) {
                        data.messages=true;

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYour Private Messages has been &aenabled"));
                    } else {
                        data.messages=false;

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYour Private Messages has been &4disabled"));
                    }
                    Mount.getInstance().getConfig().set("Users." + data.getPlayer().getName() + ".privatemessages", data.messages);
                    Mount.getInstance().saveConfig();
                }
                if(args.length == 1) {
                    if(player.hasPermission("mountmessages.toggleprivatemessages.admin") || player.hasPermission("mountmessages.*") || player.isOp()) {
                        Player target = Bukkit.getPlayer(args[0]);

                        if(target != null) {
                            if(target.hasPermission("mountmessages.toggleprivatemessages.admin") || player.hasPermission("mountmessages.*") || player.isOp()) {
                                PlayerData data = Mount.getInstance().getDataManager().getData(target.getUniqueId());

                                if(data.messages == false) {
                                    data.messages=true;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eYou have &aenabled &c" + target.getName() + "'s &ePrivate Messages"));;
                                } else {
                                    data.messages=false;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eYou have &4disabled &c" + target.getName() + "'s &ePrivate Messages"));
                                }
                                Mount.getInstance().getConfig().set("Users." + data.getPlayer().getName() + ".privatemessages", data.messages);
                                Mount.getInstance().saveConfig();
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYour Private Messages has been updated by &c" + player.getName()));
                            } else {
                                PlayerData data = Mount.getInstance().getDataManager().getData(target.getUniqueId());

                                if(data.messages == false) {
                                    data.messages=true;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eYou have &aenabled &c" + target.getName() + "'s &ePrivate Messages"));
                                } else {
                                    data.messages=false;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&eYou have &4disabled &c" + target.getName() + "'s &ePrivate Messages"));
                                }
                                Mount.getInstance().getConfig().set("Users." + data.getPlayer().getName() + ".privatemessages", data.messages);
                                Mount.getInstance().saveConfig();
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player is currently offline!"));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You dont have permissions to execute this command!"));
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You dont have permissions to execute this command!"));
            }
        } else {
            sender.sendMessage("Only Players!");
        }
        return false;
    }
}
