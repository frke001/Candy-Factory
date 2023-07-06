package util;

import java.io.IOException;

import application.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerUtil {

	public static void showScene(String fxml, String title) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();

	}
}
