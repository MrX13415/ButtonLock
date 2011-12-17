package de.MrX13415.ButtonLock;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

public class Server extends ServerListener {
	 private ButtonLock plugin;

    public Server(ButtonLock plugin) {
        this.plugin = plugin;
    }

	    
    @Override
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

    @Override
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
}