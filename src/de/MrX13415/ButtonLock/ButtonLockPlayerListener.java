package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;


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
				currentPlayerVars.getPlayer().sendMessage(Language.TEXT_GROUP_BLOCK_ADDED);
				currentPlayerVars.addNextclickedBlock.addBlock(block);
				currentPlayerVars.addNextclickedBlock.setUnlock(false);
				currentPlayerVars.addNextclickedBlock = null;
			}

			if (currentPlayerVars.removeNextclickedBlock != null) {
				currentPlayerVars.getPlayer().sendMessage(Language.TEXT_GROUP_BLOCK_REMOVED);
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
		
			if (currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				
				if (ButtonLock.passwordWasEntered(currentPlayerVars, group)) currentPlayerVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);

				if (! group.isUnlocked()) {
					event.setCancelled(true);	
				}
			}
				
			if(ButtonLock.byPass(player)){
				currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(Language.TEXT_PW_BYPASS);
				group.setUnlock(true);
				
			}else if (currentPlayerVars.getLastPassword() != null) {
				if (! group.isUnlocked()) {
					currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
					currentPlayerVars.setCurrentClickedBlock(block);
					
					player.sendMessage(Language.TEXT_CODE + Language.getMaskedText(currentPlayerVars.getLastPassword()));
					
					LockedBlockGroup.checkPasswordAndPrintResault(currentPlayerVars, currentPlayerVars.getLastPassword().hashCode());
					
//					if (group.isUnlocked()) currentPlayerVars.addPasssword(currentPlayerVars.getLastPassword().hashCode());
					
					currentPlayerVars.setLastPassword(null);
				}
				
				if (! group.isUnlocked()) event.setCancelled(true);	
			}else {
				//button is locked
				if (! group.isUnlocked()) {
					//inform player to enter a code ...
					player.sendMessage(Language.TEXT_DENIED);
					
					if (ButtonLock.configFile.useChatforPasswordInput) {
						player.sendMessage(Language.TEXT_ENTER_CODE_CHAT);
					}else {
						player.sendMessage(Language.TEXT_ENTER_CODE_COMMAND);
					}
					
					currentPlayerVars.setEnteringCode(true);					//go into entering password mode
					currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
					currentPlayerVars.setCurrentClickedBlock(block);
					
					event.setCancelled(true);						//cancel event because the button is locked ...
				}	
			}
			
			if (group.isUnlocked()) {
				
				//iconom
				if (ButtonLock.configFile.useIConomy && ! event.isCancelled()) {
					
					if (ButtonLock.iConomyByPass(player)) {
						currentPlayerVars.setCurrentClickedLockedButton(group);	//set current button 
						currentPlayerVars.setCurrentClickedBlock(block);
						player.sendMessage(Language.TEXT_ICONOMY_BYPASS);
						
					}else{
						if (iConomy.hasAccount(player.getName())){
							
							Account account = iConomy.getAccount(player.getName());
							if(account != null){ // check if the account is valid
								Holdings balance = account.getHoldings();
								
								Double costs = group.costs;
								
								if (balance.hasEnough(costs)) {
									balance.subtract(costs);
									if (costs > 0) {
										player.sendMessage(Language.TEXT_ICONOMY_MONY_SUBTRACTED + costs);
									}else{
										player.sendMessage(Language.TEXT_ICONOMY_MONY_SUBTRACTED_FREE);
									}
								}else {
									//not enough mony ...
									player.sendMessage(Language.TEXT_DENIED);
									player.sendMessage(Language.TEXT_ICONOMY_LESS_MONY + costs);
									event.setCancelled(true);
								}
							}else{
								//acc is not valid ...
								player.sendMessage(Language.TEXT_DENIED);
								player.sendMessage(Language.TEXT_ICONOMY_NOT_VALID_ACC);
								event.setCancelled(true);
							}
						}else{
							//no acc ...
							player.sendMessage(Language.TEXT_DENIED);
							player.sendMessage(Language.TEXT_ICONOMY_NO_ACC);
							event.setCancelled(true);
						}
					}
				}
				
	
				//----------------------------------------------------
				//button, etc. was successful pressed or similar ...
				//-----------------------------------------------------
				
//				currentPlayerVars.setCurrentClickedLockedButton(null);	//reset
//				currentPlayerVars.setCurrentClickedBlock(null);
				group.setUnlock(false);
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
				player.sendMessage(Language.TEXT_CODE + Language.getMaskedText(enteredCode));
				
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
    
}



