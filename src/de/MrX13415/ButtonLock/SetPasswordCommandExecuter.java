package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.LockedBlockGroup.PROTECTION_MODE;

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
	        
	        PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

	        if (currentPlayerVars != null) {

				if (ButtonLock.isProtectable(currentPlayerVars.getCurrentClickedBlock())) {
					
					//find Button in the locked-button-list ...
					LockedBlockGroup group = ButtonLock.getLockedGroup(currentPlayerVars.getCurrentClickedBlock());
					boolean isAnewGroup = false;
					if (group == null) {
						group = new LockedBlockGroup();
						isAnewGroup = true;
					}
					
					//unlock if the password was entered once
					ButtonLock.passwordWasEntered(currentPlayerVars, group);
					
					if(ButtonLock.byPass(player)) group.setUnlock(true);
					
					if (group.hasAccess(player)) {
						
						if (args.length >= 1 && args.length <= 2) {
							if (! group.containsBlock(currentPlayerVars.getCurrentClickedBlock())) group.addBlock(currentPlayerVars.getCurrentClickedBlock());
							
							Block partBlock = BlockFunctions.getPartBlock(currentPlayerVars.getCurrentClickedBlock());
							if (partBlock != null) {
								group.addBlock(partBlock);								
							}
							
							Block attachedBlock = BlockFunctions.getAttachedBlock(currentPlayerVars.getCurrentClickedBlock());
							if (attachedBlock != null) {
								group.addBlock(attachedBlock);								
							}
							
							if (! group.isOwner(((Player) sender).getName())) group.addOwner(((Player) sender).getName());
							group.setPassword(args[0].hashCode());
							group.setUnlock(false);
							
							if (isAnewGroup){
								ButtonLock.addLockedGroup(group);
							}
							
							if (args.length == 2) {
								String protectionMode = args[1].toLowerCase();
								
								if (protectionMode.startsWith("pa")) protectionMode = PROTECTION_MODE.PASSWORD.toString();
								if (protectionMode.startsWith("pr")) protectionMode = PROTECTION_MODE.PRIVATE.toString();
								if (protectionMode.startsWith("pu")) protectionMode = PROTECTION_MODE.PUBLIC.toString();
								
								if (protectionMode.equalsIgnoreCase(PROTECTION_MODE.PASSWORD.toString()) || protectionMode.equalsIgnoreCase(PROTECTION_MODE.PRIVATE.toString()) ||protectionMode.equalsIgnoreCase(PROTECTION_MODE.PUBLIC.toString())){
									PROTECTION_MODE setting = PROTECTION_MODE.valueOf(protectionMode.toUpperCase());
									
									PROTECTION_MODE settingBefore = group.getProtectionMode();
									
									if (setting != settingBefore){
										group.setProtectionMode(setting);
									}
									
								}else{
									
								}
							}
							
							if (group.getProtectionMode() == PROTECTION_MODE.PASSWORD) {
								sender.sendMessage(String.format(ButtonLock.language.GROUP_PROTECTION, ButtonLock.language.PASSWORD));
								
							}else if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
								sender.sendMessage(String.format(ButtonLock.language.GROUP_PROTECTION, ButtonLock.language.PRIVATE));
								sender.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));
								
							}else if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) {
								sender.sendMessage(String.format(ButtonLock.language.GROUP_PROTECTION, ButtonLock.language.PUBLIC));
								sender.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));
								
							}
							
							player.sendMessage(ButtonLock.language.PW_CHANGED);
							return true;	
						}else if (args.length == 0){
							if (! isAnewGroup){
								ButtonLock.removeLockedBlock(group);
								player.sendMessage(ButtonLock.language.PROTECTION_REMOVED);
								return true;
							}
						}
					}else{
						player.sendMessage(ButtonLock.language.DENIED);
						if (args.length == 0){
							player.sendMessage(ButtonLock.language.ENTER_CODE_FIRST);
						}
						return true;
					}
				}else{
					player.sendMessage(ButtonLock.language.NOT_PROTECTABLE);
					return true;
					
				}
			}else if(args.length == 0){
				player.sendMessage(ButtonLock.language.WHICH_BLOCK);
				return true;
			}
		}
	    return false;
	}
}
