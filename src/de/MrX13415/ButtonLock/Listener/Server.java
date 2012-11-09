package de.MrX13415.ButtonLock.Listener;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import de.MrX13415.ButtonLock.ButtonLock;

public class Server implements Listener {
	 private ButtonLock plugin;

    public Server(ButtonLock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (ButtonLock.iConomy) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
            	ButtonLock.iConomy = false;
                System.out.println(ButtonLock.getConsoleOutputHeader() + " un-hooked from iConomy");
            }
        }
        
        if (ButtonLock.essetials) {
            if (event.getPlugin().getDescription().getName().equals("Essentials")) {
            	ButtonLock.essetials = false;
                System.out.println(ButtonLock.getConsoleOutputHeader() + " un-hooked from Essentials");
            }
        }

    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");
        Plugin essentials = plugin.getServer().getPluginManager().getPlugin("Essentials");
        
        if (iConomy != null && ButtonLock.iConomy == false) {
            if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iCo6.iConomy")) {
            	ButtonLock.iConomy = true;
                System.out.println(ButtonLock.getConsoleOutputHeader() + " hooked into iConomy");
            }
        }
        
        if(essentials != null && ButtonLock.essetials == false){
        	if (essentials.isEnabled()) {
            	ButtonLock.essetials = true;
                System.out.println(ButtonLock.getConsoleOutputHeader() + " hooked into EssetialsEco");
            }
        }
    }
    
    @EventHandler
    public void onServerCommand(ServerCommandEvent event){
    	String cmd = event.getCommand();
    	CommandSender sender = event.getSender();
    	
    	if (cmd.equalsIgnoreCase("save-all")){
	    	ButtonLock.getButtonLockConfig().write();
			ButtonLock.lockedGroupsFile.write();
	
			String msg = "All config-files saved ... ";
			sender.sendMessage(ButtonLock.getConsoleOutputHeader() + " " + msg);
    	}
    }
    
    @EventHandler
    public void onRemoteServerCommand(RemoteServerCommandEvent event){
    	String cmd = event.getCommand();
    	CommandSender sender = event.getSender();
    	
    	if (cmd.equalsIgnoreCase("save-all")){
	    	ButtonLock.getButtonLockConfig().write();
			ButtonLock.lockedGroupsFile.write();
	
			String msg = "All config-files saved ... ";
			sender.sendMessage(ButtonLock.getConsoleOutputHeader() + " " + msg);
    	}
    }
}