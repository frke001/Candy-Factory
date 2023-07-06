package model;

import java.io.Serializable;
import java.util.Objects;

public class RawMaterial implements Serializable {
	
	private int id;
	private String name;
	private int quantity;
	private static int count = 1;
	public RawMaterial() {
		super();
		id = count++;
	}
	public RawMaterial(String name, int quantity) {
		this();
		this.name = name;
		this.quantity = quantity;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "RawMaterial [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
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
		RawMaterial other = (RawMaterial) obj;
		return id == other.id;
	}
	

}