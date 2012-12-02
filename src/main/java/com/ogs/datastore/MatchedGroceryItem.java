package com.ogs.datastore;

import java.util.Set;

import com.google.common.collect.Sets;

public class MatchedGroceryItem implements Comparable<MatchedGroceryItem> {

    public enum MatchType { EXACT, OVERLAP };

    private Query query;
    private GroceryItem item;
    private double score;
    private Set<MatchType> types;

    private MatchedGroceryItem() {}

    public MatchedGroceryItem(Query query, GroceryItem item, MatchType type) {
        this(query, item, score, Sets.newHashSet(type));
    }

    public MatchedGroceryItem(Query query, GroceryItem item, double score,
                              Set<MatchType> types) {
        if (query == null || item == null || types == null) {
            throw new IllegalArgumentException("Item and type must be non-null");
        }
        this.query = query;
        this.item = item;
        this.score = score;
        this.types = Sets.newHashSet(types);
    }

    public GroceryItem getGroceryItem() {
        return this.item;
    }

    public double getScore() {
        return this.score;
    }

    public boolean hasType(MatchType type) {
        return this.types.contains(type);
    }

    @Override
    public int compareTo(MatchedGroceryItem other) {
        if (this.types.contains(MatchType.EXACT)) {
            return 1;
        } else if (other.types.contains(MatchType.EXACT)) {
            return -1;
        } else {
            Double thisScore = new Double(this.score);
            Double otherScore = new Double(other.score);
            return -thisScore.compareTo(otherScore);
        }
    }

    @Override
    public String toString() {
        return this.item.toString() + " | " + this.score;
    }

    public static MatchedGroceryItem union(MatchedGroceryItem match1,
                                           MatchedGroceryItem match2) {
        if (match1.item.equals(match2.item)) {
            if (match1.hasType(MatchType.EXACT) && match2.hasType(MatchType.OVERLAP)) {
                return match1;
            } else if (match1.hasType(MatchType.OVERLAP)
                       && match2.hasType(MatchType.EXACT)) {
                return match2;
            } else {
                return new MatchedGroceryItem(match1.item, match1.score + match2.score,
                                              Sets.union(match1.types, match2.types));
            }
        } else {
            throw new IllegalArgumentException("match1 and match2 do not contain the " +
                                               "same grocery item");
        }
    }
}
