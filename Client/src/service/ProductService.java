package service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.model.Product;
import org.unibl.etf.model.User;
import org.unibl.etf.util.Status;

import util.ConfigUtil;

public class ProductService {
	
	public List<Product> getAllProducts() throws Exception{
		List<Product> products = new ArrayList<>();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ConfigUtil.readConfig().getProperty("PRODUCTS_URL"));
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() != Status.OK) {
	        throw new Exception(response.readEntity(String.class));	        
	    } else {
	        products = response.readEntity(new GenericType<List<Product>>() {}); // Products[].class
	    }
		return products;
	}

}
