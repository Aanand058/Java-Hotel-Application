package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DeluxRoom;
import model.DoubleRoom;
import model.PentHouse;
import model.Reservation;
import model.Room;
import model.SingleRoom;

public class GuestBookingController implements Initializable {

	private DatabaseAccess da;

	public GuestBookingController() {
		da = new DatabaseAccess();
	}

	@FXML
	private Button mainMenuBtn;

	@FXML
	private DatePicker checkInDate;

	@FXML
	private DatePicker checkoutDate;

	@FXML
	private TextField noOfDaysTF;

	@FXML
	private TextField noOfGuestTF;

	@FXML
	private TextField rateTF;

	@FXML
	private ComboBox<String> roomTypeComboBox;

	@FXML
	private Label roomsAvailLB;

	@FXML
	private TextField noOfRoomNeededTF;

	int noOfGuest = 0;
	String roomType = "";
	int noOfDays = 0;
	double ratePerDay = 0;
	int noOfRoomNeeded = 0;

	LocalDate ci;
	LocalDate co;

	LocalDate currentDate = LocalDate.now();

	public int roomID = 1;
	public int bookID = 1;

	// Room Object
	Room sr;

	@FXML
	void confirmRoom(ActionEvent event) throws IOException, SQLException {

		// ChechIn Date Validation
		if (ci == null) {
			showAlertError("Check-in date is missing.");
		}
		// CheckOut Date Validation
		if (co == null) {
			showAlertError("Check-out date is missing.");
		}

		// Print the current date
		System.out.println("Current Date: " + currentDate);

		// Check if the check-in date is before the current date
		if (ci.isBefore(currentDate)) {
			showAlertError("Check-in date cannot be before the current date.");
			return;
		}

		// Check if the check-out date is before the current date
		if (co.isBefore(currentDate)) {
			showAlertError("Check-out date cannot be before the current date.");
			return;
		}

		// Checking Fields
		if (noOfGuestTF.getText().isEmpty() || noOfDaysTF.getText().isEmpty() || rateTF.getText().isEmpty()
				|| noOfRoomNeededTF.getText().isEmpty()
				|| roomTypeComboBox.getSelectionModel().getSelectedItem() == null) {
			showAlertError("Fields Value is missing");
		}

		noOfGuest = Integer.parseInt(noOfGuestTF.getText());
		roomType = roomTypeComboBox.getValue();
		noOfDays = Integer.parseInt(noOfDaysTF.getText());
		ratePerDay = Double.parseDouble(rateTF.getText());
		noOfRoomNeeded = Integer.parseInt(noOfRoomNeededTF.getText());

		// Counting Already Booked Number of Rooms
		int singleRoom = da.countRoomsByType("Single Room");
		int doubleRoom = da.countRoomsByType("Double Room");
		int deluxRoom = da.countRoomsByType("Delux Room");
		int pentHouse = da.countRoomsByType("Pent House");

		// Creating Room Object
		if (roomType == "Single Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.SINGLE_ROOM - singleRoom)) {

				sr = new SingleRoom(roomID, ratePerDay, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Single Rooms Available are: " + (AvailableRoomsController.SINGLE_ROOM - singleRoom));
				return;
			}
		}
		if (roomType == "Double Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.DOUBLE_ROOM - doubleRoom)) {
				sr = new DoubleRoom(roomID, ratePerDay, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Double Rooms Available are: " + (AvailableRoomsController.DOUBLE_ROOM - doubleRoom));
				return;
			}

		}
		if (roomType == "Delux Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.DELUX_ROOM - deluxRoom)) {
				sr = new DeluxRoom(roomID, ratePerDay, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Delux Rooms Available are: " + (AvailableRoomsController.DELUX_ROOM - deluxRoom));
				return;
			}

		}

		if (roomType == "Pent House") {
			if (noOfRoomNeeded <= (AvailableRoomsController.PENT_HOUSE - pentHouse)) {
				sr = new PentHouse(roomID, ratePerDay, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Pent House Available are: " + (AvailableRoomsController.PENT_HOUSE - pentHouse));
				return;
			}
		}

		// Room Database
		boolean res = da.insertRoom(sr);
		if (res) {
			System.out.println("Room Row Added");
		} else {
			System.out.println("Room Row Failed ");
		}

		// Reservation
		Date bookDate = new Date();
		Reservation r = new Reservation(bookID, bookDate, ci, co);
		bookID++;

		// Reservation Database
		boolean res1 = da.insertReservation(r);
		if (res1) {
			System.out.println("Reservation Row Added");
		} else {
			System.out.println("Reservation Row Failed ");
		}

		// Move to Guest Details Window
		setScene(event, "/views/GuestDetails.fxml");
	}

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		roomTypeComboBox
				.setItems(FXCollections.observableArrayList("Single Room", "Double Room", "Delux Room", "Pent House"));

		noOfGuestTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				noOfGuestTF.setText(oldValue);
			}
			updateRoomsAvailLabel();
		});

		// Days
		noOfDaysTF.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9]")) {
				noOfDaysTF.setText("");
			}
		});

		// Rate
		rateTF.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9.]")) {
				rateTF.setText("");
			}
		});

		noOfRoomNeededTF.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9]")) {
				noOfRoomNeededTF.setText("");
			}
		});

		updateRoomsAvailLabel();

	}

	@FXML
	void getCheckInDate(ActionEvent event) {
		ci = checkInDate.getValue();

	}

	@FXML
	void getCheckOutDate(ActionEvent event) {
		co = checkoutDate.getValue();

	}

	private void showAlertError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	void mainMenuBtn(ActionEvent event) throws IOException {
		setScene(event, "/views/HomeScreen.fxml");

	}

	private void updateRoomsAvailLabel() {
		int numberOfGuests = 0;
		try {
			numberOfGuests = Integer.parseInt(noOfGuestTF.getText());
		} catch (NumberFormatException ignored) {
		}

		if (numberOfGuests == 1 || numberOfGuests == 2) {
			roomsAvailLB.setText("Single Room");
		} else if (numberOfGuests > 2 && numberOfGuests < 4) {
			roomsAvailLB.setText("Double room or two single rooms");
		} else if (numberOfGuests >= 4) {
			roomsAvailLB.setText("Multiple Double or combination of Double and single rooms\n" + "Try Pent House");
		} else {
			roomsAvailLB.setText("");
		}
	}

}
