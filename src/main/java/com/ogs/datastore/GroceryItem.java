package com.ogs.datastore;

public class GroceryItem {

    private final String name;
    private GroceryItemCategory[] categories;
	
    public GroceryItem(String name) {
        this.name = name;
        this.categories = new GroceryItemCategory[3];
    }

    public String getName() {
        return name;
    }

    public GroceryItemCategory getCategory(int level) {
        return this.categories[level];
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

    void addCategory(GroceryItemCategory cat) {
        this.categories[cat.getLevel()] = cat;
    }
}
