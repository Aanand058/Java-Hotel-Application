package model;

public class Room {
	// Attributes
	private int roomID;
	private String roomType;
	private double rate;
	private int noOfRoomBooked;

	// Constructor
	public Room(int roomID, String roomType, double rate, int numRoom) {
		this.roomID = roomID;
		this.roomType = roomType;
		this.rate = rate;
		this.noOfRoomBooked = numRoom;
	}

	public int getNoOfRoomBooked() {
		return noOfRoomBooked;
	}

	public void setNoOfRoomBooked(int noOfRoomBooked) {
		this.noOfRoomBooked = noOfRoomBooked;
	}

	// Getters and Setters
	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	// toString method to display room information
	@Override
	public String toString() {
		return "Room ID: " + roomID + "\nRoom Type: " + roomType + "\nRate: $" + rate;
	}
}