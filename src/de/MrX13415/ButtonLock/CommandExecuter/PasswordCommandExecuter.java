package de.MrX13415.ButtonLock.CommandExecuter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup;
import de.MrX13415.ButtonLock.Config.PlayerVars;

/**
* Handler for the 'PassordCommand' command.
* @author MrX13415
*/
public class PasswordCommandExecuter implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (sender instanceof Player) {
	        Player player = (Player) sender;

	        if (ButtonLock.permissions()) {
				//use Permission
				if (! ButtonLock.hasPermission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_use)) {
					sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().PERMISSIONS_NOT, ButtonLock.PERMISSION_NODE_ButtonLock_use));
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
				if (args.length == 1){
					String tmpPassword = args[0];
					currentPlayerVars.setLastPassword(tmpPassword);
					
					if (currentPlayerVars.isEnteringCode()) {
						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().CODE, ButtonLock.getCurrentLanguage().getMaskedText(tmpPassword)));
						
						LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
						if (group != null) group.checkPasswordAndPrintResault(currentPlayerVars, tmpPassword.hashCode());					
						
						currentPlayerVars.setEnteringCode(false);
						return true;
						
					}else if(currentPlayerVars.getLastPassword() != null){
						player.sendMessage(ButtonLock.getCurrentLanguage().UNLOCK_BLOCK);
						return true;
					}
				}
			} 
	    }
        return false;
	}

}