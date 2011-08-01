package de.MrX13415.ButtonLock;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import com.iConomy.*;

public class Server extends ServerListener {
	 private ButtonLock plugin;

    public Server(ButtonLock plugin) {
        this.plugin = plugin;
    }

	    
    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if (ButtonLock.iConomy != null) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
            	ButtonLock.iConomy = null;
                System.out.println(ButtonLock.consoleOutputHeader + " un-hooked from iConomy.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (ButtonLock.iConomy == null) {
            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iConomy.iConomy")) {
                    ButtonLock.iConomy = (iConomy)iConomy;
                    System.out.println(ButtonLock.consoleOutputHeader + " hooked into iConomy.");
                }
            }
        }
    }
}