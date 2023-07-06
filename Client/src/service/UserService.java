package service;

import javax.ws.rs.POST;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;
import org.unibl.etf.util.Status;

import util.ConfigUtil;

public class UserService {

	public User login(UserLoginDTO userLoginDTO) throws Exception {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ConfigUtil.readConfig().getProperty("LOGIN_URL"));
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(userLoginDTO, MediaType.APPLICATION_JSON));
		if(response.getStatus() != Status.OK) {
			throw new Exception(response.readEntity(String.class));
		}
		return response.readEntity(User.class);
	}
	
	public boolean register(User user) throws Exception {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ConfigUtil.readConfig().getProperty("REGISTER_URL"));
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(user, MediaType.APPLICATION_JSON));
		
		if(response.getStatus() != Status.OK) {
			throw new Exception("Postojeće korisničko ime!");
		}
		return true;
	}
}
