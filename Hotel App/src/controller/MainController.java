package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Button adminBtn;

	@FXML
	private Button bookGuestBtn;

	@FXML
	void handleBookGuest(ActionEvent event) throws IOException {

		setScene(event, "/views/NoOfRoomsAvail.fxml");

	}

	@FXML
	void loginAsAdmin(ActionEvent event) throws IOException {

		setScene(event, "/views/Login.fxml");

	}

	// Setting Scene
	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	
	//Quitting the application
	@FXML
	void handleExit(ActionEvent event) {
		Platform.exit();
	}

}
