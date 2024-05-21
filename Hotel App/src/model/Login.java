package model;

public class Login {
	// Attributes
	private int loginID;
	private String loginPassword;

	// Constructor
	public Login(int loginID, String loginPassword) {
		this.loginID = loginID;
		this.loginPassword = loginPassword;
	}

	// Getters and Setters
	public int getLoginID() {
		return loginID;
	}

	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	// toString method to display login information
	@Override
	public String toString() {
		return "Login ID: " + loginID + "\nLogin Password: " + loginPassword;
	}
}
