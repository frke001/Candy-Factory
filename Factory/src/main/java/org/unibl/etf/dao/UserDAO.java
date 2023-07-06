package org.unibl.etf.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.json.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.model.User;
import org.unibl.etf.util.ConfigUtil;
import org.unibl.etf.util.MyLogger;

public class UserDAO {
	
	// String username, String password, String companyName, String address, String phoneNumber,boolean activated, boolean blocked, String email
	private static final String USERS_PATH = ConfigUtil.readConfig().getProperty("USER_FILE_PATH");

	public void writeAllUsers(List<User> users) {
		JSONArray jsonArray = new JSONArray(users);
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File(USERS_PATH)));
			out.println(jsonArray.toString());
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
		
	}
	
	public List<User> readAllUsers(){
		List<User> users = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(USERS_PATH)));
			String line = in.readLine();
			JSONArray jsonArray;
 			if(line == null)
 				jsonArray = new JSONArray();
 			else 
 				jsonArray = new JSONArray(line);
 			
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				User user = new User(jsonObject.getString("username"),jsonObject.getString("password"),
						jsonObject.getString("companyName"),jsonObject.getString("address"),
						jsonObject.getString("phoneNumber"),jsonObject.getBoolean("activated"),jsonObject.getBoolean("blocked"),
						jsonObject.getString("email"));
				users.add(user);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
		return users;
	}
	
	public void writeUser(User user) {
		JSONObject jsonObject = new JSONObject(user);
		JSONArray jsonArray = new JSONArray(this.readAllUsers());
		jsonArray.put(jsonObject);
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File(USERS_PATH)));
			out.println(jsonArray.toString());
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}
}
