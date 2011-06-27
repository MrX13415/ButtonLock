package de.MrX13415.ButtonLock;

import org.bukkit.ChatColor;

public class Language {

	static final String TEXT_SUCCEED = ChatColor.GREEN + "SUCCEED";
	static final String TEXT_DENIED = ChatColor.RED + "DENIED";
	static final String TEXT_ENTER_CODE_CHAT = ChatColor.WHITE + "Enter the required password in the Chat: ";
	static final String TEXT_ENTER_CODE_COMMAND = ChatColor.WHITE + "Enter the required password with '/pw <password>' ";
	static final String TEXT_CODE = ChatColor.GOLD + "Password: ";
	static final String TEXT_PW_CHANGED = ChatColor.GRAY + "Password changed ...";
	static final String TEXT_WHICH_BUTTON = ChatColor.RED + "Select a Button first ...";
	static final String TEXT_PW_REMOVED = ChatColor.GRAY + "Password removed ...";
	static final String MASK_CHR = ChatColor.GRAY + "*";
	
	
	public static String getMaskedText(String text){
		String maskedText = "";
		for (int index = 0; index < text.length(); index++) {
			maskedText += MASK_CHR;
		}
		
		return maskedText;
	}
}
