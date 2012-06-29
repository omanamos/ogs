package com.ogs.datastore;

import org.dom4j.Element;

public class Ingredient {

    private final String content;

    private Ingredient() { this(""); }
	
    public Ingredient(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    public static Ingredient ingredientFromElement(Element element) {
        return new Ingredient(element.getTextTrim());
    }
}
