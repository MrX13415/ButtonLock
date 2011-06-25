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


public class ButtonLock extends JavaPlugin {
	
	static Server server = null;
	static Logger log = null;
	static String pluginName = null;
	static String consoleOutputHeader = null;
//	static Config configFile = null;
	static LockedBlocks lockedBlocksFile = null;
	
	//permissions
	static PermissionHandler permissionHandler;
//	static final String PERMISSION_NODE_Massband_use = "Massband.use";
	
	//holds information for all Players.
	public static ArrayList<PlayerVars> playerlist = new ArrayList<PlayerVars>();
	public static ArrayList<Button> buttonlist = new ArrayList<Button>();
	
	private final ButtonLockPlayerListener pListener = new ButtonLockPlayerListener();
	private final ButtonLockBlockListener bListener = new ButtonLockBlockListener();
//	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	
	
	@Override
	public void onDisable() {
        lockedBlocksFile = new LockedBlocks();
        lockedBlocksFile.write();
        log.info(consoleOutputHeader + " locked Blocks saved");
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
  
//        configFile = new Config();
//        configFile.read();
        //---------------------
                
        //register events ...
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_PLACE, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, bListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
		
		//register commands ...
		try {
			this.getCommand("setPassword").setExecutor(new SetPasswordCommandExecuter());
			this.getCommand("Password").setExecutor(new PasswordCommandExecuter());
		} catch (Exception e) {
			log.warning("[" + pdfFile.getName() + "] Error: Commands not definated in 'plugin.yaml'");
		}
				
		setupPermissions();
		
		//load locked Buttons ...
        lockedBlocksFile = new LockedBlocks();
        lockedBlocksFile.read();
		
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
			if ((block.getType() == Material.STONE_BUTTON) ||
				(block.getType() == Material.LEVER)) return true;
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
    
    public static void addButton(Button button) {
    	buttonlist.add(button);
	}
	
	public static void removeButton(Button button) {
		if(button != null) buttonlist.remove(button);
	}
    
    /** returns the Button for the given block
	 *   
	 * @param player
	 * @return
	 */
	public static Button getButton(Block block) {
		Button tmpButton = null;
		
		for (int buttonIndex = 0; buttonIndex < buttonlist.size(); buttonIndex++) {
    		if (buttonlist.get(buttonIndex).getBlock().equals(block)) {	//button found
    			tmpButton = buttonlist.get(buttonIndex);
    			break;
			}
    	}

		return tmpButton;
	}

	public static void checkPassword(PlayerVars tmpVars, String code){
		//check if the password was correct ...
		if (code.equals(tmpVars.getCurrentClickedLockedButton().getPassword())) {
			tmpVars.getPlayer().sendMessage(Language.TEXT_SUCCESS);
			tmpVars.getCurrentClickedLockedButton().setUnlock(true);
		
		}else{
			tmpVars.getPlayer().sendMessage(Language.TEXT_DENIED);
			tmpVars.getCurrentClickedLockedButton().setUnlock(false);
		}
	}
	
}
