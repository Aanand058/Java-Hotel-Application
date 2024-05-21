package model;

public class Bill {
	// Attributes
	private int billID;
	private double amountToPay;

	// Constructor
	public Bill(int billID, double amountToPay) {
		this.billID = billID;
		this.amountToPay = amountToPay;
	}

	// Getters and Setters
	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	// toString method to display bill information
	@Override
	public String toString() {
		return "Bill ID: " + billID + "\nAmount to Pay: $" + amountToPay;
	}
}
