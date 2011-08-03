package de.MrX13415.ButtonLock;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ButtonLockCommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (sender instanceof Player) {
	    	
	        if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
				//use Permission
				if (! ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock)) {
					return false;
				}
			}else{
				//no Permission installed ! (op only)
				if (! sender.isOp()) {
					return false;
				}
			}	   
	        
	    }
	    
	    if (args.length == 1){
			
			//save all ...
			if (args[0].endsWith("save") || args[0].endsWith("s")) {
				ButtonLock.configFile.write();
				ButtonLock.lockedGroupsFile.write();
				
				String msg = "All config-files saved ... ";
				if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}
			
			//reload all ...
			if (args[0].endsWith("reload") || args[0].endsWith("rl")) {
				ButtonLock.configFile = new Config();
				ButtonLock.grouplist = new ArrayList<LockedBlockGroup>();
				
				ButtonLock.configFile.read();
				ButtonLock.lockedGroupsFile.read();

				String msg = "All config-files reloaded ... ";
				if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}
			
			//reset ...
			if (args[0].endsWith("reset")) {
				ButtonLock.configFile = new Config();
				ButtonLock.grouplist = new ArrayList<LockedBlockGroup>();
				ButtonLock.configFile.write();
				ButtonLock.lockedGroupsFile.write();

				String msg = ButtonLock.pluginName + " reseted ... ";
				if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}
		} 
	    
	    
	    
        return false;
	}

}
