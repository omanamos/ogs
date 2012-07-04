package com.ogs.grounder;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.common.collect.Sets;
import com.ogs.datastore.Ingredient;
import com.ogs.datastore.Recipe;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Grounder g = new Grounder();
		Set<Recipe<Ingredient>> recipes = parseXml("data/raw.xml");
		g.ground(recipes);
	}
	
	public static Set<Recipe<Ingredient>> parseXml(String recipeFileName) {
		Document document = null;
		SAXReader reader = new SAXReader();
		try
		{
		   document = reader.read( recipeFileName );
		}
		catch (DocumentException e)
		{
		   e.printStackTrace();
		}
		Set<Recipe<Ingredient>> rtn = Sets.newHashSet();
		Element root = document.getRootElement();

        // iterate through child elements of root
		for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
        	Element element = i.next();
        	rtn.add(Recipe.recipeFromElement(element));
        }
        
        return rtn;
	}
}
