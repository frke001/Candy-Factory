package controllers;

import java.io.IOException;
import java.util.logging.Level;

import org.unibl.etf.service.PromotionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import util.MyLogger;

public class PublishPromotionControler {

	@FXML
	private Button publishButton;

	@FXML
	private TextArea promotionTextArea;

	@FXML
	void onPublishButton(ActionEvent event) {
		
		if("".equals(promotionTextArea.getText())) {
			alert("Greška", "Unesite promociju");
			MyLogger.log(Level.WARNING,  "Unesite promociju");
		}
		String promotion = promotionTextArea.getText();
		try {
			PromotionService.publishPromotion(promotion);
			meesageBox("Promocija", "Promocija uspješno objavljena");
			promotionTextArea.clear();
		} catch (IOException e) {
			alert("Promocija", "Promocija nije uspješno objavljena");
			MyLogger.log(Level.WARNING,  "Promocija nije uspješno objavljena");
			e.printStackTrace();
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
