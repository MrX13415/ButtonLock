package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import org.bukkit.ChatColor;


public class Language {

	private static String languageFileExtention = ".lang";
	private static String languageFilePath = "plugins/" + ButtonLock.getPluginName() + "/languages/";
	
	private static final String keyVersion = "Version";
	
	private static String fileFormat_keys = "%s: %s"; 
	private static String fileFormat_keys_key_value_seperator = ":"; 
	private static String fileFormat_Comments_prefix = "#";
	
    public String languageName;
	public String version;
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
	public String ICONOMY_BYPASS;
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
	
	public Language() {
		setDefaults();
	}
	
	public void setDefaults() {
		languageName = "english";
		version = "----";
		
		GROUP_INFO = ChatColor.GRAY + "-- Group Information ----------";
		GROUP_INFO_ENDE = ChatColor.GRAY + "-----------------------------";
		ONE_TIME_PASSWORDS = ChatColor.GRAY + "One-time passwords: " + ChatColor.GOLD + "%s";
		COSTS = ChatColor.GRAY + "Costs: " + ChatColor.GOLD + "$%s";
		COSTS_FREE = ChatColor.GRAY + "Costs: " + ChatColor.GOLD + "FREE";
		MATERIAL = ChatColor.GRAY + "Material: " + ChatColor.GOLD + "%s";
		PROTECTABLE = ChatColor.GRAY + "Block is protectable: " + ChatColor.GOLD + "%s";
		UNLOCKED = ChatColor.GREEN + "UNLOCKED";
		LOCKED = ChatColor.RED + "LOCKED";
		STATUS = ChatColor.GRAY + "Access status: %s";
		SUCCEED = ChatColor.GRAY + "Access " + ChatColor.GREEN + "SUCCEED";
		DENIED = ChatColor.GRAY + "Access " + ChatColor.RED + "DENIED";
		PROTECTION_MODE_IS = ChatColor.GRAY + "Protection mode is: " + ChatColor.GOLD + "%s";
		PRIVATE = "Private";
		PASSWORD = "Password";
		PUBLIC = "Public";
		ENTER_CODE_CHAT = ChatColor.GRAY + "Enter the required password in the Chat: (Timeout: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " seconds)";
		ENTER_CODE_COMMAND = ChatColor.GRAY + "Enter the required password with '/pw <password>' ";
		CODE = ChatColor.GOLD + "Password: %s";
		PW_CHANGED = ChatColor.GRAY + "Password changed ...";
		WHICH_BLOCK = ChatColor.RED + "Select a Block first ...";
		NOT_PROTECTABLE = ChatColor.RED + "This Block is not protectable ...";
		PROTECTION_REMOVED = ChatColor.GRAY + "Protection removed ...";
		ICONOMY_NO_ACC = ChatColor.GRAY + "You have no account ...";
		ICONOMY_NOT_VALID_ACC = ChatColor.GRAY + "The account is not valid ...";
		ICONOMY_LESS_MONY = ChatColor.GRAY + "You dont have enough mony. You need: " + ChatColor.GOLD + "$%s";
		ICONOMY_MONY_SUBTRACTED = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "$%s";
		ICONOMY_MONY_SUBTRACTED_FREE = ChatColor.GRAY + "Used costs: " + ChatColor.GOLD + "FREE";
		ENTER_CODE_FIRST = ChatColor.GRAY + "Enter the password first ...";
		UNLOCK_BLOCK = ChatColor.GRAY + "Password entered: Click at a Block to unlock it ...";
		ONE_TIME_CODE_UESED = ChatColor.GRAY + "One-time Password consumed ... (doesn't work a second time)";
		ONE_TIME_CODE_ADDED = ChatColor.GRAY + "One-time Password(s) added: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE_REMOVED = ChatColor.GRAY + "One-time Password(s) removed: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE_CLEAR = ChatColor.GRAY + "All One-time Passwords removed ...";
		ONE_TIME_CODE_RECEIVED = ChatColor.GRAY + "You have received a one-time password from: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE = ChatColor.GRAY + "Your one-time password is: " + ChatColor.GOLD + "%s";	
		FOR_PLAYERS = ChatColor.GRAY + "For Player(s): " + ChatColor.GOLD + "%s";	
		ERROR_LOADING = ChatColor.GRAY + "%s " + ChatColor.RED + "Some errors occurred during loading ... (see console)";
		GROUP_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "every time" + ChatColor.GRAY +  " ...";
		GROUP_NOT_FORCEPW = ChatColor.GRAY + "At this group, you must enter a password " + ChatColor.GOLD + "only once" + ChatColor.GRAY + " ...";
		GROUP_BLOCK_ADD = ChatColor.GRAY + "Click at a Block to add it to the current Locked group ... (it is locked too, after that)";
		GROUP_BLOCK_REMOVE = ChatColor.GRAY + "Click at a Block to remove it to the current Locked group ... (it is NOT locked anymore)";
		GROUP_BLOCK_ADDED = ChatColor.GRAY + "Block added ...";
		GROUP_BLOCK_REMOVED = ChatColor.GRAY + "Block removed ...";
		PW_BYPASS = ChatColor.GRAY + "You by-passed the Password: Access " + ChatColor.GREEN + "SUCCEED";
		ICONOMY_BYPASS = ChatColor.GRAY + "You by-passed IConomy: Access " + ChatColor.GREEN + "SUCCEED";
		GROUP_COSTS_CHANGED_COSTS = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "$%s";
		GROUP_COSTS_CHANGED_FREE = ChatColor.GRAY + "Costs changed for this group. New costs: " + ChatColor.GOLD + "FREE";
		GROUP_PROTECTION = ChatColor.GRAY + "Protection mode changed to: " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_LIST = ChatColor.GRAY + "Owner(s): " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_ADDED = ChatColor.GRAY + "Owner(s) added: " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_REMOVED = ChatColor.GRAY + "Owner(s) removed: " + ChatColor.GOLD + "%s";
		PLAYER_NOT_FOUND = ChatColor.RED + "Player(s) not found !";
		INVALID_STATE = ChatColor.RED + "Invalid state !";
		STATE_CHANGED = ChatColor.GRAY + "Locked state changed to: " + ChatColor.GOLD + "%s";
		CONSOLE_MATERIALS_ADDED = "%s Material(s) added: %s";
		CONSOLE_MATERIALS_REMOVED = "%s Material(s) removed: %s";
		MATERIALS_ADDED = ChatColor.GRAY + "Material(s) added: " + ChatColor.GOLD + "%s";
		MATERIALS_REMOVED = ChatColor.GRAY + "Material(s) removed: " + ChatColor.GOLD + "%s";
		CONSOLE_WHICH_MATERIAL = "%s Material not found ...";
		COMMAND_INGAME_ONLY = "%s This Command is only ingame available";
		CANT_REMOVE_LOCKED_GROUPS = ChatColor.RED + "You can't remove a locked group. Remove the password first.";
		GROUP_SIZE = ChatColor.GRAY + "Group size: " + ChatColor.GOLD + " %s";
		COMMAND_OP_ONLY = ChatColor.RED + "You must be OP to use this command ...";
		PERMISSIONS_NOT = ChatColor.RED + "You don't have the required Permission (" + ChatColor.GOLD + "%s" + ChatColor.RED + ")"; 
		TRUE = "true";
		FALSE = "false";
		MASK_CHR = ChatColor.GRAY + "*";
		SEPERATE_CHR = ", ";

