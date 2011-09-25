package de.MrX13415.ButtonLock;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;

import de.MrX13415.ButtonLock.LockedBlockGroup.PROTECTION_MODE;


public class ButtonLockBlockListener extends org.bukkit.event.block.BlockListener {

	public void onBlockPlace(BlockPlaceEvent event) {
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
						
					}else{
						player.sendMessage(Language.DENIED);
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
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()|| group.getProtectionMode() == PROTECTION_MODE.PUBLIC || group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}else{
				group.setUnlock(false);
			}
		}
	}

	public void onBlockBurn(BlockBurnEvent event) {
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
		super.onBlockCanBuild(event);
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()) {
				event.setBuildable(false); // cancel event because the button is locked ...
			}
		}
	}
	
	@Override
	public void onBlockFade(BlockFadeEvent event) {
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
		super.onBlockFromTo(event);
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
	public void onBlockSpread(BlockSpreadEvent event) {
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
		super.onBlockIgnite(event);
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()|| group.getProtectionMode() == PROTECTION_MODE.PUBLIC || group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@Override
	public void onSignChange(SignChangeEvent event) {
		super.onSignChange(event);
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()|| group.getProtectionMode() == PROTECTION_MODE.PUBLIC || group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
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
		super.onBlockDamage(event);
		// get event-infos ...
		Block block = event.getBlock();

		// button is unlocked
		if (ButtonLock.isProtected(block)) {
			LockedBlockGroup group = ButtonLock.getLockedGroup(block);
			if (! group.isUnlocked()|| group.getProtectionMode() == PROTECTION_MODE.PUBLIC || group.getProtectionMode() == PROTECTION_MODE.PRIVATE) {
				event.setCancelled(true); // cancel event because the button is locked ...
			}
		}
	}
	
	
	
}
