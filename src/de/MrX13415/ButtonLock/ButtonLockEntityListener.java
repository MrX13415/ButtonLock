package de.MrX13415.ButtonLock;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EndermanPickupEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

public class ButtonLockEntityListener extends EntityListener{
	
	public void onEntityExplode(EntityExplodeEvent event) {
		ButtonLock.debugEvent(event);
		
		List<Block> blocks = event.blockList();
		
		for (Block block : blocks) {
			if (ButtonLock.isProtected(block)) {	
				event.setCancelled(true);
			}
		}
	}
	
	public void onEndermanPickup(EndermanPickupEvent event) {
		ButtonLock.debugEvent(event);
		
		Block block = event.getBlock();

		if (ButtonLock.isProtected(block)) {	
			event.setCancelled(true);
		}
	}
}
