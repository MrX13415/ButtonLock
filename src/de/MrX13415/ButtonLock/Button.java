package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;


public class Button{

	private Block block;
	
	private String password = "";
	private boolean unlocked = true;
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUnlock(boolean unlock){
		this.unlocked = unlock;
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
	
	public Block getBlock(){
		return block;
	}
	
	public void setBlock(Block block){
		this.block = block;
	}
		
}
