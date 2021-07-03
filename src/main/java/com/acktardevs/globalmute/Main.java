package com.acktardevs.globalmute;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerChatEvent;

import java.util.List;
import java.util.Iterator;

public class Main extends PluginBase implements Listener {

@Override
public void onEnable() {
      this.saveDefaultConfig();      
      this.getServer().getPluginManager().registerEvents(this, this);
}

@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
   switch (command.getName()) {
        case "globalmute":
              if (args.length == 1) {
              	if (args[0].equals("on")) {
              	    boolean current = getConfig().getBoolean("global-mute");
                         if (current == true) {
                         	sender.sendMessage(getConfig().getString("already-muted"));
                        } else {
                        	getConfig().set("global-mute", true);
                            
                            if (getConfig().getBoolean("clear-chat") == true) {
                            String repeated = new String(new char[100]).replace("\0", "\n");
                            this.getServer().broadcastMessage(repeated);
                           } 
                           
                            List<String> mutedTEXT = getConfig().getStringList("muted-chat");
                            Iterator var2 = mutedTEXT.iterator();

                            while(var2.hasNext()) {
                            String tts = (String)var2.next();
                 
                            this.getServer().broadcastMessage(tts);
                           } 
                          } 
                       } else if (args[0].equals("off")) {
              	    boolean current = getConfig().getBoolean("global-mute");
                         if (current == false) {
                         	sender.sendMessage(getConfig().getString("already-unmuted"));
                        } else {
                        	getConfig().set("global-mute", false);
                            List<String> unmutedTEXT = getConfig().getStringList("unmuted-chat");
                            Iterator var3 = unmutedTEXT.iterator();

                            while(var3.hasNext()) {
                            String tts = (String)var3.next();
                 
                            this.getServer().broadcastMessage(tts);
                        } 
                    } 
                 } 
              } else {
              	sender.sendMessage("USAGE: /globalmute <on/off");
             } 
                break;
        }
    return true;
} 

@EventHandler
public void onChat(PlayerChatEvent ev) {
	if(getConfig().getBoolean("global-mute") == true && !ev.getPlayer().hasPermission("globalmute.bypass")) {
            ev.setCancelled();
            ev.getPlayer().sendMessage(getConfig().getString("chat-error"));
        }
    } 
} 
      
