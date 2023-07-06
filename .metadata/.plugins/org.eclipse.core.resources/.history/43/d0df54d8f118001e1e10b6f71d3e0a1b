package application;
	
import java.net.URL;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.ConfigUtil;
import util.MyLogger;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
	        Scene scene = new Scene(fxmlLoader.load());
	        primaryStage.setTitle("Login");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			MyLogger.log(Level.SEVERE,e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
