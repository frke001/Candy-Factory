package org.unibl.etf.service;

import java.nio.channels.NonReadableChannelException;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;

import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.exception.BlockedException;
import org.unibl.etf.exception.IllegalCredencialsException;
import org.unibl.etf.exception.NotActivatedException;
import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;

public class UserService {
	
	private UserDAO userDAO;
	
	public UserService() {
		this.userDAO = new UserDAO();
	}
	
	public boolean register(User user) {
		List<User> users = userDAO.readAllUsers();
		if(users.contains(user))
			return false;
		
		userDAO.writeUser(user);
		return true;
	}
	
	public User login(UserLoginDTO userLoginDTO) throws IllegalCredencialsException,NotActivatedException, BlockedException {
		List<User> users = userDAO.readAllUsers();
		int index = users.indexOf(new User(userLoginDTO.getUsername()));
		User tempUser = null;
		if(index != -1) {
			tempUser = users.get(index);
			if(!tempUser.getUsername().equals(userLoginDTO.getUsername()) 
					|| !tempUser.getPassword().equals(userLoginDTO.getPassword())){
				throw new IllegalCredencialsException("Neuspjesno prijavljivanje!");
			}
			if(!tempUser.isActivated()) {
				throw new NotActivatedException("Nalog nije aktiviran!");
			}
			if(tempUser.isBlocked()) {
				throw new BlockedException("Nalog je blokiran!");
			}
		}else {
			throw new IllegalCredencialsException("Neuspjesno prijavljivanje!");
		}
		return tempUser;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		return userDAO.readAllUsers();
	}
	
	public boolean delete(String username) {
		List<User> users = userDAO.readAllUsers();
		boolean status = users.remove(new User(username));	
		userDAO.writeAllUsers(users);
		
		return status;
	}
	
	public boolean block(String username) {
		List<User> users = userDAO.readAllUsers();
		int index = users.indexOf(new User(username));	
		
		if(index != -1) {
			User user = users.get(index);
			user.setBlocked(true);
			userDAO.writeAllUsers(users);
			return true;
		}
		return false;
	}
	
	public boolean unblock(String username) {
		List<User> users = userDAO.readAllUsers();
		int index = users.indexOf(new User(username));	
		
		if(index != -1) {
			User user = users.get(index);
			user.setBlocked(false);
			userDAO.writeAllUsers(users);
			return true;
		}
		return false;
	}
	public boolean activate(String username) {
		List<User> users = userDAO.readAllUsers();
		int index = users.indexOf(new User(username));	
		
		if(index != -1) {
			User user = users.get(index);
			user.setActivated(true);
			userDAO.writeAllUsers(users);
			return true;
		}
		return false;
	}

	
	

}
