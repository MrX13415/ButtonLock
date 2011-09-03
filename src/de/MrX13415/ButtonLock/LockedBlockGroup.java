package de.MrX13415.ButtonLock;

import java.util.ArrayList;
import org.bukkit.block.Block;


public class LockedBlockGroup{
	
	private ArrayList<Block> lockedBlocks = new ArrayList<Block>();
	
	private int password = 0;	//contains only the Hash of the password ...
	private ArrayList<Integer> singleUseCods = new ArrayList<Integer>();
	
	private boolean unlocked = true;
	private boolean forceEnterPasswordEveryTime = ButtonLock.configFile.forcePasswordEveryTimeByDefault; 
	public double costs = ButtonLock.configFile.iConomyCosts;
	public boolean changedSetting_forceEnterPasswordEveryTime = false;
	public boolean ChangedSetting_costs = false;
	
	public void setPassword(int passwordHashCode){
		this.password = passwordHashCode;
	}
	
	public int getPassword(){
		return password;
	}
	
	public static boolean checkPassword(PlayerVars tmpVars, int passwordHashCode){
		boolean returnvar = false;
		//check if the password was correct ...
		LockedBlockGroup group = tmpVars.getCurrentClickedLockedGroup();
		if (group != null) {
			Boolean isSingleUseCode = group.checkSingleUseCode(passwordHashCode);
			
			if (passwordHashCode == group.getPassword() || isSingleUseCode) {
//				tmpVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(true);
				returnvar = true;
				
				if (isSingleUseCode) {
					group.singleUseCods.remove((Object) passwordHashCode);
					tmpVars.getPlayer().sendMessage(Language.TEXT_SINGEL_USE_CODE_UESED);
				}else{
					tmpVars.addPassword(passwordHashCode);
				}
			}else{
				tmpVars.removePassword(passwordHashCode);
//				tmpVars.getPlayer().sendMessage(Language.TEXT_DENIED);
				tmpVars.getCurrentClickedLockedGroup().setUnlock(false);
			}
		}
		return returnvar;
	}
	
	public static void checkPasswordAndPrintResault(PlayerVars tmpVars, int passwordHashCode){
		boolean resault = checkPassword(tmpVars, passwordHashCode);
		if (resault) {
			tmpVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
		}else{
			tmpVars.getPlayer().sendMessage(Language.TEXT_DENIED);
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
	
	public boolean isForceingEnterPasswordEveryTime() {
		return forceEnterPasswordEveryTime;
	}

}
