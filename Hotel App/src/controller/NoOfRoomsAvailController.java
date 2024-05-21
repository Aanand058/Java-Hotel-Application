package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.DatabaseAccess;
import javafx.application.Platform;
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
import javafx.stage.Stage;
import model.AvailableRooms;

public class NoOfRoomsAvailController implements Initializable {
	@FXML
	private TableColumn<AvailableRooms, Integer> noOfRoomsCol;

	@FXML
	private Label noOfRoomsLB;

	@FXML
	private TableColumn<AvailableRooms, String> roomTypeCol;

	@FXML
	private TableView<AvailableRooms> tableV;

	private DatabaseAccess da;

	public NoOfRoomsAvailController() {
		da = new DatabaseAccess();
		availableRoomsList = FXCollections.observableArrayList();
	}

	// CONST VALUE FOR ROOM (FIXING NO OF ROOMS IN HOTEL)
	public static final int SINGLE_ROOM = 50;
	public static final int DOUBLE_ROOM = 50;
	public static final int DELUX_ROOM = 20;
	public static final int PENT_HOUSE = 7;

	public static final int TOTAL_ROOMS = SINGLE_ROOM + DOUBLE_ROOM + DELUX_ROOM + PENT_HOUSE;

	private ObservableList<AvailableRooms> availableRoomsList;

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@FXML
	void handleBooking(ActionEvent event) throws IOException {
		setScene(event, "/views/BookAsGeustOnly.fxml");
	}

	@FXML
	void handleExitBtn(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			int singleRoom = da.countRoomsByType("Single Room");
			int doubleRoom = da.countRoomsByType("Double Room");
			int deluxRoom = da.countRoomsByType("Delux Room");
			int pentHouse = da.countRoomsByType("Pent House");

			int totalRoomsBooked = singleRoom + doubleRoom + deluxRoom + pentHouse;
			int totalAvailableRooms = TOTAL_ROOMS - totalRoomsBooked;

			// Label
			noOfRoomsLB.setText(String.valueOf(totalAvailableRooms));

			// Table
			availableRoomsList.add(new AvailableRooms("Single Room", SINGLE_ROOM - singleRoom));
			availableRoomsList.add(new AvailableRooms("Double Room", DOUBLE_ROOM - doubleRoom));
			availableRoomsList.add(new AvailableRooms("Delux Room", DELUX_ROOM - deluxRoom));
			availableRoomsList.add(new AvailableRooms("Pent House", PENT_HOUSE - pentHouse));

			roomTypeCol.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
			noOfRoomsCol.setCellValueFactory(cellData -> cellData.getValue().noOfRoomsProperty().asObject());

			tableV.setItems(availableRoomsList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
