package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import org.bukkit.Material;

import de.MrX13415.ButtonLock.LockedBlockGroup.LOCKED_STATE;


public class Config {
	
	private String configFileName = "config.yml";
	private String configFilePath = "plugins/" + ButtonLock.pluginName + "/";
	
	private static final String keyDefaultLockedStatesForDoors = "DefaultLockedStatesForDoors";
	private static final String keyDefaultLockedStatesForLevers = "DefaultLockedStatesForLevers";
	private static final String keyConfigFileVersion = "ConfigFileVersion";
	private static final String keyUseChatforPasswordInput = "UseChatforPasswordInput";
	private static final String keyEnablePasswordByPass = "EnablePasswordByPass";
	private static final String keyForcePasswordEveryTimeByDefault = "ForcePasswordEveryTimeByDefault";
	private static final String keyEnableIConomyByPass = "EnableIConomyByPass";
	private static final String keyUsePermissions = "UsePermsissions";
	private static final String keyOPOnly = "IfNoPermissionsOPOnly";
	private static final String keyUseIConomy = "UseIConomy";
	private static final String keyIConomyIsFreeAsDefault = "IsFreeAsDefault";
	private static final String keyIConomyCosts = "DefaultPriceIfNotFree";
	private static final String keyTimeforEnteringPassword = "TimeforEnteringPassword";
	private static final String listLockableBlocks = "LockableBlocksList:";
	private static final String fileFormat_keys = "%s: %s"; 
	private static final String fileFormat_lists_start = "  - "; 
	private static final String fileFormat_lists = fileFormat_lists_start + "%s";
	private static final String fileFormat_Comments_prefix = "#";
		
	//-- file content --
	public LOCKED_STATE defaultLockedStatesForDoors = LOCKED_STATE.CLOSE;
	public LOCKED_STATE defaultLockedStatesForLevers = LOCKED_STATE.BOTH;
	public String configFileVersion = "----";
	public boolean useChatforPasswordInput = true;
	public boolean usePermissions = true;
	public boolean oPOnly = false;
	public boolean useIConomy = false;
	public boolean enableIConomyByPass = false;
	public boolean enablePasswordByPass = false;
	public boolean forcePasswordEveryTimeByDefault = false;
	public boolean iConomyIsFreeAsDefault = true;
	public double iConomyCosts = 0.50;
	public long timeforEnteringPassword = 10000;  //millis
	//------------------
	public String currentlist;	
	
	public Config() {
		//nothing
	}
	
	public Config(boolean setVersion) {
		super();
		updateVersion();
	}
	
	public void updateVersion(){
		configFileVersion = ButtonLock.pdfFile.getVersion().toString();
	}
	
