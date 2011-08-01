package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
* Handler for the 'SetPassword' command.
* @author MrX13415
*/
public class SetPasswordCommandExecuter implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (sender instanceof Player) {
	        Player player = (Player) sender;

	        if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
				//use Permission
				if (! ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_setpw)) {
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

				if (ButtonLock.isProtectable(tmpVars.getCurrentClickedBlock())) {
					//find Button in the locked-button-list ...
					LockedBlockGroup group = ButtonLock.getLockedGroup(tmpVars.getCurrentClickedBlock());
					boolean isAnewButton = false;
					if (group == null) {
						group = new LockedBlockGroup();
						isAnewButton = true;
					}
			
					if (group.isUnlocked()) {
						if (args.length == 1) {
							group.addBlock(tmpVars.getCurrentClickedBlock());

							Block partBlock = BlockFunctions.getPartBlock(tmpVars.getCurrentClickedBlock());
							if (partBlock != null) {
								group.addBlock(partBlock);								
							}
							
							Block attachedBlock = BlockFunctions.getAttachedBlock(tmpVars.getCurrentClickedBlock());
							if (attachedBlock != null) {
								group.addBlock(attachedBlock);								
							}
							
							//TODO: add more block egg. door, bed, chests ...
							
							
							
							group.setPassword(args[0].hashCode());
							group.setUnlock(false);
							
							if (isAnewButton){
								ButtonLock.addLockedGroup(group);
							}
							
							player.sendMessage(Language.TEXT_PW_CHANGED);
							return true;	
						}else if (args.length == 0){
							ButtonLock.removeLockedBlock(group);
							player.sendMessage(Language.TEXT_PW_REMOVED);
							return true;
						}
					}else{
						player.sendMessage(Language.TEXT_DENIED);
						return true;
					}
				}else{
					player.sendMessage(Language.TEXT_WHICH_BLOCK);
					return true;
					
				}
			}else if(args.length == 0){
				player.sendMessage(Language.TEXT_WHICH_BLOCK);
				return true;
			}
		}
	    return false;
	}
}
