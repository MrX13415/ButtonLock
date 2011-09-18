package de.MrX13415.ButtonLock;

import org.bukkit.ChatColor;

public class Language {

	static final String TEXT_SUCCEED = "Access " + ChatColor.GREEN + "SUCCEED";
	static final String TEXT_DENIED = "Access " + ChatColor.RED + "DENIED";
	static final String TEXT_ENTER_CODE_CHAT = ChatColor.GRAY + "Enter the required password in the Chat: ";
	static final String TEXT_ENTER_CODE_COMMAND = ChatColor.GRAY + "Enter the required password with '/pw <password>' ";
	static final String TEXT_CODE = ChatColor.GOLD + "Password: ";
	static final String TEXT_PW_CHANGED = ChatColor.GRAY + "Password changed ...";
	static final String TEXT_WHICH_BLOCK = ChatColor.RED + "Select a Block first ...";
	static final String TEXT_NOT_PROTECTABLE = ChatColor.RED + "This Block is not protectable ...";
	static final String TEXT_PW_REMOVED = ChatColor.GRAY + "Password removed ...";
	static final String TEXT_ICONOMY_NO_ACC = ChatColor.GRAY + "You have no account ...";
	static final String TEXT_ICONOMY_NOT_VALID_ACC = ChatColor.GRAY + "The account is not valid ...";
	static final String TEXT_ICONOMY_LESS_MONY = ChatColor.GRAY + "You dont have enough mony. You need " + ChatColor.GOLD + "$";
	static final String TEXT_ICONOMY_MONY_SUBTRACTED = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "$";
	static final String TEXT_ICONOMY_MONY_SUBTRACTED_FREE = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "FREE";
	static final String TEXT_ENTER_CODE_FIRST = ChatColor.GRAY + "Enter the password first ...";
	static final String TEXT_UNLOCK_BLOCK = ChatColor.GRAY + "Password entered: Click at a Block to unlock it ...";
	static final String TEXT_SINGEL_USE_CODE_UESED = ChatColor.GRAY + "Single-use-Password consumed ... (doesn't work a second time)";
	static final String TEXT_ONE_TIME_CODE_ADDED = ChatColor.GRAY + "Single-use-Password(s) added ...";
	static final String TEXT_ONE_TIME_CODE_REMOVED = ChatColor.GRAY + "Single-use-Password(s) removed ...";
	static final String TEXT_ERROR_LOADING = ChatColor.GRAY + ButtonLock.consoleOutputHeader + " " + ChatColor.RED + "Some errors occurred during loading ... (see console)";
	static final String TEXT_GROUP_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "every time" + ChatColor.GRAY +  " ...";
	static final String TEXT_GROUP_NOT_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "only once" + ChatColor.GRAY + " ...";
	static final String TEXT_GROUP_BLOCK_ADD = ChatColor.GRAY + "Click at a Block to add it to the current Locked group ... (it is locked too, after that)";
	static final String TEXT_GROUP_BLOCK_REMOVE = ChatColor.GRAY + "Click at a Block to remove it to the current Locked group ... (it is NOT locked anymore)";
	static final String TEXT_GROUP_BLOCK_ADDED = ChatColor.GRAY + "Block added ...";
	static final String TEXT_GROUP_BLOCK_REMOVED = ChatColor.GRAY + "Block removed ...";
	static final String TEXT_PW_BYPASS = ChatColor.GRAY + "You by-passed the Password: " + ChatColor.WHITE + TEXT_SUCCEED;
	static final String TEXT_ICONOMY_BYPASS = ChatColor.GRAY + "You by-passed IConomy: " + ChatColor.WHITE + TEXT_SUCCEED;
	static final String TEXT_GROUP_COSTS_CHANGED_COSTS = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "$";
	static final String TEXT_GROUP_COSTS_CHANGED_FREE = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "FREE";
	
	static final String MASK_CHR = ChatColor.GRAY + "*";
	
	
	public static String getMaskedText(String text){
		String maskedText = "";
		for (int index = 0; index < text.length(); index++) {
			maskedText += MASK_CHR;
		}
		
		return maskedText;
	}
}
