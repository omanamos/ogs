package com.ogs.grounder;

import java.util.Properties;
import java.util.Set;

import com.ogs.datastore.GroceryItem;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class IngredientMatcher {
	
	private IngredientMatcher() {};
	
	private IngredientMatcher(Set<GroceryItem> groceries) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	}
}
