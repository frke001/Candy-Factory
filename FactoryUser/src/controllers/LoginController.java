package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import application.MainFU;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.FactoryUserAuthenticationService;
import util.MyLogger;

public class LoginController implements Initializable {

	@FXML
	private Button loginButton;

	@FXML
	private TextField nameTextField;

	private FactoryUserAuthenticationService authenticationService;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		authenticationService = new FactoryUserAuthenticationService();
	}

	@FXML
	void onLoginButton(ActionEvent event) {

		if ("".equals(nameTextField.getText())) {
			alert("Greška", "Unesite ime");
			MyLogger.log(Level.WARNING, "Unesite ime");
			return;
		}
		try {
			if (authenticationService.sendLoginRequest(nameTextField.getText())) {
				System.out.println("Uspjesna prijava");
				FXMLLoader fxmlLoader = new FXMLLoader(MainFU.class.getResource("orders.fxml"));
				ProcessOrderController processOrderController = new ProcessOrderController(authenticationService);
				fxmlLoader.setController(processOrderController);
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage1 = new Stage();
				stage1.setTitle("Products");
				stage1.setScene(scene);
				stage1.show();
				Stage stage = (Stage) loginButton.getScene().getWindow();
				stage.close();
				stage1.setOnCloseRequest(e -> {
					authenticationService.end();
				});
			} else {
				alert("Greška", "Pogrešno ime");
				MyLogger.log(Level.WARNING, "Pogrešno ime");
			}
		} catch (IOException ex) {

			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}

	private void alert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
