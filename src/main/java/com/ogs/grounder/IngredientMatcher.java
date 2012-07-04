package com.ogs.grounder;

import static com.ogs.datastore.MatchedGroceryItem.MatchType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
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
        // TODO: parse ingredient content
        Map<GroceryItem, MatchedGroceryItem> matches = Maps.newHashMap();
        mergeMatches(matches, getExactMatches(ingr));
        mergeMatches(matches, getWordOverlapMatches(ingr));

        List<MatchedGroceryItem> rtn = Lists.newArrayList(matches.values());
        Collections.sort(rtn);
        return rtn;
    }

    private void mergeMatches(Map<GroceryItem, MatchedGroceryItem> matches,
                              Collection<MatchedGroceryItem> newMatches) {
        for (MatchedGroceryItem match : newMatches) {
            GroceryItem item = match.getGroceryItem();
            if (!matches.containsKey(match.getGroceryItem())) {
                matches.put(item, match);
            } else {
                matches.put(item, MatchedGroceryItem.union(match, matches.get(item)));
            }
        }
    }

    private Collection<MatchedGroceryItem> getExactMatches(Ingredient ingr) {
        GroceryItem match = this.inventory.lookUpByItemName(ingr.getContent());
        if (match == null) {
            return Lists.newArrayList();
        } else {
            return Lists.newArrayList(
                       new MatchedGroceryItem(match, 100.0, MatchType.EXACT));
        }
    }

    private Collection<MatchedGroceryItem> getWordOverlapMatches(Ingredient ingr) {
        return this.inventory.lookUpByWord(ingr.getContent());
    }
}
