package com.ogs.grounder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
        Map<GroceryItem, MatchedGroceryItem> matches = Maps.newHashMap();
        for (MatchedGroceryItem match : getExactMatches(ingr)) {
            GroceryItem item = match.getGroceryItem();
            if (!matches.containsKey(match.getGroceryItem())) {
                matches.put(item, match);
            } else {
                matches.put(item, MatchedGroceryItem.union(match, matches.get(item)));
            }
        }
        return Lists.newArrayList(matches.values());
    }

    private Collection<MatchedGroceryItem> getExactMatches(Ingredient ingr) {
        // TODO: parse ingredient content
        GroceryItem match = this.inventory.lookUpByItemName(ingr.getContent());
        return Lists.newArrayList(new MatchedGroceryItem(match, 100.0));
    }
}
