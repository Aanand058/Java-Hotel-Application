package model;

public class Guest {
	// Attributes
	private int guestID;
	private String title;
	private String firstName;
	private String lastName;
	private String address;
	private Long phone;
	private String email;

	// Constructor
	public Guest(int guestID, String title, String firstName, String lastName, String address, Long phone,
			String email) {
		this.guestID = guestID;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	// Getters and Setters
	public int getGuestID() {
		return guestID;
	}

	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// toString method to display guest information
	@Override
	public String toString() {
		return "Guest ID: " + guestID + "\nTitle: " + title + "\nFirst Name: " + firstName + "\nLast Name: " + lastName
				+ "\nAddress: " + address + "\nPhone: " + phone + "\nEmail: " + email;
	}
}
