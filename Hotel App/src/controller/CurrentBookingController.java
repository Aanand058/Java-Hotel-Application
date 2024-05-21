package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CurrentBookings;

public class CurrentBookingController implements Initializable {

	@FXML
	private Label currBLB;

	@FXML
	private TableColumn<CurrentBookings, Integer> tableBIDCol;

	@FXML
	private TableColumn<CurrentBookings, String> tableCusNameCol;

	@FXML
	private TableColumn<CurrentBookings, Integer> tableNoOfDaysCol;

	@FXML
	private TableColumn<CurrentBookings, Integer> tableNoOfRoomsCol;

	@FXML
	private TableColumn<CurrentBookings, String> tableRoomTypeCol;

	@FXML
	private TableView<CurrentBookings> tableV;

	private DatabaseAccess da;

	public CurrentBookingController() {
		da = new DatabaseAccess();
	}

	// All Bookings
	List<CurrentBookings> currBookings = new ArrayList<>();

	@FXML
	void mainMenuBtn(ActionEvent event) throws IOException {
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

		try {
			List<CurrentBookings> currBookings = da.getCurrentBookings();

			int size = currBookings.size();
			currBLB.setText(String.valueOf(size));

			ObservableList<CurrentBookings> list = FXCollections.observableArrayList(currBookings);

			// Table View
			tableBIDCol.setCellValueFactory(new PropertyValueFactory<CurrentBookings, Integer>("currBID"));
			tableCusNameCol.setCellValueFactory(new PropertyValueFactory<CurrentBookings, String>("cusName"));
			tableRoomTypeCol.setCellValueFactory(new PropertyValueFactory<CurrentBookings, String>("roomType"));
			tableNoOfRoomsCol.setCellValueFactory(new PropertyValueFactory<CurrentBookings, Integer>("noOfRooms"));
			tableNoOfDaysCol.setCellValueFactory(new PropertyValueFactory<CurrentBookings, Integer>("noOfDays"));

			tableV.setItems(list);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
