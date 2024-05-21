package model;

public class BillService {

	private String guestName;
	private int noOfRoomsBooked;
	private String roomType;
	private double rate;
	private int stayedDays;
	private String email;
	
	 // No-argument constructor with default values
    public BillService() {
        this.guestName = "";
        this.noOfRoomsBooked = 0;
        this.roomType = "";
        this.rate = 0.0;
        this.stayedDays = 0;
        this.email = "";
    }

	public BillService(String guestName, int noOfRoomsBooked, String roomType, double rate, int stay, String emailR) {
		super();
		this.guestName = guestName;
		this.noOfRoomsBooked = noOfRoomsBooked;
		this.roomType = roomType;
		this.rate = rate;
		this.stayedDays = stay;
		this.email = emailR;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStayedDays() {
		return stayedDays;
	}

	public void setStayedDays(int stayedDays) {
		this.stayedDays = stayedDays;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getNoOfRoomsBooked() {
		return noOfRoomsBooked;
	}

	public void setNoOfRoomsBooked(int noOfRoomsBooked) {
		this.noOfRoomsBooked = noOfRoomsBooked;
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

}
