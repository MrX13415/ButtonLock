package de.MrX13415.ButtonLock.Listener;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.MrX13415.ButtonLock.ButtonLock;


public class ButtonLockEntityListener implements Listener{
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		ButtonLock.debugEvent(event);
		
		List<Block> blocks = event.blockList();
		
		for (Block block : blocks) {
			if (ButtonLock.isProtected(block)) {	
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEndermanPickup(EntityChangeBlockEvent event) {
		ButtonLock.debugEvent(event);

		Block block = event.getBlock();

		if (ButtonLock.isProtected(block)) {	
			event.setCancelled(true);
		}
	}
}
