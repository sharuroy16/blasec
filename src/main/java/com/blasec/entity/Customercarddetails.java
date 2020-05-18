package com.blasec.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Class 'Customercarddetails' to map with the table 'customercarddetails' in the database 'blasec'.
 * 
 * As you can see, we use the annotation @Entity to map this class to the table customercarddetails (the class has same name as the table). 
 * All the class field names are also identical to the table's ones. 
 * The field id is annotated with @Id and @GeneratedValue annotations to indicate that this field is primary key 
 * and its value is auto generated. 
 */

@Entity
public class Customercarddetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String nameonthecard;
	private long cardnumber;
	private int cvv;
	private String expirydate;
	@Transient
	private long txnamount;
	private String billingadrs;
	@Transient
	private int month;
	@Transient
	private int year;
	
	public Customercarddetails() {}

	public Customercarddetails(String username, String nameonthecard, long cardnumber, int cvv, String expirydate, long txnamount, 
			String billingadrs, int month, int year) {
		this.username = username;
		this.nameonthecard = nameonthecard;
		this.cardnumber = cardnumber;
		this.cvv = cvv;
		this.expirydate = expirydate;
		this.txnamount = txnamount;
		this.billingadrs = billingadrs;
		this.month = month;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNameonthecard() {
		return nameonthecard;
	}

	public void setNameonthecard(String nameonthecard) {
		this.nameonthecard = nameonthecard;
	}

	public long getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(long cardnumber) {
		this.cardnumber = cardnumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}

	public long getTxnamount() {
		return txnamount;
	}

	public void setTxnamount(long txnamount) {
		this.txnamount = txnamount;
	}

	public String getBillingadrs() {
		return billingadrs;
	}

	public void setBillingadrs(String billingadrs) {
		this.billingadrs = billingadrs;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingadrs == null) ? 0 : billingadrs.hashCode());
		result = prime * result + (int) (cardnumber ^ (cardnumber >>> 32));
		result = prime * result + cvv;
		result = prime * result + ((expirydate == null) ? 0 : expirydate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + month;
		result = prime * result + ((nameonthecard == null) ? 0 : nameonthecard.hashCode());
		result = prime * result + (int) (txnamount ^ (txnamount >>> 32));
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + year;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customercarddetails other = (Customercarddetails) obj;
		if (billingadrs == null) {
			if (other.billingadrs != null)
				return false;
		} else if (!billingadrs.equals(other.billingadrs))
			return false;
		if (cardnumber != other.cardnumber)
			return false;
		if (cvv != other.cvv)
			return false;
		if (expirydate == null) {
			if (other.expirydate != null)
				return false;
		} else if (!expirydate.equals(other.expirydate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (month != other.month)
			return false;
		if (nameonthecard == null) {
			if (other.nameonthecard != null)
				return false;
		} else if (!nameonthecard.equals(other.nameonthecard))
			return false;
		if (txnamount != other.txnamount)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customercarddetails [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", nameonthecard=");
		builder.append(nameonthecard);
		builder.append(", cardnumber=");
		builder.append(cardnumber);
		builder.append(", cvv=");
		builder.append(cvv);
		builder.append(", expirydate=");
		builder.append(expirydate);
		builder.append(", txnamount=");
		builder.append(txnamount);
		builder.append(", billingadrs=");
		builder.append(billingadrs);
		builder.append(", month=");
		builder.append(month);
		builder.append(", year=");
		builder.append(year);
		builder.append("]");
		return builder.toString();
	}
}