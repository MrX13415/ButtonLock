package de.MrX13415.ButtonLock;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/** ButtonLock for Bukkit
 * 
 * @author MrX13415
 * @version 1.2 r54
 */
public class ButtonLock extends JavaPlugin {
	
	private static PluginDescriptionFile pdfFile = null;
	private static Server server = null;
	private static Logger log = null;
	private static String pluginName = null;
	private static String consoleOutputHeader = null;
	private static Config configFile = null;
	private static Language language = null;
	
	static LockedGroupsConfig lockedGroupsFile = null;
	
	//debug output
	static boolean debugmode = false;
	
	//iconomy
	public static boolean iConomy = false;
	public static boolean essetials = false;
	
	//Commands
	static final String COMMAND_SETPASSWORD = "setpassword";
	static final String COMMAND_PASSWORD = "password";
	static final String COMMAND_ONETIMEPASSWORD = "onetimepassword";
	static final String COMMAND_BUTTONLOCK = "buttonlock";

	//permissions
	private static PermissionHandler permissionHandler;
	private static boolean defaultPermission;
	
	static final String PERMISSION_NODE_ButtonLock_bypass = "ButtonLock.bypass";
	static final String PERMISSION_NODE_ButtonLock_iconcomy_bypass = "ButtonLock.iconomy.bypass";
	static final String PERMISSION_NODE_ButtonLock_use = "ButtonLock.use";
	static final String PERMISSION_NODE_ButtonLock_setpw = "ButtonLock.setpw";
	static final String PERMISSION_NODE_ButtonLock_onetimeCods = "ButtonLock.onetimecode";
	static final String PERMISSION_NODE_ButtonLock_buttonlock_normal = "ButtonLock.buttonlock.normal";
	static final String PERMISSION_NODE_ButtonLock_buttonlock_op = "ButtonLock.buttonlock.op";
	
	//holds information for all Players.
	public static ArrayList<PlayerVars> playerlist = new ArrayList<PlayerVars>();
	
	public static ArrayList<LockedBlockGroup> grouplist = new ArrayList<LockedBlockGroup>();
	public static ArrayList<Material> lockableBlocksList = new ArrayList<Material>();
	
	private final ButtonLockPlayerListener pListener = new ButtonLockPlayerListener();
	private final ButtonLockBlockListener bListener = new ButtonLockBlockListener();
	private final ButtonLockEntityListener eListener = new ButtonLockEntityListener();
//	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	
	@Override
	public void onDisable() {
        lockedGroupsFile.write();
        log.info(consoleOutputHeader + " locked Groups saved");
	}

	@Override
	public void onEnable() {
		//set static vars ...
		server = this.getServer();
		log = server.getLogger();
		
		pdfFile = this.getDescription();
		pluginName = pdfFile.getName();
		consoleOutputHeader = "[" + pluginName + "]";

        log.info(consoleOutputHeader + " v" + pdfFile.getVersion() + " " + pdfFile.getAuthors() + " is enabled.");
  
        //init and update langs ...
        updateLanguages(false);
        
        //load config ...
        configFile = new Config();
        configFile.read();
        if (configFile.update()) log.info(consoleOutputHeader + " Config file updated ...");
   
        //lang loaded from config ...
        if (! language.isUptoDate()) log.warning(consoleOutputHeader + " Your current language is not up to date! (file: " + language.getLanguageFileName() + ")");   
   
        //load locked Buttons ...
        lockedGroupsFile = new LockedGroupsConfig();
        lockedGroupsFile.read();
		
        //---------------------
        
        //register events ...
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_PLACE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BURN, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PHYSICS, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_SPREAD, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FADE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FORM, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FROMTO, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_DISPENSE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_CANBUILD, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_IGNITE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PISTON_EXTEND, bListener, Priority.Normal, this);
		
		pm.registerEvent(Event.Type.ENTITY_EXPLODE, eListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENDERMAN_PICKUP, eListener, Priority.Normal, this);
		
		pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
		
		//register commands ...
		try {
			PluginCommand setpw;
			setpw = this.getCommand(COMMAND_SETPASSWORD);
			setpw.setExecutor(new SetPasswordCommandExecuter());
			setpw.setUsage(language.COMMAND_SETPASSWORD_USAGE);
			setpw.setDescription(language.COMMAND_SETPASSWORD_DESCRIPTION);
			
			PluginCommand pw;
			pw = this.getCommand(COMMAND_PASSWORD);
			pw.setExecutor(new PasswordCommandExecuter());
			pw.setUsage(language.COMMAND_PASSWORD_USAGE);
			pw.setDescription(language.COMMAND_PASSWORD_DESCRIPTION);
			
			PluginCommand otpw;
			otpw = this.getCommand(COMMAND_ONETIMEPASSWORD);
			otpw.setExecutor(new OneTimePasswordsCommandExecuter());
			otpw.setUsage(language.COMMAND_ONETIMEPASSWORD_USAGE);
			otpw.setDescription(language.COMMAND_ONETIMEPASSWORD_DESCRIPTION);
			
			PluginCommand bl;
			bl = this.getCommand(COMMAND_BUTTONLOCK);
			bl.setExecutor(new ButtonLockCommandExecutor());
			bl.setUsage(language.COMMAND_BUTTONLOCK_USAGE);
			bl.setDescription(language.COMMAND_BUTTONLOCK_DESCRIPTION);
			
		} catch (Exception e) {
			log.warning("[" + pdfFile.getName() + "] Error: Commands not definated in 'plugin.yml'");
		}

