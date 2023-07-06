package org.unibl.etf.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.model.Product;
import org.unibl.etf.service.ProductService;

@Path("/products")
public class ProductController {

	
	private ProductService productService;
	
	public ProductController() {
		productService = new ProductService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
}
