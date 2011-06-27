package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;


public class ButtonLockBlockListener extends org.bukkit.event.block.BlockListener{
	
	 public void onBlockPlace(BlockPlaceEvent event) {
		 Player player = event.getPlayer();
		 Block block = event.getBlock();
		 
		 //find PlayerVars
		 PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		 //add a button?
		 if (currentPlayerVars != null) currentPlayerVars.setCurrentClickedBlock(block);
	 }
	 
	 public void onBlockBreak(BlockBreakEvent event){
			//get event-infos ...
			Block block = event.getBlock();

			//check if at the current block is a locked block ...
			Block blockAtBlock = ButtonLock.getProtectableBlockAtBlock(block);
			if (blockAtBlock != null) {
				block = blockAtBlock;
			}
			
			//find Button in the locked-button-list ...
			Button button = ButtonLock.getButton(block);
					
			//button was founded ...
			if (button != null) {
			
				//button is unlocked
				if (button.isUnlocked()) {
					ButtonLock.removeButton(button);
				}else{
					event.setCancelled(true);						//cancel event because the button is locked ...	
				}
			}
	 }
	 
	 public void onBlockBurn(BlockBurnEvent event){
			//get event-infos ...
			Block block = event.getBlock();

			//check if at the current block is a locked block ...
			Block blockAtBlock = ButtonLock.getProtectableBlockAtBlock(block);
			if (blockAtBlock != null) {
				block = blockAtBlock;
			}
			
			//find Button in the locked-button-list ...
			Button button = ButtonLock.getButton(block);
					
			//button was founded ...
			if (button != null) {
			
				//button is unlocked
				if (button.isUnlocked()) {
					ButtonLock.removeButton(button);
				}else{
					event.setCancelled(true);						//cancel event because the button is locked ...	
				}
			}
	 }
}
