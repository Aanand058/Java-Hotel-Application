package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.BillService;

public class CheckoutController implements Initializable {

	@FXML
	private Label dDate;

	@FXML
	private Label rDays;

	@FXML
	private Label rDiscount;

	@FXML
	private Label rEmail;

	@FXML
	private Label rID;

	@FXML
	private Label rName;

	@FXML
	private Label rNo;

	@FXML
	private Label rRate;

	@FXML
	private Label rSubtotal;

	@FXML
	private Label rTax;

	@FXML
	private Label rTotal;

	@FXML
	private Label rType;

	private DatabaseAccess da;

	public CheckoutController() {
		da = new DatabaseAccess();
	}

	@FXML
	void exitBtn(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void goToMenu(ActionEvent event) throws IOException {
		setScene(event, "/views/AdminMenu.fxml");

	}

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Generates random bill iD
		Random random = new Random();
		int rid = random.nextInt(90000) + 10000;
		rID.setText(String.valueOf(rid));

		// Current Date
		dDate.setText(LocalDate.now().toString());

		try {

			BillService checkOut = da.getCheckoutData(Main.getSearchId());

			double discountP = da.getDiscountPer(Main.getSearchId());

			// Setting LABELS
			rName.setText(checkOut.getGuestName());
			rEmail.setText(checkOut.getEmail());
			rType.setText(checkOut.getRoomType());
			rNo.setText(String.valueOf(checkOut.getNoOfRoomsBooked()));
			rRate.setText(String.valueOf(checkOut.getRate()));
			rDays.setText(String.valueOf(checkOut.getStayedDays()));

			// Calculation
			// TOTAL CALCULATION
			double rate = Double.parseDouble(rRate.getText());
			int numRooms = Integer.parseInt(rNo.getText());
			int sDays = Integer.parseInt(rDays.getText());
			double subtotal = 0;
			double discountAmount = 0;
			double total1 = 0;
			double tax = 13;
			double total = 0;
			double taxA = 0;

			subtotal = rate * numRooms * sDays;
			discountAmount = subtotal * (discountP / 100);
			total1 = subtotal - discountAmount;
			taxA = total1 * (tax / 100);
			total = taxA + total1;

			rSubtotal.setText(String.valueOf(subtotal));
			rDiscount.setText(String.valueOf(discountP));
			rTax.setText(String.valueOf(taxA));
			rTotal.setText(String.valueOf(total));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
