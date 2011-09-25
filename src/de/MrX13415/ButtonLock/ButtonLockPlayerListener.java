package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import com.iConomy.*;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;
import de.MrX13415.ButtonLock.LockedBlockGroup.LOCKED_STATE;
import de.MrX13415.ButtonLock.LockedBlockGroup.PROTECTION_MODE;


public class ButtonLockPlayerListener extends PlayerListener {
	
	public String toBin(int bin){
		String returnS = "00000000";
		String binS = Integer.toBinaryString(bin); 
		return returnS.substring(0, 7 - (binS.length() - 1)) + binS;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event){
//		try {
//			System.out.println("DATA: " + toBin(event.getClickedBlock().getData()) );
//			System.out.println("MATERIAL: " + event.getClickedBlock().getType());
//		} catch (Exception e) {
//		}

    	if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
    		Player player = event.getPlayer();
    		Block block = event.getClickedBlock();
    	
			//clicked block is a protectable block ...
			if (ButtonLock.isProtectable(block)) {
				//find PlayerVars
				PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
				addBlockToLastGroup(currentPlayerVars, block);
				
	    		if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {					
	    			//use Permission
	    			if (ButtonLock.permissionHandler.permission(player, ButtonLock.PERMISSION_NODE_ButtonLock_use)) {
	        			playerInteract(event);
	    			}else{
	    				event.setCancelled(true);	
	    			}
				}else{
					//no Permission installed ! (op only)
					if (player.isOp() && ButtonLock.configFile.oPOnly) {
						playerInteract(event);
					}else if (! ButtonLock.configFile.oPOnly){
						playerInteract(event);
					}else{
					    event.setCancelled(true);	
					}
				}
			}else{
				//find PlayerVars
				PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
				addBlockToLastGroup(currentPlayerVars, block);
				
			}
    	}
    }	
	
	
	private void addBlockToLastGroup(PlayerVars currentPlayerVars, Block block) {
		if (currentPlayerVars != null) {

			if (currentPlayerVars.addNextclickedBlock != null) {
				currentPlayerVars.getPlayer().sendMessage(Language.GROUP_BLOCK_ADDED);
				currentPlayerVars.addNextclickedBlock.addBlock(block);
				currentPlayerVars.addNextclickedBlock.setUnlock(false);
				currentPlayerVars.addNextclickedBlock = null;
			}

			if (currentPlayerVars.removeNextclickedBlock != null) {
				currentPlayerVars.getPlayer().sendMessage(Language.GROUP_BLOCK_REMOVED);
				currentPlayerVars.removeNextclickedBlock.removeBlock(block);
				currentPlayerVars.removeNextclickedBlock.setUnlock(false);
				currentPlayerVars.removeNextclickedBlock = null;
			}
			
		}
	}
	
	private void playerInteract(PlayerInteractEvent event) {
		//get event-infos ...
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		//find PlayerVars
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		//find Button in the locked-button-list ...
		LockedBlockGroup group = ButtonLock.getLockedGroup(block);
		
		//button was founded ...
		if (group != null) {
			
			Boolean atLockedState = true;			
			if (group.getLockedState() != LOCKED_STATE.BOTH && group.getLockedState() != null) {
				if(BlockFunctions.getBlockState(block) != group.getLockedState()){
					group.setUnlock(true);
					atLockedState = false;
				}
			}
			
			if(ButtonLock.byPass(player)){
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(Language.PW_BYPASS);
				group.setUnlock(true);
			}
			
			if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) {
				if (atLockedState) {
					group.setUnlock(true);
					player.sendMessage(Language.SUCCEED);
					player.sendMessage(Language.PROTECTION_MODE_IS + Language.PUBLIC);
					player.sendMessage(Language.PROTECTION_OWNER_LIST + Language.getList(group.getOwnerList()));

					//iconomy
					iconomy(event);
				}
				
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);


			}else if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				if (group.isOwner(player.getName())) {
					if (atLockedState) {
						group.setUnlock(true);
						player.sendMessage(Language.SUCCEED);
						player.sendMessage(Language.PROTECTION_MODE_IS + Language.PRIVATE);
						player.sendMessage(Language.PROTECTION_OWNER_LIST + Language.getList(group.getOwnerList()));
						
						//iconomy
						iconomy(event);
					}
				}else{
					if (atLockedState) {
						group.setUnlock(false);
						player.sendMessage(Language.DENIED);
						player.sendMessage(Language.PROTECTION_MODE_IS + Language.PRIVATE);
						player.sendMessage(Language.PROTECTION_OWNER_LIST + Language.getList(group.getOwnerList()));
						event.setCancelled(true);
					}
				}
				
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				
			}else if (group.getProtectionMode() == PROTECTION_MODE.PASSWORD) {
				
				if (currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
					currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
					currentPlayerVars.setCurrentClickedBlock(block);
					
					if (ButtonLock.passwordWasEntered(currentPlayerVars, group)) currentPlayerVars.getPlayer().sendMessage(Language.SUCCEED);
					
					if (! group.isUnlocked()) {
						event.setCancelled(true);	
					}
				}
					
				if (currentPlayerVars.getLastPassword() != null) {
					if (! group.isUnlocked()) {
						currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
						currentPlayerVars.setCurrentClickedBlock(block);
						
						player.sendMessage(Language.CODE + Language.getMaskedText(currentPlayerVars.getLastPassword()));
						
						LockedBlockGroup.checkPasswordAndPrintResault(currentPlayerVars, currentPlayerVars.getLastPassword().hashCode());
						
	//					if (group.isUnlocked()) currentPlayerVars.addPasssword(currentPlayerVars.getLastPassword().hashCode());
						
						currentPlayerVars.setLastPassword(null);
					}
					
					if (! group.isUnlocked()) event.setCancelled(true);	
				}else {
					//button is locked
					if (! group.isUnlocked()) {
						//inform player to enter a code ...
						player.sendMessage(Language.DENIED);
						
						if (ButtonLock.configFile.useChatforPasswordInput) {
							player.sendMessage(Language.ENTER_CODE_CHAT);
						}else{
							player.sendMessage(Language.ENTER_CODE_COMMAND);
						}
						
						currentPlayerVars.setEnteringCode(true);					//go into entering password mode
						currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
						currentPlayerVars.setCurrentClickedBlock(block);
						
						event.setCancelled(true);						//cancel event because the button is locked ...
					}	
				}
				
				if (group.isUnlocked()) {
					
					//iconomy
					iconomy(event);
		
					//----------------------------------------------------
					//button, etc. was successful pressed or similar ...
					//-----------------------------------------------------
					
	//				currentPlayerVars.setCurrentClickedLockedButton(null);	//reset
	//				currentPlayerVars.setCurrentClickedBlock(null);
					group.setUnlock(false);
				}
			}
		}else{
			//add a button?
			currentPlayerVars.setCurrentClickedBlock(block);
		}		
	}   

    public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
		
		long timeDifference = ButtonLock.configFile.timeforEnteringPassword; //millis
		long now = System.currentTimeMillis();
		
		if (currentPlayerVars.isEnteringCode() && (now - currentPlayerVars.getTimeSinceEnteringCode()) < timeDifference) {
			if (ButtonLock.configFile.useChatforPasswordInput) {
				//get entered text ...
				String enteredCode = event.getMessage();
				
				//Don't display the entered text int the chat ... 
				event.setCancelled(true);
				
				//display the password to the Player...
				player.sendMessage(Language.CODE + Language.getMaskedText(enteredCode));
				
				//check if the password was correct ...
				LockedBlockGroup.checkPasswordAndPrintResault(currentPlayerVars, enteredCode.hashCode());
				
				//leave password entering mode.
				currentPlayerVars.setEnteringCode(false);	
			}
		}
    } 
    
    public void onPlayerQuit(PlayerQuitEvent event) {
    	ButtonLock.removePlayer(event.getPlayer());
	}

    
    public void iconomy(PlayerInteractEvent event) {
    	//get event-infos ...
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		//find PlayerVars
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		//find Button in the locked-button-list ...
		LockedBlockGroup group = ButtonLock.getLockedGroup(block);
		
    	//iconom
		if (ButtonLock.configFile.useIConomy && ! event.isCancelled()) {
			
			if (ButtonLock.iConomyByPass(player)) {
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(Language.ICONOMY_BYPASS);
				
			}else{
				if (iConomy.hasAccount(player.getName())){
				
					Account account = iConomy.getAccount(player.getName());
					if(account != null){ // check if the account is valid
						Holdings balance = account.getHoldings();
						
						Double costs = group.costs;

						
						if (balance.hasEnough(costs)) {
							balance.subtract(costs);
							if (costs > 0) {
								player.sendMessage(Language.ICONOMY_MONY_SUBTRACTED + costs);
							}else if (! ButtonLock.configFile.iConomyIsFreeAsDefault) {
								player.sendMessage(Language.ICONOMY_MONY_SUBTRACTED_FREE);
							}
						}else {
							//not enough mony ...
							player.sendMessage(Language.DENIED);
							player.sendMessage(Language.ICONOMY_LESS_MONY + costs);
							event.setCancelled(true);
						}
					}else{
						//acc is not valid ...
						player.sendMessage(Language.DENIED);
						player.sendMessage(Language.ICONOMY_NOT_VALID_ACC);
						event.setCancelled(true);
					}
				}else{
					//no acc ...
					player.sendMessage(Language.DENIED);
					player.sendMessage(Language.ICONOMY_NO_ACC);
					event.setCancelled(true);
				}
			}
		}
	}
}




