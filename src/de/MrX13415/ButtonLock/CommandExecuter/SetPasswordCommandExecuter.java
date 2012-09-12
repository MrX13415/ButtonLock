package de.MrX13415.ButtonLock.CommandExecuter;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Block.BlockFunctions;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup;
import de.MrX13415.ButtonLock.Config.PlayerVars;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup.PROTECTION_MODE;

/**
* Handler for the 'SetPassword' command.
* @author MrX13415
*/
public class SetPasswordCommandExecuter implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (sender instanceof Player) {
	        Player player = (Player) sender;

	        if (ButtonLock.permissions()) {
				//use Permission
				if (! ButtonLock.hasPermission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_setpw)) {
					sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().PERMISSIONS_NOT, ButtonLock.PERMISSION_NODE_ButtonLock_setpw));
					return true;
				}
			}else{
				//no Permission installed ! (op only)
				if (! sender.isOp() && ButtonLock.getButtonLockConfig().oPOnly) {
					sender.sendMessage(ButtonLock.getCurrentLanguage().COMMAND_OP_ONLY);
					return true;
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
					
					if (group.hasAccess(player) || isAnewGroup) {
						
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
								sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().GROUP_PROTECTION, ButtonLock.getCurrentLanguage().PASSWORD));
								
							}else if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
								sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().GROUP_PROTECTION, ButtonLock.getCurrentLanguage().PRIVATE));
								sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_OWNER_LIST, ButtonLock.getCurrentLanguage().getList(group.getOwnerList())));
								
							}else if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) {
								sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().GROUP_PROTECTION, ButtonLock.getCurrentLanguage().PUBLIC));
								sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_OWNER_LIST, ButtonLock.getCurrentLanguage().getList(group.getOwnerList())));
								
							}
							
							player.sendMessage(ButtonLock.getCurrentLanguage().PW_CHANGED);
							return true;	
						}else if (args.length == 0){
							if (! isAnewGroup){
								ButtonLock.removeLockedBlock(group);
								player.sendMessage(ButtonLock.getCurrentLanguage().PROTECTION_REMOVED);
								return true;
							}else{
								
							}
						}
					}else{
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						if (args.length == 0){
							player.sendMessage(ButtonLock.getCurrentLanguage().ENTER_CODE_FIRST);
						}
						return true;
					}
				}else{
					player.sendMessage(ButtonLock.getCurrentLanguage().NOT_PROTECTABLE);
					return true;
					
				}
			}else if(args.length == 0){
				player.sendMessage(ButtonLock.getCurrentLanguage().WHICH_BLOCK);
				return true;
			}
	        
	    	player.sendMessage(ButtonLock.getCurrentLanguage().COMMAND_SETPASSWORD_USAGE);
			return true;
			
		}
		
	
	    return false;
	}
}
