package com.ogs.datastore;

public class GroceryItem {

	private final String name;
	
	public GroceryItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroceryItem) {
			GroceryItem other = (GroceryItem) obj;
			return this.name.equals(other.name);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
