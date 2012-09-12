package de.MrX13415.ButtonLock.Config;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.ButtonLock;


public class LockedBlockGroup{
	
	public enum PROTECTION_MODE{
		PASSWORD, PRIVATE, PUBLIC; 
	}
	
	public enum LOCKED_STATE{
		OPEN, CLOSE, ON, OFF, BOTH;  
	}
	
	private ArrayList<Block> lockedBlocks = new ArrayList<Block>();

	private int password = 0;	//contains only the Hash of the password ...
	private ArrayList<Integer> oneTimePassword = new ArrayList<Integer>();
	
	private boolean unlocked = true;
	private boolean blockEventsUnlocked = true;
	private LOCKED_STATE lockedState = null;	//default set in constructor...

	private boolean forceEnterPasswordEveryTime = ButtonLock.getButtonLockConfig().forcePasswordEveryTimeByDefault; 
	
	private PROTECTION_MODE protectionMode = PROTECTION_MODE.PASSWORD;
	private ArrayList<String> ownerList = new ArrayList<String>();
	
	public double costs = ButtonLock.getButtonLockConfig().economyCosts;;	//Free :D
	public boolean changedSetting_forceEnterPasswordEveryTime = false;
	public boolean ChangedSetting_costs = false;

	public LockedBlockGroup() {
		if (ButtonLock.getButtonLockConfig().economyIsFreeAsDefault) {
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
	
	public boolean checkPassword(PlayerVars tmpVars, int passwordHashCode){
		boolean returnvar = false;
		//check if the password was correct ...
		Boolean isOneTimePassword = this.checkOneTimePassword(passwordHashCode);
		
		if (passwordHashCode == this.getPassword() || isOneTimePassword) {
			this.setUnlock(true);
			returnvar = true;
			
			if (isOneTimePassword) {
				this.oneTimePassword.remove((Object) passwordHashCode);
				tmpVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().ONE_TIME_CODE_UESED);
			}else{
				tmpVars.addPassword(passwordHashCode);
			}
		}else{
			this.setUnlock(false);
		}

		return returnvar;
	}
	
	public void checkPasswordAndPrintResault(PlayerVars tmpVars, int passwordHashCode){
		boolean resault = checkPassword(tmpVars, passwordHashCode);
		if (resault) {
			tmpVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().SUCCEED);
		}else{
			tmpVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().DENIED);
		}
	}
	
	public void setUnlock(boolean unlock){
		this.unlocked = unlock;
		if (unlock) setBlockEventsUnlocked(true);
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
	
	public boolean isBlockEventsUnlocked() {
		return blockEventsUnlocked;
	}

	public void setBlockEventsUnlocked(boolean eventsUnlocked) {
		this.blockEventsUnlocked = eventsUnlocked;
	}

	public void BlockEventsAutolock() {
		BockEventAutoLock beal = new BockEventAutoLock();
		beal.setGroup(this);
		new Thread(beal).start();
	}
	
	private static class BockEventAutoLock implements Runnable{
		
		private LockedBlockGroup group;
		private long timeout = 1000; //ms
		
		public void run(){
			try{Thread.sleep(timeout);}catch (Exception e) {}
			group.setBlockEventsUnlocked(false);
		}

		public void setGroup(LockedBlockGroup group) {
			this.group = group;
		}
	}
	
	public boolean hasAccess(Player player){
		if ((getProtectionMode() == PROTECTION_MODE.PASSWORD && isUnlocked()) ||
		    (getProtectionMode() == PROTECTION_MODE.PUBLIC && isOwner(player.getName())) ||
			(getProtectionMode() == PROTECTION_MODE.PRIVATE && isOwner(player.getName())) ||
			(ButtonLock.passwordWasEntered(ButtonLock.getPlayerVars(player), this))){
			this.setUnlock(true);
			return true;
		}
		return false;
	}
	
	public void addOneTimePassword(int code){
		oneTimePassword.add(code);
	}
	
	public boolean removeOneTimePassword(int code){
		if (oneTimePassword.contains(code)) {
			oneTimePassword.remove((Object) code);
			return true;
		}
		return false;
	}
	
	public void clearOneTimePasswords(){
		oneTimePassword.clear();
	}
	
	public int getOneTimePassword(int index) {
		return oneTimePassword.get(index);
	}
	
	public int getOneTimePasswords() {
		return oneTimePassword.size();
	}
	
	private boolean checkOneTimePassword(int code) {
		for (int currentCode : oneTimePassword) {
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
				lockedState = ButtonLock.getButtonLockConfig().defaultLockedStatesForDoors;
			}
			
			//fence gate ...
			if (firstBlock.getType().equals(Material.FENCE_GATE)) {
				lockedState = ButtonLock.getButtonLockConfig().defaultLockedStatesForDoors;
			}
			
			//levers ...
			if (firstBlock.getType().equals(Material.LEVER)) {
				lockedState = ButtonLock.getButtonLockConfig().defaultLockedStatesForLevers;
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
		for (Player onlinePlayer : ButtonLock.getCurrentServer().getOnlinePlayers()) {
			String onlinePlayerName = onlinePlayer.getName().toLowerCase();
			
			if (onlinePlayerName.startsWith(player.toLowerCase())){
				if (! ownerList.contains(onlinePlayerName)){
					ownerList.add(onlinePlayerName);
					return onlinePlayerName;
				}
			}
		}
		
		if (ButtonLock.getButtonLockConfig().offlinePlayersAreAddable) {
			OfflinePlayer offlinePlayer = ButtonLock.getCurrentServer().getOfflinePlayer(player);
			if (offlinePlayer != null){
				if (! ownerList.contains(offlinePlayer.getName())){
					ownerList.add(offlinePlayer.getName());
					return offlinePlayer.getName();
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
		for (Player onlinePlayer : ButtonLock.getCurrentServer().getOnlinePlayers()) {
			String onlinePlayerName = onlinePlayer.getName().toLowerCase();
			
			if (onlinePlayerName.startsWith(player.toLowerCase())){
				if (ownerList.contains(onlinePlayerName)){
					ownerList.remove(onlinePlayerName);
					return onlinePlayerName;
				}
			}
		}

		if (ButtonLock.getButtonLockConfig().offlinePlayersAreAddable) {
			OfflinePlayer offlinePlayer = ButtonLock.getCurrentServer().getOfflinePlayer(player);
			if (offlinePlayer != null){
				if (! ownerList.contains(offlinePlayer.getName())){
					ownerList.remove(offlinePlayer.getName());
					return offlinePlayer.getName();
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
