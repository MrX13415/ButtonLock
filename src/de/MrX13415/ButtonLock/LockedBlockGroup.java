package de.MrX13415.ButtonLock;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class LockedBlockGroup{
	
	public enum PROTECTION_MODE{
		PASSWORD, PRIVATE, PUBLIC; 
	}
	
	public enum LOCKED_STATE{
		OPEN, CLOSE, ON, OFF, BOTH;  
	}
	
	private ArrayList<Block> lockedBlocks = new ArrayList<Block>();
	
	private int password = 0;	//contains only the Hash of the password ...
	private ArrayList<Integer> singleUseCods = new ArrayList<Integer>();
	
	private boolean unlocked = true;
	private LOCKED_STATE lockedState = null;	//default set in constructor...

	private boolean forceEnterPasswordEveryTime = ButtonLock.configFile.forcePasswordEveryTimeByDefault; 
	
	private PROTECTION_MODE protectionMode = PROTECTION_MODE.PASSWORD;
	private ArrayList<String> ownerList = new ArrayList<String>();
	
	public double costs = ButtonLock.configFile.iConomyCosts;;	//Free :D
	public boolean changedSetting_forceEnterPasswordEveryTime = false;
	public boolean ChangedSetting_costs = false;

	public LockedBlockGroup() {
		if (ButtonLock.configFile.iConomyIsFreeAsDefault) {
			costs = 0;
		}
	}
	
	public void setPassword(int passwordHashCode){
		this.password = passwordHashCode;
	}
	
	public int getPassword(){
		return password;
	}
	
	public LOCKED_STATE getLockedState() {
		return lockedState;
	}
	
	public void setLockedState(LOCKED_STATE state) {
		lockedState = state;
	}
	
	public static boolean checkPassword(PlayerVars tmpVars, int passwordHashCode){
		boolean returnvar = false;
		//check if the password was correct ...
		LockedBlockGroup group = tmpVars.getCurrentClickedLockedGroup();
		if (group != null) {
			Boolean isSingleUseCode = group.checkSingleUseCode(passwordHashCode);
			
			if (passwordHashCode == group.getPassword() || isSingleUseCode) {
//				tmpVars.getPlayer().sendMessage(Language.SUCCEED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(true);
				returnvar = true;
				
				if (isSingleUseCode) {
					group.singleUseCods.remove((Object) passwordHashCode);
					tmpVars.getPlayer().sendMessage(Language.SINGEL_USE_CODE_UESED);
				}else{
					tmpVars.addPassword(passwordHashCode);
				}
			}else{
				tmpVars.removePassword(passwordHashCode);
//				tmpVars.getPlayer().sendMessage(Language.DENIED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(false);
			}
		}
		return returnvar;
	}
	
	public static void checkPasswordAndPrintResault(PlayerVars tmpVars, int passwordHashCode){
		boolean resault = checkPassword(tmpVars, passwordHashCode);
		if (resault) {
			tmpVars.getPlayer().sendMessage(Language.SUCCEED);
		}else{
			tmpVars.getPlayer().sendMessage(Language.DENIED);
		}
	}
	
	public void setUnlock(boolean unlock){
		this.unlocked = unlock;
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
	
	public boolean hasAccess(Player player){
		if ((getProtectionMode() == PROTECTION_MODE.PASSWORD && isUnlocked()) ||
		    (getProtectionMode() == PROTECTION_MODE.PUBLIC && isOwner(player.getName())) ||
			(getProtectionMode() == PROTECTION_MODE.PRIVATE && isOwner(player.getName())) ||
			(ButtonLock.passwordWasEntered(ButtonLock.getPlayerVars(player), this))){
			return true;
		}
		return false;
	}
	
	public void addSingleUseCode(int code){
		singleUseCods.add(code);
	}
	
	public void removeSingelUseCode(int code){
		singleUseCods.remove(code);
	}
	
	public int getSinglUseCode(int index) {
		return singleUseCods.get(index);
	}
	
	private boolean checkSingleUseCode(int code) {
		for (int currentCode : singleUseCods) {
			if (currentCode == code) return true;
		}
		return false;
	}
	
	public void addBlock(Block block){
		if (! lockedBlocks.contains(block)){
			lockedBlocks.add(block);
			
			Block firstBlock = lockedBlocks.get(0);
			
			//doors and trap doors ...
			if (firstBlock.getType().equals(Material.WOODEN_DOOR) || firstBlock.getType().equals(Material.IRON_DOOR_BLOCK) || firstBlock.getType().equals(Material.TRAP_DOOR)) {
				lockedState = ButtonLock.configFile.defaultLockedStatesForDoors;
			}
			
			//fence gate ...
			if (firstBlock.getType().equals(Material.FENCE_GATE)) {
				lockedState = ButtonLock.configFile.defaultLockedStatesForDoors;
			}
			
			//levers ...
			if (firstBlock.getType().equals(Material.LEVER)) {
				lockedState = ButtonLock.configFile.defaultLockedStatesForLevers;
			}
		}
	}
	
	public boolean containsBlock(Block block){
		return lockedBlocks.contains(block);
	}
	
	public void clearGroup(){
		lockedBlocks.clear();
	}
	
	public int getGroupSize() {
		return lockedBlocks.size();
	}
	
	public Block getBlock(int index) {
 		return lockedBlocks.get(index);
	}
	
	public void removeBlock(Block block) {
		lockedBlocks.remove(block);
	}

	public void setForceEnterPasswordEveryTime(boolean force) {
		forceEnterPasswordEveryTime = force;
	}
	
	public void setProtectionMode(PROTECTION_MODE mode) {
		protectionMode = mode;
	}
	
	public PROTECTION_MODE getProtectionMode() {
		return protectionMode;
	}
	
	public boolean isForceingEnterPasswordEveryTime() {
		return forceEnterPasswordEveryTime;
	}
	
	public String addOwner(String player) {
		for (Player onlinePlayer : ButtonLock.server.getOnlinePlayers()) {
			String onlinePlayerName = onlinePlayer.getName().toLowerCase();
			
			if (onlinePlayerName.startsWith(player.toLowerCase())){
				if (! ownerList.contains(onlinePlayerName)){
					ownerList.add(onlinePlayerName);
					return onlinePlayerName;
				}
			}
		}
		return "";
	}
	
	public String[] getOwnerList() {
		String[] returnlist = new String[ownerList.size()];
		for (int ownerIndex = 0; ownerIndex < ownerList.size(); ownerIndex++) {
			returnlist[ownerIndex] = ownerList.get(ownerIndex);
		}
		return returnlist;
	}
	
	public String getOwner(int index) {
		return ownerList.get(index);
	}
	
	public String removeOwner(String player) {
		for (Player onlinePlayer : ButtonLock.server.getOnlinePlayers()) {
			String onlinePlayerName = onlinePlayer.getName().toLowerCase();
			
			if (onlinePlayerName.startsWith(player.toLowerCase())){
				if (ownerList.contains(onlinePlayerName)){
					ownerList.remove(onlinePlayerName);
					return onlinePlayerName;
				}
			}
		}
		return "";
	}

	public int getOwnerListSize() {
		return ownerList.size();
	}

	public boolean isOwner(String player) {
		return ownerList.contains(player.toLowerCase());
	}
	
}
