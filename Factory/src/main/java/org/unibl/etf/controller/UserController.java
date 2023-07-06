package org.unibl.etf.controller;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.exception.BlockedException;
import org.unibl.etf.exception.IllegalCredencialsException;
import org.unibl.etf.exception.NotActivatedException;
import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;
import org.unibl.etf.service.UserService;
import org.unibl.etf.util.Status;

@Path("/users")
public class UserController {

	
	private UserService userService;
	
	public UserController() {
		this.userService = new UserService();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		if(userService.register(user))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserLoginDTO userLoginDTO) {
		
		try {
			User user = userService.login(userLoginDTO);
			return Response.status(Status.OK).entity(user).build();
			
		} catch (IllegalCredencialsException ex) {
			return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
		} catch (NotActivatedException ex) {
			return Response.status(Status.FORBIDDEN).entity(ex.getMessage()).build();
		} catch (BlockedException ex) {
			return Response.status(Status.FORBIDDEN).entity(ex.getMessage()).build();
		}
		
	}
	
}
