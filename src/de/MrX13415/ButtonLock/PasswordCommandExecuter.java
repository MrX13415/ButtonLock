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

	        PlayerVars tmpVars = ButtonLock.getPlayerVars(player);
	        
	        if (tmpVars != null) {
				if (args.length == 1){
					String tmpPw = args[0];

					if (tmpVars.isEnteringCode()) {
						player.sendMessage(Language.TEXT_CODE + Language.getMaskedText(tmpPw));
						ButtonLock.checkPassword(tmpVars, tmpPw);
						tmpVars.getCurrentClickedLockedButton().setUnlock(true);
						return true;
					}
				}
			} 
	    }
        return false;
	}

}