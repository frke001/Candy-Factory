package application;
	
import java.util.logging.Level;

import org.unibl.etf.util.ConfigUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.MyLogger;
import javafx.scene.Scene;


public class MainClass extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startup.fxml"));
	        Scene scene = new Scene(fxmlLoader.load());
	        primaryStage.setTitle("Users");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			MyLogger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.readConfig().getProperty("POLICY"));
		System.setProperty("java.security.policy", ConfigUtil.readConfig().getProperty("POLICY"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		launch(args);
	}
}
