package com.blasec.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class 'Logindetails' to map with the table 'logindetails' in the database 'blasec'.
 * 
 * As you can see, we use the annotation @Entity to map this class to the table logindetails (the class has same name as the table). 
 * All the class field names are also identical to the table's ones. 
 * The field id is annotated with @Id and @GeneratedValue annotations to indicate that this field is primary key 
 * and its value is auto generated. 
 */
@Entity
public class Logindetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String userpassword;
	private String securityquestion;
	private String securityanswer;
	private char openstatus;

	public Logindetails() {}

	public Logindetails(String username, String userpassword, String securityquestion, String securityanswer, char openstatus) {
		this.username = username;
		this.userpassword = userpassword;
		this.securityquestion = securityquestion;
		this.securityanswer = securityanswer;
		this.openstatus = openstatus;
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

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getSecurityquestion() {
		return securityquestion;
	}

	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}

	public String getSecurityanswer() {
		return securityanswer;
	}

	public void setSecurityanswer(String securityanswer) {
		this.securityanswer = securityanswer;
	}

	public char getOpenstatus() {
		return openstatus;
	}

	public void setOpenstatus(char openstatus) {
		this.openstatus = openstatus;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + openstatus;
		result = prime * result + ((securityanswer == null) ? 0 : securityanswer.hashCode());
		result = prime * result + ((securityquestion == null) ? 0 : securityquestion.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((userpassword == null) ? 0 : userpassword.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logindetails other = (Logindetails) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (openstatus != other.openstatus)
			return false;
		if (securityanswer == null) {
			if (other.securityanswer != null)
				return false;
		} else if (!securityanswer.equals(other.securityanswer))
			return false;
		if (securityquestion == null) {
			if (other.securityquestion != null)
				return false;
		} else if (!securityquestion.equals(other.securityquestion))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (userpassword == null) {
			if (other.userpassword != null)
				return false;
		} else if (!userpassword.equals(other.userpassword))
			return false;
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Logindetails [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", userpassword=");
		builder.append(userpassword);
		builder.append(", securityquestion=");
		builder.append(securityquestion);
		builder.append(", securityanswer=");
		builder.append(securityanswer);
		builder.append(", openstatus=");
		builder.append(openstatus);
		builder.append("]");
		return builder.toString();
	}
}