		COMMAND_SETPASSWORD_USAGE = ChatColor.GREEN + "Command: " + ChatColor.RED + "/setpassword" + ChatColor.GREEN + " or " + ChatColor.RED + "/setpw " +
				 					ChatColor.GREEN + "(see also: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
				 					ChatColor.RED + "/setpw "			+ ChatColor.GOLD + "<password> "				+ ChatColor.GRAY + "Set/changes a password" + "\n" +
				 					ChatColor.RED + "/setpw "	+ ChatColor.GOLD + "<password> <protection> "	+ ChatColor.GRAY + "Set/changes a password and the protection mode" + "\n" +
				 					ChatColor.RED + "/setpw "	+ ChatColor.GOLD + "<password> PASSWORD "		+ ChatColor.GRAY + "~ sets protection mode to PASSWORD (default)" + "\n" +
				 					ChatColor.RED + "/setpw "	+ ChatColor.GOLD + "<password> PUBLIC "			+ ChatColor.GRAY + "~ sets protection mode to PUBLIC" + "\n" +
				 					ChatColor.RED + "/setpw "	+ ChatColor.GOLD + "<password> PRIVATE "		+ ChatColor.GRAY + "~ sets protection mode to PRIVATE" + "\n" +
				 					ChatColor.RED + "/setpw " 	+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Removes a password";
		COMMAND_SETPASSWORD_DESCRIPTION = "Set, changes or removes a password";
		
