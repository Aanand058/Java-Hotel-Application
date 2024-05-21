package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import database.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class LoginController implements Initializable {

	private DatabaseAccess da;

	public LoginController() {
		da = new DatabaseAccess();
	}

	@FXML
	private TextField idTF;

	@FXML
    private PasswordField passwordF;


	@FXML
	void exitBtn(ActionEvent event) throws IOException {

		setScene(event, "/views/HomeScreen.fxml");
	}

	@FXML
	void loginBtn(ActionEvent event) throws IOException, SQLException {


		if (idTF.getText().isEmpty() && passwordF.getText().isEmpty()) {
			System.out.println("EMpty Field");
		}

		String email = idTF.getText();
		String password = passwordF.getText();

		boolean res = da.validation(email, password);

		if (res) {
			setScene(event, "/views/AdminMenu.fxml");
		} else {
			showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.\n Please Login By Admin.");
			System.out.println(" Login Failed ");
		}

	}

	private void showAlert(Alert.AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void setScene(ActionEvent event, String s) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(s));
		var scene = new Scene(parent);
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		

	}
}
