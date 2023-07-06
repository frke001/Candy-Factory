package org.unibl.etf.model;

import java.util.List;

public class Order {
	
	private String user;
	private String userEmail;
	private List<ProductOrder> orderItems;
	private boolean status = false;
	public Order() {
		super();
	}



	public Order(String user, String userEmail, List<ProductOrder> orderItems) {
		super();
		this.user = user;
		this.userEmail = userEmail;
		this.orderItems = orderItems;
	}



	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public List<ProductOrder> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<ProductOrder> orderItems) {
		this.orderItems = orderItems;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	@Override
	public String toString() {
		return "Order [userId=" + user + ", userEmail=" + userEmail + ", orderItems=" + orderItems + ", status="
				+ (status? "Prihvaćena": "Nije prihvaćena") + "]";
	}


	
	
}

