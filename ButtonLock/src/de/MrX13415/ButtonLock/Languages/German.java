package de.MrX13415.ButtonLock.Languages;

import org.bukkit.ChatColor;


public class German extends Language{

	public German(){
		_LastVersion = "1.6"; 
		_languageName = "german";
		_version = "1.6";
		
		GROUP_INFO = ChatColor.GRAY + "-- Gruppen Informationen ------";
		GROUP_INFO_ENDE = ChatColor.GRAY + "-----------------------------";
		ONE_TIME_PASSWORDS = ChatColor.GRAY + "Einmal Passw�rter: " + ChatColor.GOLD + "%s";
		COSTS = ChatColor.GRAY + "Kosten: " + ChatColor.GOLD + "%s";
		COSTS_FREE = ChatColor.GRAY + "Kosten: " + ChatColor.GOLD + "kostenlos";
		MATERIAL = ChatColor.GRAY + "Material: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " (ID: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + ")";
		PROTECTABLE = ChatColor.GRAY + "Block ist sch�tzbar: " + ChatColor.GOLD + "%s";
		UNLOCKED = ChatColor.GREEN + "OFFEN";
		LOCKED = ChatColor.RED + "ABGESCHLOSSEN";
		STATUS = ChatColor.GRAY + "Zugriffs Status: %s";
		SUCCEED = ChatColor.GRAY + "Zugriff " + ChatColor.GREEN + "ERFOLGREICH";
		DENIED = ChatColor.GRAY + "Zugriff " + ChatColor.RED + "VERWEIGERT";
		PROTECTION_MODE_IS = ChatColor.GRAY + "Schutz: " + ChatColor.GOLD + "%s";
		PRIVATE = "Private";
		PASSWORD = "Passwort";
		PUBLIC = "�ffentlich";
		ENTER_CODE_CHAT = ChatColor.GRAY + "Geben Sie das ben�tigte Passwort in den Chat ein: (Verbleibend: " + ChatColor.GOLD + "%s" + ChatColor.GRAY + " sekunden)";
		ENTER_CODE_COMMAND = ChatColor.GRAY + "Geben Sie das ben�tigte Passwort mit '/pw <passwort>' ein.";
		CODE = ChatColor.GOLD + "Passwort: %s";
		PW_CHANGED = ChatColor.GRAY + "Passwort ge�ndert ...";
		WHICH_BLOCK = ChatColor.RED + "W�hlen Sie zuerst ein Block aus ...";
		NOT_PROTECTABLE = ChatColor.RED + "Dieser Block ist nicht sch�tzbar ...";
		PROTECTION_REMOVED = ChatColor.GRAY + "Sch�tz entfernt ...";
		ICONOMY_NO_ACC = ChatColor.GRAY + "Sie haben kein Bank-Account ...";
		ICONOMY_NOT_VALID_ACC = ChatColor.GRAY + "Ihr Bank-Account ist ung�ltig ...";
		ICONOMY_LESS_MONY = ChatColor.GRAY + "Sie haben nicht gen�gend Geld. Sie brauchend: " + ChatColor.GOLD + "%s";
		ICONOMY_MONY_SUBTRACTED = ChatColor.GRAY + "Kosten: " + ChatColor.GOLD + "%s";
		ICONOMY_MONY_SUBTRACTED_FREE = ChatColor.GRAY + "Kosten: " + ChatColor.GOLD + "FREE";
		ICONOMY_MONY_GAINED = ChatColor.GRAY + "Erhalten: " + ChatColor.GOLD + "%s";
		ENTER_CODE_FIRST = ChatColor.GRAY + "Geben Sie zuerst das Passwort ein ...";
		UNLOCK_BLOCK = ChatColor.GRAY + "Ein Passwort wurde eingegeben: Klicken Sie auf ein Block um den Schutz aufzuheben ...";
		ONE_TIME_CODE_UESED = ChatColor.GRAY + "Einmal Passwort verbraucht ... (funktioniert kein 2. mal)";
		ONE_TIME_CODE_ADDED = ChatColor.GRAY + "Einmal Passwort(er) hinzugef�ght: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE_REMOVED = ChatColor.GRAY + "Einmal Passwort(er) entfernt: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE_CLEAR = ChatColor.GRAY + "Alle Einmal Passw�rter wurden entfernt ...";
		ONE_TIME_CODE_RECEIVED = ChatColor.GRAY + "Sie haben ein Einmal Passwort erhalten von: " + ChatColor.GOLD + "%s";
		ONE_TIME_CODE = ChatColor.GRAY + "ihr Einmal Passwort ist: " + ChatColor.GOLD + "%s";	
		FOR_PLAYERS = ChatColor.GRAY + "F�r die Spieler: " + ChatColor.GOLD + "%s";	
		ERROR_LOADING = ChatColor.GRAY + "%s " + ChatColor.RED + "Einige Fehler sind beim laden aufgetreten ... (siehe Konsole)";
		ERROR_SAVING = ChatColor.GRAY + "%s " + ChatColor.RED + "Einige Fehler sind beim speichern aufgetreten ... (siehe Konsole)";
		GROUP_FORCEPW = ChatColor.GRAY + "F�r diese Gruppe, m�ssen Sie das Passwort " + ChatColor.GOLD + "jedesmal" + ChatColor.GRAY +  " eingeben ...";
		GROUP_NOT_FORCEPW = ChatColor.GRAY + "F�r diese Gruppe, m�ssen Sie das Passwort " + ChatColor.GOLD + "nur einmal" + ChatColor.GRAY +  " eingeben ...";
		GROUP_BLOCK_ADD = ChatColor.GRAY + "Klicken Sie auf einen Block, um ihn der aktuellen Gruppe hinzuf�gen ... (Block is auch gesch�tzt)";
		GROUP_BLOCK_REMOVE = ChatColor.GRAY + "Klicken Sie auf einen Block, um ihn von aktuellen Gruppe zu entfernen ... (Block ist nicht mehr gesch�tzt)";
		GROUP_BLOCK_ADDED = ChatColor.GRAY + "Block hinzugef�gt ...";
		GROUP_BLOCK_REMOVED = ChatColor.GRAY + "Block entfernt ...";
		PW_BYPASS = ChatColor.GRAY + "Passwort umgangen: Zugriff " + ChatColor.GREEN + "ERLAUBT";
		ECONOMY_BYPASS = ChatColor.GRAY + "IConomy umgangen: Zugriff " + ChatColor.GREEN + "ERLAUBT";
		GROUP_COSTS_CHANGED_COSTS = ChatColor.GRAY + "Kosten f�r diese Gruppe ge�ndert. Neue Kosten: " + ChatColor.GOLD + "$%s";
		GROUP_COSTS_CHANGED_FREE = ChatColor.GRAY + "Kosten f�r diese Gruppe ge�ndert. Neue Kosten: " + ChatColor.GOLD + "kostenlos";
		GROUP_PROTECTION = ChatColor.GRAY + "Schutz ge�ndert zu: " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_LIST = ChatColor.GRAY + "Besitzer: " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_ADDED = ChatColor.GRAY + "Besitzer hinzugef�ght: " + ChatColor.GOLD + "%s";
		PROTECTION_OWNER_REMOVED = ChatColor.GRAY + "Besitzer entfernt: " + ChatColor.GOLD + "%s";
		PLAYER_NOT_FOUND = ChatColor.RED + "Spieler nicht gefunden !";
		INVALID_STATE = ChatColor.RED + "Ung�ltiger Zustand !";
		STATE_CHANGED = ChatColor.GRAY + "Gesch�tzter Status ge�ndert zu: " + ChatColor.GOLD + "%s";
		CONSOLE_MATERIALS_ADDED = " Material(ien) hinzugef�ght: %s";
		CONSOLE_MATERIALS_REMOVED = " Material(ien) entfernt: %s";
		MATERIALS_ADDED = ChatColor.GRAY + "Material(ien) hinzugef�ght: " + ChatColor.GOLD + "%s";
		MATERIALS_REMOVED = ChatColor.GRAY + "Material(ien) entfernt: " + ChatColor.GOLD + "%s";
		CONSOLE_WHICH_MATERIAL = " Ung�ltiges Material ...";
		COMMAND_INGAME_ONLY = "%s Dieser Befehl ist nur im Spiel verf�gbar";
		CANT_REMOVE_LOCKED_GROUPS = ChatColor.RED + "Gesch�tzten Bl�cke k�nnen nicht entfernen werden. Entfernen Sie zuerst den Shutz";
		GROUP_SIZE = ChatColor.GRAY + "Gruppen gr��e: " + ChatColor.GOLD + "%s";
		COMMAND_OP_ONLY = ChatColor.RED + "Du must OP sein, um diesen Befehl benutzen zu k�nnen";
		PERMISSIONS_NOT = ChatColor.RED + "Du hast nicht die ben�tigte Berechtigung ... (" + ChatColor.GOLD + "%s" + ChatColor.RED + ")"; 
		PASSWORDLIST_CLEAR = ChatColor.GRAY + "Gespeicherte Passw�rter wurden gel�scht ..."; 
		TRUE = "Ja";
		FALSE = "Nein";
		MASK_CHR = ChatColor.GRAY + "*";
		SEPERATE_CHR = ", ";
		
		COMMAND_SETPASSWORD_USAGE = ChatColor.GREEN + "\nBefehl: " + ChatColor.RED + "/setpassword" + ChatColor.GREEN + " oder " + ChatColor.RED + "/setpw " +
					ChatColor.GREEN + "(siehe auch: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
					ChatColor.RED + "/pw "			+ ChatColor.GOLD + "<password> "				+ ChatColor.GRAY + "Setzt/�ndert ein Passwort" + "\n" +
					ChatColor.RED + "/pw "	+ ChatColor.GOLD + "<password> <protection> "	+ ChatColor.GRAY + "Setzt/�ndert ein Passwort und dens Schutz" + "\n" +
					ChatColor.RED + "/pw "	+ ChatColor.GOLD + "<password> PASSWORD "		+ ChatColor.GRAY + "~ Setzt den schutz zu PASSWORD (Standard)" + "\n" +
					ChatColor.RED + "/pw "	+ ChatColor.GOLD + "<password> PUBLIC "			+ ChatColor.GRAY + "~ Setzt den schutz zu PUBLIC" + "\n" +
					ChatColor.RED + "/pw "	+ ChatColor.GOLD + "<password> PRIVATE "		+ ChatColor.GRAY + "~ Setzt den schutz zu PRIVATE" + "\n" +
					ChatColor.RED + "/pw " 	+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Entfernt ein Passwort";
		COMMAND_SETPASSWORD_DESCRIPTION = "Setzt/�ndert oder Entfernt ein Passwort";
		
		COMMAND_PASSWORD_USAGE = ChatColor.GREEN + "\nBefehl: " + ChatColor.RED + "/password" + ChatColor.GREEN + " oder " + ChatColor.RED + "/pw " +
					 ChatColor.GREEN + "(siehe auch: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
					 ChatColor.RED + "/pw " + ChatColor.GOLD + "<password> " + ChatColor.GRAY + "Ein Passwort eingeben ...";
		COMMAND_PASSWORD_DESCRIPTION = "Ein Passwort eingeben ...";
		
		COMMAND_ONETIMEPASSWORD_USAGE = ChatColor.GREEN + "\nBefehl: " + ChatColor.RED + "/onetimepassword" + ChatColor.GREEN + " oder " + ChatColor.RED + "/otpw " +
							ChatColor.GREEN + "(siehe auch: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
							ChatColor.RED + "/otpw "			+ ChatColor.GOLD + "<add|remove> <password> "	+ ChatColor.GRAY + "Ein Einmal-Passwort zu/von einer Gruppe hinzuf�gen/entfernen" + "\n" +
							ChatColor.RED + "/otpw removeall"	+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Entfernt alle Einmal-Passw�rter von einer Gruppe" + "\n" +
							ChatColor.RED + "/otpw generate"	+ ChatColor.GOLD + "<player1> [plr2] [...]"		+ ChatColor.GRAY + "Generiert Zufalls-Einmal-Passw�rter f�r die gegebenen Spieler";
		COMMAND_ONETIMEPASSWORD_DESCRIPTION = "Verwaltet Einmal-Passw�rter";
		
		COMMAND_BUTTONLOCK_USAGE = ChatColor.GREEN + "\nBefehl: " + ChatColor.RED + "/buttonlock" + ChatColor.GREEN + " oder " + ChatColor.RED + "/bl " +
					   ChatColor.GREEN + "(siehe auch: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/buttonlock/pages/commands-list" + ChatColor.GREEN + "):\n" +
					   ChatColor.RED + "/bl "						+ ChatColor.GOLD + "<save|reload> "				+ ChatColor.GRAY + "Speichert/l�dt alle Config-Datein von Buttonlock" + "\n" +
					   ChatColor.RED + "/bl version "				+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Installierte version von Buttonlock" + "\n" +
					   ChatColor.RED + "/bl remove " 				+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Entfernt eine Gruppe ... (ohne Passwort!)" + "\n" +
					   ChatColor.RED + "/bl reset " 				+ ChatColor.GOLD + "<all|langs|config|groups> "	+ ChatColor.GRAY + "Setzt Buttonlock zur�ck ..." + "\n" +
					   ChatColor.RED + "/bl protectableBlocks " 	+ ChatColor.GOLD + "<add|remove> [blocktype] "	+ ChatColor.GRAY + "Der gegebene Block ist sch�tzbar" + "\n" +
					   ChatColor.RED + "/bl info " 					+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "Informationen �ber eine Gruppe" + "\n" +
					   ChatColor.RED + "/bl clearPasswordlist " 	+ ChatColor.GOLD + ""							+ ChatColor.GRAY + "L�scht alle gespeicherten Passw�rter" + "\n" +
					   ChatColor.RED + "/bl group "					+ ChatColor.GOLD + "<add|remove> "				+ ChatColor.GRAY + "Ein Block zu/von einer Gruppe hinzuf�gen/entfernen" + "\n" +
					   ChatColor.RED + "/bl group costs "			+ ChatColor.GOLD + "[costs] "					+ ChatColor.GRAY + "�ndert die Kosten einer gesch�tzten Gruppe" + "\n" +
					   ChatColor.RED + "/bl group forcePassword "	+ ChatColor.GOLD + "<true|false> "				+ ChatColor.GRAY + "Erzwingt die Passwort eingabe wenn true" + "\n" +
					   ChatColor.RED + "/bl group owner "			+ ChatColor.GOLD + "<add|remove> <o1> [...] "	+ ChatColor.GRAY + "Ein Besitzer zu/von einer Gruppe hinzuf�gen/entfernen" + "\n" +
					   ChatColor.RED + "/bl group setLockedState "	+ ChatColor.GOLD + "<open|close|on|off|both> "	+ ChatColor.GRAY + "Setzt/�ndert den gesch�tzten status";
		COMMAND_BUTTONLOCK_DESCRIPTION = "Verwaltet Buttonlock und gesch�tzte Gruppen";
	
	}
}
