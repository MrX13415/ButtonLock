package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OneTimePasswordsCommandExecuter implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (sender instanceof Player) {
	        Player player = (Player) sender;
	        
	        if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
				//use Permission
				if (! ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_onetimeCods)) {
					return false;
				}
			}else{
				//no Permission installed ! (op only)
				if (! sender.isOp()) {
					return false;
				}
			}
	        
	        PlayerVars tmpVars = ButtonLock.getPlayerVars(player);

	        if (tmpVars != null) {

	        	Block clickedBlock = tmpVars.getCurrentClickedBlock();
	        	if (clickedBlock != null) {
	        		if (ButtonLock.isProtectable(clickedBlock)) {
	        		
	        			//find Button in the locked-button-list ...
						LockedBlockGroup group = ButtonLock.getLockedGroup(tmpVars.getCurrentClickedBlock());
						
						if (group != null) {
							if (group.isUnlocked() && args.length >= 2) {
								
								if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
									for (int index = 1; index < args.length; index++) {
										group.addSingleUseCode(args[index].hashCode());
									}
									tmpVars.getPlayer().sendMessage(Language.TEXT_ONE_TIME_CODE_ADDED);
									return true;
								}
								
								if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
									for (int index = 1; index < args.length; index++) {
										group.addSingleUseCode(args[index].hashCode());
									}
									tmpVars.getPlayer().sendMessage(Language.TEXT_ONE_TIME_CODE_REMOVED);
									return true;
								}
					
							}else{
								tmpVars.getPlayer().sendMessage(Language.TEXT_ENTER_CODE_FIRST);
							}
							
						}else{
							tmpVars.getPlayer().sendMessage(Language.TEXT_WHICH_BLOCK);
						}
		        	}
				}else{
					tmpVars.getPlayer().sendMessage(Language.TEXT_WHICH_BLOCK);
				}
			}
		}
	    return false;
	}
}
