package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LockedGroupsConfig {
	
	private String configFileName = "LockedGroups.yml";
	private String configFilePath = "plugins" + File.separator + ButtonLock.pluginName + File.separator;

	private static final String keyGroupNr = "LockedGroup#: ";
	private static final String keyGroupPW = "GroupPW";
	private static final String keyBlockNr = "Block#: ";
//	private static final String keyBlockWorld = "BlockWorld";	//this file setting is not needed any more after 0.4 r10
	private static final String keyBlockPosX = "BlockPosX";
	private static final String keyBlockPosY = "BlockPosY";
	private static final String keyBlockPosZ = "BlockPosZ";
	private static final String fileFormat_Section_start = "";
	private static final String fileFormat_Section_end = "";
	private static final String fileFormat_Section = fileFormat_Section_start + "%s" + fileFormat_Section_end; 
	private static final String fileFormat_Sub = "  ";
	private static final String fileFormat_Sub_Section_start = "";
	private static final String fileFormat_Sub_Section_end = "";
	private static final String fileFormat_Sub_Section = fileFormat_Sub + fileFormat_Sub_Section_start + "%s" + fileFormat_Sub_Section_end; 
	private static final String fileFormat_valueseperator = ":";
	private static final String fileFormat_keys = "  %s" + fileFormat_valueseperator + " %s"; 

		
	public void read() {
		LineNumberReader reader;
		
		int groupNr = -1;
		LockedBlockGroup currentGroup = null;
		int groupPW = 0;
		
		int blockNr = -1;
		String blockWorld = "";
		int blockPosX = 0;
		int blockPosY = 0;
		int blockPosZ = 0;
		
		//get current Server ...
		Server server = ButtonLock.server;

		//read file for each World ...
		for (int worldIndex = 0; worldIndex < server.getWorlds().size(); worldIndex++) {
			World currentworld = server.getWorlds().get(worldIndex);
			
			//make a directory for each World
			String filePath = configFilePath + currentworld.getName() + "/";
			blockWorld = currentworld.getName();
			
			//--- Read a file ---
			try {
				reader = new LineNumberReader(new FileReader(filePath + configFileName));
				int lineNr = 0;
				try {				
					while (reader.ready()) {
						String currentline = reader.readLine();
						lineNr += 1;
						
						if (currentline.contains(keyGroupNr) && currentline.startsWith(fileFormat_Section_start)) {
							//add Group if set before collecting date of the next group ...
							if (currentGroup != null && groupNr > -1) {
								ButtonLock.addLockedGroup(currentGroup);	//add group
							}
						
							currentGroup = new LockedBlockGroup();
							groupNr = Integer.valueOf(currentline.replace(keyGroupNr, "").replace(fileFormat_Section_start, "").replace(fileFormat_Section_end, ""));  //filter next integer out and convert it to integer
						}
						
						if (currentline.contains(keyBlockNr) && currentline.startsWith(fileFormat_Sub_Section_start)) {
							//add Block if set before collecting date of the next Block ...
							if (blockNr > -1) {
								addNewBlock(currentGroup, blockWorld, groupPW, blockPosX, blockPosY, blockPosZ);
							}
						
							blockNr = Integer.valueOf(currentline.replace(keyBlockNr, "").replace(fileFormat_Sub_Section_start, "").replace(fileFormat_Sub_Section_end, "").replace(fileFormat_Sub, ""));  //filter nlxy integer out and convert it to integer
						}
						
						if (currentline.contains(fileFormat_valueseperator)) {
							String[] line = currentline.replace(" ", "").split(fileFormat_valueseperator);
							
							//no need any more ...
//							if (line[0].equalsIgnoreCase(keyBlockWorld)) {
//								blockWorld = line[1];
//							}
							if (line[0].equalsIgnoreCase(keyGroupPW)) {
								groupPW = Integer.valueOf(line[1]);
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

					//add last Block
					if (blockNr > -1) {
						addNewBlock(currentGroup, blockWorld, groupPW, blockPosX, blockPosY, blockPosZ);
					}
					//add last Group
					if (currentGroup != null && groupNr > -1) {
						ButtonLock.addLockedGroup(currentGroup);	//add group
						currentGroup = null;
					}
				} catch (Exception e) {
					ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: An error occurred while loading locked groups at line: " + lineNr + " | Java Error: " + e);
				}
			} catch (FileNotFoundException e) {
				ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: Save files not found.");
			}
			//-------------------
			
		}
	}

	public boolean write() {
		FileWriter writer;
		
		//get current Server ...
		Server server = ButtonLock.server;
		
		try {
			//write file for each World ...
			for (int worldIndex = 0; worldIndex < server.getWorlds().size(); worldIndex++) {
				World currentworld = server.getWorlds().get(worldIndex);
				
				//make a directory for each World
				String filePath = configFilePath + currentworld.getName() + "/";
				
				//--- write a file ---
				File directory = new File(filePath);
				if (! directory.exists()) directory.mkdirs();

//				new File(filePath + configFileName).delete();
				
				writer = new FileWriter(filePath + configFileName);
				
				for (int groupIndex = 0; groupIndex < ButtonLock.grouplist.size(); groupIndex++) {
					LockedBlockGroup group = ButtonLock.grouplist.get(groupIndex);
					

					if (group.getGroupSize() != 0) {
						if (group.getBlock(0).getWorld().getName().equals(currentworld.getName())) {
							int blockPW = group.getPassword();
							writer.write(String.format(fileFormat_Section, keyGroupNr + groupIndex) + "\n");
							writer.write(String.format(fileFormat_keys, keyGroupPW, blockPW) + "\n");
							
							for (int blockIndex = 0; blockIndex < group.getGroupSize(); blockIndex++) {
								
								int blockPosX = group.getBlock(blockIndex).getX();
								int blockPosY = group.getBlock(blockIndex).getY();
								int blockPosZ = group.getBlock(blockIndex).getZ();
								
								writer.write(String.format(fileFormat_Sub_Section, keyBlockNr + blockIndex) + "\n");
			//					writer.write(String.format(fileFormat_Sub_Sub + fileFormat_keys, keyBlockWorld, blockWorld) + "\n");	//no need any more ...
								writer.write(String.format(fileFormat_Sub + fileFormat_keys, keyBlockPosX, blockPosX) + "\n");
								writer.write(String.format(fileFormat_Sub + fileFormat_keys, keyBlockPosY, blockPosY) + "\n");
								writer.write(String.format(fileFormat_Sub + fileFormat_keys, keyBlockPosZ, blockPosZ) + "\n");
							}
						}					
					}
				
				}
				writer.close();
				//--------------------
			}
			
			return true;
		} catch (Exception e1) {
			System.err.println(ButtonLock.consoleOutputHeader + " Error: An error occurred while saveing all locked groups");
			e1.printStackTrace();
		}
		
		
		return false;
	}
	
	private void addNewBlock(LockedBlockGroup currentGroup, String blockWorld, int groupPW, int blockPosX, int blockPosY, int blockPosZ ){
		if (currentGroup != null) {
			World world = ButtonLock.server.getWorld(blockWorld);
			Block block = world.getBlockAt(blockPosX, blockPosY, blockPosZ);
			
			currentGroup.addBlock(block);
			currentGroup.setPassword(groupPW);
			currentGroup.setUnlock(false);
		}
	}
}
