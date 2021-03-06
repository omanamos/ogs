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
import com.ogs.datastore.MatchedGroceryItem;
import com.ogs.datastore.MatchedIngredient;
import com.ogs.datastore.Recipe;

public class Grounder {

    private IngredientMatcher matcher;

    public Grounder() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =
            DriverManager.getConnection("jdbc:sqlite:database");
        this.matcher = new IngredientMatcher(conn);
    }
	
    public Recipe<MatchedIngredient> ground(Recipe<Ingredient> recipe) {
        List<MatchedIngredient> matchedIngredients = Lists.newArrayList();
        for (Ingredient ingr : recipe) {
            List<MatchedGroceryItem> matches = this.matcher.match(ingr);
            matchedIngredients.add(new MatchedIngredient(ingr, matches));
        }
        return Recipe.buildMatchedRecipe(recipe.getName(), matchedIngredients);
    }

    public Set<Recipe<MatchedIngredient>> ground(Set<Recipe<Ingredient>> recipes) {
        Set<Recipe<MatchedIngredient>> rtn = Sets.newHashSet();
        for (Recipe<Ingredient> recipe : recipes) {
            rtn.add(this.ground(recipe));
        }
        return rtn;
    }
}
