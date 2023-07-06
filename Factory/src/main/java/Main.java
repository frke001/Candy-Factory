
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unibl.etf.dao.FactoryUserDAO;
import org.unibl.etf.dao.ProductDao;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.model.FactoryUser;
import org.unibl.etf.model.Product;
import org.unibl.etf.model.User;
import org.unibl.etf.service.UserService;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisClusterCrossSlotException;

public class Main {

	
	public static void main(String args[]) {
		
		UserDAO userDAO = new UserDAO();
//		
//		userDAO.writeAllUsers(Arrays.asList(new User("frke"), new User("dafi"),new User("dzona")));
//		userDAO.writeUser(new User("test"));
//		userDAO.writeUser(new User("test1"));
//		List<User> users = userDAO.readAllUsers();
//		for (var user : users) {
//			System.out.println(user);
//		}
		
//		UserService userService = new UserService();
//		//userService.block("frke");
//		userService.activate("dafi");
//		userService.delete("test");
		
		
		ProductDao productDao = new ProductDao();
		
//		productDao.deleteProduct(1);
//		productDao.deleteProduct(2);
//		productDao.deleteProduct(3);
//		productDao.deleteProduct(4);
		
//		Product product = new Product("Ime1",1.4,10);
//		
//		productDao.addProduct(product);
//		productDao.addProduct(new Product("Ime2",2.4,10));
//		productDao.addProduct(new Product("Ime3",3.4,10));
//		
//		
//		product.setName("NovoIme");
//		productDao.updateProduct(product);
//		
//		for (var p : productDao.getAllProduct()) {
//			System.out.println(p);
//		}
		FactoryUserDAO factoryUserDAO = new FactoryUserDAO();
				
		for(var el : factoryUserDAO.readAllUsers()) {
			System.out.println(el);
		}
	}
}
