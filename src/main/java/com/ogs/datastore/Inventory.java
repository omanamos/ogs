package com.ogs.datastore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Inventory {

    private static final String SELECT_SQL =
        "select distinct base_name as name, c0.category as cat0, c1.category " +
        "as cat1, c2.category as cat2 from fresh f join fresh_categories c0 " +
        "on f.asin = c0.asin join fresh_categories c1 on f.asin = c1.asin " +
        "join fresh_categories c2 on f.asin = c2.asin where c0.level = 0 and " +
        "c1.level = 1 and c2.level = 2;";

    private Set<GroceryItem> groceries;

    private Inventory() {}

    private Inventory(Set<GroceryItem> groceries) {
        this.groceries = groceries;
    }

    public static void buildFromSql(Connection conn) throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(SELECT_SQL);
        
        Set<GroceryItem> inv = Sets.newHashSet();
        Map<GroceryItemCategory, GroceryItemCategory> categories =
            Maps.newHashMap();

        while(rs.next()) {
            GroceryItem item = new GroceryItem(rs.getString("name"));
            inv.add(item);

            for (int i = 0; i < 3; i++) {
                String categoryName = rs.getString("cat" + i);
                GroceryItemCategory cat =
                    new GroceryItemCategory(categoryName, i);
                if (!categories.containsKey(cat)) {
                    categories.put(cat, cat);
                }
                cat = categories.get(cat); // Retrieve object w/ data
                cat.addItem(item);
                item.addCategory(cat);
                // TODO: index categories somehow (order by category size?)
            }
        }
    }
}
