package de.MrX13415.ButtonLock.Languages;

import org.bukkit.ChatColor;


public class English extends Language{

	public English(){
		_LastVersion = "1.5"; 
		_languageName = "english";
	    _version = "1.5";
	
	    GROUP_INFO = ChatColor.GRAY + "-- Group Information ----------";
	    GROUP_INFO_ENDE = ChatColor.GRAY + "-----------------------------";
	    ONE_TIME_PASSWORDS = ChatColor.GRAY + "One-time passwords: " + ChatColor.GOLD + "%s";
	    COSTS = ChatColor.GRAY + "Costs: " + ChatColor.GOLD + "$%s";
	    COSTS_FREE = ChatColor.GRAY + "Costs: " + ChatColor.GOLD + "FREE";
	    MATERIAL = ChatColor.GRAY + "Material: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " (ID: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + ")";
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
		ECONOMY_BYPASS = ChatColor.GRAY + "You by-passed IConomy: Access " + ChatColor.GREEN + "SUCCEED";
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
		PASSWORDLIST_CLEAR = ChatColor.GRAY + "Saved passwords were removed ..."; 
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
								   ChatColor.RED + "/bl clearPasswordlist "		+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Removes all saved passwords" + "\n" +
								   ChatColor.RED + "/bl group "					+ ChatColor.GOLD + "<add|remove> "				+ ChatColor.GRAY + "Adds/removes a Block to/from a group" + "\n" +
								   ChatColor.RED + "/bl group costs "			+ ChatColor.GOLD + "[costs] "					+ ChatColor.GRAY + "Change the costs of a locked group" + "\n" +
								   ChatColor.RED + "/bl group forcePassword "	+ ChatColor.GOLD + "<true|false> "				+ ChatColor.GRAY + "Enforces a password every time if true" + "\n" +
								   ChatColor.RED + "/bl group owner "			+ ChatColor.GOLD + "<add|remove> <o1> [...] "	+ ChatColor.GRAY + "Adds/removes a owner to/from a group" + "\n" +
								   ChatColor.RED + "/bl group setLockedState "	+ ChatColor.GOLD + "<open|close|on|off|both> "	+ ChatColor.GRAY + "Set/changes the locked state";
		COMMAND_BUTTONLOCK_DESCRIPTION = "Manages Buttonlock and locked groups";
		
	}
}
