/**********************************************
Project - Hotel ABC
Course: APD545 - 5th Semester
Last Name: Aman
First Name: Aanand
ID: 166125211
Section: ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 2024/04/13
**********************************************/

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableView;
import model.AvailableRooms;
import model.BillService;
import model.CurrentBookings;
import model.Guest;
import model.Reservation;
import model.Room;

public class DatabaseAccess {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/apd";
	private static final String USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL, isAdmin BOOLEAN NOT NULL)";
	private static final String INSERT_QRY = "INSERT INTO users (username, password, isAdmin) VALUES (?, ?, ?)";
	private static final String SELECT_QRY = "SELECT * FROM users WHERE username=? AND password=? AND isAdmin =true";

	private static final String GUEST_TABLE = "CREATE TABLE IF NOT EXISTS Guest (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(50), first_name VARCHAR(50), last_name VARCHAR(50), address VARCHAR(100), phone VARCHAR(100), email VARCHAR(100))";
	private static final String GUEST_QRY = "INSERT INTO Guest (title, first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?,?)";
	private static final String GET_GUESTFULLNAME_QRY = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM Guest WHERE id = ?";

	private static final String ROOM_TABLE = "CREATE TABLE IF NOT EXISTS Room(id INT AUTO_INCREMENT PRIMARY KEY, roomType VARCHAR(50), rate DOUBLE, noOfRoom INT)";
	private static final String ROOM_QRY = "INSERT INTO Room(roomType,rate, noOfRoom) VALUES (?,?,?)";
	private static final String GET_ROOM_QRY = "SELECT roomType, rate, noOfRoom FROM Room WHERE id = ?";

	private static final String RES_TABLE = "CREATE TABLE IF NOT EXISTS Reservations (id INT AUTO_INCREMENT PRIMARY KEY, bookDate DATE, checkIn DATE, checkOut DATE)";
	private static final String RES_QRY = "INSERT INTO Reservations(bookDate,checkIn,checkOut) VALUES (?,?,?)";


	private static final String BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS Booking (id INT AUTO_INCREMENT PRIMARY KEY, guest_title VARCHAR(50), guest_first_name VARCHAR(50), guest_last_name VARCHAR(50), guest_address VARCHAR(100), guest_phone VARCHAR(100), guest_email VARCHAR(100), room_roomType VARCHAR(50), room_rate DOUBLE, room_noOfRoom INT, reservation_bookDate DATE, reservation_checkIn DATE, reservation_checkOut DATE, isBooked BOOLEAN, stayedDays INT, discount DOUBLE, total DOUBLE)";
	private static final String INSERT_BOOKING_QRY = "INSERT INTO Booking (guest_title, guest_first_name, guest_last_name, guest_address, guest_phone, guest_email, room_roomType, room_rate, room_noOfRoom, reservation_bookDate, reservation_checkIn, reservation_checkOut, isBooked,stayedDays) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";

	// Insert Login UserPass
	public void insertRecord(String username, String pass, boolean isAdmin) throws SQLException {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	         Statement stmt = conn.createStatement()) {

	        // Create users table if it does not exist
	       // stmt.executeUpdate(USERS_TABLE);

	        // Prepare the insert statement
	        PreparedStatement ps = conn.prepareStatement(INSERT_QRY);
	        ps.setString(1, username);
	        ps.setString(2, pass);
	        ps.setBoolean(3, isAdmin);

	        // Execute the insert statement
	        ps.executeUpdate();
	    }
	}


	// Login Validation
	public boolean validation(String email, String pass) throws SQLException {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(SELECT_QRY)) {

			ps.setString(1, email);
			ps.setString(2, pass);
		

			ResultSet rs = ps.executeQuery();
			while (rs.next())
				return true;
		}
		return false;
	}

	// Guest Table
	public boolean insertGuest(Guest g) throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement ct = conn.prepareStatement(GUEST_TABLE);
			ct.executeUpdate();

			PreparedStatement insertStatement = conn.prepareStatement(GUEST_QRY);
			insertStatement.setString(1, g.getTitle());
			insertStatement.setString(2, g.getFirstName());
			insertStatement.setString(3, g.getLastName());
			insertStatement.setString(4, g.getAddress());
			insertStatement.setString(5, Long.toString(g.getPhone()));
			insertStatement.setString(6, g.getEmail());
			insertStatement.executeUpdate();

			System.out.println("Booking Confirmed\n Data Inseretd");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	//INSERT GUEST DATA
	public int insertGuestID(Guest g) throws SQLException {
		ResultSet generatedKeys = null;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement ct = conn.prepareStatement(GUEST_TABLE);
			ct.executeUpdate();

			PreparedStatement insertStatement = conn.prepareStatement(GUEST_QRY, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, g.getTitle());
			insertStatement.setString(2, g.getFirstName());
			insertStatement.setString(3, g.getLastName());
			insertStatement.setString(4, g.getAddress());
			insertStatement.setString(5, Long.toString(g.getPhone()));
			insertStatement.setString(6, g.getEmail());
			insertStatement.executeUpdate();

			// Retrieve the generated keys (which includes the ID of the newly added guest)
			generatedKeys = insertStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				int guestId = generatedKeys.getInt(1);
				System.out.println("Booking Confirmed\nData Inserted. Guest ID: " + guestId);
				return guestId;
			} else {
				System.out.println("Failed to retrieve the ID of the newly added guest");
				return -1; // Return -1 to indicate failure
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return 0;
	}

	// GET GUEST FULL NAME
	public String getGuestName(int id) throws SQLException {
		String fName = null;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement(GET_GUESTFULLNAME_QRY);
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			// Check if a row was returned
			if (resultSet.next()) {

				String fullName = resultSet.getString("full_name");
				fName = fullName;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return fName;
	}

	// INSERT Room Table
	public boolean insertRoom(Room room) throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement ct = conn.prepareStatement(ROOM_TABLE);
			ct.executeUpdate();

			PreparedStatement insertStatement = conn.prepareStatement(ROOM_QRY);
			insertStatement.setString(1, room.getRoomType());
			insertStatement.setDouble(2, room.getRate());
			insertStatement.setInt(3, room.getNoOfRoomBooked());

			insertStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	// GET Room Table
	public Room getRoom(int id) throws SQLException {
		Room room = null;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement(GET_ROOM_QRY);
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {

				String roomType = resultSet.getString("roomType");
				double rate = resultSet.getDouble("rate");
				int noOfRoom = resultSet.getInt("noOfRoom");
				room = new Room(id, roomType, rate, noOfRoom);
			}
			System.out.println("Get ROOM ROW");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return room;
	}

	// INSERT INTO Reservation Table
	public boolean insertReservation(Reservation reservation) throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement ct = conn.prepareStatement(RES_TABLE);
			ct.executeUpdate();

			PreparedStatement insertStatement = conn.prepareStatement(RES_QRY);
			insertStatement.setDate(1, new java.sql.Date(reservation.getBookDate().getTime()));
			insertStatement.setDate(2, java.sql.Date.valueOf(reservation.getCheckIn()));
			insertStatement.setDate(3, java.sql.Date.valueOf(reservation.getCheckOut()));

			insertStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	// GET STAYED DYAS
	public int getStayedDays(int id) throws SQLException {
		int days = 0;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement("SELECT DATEDIFF(checkOut, checkIn) AS NumberOfDays FROM Reservations WHERE id = ?");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			
			if (resultSet.next()) {
				days = resultSet.getInt("NumberOfDays");
				System.out.println("NO. of Days: " + days);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return days;
	}

	// BOOKING INSERTION
	public boolean insertBooking(int id) throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement ct = conn.prepareStatement(BOOKING_TABLE);
			ct.executeUpdate();

			// Get GUEST DATA
			PreparedStatement guestStatement = conn.prepareStatement("SELECT * FROM Guest WHERE id = ?");
			guestStatement.setInt(1, id);
			ResultSet guestResult = guestStatement.executeQuery();
			guestResult.next();
			String guestTitle = guestResult.getString("title");
			String guestFirstName = guestResult.getString("first_name");
			String guestLastName = guestResult.getString("last_name");
			String guestAddress = guestResult.getString("address");
			String guestPhone = guestResult.getString("phone");
			String guestEmail = guestResult.getString("email");

			// GET ROOM DATA
			PreparedStatement roomStatement = conn.prepareStatement("SELECT * FROM Room WHERE id = ?");
			roomStatement.setInt(1, id);
			ResultSet roomResult = roomStatement.executeQuery();
			roomResult.next();
			String roomType = roomResult.getString("roomType");
			double roomRate = roomResult.getDouble("rate");
			int roomNoOfRoom = roomResult.getInt("noOfRoom");

			// GET RESERVATION DATA
			PreparedStatement reservationStatement = conn.prepareStatement("SELECT * FROM Reservations WHERE id = ?");
			reservationStatement.setInt(1, id);
			ResultSet reservationResult = reservationStatement.executeQuery();
			reservationResult.next();
			java.sql.Date reservationBookDate = reservationResult.getDate("bookDate");
			java.sql.Date reservationCheckIn = reservationResult.getDate("checkIn");
			java.sql.Date reservationCheckOut = reservationResult.getDate("checkOut");

			// GET STAYED DAYS
			int sdays = 0;
			PreparedStatement selectStatement = conn.prepareStatement("SELECT DATEDIFF(checkOut, checkIn) AS NumberOfDays FROM Reservations WHERE id = ?");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				sdays = resultSet.getInt("NumberOfDays");
			}

			PreparedStatement insertStatement = conn.prepareStatement(INSERT_BOOKING_QRY);
			insertStatement.setString(1, guestTitle);
			insertStatement.setString(2, guestFirstName);
			insertStatement.setString(3, guestLastName);
			insertStatement.setString(4, guestAddress);
			insertStatement.setString(5, guestPhone);
			insertStatement.setString(6, guestEmail);
			insertStatement.setString(7, roomType);
			insertStatement.setDouble(8, roomRate);
			insertStatement.setInt(9, roomNoOfRoom);
			insertStatement.setDate(10, reservationBookDate);
			insertStatement.setDate(11, reservationCheckIn);
			insertStatement.setDate(12, reservationCheckOut);
			insertStatement.setBoolean(13, true);
			insertStatement.setInt(14, sdays);

			insertStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	// GET BILL SERVICE DATA (OPTION 2)
	public BillService getBillServiceData(int id) throws SQLException {
		BillService billData = null;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement(
					"SELECT CONCAT(guest_first_name, ' ', guest_last_name) AS fullName,guest_email, room_noOfRoom, room_roomType, stayedDays, room_rate FROM Booking WHERE id=? AND isbooked = true");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				String fname = resultSet.getString("fullName");
				int noOfRoom = resultSet.getInt("room_noOfRoom");
				String roomType = resultSet.getString("room_roomType");
				int stayedDays = resultSet.getInt("stayedDays");
				double rate = resultSet.getDouble("room_rate");
				String email = resultSet.getString("guest_email");

				System.out.println("Full Name: " + fname);
				System.out.println("Number of Rooms: " + noOfRoom);
				System.out.println("Room Type: " + roomType);
				System.out.println("Stayed Days: " + stayedDays);
				System.out.println("Rate: " + rate);

				// Create the BillService object
				billData = new BillService(fname, noOfRoom, roomType, rate, stayedDays, email);
			} else {

				return null;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billData;
	}

	// CHECK IS BOOKED OR NOT
	public boolean checkBooked(int id) throws SQLException {
		boolean isBooked = false;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn
					.prepareStatement("SELECT id FROM Booking WHERE id=? AND isbooked = true");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				isBooked = true;
			} else {
				System.out.println("Guest Already Checked Out.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isBooked;
	}

	// GET CHECKOUT DATA 
	public BillService getCheckoutData(int id) throws SQLException {
		BillService billData = null;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement(
					"SELECT CONCAT(guest_first_name, ' ', guest_last_name) AS fullName,guest_email, room_noOfRoom, room_roomType, stayedDays, room_rate FROM Booking WHERE id=?");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				String fname = resultSet.getString("fullName");
				int noOfRoom = resultSet.getInt("room_noOfRoom");
				String roomType = resultSet.getString("room_roomType");
				int stayedDays = resultSet.getInt("stayedDays");
				double rate = resultSet.getDouble("room_rate");
				String email = resultSet.getString("guest_email");

				System.out.println("Full Name: " + fname);
				System.out.println("Number of Rooms: " + noOfRoom);
				System.out.println("Room Type: " + roomType);
				System.out.println("Stayed Days: " + stayedDays);
				System.out.println("Rate: " + rate);

				// Create the BillService object
				billData = new BillService(fname, noOfRoom, roomType, rate, stayedDays, email);
			} else {
				System.out.println("Guest Already Checked Out.");
				return null;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billData;
	}

	// GET CURRENT BOOKINGS DATA (OPTION 3)
	public List<CurrentBookings> getCurrentBookings() throws SQLException {
		List<CurrentBookings> bookingList = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement(
					"SELECT id, CONCAT(guest_first_name, ' ', guest_last_name) AS fullName, room_roomType, room_noOfRoom, stayedDays FROM Booking WHERE isbooked = true");

			ResultSet resultSet = selectStatement.executeQuery();

			
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No Current Bookings");
				return bookingList; 
			}

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fullName = resultSet.getString("fullName");
				String roomType = resultSet.getString("room_roomType");
				int noOfDays = resultSet.getInt("room_noOfRoom");
				int stayedDays = resultSet.getInt("stayedDays");

				CurrentBookings booking = new CurrentBookings(id, fullName, roomType, noOfDays, stayedDays);
				bookingList.add(booking);
			}

		} catch (SQLException e) {
	        // Check if the SQLException is due to table not existing
	        if (e.getSQLState().equals("42S02")) {
	            System.out.println("Table does not exist.");
	        } else {
	            e.printStackTrace();
	        }
	    }
		return bookingList;
	}

	// UPDATE DISCOUNT AND TOTAL IN BOOKING (CHECKOUT)
	public boolean updateBookingID(int id, double discount, double total) throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement updateStatement = conn.prepareStatement(
					"UPDATE Booking SET discount = ?, total = ?, isBooked = false WHERE id = ?");
			updateStatement.setDouble(1, discount);
			updateStatement.setDouble(2, total);
			updateStatement.setInt(3, id);

			int rowsAffected = updateStatement.executeUpdate();

			
			if (rowsAffected > 0) {
				System.out.println("Booking with ID " + id + " updated successfully.\n total=" + total);
				return true;
			} else {
				System.out.println("No booking found with ID " + id + ".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// GET DISCOUNT PERCENTAGE
	public double getDiscountPer(int id) throws SQLException {
		double dis = 0;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			PreparedStatement selectStatement = conn.prepareStatement("SELECT discount FROM Booking WHERE id=?");
			selectStatement.setInt(1, id);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				dis = resultSet.getDouble("discount");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dis;
	}

	// GET NO OF ROOMS
	public int countRoomsByType(String roomType) throws SQLException {
		int count = 0;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			PreparedStatement selectStatement = conn.prepareStatement(
					"SELECT SUM(room_noOfRoom) AS count FROM Booking WHERE room_roomType =? AND isbooked = true");

			selectStatement.setString(1, roomType);

			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt("count");
			}
		} catch (SQLException e) {
	       
	        if (e.getSQLState().equals("42S02")) {
	            System.out.println("Table does not exist.");
	           
	        } else {
	            e.printStackTrace();
	        }
	    }
		return count;
	}

}
