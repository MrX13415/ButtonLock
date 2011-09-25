package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.LockedBlockGroup.PROTECTION_MODE;

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
							if (args.length >= 2  && ((group.isUnlocked() && group.getProtectionMode() == PROTECTION_MODE.PASSWORD)
								    || (group.getProtectionMode() == PROTECTION_MODE.PRIVATE && group.isOwner(player.getName()))
								    || (group.getProtectionMode() == PROTECTION_MODE.PUBLIC && group.isOwner(player.getName())))) {
								
								if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
									for (int index = 1; index < args.length; index++) {
										group.addSingleUseCode(args[index].hashCode());
									}
									tmpVars.getPlayer().sendMessage(Language.ONE_TIME_CODE_ADDED);
									return true;
								}
								
								if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
									for (int index = 1; index < args.length; index++) {
										group.addSingleUseCode(args[index].hashCode());
									}
									tmpVars.getPlayer().sendMessage(Language.ONE_TIME_CODE_REMOVED);
									return true;
								}
					
							}else{
								tmpVars.getPlayer().sendMessage(Language.DENIED);
								tmpVars.getPlayer().sendMessage(Language.ENTER_CODE_FIRST);
					        	return true;
							}
							
						}else{
							tmpVars.getPlayer().sendMessage(Language.WHICH_BLOCK);
				        	return true;
						}
		        	}
				}else{
					tmpVars.getPlayer().sendMessage(Language.WHICH_BLOCK);
		        	return true;
				}
			}
		}
	    return false;
	}
}
