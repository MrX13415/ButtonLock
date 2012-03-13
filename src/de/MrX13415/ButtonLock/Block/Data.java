package de.MrX13415.ButtonLock.Block;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Data {

	//bits
	static final int DOOR_TOP = 3;//1 == TOP
	static final int BED_HEAD = 3; //1 == HEAD
	
	static final int DOOR_OPEN = 2;		//1 == open | doors; trap-doors; fence-gates
	static final int LEVER_ON = 3;		//1 == ON
	
	//bed
	static final int BED_DIGITS = 2;
	static final String BED_FACING_WEST = "00";
	static final String BED_FACING_EAST = "10";
	static final String BED_FACING_SOUTH = "11";
	static final String BED_FACING_NORTH = "01";
	
	//OBJECT_GROUP_1: button, lever, torch, redstone-torch
	static final int OBJECT_GROUP_1_DIGITS = 3;
	static final String OBJECT_GROUP_1_FACING_WEST = "100";
	static final String OBJECT_GROUP_1_FACING_EAST = "011";
	static final String OBJECT_GROUP_1_FACING_SOUTH = "010";
	static final String OBJECT_GROUP_1_FACING_NORTH = "001"; 
	static final String OBJECT_GROUP_1_FACING_TOP_EAST_WEST = "101";
	static final String OBJECT_GROUP_1_FACING_TOP_NORTH_SOUTH = "110";
	
	//OBJECT_GROUP_3: ladder, wall-sing
	static final int OBJECT_GROUP_3_DIGITS = 3;
	static final String OBJECT_GROUP_3_FACING_WEST = "010";
	static final String OBJECT_GROUP_3_FACING_EAST = "011";
	static final String OBJECT_GROUP_3_FACING_SOUTH = "100";
	static final String OBJECT_GROUP_3_FACING_NORTH = "101";
	
	//OBJECT_GROUP_4: trap-door
	static final int OBJECT_GROUP_4_DIGITS = 2;
	static final String OBJECT_GROUP_4_FACING_WEST = "00";
	static final String OBJECT_GROUP_4_FACING_EAST = "01";
	static final String OBJECT_GROUP_4_FACING_SOUTH = "10";
	static final String OBJECT_GROUP_4_FACING_NORTH = "11";
	
	/** returns the data with a give digit lenght
	 * 
	 * @param block
	 * @param digits
	 * @return
	 */
	public static String getData(Block block, int digits){
		String data = Integer.toBinaryString(block.getData());
		data = "00000000".substring(0, 7 - (data.length() - 1)) + data;
		if (data.length() >= digits) return data.substring(data.length() - digits);
		return "";
	}

	public static boolean isFromObjectGroup1(Block block){
		if (block.getType().equals(Material.STONE_BUTTON) ||
			block.getType().equals(Material.LEVER) ||
			block.getType().equals(Material.TORCH) ||
			block.getType().equals(Material.REDSTONE_TORCH_ON) ||
			block.getType().equals(Material.REDSTONE_TORCH_OFF)) return true;
		return false;
	}

	public static boolean isObjectGroup_1_facing_TOP(Block block){
		String data = getData(block, OBJECT_GROUP_1_DIGITS);
		if (data.equals(OBJECT_GROUP_1_FACING_TOP_EAST_WEST)) return true;
		if (data.equals(OBJECT_GROUP_1_FACING_TOP_NORTH_SOUTH)) return true;
		return false;
	}
	
	public static boolean isObjectGroup_facing(Block block, int digits, String obejectGroupFace){
		String data = getData(block, digits);
		if (data.equals(obejectGroupFace)) return true;
		return false;
	}
	
	public static boolean isFromObjectGroup2(Block block){
		if (block.getType().equals(Material.WOODEN_DOOR) ||
			block.getType().equals(Material.IRON_DOOR_BLOCK) ||
			block.getType().equals(Material.RED_MUSHROOM) ||
			block.getType().equals(Material.RED_ROSE) ||
			block.getType().equals(Material.YELLOW_FLOWER) ||
			block.getType().equals(Material.BROWN_MUSHROOM) ||
			block.getType().equals(Material.CACTUS) ||
			block.getType().equals(Material.REDSTONE_WIRE) ||
			block.getType().equals(Material.STONE_PLATE) ||
			block.getType().equals(Material.WOOD_PLATE) ||
			block.getType().equals(Material.CAKE_BLOCK) ||
			block.getType().equals(Material.RAILS) ||
			block.getType().equals(Material.POWERED_RAIL) ||
			block.getType().equals(Material.DETECTOR_RAIL) ||
			block.getType().equals(Material.CROPS) ||
			block.getType().equals(Material.SAPLING) ||
			block.getType().equals(Material.SUGAR_CANE_BLOCK) ||
			block.getType().equals(Material.DEAD_BUSH) ||
			block.getType().equals(Material.SIGN_POST) ||
			block.getType().equals(Material.LONG_GRASS) ||
			block.getType().equals(Material.DIODE_BLOCK_ON) ||
			block.getType().equals(Material.DIODE_BLOCK_OFF)) return true;
		return false;
	}
	
	public static boolean isFromObjectGroup3(Block block){
		if (block.getType().equals(Material.LADDER) ||
			block.getType().equals(Material.WALL_SIGN)) return true;
		return false;
	}
	
	public static boolean isFromObjectGroup4(Block block){
		if (block.getType().equals(Material.TRAP_DOOR)) return true;
		return false;
	}
	
	/**
	 * 
	 * @param value
	 * @param bit
	 * @return true == 1 / false == 0
	 */
	public static boolean bitTest(int value, int bit){
		String binary = Integer.toBinaryString(value);
		String requestedBit = "0";
		int pos = binary.length() - bit - 1;
		
		if (! (pos < 0)){
			requestedBit = binary.substring(pos, pos + 1);
		}

		if (requestedBit.equalsIgnoreCase("1")) {
			return true;
		}
	
		return false;
	}

}
