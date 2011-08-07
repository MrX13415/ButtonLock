package de.MrX13415.ButtonLock;

import java.util.ArrayList;
import org.bukkit.block.Block;


public class LockedBlockGroup{
	
	private ArrayList<Block> lockedBlocks = new ArrayList<Block>();
	
	private int password = 0;	//contains only the Hash of the password ...
	private ArrayList<Integer> singleUseCods = new ArrayList<Integer>();
	
	private boolean unlocked = true;

	public void setPassword(int passwordHashCode){
		this.password = passwordHashCode;
	}
	
	public int getPassword(){
		return password;
	}
	
	public static void checkPassword(PlayerVars tmpVars, int passwordHashCode){
		//check if the password was correct ...
		LockedBlockGroup group = tmpVars.getCurrentClickedLockedGroup();
		if (group != null) {
			Boolean isSingleUseCode = group.checkSingleUseCode(passwordHashCode);
			
			if (passwordHashCode == group.getPassword() || isSingleUseCode) {
				tmpVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(true);
				
				if (isSingleUseCode) {
					group.singleUseCods.remove((Object) passwordHashCode);
					tmpVars.getPlayer().sendMessage(Language.TEXT_SINGEL_USE_CODE_UESED);
				}
			}else{
				tmpVars.getPlayer().sendMessage(Language.TEXT_DENIED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(false);
			}
		}
	}
	
	public void setUnlock(boolean unlock){
		this.unlocked = unlock;
	}
	
	public boolean isUnlocked(){
		return unlocked;
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
