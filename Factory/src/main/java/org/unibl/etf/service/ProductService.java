package org.unibl.etf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.unibl.etf.dao.ProductDao;
import org.unibl.etf.model.Product;

import redis.clients.jedis.Jedis;

public class ProductService {

	private static ProductDao productDao;

	static {
		productDao = new ProductDao();
		// System.out.println("Pozdrav iz statickog!");
		Product.count = productDao.getMaxId() + 1;
		if (Product.count == 1) {
			productDao.addProduct(new Product("Sladoled", 1.5, 150));
			productDao.addProduct(new Product("Čokolada", 5.0, 25));
			productDao.addProduct(new Product("Napolitanka", 2.5, 100));
			productDao.addProduct(new Product("Evrokrem", 4.0, 50));
			productDao.addProduct(new Product("Jaffa", 1.7, 70));
		}
	}

	public ProductService() {
	}

	public List<Product> getAllProducts() {
		return productDao.getAllProducts();
	}
	
	public void addProduct(Product product) {
		productDao.addProduct(product);
	}

	public Product getProductById(int productId) {
		return productDao.getProductById(productId);
	}

	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

	public void deleteProduct(int productId) {
		productDao.deleteProduct(productId);
	}


}
