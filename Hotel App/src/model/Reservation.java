package model;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
	// Attributes
	private int bookID;
	private Date bookDate;
	private LocalDate checkIn;
	private LocalDate checkOut;

	// Constructor
	public Reservation(int bookID, Date bookDate, LocalDate checkIn, LocalDate checkOut) {
		this.bookID = bookID;
		this.bookDate = bookDate;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	// Getters and Setters
	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	// toString method to display reservation information
	@Override
	public String toString() {
		return "Booking ID: " + bookID + "\nBooking Date: " + bookDate + "\nCheck-in Date: " + checkIn
				+ "\nCheck-out Date: " + checkOut;
	}
}
