package com.ogs.datastore;

import static com.ogs.grounder.Utils.cleanString;

public class GroceryItem implements HasName {

    private final String name;
    private final String originalName;
    private GroceryItemCategory[] categories;
	
    public GroceryItem(String name) {
        this.originalName = name;
        this.name = cleanString(name);
        this.categories = new GroceryItemCategory[3];
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return this.originalName;
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
        return originalName;
    }

    void addCategory(GroceryItemCategory cat) {
        this.categories[cat.getLevel()] = cat;
    }
}
