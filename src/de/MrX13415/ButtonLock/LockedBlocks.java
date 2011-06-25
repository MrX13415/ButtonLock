package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LockedBlocks {
	
	private String configFileName = "lockedblocks.yml";
	private String configFilePath = "plugins/" + ButtonLock.pluginName + "/";
	
	private static final String keyBlockNr = "LockedBlock#";
	private static final String keyBlockWorld = "BlockWorld";
	private static final String keyBlockPW = "BlockPW";
	private static final String keyBlockPosX = "BlockPosX";
	private static final String keyBlockPosY = "BlockPosY";
	private static final String keyBlockPosZ = "BlockPosZ";
	private static final String fileFormat_Section_start = "[";
	private static final String fileFormat_Section_end = "]";
	private static final String fileFormat_Section = fileFormat_Section_start + "%s" + fileFormat_Section_end; 
	private static final String fileFormat_keys = " %s=%s"; 
	private static final String fileFormat_valueseperator = "=";
		
	public void read() {
		LineNumberReader reader;
		
		int blockNr = -1;
		String blockWorld = "";
		String blockPW = "";
		int blockPosX = 0;
		int blockPosY = 0;
		int blockPosZ = 0;
		
		try {
			reader = new LineNumberReader(new FileReader(configFilePath + configFileName));
			
			try {				
				while (reader.ready()) {
					String currentline = reader.readLine();
					
					System.out.println(":" + currentline);

					if (currentline.contains(keyBlockNr) && currentline.startsWith(fileFormat_Section_start)) {

						//add Block if some is set before colecting date of the next block ...
						if (blockNr > -1) {
							addNewButton(blockWorld, blockPW, blockPosX, blockPosY, blockPosZ);
						}
						
						blockNr = Integer.valueOf(currentline.replace(keyBlockNr, "").replace(fileFormat_Section_start, "").replace(fileFormat_Section_end, ""));  //filter nlxy integer out and convert it to integer
					}
					
					if (currentline.contains(fileFormat_valueseperator)) {
						String[] line = currentline.replace(" ", "").split(fileFormat_valueseperator);
						
						if (line[0].equalsIgnoreCase(keyBlockWorld)) {
							blockWorld = line[1];
						}
						
						if (line[0].equalsIgnoreCase(keyBlockPW)) {
							blockPW = line[1];
						}

						if (line[0].equalsIgnoreCase(keyBlockPosX)) {
							blockPosX = Integer.valueOf(line[1]);
						}
						
						if (line[0].equalsIgnoreCase(keyBlockPosY)) {
							blockPosY = Integer.valueOf(line[1]);
						}
						
						if (line[0].equalsIgnoreCase(keyBlockPosZ)) {
							blockPosZ = Integer.valueOf(line[1]);
						}
						
					}

				}
				
				if (blockNr > -1) {
					addNewButton(blockWorld, blockPW, blockPosX, blockPosY, blockPosZ);
				}
			} catch (Exception e) {
				ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: An error occurred while reading.");
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: config.yml in '" + ButtonLock.pluginName + "' not found.");
			
			if (write()) { //create new File
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " New Config file created. (" + ButtonLock.pluginName + "/config.yml)");
			}
		}
	}
	
	public boolean write() {
		FileWriter writer;
		try {
			File directory = new File(configFilePath);
			if (! directory.exists()) directory.mkdir();
			
			File file = new File(configFilePath + configFileName);	
			file.delete();	//create a new file
			
			writer = new FileWriter(configFilePath + configFileName);
			
			for (int index = 0; index < ButtonLock.buttonlist.size(); index++) {
				Button button = ButtonLock.buttonlist.get(index);
				int blockNr = index;
				String blockWorld = button.getBlock().getWorld().getName();
				String blockPW = button.getPassword();
				int blockPosX = button.getBlock().getX();
				int blockPosY = button.getBlock().getY();
				int blockPosZ = button.getBlock().getZ();
				
				writer.write(String.format(fileFormat_Section, keyBlockNr + blockNr) + "\n");
				writer.write(String.format(fileFormat_keys, keyBlockWorld, blockWorld) + "\n");
				writer.write(String.format(fileFormat_keys, keyBlockPW, blockPW) + "\n");
				writer.write(String.format(fileFormat_keys, keyBlockPosX, blockPosX) + "\n");
				writer.write(String.format(fileFormat_keys, keyBlockPosY, blockPosY) + "\n");
				writer.write(String.format(fileFormat_keys, keyBlockPosZ, blockPosZ) + "\n");
				
			}
			writer.close();
		
			return true;
		} catch (Exception e1) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: can't create new config file.");		
		}
		return false;
	}
	
	private void addNewButton(String blockWorld, String blockPW, int blockPosX, int blockPosY, int blockPosZ ){
		try {
			World world = ButtonLock.server.getWorld(blockWorld);
			Block block = world.getBlockAt(blockPosX, blockPosY, blockPosZ);

			Button newButton = new Button();
			newButton.setBlock(block);
			newButton.setPassword(blockPW);
			newButton.setUnlock(false);
			
			ButtonLock.addButton(newButton);	//add loaded, locked Button
			
		} catch (Exception e) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Some buttons could not be loaded ...");
		}
	}
}
