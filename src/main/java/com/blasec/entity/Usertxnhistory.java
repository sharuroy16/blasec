package com.blasec.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class 'Usertxnhistory' to map with the table 'usertxnhistory' in the database 'blasec'.
 * 
 * As you can see, we use the annotation @Entity to map this class to the table usertxnhistory (the class has same name as the table). 
 * All the class field names are also identical to the table's ones. 
 * The field id is annotated with @Id and @GeneratedValue annotations to indicate that this field is primary key 
 * and its value is auto generated. 
 */

@Entity
public class Usertxnhistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private long txnamount;
	private String txncontinent;
	private String txncountry;
	private double txnlatitude;
	private double txnlongitude;
	private String txntimezone;
	private String txndate;
	private String remarks;
	
	public Usertxnhistory() {}

	public Usertxnhistory(String username, long txnamount, String txncontinent, String txncountry, double txnlatitude, 
			double txnlongitude, String txntimezone, String txndate, String remarks) {
		this.username = username;
		this.txnamount = txnamount;
		this.txncontinent = txncontinent;
		this.txncountry = txncountry;
		this.txnlatitude = txnlatitude;
		this.txnlongitude = txnlongitude;
		this.txntimezone = txntimezone;
		this.txndate = txndate;
		this.remarks = remarks;
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

	public long getTxnamount() {
		return txnamount;
	}

	public void setTxnamount(long txnamount) {
		this.txnamount = txnamount;
	}

	public String getTxncontinent() {
		return txncontinent;
	}

	public void setTxncontinent(String txncontinent) {
		this.txncontinent = txncontinent;
	}

	public String getTxncountry() {
		return txncountry;
	}

	public void setTxncountry(String txncountry) {
		this.txncountry = txncountry;
	}

	public double getTxnlatitude() {
		return txnlatitude;
	}

	public void setTxnlatitude(double txnlatitude) {
		this.txnlatitude = txnlatitude;
	}

	public double getTxnlongitude() {
		return txnlongitude;
	}

	public void setTxnlongitude(double txnlongitude) {
		this.txnlongitude = txnlongitude;
	}

	public String getTxntimezone() {
		return txntimezone;
	}

	public void setTxntimezone(String txntimezone) {
		this.txntimezone = txntimezone;
	}

	public String getTxndate() {
		return txndate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + (int) (txnamount ^ (txnamount >>> 32));
		result = prime * result + ((txncontinent == null) ? 0 : txncontinent.hashCode());
		result = prime * result + ((txncountry == null) ? 0 : txncountry.hashCode());
		result = prime * result + ((txndate == null) ? 0 : txndate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(txnlatitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(txnlongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((txntimezone == null) ? 0 : txntimezone.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usertxnhistory other = (Usertxnhistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (txnamount != other.txnamount)
			return false;
		if (txncontinent == null) {
			if (other.txncontinent != null)
				return false;
		} else if (!txncontinent.equals(other.txncontinent))
			return false;
		if (txncountry == null) {
			if (other.txncountry != null)
				return false;
		} else if (!txncountry.equals(other.txncountry))
			return false;
		if (txndate == null) {
			if (other.txndate != null)
				return false;
		} else if (!txndate.equals(other.txndate))
			return false;
		if (Double.doubleToLongBits(txnlatitude) != Double.doubleToLongBits(other.txnlatitude))
			return false;
		if (Double.doubleToLongBits(txnlongitude) != Double.doubleToLongBits(other.txnlongitude))
			return false;
		if (txntimezone == null) {
			if (other.txntimezone != null)
				return false;
		} else if (!txntimezone.equals(other.txntimezone))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usertxnhistory [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", txnamount=");
		builder.append(txnamount);
		builder.append(", txncontinent=");
		builder.append(txncontinent);
		builder.append(", txncountry=");
		builder.append(txncountry);
		builder.append(", txnlatitude=");
		builder.append(txnlatitude);
		builder.append(", txnlongitude=");
		builder.append(txnlongitude);
		builder.append(", txntimezone=");
		builder.append(txntimezone);
		builder.append(", txndate=");
		builder.append(txndate);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append("]");
		return builder.toString();
	}
}