	public void read() {
		LineNumberReader reader;
		
		try {
			reader = new LineNumberReader(new FileReader(configFilePath + configFileName));
			
			try {
				while (reader.ready()) {
					String line = reader.readLine();

					if (! line.startsWith(fileFormat_Comments_prefix)) {
						if (line.startsWith(listLockableBlocks)) {
							currentlist = listLockableBlocks;
							ButtonLock.lockableBlocksList.clear();
						}

						if (line.startsWith(fileFormat_lists_start)) {
							if (currentlist.equals(listLockableBlocks)) {
								ButtonLock.lockableBlocksList.add(Material.getMaterial(line.replace(fileFormat_lists_start, "")));
							}
						}
						
						
						String[] keyLine = line.replaceAll(": ", ":").split(":");
						
						if (keyLine[0].equalsIgnoreCase(keyUseChatforPasswordInput)) {
							useChatforPasswordInput = Boolean.valueOf(keyLine[1]);
						}
											
						if (keyLine[0].equalsIgnoreCase(keyUsePermissions)) {
							usePermissions = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyOPOnly)) {
							oPOnly = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyUseIConomy)) {
							useIConomy = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyIConomyIsFreeAsDefault)) {
							iConomyIsFreeAsDefault = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyIConomyCosts)) {
							iConomyCosts = Double.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyTimeforEnteringPassword)) {
							timeforEnteringPassword = Long.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyEnableIConomyByPass)) {
							enableIConomyByPass = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyEnablePasswordByPass)) {
							enablePasswordByPass = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyForcePasswordEveryTimeByDefault)) {
							forcePasswordEveryTimeByDefault = Boolean.valueOf(keyLine[1]);
						}
						
						if (keyLine[0].equalsIgnoreCase(keyConfigFileVersion)) {
							configFileVersion = keyLine[1];
						}
						
						if (keyLine[0].equalsIgnoreCase(keyDefaultLockedStatesForDoors)) {
							defaultLockedStatesForDoors = LOCKED_STATE.valueOf(keyLine[1]);
							
						}
						
						if (keyLine[0].equalsIgnoreCase(keyDefaultLockedStatesForLevers)) {
							defaultLockedStatesForLevers = LOCKED_STATE.valueOf(keyLine[1]);
						}
					}
				}				
			} catch (Exception e) {
				ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: An error occurred while reading.");
			}

		} catch (FileNotFoundException e) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: config file not found.");
			
			ButtonLock.lockableBlocksList.add(Material.STONE_BUTTON);
			ButtonLock.lockableBlocksList.add(Material.LEVER);
			ButtonLock.lockableBlocksList.add(Material.CHEST);
			ButtonLock.lockableBlocksList.add(Material.WOODEN_DOOR);
			ButtonLock.lockableBlocksList.add(Material.IRON_DOOR_BLOCK);
			ButtonLock.lockableBlocksList.add(Material.TRAP_DOOR);
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
			
			writer = new FileWriter(configFilePath + configFileName);
			
			writer.write("#" + ButtonLock.pdfFile.getFullName() + "  by: " + ButtonLock.pdfFile.getAuthors() + "\n");
			writer.write(String.format(fileFormat_keys, keyConfigFileVersion, configFileVersion) + "\n");
			writer.write("\n");
			writer.write(String.format(fileFormat_keys, keyUseChatforPasswordInput, useChatforPasswordInput) + "\n");
			writer.write(String.format(fileFormat_keys, keyEnablePasswordByPass, enablePasswordByPass) + "\n");
			writer.write(String.format(fileFormat_keys, keyForcePasswordEveryTimeByDefault, forcePasswordEveryTimeByDefault) + "\n");
			writer.write(String.format(fileFormat_keys, keyDefaultLockedStatesForDoors, defaultLockedStatesForDoors) + "\n");
			writer.write(String.format(fileFormat_keys, keyDefaultLockedStatesForLevers, defaultLockedStatesForLevers) + "\n");
			writer.write("\n");
			writer.write(fileFormat_Comments_prefix + " in milliseconds (1000 = 1 sec)\n");
			writer.write(String.format(fileFormat_keys, keyTimeforEnteringPassword, timeforEnteringPassword) + "\n");
			writer.write("\n");
			writer.write(String.format(fileFormat_keys, keyUsePermissions, usePermissions) + "\n");
			writer.write(String.format(fileFormat_keys, keyOPOnly, oPOnly) + "\n");
			writer.write("\n");
			writer.write(fileFormat_Comments_prefix + " IConomy\n");
			writer.write(String.format(fileFormat_keys, keyUseIConomy, useIConomy) + "\n");
			writer.write(String.format(fileFormat_keys, keyEnableIConomyByPass, enableIConomyByPass) + "\n");
			writer.write(String.format(fileFormat_keys, keyIConomyIsFreeAsDefault , iConomyIsFreeAsDefault) + "\n");
			writer.write(String.format(fileFormat_keys, keyIConomyCosts , iConomyCosts) + "\n");
			writer.write("\n");
			writer.write(listLockableBlocks + "\n");
			
			if (ButtonLock.lockableBlocksList.size() <= 0){
				ButtonLock.lockableBlocksList.add(Material.STONE_BUTTON);
				ButtonLock.lockableBlocksList.add(Material.LEVER);
				ButtonLock.lockableBlocksList.add(Material.CHEST);
				ButtonLock.lockableBlocksList.add(Material.WOODEN_DOOR);
				ButtonLock.lockableBlocksList.add(Material.IRON_DOOR_BLOCK);
				ButtonLock.lockableBlocksList.add(Material.TRAP_DOOR);
			}
			
			for (int index = 0; index < ButtonLock.lockableBlocksList.size(); index++) {
				writer.write(String.format(fileFormat_lists, ButtonLock.lockableBlocksList.get(index)) + "\n");
			}
			
			writer.close();
		
			return true;
		} catch (Exception e1) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: can't create new config file.");		
		}
		return false;
	}
	
}
