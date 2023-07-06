package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigUtil {
	
private static final String CONFIG_FILE_PATH = "./resources/config.properties";
	
	public static Properties readConfig() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(new File(CONFIG_FILE_PATH)));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
		return properties;
	}

}
