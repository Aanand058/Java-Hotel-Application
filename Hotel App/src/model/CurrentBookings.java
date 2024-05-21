package model;

public class CurrentBookings {

	private int currBID;
	private String cusName;
	private String roomType;
	private int noOfRooms;
	private int noOfDays;

	public CurrentBookings(int currBID, String cusName, String roomType, int noOfRooms, int noOfDays) {
		super();
		this.currBID = currBID;
		this.cusName = cusName;
		this.roomType = roomType;
		this.noOfRooms = noOfRooms;
		this.noOfDays = noOfDays;
	}

	public int getCurrBID() {
		return currBID;
	}

	public void setCurrBID(int currBID) {
		this.currBID = currBID;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

}
