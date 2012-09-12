package de.MrX13415.ButtonLock.Languages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import org.bukkit.ChatColor;
import de.MrX13415.ButtonLock.ButtonLock;


public class LanguageLoader {

	private static String languageFileExtention = ".lang";
	private static String languageFilePath = "plugins/" + ButtonLock.getPluginName() + "/languages/";
	
	private static final String keyName = "Name";
	private static final String keyVersion = "Version";
	
	private static String fileFormat_keys = "%s: %s"; 
	private static String fileFormat_keys_key_value_seperator = ":"; 
	private static String fileFormat_Comments_prefix = "#";
	
	private static Language defaultLanguage = new English();
	
	private LanguageLoader() {
	}
	
	public static String getLanguageFileExtention() {
		return languageFileExtention;
	}

	public static String getLanguageFilePath() {
		return languageFilePath;
	}

	public static boolean langExists(String languageName) {
		File langfile = new File(languageFilePath + languageName + languageFileExtention);
		if (langfile.exists()) {
			return true;
		}
		return false;
	}
	
	public static Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public static Language getBaseLanguage(String language){
		if(new English()._languageName.equalsIgnoreCase(language)) return new English();
		if(new German()._languageName.equalsIgnoreCase(language)) return new German();
		return defaultLanguage;
	}
	
	public static void updateLanguages(boolean force) {
		Language language;
		
		language = LanguageLoader.loadLanguage(new English()._languageName);
		if (language.update(force)) ButtonLock.getButtonlockLogger().info(ButtonLock.getConsoleOutputHeader() + " " + language._languageName + " language file updated ...");   

		//german ...
		language = LanguageLoader.loadLanguage(new German()._languageName);
		if (language.update(force)) ButtonLock.getButtonlockLogger().info(ButtonLock.getConsoleOutputHeader() + " " + language._languageName + " language file updated ...");  
       
      	language = null;
	}
	
	public static Language loadDefaultLanguage() {
		return loadLanguage(defaultLanguage._languageName);
	}
	
	public static Language loadLanguage(Language language) {
		return loadLanguage(language._languageName);
	}
	
	public static Language loadLanguage(String languageName) {
		
		//Select default lang ..
		Language lang = getBaseLanguage(languageName);
		
		LineNumberReader reader;
		
		try {
			reader = new LineNumberReader(new FileReader(languageFilePath + languageName + languageFileExtention));
			
			try {
				while (reader.ready()) {
					String line = reader.readLine();

					if (! line.startsWith(fileFormat_Comments_prefix) && ! line.isEmpty()) {
						
						String[] keyLine = new String[2];
						keyLine[0] = line;
						keyLine[1] = "";
						
						if (line.contains(fileFormat_keys_key_value_seperator)) {
							keyLine[0] = line.substring(0, line.indexOf(fileFormat_keys_key_value_seperator)).trim();
							keyLine[1] = line.substring(line.indexOf(fileFormat_keys_key_value_seperator) + fileFormat_keys_key_value_seperator.length()).trim();
						}
					
						if ("Name".equalsIgnoreCase(keyLine[0])){
							lang._languageName = keyLine[1].trim();
						}
						
						if ("Version".equalsIgnoreCase(keyLine[0])){
							lang._version = keyLine[1].trim();
						}

						for(Field langField:Language.class.getDeclaredFields()){		
							if (langField.getName().equalsIgnoreCase(keyLine[0])){
							
								langField.set(lang, keyLine[1].trim().replaceAll("\\\\n", "\n"));
								break;
							}
						}
					}
				}	
				
			} catch (Exception e) {
				ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: An error occurred while loading the language file.");
				e.printStackTrace();
			}
			
			try {reader.close();} catch (IOException e) {}

		} catch (FileNotFoundException e) {
			ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: language not found: " + languageName);
			
			saveLanguage(lang);
		}
		
		return lang;
	}
	
	public static boolean saveLanguage(Language lang) {
		
		FileWriter writer;
		try {
			File directory = new File(languageFilePath);
			if (! directory.exists()) directory.mkdir();
			
			writer = new FileWriter(languageFilePath + lang._languageName + languageFileExtention);

			writer.write("#" + ButtonLock.getPluginDescriptionFile().getName() + " by " + ButtonLock.getPluginDescriptionFile().getAuthors() + "\n");
			writer.write("#Language: " + lang._languageName + "  Autor: " + ButtonLock.getPluginDescriptionFile().getAuthors() + "\n");
			writer.write("#\n");
			writer.write("# Colors: \n");
			writer.write("# \n");
			writer.write("#    Use the prefix behind the 'Name' of the color.\n");
			writer.write("#    Example: \"" + ChatColor.GOLD + "GOLD\"\n");
			writer.write("# \n");
			for (ChatColor color : ChatColor.values()) {
				writer.write("#        " + color.name() + ": " + color + "\n");
			}
			writer.write("#\n");
			writer.write("# Format: \n");
			writer.write("# \n");
			writer.write("#    The Prefix %s will be replaced with a internal value.\n");
			writer.write("#    Example: \"Player added: %s\" ==> \"Player added: MrX13415\"\n");
			writer.write("# \n");
			writer.write("#    It is not necessary to use the prefix.\n");
			writer.write("#    Example: \"Player added ...\"\n");
			writer.write("# \n");
			writer.write("#    For future informations visit: http://download.oracle.com/javase/6/docs/api/java/util/Formatter.html\n");
			writer.write("# \n");
			writer.write(String.format(fileFormat_keys, keyName, lang._languageName) + "\n");
			writer.write(String.format(fileFormat_keys, keyVersion, lang._LastVersion) + "\n");
			writer.write("\n"); 
			
			for(Field langField:Language.class.getDeclaredFields()){
				if (!langField.getName().startsWith("_")){  
					String value = (String) langField.get(lang);
					writer.write(String.format(fileFormat_keys, langField.getName(), value.replaceAll("\\n", "\\\\n")) + "\n");
				}
			}
			
			writer.close();
			
			ButtonLock.getButtonlockLogger().info(ButtonLock.getConsoleOutputHeader() + " Language saved: " + lang._languageName);
			
			return true;
		} catch (Exception e1) {
			ButtonLock.getButtonlockLogger().warning(ButtonLock.getConsoleOutputHeader() + " Error: can't create language file: " + lang._languageName);		
		}
		return false;
	}
}
