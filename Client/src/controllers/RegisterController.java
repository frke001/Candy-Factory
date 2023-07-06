package controllers;

import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import util.ControllerUtil;
import util.MyLogger;

public class RegisterController implements Initializable {

	@FXML
	private TextField addressTextField;

	@FXML
	private TextField companyTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField passwordRepeatedTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private Button registerButton;

	@FXML
	private TextField usernameTextField;

	private UserService userService;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userService = new UserService();

	}

	//String username, String password, String companyName, String address, String phoneNumber,
	//String email
	@FXML
	void onRegisterButton(ActionEvent event) {
		if("".equals(companyTextField.getText()) || "".equals(addressTextField.getText()) 
				||"".equals(phoneTextField.getText()) ||"".equals(usernameTextField.getText()) 
				||"".equals(passwordTextField.getText()) ||"".equals(passwordRepeatedTextField.getText()) 
				||"".equals(emailTextField.getText())){
			alert("Greška", "Unesite podatke");
			MyLogger.log(Level.WARNING, "Unesite podatke");
			return;
		}
		if(!passwordRepeatedTextField.getText().equals(passwordTextField.getText())) {
			alert("Greška", "Pogrešna lozinka");
			MyLogger.log(Level.WARNING, "Unesite podatke");
			return;
		}
		User user = new User(usernameTextField.getText(),passwordTextField.getText(),companyTextField.getText(),
				addressTextField.getText(),phoneTextField.getText(),emailTextField.getText());
		try {
			userService.register(user);
			Stage stage = (Stage) registerButton.getScene().getWindow();
	        stage.close();
	        ControllerUtil.showScene("login.fxml", "Login");
		} catch (Exception ex) {
			alert("Greška", ex.getMessage());
			MyLogger.log(Level.SEVERE, ex.getMessage());
			return;
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
