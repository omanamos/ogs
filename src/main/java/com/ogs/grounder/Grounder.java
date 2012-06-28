package com.ogs.grounder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ogs.datastore.GroceryItem;
import com.ogs.datastore.Ingredient;
import com.ogs.datastore.MatchedIngredient;
import com.ogs.datastore.Recipe;

public class Grounder {

	private Set<GroceryItem> groceries;
	
	public Grounder() throws ClassNotFoundException, SQLException {
	      this.groceries = loadGrocieries();
	}

	public Set<GroceryItem> getGroceries() {
		return groceries;
	}
	
	public Recipe<MatchedIngredient> ground(Recipe<Ingredient> recipe) {
		List<MatchedIngredient> matchedIngredients = Lists.newArrayList();
		for (Ingredient ingr : recipe) {
			List<GroceryItem> matches = getMatches(ingr);
			matchedIngredients.add(new MatchedIngredient(ingr.getContent(), matches));
		}
		return Recipe.buildMatchedRecipe(recipe.getName(), matchedIngredients);
	}
	
	private List<GroceryItem> getMatches(Ingredient ingr) {
		//TODO: return matches for ingredient
		return null;
	}
	
	public Set<Recipe<MatchedIngredient>> ground(Set<Recipe<Ingredient>> recipes) {
		Set<Recipe<MatchedIngredient>> rtn = Sets.newHashSet();
		for (Recipe<Ingredient> recipe : recipes) {
			rtn.add(this.ground(recipe));
		}
		return rtn;
	}
	
	@Override
	public String toString() {
//		String rtn = "";
//		for (Recipe rec : this.recipes) {
//			rtn += rec.toString() + "\n\n";
//		}
//		return rtn.trim();
		return this.groceries.size() + "";
	}
	
	private static Set<GroceryItem> loadGrocieries() throws ClassNotFoundException, SQLException {
		Set<GroceryItem> rtn = Sets.newHashSet();
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Ryan\\Desktop\\Dev\\workspace\\data\\data");
	    Statement stat = conn.createStatement();
	    
	    ResultSet rs = stat.executeQuery("select base_name from fresh where usable;");
	    while (rs.next()) {
	    	rtn.add(new GroceryItem(rs.getString("base_name")));
	    }
	    
	    rs.close();
	    conn.close();
	    return rtn;
	}
}
