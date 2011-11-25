package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;


public class ButtonLockBlockListener extends org.bukkit.event.block.BlockListener {

//	org.bukkit.event.block.BlockListener ebb; 
	
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
						player.sendMessage(ButtonLock.language.DENIED);
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
	
	public void onBlockPhysics(BlockPhysicsEvent event) {
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
						player.sendMessage(ButtonLock.language.CANT_REMOVE_LOCKED_GROUPS);
						event.setCancelled(true);
					}else {
						player.sendMessage(ButtonLock.language.DENIED);
						event.setCancelled(true);
					}
				}
				break;
			}
		}
	}

	public void onBlockBurn(BlockBurnEvent event) {
		ButtonLock.debugEvent(event);
			
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}else{
				group.setUnlock(false);
			}
		}
	}
	
	@Override
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
	
	@Override
	public void onBlockFade(BlockFadeEvent event) {
		ButtonLock.debugEvent(event);
			
		super.onBlockFade(event);
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
	
	
	@Override
	public void onBlockForm(BlockFormEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockForm(event);
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
	
	
	@Override
	public void onBlockFromTo(BlockFromToEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockFromTo(event);
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
	
	
	@Override
	public void onBlockSpread(BlockSpreadEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockSpread(event);
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
	
	
	@Override
	public void onBlockIgnite(BlockIgniteEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockIgnite(event);
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
	
	
	@Override
	public void onSignChange(SignChangeEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onSignChange(event);
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
	
	
	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onLeavesDecay(event);
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
	
	@Override
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockRedstoneChange(event);
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()) {
				event.setNewCurrent(event.getOldCurrent()); // cancel event because the button is locked ...
			}
		}
	}
	
	
	@Override
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockPistonExtend(event);
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
	
	
	@Override
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockPistonRetract(event);
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
	
	
	@Override
	public void onBlockDispense(BlockDispenseEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockDispense(event);
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
	
	
	@Override
	public void onBlockDamage(BlockDamageEvent event) {
		ButtonLock.debugEvent(event);
		
		super.onBlockDamage(event);
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