		COMMAND_PASSWORD_USAGE = ChatColor.GREEN + "Command: " + ChatColor.RED + "/password" + ChatColor.GREEN + " or " + ChatColor.RED + "/pw " +
								 ChatColor.GREEN + "(see also: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
								 ChatColor.RED + "/pw " + ChatColor.GOLD + "<password> " + ChatColor.GRAY + "Enter a password";
		COMMAND_PASSWORD_DESCRIPTION = "Enter a password";
		
		COMMAND_ONETIMEPASSWORD_USAGE = ChatColor.GREEN + "Command: " + ChatColor.RED + "/onetimepassword" + ChatColor.GREEN + " or " + ChatColor.RED + "/otpw " +
			 	   						ChatColor.GREEN + "(see also: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
			 	   						ChatColor.RED + "/otpw "			+ ChatColor.GOLD + "<add|remove> <password> "	+ ChatColor.GRAY + "Adds/removes a one-time password to/from a group" + "\n" +
										ChatColor.RED + "/otpw removeall"	+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Removes all one-time passwords from a group" + "\n" +
										ChatColor.RED + "/otpw generate"	+ ChatColor.GOLD + "<player1> [plr2] [...]"		+ ChatColor.GRAY + "Generates random one-time passwords for the given players";
		COMMAND_ONETIMEPASSWORD_DESCRIPTION = "Add or remove a one-tme password";
		
