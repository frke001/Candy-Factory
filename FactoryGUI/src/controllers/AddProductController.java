package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.dao.ProductDao;
import org.unibl.etf.model.Product;
import org.unibl.etf.service.ProductService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.MyLogger;

public class AddProductController implements Initializable{

	@FXML
	private Button addButton;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField quantityTextField;
	
	private ProductService productService;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		productService = new ProductService();
	}

	@FXML
	void onAddButton(ActionEvent event) {
		if("".equals(nameTextField.getText()) || "".equals(priceTextField.getText()) || "".equals(quantityTextField.getText())) {
			alert("Greška", "Unesite podatke");
			MyLogger.log(Level.WARNING, "Unesite podatke");
			return;
		}
		
		try {
			Product product = new Product(nameTextField.getText(),Double.parseDouble(priceTextField.getText()),
					Integer.parseInt(quantityTextField.getText()));
			productService.addProduct(product);
			ProductController.productList.add(product);
			Stage stage = (Stage) addButton.getScene().getWindow();
	        stage.close();
		}catch (NumberFormatException ex) {
			alert("Greška", "Pogrešan format");
			MyLogger.log(Level.WARNING, "Pogrešan format");
			return;
		}
	}
	
	private void alert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
}
}
