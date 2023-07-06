package org.unibl.etf.model;

import java.util.Objects;

public class User {
	
	private String username;
	private String password;
	private String companyName;
	private String address;
	private String phoneNumber;
	private boolean activated = false;
	private boolean blocked = false;
	private String email;
	
	public User() {
		super();
	}

	public User(String username) {
		super();
		this.username = username;
	}

	public User(String username, String password, String companyName, String address, String phoneNumber,
			boolean activated, boolean blocked, String email) {
		super();
		this.username = username;
		this.password = password;
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.activated = activated;
		this.blocked = blocked;
		this.email = email;
	}
	

	public User(String username, String password, String companyName, String address, String phoneNumber,
			String email) {
		super();
		this.username = username;
		this.password = password;
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", companyName=" + companyName + ", address="
				+ address + ", phoneNumber=" + phoneNumber + ", activated=" + activated + ", blocked=" + blocked
				+ ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}
	
	
}