		//iconomy
		getServer().getPluginManager().registerEvent(org.bukkit.event.Event.Type.PLUGIN_ENABLE, new de.MrX13415.ButtonLock.Server(this), Priority.Monitor, this);
	    getServer().getPluginManager().registerEvent(org.bukkit.event.Event.Type.PLUGIN_DISABLE, new de.MrX13415.ButtonLock.Server(this), Priority.Monitor, this);
	   
		//permission
		setupPermissions();
	}
	
//    public boolean isDebugging(final Player player) {
//        if (debugees.containsKey(player)) {
//            return debugees.get(player);
//        } else {
//            return false;
//        }
//    }
//    
//    public void setDebugging(final Player player, final boolean value) {
//        debugees.put(player, value);
//    }
    	
	public static Server getCurrentServer(){
		return server;
	}
	
	public static String getPluginName(){
		return pluginName;
	}
	
	public static String getConsoleOutputHeader(){
		return consoleOutputHeader;
	}
	
	public static PluginDescriptionFile getPluginDescriptionFile(){
		return pdfFile;
	}
	
	public static Logger getLogger(){
		return log;
	}
	
	public static Config getButtonLockConfig(){
		return configFile;
	}
	
	public static void setButtonLockConfig(Config config){
		configFile = config;
	}
	
	public static Language getCurrentLanguage(){
		return language;
	}
	
	public static void setCurrentLanguage(Language lang){
		language = lang;
	}
	
	public static Language getLanguageDefaults(String language) {
		if(new Language().languageName.equalsIgnoreCase(language)) return new Language();
		if(new Language_German().languageName.equalsIgnoreCase(language)) return new Language_German();
		return new Language();
	}
	
	public static void updateLanguages(boolean force) {
		 //init and update langs ...
        language = new Language();
        language.load();
        if (language.update(force)) log.info(consoleOutputHeader + " Default language file updated ...");   

        //german ...
        Language language_ger = new Language_German();
		language_ger.load();  
        if (language_ger.update(force)) log.info(consoleOutputHeader + " German language file updated ...");        
        language_ger = null;
	}
	
	public static boolean isProtectable(Block block){
		if (block != null) {

			for (int materialIndex = 0; materialIndex < lockableBlocksList.size(); materialIndex++) {
				Material material = lockableBlocksList.get(materialIndex);

				if (block.getType().equals(material)) return true;	
			}
		}
		return false;
	}
	
	public static boolean isProtected(Block block){
		for (int groupIndex = 0; groupIndex < grouplist.size(); groupIndex++) {
			LockedBlockGroup group = grouplist.get(groupIndex);
			
			for (int blockIndex = 0; blockIndex < group.getGroupSize(); blockIndex++) {
				Block protectedBlock = group.getBlock(blockIndex);

				if (block.equals(protectedBlock)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean permissions(){
		if ((ButtonLock.permissionHandler != null || defaultPermission) && ButtonLock.getButtonLockConfig().usePermissions){
			return true;
		}
		return false;
	}
	
	public static boolean hasPermission(Player player, String permission){
		
		if (ButtonLock.permissionHandler != null){
			if (ButtonLock.permissionHandler.permission(player, permission)){
				return true;
			}
		}
		
		if (player.hasPermission(permission)){
			return true;
		}
		
		return false;
	}
	
    private void setupPermissions() {
	      Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	      Plugin permissionsBukkitPlugin = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");

	      if (ButtonLock.permissionHandler == null) {
	          if (permissionsPlugin != null) {
	        	  ButtonLock.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	        	  log.info(consoleOutputHeader + " Permission system detected: " + permissionsPlugin.getDescription().getFullName());
	          } else if (ButtonLock.getButtonLockConfig().usePermissions){
	        	  defaultPermission = true;
	        	  
	        	  String pluginName = "";
	        	  
	        	  if (permissionsBukkitPlugin != null){
	        		  pluginName = ": " + permissionsBukkitPlugin.getDescription().getFullName();
	        	  }
	        	  
	        	  log.info(consoleOutputHeader + " Permission system detected" + pluginName);
	          } else {
	        	  log.warning(consoleOutputHeader + " Permission system NOT detected! (everyone will have permissions to use it.)");
	          }
	      }
	  }
    
	/** remove PlayerVars from the current Player, to keep
	 *  the ArrayList short.
	 * 
	 * @param player
	 */
	public static void removePlayer(Player player) {
		PlayerVars playerVars = getPlayerVars(player);
		if (playerVars != null) playerlist.remove(playerVars);
	}

	/** returns the PlayerVars for the given player
	 *  or create it.
	 *   
	 * @param player
	 * @return
	 */
	public static PlayerVars getPlayerVars(Player player) {
		PlayerVars playerVars = null;
		
		for (int playerIndex = 0; playerIndex < playerlist.size(); playerIndex++) {
    		if (playerlist.get(playerIndex).getPlayer().equals(player)) {
    			playerVars = playerlist.get(playerIndex);
    			break;
    		}
    	}
    	
		if (playerVars == null){
			//create PlayerVars for the current Player.
			playerVars = new PlayerVars(player);
			playerlist.add(playerVars);
		}
		
		return playerVars;
	}
    
    public static void addLockedGroup(LockedBlockGroup group) {
    	//only add a group if it contains a locked block
    	if (group.getGroupSize() > 0) {
        	grouplist.add(group);
		}
    }
	
	public static void removeLockedBlock(LockedBlockGroup block) {
		if(block != null) grouplist.remove(block);
	}
    
    /** returns the Group for the given block
	 *   
	 * @param player
	 * @return
	 */
	public static LockedBlockGroup getLockedGroup(Block block) {	
		for (int buttonIndex = 0; buttonIndex < grouplist.size(); buttonIndex++) {
    		LockedBlockGroup group = grouplist.get(buttonIndex);

			for (int groupIndex = 0; groupIndex < group.getGroupSize(); groupIndex++) {
				if (group.getBlock(groupIndex).equals(block)) {	//button found
	    			return group;
				}	
			}
    	}

		return null;
	}
	
	public static boolean byPass(Player player) {
		if(ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions &&
		   ButtonLock.permissionHandler.has(player, ButtonLock.PERMISSION_NODE_ButtonLock_bypass) && configFile.enablePasswordByPass){
			return true;
		}
		return false;
	}
	
	public static boolean iConomyByPass(Player player) {
		if(ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions &&
		   ButtonLock.permissionHandler.has(player, ButtonLock.PERMISSION_NODE_ButtonLock_iconcomy_bypass) && configFile.enableEonomyByPass){
			return true;
		}
		return false;
	}
	
	public static boolean passwordWasEntered(PlayerVars currentPlayerVars, LockedBlockGroup group) {
		if (currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
			for (int enteresPasswordHashIndex = 0; enteresPasswordHashIndex < currentPlayerVars.getEnteredPasswords(); enteresPasswordHashIndex++) {	
			  	if (group.checkPassword(currentPlayerVars, currentPlayerVars.getPassword(enteresPasswordHashIndex))){
//			  		currentPlayerVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
			  		return true;
			  	}
			}
		}
		return false;
	}
	
	public static void debugEvent(Event event) {
		
		String msg = " Name: " + event.getEventName();
		
		try {
			PlayerEvent playerevent = (PlayerEvent) event;
			msg += ChatColor.GREEN + " | " + playerevent.getPlayer().getName();
		} catch (Exception e) {
		}
		
		try {
			BlockEvent blockevent = (BlockEvent) event;
			msg += ChatColor.BLUE + " | " + blockevent.getBlock().getType() + ":" + blockevent.getBlock().getData() +
				    ChatColor.YELLOW + " | {" + blockevent.getBlock().getLocation().getWorld().getName() + ";" + 
					blockevent.getBlock().getLocation().getX() + ";" + blockevent.getBlock().getLocation().getY() + ";" + 
			        blockevent.getBlock().getLocation().getZ() + "}";
		} catch (Exception e) {
		}
		
		try {
			EntityEvent entityevent = (EntityEvent) event;
			msg += ChatColor.AQUA + " | " + entityevent.getEntity().getEntityId() + 
					ChatColor.YELLOW + " | {" + entityevent.getEntity().getLocation().getWorld().getName() + ";" + 
					entityevent.getEntity().getLocation().getX() + ";" + entityevent.getEntity().getLocation().getY() + ";" + 
				    entityevent.getEntity().getLocation().getZ() + "}";
		} catch (Exception e) {
		}
		
		if (ButtonLock.debugmode) {
			ButtonLock.server.broadcastMessage(ChatColor.GOLD + ButtonLock.consoleOutputHeader + ChatColor.GRAY + msg);
			ButtonLock.log.info(ButtonLock.consoleOutputHeader + msg);
		}	
	}
}
