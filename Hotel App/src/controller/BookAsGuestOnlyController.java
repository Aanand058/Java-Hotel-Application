package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DeluxRoom;
import model.DoubleRoom;
import model.Guest;
import model.PentHouse;
import model.Reservation;
import model.Room;
import model.SingleRoom;

public class BookAsGuestOnlyController implements Initializable {

	@FXML
	private TextField addressTextField;

	@FXML
	private DatePicker checkInDate;

	@FXML
	private DatePicker checkoutDate;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private Button mainMenuBtn;

	@FXML
	private TextField noOfDaysTF;

	@FXML
	private TextField noOfGuestTF;

	@FXML
	private TextField noOfRoomNeededTF;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ComboBox<String> roomTypeComboBox;

	@FXML
	private Label roomsAvailLB;

	@FXML
	private Label roomRateLB;

	private DatabaseAccess da;

	public BookAsGuestOnlyController() {
		da = new DatabaseAccess();
	}

	// Local Variables
	int noOfGuest = 0;
	String roomType = "";
	int noOfDays = 0;
	int noOfRoomNeeded = 0;

	LocalDate ci;
	LocalDate co;

	LocalDate currentDate = LocalDate.now();

	public int roomID = 1;
	public int bookID = 1;

	// Room Object
	Room sr;

	// FIXING THE RATE FOR ROOMS IF BOOKING AS GUEST
	final double singleRoomRate = 200.00;
	final double doubleRoomRate = 350.00;
	final double deluxRoomRate = 500.00;
	final double pentHouseRate = 1000.00;

	@FXML
	private ComboBox<String> titleComboBox;

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@FXML
	void confirmRoom(ActionEvent event) throws SQLException, IOException {

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
		if (noOfGuestTF.getText().isEmpty() || noOfDaysTF.getText().isEmpty() || noOfRoomNeededTF.getText().isEmpty()
				|| roomTypeComboBox.getSelectionModel().getSelectedItem() == null) {
			showAlertError("Fields Value is missing");
		}

		noOfGuest = Integer.parseInt(noOfGuestTF.getText());
		roomType = roomTypeComboBox.getValue();
		noOfDays = Integer.parseInt(noOfDaysTF.getText());
		noOfRoomNeeded = Integer.parseInt(noOfRoomNeededTF.getText());

		// Counting Already Booked Number of Rooms
		int singleRoom = da.countRoomsByType("Single Room");
		int doubleRoom = da.countRoomsByType("Double Room");
		int deluxRoom = da.countRoomsByType("Delux Room");
		int pentHouse = da.countRoomsByType("Pent House");

		// Creating Room Object
		if (roomType == "Single Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.SINGLE_ROOM - singleRoom)) {

				sr = new SingleRoom(roomID, singleRoomRate, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Single Rooms Available are: " + (AvailableRoomsController.SINGLE_ROOM - singleRoom));
				return;
			}
		}
		if (roomType == "Double Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.DOUBLE_ROOM - doubleRoom)) {
				sr = new DoubleRoom(roomID, doubleRoomRate, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError(
						"No of Double Rooms Available are: " + (AvailableRoomsController.DOUBLE_ROOM - doubleRoom));
				return;
			}

		}
		if (roomType == "Delux Room") {
			if (noOfRoomNeeded <= (AvailableRoomsController.DELUX_ROOM - deluxRoom)) {
				sr = new DeluxRoom(roomID, deluxRoomRate, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError("No of Delux Rooms Available are: " + (AvailableRoomsController.DELUX_ROOM - deluxRoom));
				return;
			}

		}

		if (roomType == "Pent House") {
			if (noOfRoomNeeded <= (AvailableRoomsController.PENT_HOUSE - pentHouse)) {
				sr = new PentHouse(roomID, pentHouseRate, noOfRoomNeeded);
				roomID++;
			} else {
				showAlertError("No of Pent House Available are: " + (AvailableRoomsController.PENT_HOUSE - pentHouse));
				return;
			}
		}

		// Reservation
		Date bookDate = new Date();
		Reservation r = new Reservation(bookID, bookDate, ci, co);
		bookID++;


		// Guest Details
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

				// Room Database
				boolean res = da.insertRoom(sr);
				if (res) {
					System.out.println("Room Row Added");
				} else {
					System.out.println("Room Row Failed ");
				}

				// Reservation Database
				boolean res1 = da.insertReservation(r);
				if (res1) {
					System.out.println("Reservation Row Added");
				} else {
					System.out.println("Reservation Row Failed ");
				}

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
					setScene(event, "/views/HomeScreen.fxml");
				} else {
					showAlert(Alert.AlertType.ERROR, "Failed", "Plese Check All fields and Try Agian");
				}

			}

		}

	}

	private void showAlertError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	void getCheckInDate(ActionEvent event) {
		ci = checkInDate.getValue();
	}

	@FXML
	void getCheckOutDate(ActionEvent event) {
		co = checkoutDate.getValue();
	}

	@FXML
	void mainMenuBtn(ActionEvent event) throws IOException {
		setScene(event, "/views/HomeScreen.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		roomTypeComboBox
				.setItems(FXCollections.observableArrayList("Single Room", "Double Room", "Delux Room", "Pent House"));

		// Add listener to the combo box selection
		roomTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// Update room rate label based on the selected room type
			switch (newValue) {
			case "Single Room":
				roomRateLB.setText("$" + singleRoomRate);
				break;
			case "Double Room":
				roomRateLB.setText("$" + doubleRoomRate);
				break;
			case "Delux Room":
				roomRateLB.setText("$" + deluxRoomRate);
				break;
			case "Pent House":
				roomRateLB.setText("$" + pentHouseRate);
				break;
			default:
				roomRateLB.setText("N/A");
				break;
			}
		});

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

		noOfRoomNeededTF.setOnKeyTyped(e -> {
			String input = e.getCharacter();
			if (!input.matches("[0-9]")) {
				noOfRoomNeededTF.setText("");
			}
		});

		updateRoomsAvailLabel();

		// Guest title
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

	private static void showAlert(Alert.AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
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
