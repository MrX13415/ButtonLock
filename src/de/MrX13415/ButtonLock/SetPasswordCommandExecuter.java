package de.MrX13415.ButtonLock;

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

	        PlayerVars tmpVars = ButtonLock.getPlayerVars(player);

	        if (tmpVars != null) {

				if (ButtonLock.isProtectable(tmpVars.getCurrentClickedBlock())) {
					//find Button in the locked-button-list ...
					Button button = ButtonLock.getButton(tmpVars.getCurrentClickedBlock());
					boolean isAnewButton = false;
					if (button == null) {
						button = new Button();
						isAnewButton = true;
					}
			
					if (button.isUnlocked()) {
						if (args.length == 1) {
							button.setBlock(tmpVars.getCurrentClickedBlock());
							button.setPassword(args[0].hashCode());
							button.setUnlock(false);
							
							if (isAnewButton) ButtonLock.addButton(button);
							
							player.sendMessage(Language.TEXT_PW_CHANGED);
							return true;	
						}else if (args.length == 0){
							ButtonLock.removeButton(button);
							player.sendMessage(Language.TEXT_PW_REMOVED);
							return true;
						}
					}else{
						player.sendMessage(Language.TEXT_DENIED);
						return true;
					}
				}else{
					player.sendMessage(Language.TEXT_WHICH_BUTTON);
					return true;
					
				}
			}else if(args.length == 0){
				player.sendMessage(Language.TEXT_WHICH_BUTTON);
				return true;
			}
		}
	    return false;
	}
}
