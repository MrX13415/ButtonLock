package de.MrX13415.ButtonLock;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;


public class BlockFunctions {

	public static Block getPartBlock(Block block) {
		Material blockType = block.getType();
		Block partBlock = null;
		
		//get Part Blocks ...
		//... for Doors		
		if (isDoorTop(block)){ //test if the given Block is the top of a door
			partBlock = block.getRelative(BlockFace.DOWN);
			if (! isDoorBottom(partBlock)) partBlock = null;
		}else{
			partBlock = block.getRelative(BlockFace.UP);
			if (! isDoorTop(partBlock)) partBlock = null;
		}
		
		//... fot Chests
		if (blockType.equals(Material.CHEST)){ //test if the given Block is the top of a door
			BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}; 
			for (BlockFace currentface : faces) {
				if (block.getRelative(currentface).getType().equals(Material.CHEST)) 	partBlock = block.getRelative(currentface);
			}
		}
		
		
		//... for Beds
		if (blockType.equals(Material.BED_BLOCK)) {
			if (isBedHead(block)){ //test if the given Block is the top of a bed
				
				BlockFace direction = getBedDirection(block);
				if (direction.equals(BlockFace.SOUTH)) {
					direction = BlockFace.NORTH;
				}else if (direction.equals(BlockFace.NORTH)) {
					direction = BlockFace.SOUTH;
				}else if (direction.equals(BlockFace.EAST)) {
					direction = BlockFace.WEST;
				}else if (direction.equals(BlockFace.WEST)) {
					direction = BlockFace.EAST;
				}
				
				partBlock = block.getRelative(direction);
			}else{
				partBlock = block.getRelative(getBedDirection(block));
			}

		}
				
		return partBlock;
	}
	
	private static BlockFace getBedDirection(Block block){
		if (Data.isObjectGroup_facing(block, Data.BED_DIGITS, Data.BED_FACING_NORTH)) {
			return BlockFace.NORTH;
		}else if (Data.isObjectGroup_facing(block, Data.BED_DIGITS, Data.BED_FACING_SOUTH)) {
			return BlockFace.SOUTH;
		}else if (Data.isObjectGroup_facing(block, Data.BED_DIGITS, Data.BED_FACING_WEST)) {
			return BlockFace.WEST;
		}else if (Data.isObjectGroup_facing(block, Data.BED_DIGITS, Data.BED_FACING_EAST)) {
			return BlockFace.EAST;
		}
		return BlockFace.SELF;
	}
	
	public static Block getAttachedBlock(Block block) {
		Block attachedBlock = null;
	
		//button, lever, torch, redstone-torch
		if (Data.isFromObjectGroup1(block)) {
			if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_1_DIGITS, Data.OBJECT_GROUP_1_FACING_NORTH)) {
				attachedBlock = block.getRelative(BlockFace.NORTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_1_DIGITS, Data.OBJECT_GROUP_1_FACING_SOUTH)) {
				attachedBlock = block.getRelative(BlockFace.SOUTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_1_DIGITS, Data.OBJECT_GROUP_1_FACING_WEST)) {
				attachedBlock = block.getRelative(BlockFace.WEST);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_1_DIGITS, Data.OBJECT_GROUP_1_FACING_EAST)) {
				attachedBlock = block.getRelative(BlockFace.EAST);
			}else if (Data.isObjectGroup_1_facing_TOP(block)) {
				attachedBlock = block.getRelative(BlockFace.DOWN);
			}
		}
		
		if (Data.isFromObjectGroup2(block)) {
			attachedBlock = block.getRelative(BlockFace.DOWN);
			
			//special for Doors
			if (isDoorTop(block)){ //test if the given Block is the top of a door
				attachedBlock = attachedBlock.getRelative(BlockFace.DOWN);
			}
		}
		
		if (Data.isFromObjectGroup3(block)) {
			if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_3_DIGITS, Data.OBJECT_GROUP_3_FACING_NORTH)) {
				attachedBlock = block.getRelative(BlockFace.NORTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_3_DIGITS, Data.OBJECT_GROUP_3_FACING_SOUTH)) {
				attachedBlock = block.getRelative(BlockFace.SOUTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_3_DIGITS, Data.OBJECT_GROUP_3_FACING_WEST)) {
				attachedBlock = block.getRelative(BlockFace.WEST);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_3_DIGITS, Data.OBJECT_GROUP_3_FACING_EAST)) {
				attachedBlock = block.getRelative(BlockFace.EAST);
			}
		}
		
		if (Data.isFromObjectGroup4(block)) {
			if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_4_DIGITS, Data.OBJECT_GROUP_4_FACING_NORTH)) {
				attachedBlock = block.getRelative(BlockFace.NORTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_4_DIGITS, Data.OBJECT_GROUP_4_FACING_SOUTH)) {
				attachedBlock = block.getRelative(BlockFace.SOUTH);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_4_DIGITS, Data.OBJECT_GROUP_4_FACING_WEST)) {
				attachedBlock = block.getRelative(BlockFace.WEST);
			}else if (Data.isObjectGroup_facing(block, Data.OBJECT_GROUP_4_DIGITS, Data.OBJECT_GROUP_4_FACING_EAST)) {
				attachedBlock = block.getRelative(BlockFace.EAST);
			}
		}
		
		return attachedBlock;
	}

	public static boolean isDoorTop(Block block) {
		if (block.getType().equals(Material.WOODEN_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK)) {
			if (Data.bitTest(block.getData(), Data.DOOR_TOP)){ //test if the give Block is the top of a door
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDoorBottom(Block block) {
		if (block.getType().equals(Material.WOODEN_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK)) {
			if (! isDoorTop(block)){ //test if the give Block is the top of a door
				return true;
			}
		}
		return false;
	}
	
	public static boolean isBedHead(Block block) {
		if (Data.bitTest(block.getData(), Data.BED_HEAD)){ //test if the given Block is the bed head
			return true;
		}
		return false;
	}
	
}
