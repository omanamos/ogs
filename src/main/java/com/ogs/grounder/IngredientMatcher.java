package com.ogs.grounder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.ogs.datastore.GroceryItem;
import com.ogs.datastore.GroceryItemCategory;
import com.ogs.datastore.Ingredient;
import com.ogs.datastore.Inventory;
import com.ogs.datastore.MatchedGroceryItem;

public class IngredientMatcher {

    private Inventory inventory;

    private IngredientMatcher() {};

    public IngredientMatcher(Connection conn) throws SQLException {
        this.inventory = Inventory.buildFromSql(conn);
    }

    public List<MatchedGroceryItem> match(Ingredient ingr) {
        Set<MatchedGroceryItem> matches = Sets.newHashSet();
        // TODO: add hashcode in matchedgroceryitem
        matches.add(this.getExactMatches(ingr));
    }

    private Collection<GroceryItem> getExactMatches(Ingredient ingr) {
        // TODO: parse ingredient content
        GroceryItem match = this.inventory.lookupByItemName(ingr.getContent());
        return Lists.newArrayList(new MatchedGroceryItem(match, 100.0));
    }
}
