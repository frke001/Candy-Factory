package controllers;

import java.io.IOException;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.ControllerUtil;
import util.MyLogger;

public class StartupController {
	@FXML
	private Button productsButton;

	@FXML
	private Button promotionButton;

	@FXML
	private Button usersButton;
	@FXML
	private Button distributorsButton;

	@FXML
	void onProductsButton(ActionEvent event) {
		try {
			ControllerUtil.showScene("products.fxml", "Proizvodi");
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}

	@FXML
	void onPromotionButton(ActionEvent event) {
		try {
			ControllerUtil.showScene("promotion.fxml", "Promocija");
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}

	@FXML
	void onUsersButton(ActionEvent event) {
		try {
			ControllerUtil.showScene("users.fxml", "Nalozi");
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}

	@FXML
	void onDistributorsButton(ActionEvent event) {
		try {
			ControllerUtil.showScene("distributors.fxml", "Nalozi");
		} catch (IOException ex) {
			ex.printStackTrace();
			MyLogger.log(Level.SEVERE, ex.getMessage());
		}
	}
}
