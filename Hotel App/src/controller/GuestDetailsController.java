package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Guest;

public class GuestDetailsController implements Initializable {

	private DatabaseAccess da;

	public GuestDetailsController() {
		da = new DatabaseAccess();
	}

	@FXML
	private TextField addressTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ComboBox<String> titleComboBox;

	@FXML
	void submitGuestInfoAction(ActionEvent event) throws SQLException, IOException {

		if (addressTextField.getText().isEmpty() || emailTextField.getText().isEmpty()
				|| firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()
				|| phoneTextField.getText().isEmpty()) {

			showAlert(Alert.AlertType.ERROR, "Empty Value", "Enter all fields");
		} else {

			String title = titleComboBox.getValue();
			String address = addressTextField.getText();
			String email = emailTextField.getText();
			String fname = firstNameTextField.getText();
			String lname = lastNameTextField.getText();
			long phone = Long.parseLong(phoneTextField.getText());

			boolean isValid = isValidEmail(email);

			if (isValid) {
				// Guest
				Guest g = new Guest(Main.getGuestId(), title, fname, lname, address, phone, email);
				Main.guestID++;

				// Guest Database
				int i = da.insertGuestID(g);

				System.out.print("Guest ID = " + i);

				// Booking DB
				boolean book = da.insertBooking(i);

				if (book) {
					showAlert(Alert.AlertType.CONFIRMATION, "Booked", "Thank You Booking");
					setScene(event, "/views/AdminMenu.fxml");
				} else {
					showAlert(Alert.AlertType.ERROR, "Failed", "Plese Check All fields and Try Agian");
				}

			}

		}

	}

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	private static void showAlert(Alert.AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		titleComboBox.setItems(FXCollections.observableArrayList("Mr.", "Ms.", "Mrs."));

		firstNameTextField.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[a-zA-Z]")) {
				phoneTextField.setText("");
			}
		});

		phoneTextField.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9]")) {
				phoneTextField.setText("");
			}
		});

		lastNameTextField.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[a-zA-Z]")) {
				lastNameTextField.setText("");
			}
		});

	}

	public static boolean isValidEmail(String email) {
		// Regular expression for a valid email address
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);

		if (matcher.matches()) {
			return true;
		} else {
			showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Email!");
			return false;
		}
	}

}
