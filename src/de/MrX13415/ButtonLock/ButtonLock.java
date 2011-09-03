package de.MrX13415.ButtonLock;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.iConomy.*;

/** ButtonLock for Bukkit
 * 
 * @author Oliver Daus
 * @version 0.8 r21
 */
public class ButtonLock extends JavaPlugin {
	
	static Server server = null;
	static Logger log = null;
	static String pluginName = null;
	static String consoleOutputHeader = null;
	static Config configFile = null;
	static LockedGroupsConfig lockedGroupsFile = null;
	
	//iconomy
	public static iConomy iConomy = null;
	
	//permissions
	static PermissionHandler permissionHandler;
	static final String PERMISSION_NODE_ButtonLock_bypass = "ButtonLock.bypass";
	static final String PERMISSION_NODE_ButtonLock_iconcomy_bypass = "ButtonLock.iconomy.bypass";
	static final String PERMISSION_NODE_ButtonLock_use = "ButtonLock.use";
	static final String PERMISSION_NODE_ButtonLock_setpw = "ButtonLock.setpw";
	static final String PERMISSION_NODE_ButtonLock_singleUseCods = "ButtonLock.singleusecods";
	static final String PERMISSION_NODE_ButtonLock_buttonlock = "ButtonLock.buttonlock";
	
	//holds information for all Players.
	public static ArrayList<PlayerVars> playerlist = new ArrayList<PlayerVars>();
	public static ArrayList<LockedBlockGroup> grouplist = new ArrayList<LockedBlockGroup>();
	public static ArrayList<Material> lockableBlocksList = new ArrayList<Material>();
	
	private final ButtonLockPlayerListener pListener = new ButtonLockPlayerListener();
	private final ButtonLockBlockListener bListener = new ButtonLockBlockListener();
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
		
		PluginDescriptionFile pdfFile = this.getDescription();
		pluginName = pdfFile.getName();
		consoleOutputHeader = "[" + pluginName + "]";

        log.info(consoleOutputHeader + " v" + pdfFile.getVersion() + " " + pdfFile.getAuthors() + " is enabled.");
  
        //load config ...
        configFile = new Config();
        configFile.read();
        
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
		pm.registerEvent(Event.Type.BLOCK_PISTON_RETRACT, bListener, Priority.Normal, this);
		
		pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
		
		//register commands ...
		try {
			this.getCommand("setpassword").setExecutor(new SetPasswordCommandExecuter());
			this.getCommand("password").setExecutor(new PasswordCommandExecuter());
			this.getCommand("singleusepassword").setExecutor(new SingleUsePasswordCommandExecuter());
			this.getCommand("buttonlock").setExecutor(new ButtonLockCommandExecutor());
			
		} catch (Exception e) {
			log.warning("[" + pdfFile.getName() + "] Error: Commands not definated in 'plugin.yaml'");
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
	
    private void setupPermissions() {
	      Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

	      if (ButtonLock.permissionHandler == null) {
	          if (permissionsPlugin != null) {
	        	  ButtonLock.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	        	  log.info(consoleOutputHeader + " Permission system detected: " + permissionsPlugin.getDescription().getFullName());
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
		   ButtonLock.permissionHandler.has(player, ButtonLock.PERMISSION_NODE_ButtonLock_iconcomy_bypass) && configFile.enableIConomyByPass){
			return true;
		}
		return false;
	}
	
	public static boolean passwordWasEntered(PlayerVars currentPlayerVars, LockedBlockGroup group) {
		if (! group.isUnlocked() && currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
			for (int enteresPasswordHashIndex = 0; enteresPasswordHashIndex < currentPlayerVars.getEnteredPasswords(); enteresPasswordHashIndex++) {	
			  	if (LockedBlockGroup.checkPassword(currentPlayerVars, currentPlayerVars.getPassword(enteresPasswordHashIndex))){
//			  		currentPlayerVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
			  		return true;
			  	}
			}
		}
		return false;
	}
}
