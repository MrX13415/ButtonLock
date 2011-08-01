package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;


public class ButtonLockBlockListener extends org.bukkit.event.block.BlockListener {

	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		// find PlayerVars
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		// add a button?
		if (currentPlayerVars != null)
			currentPlayerVars.setCurrentClickedBlock(block);
	}
		
	public void onBlockPhysics(BlockPhysicsEvent event) {
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			event.setCancelled(true); // cancel event because the button is locked ...
		}
	}

	public void onBlockBreak(BlockBreakEvent event) {
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			event.setCancelled(true); // cancel event because the button is locked ...
		}
	}

	public void onBlockBurn(BlockBurnEvent event) {
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			event.setCancelled(true); // cancel event because the button is locked ...
		}
	}
}
