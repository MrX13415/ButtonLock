package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class PlayerVars{

	private Player player;

	private Button currentClickedLockedButton;
	private Block currentClickedBlock;
	
	private boolean isEnteringCode = false;
//	private String enteredCode = "";
	
	public PlayerVars(Player player){
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Button getCurrentClickedLockedButton() {
		return currentClickedLockedButton;
	}
	
	public void setCurrentClickedLockedButton(Button button) {
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
	
	public void setEnteringCode(boolean isEnteringCode) {
		this.isEnteringCode = isEnteringCode;
	}
	
}
