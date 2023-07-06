package org.unibl.etf.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.unibl.etf.model.Product;
import org.unibl.etf.util.ConfigUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisClusterCrossSlotException;

public class ProductDao {

	private JedisPool jedisPool;
	private static final String KEY = "product:";

	public ProductDao() {
		String string = ConfigUtil.readConfig().getProperty("JEDIS_HOST");
		jedisPool = new JedisPool(ConfigUtil.readConfig().getProperty("JEDIS_HOST"));
	}

	public void addProduct(Product product) {
		try (Jedis jedis = jedisPool.getResource()) {
			//product.setId(getMaxId() + 1);
			jedis.hmset(KEY + String.valueOf(product.getId()), product.toMap());
		}
	}

	public Product getProductById(int productId) {
		String key = "product:" + productId;
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String, String> objectMap = jedis.hgetAll(key);

			if (objectMap != null && !objectMap.isEmpty()) {
				return new Product(objectMap);
			}
		}
		return null;
	}

	public void updateProduct(Product product) {
		String key = "product:" + product.getId();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hmset(key, product.toMap());
		}
	}

	public void deleteProduct(int productId) {
		String key = "product:" + productId;
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
		}
	}

	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();
	        try(Jedis jedis = jedisPool.getResource()){
	            Set<String> keys = jedis.keys(KEY + "*");
	            for(String key : keys) {
	            	Map<String, String> productMap = jedis.hgetAll(key);
	                products.add(new Product(productMap));
	            }
	        }
	        return products;
	}
	
	public int getMaxId() {
		try (Jedis jedis = jedisPool.getResource()) {
			var keys = jedis.keys(KEY+"*");
			int maxId = keys.stream()
            .mapToInt(key -> Integer.parseInt(key.split(":")[1])) 
            .max().orElse(0);
			return maxId;
		}
	}

}
