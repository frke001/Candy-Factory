package org.unibl.etf.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigUtil {

	// private static final String CONFIG_FILE_PATH = "D:\\Podaci
	// Preneseno\\Nemanja\\Desktop\\Faks\\3. GODINA\\Sesti semestar\\Mrezno i
	// distribuirano
	// programiranje\\ProjekatMDP\\Factory\\src\\main\\resources\\config.properties";
	private static final String CONFIG_FILE_PATH = "../config.properties";
	private static final String CONFIG_FILE_PATH_GUI = "./resources/config.properties";

	public static Properties readConfig() {
		Properties properties = new Properties();
		try {
			String path = "";
			String userDir = System.getProperty("user.dir");
			if (userDir.endsWith("FactoryGUI"))
				path = CONFIG_FILE_PATH_GUI;
			else {
				InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
				properties.load(is);
				return properties;
			}
			properties.load(new FileReader(new File(path)));
			//properties.load(is);
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