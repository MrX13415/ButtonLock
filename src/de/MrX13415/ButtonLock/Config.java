package de.MrX13415.ButtonLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;

import org.bukkit.Material;

public class Config {
	
	private String configFileName = "config.yml";
	private String configFilePath = "plugins/" + ButtonLock.pluginName + "/";
	
	private static final String keyUseChatforPasswordInput = "useChatforPasswordInput";
	private static final String listLockableBlocks = "ListLockableBlocks:";
	private static final String fileFormat_keys = "%s: %s"; 
	private static final String fileFormat_lists_start = "  - "; 
	private static final String fileFormat_lists = fileFormat_lists_start + "%s";
		
	//-- file content --
	public boolean useChatforPasswordInput = true;
	//------------------
	public String currentlist;	
	
	public void read() {
		LineNumberReader reader;
		
		try {
			reader = new LineNumberReader(new FileReader(configFilePath + configFileName));
			
			try {
				while (reader.ready()) {
					String line = reader.readLine();

					if (line.startsWith(listLockableBlocks)) {
						currentlist = listLockableBlocks;
					}

					if (line.startsWith(fileFormat_lists_start)) {
						if (currentlist.equals(listLockableBlocks)) {
							ButtonLock.lockableBlocksList.add(Material.getMaterial(line.replace(fileFormat_lists_start, "")));
						}
					}
					
					String[] keyLine = line.replace(" ", "").split(":");
					
					if (keyLine[0].equalsIgnoreCase(keyUseChatforPasswordInput)) {
						String tmpValue = keyLine[1].toLowerCase();
						if (tmpValue.contains("true")) {
							useChatforPasswordInput= true;
						}else if (tmpValue.contains("false")) {
							useChatforPasswordInput = false;
						}else if (tmpValue.contains("1")) {
							useChatforPasswordInput = true;
						}else if (tmpValue.contains("0")) {
							useChatforPasswordInput = false;
						}
					}

				}				
			} catch (Exception e) {
				ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: An error occurred while reading.");
			}

		} catch (FileNotFoundException e) {
			ButtonLock.log.warning(ButtonLock.consoleOutputHeader + " Error: config.yml in '" + ButtonLock.pluginName + "' not found.");
			
			ButtonLock.lockableBlocksList.add(Material.STONE_BUTTON);
			ButtonLock.lockableBlocksList.add(Material.LEVER);
			ButtonLock.lockableBlocksList.add(Material.CHEST);
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
			
			writer.write(String.format(fileFormat_keys, keyUseChatforPasswordInput, useChatforPasswordInput) + "\n");
			writer.write(listLockableBlocks + "\n");
			
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
