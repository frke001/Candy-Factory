package org.unibl.etf.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
	
	private int id;
	private String name;
	private double price;
	private int quantity;
	public static int count = 1;
	public Product() {
		super();
		this.id = count++;
	}
	public Product(String name, double price, int quantity) {
		this();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	public Product(Map<String, String> objectMap) {
		this.id = Integer.parseInt(objectMap.get("id"));
		this.name = objectMap.get("name");
		this.price = Double.parseDouble(objectMap.get("price"));
		this.quantity = Integer.parseInt(objectMap.get("quantity"));
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
	public Map<String, String> toMap(){
		Map<String, String> objectMap = new HashMap<>();
		objectMap.put("id", String.valueOf(id));
		objectMap.put("name", name);
		objectMap.put("price", String.valueOf(price));
		objectMap.put("quantity", String.valueOf(quantity));
		
		return objectMap;
	}
}
