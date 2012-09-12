package de.MrX13415.ButtonLock.Config;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class PlayerVars{

	private Player player;

	private LockedBlockGroup currentClickedLockedButton;
	private Block currentClickedBlock;
	
	private boolean isEnteringCode = false;
	private long timeSinceEnteringCode = 0;
	private String lastPassword = null;
	
	public LockedBlockGroup groupToAddBlocks = null;
	public LockedBlockGroup groupToRemoveBlocks = null;
	
	private ArrayList<Integer> enteredPasswords = new ArrayList<Integer>();
		
	public PlayerVars(Player player){
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public LockedBlockGroup getCurrentClickedLockedGroup() {
		return currentClickedLockedButton;
	}
	
	public void setCurrentClickedLockedGroup(LockedBlockGroup button) {
		currentClickedLockedButton = button;
	}
	
	public Block getCurrentClickedBlock() {
		return currentClickedBlock;
	}
	
	public void setCurrentClickedBlock(Block block) {
		currentClickedBlock = block;
	}
	
	public boolean isEnteringCode() {
		return isEnteringCode;
	}
	
	public void setLastPassword(String password) {
		lastPassword = password;
	}
	
	public String getLastPassword() {
		return lastPassword;
	}
	
	public void setEnteringCode(boolean isEnteringCode) {
		timeSinceEnteringCode = System.currentTimeMillis();
		this.isEnteringCode = isEnteringCode;
	}
	
	public long getTimeSinceEnteringCode() {
		return timeSinceEnteringCode;
	}
	
	
	public void addPassword(int passwordHash) {
		if (!enteredPasswords.contains(passwordHash)) enteredPasswords.add(passwordHash);
	}
	
	public void removePassword(int passwordHash) {
		enteredPasswords.remove((Object) passwordHash);
	}
	
	public int getPassword(int index) {
		return enteredPasswords.get(index);
	}
	
	public int getEnteredPasswords() {
		return enteredPasswords.size();
	}
	
	public void clearEnteredPasswords() {
		enteredPasswords.clear();
	}
		
}
