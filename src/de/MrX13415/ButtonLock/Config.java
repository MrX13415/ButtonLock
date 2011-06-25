package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;

import org.bukkit.Material;

public class Config {
	
//	private String configFileName = "config.yml";
//	private String configFilePath = "plugins/" + ButtonLock.pluginName + "/";
//	
//	private static final String keyBlockPW = "BlockPW";
//	private static final String keyBlockPosX = "BlockPosX";
//	private static final String keyBlockPosY = "BlockPosY";
//	private static final String keyBlockPosZ = "BlockPosZ";
//	private static final String keyBlockType = "BlockType";
//	private static final String fileFormat = "%s: %s"; 
//	
//	//-- file content --
//	public String blockPW = "";
//	public double blockPosX = 0;
//	public double blockPosY = 0;
//	public double blockPosZ = 0;
//	public Material blockType = Material.AIR;
//	//------------------
//	
//	public void read() {
//		LineNumberReader reader;
//		
//		try {
//			reader = new LineNumberReader(new FileReader(configFilePath + configFileName));
//			
//			try {
//				while (reader.ready()) {
//					String[] line = reader.readLine().replace(" ", "").split(":");
//					
//					if (line[0].equalsIgnoreCase(keyItemID)) {
//						itemID = Integer.valueOf(line[1]);
//					}
//
//					if (line[0].equalsIgnoreCase(keyItemName)) {
//						itemName = line[1];
//					}
//					
//					if (line[0].equalsIgnoreCase(keyUsePermissions)) {
//						String tmpValue = line[1].toLowerCase();
//						if (tmpValue.contains("true")) {
//							usePermissions = true;
//						}else if (tmpValue.contains("false")) {
//							usePermissions = false;
//						}else if (tmpValue.contains("1")) {
//							usePermissions = true;
//						}else if (tmpValue.contains("0")) {
//							usePermissions = false;
//						}
//					}
//
//				}				
//			} catch (Exception e) {
//				System.err.println(ButtonLock.consoleOutputHeader + " Error: An error occurred while reading.");
//			}
//
//		} catch (FileNotFoundException e) {
//			System.err.println(ButtonLock.consoleOutputHeader + " Error: config.yml in '" + ButtonLock.pluginName + "' not found.");
//			
//			if (write()) { //create new File
//				System.out.println(ButtonLock.consoleOutputHeader + " New Config file created. (" + ButtonLock.pluginName + "/config.yml)");
//			}
//		}
//	}
//	
//	public boolean write() {
//		FileWriter writer;
//		try {
//			File directory = new File(configFilePath);
//			if (! directory.exists()) directory.mkdir();
//			
//			writer = new FileWriter(configFilePath + configFileName);
//			
//			writer.write(String.format(fileFormat, keyItemID, itemID) + "\n");
//			writer.write(String.format(fileFormat, keyItemName, itemName) + "\n");
//			writer.write(String.format(fileFormat, keyUsePermissions, usePermissions) + "\n");
//			
//			writer.close();
//		
//			return true;
//		} catch (Exception e1) {
//			System.err.println(ButtonLock.consoleOutputHeader + " Error: can't create new config file.");		
//		}
//		return false;
//	}
	
}
