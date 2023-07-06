package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.model.User;
import org.unibl.etf.service.UserService;
import org.unibl.etf.util.Status;

import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import util.MyLogger;

public class UsersController implements Initializable {
	
	 @FXML
	    private Button activateButton;

	    @FXML
	    private TableColumn<User, Boolean> activateColumn;

	    @FXML
	    private TableColumn<User, String> addressColumn;

	    @FXML
	    private Button blockButton;

	    @FXML
	    private TableColumn<User, Boolean> blockColumn;

	    @FXML
	    private TableColumn<User, String> companyColumn;

	    @FXML
	    private Button deleteButton;
	    
	    @FXML
	    private Button unblockButton;

	    @FXML
	    private TableColumn<User, String> emailColumn;

	    @FXML
	    private TableColumn<User, String> phoneColumn;

	    @FXML
	    private TableColumn<User, String> usernameColumn;

	    @FXML
	    private TableView<User> usersTable;
	    
	    private UserService userService;
	    
	    public static ObservableList<User> users;
	    
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	    	userService = new UserService();
	    	
	    	users = FXCollections.observableArrayList(userService.getAllUsers());
	    	
	    	usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
			companyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
			addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
			phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
			activateColumn.setCellValueFactory(new PropertyValueFactory<>("activated"));
			blockColumn.setCellValueFactory(new PropertyValueFactory<>("blocked"));
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
			
			usersTable.getItems().clear();
			usersTable.setItems(users);
			
			usersTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                	usersTable.getSelectionModel().setCellSelectionEnabled(true);
                	usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                }
                });
			
		}

	    @FXML
	    void onActivateButton(ActionEvent event) {
	    	User selectedItem = usersTable.getSelectionModel().getSelectedItem();
	        if(selectedItem != null){
	            boolean status = userService.activate(selectedItem.getUsername());
	            selectedItem.setActivated(status);
	            if(!status) {
	            	alert("Greška", "Nalog nije uspješno aktiviran");
	            	MyLogger.log(Level.WARNING, "Nalog nije uspješno aktiviran");
	            }else {
	            	selectedItem.setActivated(true);
	            	usersTable.refresh();
	            	meesageBox("Aktivacija", "Nalog uspješno aktiviran");
	            }
	        }
	    }

	    @FXML
	    void onBlockButton(ActionEvent event) {
	    	User selectedItem = usersTable.getSelectionModel().getSelectedItem();
	        if(selectedItem != null){
	            boolean status = userService.block(selectedItem.getUsername());
	            selectedItem.setBlocked(true);
	            if(!status) {
	            	alert("Greška", "Nalog nije uspješno blokiran");
	            	MyLogger.log(Level.WARNING, "Nalog nije uspješno blokiran");
	            }else {
	            	selectedItem.setBlocked(true);
	            	usersTable.refresh();
	            	meesageBox("Blokiranje", "Nalog uspješno blokiran");
	            }
	        }
	    }
	    
	    @FXML
	    void onUnblockButton(ActionEvent event) {
	    	User selectedItem = usersTable.getSelectionModel().getSelectedItem();
	        if(selectedItem != null){
	            boolean status = userService.unblock(selectedItem.getUsername());
	            if(!status) {
	            	alert("Greška", "Nalog nije uspješno blokiran");
	            	MyLogger.log(Level.WARNING,  "Nalog nije uspješno blokiran");
	            }else {
	            	selectedItem.setBlocked(false);
	            	usersTable.refresh();
	            	meesageBox("Blokiranje", "Nalog uspješno blokiran");
	            }
	        }
	    }

	    @FXML
	    void onDeleteButton(ActionEvent event) {
	    	User selectedItem = usersTable.getSelectionModel().getSelectedItem();
	    	users.remove(selectedItem);
	        if(selectedItem != null){
	            boolean status = userService.delete(selectedItem.getUsername());
	            if(!status) {
	            	alert("Greška", "Nalog nije uspješno obrisan");
	            	MyLogger.log(Level.WARNING,  "Nalog nije uspješno obrisan");
	            }
	            else {
	            	meesageBox("Brisanje", "Nalog uspješno obrisan");
	            }
	        }
	    }
	    
	    private void alert(String title, String message){
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    private void meesageBox(String title, String message){
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }




}
