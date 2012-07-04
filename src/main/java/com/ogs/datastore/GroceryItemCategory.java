package com.ogs.datastore;

import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;

public class GroceryItemCategory implements HasName, Iterable<GroceryItem>,
                                            Comparable<GroceryItemCategory> {

    private final String name;
    private final int level;
    private Set<GroceryItem> groceries;

    private GroceryItemCategory() { this("", 0); }

    public GroceryItemCategory(String name, int level) {
        this.name = name;
        this.level = level;
        this.groceries = Sets.newHashSet();
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(GroceryItemCategory other) {
        Integer thisSize = new Integer(this.groceries.size());
        Integer otherSize = new Integer(other.groceries.size());
        return -thisSize.compareTo(otherSize);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GroceryItemCategory) {
            GroceryItemCategory other = (GroceryItemCategory) obj;
            return this.name.equals(other.name) && this.level == other.level;
        } else {
            return false;
        }
    }
	
    @Override
    public int hashCode() {
        return name.hashCode() + level;
    }

    @Override
    public Iterator<GroceryItem> iterator() {
        return this.groceries.iterator();
    }
	
    @Override
    public String toString() {
        return name;
    }

    void addItem(GroceryItem item) {
        this.groceries.add(item);
    }
}
