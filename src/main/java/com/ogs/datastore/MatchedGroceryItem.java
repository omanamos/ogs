package com.ogs.datastore;

public class MatchedGroceryItem implements Comparable<MatchedGroceryItem> {

    private GroceryItem item;
    private double score;

    private MatchedGroceryItem() {}

    public MatchedGroceryItem(GroceryItem item, double score) {
        this.item = item;
        this.score = score;
    }

    public GroceryItem getGroceryItem() {
        return this.item;
    }

    public double getScore() {
        return this.score;
    }

    @Override
    public int compareTo(MatchedGroceryItem other) {
        Double thisScore = new Double(this.score);
        Double otherScore = new Double(other.score);
        return -thisScore.compareTo(otherScore);
    }
}
