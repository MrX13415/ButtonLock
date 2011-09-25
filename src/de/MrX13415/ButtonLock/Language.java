package de.MrX13415.ButtonLock;

import org.bukkit.ChatColor;


public class Language {

	static final String SUCCEED = "Access " + ChatColor.GREEN + "SUCCEED";
	static final String DENIED = "Access " + ChatColor.RED + "DENIED";
	static final String PROTECTION_MODE_IS = ChatColor.GRAY + "Protection mode is: " + ChatColor.GOLD;
	static final String PRIVATE = "Private";
	static final String PASSWORD = "Password";
	static final String PUBLIC = "Public";
	static final String ENTER_CODE_CHAT = ChatColor.GRAY + "Enter the required password in the Chat: (Timout after: " + ChatColor.GOLD + (double) ((double) ButtonLock.configFile.timeforEnteringPassword/(double) 1000) + ChatColor.GRAY + " seconds)";
	static final String ENTER_CODE_COMMAND = ChatColor.GRAY + "Enter the required password with '/pw <password>' ";
	static final String CODE = ChatColor.GOLD + "Password: ";
	static final String PW_CHANGED = ChatColor.GRAY + "Password changed ...";
	static final String WHICH_BLOCK = ChatColor.RED + "Select a Block first ...";
	static final String NOT_PROTECTABLE = ChatColor.RED + "This Block is not protectable ...";
	static final String PW_REMOVED = ChatColor.GRAY + "Password removed ...";
	static final String ICONOMY_NO_ACC = ChatColor.GRAY + "You have no account ...";
	static final String ICONOMY_NOT_VALID_ACC = ChatColor.GRAY + "The account is not valid ...";
	static final String ICONOMY_LESS_MONY = ChatColor.GRAY + "You dont have enough mony. You need " + ChatColor.GOLD + "$";
	static final String ICONOMY_MONY_SUBTRACTED = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "$";
	static final String ICONOMY_MONY_SUBTRACTED_FREE = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "FREE";
	static final String ENTER_CODE_FIRST = ChatColor.GRAY + "Enter the password first ...";
	static final String UNLOCK_BLOCK = ChatColor.GRAY + "Password entered: Click at a Block to unlock it ...";
	static final String SINGEL_USE_CODE_UESED = ChatColor.GRAY + "Single-use-Password consumed ... (doesn't work a second time)";
	static final String ONE_TIME_CODE_ADDED = ChatColor.GRAY + "Single-use-Password(s) added ...";
	static final String ONE_TIME_CODE_REMOVED = ChatColor.GRAY + "Single-use-Password(s) removed ...";
	static final String ERROR_LOADING = ChatColor.GRAY + ButtonLock.consoleOutputHeader + " " + ChatColor.RED + "Some errors occurred during loading ... (see console)";
	static final String GROUP_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "every time" + ChatColor.GRAY +  " ...";
	static final String GROUP_NOT_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "only once" + ChatColor.GRAY + " ...";
	static final String GROUP_BLOCK_ADD = ChatColor.GRAY + "Click at a Block to add it to the current Locked group ... (it is locked too, after that)";
	static final String GROUP_BLOCK_REMOVE = ChatColor.GRAY + "Click at a Block to remove it to the current Locked group ... (it is NOT locked anymore)";
	static final String GROUP_BLOCK_ADDED = ChatColor.GRAY + "Block added ...";
	static final String GROUP_BLOCK_REMOVED = ChatColor.GRAY + "Block removed ...";
	static final String PW_BYPASS = ChatColor.GRAY + "You by-passed the Password: " + ChatColor.WHITE + SUCCEED;
	static final String ICONOMY_BYPASS = ChatColor.GRAY + "You by-passed IConomy: " + ChatColor.WHITE + SUCCEED;
	static final String GROUP_COSTS_CHANGED_COSTS = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "$";
	static final String GROUP_COSTS_CHANGED_FREE = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "FREE";
	static final String GROUP_PROTECTION_PASSWORD = ChatColor.GRAY + "Protection mode changed to: " + ChatColor.GOLD + PASSWORD;
	static final String GROUP_PROTECTION_PRIVATE = ChatColor.GRAY + "Protection mode changed to: " + ChatColor.GOLD + PRIVATE;
	static final String GROUP_PROTECTION_PUBLIC = ChatColor.GRAY + "Protection mode changed to: " + ChatColor.GOLD + PUBLIC;
	static final String PROTECTION_OWNER_LIST = ChatColor.GRAY + "Owner(s): " + ChatColor.GOLD;
	static final String PROTECTION_OWNER_ADDED = ChatColor.GRAY + "Owner(s) added: " + ChatColor.GOLD;
	static final String PROTECTION_OWNER_REMOVED = ChatColor.GRAY + "Owner(s) removed: " + ChatColor.GOLD;
	static final String PLAYER_NOT_FOUND = ChatColor.RED + "Player(s) not found or online !";
	static final String INVALID_STATE = ChatColor.RED + "Invalid state !";
	static final String STATE_CHANGED = ChatColor.GRAY + "Locked state changed to: " + ChatColor.GOLD;
	static final String MATERIALS_ADDED_CONSOLE = ButtonLock.consoleOutputHeader + "Material(s) added: ";
	static final String MATERIALS_REMOVED_CONSOLE = ButtonLock.consoleOutputHeader + " Material(s) removed: ";
	static final String MATERIALS_ADDED = ChatColor.GRAY + "Material(s) added: " + ChatColor.GOLD;
	static final String MATERIALS_REMOVED = ChatColor.GRAY + "Material(s) removed: " + ChatColor.GOLD;
	static final String WHICH_MATERIAL_CONSOLE = ButtonLock.consoleOutputHeader + " Material not found ...";
	
	static final String MASK_CHR = ChatColor.GRAY + "*";
	static final String SEPERATE_CHR = ", ";


	/**
	 * Use colors as default ...
	 * 
	 * @param listArray
	 * @return
	 */
	public static String getList(Object[] listArray){
		return getList(listArray, true);
	}
	
	public static String getList(Object[] listArray, boolean useColors){
		String returnStr = "";
		for (Object object : listArray) {
			if (useColors) {
				returnStr += ChatColor.GOLD + object.toString() + ChatColor.GRAY + SEPERATE_CHR;
			}else{
				returnStr += object.toString() + SEPERATE_CHR;
			}
		}
		
		if (returnStr.length() >= 2) return returnStr = returnStr.substring(0, returnStr.length() - 2);
		return returnStr;
	}
	
	public static String getMaskedText(String text){
		String maskedText = "";
		for (int index = 0; index < text.length(); index++) {
			maskedText += MASK_CHR;
		}
		
		return maskedText;
	}
}
