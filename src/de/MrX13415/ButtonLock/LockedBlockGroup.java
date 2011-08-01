package de.MrX13415.ButtonLock;

import java.util.ArrayList;
import org.bukkit.block.Block;


public class LockedBlockGroup{
	
	private ArrayList<Block> lockedBlocks = new ArrayList<Block>();
	
	private int password = 0;	//contains only the Hash of the password ...
	private boolean unlocked = true;
	
	public void setPassword(int passwordHashCode){
		this.password = passwordHashCode;
	}
	
	public int getPassword(){
		return password;
	}
	
	public static void checkPassword(PlayerVars tmpVars, int passwordHashCode){
		//check if the password was correct ...
		if (passwordHashCode == tmpVars.getCurrentClickedLockedButton().getPassword()) {
			tmpVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
			tmpVars.getCurrentClickedLockedButton().setUnlock(true);
		
		}else{
			tmpVars.getPlayer().sendMessage(Language.TEXT_DENIED);
			tmpVars.getCurrentClickedLockedButton().setUnlock(false);
		}
	}
	
	public void setUnlock(boolean unlock){
		this.unlocked = unlock;
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
	
//	public Block getBlockPart1(){
//		return blockPart1;
//	}
//	
//	public Block getBlockPart2(){
//		return blockPart2;
//	}
	
	public void addBlock(Block block){
		lockedBlocks.add(block);
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


}
