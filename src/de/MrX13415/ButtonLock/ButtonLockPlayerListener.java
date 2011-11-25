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
		ButtonLock.debugEvent(event);
		
    	if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
    		Player player = event.getPlayer();
    		Block block = event.getClickedBlock();
    	
			//clicked block is a protectable block ...
			if (ButtonLock.isProtectable(block) || ButtonLock.isProtected(block)) {
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
				currentPlayerVars.setCurrentClickedBlock(block);
				currentPlayerVars.setCurrentClickedLockedGroup(null);
				
				
			}
    	}
    }	
	
	
	private void addBlockToLastGroup(PlayerVars currentPlayerVars, Block block) {
		if (currentPlayerVars != null) {

			if (currentPlayerVars.addNextclickedBlock != null) {
				currentPlayerVars.getPlayer().sendMessage(ButtonLock.language.GROUP_BLOCK_ADDED);
				currentPlayerVars.addNextclickedBlock.addBlock(block);
				currentPlayerVars.addNextclickedBlock.setUnlock(false);
				currentPlayerVars.addNextclickedBlock = null;
			}

			if (currentPlayerVars.removeNextclickedBlock != null) {
				currentPlayerVars.getPlayer().sendMessage(ButtonLock.language.GROUP_BLOCK_REMOVED);
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
			
			currentPlayerVars.setCurrentClickedBlock(block);
			currentPlayerVars.setCurrentClickedLockedGroup(group);
			
			Boolean atLockedState = true;			
			if (group.getLockedState() != LOCKED_STATE.BOTH && group.getLockedState() != null) {
				if(BlockFunctions.getBlockState(block) != group.getLockedState()){
					group.setUnlock(true);
					atLockedState = false;
				}
			}
			
			if(ButtonLock.byPass(player)){
				player.sendMessage(ButtonLock.language.PW_BYPASS);
				group.setUnlock(true);
			}
			
			if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) {
				if (atLockedState) {
					group.setUnlock(true);
					player.sendMessage(ButtonLock.language.SUCCEED);
					player.sendMessage(String.format(ButtonLock.language.PROTECTION_MODE_IS, ButtonLock.language.PUBLIC));
					player.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));

					//iconomy
					iconomy(event);
				}

			}else if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				if (group.isOwner(player.getName())) {
					if (atLockedState) {
						group.setUnlock(true);
						player.sendMessage(ButtonLock.language.SUCCEED);
						player.sendMessage(String.format(ButtonLock.language.PROTECTION_MODE_IS, ButtonLock.language.PRIVATE));
						player.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));
						
						//iconomy
						iconomy(event);
					}
				}else{
					if (atLockedState) {
						group.setUnlock(false);
						player.sendMessage(ButtonLock.language.DENIED);
						player.sendMessage(String.format(ButtonLock.language.PROTECTION_MODE_IS, ButtonLock.language.PRIVATE));
						player.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));
						event.setCancelled(true);
					}
				}
				
			}else if (group.getProtectionMode() == PROTECTION_MODE.PASSWORD) {
				
				if (currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
	
					if (ButtonLock.passwordWasEntered(currentPlayerVars, group)) currentPlayerVars.getPlayer().sendMessage(ButtonLock.language.SUCCEED);
					
					if (! group.hasAccess(player)) {
						event.setCancelled(true);	
					}
				}
					
				if (currentPlayerVars.getLastPassword() != null) {
					if (! group.hasAccess(player)) {

						player.sendMessage(String.format(ButtonLock.language.CODE, ButtonLock.language.getMaskedText(currentPlayerVars.getLastPassword())));
						
						group.checkPasswordAndPrintResault(currentPlayerVars, currentPlayerVars.getLastPassword().hashCode());
						
	//					if (group.hasAccess(player)) currentPlayerVars.addPasssword(currentPlayerVars.getLastPassword().hashCode());
						
						currentPlayerVars.setLastPassword(null);
					}
					
					if (! group.hasAccess(player)) event.setCancelled(true);	
				}else {
					//button is locked
					if (! group.hasAccess(player)) {
						//inform player to enter a code ...
						player.sendMessage(ButtonLock.language.DENIED);
						
						if (ButtonLock.configFile.useChatforPasswordInput) {
							player.sendMessage(String.format(ButtonLock.language.ENTER_CODE_CHAT, (double) ((double) ButtonLock.configFile.timeforEnteringPassword/(double) 1000)) );
						}else{
							player.sendMessage(ButtonLock.language.ENTER_CODE_COMMAND);
						}
						
						currentPlayerVars.setEnteringCode(true);					//go into entering password mode
						
						event.setCancelled(true);						//cancel event because the button is locked ...
					}	
				}
				
				if (group.hasAccess(player)) {
					
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
    	ButtonLock.debugEvent(event);
		
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
				player.sendMessage(String.format(ButtonLock.language.CODE, ButtonLock.language.getMaskedText(enteredCode)));
				
				//check if the password was correct ...
				LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
				if (group != null) group.checkPasswordAndPrintResault(currentPlayerVars, enteredCode.hashCode());
				
				//leave password entering mode.
				currentPlayerVars.setEnteringCode(false);	
			}
		}
    } 
    
    public void onPlayerQuit(PlayerQuitEvent event) {
    	ButtonLock.debugEvent(event);
		
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
				currentPlayerVars.setCurrentClickedLockedGroup(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(ButtonLock.language.ICONOMY_BYPASS);
				
			}else{
				if (iConomy.hasAccount(player.getName())){
				
					Account account = iConomy.getAccount(player.getName());
					if(account != null){ // check if the account is valid
						Holdings balance = account.getHoldings();
						
						Double costs = group.costs;

						
						if (balance.hasEnough(costs)) {
							balance.subtract(costs);
							if (costs > 0) {
								player.sendMessage(String.format(ButtonLock.language.ICONOMY_MONY_SUBTRACTED, costs));
							}else if (! ButtonLock.configFile.iConomyIsFreeAsDefault) {
								player.sendMessage(ButtonLock.language.ICONOMY_MONY_SUBTRACTED_FREE);
							}
						}else {
							//not enough mony ...
							player.sendMessage(ButtonLock.language.DENIED);
							player.sendMessage(String.format(ButtonLock.language.ICONOMY_LESS_MONY, costs));
							event.setCancelled(true);
						}
					}else{
						//acc is not valid ...
						player.sendMessage(ButtonLock.language.DENIED);
						player.sendMessage(ButtonLock.language.ICONOMY_NOT_VALID_ACC);
						event.setCancelled(true);
					}
				}else{
					//no acc ...
					player.sendMessage(ButtonLock.language.DENIED);
					player.sendMessage(ButtonLock.language.ICONOMY_NO_ACC);
					event.setCancelled(true);
				}
			}
		}
	}
}




