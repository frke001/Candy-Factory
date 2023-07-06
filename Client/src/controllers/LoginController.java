package controllers;



import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.exception.BlockedException;
import org.unibl.etf.exception.IllegalCredencialsException;
import org.unibl.etf.exception.NotActivatedException;
import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import util.ControllerUtil;
import util.MyLogger;

public class LoginController implements Initializable{

    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private Button registerButton;
    
    private UserService userService;
    

    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userService = new UserService();
		
	}

    @FXML
    void onLoginButton(ActionEvent event) {
    	if("".equals(usernameTextField.getText()) || "".equals(passwordTextField.getText())) {
    		alert("Greška", "Unesite podatke");
    		MyLogger.log(Level.WARNING, "Unesite podatke");
    		return;
    	}
    	UserLoginDTO userLoginDTO = new UserLoginDTO(usernameTextField.getText(),passwordTextField.getText());

		try {
			User user = userService.login(userLoginDTO);
			Stage stage = (Stage) loginButton.getScene().getWindow();
	        stage.close();
	        
	        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("products.fxml"));
	        ProductsController productsController = new ProductsController(user);
	        fxmlLoader.setController(productsController);
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage1 = new Stage();
			stage1.setTitle("Products");
			stage1.setScene(scene);
			stage1.show();
			
		} catch (Exception ex) {
			alert("Greška", ex.getMessage());
			MyLogger.log(Level.SEVERE,ex.getMessage());
			return;
		}

    }
    @FXML
    void onRegisterButton(ActionEvent event) {
    	try {
    		Stage stage = (Stage) loginButton.getScene().getWindow();
	        stage.close();
    		ControllerUtil.showScene("register.fxml", "Register");
    	}catch (Exception ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE,ex.getMessage());
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

