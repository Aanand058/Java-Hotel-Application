package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMenuController {

	@FXML
	void billServiceAction(ActionEvent event) throws IOException {
		setScene(event, "/views/EnterID.fxml");
	}

	@FXML
	void bookRoomAction(ActionEvent event) throws IOException {
		setScene(event, "/views/GuestBoooking.fxml");
	}

	@FXML
	void exitAction(ActionEvent event) throws IOException {
		setScene(event, "/views/HomeScreen.fxml");

	}

	@FXML
	void viewBookingsAction(ActionEvent event) throws IOException {
		setScene(event, "/views/CurrentBooking.fxml");

	}

	@FXML
	void viewRoomsAction(ActionEvent event) throws IOException {
		setScene(event, "/views/AvailableRooms.fxml");
	}
	
	
	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}


}
