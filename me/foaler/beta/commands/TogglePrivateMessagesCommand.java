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

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                .getString("Messages.enabledMessages")));
                    } else {
                        data.messages=false;

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                .getString("Messages.disabledMessages")));
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

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.forceEnabledMessages")));
                                } else {
                                    data.messages=false;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.forceDisabledMessages")));
                                }
                                Mount.getInstance().getConfig().set("Users." + data.getPlayer().getName() + ".privatemessages", data.messages);
                                Mount.getInstance().saveConfig();
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                        .getString("Messages.alertChanges").replaceAll("%player%", player.getName())));
                            } else {
                                PlayerData data = Mount.getInstance().getDataManager().getData(target.getUniqueId());

                                if(data.messages == false) {
                                    data.messages=true;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.forceEnabledMessages")));
                                } else {
                                    data.messages=false;

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.forceDisabledMessages")));
                                }
                                Mount.getInstance().getConfig().set("Users." + data.getPlayer().getName() + ".privatemessages", data.messages);
                                Mount.getInstance().saveConfig();
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                    .getString("Messages.playerNotOnline").replaceAll("%player%", args[0])));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                .getString("Messages.noPermission")));
                    }
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                        .getString("Messages.noPermission")));
            }
        } else {
            sender.sendMessage("Only Players!");
        }
        return false;
    }
}
