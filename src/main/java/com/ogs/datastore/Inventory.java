package com.ogs.datastore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
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
    private List<GroceryItemCategory> sortedCategories;

    private Map<String, GroceryItem> groceryItemLookup;
    private Map<String, List<GroceryItemCategory>> categoryLookup;

    private Inventory() {}

    /**
     * For use by the static constructor in this class only
     * @param groceries set of grocery items
     * @param sortedCategories set of grocery categories sorted in descending
     * order wrt number of grocery items in any given category
     */
    private Inventory(Set<GroceryItem> groceries,
                      List<GroceryItemCategory> sortedCategories) {
        this.groceries = groceries;
        this.sortedCategories = sortedCategories;

        this.groceryItemLookup = Maps.newHashMap();
        for (GroceryItem item : this.groceries) {
            this.groceryItemLookup.put(item.getName(), item);
        }

        this.categoryLookup = Maps.newHashMap();
        for (GroceryItemCategory cat : this.sortedCategories) {
            if (!this.categoryLookup.containsKey(cat.getName())) {
                this.categoryLookup.put(cat.getName(),
                                        Lists.<GroceryItemCategory>newArrayList());
            }
            this.categoryLookup.get(cat.getName()).add(cat);
        }
    }

    /**
     * @param key string to look for exact match for in the grocery index
     * @return matching grocery item, null otherwise
     */
    public GroceryItem lookUpByItemName(String key) {
        return this.groceryItemLookup.get(key);        
    }

    /**
     * @param key string to look for exact match for in the grocery category index
     * @return matching grocery item, null otherwise
     */
    public List<GroceryItemCategory> lookUpByCategory(String key) {
        return this.categoryLookup.get(key);
    }

    @Override
    public String toString() {
        return "Inventory:\n------------\nGrocery Size: " +
            this.groceries.size() + "\nCategory Size: " +
            this.sortedCategories.size();
    }

    /**
     * @param conn connection to grocery inventory database
     * @return Inventory object populated with data retrieved from given
     * database connection
     */
    public static Inventory buildFromSql(Connection conn) throws SQLException {
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
            }
        }
        List<GroceryItemCategory> sortedCategories =
            Lists.newArrayList(categories.keySet());
        Collections.sort(sortedCategories);

        Inventory rtn = new Inventory(inv, sortedCategories);
        return rtn;
    }
}
