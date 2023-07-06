package controllers;

import java.net.URL;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;

import model.RawMaterial;
import rmi.DistributorRMI;
import rmi.DistributorRMIImpl;
import util.ConfigUtil;
import util.MyLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegisterController implements Initializable {

	@FXML
	private Button addButton;

	@FXML
	private TextField companyNameTextField;

	@FXML
	private TableColumn<RawMaterial, Integer> idColumn;

	@FXML
	private TableColumn<RawMaterial, String> nameColumn;

	@FXML
	private TextField productNameTextField;

	@FXML
	private TableView<RawMaterial> productsTable;

	@FXML
	private TableColumn<RawMaterial, Integer> quantityColumn;

	@FXML
	private TextField quantityTextField;

	@FXML
	private Button registerButton;
	
	private ObservableList<RawMaterial> productList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		productList = FXCollections.observableArrayList();
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		productsTable.getItems().clear();
		productsTable.setItems(productList);

	}

	@FXML
	void onAddButton(ActionEvent event) {
		if("".equals(productNameTextField.getText()) || "".equals(quantityTextField.getText())) {
			alert("Greška", "Unesite podatke");
			MyLogger.log(Level.WARNING, "Unesite podatke");
			return;
		}
		try {
			RawMaterial product = new RawMaterial(productNameTextField.getText(),Integer.parseInt(quantityTextField.getText()));
			productList.add(product);
			productNameTextField.clear();
			quantityTextField.clear();
		}catch (NumberFormatException ex) {
			alert("Greška", "Pogrešan format");
			MyLogger.log(Level.WARNING, "Pogrešan format");
		}
		
	}

	@FXML
	void onRegisterButton(ActionEvent event) {
		
		if("".equals(companyNameTextField.getText())) {
			alert("Greška", "Unesite ime firme");
			MyLogger.log(Level.WARNING, "Unesite ime firme");
			return;
		}
		System.setProperty("java.security.policy", ConfigUtil.readConfig().getProperty("POLICY"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		DistributorRMIImpl server = new DistributorRMIImpl(productList);

		try {
			DistributorRMI stub = (DistributorRMI) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.getRegistry(Integer.parseInt(ConfigUtil.readConfig().getProperty("REGISTRY_PORT")));
			registry.rebind(companyNameTextField.getText(), stub);
			
		} catch (Exception ex) {
			alert("Greška", "Greška sa registracijom firme");
			MyLogger.log(Level.SEVERE, "Greška sa registracijom firme");
			MyLogger.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
		}
		 registerButton.setDisable(true);
	}
	
	private void alert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	

}
