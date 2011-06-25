package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;


public class ButtonLockPlayerListener extends PlayerListener {

	public void onPlayerInteract(PlayerInteractEvent event){
    	if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
//    		Player player = event.getPlayer();
    		Block block = event.getClickedBlock();
    	

			//clicked block is a button ...
			if (ButtonLock.isProtectable(block)) {

//    		if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
//    			//use Permission
//    			if (ButtonLock.permissionHandler.permission(player, ButtonLock.PERMISSION_NODE_Massband_use)) {
//        			playerInteract(player, block);
//    			}
//			}else{
				//no Permission installed ! (everyone has access)
				playerInteract(event);
//			}
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
		Button button = ButtonLock.getButton(block);

		//button was founded ...
		if (button != null) {
		
			//button is locked
			if (! button.isUnlocked()) {
				//inform player to enter a code ...
				player.sendMessage(Language.TEXT_DENIED);
				player.sendMessage(Language.TEXT_ENTER_CODE);
				
				currentPlayerVars.setEnteringCode(true);					//go into entering password mode
				currentPlayerVars.setCurrentClickedLockedButton(button);	//set current button 
				currentPlayerVars.setCurrentClickedBlock(block);
				
				event.setCancelled(true);						//cancel event because the button is locked ...
			}			
			
			//lock button
			button.setUnlock(false);
		}else{
			//add a button?
			currentPlayerVars.setCurrentClickedBlock(block);
		}
		
		
	}

    public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);
		
		if (currentPlayerVars.isEnteringCode()) {
			//get entered text ...
			String enteredCode = event.getMessage();
			
			//Don't display the entered text int the chat ... 
			event.setCancelled(true);
			
			//display the password to the Player...
			player.sendMessage(Language.TEXT_CODE + Language.getMaskedText(enteredCode));
			
			//check if the password was correct ...
			if (enteredCode.equals(currentPlayerVars.getCurrentClickedLockedButton().getPassword())) {
				player.sendMessage(Language.TEXT_SUCCESS);
				currentPlayerVars.getCurrentClickedLockedButton().setUnlock(true);
			
			}else{
				player.sendMessage(Language.TEXT_DENIED);
				currentPlayerVars.getCurrentClickedLockedButton().setUnlock(false);
				
			}
			//leave password entering mode.
			currentPlayerVars.setEnteringCode(false);
		}
    } 
    
    public void onPlayerQuit(PlayerQuitEvent event) {
    	ButtonLock.removePlayer(event.getPlayer());
	}
    
}



