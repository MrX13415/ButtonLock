package de.MrX13415.ButtonLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
* Handler for the 'PassordCommand' command.
* @author MrX13415
*/
public class PasswordCommandExecuter implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (sender instanceof Player) {
	        Player player = (Player) sender;

	        if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
				//use Permission
				if (! ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_use)) {
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
				if (args.length == 1){
					String tmpPassword = args[0];
					currentPlayerVars.setLastPassword(tmpPassword);
					
					if (currentPlayerVars.isEnteringCode()) {
						player.sendMessage(Language.TEXT_CODE + Language.getMaskedText(tmpPassword));
						LockedBlockGroup.checkPasswordAndPrintResault(currentPlayerVars, tmpPassword.hashCode());					
						
						currentPlayerVars.setEnteringCode(false);
						return true;
				
					}else if(currentPlayerVars.getLastPassword() != null){
						player.sendMessage(Language.TEXT_UNLOCK_BLOCK);
						return true;
					}
				}
			} 
	    }
        return false;
	}

}