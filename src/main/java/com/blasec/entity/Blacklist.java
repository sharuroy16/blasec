package com.blasec.entity;

public class Blacklist {

	private String ip;
	private String cardholdername;
	private String cardholderaddress;
	private String cardnumber;
	
	public Blacklist() {}
	
	public Blacklist(String ip, String cardholdername, String cardholderaddress, String cardnumber) {
		this.ip = ip;
		this.cardholdername = cardholdername;
		this.cardholderaddress = cardholderaddress;
		this.cardnumber = cardnumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCardholdername() {
		return cardholdername;
	}

	public void setCardholdername(String cardholdername) {
		this.cardholdername = cardholdername;
	}

	public String getCardholderaddress() {
		return cardholderaddress;
	}

	public void setCardholderaddress(String cardholderaddress) {
		this.cardholderaddress = cardholderaddress;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
}
