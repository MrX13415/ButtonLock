package de.MrX13415.ButtonLock.Languages;

import org.bukkit.ChatColor;


public class Language {

	public String _LastVersion = "----"; 
	
    public String _languageName = "";
	public String _version = "";
	
	public String GROUP_INFO;
	public String GROUP_INFO_ENDE;
	public String ONE_TIME_PASSWORDS;
	public String COSTS;
	public String COSTS_FREE;
	public String MATERIAL;
	public String PROTECTABLE;
	public String UNLOCKED; 
	public String LOCKED;
	public String STATUS;
	public String SUCCEED;
	public String DENIED;
	public String PROTECTION_MODE_IS;
	public String PRIVATE;
	public String PASSWORD;
	public String PUBLIC;
	public String ENTER_CODE_CHAT;
	public String ENTER_CODE_COMMAND;
	public String CODE;
	public String PW_CHANGED;
	public String WHICH_BLOCK; 
	public String NOT_PROTECTABLE;
	public String PROTECTION_REMOVED;
	public String ICONOMY_NO_ACC;
	public String ICONOMY_NOT_VALID_ACC;
	public String ICONOMY_LESS_MONY;
	public String ICONOMY_MONY_SUBTRACTED;
	public String ICONOMY_MONY_SUBTRACTED_FREE;
	public String ENTER_CODE_FIRST;
	public String UNLOCK_BLOCK;
	public String ONE_TIME_CODE_UESED;
	public String ONE_TIME_CODE_ADDED;
	public String ONE_TIME_CODE_REMOVED;
	public String ONE_TIME_CODE_CLEAR;
	public String ONE_TIME_CODE_RECEIVED;
	public String ONE_TIME_CODE;
	public String FOR_PLAYERS;
	public String ERROR_LOADING;
	public String GROUP_FORCEPW;
	public String GROUP_NOT_FORCEPW;
	public String GROUP_BLOCK_ADD;
	public String GROUP_BLOCK_REMOVE;
	public String GROUP_BLOCK_ADDED;
	public String GROUP_BLOCK_REMOVED;
	public String PW_BYPASS;
	public String ECONOMY_BYPASS;
	public String GROUP_COSTS_CHANGED_COSTS;
	public String GROUP_COSTS_CHANGED_FREE;
	public String GROUP_PROTECTION;
	public String PROTECTION_OWNER_LIST;
	public String PROTECTION_OWNER_ADDED;
	public String PROTECTION_OWNER_REMOVED;
	public String PLAYER_NOT_FOUND;
	public String INVALID_STATE;
	public String STATE_CHANGED;
	public String CONSOLE_MATERIALS_ADDED;
	public String CONSOLE_MATERIALS_REMOVED;
	public String MATERIALS_ADDED;
	public String MATERIALS_REMOVED;
	public String CONSOLE_WHICH_MATERIAL;
	public String COMMAND_INGAME_ONLY;
	public String CANT_REMOVE_LOCKED_GROUPS;
	public String GROUP_SIZE;
	public String PERMISSIONS_NOT;
	public String PASSWORDLIST_CLEAR;
	
	public String TRUE;
	public String FALSE;
	public String MASK_CHR;
	public String SEPERATE_CHR;
	
	public String COMMAND_SETPASSWORD_USAGE;
	public String COMMAND_SETPASSWORD_DESCRIPTION;
	public String COMMAND_PASSWORD_USAGE;
	public String COMMAND_PASSWORD_DESCRIPTION;
	public String COMMAND_ONETIMEPASSWORD_USAGE;
	public String COMMAND_ONETIMEPASSWORD_DESCRIPTION;
	public String COMMAND_BUTTONLOCK_USAGE;
	public String COMMAND_BUTTONLOCK_DESCRIPTION;
	public String COMMAND_OP_ONLY;
		
	public boolean isUptoDate(){
		if (this._version.equalsIgnoreCase(this._LastVersion)){
			return true;
		}
		return false;
	}

	public boolean update(){
		return update(false);
	}
	
	public boolean update(boolean force){
		boolean returnStatus = false;
		if (! isUptoDate() || force) {
		    returnStatus = LanguageLoader.saveLanguage(this);
		}
		return returnStatus;
	}
	
	public String getBoolean(boolean value){
		if (value) return this.TRUE;
		return this.FALSE;
	}
	
	public String getList(Object[] listArray){
		return getList(listArray, true);
	}
	
	public String getList(Object[] listArray, boolean useColors){
		String returnStr = "";
		for (Object object : listArray) {
			if (useColors) {
				returnStr += ChatColor.GOLD + object.toString() + ChatColor.GRAY + this.SEPERATE_CHR;
			}else{
				returnStr += object.toString() + this.SEPERATE_CHR;
			}
		}
		System.out.println(returnStr.substring(0, returnStr.length() - 1));
		if (returnStr.length() >= 2) return returnStr = returnStr.substring(0, returnStr.length() - 1);
		return returnStr;
	}
	
	public String getMaskedText(String text){
		String maskedText = "";
		for (int index = 0; index < text.length(); index++) {
			maskedText += this.MASK_CHR;
		}
		
		return maskedText;
	}

	public String getLanguageFileName() {
		return _languageName + LanguageLoader.getLanguageFileExtention();
	}
}
