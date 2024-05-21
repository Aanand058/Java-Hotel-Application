package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import database.DatabaseAccess;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BillService;
import model.Room;

public class BillServiceController implements Initializable {

	@FXML
	private TextField bookingIDTF;

	@FXML
	private TextField discountTF;

	@FXML
	private TextField guestNameTF;

	@FXML
	private TextField noOfRoomsTF;

	@FXML
	private TextField rateTF;

	@FXML
	private TextArea totalTA;

	@FXML
	private TextField typeRoomTF;

	private DatabaseAccess da;

	public BillServiceController() {
		da = new DatabaseAccess();
	}

	// MAX DISCOUNT
	double MAX_DISCOUNT = 25;

	// NO OF DAYS STAYED
	int noOfStayedDays = 0;

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@FXML
	void exitBtn(ActionEvent event) throws IOException {
		setScene(event, "/views/AdminMenu.fxml");
	}

	@FXML
	void totalBtn(ActionEvent event) throws SQLException, IOException {

		if (discountTF.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Failed", "Plese Valid Discount %");
		} else {
			double d = Double.parseDouble(discountTF.getText());
			if (d > MAX_DISCOUNT) {
				showAlert(Alert.AlertType.ERROR, "Error", "MAX DISCOUNT ONLY UPTO 25%");
				// Applies 25% discount if > 25% as input
				discountTF.setText("25");
				d = 25;

			} else if (d == 0) {
				discountTF.setText("0");
				d = 0;
			}

			// TOTAL CALCULATION
			double rate = Double.parseDouble(rateTF.getText());
			int numRooms = Integer.parseInt(noOfRoomsTF.getText());
			double subtotal = 0;
			double discountAmount = 0;
			double tax = 13;
			double total = 0;
			double taxA = 0;

			subtotal = rate * numRooms * noOfStayedDays;
			discountAmount = subtotal * (d / 100);
			subtotal = subtotal - discountAmount;
			taxA = subtotal * (tax / 100);
			total = taxA + subtotal;

			totalTA.setText(String.valueOf(total));

			// UPDATE TO RATES TO DB
			boolean res = da.updateBookingID(Main.getSearchId(), d, total);
			if (res) {

				System.out.println("Discount and Total Updated in Booking Table");
				setScene(event, "/views/Checkout.fxml");
			} else {
				System.out.println("Failed updating discount and total");
			}
		}

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

		try {

			BillService bill = da.getBillServiceData(Main.getSearchId());

			// Get number of days the guest stayed
			noOfStayedDays = bill.getStayedDays();

			// Discount Validation
			discountTF.setOnKeyTyped(e -> {
				String input = e.getCharacter();
				if (!input.matches("[0-9.]")) {
					discountTF.setText("");
				}
			});

			// Setting Field Values
			bookingIDTF.setText(String.valueOf(Main.getSearchId()));
			guestNameTF.setText(bill.getGuestName());
			noOfRoomsTF.setText(String.valueOf(bill.getNoOfRoomsBooked()));
			typeRoomTF.setText(bill.getRoomType());
			rateTF.setText(String.valueOf(bill.getRate()));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
