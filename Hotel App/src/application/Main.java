


package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/HomeScreen.fxml"));
			Scene scene = new Scene(root, 800, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			// Server Connection
			Socket socket = new Socket("localhost", 6000);
			System.out.println("Connected to server");

			// Send data to the server
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("Hello from Hotel ABC Client!");

			// Receive data from the server
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = in.readLine();
			System.out.println("Server response: " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	
	
	
	
	
	
	// COMMON ID FOR GUEST,ROOM,RESERVATION,BOOKING
	public static int guestID = 1;

	public static int getGuestId() {
		return guestID;
	}

	// SEARCH FOR BOOKING ID FOR BILLING PURPOSE
	public static int searchID = 0;

	public static int getSearchId() {
		return searchID;
	}

}
