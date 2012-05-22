package com.ogs.datastore;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.google.common.collect.Lists;

public class Recipe<E extends Ingredient> implements Iterable<E> {
	
	private final String name;
	private List<E> ingredients;
	
	@SuppressWarnings("unused")
	private Recipe() { this(""); }
	
	public Recipe(String name) {
		this.name = name;
		this.ingredients = Lists.newArrayList();
	}

	public String getName() {
		return name;
	}

	@Override
	public Iterator<E> iterator() {
		return this.ingredients.iterator();
	}
	
	@Override
	public String toString() {
		String rtn = "Name: " + this.name + "\nIngredients:";
		for (Ingredient ingr : this.ingredients) {
			rtn += "\n" + ingr.toString();
		}
		return rtn;
	}
	
	private void addIngredient(E ingr) {
		this.ingredients.add(ingr);
	}
	
	private void addIngredients(Collection<E> ingredients) {
		this.ingredients.addAll(ingredients);
	}
	
	public static Recipe<MatchedIngredient> buildMatchedRecipe(String name,
			List<MatchedIngredient> ingredients) {
		Recipe<MatchedIngredient> rtn = new Recipe<MatchedIngredient>(name);
		rtn.addIngredients(ingredients);
		return rtn;
	}
	
	public static Recipe<Ingredient> recipeFromElement(Element element) {
		String name = element.attributeValue("name");
		Recipe<Ingredient> rtn = new Recipe<Ingredient>(name);
		for (Iterator<Element> i = element.elementIterator(); i.hasNext();) {
        	Element ingr = i.next();
			rtn.addIngredient(Ingredient.ingredientFromElement(ingr));
		}
		return rtn;
	}
}
