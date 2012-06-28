package com.ogs.grounder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

import com.ogs.datastore.GroceryItem;
import com.ogs.datastore.GroceryItemCategory;

public class IngredientMatcher {

    private Set<GroceryItem> inventory;
    private Map<GroceryItemCategory, GroceryItem> catLookup;

    private IngredientMatcher() {};

    public static void buildFromSql(Connection conn) throws SQLException {

    }
}
