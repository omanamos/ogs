package com.ogs.datastore;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class MatchedIngredient implements Iterable<MatchedGroceryItem> {

    private Ingredient ingr;
    private List<MatchedGroceryItem> matches;
	
    private MatchedIngredient() { }
	
    public MatchedIngredient(Ingredient ingr, List<MatchedGroceryItem> matches) {
        this.ingr = ingr;
        this.matches = Lists.newArrayList(matches);
    }

    public Ingredient getIngredient() {
        return this.ingr;
    }

    @Override
    public Iterator<MatchedGroceryItem> iterator() {
        return this.matches.iterator();
    }

    @Override
    public String toString() {
        String rtn = "Ingredient: " + ingr.toString() + "\nMatches:\n";
        int cnt = 0;
        for (MatchedGroceryItem match : this.matches) {
            rtn += match.toString() + "\n";
            cnt++;
            if (cnt > 10) break;
        }
        return rtn;
    }
}
