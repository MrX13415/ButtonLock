package de.MrX13415.ButtonLock.Listener;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Block.BlockFunctions;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup;
import de.MrX13415.ButtonLock.Config.PlayerVars;

public class ButtonLockBlockListener implements Listener {

//	org.bukkit.event.block.BlockListener ebb; 
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		ButtonLock.debugEvent(event);
			
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		LockedBlockGroup group = null;
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

		for (BlockFace face : faces) {
			Block relativeblock = block.getRelative(face);
			if (ButtonLock.isProtected(relativeblock)){
				group = ButtonLock.getLockedGroup(relativeblock);
				
				if (group != null) {
					if (group.hasAccess(player)) {
						Block partBlock = BlockFunctions.getPartBlock(relativeblock);
						if (partBlock != null){
							group.addBlock(partBlock);								
						}
						
						Block attachedBlock = BlockFunctions.getAttachedBlock(relativeblock);
						if (attachedBlock != null){
							group.addBlock(attachedBlock);
						}
						
					}else {
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						event.setCancelled(true);
					}
				}
				break;
			}
		}
		
		// find PlayerVars
		PlayerVars currentPlayerVars = ButtonLock.getPlayerVars(player);

		// add a button?
		if (currentPlayerVars != null)
			currentPlayerVars.setCurrentClickedBlock(block);
	}
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);

			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		ButtonLock.debugEvent(event);
//		
//		// get event-infos ...
//		Block block = event.getBlock();
//
//		// button is unlocked
//		if (ButtonLock.isProtected(block)) {
//			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
//			
//			event.getPlayer().sendMessage("" + group.hasAccess(event.getPlayer()));
//			
//			if (! group.hasAccess(event.getPlayer())) {
//				event.setCancelled(true); // cancel event because the button is locked ...
//			}
//		}
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		LockedBlockGroup group = null;
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

		for (BlockFace face : faces) {
			Block relativeblock = block.getRelative(face);
			if (ButtonLock.isProtected(relativeblock)){
				group = ButtonLock.getLockedGroup(relativeblock);
				
				if (group != null) {
					if (group.hasAccess(player) && ! group.containsBlock(block)) {
						
						
//						Block partBlock = BlockFunctions.getPartBlock(relativeblock);
//						if (partBlock != null){
//							group.addBlock(partBlock);								
//						}
//						
//						Block attachedBlock = BlockFunctions.getAttachedBlock(relativeblock);
//						if (attachedBlock != null){
//							group.addBlock(attachedBlock);
//						}
						
					}else if (group.containsBlock(block)){
						player.sendMessage(ButtonLock.getCurrentLanguage().CANT_REMOVE_LOCKED_GROUPS);
						event.setCancelled(true);
					}else {
						player.sendMessage(ButtonLock.getCurrentLanguage().DENIED);
						event.setCancelled(true);
					}
				}
				break;
			}
		}
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		ButtonLock.debugEvent(event);
			
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}else{
				group.setUnlock(false);
			}
		}
	}
	
	@EventHandler
	public void onBlockCanBuild(BlockCanBuildEvent event) {
//
//		if (ButtonLock.debugmode) {
//			String msg = " onCanBuildEvent event";
//			ButtonLock.server.broadcastMessage(ChatColor.GOLD + ButtonLock.consoleOutputHeader + ChatColor.GRAY + msg);
//			ButtonLock.log.info(ButtonLock.consoleOutputHeader + msg);
//		}
//			
//		super.onBlockCanBuild(event);
//		// get event-infos ...
//		Block block = event.getBlock();
//
//		// button is unlocked
//		if (ButtonLock.isProtected(block)) {
//			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
//			
//			if (! group.isUnlocked()) {
//				event.setBuildable(false); // cancel event because the button is locked ...
//			}
//		}
	}
	
	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		ButtonLock.debugEvent(event);
			
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
		
	@EventHandler
	public void onBlockForm(BlockFormEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();
		Block toBlock = event.getToBlock();
		
		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			event.setCancelled(true); // cancel event because the button is locked ...
		}
		
		// button is unlocked
		if (ButtonLock.isProtected(toBlock)) {
			event.setCancelled(true); // cancel event because the button is locked ...
		}
	}
		
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.hasAccess(event.getPlayer())) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.hasAccess(event.getPlayer())) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
//		ButtonLock.debugEvent(event);
//		
//		// get event-infos ...
//		Block block = event.getBlock();
//
//		// button is unlocked
//		if (ButtonLock.isProtected(block)) {
//			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
//			if (! group.isUnlocked()) {
//				event.setNewCurrent(event.getOldCurrent()); // cancel event because the button is locked ...
//			}
//		}
	}
	
	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
		
	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event) {
		ButtonLock.debugEvent(event);
		

		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isBlockEventsUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		ButtonLock.debugEvent(event);
		
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.hasAccess(event.getPlayer())) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	
	
}
