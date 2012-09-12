package de.MrX13415.ButtonLock.Listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import com.earth2me.essentials.api.*;
import com.iCo6.system.*;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Block.BlockFunctions;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup;
import de.MrX13415.ButtonLock.Config.PlayerVars;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup.LOCKED_STATE;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup.PROTECTION_MODE;


public class ButtonLockPlayerListener implements Listener {
	
	public String toBin(int bin){
		String returnS = "00000000";
		String binS = Integer.toBinaryString(bin); 
		return returnS.substring(0, 7 - (binS.length() - 1)) + binS;
	}
	
	@EventHandler
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
				addOrRemoveBlockToLastGroup(currentPlayerVars, block);
				
	    		if (ButtonLock.permissions()) {					
	    			//use Permission
	    			if (ButtonLock.hasPermission(player, ButtonLock.PERMISSION_NODE_ButtonLock_use)) {
	        			playerInteract(event);
	    			}else{
	    				player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PERMISSIONS_NOT,  ButtonLock.PERMISSION_NODE_ButtonLock_use));
	    				event.setCancelled(true);	
	    			}
				}else{
					//no Permission installed ! (op only)
					if (player.isOp() && ButtonLock.getButtonLockConfig().oPOnly) {
						playerInteract(event);
					}else if (! ButtonLock.getButtonLockConfig().oPOnly){
						playerInteract(event);
					}else{
					    event.setCancelled(true);	
					}
				}
			}else{
				//find PlayerVars
				PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
				addOrRemoveBlockToLastGroup(currentPlayerVars, block);
				currentPlayerVars.setCurrentClickedBlock(block);
				currentPlayerVars.setCurrentClickedLockedGroup(null);
				
				
			}	
    	}
    }	
	
	private void addOrRemoveBlockToLastGroup(PlayerVars currentPlayerVars, Block block) {
		if (currentPlayerVars != null) {

			if (currentPlayerVars.groupToAddBlocks != null) {
				currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().GROUP_BLOCK_ADDED);
				currentPlayerVars.groupToAddBlocks.addBlock(block);
				
				Block partBlock = BlockFunctions.getPartBlock(block);
				if (partBlock != null) {
					currentPlayerVars.groupToAddBlocks.addBlock(partBlock);
				}
				
				Block attachedBlock = BlockFunctions.getAttachedBlock(block);
				if (attachedBlock != null) {
					currentPlayerVars.groupToAddBlocks.addBlock(attachedBlock);								
				}
								
				currentPlayerVars.groupToAddBlocks.setUnlock(false);
				currentPlayerVars.groupToAddBlocks = null;
			}

			if (currentPlayerVars.groupToRemoveBlocks != null) {
				currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().GROUP_BLOCK_REMOVED);
				currentPlayerVars.groupToRemoveBlocks.removeBlock(block);
				
				Block partBlock = BlockFunctions.getPartBlock(block);
				if (partBlock != null) {
					currentPlayerVars.groupToRemoveBlocks.removeBlock(partBlock);								
				}
				
				Block attachedBlock = BlockFunctions.getAttachedBlock(block);
				if (attachedBlock != null) {
					currentPlayerVars.groupToRemoveBlocks.removeBlock(attachedBlock);								
				}
				
				currentPlayerVars.groupToRemoveBlocks.setUnlock(false);
				currentPlayerVars.groupToRemoveBlocks = null;
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
			if (!group.isUnlocked()) group.setBlockEventsUnlocked(false);
			
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
				player.sendMessage(ButtonLock.getCurrentLanguage().PW_BYPASS);
				group.setUnlock(true);
			}
			
			if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) {
				if (atLockedState) {
					group.setUnlock(true);
					player.sendMessage(ButtonLock.getCurrentLanguage().SUCCEED);
					player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_MODE_IS, ButtonLock.getCurrentLanguage().PUBLIC));
					player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_OWNER_LIST, ButtonLock.getCurrentLanguage().getList(group.getOwnerList())));

					//iconomy
					iconomy(event);
				}

			}else if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				if (group.isOwner(player.getName())) {
					if (atLockedState) {
						group.setUnlock(true);
						player.sendMessage(ButtonLock.getCurrentLanguage().SUCCEED);
						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_MODE_IS, ButtonLock.getCurrentLanguage().PRIVATE));
						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_OWNER_LIST, ButtonLock.getCurrentLanguage().getList(group.getOwnerList())));
						
						//iconomy
						iconomy(event);
					}
				}else{
					if (atLockedState) {
						group.setUnlock(false);
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_MODE_IS, ButtonLock.getCurrentLanguage().PRIVATE));
						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().PROTECTION_OWNER_LIST, ButtonLock.getCurrentLanguage().getList(group.getOwnerList())));
						event.setCancelled(true);
					}
				}
				
			}else if (group.getProtectionMode() == PROTECTION_MODE.PASSWORD) {
				
				if (currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
	
					if (ButtonLock.passwordWasEntered(currentPlayerVars, group)) currentPlayerVars.getPlayer().sendMessage(ButtonLock.getCurrentLanguage().SUCCEED);
					
					if (! group.hasAccess(player)) {
						event.setCancelled(true);	
					}
				}
					
				if (currentPlayerVars.getLastPassword() != null) {
					if (! group.hasAccess(player)) {

						player.sendMessage(String.format(ButtonLock.getCurrentLanguage().CODE, ButtonLock.getCurrentLanguage().getMaskedText(currentPlayerVars.getLastPassword())));
						
						group.checkPasswordAndPrintResault(currentPlayerVars, currentPlayerVars.getLastPassword().hashCode());
						
	//					if (group.hasAccess(player)) currentPlayerVars.addPasssword(currentPlayerVars.getLastPassword().hashCode());
						
						currentPlayerVars.setLastPassword(null);
					}
					
					if (! group.hasAccess(player)) event.setCancelled(true);	
				}else {
					//button is locked
					if (! group.hasAccess(player)) {
						//inform player to enter a code ...
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						
						if (ButtonLock.getButtonLockConfig().useChatforPasswordInput) {
							player.sendMessage(String.format(ButtonLock.getCurrentLanguage().ENTER_CODE_CHAT, (double) ((double) ButtonLock.getButtonLockConfig().timeforEnteringPassword/(double) 1000)) );
						}else{
							player.sendMessage(ButtonLock.getCurrentLanguage().ENTER_CODE_COMMAND);
						}
						
						currentPlayerVars.setEnteringCode(true);					//go into entering password mode
						
						event.setCancelled(true);						//cancel event because the button is locked ...
					}	
				}
				
				if (group.hasAccess(player)) {
					
					//economy
					if (ButtonLock.iConomy) iconomy(event);
					if (ButtonLock.essetials) essentialsEco(event);
					
		
					//----------------------------------------------------
					//button, etc. was successful pressed or similar ...
					//-----------------------------------------------------
					
	//				currentPlayerVars.setCurrentClickedLockedButton(null);	//reset
	//				currentPlayerVars.setCurrentClickedBlock(null);
					group.setUnlock(false);
					group.BlockEventsAutolock();
				}
			}
		}else{
			//add a button?
			currentPlayerVars.setCurrentClickedBlock(block);
		}		
	}   

	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
    	ButtonLock.debugEvent(event);
    	    	
		Player player = event.getPlayer();
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
		
		long timeDifference = ButtonLock.getButtonLockConfig().timeforEnteringPassword; //millis
		long now = System.currentTimeMillis();
		
		if (currentPlayerVars.isEnteringCode() && (now - currentPlayerVars.getTimeSinceEnteringCode()) < timeDifference) {
			if (ButtonLock.getButtonLockConfig().useChatforPasswordInput) {
				//get entered text ...
				String enteredCode = event.getMessage();
				
				//Don't display the entered text int the chat ... 
				event.setCancelled(true);
				
				//display the password to the Player...
				player.sendMessage(String.format(ButtonLock.getCurrentLanguage().CODE, ButtonLock.getCurrentLanguage().getMaskedText(enteredCode)));
				
				//check if the password was correct ...
				LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
				if (group != null) group.checkPasswordAndPrintResault(currentPlayerVars, enteredCode.hashCode());
				
				//leave password entering mode.
				currentPlayerVars.setEnteringCode(false);	
			}
		}
    } 
    
	@EventHandler
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
		if (ButtonLock.getButtonLockConfig().useiConomy && ! event.isCancelled()) {
			
			Accounts accounts = new Accounts();

			if (ButtonLock.iConomyByPass(player)) {
				currentPlayerVars.setCurrentClickedLockedGroup(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(ButtonLock.getCurrentLanguage().ECONOMY_BYPASS);
				
			}else{
				if (accounts.exists(player.getName())){
				
					Account account = accounts.get(player.getName());
					if(account != null){ // check if the account is valid
						Holdings balance = account.getHoldings();
						
						Double costs = group.costs;

						
						if (balance.hasEnough(costs)) {
							balance.subtract(costs);
							if (costs > 0) {
								player.sendMessage(String.format(ButtonLock.getCurrentLanguage().ICONOMY_MONY_SUBTRACTED, costs));
							}else if (! ButtonLock.getButtonLockConfig().economyIsFreeAsDefault) {
								player.sendMessage(ButtonLock.getCurrentLanguage().ICONOMY_MONY_SUBTRACTED_FREE);
							}
						}else {
							//not enough mony ...
							player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
							player.sendMessage(String.format(ButtonLock.getCurrentLanguage().ICONOMY_LESS_MONY, costs));
							event.setCancelled(true);
						}
					}else{
						//acc is not valid ...
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						player.sendMessage(ButtonLock.getCurrentLanguage().ICONOMY_NOT_VALID_ACC);
						event.setCancelled(true);
					}
				}else{
					//no acc ...
					player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
					player.sendMessage(ButtonLock.getCurrentLanguage().ICONOMY_NO_ACC);
					event.setCancelled(true);
				}
			}
		}
	}
    
    public void essentialsEco(PlayerInteractEvent event) {
    	//get event-infos ...
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		//find PlayerVars
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		//find Button in the locked-button-list ...
		LockedBlockGroup group = ButtonLock.getLockedGroup(block);
		
    	//iconom
		if (ButtonLock.getButtonLockConfig().useEssentialsEco && ! event.isCancelled()) {
		
			if (ButtonLock.iConomyByPass(player)) {
				currentPlayerVars.setCurrentClickedLockedGroup(group);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				player.sendMessage(ButtonLock.getCurrentLanguage().ECONOMY_BYPASS);
				
			}else{
				
				String name = player.getName();
				
				if (Economy.playerExists(name)){
					
					try{
						double costs = group.costs;
						
						if (Economy.hasEnough(name, costs)) {
							Economy.subtract(name, costs);
							
							if (costs > 0) {
								player.sendMessage(String.format(ButtonLock.getCurrentLanguage().ICONOMY_MONY_SUBTRACTED, costs));
							}else if (! ButtonLock.getButtonLockConfig().economyIsFreeAsDefault) {
								player.sendMessage(ButtonLock.getCurrentLanguage().ICONOMY_MONY_SUBTRACTED_FREE);
							}	
						}else {
							//not enough money ...
							player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
							player.sendMessage(String.format(ButtonLock.getCurrentLanguage().ICONOMY_LESS_MONY, costs));
							event.setCancelled(true);
						}
					}catch (Exception e){	
					}
				}else{
					//no acc ...
					player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
					player.sendMessage(ButtonLock.getCurrentLanguage().ICONOMY_NO_ACC);
					event.setCancelled(true);
				}
			}
		}
	}
    

}