		COMMAND_BUTTONLOCK_USAGE = ChatColor.GREEN + "Command: " + ChatColor.RED + "/buttonlock" + ChatColor.GREEN + " or " + ChatColor.RED + "/bl " +
								   ChatColor.GREEN + "(see also: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
								   ChatColor.RED + "/bl "						+ ChatColor.GOLD + "<save|reload> "				+ ChatColor.GRAY + "Saves/reloads all config-files from Buttonlock" + "\n" +
								   ChatColor.RED + "/bl version "				+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Installed version of Buttonlock" + "\n" +
								   ChatColor.RED + "/bl remove " 				+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Removes a locked Group ... (without a password!)" + "\n" +
								   ChatColor.RED + "/bl reset " 				+ ChatColor.GOLD + "<all|langs|config|groups> "	+ ChatColor.GRAY + "Resets Buttonlock ..." + "\n" +
								   ChatColor.RED + "/bl protectableBlocks " 	+ ChatColor.GOLD + "<add|remove> [blocktype] "	+ ChatColor.GRAY + "Makes the given block protectable" + "\n" +
								   ChatColor.RED + "/bl info " 					+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Informations about a group" + "\n" +
								   ChatColor.RED + "/bl group "					+ ChatColor.GOLD + "<add|remove> "				+ ChatColor.GRAY + "Adds/removes a Block to/from a group" + "\n" +
								   ChatColor.RED + "/bl group costs "			+ ChatColor.GOLD + "[costs] "					+ ChatColor.GRAY + "Change the costs of a locked group" + "\n" +
								   ChatColor.RED + "/bl group forcePassword "	+ ChatColor.GOLD + "<true|false> "				+ ChatColor.GRAY + "Enforces a password every time if true" + "\n" +
								   ChatColor.RED + "/bl group owner "			+ ChatColor.GOLD + "<add|remove> <o1> [...] "	+ ChatColor.GRAY + "Adds/removes a owner to/from a group" + "\n" +
								   ChatColor.RED + "/bl group setLockedState "	+ ChatColor.GOLD + "<open|close|on|off|both> "	+ ChatColor.GRAY + "Set/changes the locked state";
		COMMAND_BUTTONLOCK_DESCRIPTION = "Manages Buttonlock and locked groups";
		
	}
	
	public String getLanguageFileName(){
		return languageName + languageFileExtention;
	}
	
	public boolean isUptoDate(){
		if (this.version.equalsIgnoreCase(ButtonLock.getPluginDescriptionFile().getVersion())){
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
			setDefaults();
			version = ButtonLock.getPluginDescriptionFile().getVersion().toString();
		    returnStatus = write();
		}
		return returnStatus;
	}
	
	public String getBoolean(boolean value){
		if (value) return TRUE;
		return FALSE;
	}
	
	/**
	 * Use colors as default ...
	 * 
	 * @param listArray
	 * @return
	 */
	public String getList(Object[] listArray){
		return getList(listArray, true);
	}
	
	public String getList(Object[] listArray, boolean useColors){
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
	
	public String getMaskedText(String text){
		String maskedText = "";
		for (int index = 0; index < text.length(); index++) {
			maskedText += MASK_CHR;
		}
		
		return maskedText;
	}
	
	public boolean langExists(String languageName) {
		langExistsThis();
		
		File langfile = new File(languageFilePath + languageName + languageFileExtention);
		if (langfile.exists()) {
			return true;
		}
		return false;
	}
	
	public boolean langExistsThis() {
		File langfile = new File(languageFilePath + languageName + languageFileExtention);
		if (! langfile.exists()) {
			return write();
		}
		return true;
	}
	
	
	public void load() {
		load(languageName);
	}
		
	public void load(String languageName) {
		LineNumberReader reader;
		
		try {
			reader = new LineNumberReader(new FileReader(languageFilePath + languageName + languageFileExtention));
			
			try {
				while (reader.ready()) {
					String line = reader.readLine();

					if (! line.startsWith(fileFormat_Comments_prefix) && ! line.isEmpty()) {
						

						String[] keyLine = new String[2];
						keyLine[0] = line;
						keyLine[1] = "";
						if (line.contains(fileFormat_keys_key_value_seperator)) {
							keyLine[0] = line.substring(0, line.indexOf(fileFormat_keys_key_value_seperator)).trim();
							keyLine[1] = line.substring(line.indexOf(fileFormat_keys_key_value_seperator) + fileFormat_keys_key_value_seperator.length()).trim();
						}
					
						this.languageName = languageName;
						
						if (keyLine[0].equalsIgnoreCase(keyVersion)) {version = keyLine[1].trim();}
						
						if (keyLine[0].equalsIgnoreCase("GROUP_INFO")) {GROUP_INFO = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_INFO_ENDE")) {GROUP_INFO_ENDE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_PASSWORDS")) {ONE_TIME_PASSWORDS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COSTS")) {COSTS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COSTS_FREE")) {COSTS_FREE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("MATERIAL")) {MATERIAL = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTABLE")) {PROTECTABLE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("UNLOCKED")) {UNLOCKED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("LOCKED")) {LOCKED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("STATUS")) {STATUS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("SUCCEED")) {SUCCEED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("DENIED")) {DENIED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTION_MODE_IS")) {PROTECTION_MODE_IS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PRIVATE")) {PRIVATE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PASSWORD")) {PASSWORD = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PUBLIC")) {PUBLIC = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ENTER_CODE_CHAT")) {ENTER_CODE_CHAT = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ENTER_CODE_COMMAND")) {ENTER_CODE_COMMAND = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("CODE")) {CODE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PW_CHANGED")) {PW_CHANGED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("WHICH_BLOCK")) {WHICH_BLOCK = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("NOT_PROTECTABLE")) {NOT_PROTECTABLE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTION_REMOVED")) {PROTECTION_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_NO_ACC")) {ICONOMY_NO_ACC = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_NOT_VALID_ACC")) {ICONOMY_NOT_VALID_ACC = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_LESS_MONY")) {ICONOMY_LESS_MONY = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_MONY_SUBTRACTED")) {ICONOMY_MONY_SUBTRACTED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_MONY_SUBTRACTED_FREE")) {ICONOMY_MONY_SUBTRACTED_FREE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ENTER_CODE_FIRST")) {ENTER_CODE_FIRST = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("UNLOCK_BLOCK")) {UNLOCK_BLOCK = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE_UESED")) {ONE_TIME_CODE_UESED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE_ADDED")) {ONE_TIME_CODE_ADDED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE_REMOVED")) {ONE_TIME_CODE_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE_CLEAR")) {ONE_TIME_CODE_CLEAR = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE_RECEIVED")) {ONE_TIME_CODE_RECEIVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ONE_TIME_CODE")) {ONE_TIME_CODE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("FOR_PLAYERS")) {FOR_PLAYERS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ERROR_LOADING")) {ERROR_LOADING = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_FORCEPW")) {GROUP_FORCEPW = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_NOT_FORCEPW")) {GROUP_NOT_FORCEPW = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_BLOCK_ADD")) {GROUP_BLOCK_ADD = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_BLOCK_REMOVE")) {GROUP_BLOCK_REMOVE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_BLOCK_ADDED")) {GROUP_BLOCK_ADDED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_BLOCK_REMOVED")) {GROUP_BLOCK_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PW_BYPASS")) {PW_BYPASS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("ICONOMY_BYPASS")) {ICONOMY_BYPASS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_COSTS_CHANGED_COSTS")) {GROUP_COSTS_CHANGED_COSTS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_COSTS_CHANGED_FREE")) {GROUP_COSTS_CHANGED_FREE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_PROTECTION_PASSWORD")) {GROUP_PROTECTION = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTION_OWNER_LIST")) {PROTECTION_OWNER_LIST = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTION_OWNER_ADDED")) {PROTECTION_OWNER_ADDED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PROTECTION_OWNER_REMOVED")) {PROTECTION_OWNER_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PLAYER_NOT_FOUND")) {PLAYER_NOT_FOUND = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("INVALID_STATE")) {INVALID_STATE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("STATE_CHANGED")) {STATE_CHANGED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("CONSOLE_MATERIALS_ADDED")) {CONSOLE_MATERIALS_ADDED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("CONSOLE_MATERIALS_REMOVED")) {CONSOLE_MATERIALS_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("MATERIALS_ADDED")) {MATERIALS_ADDED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("MATERIALS_REMOVED")) {MATERIALS_REMOVED = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("CONSOLE_WHICH_MATERIAL")) {CONSOLE_WHICH_MATERIAL = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COMMAND_INGAME_ONLY")) {COMMAND_INGAME_ONLY = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("CANT_REMOVE_LOCKED_GROUPS")) {CANT_REMOVE_LOCKED_GROUPS = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("GROUP_SIZE")) {GROUP_SIZE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("TRUE")) {TRUE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("FALSE")) {FALSE = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("MASK_CHR")) {MASK_CHR = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("SEPERATE_CHR")) {SEPERATE_CHR = keyLine[1];}
						
						if (keyLine[0].equalsIgnoreCase("COMMAND_SETPASSWORD_USAGE")) {COMMAND_SETPASSWORD_USAGE = keyLine[1].replace("\\n", "\n");}
						if (keyLine[0].equalsIgnoreCase("COMMAND_SETPASSWORD_DESCRIPTION")) {COMMAND_SETPASSWORD_DESCRIPTION = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COMMAND_PASSWORD_USAGE")) {COMMAND_PASSWORD_USAGE = keyLine[1].replace("\\n", "\n");}
						if (keyLine[0].equalsIgnoreCase("COMMAND_PASSWORD_DESCRIPTION")) {COMMAND_PASSWORD_DESCRIPTION = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COMMAND_ONETIMEPASSWORD_USAGE")) {COMMAND_ONETIMEPASSWORD_USAGE = keyLine[1].replace("\\n", "\n");}
						if (keyLine[0].equalsIgnoreCase("COMMAND_ONETIMEPASSWORD_DESCRIPTION")) {COMMAND_ONETIMEPASSWORD_DESCRIPTION = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COMMAND_BUTTONLOCK_USAGE")) {COMMAND_BUTTONLOCK_USAGE = keyLine[1].replace("\\n", "\n");}
						if (keyLine[0].equalsIgnoreCase("COMMAND_BUTTONLOCK_DESCRIPTION")) {COMMAND_BUTTONLOCK_DESCRIPTION = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("COMMAND_OP_ONLY")) {COMMAND_OP_ONLY = keyLine[1];}
						if (keyLine[0].equalsIgnoreCase("PERMISSIONS_NOT")) {PERMISSIONS_NOT = keyLine[1];}
						
					}
				}	
				
			} catch (Exception e) {
				ButtonLock.getLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: An error occurred while loading the language file.");
				e.printStackTrace();
				
			}

		} catch (FileNotFoundException e) {
			ButtonLock.getLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: language not found: " + languageName);
		}
	}
	
	public boolean write() {
		FileWriter writer;
		try {
			File directory = new File(languageFilePath);
			if (! directory.exists()) directory.mkdir();
			
			writer = new FileWriter(languageFilePath + languageName + languageFileExtention);

			writer.write("#" + ButtonLock.getPluginDescriptionFile().getName() + " by " + ButtonLock.getPluginDescriptionFile().getAuthors() + "\n");
			writer.write("#Language: " + languageName + "  Autor: " + ButtonLock.getPluginDescriptionFile().getAuthors() + "\n");
			writer.write("#\n");
			writer.write("# Colors: \n");
			writer.write("# \n");
			writer.write("#    Use the prefix behind the 'Name' of the color.\n");
			writer.write("#    Example: \"" + ChatColor.GOLD + "GOLD\"\n");
			writer.write("# \n");
			for (ChatColor color : ChatColor.values()) {
				writer.write("#        " + color.name() + ": " + color + "\n");
			}
			writer.write("#\n");
			writer.write("# Format: \n");
			writer.write("# \n");
			writer.write("#    The Prefix %s will be replaced with a internal value.\n");
			writer.write("#    Example: \"Player added: %s\" ==> \"Player added: MrX13415\"\n");
			writer.write("# \n");
			writer.write("#    It is not necessary to use the prefix.\n");
			writer.write("#    Example: \"Player added ...\"\n");
			writer.write("# \n");
			writer.write("#    For future informations visit: http://download.oracle.com/javase/6/docs/api/java/util/Formatter.html\n");
			writer.write("# \n");
			writer.write(String.format(fileFormat_keys, keyVersion, version) + "\n");
			writer.write("\n");
			writer.write(String.format(fileFormat_keys, "GROUP_INFO", GROUP_INFO) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_INFO_ENDE", GROUP_INFO_ENDE) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_PASSWORDS", ONE_TIME_PASSWORDS) + "\n");
			writer.write(String.format(fileFormat_keys, "COSTS", COSTS) + "\n");
			writer.write(String.format(fileFormat_keys, "COSTS_FREE", COSTS_FREE) + "\n");
			writer.write(String.format(fileFormat_keys, "MATERIAL", MATERIAL) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTABLE", PROTECTABLE) + "\n");
			writer.write(String.format(fileFormat_keys, "UNLOCKED", UNLOCKED) + "\n");
			writer.write(String.format(fileFormat_keys, "LOCKED", LOCKED) + "\n");
			writer.write(String.format(fileFormat_keys, "STATUS", STATUS) + "\n");
			writer.write(String.format(fileFormat_keys, "SUCCEED", SUCCEED) + "\n");
			writer.write(String.format(fileFormat_keys, "DENIED", DENIED) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTION_MODE_IS", PROTECTION_MODE_IS) + "\n");
			writer.write(String.format(fileFormat_keys, "PRIVATE", PRIVATE) + "\n");
			writer.write(String.format(fileFormat_keys, "PASSWORD", PASSWORD) + "\n");
			writer.write(String.format(fileFormat_keys, "PUBLIC", PUBLIC) + "\n");
			writer.write(String.format(fileFormat_keys, "ENTER_CODE_CHAT", ENTER_CODE_CHAT) + "\n");
			writer.write(String.format(fileFormat_keys, "ENTER_CODE_COMMAND", ENTER_CODE_COMMAND) + "\n");
			writer.write(String.format(fileFormat_keys, "CODE", CODE) + "\n");
			writer.write(String.format(fileFormat_keys, "PW_CHANGED", PW_CHANGED) + "\n");
			writer.write(String.format(fileFormat_keys, "WHICH_BLOCK", WHICH_BLOCK) + "\n");
			writer.write(String.format(fileFormat_keys, "NOT_PROTECTABLE", NOT_PROTECTABLE) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTION_REMOVED", PROTECTION_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_NO_ACC", ICONOMY_NO_ACC) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_NOT_VALID_ACC", ICONOMY_NOT_VALID_ACC) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_LESS_MONY", ICONOMY_LESS_MONY) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_MONY_SUBTRACTED", ICONOMY_MONY_SUBTRACTED) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_MONY_SUBTRACTED_FREE", ICONOMY_MONY_SUBTRACTED_FREE) + "\n");
			writer.write(String.format(fileFormat_keys, "ENTER_CODE_FIRST", ENTER_CODE_FIRST) + "\n");
			writer.write(String.format(fileFormat_keys, "UNLOCK_BLOCK", UNLOCK_BLOCK) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE_UESED", ONE_TIME_CODE_UESED) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE_ADDED", ONE_TIME_CODE_ADDED) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE_REMOVED", ONE_TIME_CODE_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE_CLEAR", ONE_TIME_CODE_CLEAR) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE_RECEIVED", ONE_TIME_CODE_RECEIVED) + "\n");
			writer.write(String.format(fileFormat_keys, "ONE_TIME_CODE", ONE_TIME_CODE) + "\n");
			writer.write(String.format(fileFormat_keys, "FOR_PLAYERS", FOR_PLAYERS) + "\n");
			writer.write(String.format(fileFormat_keys, "ERROR_LOADING", ERROR_LOADING) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_FORCEPW", GROUP_FORCEPW) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_NOT_FORCEPW", GROUP_NOT_FORCEPW) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_BLOCK_ADD", GROUP_BLOCK_ADD) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_BLOCK_REMOVE", GROUP_BLOCK_REMOVE) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_BLOCK_ADDED", GROUP_BLOCK_ADDED) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_BLOCK_REMOVED", GROUP_BLOCK_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "PW_BYPASS", PW_BYPASS) + "\n");
			writer.write(String.format(fileFormat_keys, "ICONOMY_BYPASS", ICONOMY_BYPASS) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_COSTS_CHANGED_COSTS", GROUP_COSTS_CHANGED_COSTS) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_COSTS_CHANGED_FREE", GROUP_COSTS_CHANGED_FREE) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_PROTECTION_PASSWORD", GROUP_PROTECTION) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTION_OWNER_LIST", PROTECTION_OWNER_LIST) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTION_OWNER_ADDED", PROTECTION_OWNER_ADDED) + "\n");
			writer.write(String.format(fileFormat_keys, "PROTECTION_OWNER_REMOVED", PROTECTION_OWNER_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "PLAYER_NOT_FOUND", PLAYER_NOT_FOUND) + "\n");
			writer.write(String.format(fileFormat_keys, "INVALID_STATE", INVALID_STATE) + "\n");
			writer.write(String.format(fileFormat_keys, "STATE_CHANGED", STATE_CHANGED) + "\n");
			writer.write(String.format(fileFormat_keys, "MATERIALS_ADDED_CONSOLE", CONSOLE_MATERIALS_ADDED) + "\n");
			writer.write(String.format(fileFormat_keys, "MATERIALS_REMOVED_CONSOLE", CONSOLE_MATERIALS_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "MATERIALS_ADDED", MATERIALS_ADDED) + "\n");
			writer.write(String.format(fileFormat_keys, "MATERIALS_REMOVED", MATERIALS_REMOVED) + "\n");
			writer.write(String.format(fileFormat_keys, "WHICH_MATERIAL_CONSOLE", CONSOLE_WHICH_MATERIAL) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_INGAME_ONLY", COMMAND_INGAME_ONLY) + "\n");
			writer.write(String.format(fileFormat_keys, "CANT_REMOVE_LOCKED_GROUPS", CANT_REMOVE_LOCKED_GROUPS) + "\n");
			writer.write(String.format(fileFormat_keys, "GROUP_SIZE", GROUP_SIZE) + "\n");
			writer.write(String.format(fileFormat_keys, "TRUE", TRUE) + "\n");
			writer.write(String.format(fileFormat_keys, "FALSE", FALSE) + "\n");
			writer.write("\n");
			writer.write(String.format(fileFormat_keys, "MASK_CHR", MASK_CHR) + "\n");
			writer.write(String.format(fileFormat_keys, "SEPERATE_CHR", SEPERATE_CHR) + "\n");
			writer.write("\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_SETPASSWORD_USAGE", COMMAND_SETPASSWORD_USAGE.replace("\n", "\\n")) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_SETPASSWORD_DESCRIPTION", COMMAND_SETPASSWORD_DESCRIPTION) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_PASSWORD_USAGE", COMMAND_PASSWORD_USAGE.replace("\n", "\\n")) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_PASSWORD_DESCRIPTION", COMMAND_PASSWORD_DESCRIPTION) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_ONETIMEPASSWORD_USAGE", COMMAND_ONETIMEPASSWORD_USAGE.replace("\n", "\\n")) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_ONETIMEPASSWORD_DESCRIPTION", COMMAND_ONETIMEPASSWORD_DESCRIPTION) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_BUTTONLOCK_USAGE", COMMAND_BUTTONLOCK_USAGE.replace("\n", "\\n")) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_BUTTONLOCK_DESCRIPTION", COMMAND_BUTTONLOCK_DESCRIPTION) + "\n");
			writer.write(String.format(fileFormat_keys, "COMMAND_OP_ONLY", COMMAND_OP_ONLY) + "\n");
			writer.write(String.format(fileFormat_keys, "PERMISSIONS_NOT", PERMISSIONS_NOT) + "\n");
			writer.close();
			
			return true;
		} catch (Exception e1) {
			ButtonLock.getLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: can't create default language file.");		
		}
		return false;
	}
}
