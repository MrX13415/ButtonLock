package de.MrX13415.ButtonLock.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.MrX13415.ButtonLock.ButtonLock;
import de.MrX13415.ButtonLock.Config.LockedBlockGroup.PROTECTION_MODE;

public class LockedGroupsConfig {
	
	private String configFileName = "LockedGroups.yml";
	private String configFilePath = "plugins" + File.separator + ButtonLock.getPluginName() + File.separator;

	private static final String keyGroupNr = "LockedGroup#: ";
	private static final String keyOwnerList = "OwnerList";
	private static final String keyProtection = "Protection";
	private static final String keyGroupPW = "GroupPW";
	private static final String keyForcePW = "ForcePW";
	private static final String keyCosts = "Costs";
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
	private static final String fileFormat_list_start = "["; 
	private static final String fileFormat_list_end  = "]"; 
	private static final String fileFormat_list_seperate  = ","; 
	
	public void read() {
		LineNumberReader reader;
		
		boolean errorsORwarinings = false;
		
		int groupNr = -1;
		int blockNr = -1;
		LockedBlockGroup currentGroup = null;
		int groupPW = 0;
		boolean forcePW = ButtonLock.getButtonLockConfig().forcePasswordEveryTimeByDefault;
		
		double costs = 0;
		if (ButtonLock.getButtonLockConfig().economyIsFreeAsDefault){
			costs = 0.00;
		}else{
			costs = ButtonLock.getButtonLockConfig().economyCosts;
		}
		
		boolean changedSetting_fpet = false;
		boolean changedSetting_c = false;
		boolean pwIsSet = false;
		String[] list = null;
		PROTECTION_MODE protection = PROTECTION_MODE.PASSWORD;
		
//		String blockWorld;
		int blockPosX = 0;
		int blockPosY = 0;
		int blockPosZ = 0;
		
		//get current Server ...
		Server server = ButtonLock.getCurrentServer();
		
		//clear all current locked groups before loading ...
		ButtonLock.grouplist.clear();

		//read file for each World ...
		for (int worldIndex = 0; worldIndex < server.getWorlds().size(); worldIndex++) {
			World currentworld = server.getWorlds().get(worldIndex);
			
			//make a directory for each World
			String filePath = configFilePath + currentworld.getName() + "/";
//			blockWorld = currentworld.getName();
			
			//--- Read a file ---
			try {
				reader = new LineNumberReader(new FileReader(filePath + configFileName));
				int lineNr = 0;
				try {				
					while (reader.ready()) {
						String currentline = reader.readLine().trim();
						lineNr += 1;
						
						if (currentline.contains(keyGroupNr) && currentline.startsWith(fileFormat_Section_start)) {
							//add Group if set before collecting date of the next group ...
						
							if (currentGroup != null && groupNr > -1) {
								if (! pwIsSet) {
									ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: Password for Group " + groupNr + " not found! - Default password: \"1\"");
									errorsORwarinings = true;
									groupPW = 49; //hash 49 = char "1" 
								}

								for (String owner : list) {
									currentGroup.addOwner(owner);	
								}
								currentGroup.setProtectionMode(protection);
								currentGroup.setPassword(groupPW);
								currentGroup.setForceEnterPasswordEveryTime(forcePW);
								currentGroup.costs = costs;
								currentGroup.changedSetting_forceEnterPasswordEveryTime = changedSetting_fpet;
								currentGroup.ChangedSetting_costs = changedSetting_c;
								currentGroup.setUnlock(false);
								
								ButtonLock.addLockedGroup(currentGroup);	//add group
								currentGroup = null;
								
								//reset settings to default ...
								forcePW = ButtonLock.getButtonLockConfig().forcePasswordEveryTimeByDefault;
								if (ButtonLock.getButtonLockConfig().economyIsFreeAsDefault){
									costs = 0;
								}else{
									costs = ButtonLock.getButtonLockConfig().economyCosts;
								}
								
							}
						
							currentGroup = new LockedBlockGroup();
							currentGroup.clearGroup();
							pwIsSet = false;
							groupNr += 1; //Integer.valueOf(currentline.replace(keyGroupNr, "").replace(fileFormat_Section_start, "").replace(fileFormat_Section_end, ""));  //filter next integer out and convert it to integer
							blockNr = -1;
						}else if (currentline.contains(keyBlockNr) && currentline.startsWith(fileFormat_Sub_Section_start)) {
							blockNr += 1;
							
							boolean posXSet = false;
							boolean posYSet = false;
							boolean posZSet = false;
							
							//run 3 times
							for (int index = 1; index <= 3; index++) {
								currentline = reader.readLine();
								lineNr += 1;
								
								if (currentline.contains(fileFormat_valueseperator)) {
									
									String[] line = new String[2];
									line[0] = currentline.substring(0, currentline.indexOf(fileFormat_valueseperator)).trim();
									line[1] = currentline.substring(currentline.indexOf(fileFormat_valueseperator) + fileFormat_valueseperator.length()).trim();
								
									if (line[0].equalsIgnoreCase(keyBlockPosX)) {
										posXSet = true;
										blockPosX = Integer.valueOf(line[1]);
									}
									
									if (line[0].equalsIgnoreCase(keyBlockPosY)) {
										posYSet = true;
										blockPosY = Integer.valueOf(line[1]);
									}
									
									if (line[0].equalsIgnoreCase(keyBlockPosZ)) {
										posZSet = true;
										blockPosZ = Integer.valueOf(line[1]);
									}
								}
							}
							
							//add Block ...
							if (currentGroup != null) {
								if (posXSet && posYSet && posZSet) {
									posXSet = false;
									posYSet = false;
									posZSet = false;
									Block block = currentworld.getBlockAt(blockPosX, blockPosY, blockPosZ);
									currentGroup.addBlock(block);
								}else{
									ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: Missing coordinate form Block " + blockNr + " in Group " + groupNr + "!");
									errorsORwarinings = true;
								}
							}
						
						}else if (currentline.contains(fileFormat_valueseperator)) {
							String[] line = currentline.trim().split(fileFormat_valueseperator);
							line[0] = line[0].trim();
							line[1] = line[1].trim();
							
							//no need any more ...
//							if (line[0].equalsIgnoreCase(keyBlockWorld)) {
//								blockWorld = line[1];
//							}
							
							if (line[0].equalsIgnoreCase(keyOwnerList)) {
								list = line[1].replace(fileFormat_list_start, "").replace(fileFormat_list_end, "").split(fileFormat_list_seperate);
							}
							
							if (line[0].equalsIgnoreCase(keyGroupPW)) {
								pwIsSet = true;
								groupPW = Integer.valueOf(line[1]);
							}
							
							if (line[0].equalsIgnoreCase(keyForcePW)) {
								changedSetting_fpet = true;
								forcePW = Boolean.parseBoolean(line[1]);
							}
							
							if (line[0].equalsIgnoreCase(keyCosts)) {
								changedSetting_c = true;
								costs = Double.valueOf(line[1]);
							}
							
							if (line[0].equalsIgnoreCase(keyProtection)) {
								protection = PROTECTION_MODE.valueOf(line[1]);
							}
						}
					}

					//add last Group
					if (currentGroup != null && groupNr > -1) {
						if (pwIsSet) {
							for (String owner : list) {
								currentGroup.addOwner(owner);	
							}
							currentGroup.setProtectionMode(protection);
							currentGroup.setPassword(groupPW);
							currentGroup.setForceEnterPasswordEveryTime(forcePW);
							currentGroup.costs = costs;
							currentGroup.changedSetting_forceEnterPasswordEveryTime = changedSetting_fpet;
							currentGroup.ChangedSetting_costs = changedSetting_c;
							currentGroup.setUnlock(false);
							
							ButtonLock.addLockedGroup(currentGroup);	//add group
							currentGroup = null;
						}else{
							ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: Password for Group " + groupNr + " not found! - Default password: \"1\"");
							errorsORwarinings = true;
						}
					}
				} catch (Exception e) {
					ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: An error occurred while loading locked groups at line: " + lineNr + " | Java Error: " + e);
					errorsORwarinings = true;
				}
			} catch (FileNotFoundException e) {
				ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: Save files not found.");
				errorsORwarinings = true;
			}
			//-------------------
			
		}
		
		if (errorsORwarinings) {
			ButtonLock.getCurrentServer().broadcastMessage(String.format(ButtonLock.getCurrentLanguage().ERROR_LOADING, ButtonLock.getConsoleOutputHeader()));
		}
		
	}

	public boolean write() {
		FileWriter writer;
		
		//get current Server ...
		Server server = ButtonLock.getCurrentServer();
		
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

							writer.write(String.format(fileFormat_Section, keyGroupNr + groupIndex) + "\n");
							
							String ownerList = "";
							for (int index = 0; index < group.getOwnerListSize(); index++) {
								ownerList += group.getOwner(index) + fileFormat_list_seperate;
							}
							
							if (ownerList.endsWith(fileFormat_list_seperate)) ownerList.substring(0, ownerList.length() - 1);
							ownerList = fileFormat_list_start + ownerList + fileFormat_list_end; 
									
							writer.write(String.format(fileFormat_keys, keyOwnerList, ownerList) + "\n");
							writer.write(String.format(fileFormat_keys, keyProtection, group.getProtectionMode()) + "\n");
							writer.write(String.format(fileFormat_keys, keyGroupPW, group.getPassword()) + "\n");
							
							if (group.changedSetting_forceEnterPasswordEveryTime) 
								writer.write(String.format(fileFormat_keys, keyForcePW, group.isForceingEnterPasswordEveryTime()) + "\n");
							
							if (group.ChangedSetting_costs) 
								writer.write(String.format(fileFormat_keys, keyCosts, group.costs) + "\n");
							
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
			System.err.println(ButtonLock.getConsoleOutputHeader() + " Error: An error occurred while saveing all locked groups");
			e1.printStackTrace();
		}
		
		
		return false;
	}

}
