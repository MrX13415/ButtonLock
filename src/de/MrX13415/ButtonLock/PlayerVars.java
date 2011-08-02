package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class PlayerVars{

	private Player player;

	private LockedBlockGroup currentClickedLockedButton;
	private Block currentClickedBlock;
	
	private boolean isEnteringCode = false;
	private long timeSinceEnteringCode = 0;
	private String lastPassword = null;
	
	public PlayerVars(Player player){
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public LockedBlockGroup getCurrentClickedLockedGroup() {
		return currentClickedLockedButton;
	}
	
	public void setCurrentClickedLockedButton(LockedBlockGroup button) {
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
	
}
