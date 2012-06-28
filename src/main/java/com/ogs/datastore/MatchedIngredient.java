package com.ogs.datastore;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class MatchedIngredient extends Ingredient implements Iterable<GroceryItem> {
	
	private List<GroceryItem> matches;
	
	@SuppressWarnings("unused")
	private MatchedIngredient() { this(""); }
	
	private MatchedIngredient(String content) { this("", Lists.<GroceryItem>newArrayList()); }
	
	public MatchedIngredient(String content, List<GroceryItem> matches) {
		super(content);
		this.matches = Lists.newArrayList(matches);
	}

	@Override
	public Iterator<GroceryItem> iterator() {
		return this.matches.iterator();
	}
}
