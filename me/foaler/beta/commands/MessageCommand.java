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
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                .getString("Messages.cannotMessageYourSelf").replaceAll("%player%", sender.getName())));
                    } else {
                        if(!sender.hasPermission("mountmessages.message.admin") || !sender.hasPermission("mountmessages.*") || !sender.isOp()) {
                            if(sender instanceof Player) {
                                Player player = (Player) sender;
                                PlayerData playerData = Mount.getInstance().getDataManager().getData(player.getUniqueId());
                                PlayerData targetData = Mount.getInstance().getDataManager().getData(player.getUniqueId());

                                if(playerData.messages == false) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            Mount.getInstance().getConfig()
                                                    .getString("Messages.yourPrivateMessagesDisabled")));
                                } else if(targetData.messages == false) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.privateMessagesDisabled").replaceAll("%player%", target.getName())));
                                } else {
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.messageReceiver").replaceAll("%player%", sender.getName())));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.messageSender").replaceAll("%player%", target.getName())));
                                }
                            } else {
                                PlayerData data = Mount.getInstance().getDataManager().getData(target.getUniqueId());

                                if(data.messages == false) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            Mount.getInstance().getConfig()
                                                    .getString("Messages.privateMessagesDisabled").replaceAll("%player%", target.getName())));
                                } else {
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.messageReceiver").replaceAll("%player%", sender.getName())));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                            .getString("Messages.messageSender").replaceAll("%player%", target.getName())));
                                }
                            }
                        } else {
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                    .getString("Messages.messageReceiver").replaceAll("%player%", sender.getName())));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                                    .getString("Messages.messageSender").replaceAll("%player%", target.getName())));
                        }
                    }
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                        .getString("Messages.playerNotOnline").replaceAll("%player%", args[0])));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eUsage&7: &a/" + cmd.getName() + " &7(&cplayer&7) &7(&ctext&7)"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Mount.getInstance().getConfig()
                    .getString("Messages.noPermission")));
        }
        return false;
    }
}