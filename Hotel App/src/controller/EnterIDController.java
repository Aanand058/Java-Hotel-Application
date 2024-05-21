package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import database.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BillService;

public class EnterIDController implements Initializable {

	private DatabaseAccess da;

	public EnterIDController() {
		da = new DatabaseAccess();
	}

	@FXML
	private TextField idTF;

	@FXML
	void handleIDBtn(ActionEvent event) throws IOException, SQLException {

		if (idTF.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Failed", "Plese Enter Valid Booking ID");
		} else {
			Main.searchID = Integer.parseInt(idTF.getText());

			//Check is booked or not
			boolean checkID = da.checkBooked(Main.getSearchId());

			if (!checkID) {
				showAlert(Alert.AlertType.ERROR, "Failed", "No Bookings Found.\n Please Check Current Bookings");
				setScene(event, "/views/AdminMenu.fxml");
			} else {
				setScene(event, "/views/BillService.fxml");
			}
		}
	}

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	private void showAlert(Alert.AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Only Accepts Numbers 
		idTF.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9]")) {
				idTF.setText("");
			}
		});

	}
}
