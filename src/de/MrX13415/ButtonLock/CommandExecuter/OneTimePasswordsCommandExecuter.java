package de.MrX13415.ButtonLock.CommandExecuter;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup;
import de.MrX13415.ButtonLock.Config.PlayerVars;


public class OneTimePasswordsCommandExecuter implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (sender instanceof Player) {
	        Player player = (Player) sender;
	        
	        if (ButtonLock.permissions()) {
				//use Permission
				if (! ButtonLock.hasPermission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_onetimeCods)) {
					sender.sendMessage(String.format(ButtonLock.getCurrentLanguage().PERMISSIONS_NOT, ButtonLock.PERMISSION_NODE_ButtonLock_onetimeCods));
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

	        	Block clickedBlock = currentPlayerVars.getCurrentClickedBlock();
	        	if (clickedBlock != null) {
	        		if (ButtonLock.isProtectable(clickedBlock)) {
	        		
	        			//find Button in the locked-button-list ...
						LockedBlockGroup group = ButtonLock.getLockedGroup(currentPlayerVars.getCurrentClickedBlock());
						
						if (group != null) {
							
							if (! group.hasAccess((Player) sender)) {
								sender.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
								return true;
							}
							
							if (args.length >= 2) {
								if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a")) {
									ArrayList<String> oneTimePasswords = new ArrayList<String>();
									
									for (int index = 1; index < args.length; index++) {
										oneTimePasswords.add(args[index]);
										group.addOneTimePassword(args[index].hashCode());
									}
									currentPlayerVars.getPlayer().sendMessage(String.format(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_ADDED, ButtonLock.getCurrentLanguage().getList(oneTimePasswords.toArray())));
									return true;
								}
								
								if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
									ArrayList<String> oneTimePasswords = new ArrayList<String>();
									
									for (int index = 1; index < args.length; index++) {
										if (group.removeOneTimePassword(args[index].hashCode())) {
											oneTimePasswords.add(args[index]);
										}
									}
									currentPlayerVars.getPlayer().sendMessage(String.format(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_REMOVED, ButtonLock.getCurrentLanguage().getList(oneTimePasswords.toArray())));
									return true;
								}
								
								if (args[0].equalsIgnoreCase("generate") || args[0].equalsIgnoreCase("gen") || args[0].equalsIgnoreCase("g")) {
									ArrayList<String> oneTimePasswords = new ArrayList<String>();
									ArrayList<String> players = new ArrayList<String>();
									
									for (int playerIndex = 1; playerIndex < args.length; playerIndex++) {
										
										for (Player onlinePlayer : ButtonLock.getCurrentServer().getOnlinePlayers()) {
											String onlinePlayerName = onlinePlayer.getName().toLowerCase();
											
											if (onlinePlayerName.startsWith(args[playerIndex].toLowerCase())){
												players.add(onlinePlayer.getName());
												
												String password = generatePassword(4, 8, true);
												oneTimePasswords.add(password);
												
												group.addOneTimePassword(password.hashCode());
												
												onlinePlayer.sendMessage(String.format(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_RECEIVED, sender.getName()));
												onlinePlayer.sendMessage(String.format(ButtonLock.getCurrentLanguage().ONE_TIME_CODE, password));
											}
										}
									}
									
									if (! oneTimePasswords.isEmpty()) {
										currentPlayerVars.getPlayer().sendMessage(String.format(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_ADDED, ButtonLock.getCurrentLanguage().getList(oneTimePasswords.toArray())));
										currentPlayerVars.getPlayer().sendMessage(String.format(ButtonLock.getCurrentLanguage().FOR_PLAYERS, ButtonLock.getCurrentLanguage().getList(players.toArray())));
									}else{
										currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().PLAYER_NOT_FOUND);
									}
									return true;
								}
							}
							
							if (args.length == 1) {
								if (args[0].equalsIgnoreCase("removeAll") || args[0].equalsIgnoreCase("ra")) {
									group.clearOneTimePasswords();
									currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_CLEAR);
									return true;
								}
							}
						}else{
							currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().WHICH_BLOCK);
				        	return true;
						}
		        	}
				}else{
					currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().WHICH_BLOCK);
		        	return true;
				}
			}
		}
	    return false;
	}
	
	/** generates a Password
	 * 
	 * @param minLength
	 * @param maxlength
	 * @param useUperCaseToo
	 * @return
	 */
	private String generatePassword(int minLength, int maxlength, boolean useUperCaseToo) {
		int length = random(minLength, maxlength);  
		
		String[] chars = {"0","1","2","3","4","5","6","7","8","9", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		
		String password = "";
		
		while (password.length() != length) {
			int randomCharNumber = random(0, (chars.length - 1));  
			String randomChar = chars[randomCharNumber];
			
			if (useUperCaseToo) {
				int upercaseNumber = random(0, 1);

				if (upercaseNumber == 1) {
					 randomChar = randomChar.toUpperCase();
				}
			}
			
			password += randomChar;
		}

		return password;
	}
	
    public static int random(int low, int high) {  
        high++;  
        return (int) (Math.random() * (high - low) + low); 
    }  
}
