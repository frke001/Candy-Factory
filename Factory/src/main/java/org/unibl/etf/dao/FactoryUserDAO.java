package org.unibl.etf.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.enterprise.inject.New;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.model.FactoryUser;
import org.unibl.etf.model.User;
import org.unibl.etf.util.ConfigUtil;
import org.unibl.etf.util.MyLogger;

public class FactoryUserDAO {
	
	public List<FactoryUser> readAllUsers(){
		List<FactoryUser> users = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(ConfigUtil.readConfig().getProperty("FACTORY_USER_FILE_PATH"))));
			String line = in.readLine();
			JSONArray jsonArray;
 			if(line == null)
 				jsonArray = new JSONArray();
 			else 
 				jsonArray = new JSONArray(line);
 			
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				FactoryUser user = new FactoryUser(jsonObject.getString("name"));
				users.add(user);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
		return users;
	}
	
	public boolean checkLogin(String name) {
		List<FactoryUser> users = this.readAllUsers();
		return users.contains(new FactoryUser(name));
	}
}
