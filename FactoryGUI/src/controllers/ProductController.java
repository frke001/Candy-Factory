package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.dao.ProductDao;
import org.unibl.etf.model.Product;
import org.unibl.etf.model.User;
import org.unibl.etf.service.ProductService;

import application.MainClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.ControllerUtil;
import util.MyLogger;

public class ProductController implements Initializable {

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableColumn<Product, Integer> idColumn;

	@FXML
	private TableColumn<Product, String> nameColumn;

	@FXML
	private TableColumn<Product, Double> priceColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private TableColumn<Product, Integer> quantityColumn;

	@FXML
	private Button updateButton;

	
	private ProductService productService;

	public static ObservableList<Product> productList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		productService = new ProductService();

		try {
			productList = FXCollections.observableArrayList(productService.getAllProducts());
		} catch (Exception e) {
			e.printStackTrace();
		}

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		productsTable.getItems().clear();
		productsTable.setItems(productList);

		productsTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				productsTable.getSelectionModel().setCellSelectionEnabled(true);
				productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			}
		});

	}

	@FXML
	void onAddButton(ActionEvent event) {
		try {
			ControllerUtil.showScene("addProduct.fxml", "Dodaj proizvod");
		} catch (IOException e) {
			e.printStackTrace();
			MyLogger.log(Level.SEVERE,  e.getMessage());
		}
	}

	@FXML
	void onDeleteButton(ActionEvent event) {
		Product selectedItem = productsTable.getSelectionModel().getSelectedItem();
		productList.remove(selectedItem);
		if (selectedItem != null) {
			try {
				
				productService.deleteProduct(selectedItem.getId());
				meesageBox("Brisanje", "Proizvod uspješno obrisan");
			} catch (Exception ex) {
				alert("Greška", "Proizvod nije uspješno obrisan");
				MyLogger.log(Level.WARNING,  "Proizvod nije uspješno obrisan");
			}
		}
	}

	@FXML
	void onUpdateButton(ActionEvent event) {
		Product product = productsTable.getSelectionModel().getSelectedItem();
		if (product != null) {
			try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("updateProduct.fxml"));
			UpdateProductController updateProductController = new UpdateProductController(product);
			fxmlLoader.setController(updateProductController);
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage1 = new Stage();
			stage1.setTitle("Products");
			stage1.setScene(scene);
			stage1.showAndWait();
			productsTable.refresh();
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void alert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void meesageBox(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
