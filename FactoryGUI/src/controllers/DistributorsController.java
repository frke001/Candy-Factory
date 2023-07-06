package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.lang.model.element.Element;

import org.unibl.etf.model.Distributor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RawMaterial;
import rmi.DistributorRMI;
import util.ConfigUtil;
import util.MyLogger;

public class DistributorsController implements Initializable {
	@FXML
	private Button buyButton;

	@FXML
	private TableColumn<Distributor, String> distributorsNameColumn;

	@FXML
	private TableView<Distributor> distributorsTable;

	@FXML
	private TableColumn<RawMaterial, Integer> idColumn;

	@FXML
	private TableColumn<RawMaterial, String> nameColumn;

	@FXML
	private TableView<RawMaterial> materialsTable;

	@FXML
	private TableColumn<RawMaterial, Integer> quantityColumn;

	private ObservableList<Distributor> distributorsList;

	private ObservableList<RawMaterial> materials;
	
	private DistributorRMI stub;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			
			distributorsList = FXCollections.observableArrayList();
			String[] names = registry.list();
			for(var el : names) {
				distributorsList.add(new Distributor(el));
			}


			System.out.println(distributorsList);
			materials = FXCollections.observableArrayList();

			distributorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

			distributorsTable.getItems().clear();
			distributorsTable.setItems(distributorsList);
			
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			
			materialsTable.getItems().clear();
			materialsTable.setItems(materials);
			
			materialsTable.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1) {
					materialsTable.getSelectionModel().setCellSelectionEnabled(true);
					materialsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				}
			});
			
			distributorsTable.setOnMouseClicked(event -> {
				
				if (event.getClickCount() == 1) {
					distributorsTable.getSelectionModel().setCellSelectionEnabled(true);
					distributorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				}
				if (event.getClickCount() == 2) {
					Distributor distributor = distributorsTable.getSelectionModel().getSelectedItem();

					if (distributor != null) {
						try {
							stub = (DistributorRMI) registry.lookup(distributor.getName());
							materials.clear();
							materials.addAll(stub.getAllRawMaterials());
						} catch (Exception ex) {	
							ex.printStackTrace();
							alert("Greška", "Greška prilikom dobavljanja sirovina");
						}
					}
				}
			});

		} catch (Exception e) {
			alert("Greška", "Greška prilikom dobavljanja distributera");
			MyLogger.log(Level.SEVERE,  "Greška prilikom dobavljanja distributera");
		}

	}

	@FXML
	void onBuyButton(ActionEvent event) {
		
		RawMaterial rawMaterial = materialsTable.getSelectionModel().getSelectedItem();
		
		if(rawMaterial != null) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Unos količine");
			dialog.setHeaderText("Unesite količinu za " + rawMaterial.getName());
			dialog.setContentText("Količina:");

			dialog.showAndWait().ifPresent(quantity -> {
				try {
					int quantityValue = Integer.parseInt(quantity);
					if(stub.buyRawMateral(rawMaterial.getId(),  quantityValue)) {
						meesageBox("Količina","Uspješno kupljena sirovina");
					}else {
						alert("Greška", "Prevelika količina");
						MyLogger.log(Level.WARNING,  "Prevelika količina");
					}
				} catch (NumberFormatException | RemoteException e ) {
					alert("Greška", "Nevalidna količina");
					MyLogger.log(Level.WARNING,  "Nevalidna količina");
				}
			});
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
